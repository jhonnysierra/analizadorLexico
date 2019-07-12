package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorSemantico.Simbolo;
import uniquindio.compiladores.analizadorSemantico.TablaSimbolos;

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
	
	public abstract void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito);
	
	public abstract String obtenerTipo();
}
