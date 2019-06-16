package uniquindio.compiladores.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Clase que representa una expresion del lenguaje
 *  
 * <Expresion>::=<ExpresionAritmetica> | <ExpresionRelacional> | <ExpresionCadena>
 * 
 * @author JHONNY_JORGE_CARLOS
 *
 */
public abstract class Expresion {

	public Expresion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DefaultMutableTreeNode getArbolVisual() {
		return null;
	}
}
