package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorSemantico.Simbolo;
import uniquindio.compiladores.analizadorSemantico.TablaSimbolos;

public class Decision extends Sentencia {

	private Expresion expresionR;
	private ArrayList<Sentencia> listaSentenciasSI;
	private ArrayList<Sentencia> listaSentenciasNO;
		

	
	public Decision(Expresion expresionR, ArrayList<Sentencia> listaSentenciasSI,
			ArrayList<Sentencia> listaSentenciasNO) {
		super();
		this.expresionR = expresionR;
		this.listaSentenciasSI = listaSentenciasSI;
		this.listaSentenciasNO = listaSentenciasNO;
	}


	public Expresion getExpresionR() {
		return expresionR;
	}

	public ArrayList<Sentencia> getListaSentenciasSI() {
		return listaSentenciasSI;
	}

	public ArrayList<Sentencia> getListaSentenciasNO() {
		return listaSentenciasNO;
	}


	@Override
	public String toString() {
		return "Decision [expresionR=" + expresionR + ", listaSentenciasSI=" + listaSentenciasSI
				+ ", listaSentenciasNO=" + listaSentenciasNO + "]";
	}


	/**
	 * Devuelve el arbol visual para la clase ciclo.
	 * 
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Decisión");

		DefaultMutableTreeNode condicion = new DefaultMutableTreeNode("Condición");
		
		condicion.add(expresionR.getArbolVisual());
		nodo.add(condicion);
		
		
		if (listaSentenciasSI != null) {
			DefaultMutableTreeNode sentencias = new DefaultMutableTreeNode("Sentencias SI");	
			
			for (Sentencia sentencia : listaSentenciasSI) {
				sentencias.add(sentencia.getArbolVisual());
			}
			nodo.add(sentencias);
		}else {
			nodo.add(new DefaultMutableTreeNode("Sentencias SI: Sin sentencias"));
		}
		
		if (listaSentenciasNO!= null) {
			DefaultMutableTreeNode sentencias = new DefaultMutableTreeNode("Sentencias NO");	
			
			for (Sentencia sentencia : listaSentenciasNO) {
				sentencias.add(sentencia.getArbolVisual());
			}
			nodo.add(sentencias);
		}else {
			nodo.add(new DefaultMutableTreeNode("Sentencias NO: Sin sentencias"));
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
