package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorSemantico.Simbolo;
import uniquindio.compiladores.analizadorSemantico.TablaSimbolos;
import uniquindio.compiladores.analizadorlexico.Token;

/**
 * Clase que representa la categoria incremento
 * 
 * @author JHONNY - JORGE - CARLOS
 *
 */
public class Incremento extends Sentencia{

	private Token nombre, operador;
		
	public Incremento(Token nombre, Token operador) {
		super();
		this.nombre = nombre;
		this.operador = operador;
	}

	public Token getNombre() {
		return nombre;
	}

	public Token getOperador() {
		return operador;
	}


	@Override
	public String toString() {
		return "Incremento [nombre=" + nombre + ", operador=" + operador + "]";
	}

	/**
	 * Devuelve el arbol visual de la clase incremento
	 * 
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Incremento");

		nodo.add(new DefaultMutableTreeNode("Identificador: " + nombre.getPalabra()));
		nodo.add(new DefaultMutableTreeNode("Operador: " + operador.getPalabra()));

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
