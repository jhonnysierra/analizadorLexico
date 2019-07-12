package uniquindio.compiladores.analizadorSemantico;

import java.util.ArrayList;

/**
 * Clase que representa un simbolo dentro del codigo fuente (declaracion de funcion, variable o parametro)
 * 
 * @author JHONNY
 *
 */
public class Simbolo {
	private String nombre;
	private String tipo;
	private Simbolo ambito;
	private ArrayList<String> tiposParams;
	
	public Simbolo(String nombre, String tipo, Simbolo ambito) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.ambito = ambito;
		
	}

	public Simbolo(String nombre, String tipo, Simbolo ambito, ArrayList<String> tiposParams) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.ambito = ambito;
		this.tiposParams = tiposParams;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ambito == null) ? 0 : ambito.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((tiposParams == null) ? 0 : tiposParams.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Simbolo other = (Simbolo) obj;
		if (ambito == null) {
			if (other.ambito != null)
				return false;
		} else if (!ambito.equals(other.ambito))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (tiposParams == null) {
			if (other.tiposParams != null)
				return false;
		} else if (!tiposParams.equals(other.tiposParams))
			return false;
		return true;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public Simbolo getAmbito() {
		return ambito;
	}

	public ArrayList<String> getTiposParams() {
		return tiposParams;
	}
	
}
