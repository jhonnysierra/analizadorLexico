package uniquindio.compiladores.analizadorSemantico;

import java.util.ArrayList;

public class TablaSimbolos {
	private ArrayList<Simbolo> tablaSimbolos;
	private ArrayList<String> errores;

	public TablaSimbolos(ArrayList<String> errores) {
		this.tablaSimbolos = new ArrayList<Simbolo>();
		this.errores = errores;
	}

	public Simbolo agregarSimbolo(String nombre, String tipo, Simbolo ambito) {
		Simbolo s = new Simbolo(nombre, tipo, ambito);

		if (!tablaSimbolos.contains(s)) {
			tablaSimbolos.add(s);
		} else {
			errores.add("La variable " + nombre + " dentro del ámbito " + ambito.getNombre() + " ya existe.");
		}

		return s;
	}

	public Simbolo agregarSimbolo(String nombre, String tipo, Simbolo ambito, ArrayList<String> params) {
		Simbolo s = new Simbolo(nombre, tipo, ambito, params);

		if (!tablaSimbolos.contains(s)) {
			tablaSimbolos.add(s);
		} else {
			errores.add("La función " + nombre +  " ya existe.");
		}

		return s;
	}
	
	public Simbolo buscarSimbolo(String nombre, Simbolo ambito) {
		for (Simbolo simbolo: tablaSimbolos) {
			if (simbolo.getNombre().equals(nombre) && simbolo.getAmbito().equals(ambito)) {
				return simbolo;
			}
		}
		
		return null;
	}
	
	public Simbolo buscarSimbolo(String nombre, ArrayList<String> params) {
		for (Simbolo simbolo: tablaSimbolos) {
			if (simbolo.getNombre().equals(nombre) && simbolo.getTiposParams().equals(params)) {
				return simbolo;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "TablaSimbolos [tablaSimbolos=" + tablaSimbolos + ", errores=" + errores + "]";
	}

	public ArrayList<Simbolo> getTablaSimbolos() {
		return tablaSimbolos;
	}

	public ArrayList<String> getErrores() {
		return errores;
	}

	
}
