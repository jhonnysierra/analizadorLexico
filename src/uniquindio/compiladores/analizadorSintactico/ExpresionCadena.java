package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorlexico.Categoria;
import uniquindio.compiladores.analizadorlexico.Token;

public class ExpresionCadena extends Expresion {

	private ArrayList<Token> cadena;
	
	

	public ExpresionCadena(ArrayList<Token> cadena) {
		super();
		this.cadena = cadena;
	}


	public ArrayList<Token> getCadena() {
		return cadena;
	}


	@Override
	public String toString() {
		return "ExpresionCadena [cadena=" + cadena + "]";
	}


	/**
	 * Devuelve el arbol visual de la clase expresion cadena
	 * 
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Expresión Cadena");

		if (cadena != null) {	
			
			for (Token e : cadena) {
				if (e.getCategoria()==Categoria.IDENTIFICADOR) {
					nodo.add(new DefaultMutableTreeNode("Identificador: " + e.getPalabra()));	
				} else {
					nodo.add(new DefaultMutableTreeNode("Cadena: " + e.getCategoria()));
				}
				
			}
		}else {
			nodo.add(new DefaultMutableTreeNode("Cadena: null"));
		}

		return nodo;
	}
}
