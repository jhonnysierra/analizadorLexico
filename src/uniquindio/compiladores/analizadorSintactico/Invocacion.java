package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorSemantico.Simbolo;
import uniquindio.compiladores.analizadorSemantico.TablaSimbolos;
import uniquindio.compiladores.analizadorlexico.Token;

public class Invocacion extends Sentencia{

	private Token nombreFuncion;
	private ArrayList<Argumento> listaArgumentos;
	
	public Invocacion(Token nombreFuncion, ArrayList<Argumento> listaArgumentos) {
		super();
		this.nombreFuncion = nombreFuncion;
		this.listaArgumentos = listaArgumentos;
	}

	public Token getNombreFuncion() {
		return nombreFuncion;
	}

	public ArrayList<Argumento> getListaArgumentos() {
		return listaArgumentos;
	}

	@Override
	public String toString() {
		return "Invocacion [nombreFuncion=" + nombreFuncion + ", listaArgumentos=" + listaArgumentos + "]";
	}
	
	/**
	 * Devuelve el arbol visual para la clase Invocacion.
	 * 
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Invocación");

		nodo.add(new DefaultMutableTreeNode("Nombre función: " + nombreFuncion.getPalabra()));
		
		if (listaArgumentos != null) {
			DefaultMutableTreeNode argumentos = new DefaultMutableTreeNode("Argumentos");	
			
			for (Argumento argumento : listaArgumentos) {
				argumentos.add(argumento.getArbolVisual());
			}
			nodo.add(argumentos);
		}else {
			nodo.add(new DefaultMutableTreeNode("Argumentos: Sin argumentos "));
		}

		return nodo;
	}

	@Override
	public void crearTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {


	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {


	}
	
	
	
}
