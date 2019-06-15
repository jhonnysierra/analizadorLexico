package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

public class UnidadDeCompilacion {

	private ArrayList<Funcion> listaFunciones;

	public UnidadDeCompilacion(ArrayList<Funcion> listaFunciones) {
		super();
		this.listaFunciones = listaFunciones;
	}

	public ArrayList<Funcion> getListaFunciones() {
		return listaFunciones;
	}

	@Override
	public String toString() {
		return "UnidadDeCompilacion [listaFunciones=" + listaFunciones + "]";
	}

	/**
	 * Devuelve el arbor visual de la unidad de compilacion
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Unidad de compilacion");
		
		for (Funcion f: listaFunciones) {
			raiz.add(f.getArbolVisual());
		}
		
		return raiz;
	}
	
}
