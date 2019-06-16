package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
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

	/**
	 * Devuelve el arbol visual para la clase funcion.
	 * 
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Función");

		nodo.add(new DefaultMutableTreeNode("Tipo Retorno: " + tipoRetorno.getPalabra()));
		nodo.add(new DefaultMutableTreeNode("Nombre: " + nombreFuncion.getPalabra()));
		
		if (listaParametros != null) {
			DefaultMutableTreeNode parametros = new DefaultMutableTreeNode("Parámetros");	
			
			for (Parametro parametro : listaParametros) {
				parametros.add(parametro.getArbolVisual());
			}
			nodo.add(parametros);
		}else {
			nodo.add(new DefaultMutableTreeNode("Parametros: Sin parámetros "));
		}
		
		if (listaSentencias != null) {
			DefaultMutableTreeNode sentencias = new DefaultMutableTreeNode("Sentencias");	
			
			for (Sentencia sentencia : listaSentencias) {
				sentencias.add(sentencia.getArbolVisual());
			}
			nodo.add(sentencias);
		}else {
			nodo.add(new DefaultMutableTreeNode("Sentencias: Sin sentencias"));
		}
		

		return nodo;
	}

}
