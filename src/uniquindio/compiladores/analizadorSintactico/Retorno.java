package uniquindio.compiladores.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

public class Retorno extends Sentencia {

	private Expresion expresion;

	public Retorno(Expresion expresion) {
		super();
		this.expresion = expresion;
	}

	public Expresion getExpresion() {
		return expresion;
	}

	@Override
	public String toString() {
		return "Retorno [expresion=" + expresion + "]";
	}

	/**
	 * Devuelve el arbol visual de la clase retorno
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Retorno");
		
		nodo.add(expresion.getArbolVisual());

		return nodo;
	}

}
