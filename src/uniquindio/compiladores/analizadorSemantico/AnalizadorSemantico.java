package uniquindio.compiladores.analizadorSemantico;

import java.util.ArrayList;

import uniquindio.compiladores.analizadorSintactico.UnidadDeCompilacion;

/**
 * Clase que representa el analizador semantico del compilador 
 * @author JHONNY
 *
 */
public class AnalizadorSemantico {
	private ArrayList<String> errores;
	private UnidadDeCompilacion unidadCompilacion;
	private TablaSimbolos tablaSimbolos;
	
	public AnalizadorSemantico(UnidadDeCompilacion unidadCompilacion) {
		this.errores = new ArrayList<String>();
		this.unidadCompilacion = unidadCompilacion;
		this.tablaSimbolos = new TablaSimbolos(errores);
	}

	public void analizar() {
		unidadCompilacion.crearTablaSimbolos(tablaSimbolos, errores); 
		unidadCompilacion.analizarSemantica(tablaSimbolos, errores);
	}

	public ArrayList<String> getErrores() {
		return errores;
	}

	public UnidadDeCompilacion getUnidadCompilacion() {
		return unidadCompilacion;
	}

	public TablaSimbolos getTablaSimbolos() {
		return tablaSimbolos;
	}
	
}
