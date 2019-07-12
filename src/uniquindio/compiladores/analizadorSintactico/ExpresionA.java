package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorSemantico.Simbolo;
import uniquindio.compiladores.analizadorSemantico.TablaSimbolos;
import uniquindio.compiladores.analizadorlexico.Token;

public class ExpresionA extends Sentencia{
	private Token nombre;
	private Expresion expresion;

	public ExpresionA(Token nombre) {
		super();
		this.nombre = nombre;
	}

	public ExpresionA(Expresion expresion) {
		super();
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
		return "ExpresionA [nombre=" + nombre + ", expresion=" + expresion + "]";
	}

	/**
	 * Devuelve el arbol visual de la clase Expresion A
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Expresión A");
		
		if (expresion!=null) {
			nodo.add(expresion.getArbolVisual());
		} else {
			nodo.add(new DefaultMutableTreeNode("Nombre: " + nombre.getPalabra()));
		}
		
		return nodo;
	}

	@Override
	public void crearTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

}
