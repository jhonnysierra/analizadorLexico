package uniquindio.compiladores.analizadorSintactico;

import uniquindio.compiladores.analizadorlexico.Token;

public class Parametro {

	private Token tipoDato , nombre ;

	public Parametro(Token tipoDato, Token nombre) {
		super();
		this.tipoDato = tipoDato;
		this.nombre = nombre;
		
	}

	public Token getTipoDato() {
		return tipoDato;
	}

	public Token getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return "Parametro [tipoDato=" + tipoDato + ", nombre=" + nombre + "]";
	}
	
}
