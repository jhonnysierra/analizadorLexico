package uniquindio.compiladores.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorlexico.Token;

public class ExpresionAsignacion extends Sentencia{
	private Token nombre,expresionA;
	private Expresion expresion;
	
	
	public ExpresionAsignacion(Token nombre, Token expresionA) {
		super();
		this.nombre = nombre;
		this.expresionA = expresionA;
	}

	public ExpresionAsignacion(Token nombre, Expresion expresion) {
		super();
		this.nombre = nombre;
		this.expresion = expresion;
	}
	
	public Token getNombre() {
		return nombre;
	}
	public Expresion getExpresion() {
		return expresion;
	}

	
	@Override
	public String toString() {
		return "ExpresionAsignacion [nombre=" + nombre + ", expresionA=" + expresionA + ", expresion=" + expresion
				+ "]";
	}

	/**
	 * Devuelve el arbol visual de la clase Expresion Asignacion
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Expresión Asignación");
		
		if (expresion!=null) {
			nodo.add(new DefaultMutableTreeNode("Nombre: " + nombre.getPalabra()));
			nodo.add(expresion.getArbolVisual());
		} else {
			nodo.add(new DefaultMutableTreeNode("Nombre: " + nombre.getPalabra()));
			nodo.add(new DefaultMutableTreeNode("Expresión: " + expresionA.getPalabra()));
		}


		return nodo;
	}

}
