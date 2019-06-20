package uniquindio.compiladores.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author JHONNY - JORGE - CARLOS
 *
 */
public class Argumento {

	private ExpresionA expresion;
	
	public Argumento(ExpresionA expresion) {
		super();
		this.expresion = expresion;
	}

	public ExpresionA getExpresion() {
		return expresion;
	}

	@Override
	public String toString() {
		return "Argumento [expresion=" + expresion + "]";
	}


	/**
	 * Devuelve el arbol visual de la clase Argumento
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Argumento");
		
		nodo.add(expresion.getArbolVisual());

		return nodo;
	}
}
