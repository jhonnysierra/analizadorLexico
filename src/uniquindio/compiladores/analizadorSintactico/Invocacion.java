package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorSemantico.Simbolo;
import uniquindio.compiladores.analizadorSemantico.TablaSimbolos;
import uniquindio.compiladores.analizadorlexico.Token;

public class Invocacion extends Sentencia{

	private Token nombreFuncion;
	private ArrayList<Argumento> listaArgumentos;
	private Simbolo ambito;
	
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
	
	public Simbolo getAmbito() {
		return ambito;
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

	public ArrayList<String> getTiposArgs() {
		ArrayList<String> l = new ArrayList<String>();

		if (listaArgumentos != null) {
			for (Argumento a : listaArgumentos) {
				l.add(a.getExpresion().getNombre().getPalabra());
			}
		}
		return l;

	}
	
	@Override
	public void crearTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {


	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {
/*		Simbolo s = tablaSimbolos.buscarSimbolo(nombreFuncion.getPalabra(), getTiposArgs());
		
		if (s==null) {
			errores.add("La función " + nombreFuncion + " no existe");
		}
*/
	}
	
	
	
}
