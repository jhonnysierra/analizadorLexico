package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorSemantico.TablaSimbolos;

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
	 * 
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Unidad de compilacion");

		for (Funcion f : listaFunciones) {
			raiz.add(f.getArbolVisual());
		}

		return raiz;
	}

	public void crearTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> errores) {
		for (Funcion f : listaFunciones) {
			f.crearTablaSimbolos(tablaSimbolos, errores);
		}

	}

	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> errores) {
		for (Funcion f : listaFunciones) {
			f.analizarSemantica(tablaSimbolos, errores);
		}

	}

}
