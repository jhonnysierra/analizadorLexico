package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorSemantico.Simbolo;
import uniquindio.compiladores.analizadorSemantico.TablaSimbolos;
import uniquindio.compiladores.analizadorlexico.Token;

public class AsignarFuncion extends Sentencia{
	
	private Token nombre;
	private Invocacion invocacionF;
	
	public AsignarFuncion(Token nombre, Invocacion invocacionF) {
		super();
		this.nombre = nombre;
		this.invocacionF = invocacionF;
	}

	public Token getNombre() {
		return nombre;
	}

	public Invocacion getInvocacionF() {
		return invocacionF;
	}

	@Override
	public String toString() {
		return "AsignarFuncion [nombre=" + nombre + ", invocacionF=" + invocacionF + "]";
	}
	
	/**
	 * Devuelve el arbol visual de la clase Asignar funcion
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Expresión A");
		
		nodo.add(new DefaultMutableTreeNode("Nombre: " + nombre.getPalabra()));
		nodo.add(invocacionF.getArbolVisual());
		
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
