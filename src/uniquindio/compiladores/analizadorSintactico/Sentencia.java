package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorSemantico.Simbolo;
import uniquindio.compiladores.analizadorSemantico.TablaSimbolos;

/**
 * Clase que representa una Sentencia del lenguaje
 *  
 * @author JHONNY - JORGE - CARLOS
 *
 */
public abstract class Sentencia {

	public Sentencia() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DefaultMutableTreeNode getArbolVisual() {
		return null;
	}

	public void crearTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}



}
