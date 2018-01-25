package mgr.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;


public class Annotation implements Comparable<Annotation> {
	public static final int SUB = 0;
	public static final int NOT = 1;
	public static final int MAR = 2;


	private String titulo;
	private String autor;
	private Date fecha;
	private Integer tipo; //Enumeration
	private String mensaje = "";

	private Integer paginaIni;
	private Integer paginaFin;

	private Integer posIni;
	private Integer posFin;


	public Annotation() {}

	public Annotation(String nota) {
		int sepIni = 0;

		String[] notas = nota.split("\n");
		nota = notas[0];

		try {
			sepIni = nota.indexOf("-");
			if (sepIni > 0) {
				titulo = nota.substring(1, sepIni - 1);
				autor = nota.substring(sepIni + 2, nota.length());
			} else {
				sepIni = nota.indexOf("(");
				if (sepIni > 0) {
					titulo = nota.substring(1, sepIni - 1);
					autor = nota.substring(sepIni + 1, nota.length() - 1);
				} else {
					titulo = nota.substring(1, nota.length());
					autor = "";
				}

			}
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println("nota: " + nota);
			e.printStackTrace();
		}
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getAutor() {
		return autor;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setPaginaIni(Integer paginaIni) {
		this.paginaIni = paginaIni;
	}

	public Integer getPaginaIni() {
		return paginaIni;
	}

	public void setPaginaFin(Integer paginaFin) {
		this.paginaFin = paginaFin;
	}

	public Integer getPaginaFin() {
		return paginaFin;
	}

	public void setPosIni(Integer posIni) {
		this.posIni = posIni;
	}

	public Integer getPosIni() {
		return posIni;
	}

	public void setPosFin(Integer posFin) {
		this.posFin = posFin;
	}

	public Integer getPosFin() {
		return posFin;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("titulo: " + titulo).append(", ");
		sb.append("autor: " + autor).append(", ");
		sb.append("fecha: " + fecha.toString()).append(", ");
		sb.append("tipo: " + tipo).append(", ");
		sb.append("paginaIni: " + paginaIni).append(", ");
		sb.append("posIni: " + posIni).append(", ");
		sb.append("posFin: " + posFin).append(", ");
		sb.append("mensaje: " + mensaje);

		return sb.toString();
	}

	public String toCSV() {
		StringBuilder sb = new StringBuilder();

		sb.append(titulo).append("\t");
		sb.append(autor).append("\t");
		sb.append(codeToString(tipo)).append("\t");

		sb.append(blankIfNull(paginaIni)).append("\t");
		sb.append(blankIfNull(paginaFin)).append("\t");

		sb.append(blankIfNull(posIni)).append("\t");
		sb.append(blankIfNull(posFin)).append("\t");


		sb.append(dateToString(fecha, "dd/MM/yyyy hh:mm:ss")).append("\t");
		sb.append(mensaje);

		return sb.toString();
	}
	
	public static String getCabecera() {
		StringBuilder sb = new StringBuilder();
		sb.append("titulo").append("\t");
		sb.append("autor").append("\t");
		sb.append("tipo").append("\t");
		sb.append("paginaIni").append("\t");
		sb.append("paginaFin").append("\t");
		sb.append("posIni").append("\t");
		sb.append("posFin").append("\t");
		sb.append("fecha").append("\t");
		sb.append("mensaje").append("\r\n");
		return sb.toString();
	}

	private static String dateToString(Date fecha, String formato) {
		String resultado = "";
		if (fecha != null) {
			DateFormat df = new SimpleDateFormat(formato);
			resultado = df.format(fecha);
		}

		return resultado;
	}

	private static String codeToString(int codigo) {
		String resultado = "";
		switch (codigo) {
		case SUB:
			resultado = "Subrayado";
			break;

		case NOT:
			resultado = "Nota";
			break;

		case MAR:
			resultado = "Marcador";
			break;
		}


		return resultado;
	}

	public static String blankIfNull(Integer data) {
		String resultado = "";
		if (data != null) {
			resultado = String.valueOf(data);
		}
		return resultado;
	}
	


	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof Annotation)) {
			return false;
		}
		final Annotation other = (Annotation)object;
		if (!(titulo == null ? other.titulo == null : titulo.equals(other.titulo))) {
			return false;
		}
		if (!(autor == null ? other.autor == null : autor.equals(other.autor))) {
			return false;
		}
		if (!(tipo == null ? other.tipo == null : tipo.equals(other.tipo))) {
			return false;
		}
		if (!(mensaje == null ? other.mensaje == null : mensaje.equals(other.mensaje))) {
			return false;
		}
		
		if (!(paginaIni == null ? other.paginaIni == null : paginaIni.equals(other.paginaIni))) {
			return false;
		}
		if (!(paginaFin == null ? other.paginaFin == null : paginaFin.equals(other.paginaFin))) {
			return false;
		}
		if (!(posIni == null ? other.posIni == null : posIni.equals(other.posIni))) {
			return false;
		}
		if (!(posFin == null ? other.posFin == null : posFin.equals(other.posFin))) {
			return false;
		}
		return true;
	}

	public boolean equalsPosittionIni(Annotation annotation) {

		final Annotation other = annotation;
		if (!(titulo == null ? other.titulo == null : titulo.equals(other.titulo))) {
			return false;
		}
		if (!(autor == null ? other.autor == null : autor.equals(other.autor))) {
			return false;
		}
		if (!(tipo == null ? other.tipo == null : tipo.equals(other.tipo))) {
			return false;
		}
		if (!(mensaje == null ? other.mensaje == null : mensaje.equals(other.mensaje))) {
			return false;
		}
		if (!(paginaIni == null ? other.paginaIni == null : paginaIni.equals(other.paginaIni))) {
			return false;
		}
		if (!(posIni == null ? other.posIni == null : posIni.equals(other.posIni))) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		final int PRIME = 37;
		int result = 1;
		result = PRIME * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = PRIME * result + ((autor == null) ? 0 : autor.hashCode());
		result = PRIME * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = PRIME * result + ((mensaje == null) ? 0 : mensaje.hashCode());
		result = PRIME * result + ((paginaIni == null) ? 0 : paginaIni.hashCode());
		result = PRIME * result + ((paginaFin == null) ? 0 : paginaFin.hashCode());
		result = PRIME * result + ((posIni == null) ? 0 : posIni.hashCode());
		result = PRIME * result + ((posFin == null) ? 0 : posFin.hashCode());
		return result;
	}

	public int compareTo(Annotation annotation) {
		
		int resultado = autor.compareToIgnoreCase(annotation.getAutor());
		
		if (resultado==0) {
			resultado = titulo.compareToIgnoreCase(annotation.getTitulo());
		}

		if (resultado == 0) {
			if (paginaIni != null) {
				resultado = paginaIni.compareTo(annotation.getPaginaIni());
			}
		}

		if (resultado == 0) {
			resultado = posIni.compareTo(annotation.getPosIni());
		}		


		//System.out.println("resultado: " + resultado);
		return resultado;
		
	}

	public static void main(String[] arg) {
		Annotation an1 = new Annotation();
		an1.setAutor("Pepe");
		an1.setTitulo("Cosa");
		an1.setPaginaIni(10);

		Annotation an2 = new Annotation();
		an2.setAutor("Pepe");
		an2.setTitulo("cosa");
		an2.setPaginaIni(12);
		
		int compare = an1.compareTo(an2);
		
		System.out.println("compare: " + compare);
	}
}
