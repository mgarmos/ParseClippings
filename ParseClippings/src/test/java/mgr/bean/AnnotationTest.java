package mgr.bean;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class AnnotationTest {
	private Annotation an1;
	private Annotation an2;
	private Annotation an3;

	@Before
	public void setUp() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String dateInString = "26/01/2018 05:18:19";
		Date date = null;
		
		try {
			 date = sdf.parse(dateInString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		an1 = new Annotation();
		an1.setTitulo("Titulo"); 
		an1.setAutor("Autor");
		an1.setFecha(date);
		an1.setTipo(1);
		an1.setMensaje("Mensaje");
		an1.setPaginaIni(10);
		an1.setPaginaFin(11);
		an1.setPosIni(100);
		an1.setPosFin(200);
		
		an2 = new Annotation();
		an2.setTitulo("Titulo1"); 
		an2.setAutor("Autor");
		an2.setFecha(date);
		an2.setTipo(1);
		an2.setMensaje("Mensaje");
		an2.setPaginaIni(10);
		an2.setPaginaFin(11);
		an2.setPosIni(100);
		an2.setPosFin(200);
		
		an3 = new Annotation();
		an3.setTitulo("Titulo"); 
		an3.setAutor("Autor1");
		an3.setFecha(date);
		an3.setTipo(1);
		an3.setMensaje("Mensaje");
		an3.setPaginaIni(10);
		an3.setPaginaFin(11);
		an3.setPosIni(100);
		an3.setPosFin(200);
	}

	@Test
	public void shouldPrintCVSFormat() {
		String resultAn1 = "Titulo	Autor	Nota	10	11	100	200	26/01/2018 05:18:19	Mensaje";
		assertEquals(resultAn1, an1.toCSV());
	}
	
	@Test
	public void compareTo_equals() {
		assertEquals(0, an1.compareTo(an1));
	}
	
	@Test
	public void compareTo_next() {
		assertEquals(1, an2.compareTo(an1));
		assertEquals(-1, an1.compareTo(an2));
		assertEquals(1, an3.compareTo(an2));
	}

}
