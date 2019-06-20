package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

public class Ciclo extends Sentencia {

	private Expresion expresionR;
	private ArrayList<Sentencia> listaSentencias;
		
	public Ciclo(Expresion expresionR, ArrayList<Sentencia> listaSentencias) {
		super();
		this.expresionR = expresionR;
		this.listaSentencias = listaSentencias;
	}
	
	public Expresion getExpresionR() {
		return expresionR;
	}
	
	public ArrayList<Sentencia> getListaSentencias() {
		return listaSentencias;
	}

	@Override
	public String toString() {
		return "Ciclo [expresionR=" + expresionR + ", listaSentencias=" + listaSentencias + "]";
	}
	
	/**
	 * Devuelve el arbol visual para la clase ciclo.
	 * 
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Ciclo");

		DefaultMutableTreeNode condicion = new DefaultMutableTreeNode("Condición");
		
		condicion.add(expresionR.getArbolVisual());
		nodo.add(condicion);
		
		
		if (listaSentencias != null) {
			DefaultMutableTreeNode sentencias = new DefaultMutableTreeNode("Sentencias");	
			
			for (Sentencia sentencia : listaSentencias) {
				sentencias.add(sentencia.getArbolVisual());
			}
			nodo.add(sentencias);
		}else {
			nodo.add(new DefaultMutableTreeNode("Sentencias: Sin sentencias"));
		}
		

		return nodo;
	}
}
