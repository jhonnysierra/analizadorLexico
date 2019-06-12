package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

public class UnidadDeCompilacion {

	private ArrayList<Funcion> listaFunciones;

	public UnidadDeCompilacion(ArrayList<Funcion> listaFunciones) {
		super();
		this.listaFunciones = listaFunciones;
	}

	public ArrayList<Funcion> getListaFunciones() {
		return listaFunciones;
	}

	@Override
	public String toString() {
		return "UnidadDeCompilacion [listaFunciones=" + listaFunciones + "]";
	}

	
}
