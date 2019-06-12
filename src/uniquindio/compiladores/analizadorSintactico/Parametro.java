package uniquindio.compiladores.analizadorSintactico;

import uniquindio.compiladores.analizadorlexico.Token;

public class Parametro {

	private Token nombre, tipoDato;

	public Parametro(Token nombre, Token tipoDato) {
		super();
		this.nombre = nombre;
		this.tipoDato = tipoDato;
	}

	public void setNombre(Token nombre) {
		this.nombre = nombre;
	}

	public void setTipoDato(Token tipoDato) {
		this.tipoDato = tipoDato;
	}

	@Override
	public String toString() {
		return "Parametro [nombre=" + nombre + ", tipoDato=" + tipoDato + "]";
	}

	
}
