package mgr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mgr.bean.Annotation;

import org.junit.Test;

public class OverlappingTest {
	
	private Annotation an1;
	private Annotation an2;
	private Annotation an3;
	
	
	@Test
	public void calculaMatrizCoincidenciasTest() {
		
		String s1 = "It was the best of times, it was the worst of times it was the age of wisdom";
		String s2 = "best of times, it was the worst of times";
		String s3 = "it was the worst of times it was the age of wisdom";
		String s4 = "the winter of despair";
		
		
		int[][] resultado = Overlapping.calculaMatrizCoincidencias(s1.split(" "), s2.split(" "));
		
		Overlapping.imprime(resultado);
		
		
		
	}
	

	@Test
	public void executeTest() {
		
		String s1 = "It was the best of times it was the worst of times it was the age of wisdom";
		String s2 = "best of times it was the worst of times";
		String s3 = "it was the worst of times it was the age of wisdom";
		String s4 = "the winter of despair";
		
		List<Annotation> annotations = new ArrayList<Annotation>();
		annotations.add(getAnnotation(s1));
		annotations.add(getAnnotation(s2));
		annotations.add(getAnnotation(s3));
		annotations.add(getAnnotation(s4));
		
		System.out.println(Overlapping.execute(annotations, true));
		//assertEquals(1, 1);
		
		
		
	}
	
	/**
	 * Return always the same annotation that only differs in the message
	 * @param message
	 * @return
	 */
	private Annotation getAnnotation(String message) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String dateInString = "01/01/2000 00:00:00";
		Date date = null;
		
		try {
			 date = sdf.parse(dateInString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Annotation an1 = new Annotation();
		an1.setAutor("Pepe");
		an1.setTitulo("Cosa");
		an1.setPaginaIni(10);
		an1.setPosIni(10);
		an1.setPosFin(10);
		an1.setFecha(date);
		an1.setMensaje(message);
		
		return an1;
	
	}

}
