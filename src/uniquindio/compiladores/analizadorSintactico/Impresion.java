package uniquindio.compiladores.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

public class Impresion extends Sentencia{
	
	private ExpresionCadena expresion;

	public Impresion(ExpresionCadena expresion) {
		super();
		this.expresion = expresion;
	}

	public ExpresionCadena getExpresion() {
		return expresion;
	}

	@Override
	public String toString() {
		return "Impresion [expresion=" + expresion + "]";
	}
	
	/**
	 * Devuelve el arbol visual de la clase impresion
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Imprimir");
		
		nodo.add(expresion.getArbolVisual());

		return nodo;
	}

}
