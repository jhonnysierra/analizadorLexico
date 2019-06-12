package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import uniquindio.compiladores.analizadorlexico.Token;


public class Funcion {

	private Token nombreFuncion;
	private ArrayList<Parametro> listaParametros;
	private Token tipoRetorno;
	private ArrayList<Sentencia> listaSentencias;
	
	public Funcion(Token nombreFuncion, ArrayList<Parametro> listaParametros, Token tipoRetorno,
			ArrayList<Sentencia> listaSentencias) {
		super();
		this.nombreFuncion = nombreFuncion;
		this.listaParametros = listaParametros;
		this.tipoRetorno = tipoRetorno;
		this.listaSentencias = listaSentencias;
	}

	public Token getNombreFuncion() {
		return nombreFuncion;
	}

	public ArrayList<Parametro> getListaParametros() {
		return listaParametros;
	}
	
	public Token getTipoRetorno() {
		return tipoRetorno;
	}

	public ArrayList<Sentencia> getListaSentencias() {
		return listaSentencias;
	}

	@Override
	public String toString() {
		return "Funcion [nombreFuncion=" + nombreFuncion + ", listaParametros=" + listaParametros + ", tipoRetorno="
				+ tipoRetorno + ", listaSentencias=" + listaSentencias + "]";
	}
	
}
