package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import uniquindio.compiladores.analizadorSemantico.Simbolo;
import uniquindio.compiladores.analizadorSemantico.TablaSimbolos;
import uniquindio.compiladores.analizadorlexico.Token;

public class Declaracion extends Sentencia{
	private Token tipoDato, nombre;

	public Declaracion(Token tipoDato, Token nombre) {
		super();
		this.tipoDato = tipoDato;
		this.nombre = nombre;
	}

	public Token getTipoDato() {
		return tipoDato;
	}

	public Token getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return "Declaracion [tipoDato=" + tipoDato + ", nombre=" + nombre + "]";
	}
	
	/**
	 * Devuelve el arbol visual de la clase declaracion
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Declaración");
		
		nodo.add(new DefaultMutableTreeNode("Tipo Dato: " + tipoDato.getPalabra()));
		nodo.add(new DefaultMutableTreeNode("Nombre: " + nombre.getPalabra()));

		return nodo;
	}

	@Override
	public void crearTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {
		tablaSimbolos.agregarSimbolo(nombre.getPalabra(), tipoDato.getPalabra(), ambito);
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}
	
}
