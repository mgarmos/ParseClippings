package mgr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mgr.bean.Annotation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Overlapping {
	private static Logger logger;

	public static int[][] calculaMatrizCoincidencias(String[] seqA, String[] seqB) {

		int[][] resultado = new int[seqB.length][seqA.length];
		for (int fila = 0; fila < seqB.length; fila++) {
			for (int columna = 0; columna < seqA.length; columna++) {
				if (seqA[columna].equals(seqB[fila])) {
					resultado[fila][columna] = 1 + coincideAnterior(fila, columna, resultado);
				} else {
					resultado[fila][columna] = 0;
				}
			}
		}
		return resultado;
	}


	private static int coincideAnterior(int fila, int columna, int[][] coincidencias) {
		int resultado = 0;
		if (fila > 0 && columna > 0) {
			resultado = coincidencias[fila - 1][columna - 1];
		}
		return resultado;
	}

	private static int[] calculaPosicionMaximo(int[][] matrizCoincidencias) {
		int[] resultado = { 0, 0 };
		int max = matrizCoincidencias[0][0];
		for (int fila = 0; fila < matrizCoincidencias.length; fila++) {
			for (int columna = 0; columna < matrizCoincidencias[fila].length; columna++) {
				if (matrizCoincidencias[fila][columna] > max) {
					resultado[0] = columna; //X
					resultado[1] = fila; // y
					max = matrizCoincidencias[fila][columna];
				}
			}
		}
		return resultado;
	}


	private static List<String> reconstruye(String[] seqA, String[] seqB, int[][] matrizCoincidencias) {
		List<String> resultado = new ArrayList<String>();

		int[] maximo = calculaPosicionMaximo(matrizCoincidencias);
		int xMax = maximo[0];
		int yMax = maximo[1];


		if (matrizCoincidencias[yMax][xMax] > 0 && (xMax == seqA.length - 1 || yMax == seqB.length - 1)) {
			if (xMax == seqA.length - 1) {
				for (int cont = seqB.length - 1; cont > maximo[1]; cont--) {
					resultado.add(seqB[cont]);
				}
			} else {
				for (int cont = seqA.length - 1; cont > maximo[0]; cont--) {
					resultado.add(seqA[cont]);
				}
			}

			while (xMax >= 0 && yMax >= 0) {
				resultado.add(seqB[yMax]);
				xMax--;
				yMax--;
			}

			if (yMax == -1) {
				for (int pos = xMax; pos >= 0; pos--) {
					resultado.add(seqA[pos]);
				}

			} else if (xMax == -1) {
				for (int pos = yMax; pos >= 0; pos--) {
					resultado.add(seqB[pos]);
				}
			}

			Collections.reverse(resultado);
		}

		return resultado;
	}

	private static void imprime(int[][] resultado) {
		for (int fila = 0; fila < resultado.length; fila++) {
			for (int columna = 0; columna < resultado[fila].length; columna++) {
				System.out.print(resultado[fila][columna] + " ");
			}
			System.out.println("");
		}
	}

	private static String printList(List<String> cadena) {
		StringBuilder sb = new StringBuilder();
		for (String palabra : cadena) {
			sb.append(palabra + " ");
		}
		return sb.toString().trim();
	}

	public static List<Annotation> execute(List<Annotation> annotations, boolean verbose) {

		List<Annotation> resultado = new ArrayList<Annotation>();
		List<Annotation> deleted = new ArrayList<Annotation>();
		String annotation = "";

		String seqA = "";
		String seqB = "";

		//Initialize log
		if (verbose) {
			logger = LogManager.getLogger(Overlapping.class);
		}

		for (int i = 0; i < annotations.size() - 1; i++) {
			seqA = annotations.get(i).getMensaje().trim();
			if (!deleted.contains(annotations.get(i))) {
				for (int j = i + 1; j < annotations.size(); j++) {
					seqB = annotations.get(j).getMensaje().trim();
					
					int[][] matrizCoincidencias = Overlapping.calculaMatrizCoincidencias(seqA.split(" "), seqB.split(" "));
					annotation = printList(reconstruye(seqA.split(" "), seqB.split(" "), matrizCoincidencias));

					if (!annotation.equals("")) {
						seqA = annotation;
						deleted.add(annotations.get(j));
						if (verbose) {
							logger.info("... se elimina : " + seqA);
						}
						
					}
				}

				resultado.add(annotations.get(i));
				if (verbose) {
					logger.info("... se aniade : " + seqA);
				}
			}


		}

		if (verbose) {
			logger.info("Analizadas " + annotations.size() + " anotaciones");
			logger.info("Resultado " + resultado.size() + " anotaciones");
		}

		return resultado;
	}


	public static void main(String[] args) {

		Annotation an1 = new Annotation();
		an1.setAutor("Pepe");
		an1.setTitulo("Cosa");
		an1.setPaginaIni(10);
		an1.setPosIni(10);
		an1.setPosFin(10);
		an1.setFecha(new Date());
		an1.setMensaje("de mensaje");

		Annotation an2 = new Annotation();
		an2.setAutor("Pepe");
		an2.setTitulo("Cosa");
		an2.setPaginaIni(10);
		an2.setPosIni(10);
		an2.setPosFin(10);
		an2.setFecha(new Date());
		an2.setMensaje("Prueba de mensaje");

		Annotation an3 = new Annotation();
		an3.setAutor("Pepe");
		an3.setTitulo("Cosa");
		an3.setPaginaIni(10);
		an3.setPosIni(10);
		an3.setPosFin(10);
		an3.setFecha(new Date());
		an3.setMensaje("con truco");		
		
		List<Annotation> annotations = new ArrayList<Annotation>();
		annotations.add(an1);
		annotations.add(an2);
		annotations.add(an3);
		
		System.out.println(execute(annotations, true));
	}
}
