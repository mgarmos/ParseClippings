package mgr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mgr.bean.Annotation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class Parser {
	private static final String SEP_ANOTACION = "==========";
	
	private static Logger logger;
	

	public static void execute(String pathIn, String pathOut, boolean removeDuplicated, boolean removeOverlapped, boolean verbose) 
								throws FileNotFoundException, UnsupportedEncodingException, IOException {
		
		if (verbose) {
			logger = LogManager.getLogger(Parser.class);
		}
																																		  
		List<Annotation> anotaciones = readFile(pathIn, removeDuplicated, verbose);
		Collections.sort(anotaciones);

		//Detectar inclusiones y solapes
		if (removeOverlapped) {
			anotaciones = Overlapping.execute(anotaciones, verbose);
		}

		writeFile(pathOut, anotaciones);

	}

	private static List<Annotation> readFile(String path, boolean removeDuplicated, boolean verbose) throws FileNotFoundException, UnsupportedEncodingException, IOException {
		BufferedReader br = null;
		FileReader fr = null;
		List<Annotation> resultado = new ArrayList<Annotation>();

		try {
			fr = new FileReader(path);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));

			int contadorAnotaciones = 0;

			String linea;
			Annotation anotacion = null;
			int numLinea = 0;
			while ((linea = br.readLine()) != null) {

				//debug(linea);

				numLinea++;
				//Header. Contents author and title			
				if (numLinea == 1) {
					anotacion = new Annotation();
					setHeader(linea, anotacion);

				} else if (numLinea == 2) {
					setCoor(linea, anotacion);
				} else if (numLinea >= 4) {
					if (!linea.equals(SEP_ANOTACION)) {
						setMessage(linea, anotacion);
					} else {
						numLinea = 0;

						if (!removeDuplicated || !resultado.contains(anotacion)) {
							resultado.add(anotacion);
							contadorAnotaciones++;
						} else if (verbose){
							logger.info(" se desecha: " + anotacion.getMensaje());
						}
					}
				}
			}


		} finally {

			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (IOException ex) {
				//ex.printStackTrace();
			}
		}
		return resultado;
	}

	private static void setHeader(String text, Annotation anotacion) {

		//Se comprueba si el primer caracter es un código raro
		int primerCaracter = text.charAt(0);
		if (primerCaracter == 65279) {
			text = text.substring(1);
		}

		if (text.lastIndexOf(")") == text.length() - 1) {

			int sepIni = text.indexOf("(");
			if (sepIni > 0) {
				anotacion.setTitulo(text.substring(0, sepIni - 1));
				anotacion.setAutor(text.substring(sepIni + 1, text.length() - 1));
			} else {
				anotacion.setTitulo(text.substring(0, text.length()));
				anotacion.setAutor(""); // Dejar null
			}

		} else {
			int sepIni = text.indexOf("-");
			if (sepIni > 0) {
				anotacion.setTitulo(text.substring(0, sepIni - 1));
				anotacion.setAutor(text.substring(sepIni + 2, text.length()));
			} else {
				anotacion.setTitulo(text.substring(0));
				anotacion.setAutor(""); // Dejar null
			}
		}
	}

	/**
	 * Parses the type, position of the annotations and its date
	 * @param text
	 * @param anotacion
	 */
	private static void setCoor(String text, Annotation anotacion) {
		String[] trozo = text.split("\\|");

		int puntero = -1;

		if (trozo[0].indexOf("subrayado") > 0) {
			anotacion.setTipo(Annotation.SUB);
		} else if (text.indexOf("nota") > 0) {
			anotacion.setTipo(Annotation.NOT);
		} else if (trozo[0].indexOf("marcador") > 0) {
			anotacion.setTipo(Annotation.MAR);
		}

		if ((puntero = trozo[0].indexOf("página")) > 0) {
			anotacion.setPaginaIni(Integer.parseInt(trozo[0].substring(puntero + 7, trozo[0].length()).trim()));
		}

		if ((puntero = trozo[1].indexOf("posición")) > 0) {
			String[] coor = trozo[1].substring(puntero + 9, trozo[1].length()).trim().split("-");
			anotacion.setPosIni(Integer.parseInt(coor[0]));
			if (coor.length > 1) {
				anotacion.setPosFin(Integer.parseInt(coor[1]));
			}
		}

		String fecha = trozo[trozo.length - 1];
		anotacion.setFecha(getDate(fecha));
	}

	private static void setMessage(String text, Annotation anotacion) {
		//Delete doble quotes.Find onther solution
		text = text.replaceAll("\"", "");
		anotacion.setMensaje(anotacion.getMensaje() + text); //Por si son varia lineas
	}

	//@ UTIL

	private static Date getDate(String texto) {
		String fecha = texto.substring(texto.indexOf(",") + 2, texto.length());
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy HH:mm:ss", Locale.getDefault());
		Date resultado = null;
		try {
			resultado = sdf.parse(fecha);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultado;
	}


	private static void writeFile(String fileName, List<Annotation> anotaciones) throws IOException {
		BufferedWriter bw = null;
		FileWriter fw = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(Annotation.getCabecera());
			for (Annotation anotacion : anotaciones) {
				sb.append(anotacion.toCSV()).append("\r\n");
			}

			File file = new File(fileName);

			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(sb.toString());
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
				if (fw != null) {
					fw.close();
				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
