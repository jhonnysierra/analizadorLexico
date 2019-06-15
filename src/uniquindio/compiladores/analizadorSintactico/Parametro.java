package uniquindio.compiladores.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

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
	
	/**
	 * Devuelve el arbol visual de la clase parametro
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Parámetro");
		
		nodo.add(new DefaultMutableTreeNode("Tipo Dato: " + tipoDato.getPalabra()));
		nodo.add(new DefaultMutableTreeNode("Nombre: " + nombre.getPalabra()));

		return nodo;
	}
}
