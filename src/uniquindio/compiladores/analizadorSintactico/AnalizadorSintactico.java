package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;



import uniquindio.compiladores.analizadorlexico.Categoria;
import uniquindio.compiladores.analizadorlexico.Token;

/**
 * Clase que representa el Analizador Sintáctico o Gramatical del compilador
 * @author caflorezvi
 *
 */
public class AnalizadorSintactico {

	private ArrayList<Token> tablaToken;
	private ArrayList<ErrorSintactico> tablaErrores;
	private Token tokenActual;
	private int posActual;
	private UnidadDeCompilacion unidadDeCompilacion;
	
	public AnalizadorSintactico( ArrayList<Token> tablaToken ) {
		this.tablaToken = tablaToken;
		this.posActual = 0;
		this.tokenActual = tablaToken.get(posActual);
		this.tablaErrores = new ArrayList<>();
	}
	
	public void analizar() {
		unidadDeCompilacion = esUnidadDeCompilacion();
	}
	
	/**
	 * <UnidadDeCompilacion> ::= <ListaFunciones>
	 */
	public UnidadDeCompilacion esUnidadDeCompilacion() {		
		ArrayList<Funcion> funciones = esListaFunciones();
		return new UnidadDeCompilacion(funciones);		
	}
	
	/**
	 * <ListaFunciones> ::= <Funcion>[<ListaFunciones>]
	 */
	public ArrayList<Funcion> esListaFunciones(){
		
		ArrayList<Funcion> funciones = new ArrayList<>();
		
		Funcion f = esFuncion();
		
		while(f!=null) {
			funciones.add(f);
			f=esFuncion();
		}		
		
		return funciones;		
	}
	
	/**
	 * <Funcion> ::= function <tipoRetorno> identificador "(" [<ListaParametros>] ")" "{" [<ListaSentencias>] "}"
	 */
	public Funcion esFuncion() {

		if( tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("Fun") ) {
			obtenerSgteToken();
			
			if(tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token nombreFuncion = tokenActual;
				obtenerSgteToken();
				
				if(tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
					obtenerSgteToken();
					
					ArrayList<Parametro> parametros = esListaParametros();
					
					if(tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {
						obtenerSgteToken();
						Token tipoRetorno = null;						
						
						if( tokenActual.getCategoria() == Categoria.DOS_PUNTOS ) {
							obtenerSgteToken();
							
							tipoRetorno = esTipoRetorno();
							
							if(tipoRetorno!=null) {
								obtenerSgteToken();
							}else {
								reportarError("Falta el tipo de retorno");
							}
							
						}
						
						if( tokenActual.getCategoria() == Categoria.LLAVE_IZQ ) {							
							obtenerSgteToken();
						
							ArrayList<Sentencia> sentencias = esListaSentencias();
							
							if( tokenActual.getCategoria() == Categoria.LLAVE_DER ) {
								obtenerSgteToken();
								return new Funcion(nombreFuncion, parametros, tipoRetorno, sentencias);
							}else {
								reportarError("Falta la llave derecha");
							}
							
						}else {
							reportarError("Falta la llave izquierda");
						}
						
					}else {
						reportarError("Falta el paréntesis derecho");
					}
										
				}else {
					reportarError("Falta el paréntesis izquierdo");
				}
			}else {
				reportarError("Falta el nombre de la función");
			}
		}
		
		return null;		
	}
	
	/**
	 * <ListaSentencias> ::= <Sentencia>[<ListaSentencias>]
	 */
	public ArrayList<Sentencia> esListaSentencias(){

		ArrayList<Sentencia> sentencias = new ArrayList<>();
		
		Sentencia s = esSentencia();
		
		while(s!=null) {
			sentencias.add(s);
			s=esSentencia();			
		}		
		
		return sentencias;				
	}
	
	/**
	 * <Sentencia> ::= <Ciclo>
	 */
	public Sentencia esSentencia() {
		
		Sentencia s = esCiclo();
		
		if(s!=null) {
			return s;
		}
		
		return null;
	}
	
	/**
	 * <Ciclo> ::= Hacer BNF
	 */
	public Ciclo esCiclo() {
		/*
		 * TODO Completar
		 */
		return null;
	}
	
	/**
	 * <ListaParametros> ::= <Parametro>[","<ListaParametros>]
	 */
	public ArrayList<Parametro> esListaParametros(){

		ArrayList<Parametro> parametros = new ArrayList<>();
		
		Parametro p = esParametro();
		
		while(p!=null) {
			parametros.add(p);
			
			if(tokenActual.getCategoria() == Categoria.COMA) {
				obtenerSgteToken();
				p=esParametro();
			}else {
				p = null;
			}
						
		}		
		
		return parametros;		
	}
	
	/**
	 * <Parametro> ::= identificador":"<TipoDeDato>
	 */
	public Parametro esParametro() {
		if( tokenActual.getCategoria() == Categoria.IDENTIFICADOR ) {
			Token nombre = tokenActual;
			obtenerSgteToken();
			
			if( tokenActual.getCategoria() == Categoria.DOS_PUNTOS ) {
				obtenerSgteToken();
				
				Token tipoDato = esTipoDato();
				
				if(tipoDato!=null) {
					obtenerSgteToken();
					return new Parametro(nombre, tipoDato);
				}else {
					reportarError("Falta el tipo de dato del parámetro");
				}
				
			}else {
				reportarError("Falta dos puntos");
			}
			
		}
		
		return null;
	}
	
	public void reportarError(String mensaje) {
		tablaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
	}
	
	/**
	 * <TipoRetorno> ::= Int | Decimal | String | Logical | Void
	 */
	public Token esTipoRetorno() {
		
		if(tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA) {
			
			if( tokenActual.getPalabra().equals("Int") || tokenActual.getPalabra().equals("Decimal") || tokenActual.getPalabra().equals("String") || tokenActual.getPalabra().equals("Logical") || tokenActual.getPalabra().equals("Void") ) {
				return tokenActual;
			}
			
		}
		return null;
	}
	
	/**
	 * <TipoDato> ::= Int | Decimal | String | Logical
	 */
	public Token esTipoDato() {
		
		if(tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA) {
			
			if( tokenActual.getPalabra().equals("Int") || tokenActual.getPalabra().equals("Decimal") || tokenActual.getPalabra().equals("String") || tokenActual.getPalabra().equals("Logical")  ) {
				return tokenActual;
			}
			
		}
		return null;
	}
	
	public void obtenerSgteToken() {
		posActual++;
		
		if(posActual<tablaToken.size()) {
			tokenActual = tablaToken.get(posActual);	
		}
		
	}

	public UnidadDeCompilacion getUnidadDeCompilacion() {
		return unidadDeCompilacion;
	}

	public ArrayList<ErrorSintactico> getTablaErrores() {
		return tablaErrores;
	}

}