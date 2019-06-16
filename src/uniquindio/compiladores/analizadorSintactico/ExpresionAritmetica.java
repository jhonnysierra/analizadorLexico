package uniquindio.compiladores.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorlexico.Token;

/**
 * Clase que representa los datos que se desean mostrar de una expresion aritmetica
 * 
 * @author JHONNY - JORGE - CARLOS
 *
 */
public class ExpresionAritmetica extends Expresion{

	private Token termino1, operador, termino2;


	
	public ExpresionAritmetica(Token termino1, Token operador, Token termino2) {
		super();
		this.termino1 = termino1;
		this.operador = operador;
		this.termino2 = termino2;
	}



	public Token getTermino1() {
		return termino1;
	}

	public Token getOperador() {
		return operador;
	}

	public Token getTermino2() {
		return termino2;
	}



	@Override
	public String toString() {
		return "ExpresionAritmetica [termino1=" + termino1 + ", operador=" + operador + ", termino2=" + termino2 + "]";
	}

	/**
	 * Devuelve el arbol visual de la clase expresion Aritmetica
	 * 
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Operación Aritmética");

		nodo.add(new DefaultMutableTreeNode("Termino 1: " + termino1.getPalabra()));
		nodo.add(new DefaultMutableTreeNode("Operación: " + operador.getPalabra()));
		nodo.add(new DefaultMutableTreeNode("Termino 2: " + termino2.getPalabra()));
		
		return nodo;
	}

}
