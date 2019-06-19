package uniquindio.compiladores.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorlexico.Token;

public class Lectura extends Sentencia {

	private Token nombre;

	public Lectura(Token nombre) {
		super();
		this.nombre = nombre;
	}

	public Token getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return "Lectura [nombre=" + nombre + "]";
	}

	/**
	 * Devuelve el arbol visual de la clase lectura
	 * 
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Lectura");

		nodo.add(new DefaultMutableTreeNode("Identificador: " + nombre.getPalabra()));

		return nodo;
	}

}
