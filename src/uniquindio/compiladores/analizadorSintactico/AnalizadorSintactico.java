package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import uniquindio.compiladores.analizadorlexico.Categoria;
import uniquindio.compiladores.analizadorlexico.Token;

/**
 * Clase que representa el Analizador SintÃ¡ctico o Gramatical del compilador
 * 
 * @author caflorezvi
 *
 */
public class AnalizadorSintactico {

	private ArrayList<Token> tablaToken;
	private ArrayList<ErrorSintactico> tablaErrores;
	private Token tokenActual;
	private int posActual;
	private UnidadDeCompilacion unidadDeCompilacion;

	public AnalizadorSintactico(ArrayList<Token> tablaToken) {
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
	public ArrayList<Funcion> esListaFunciones() {

		ArrayList<Funcion> funciones = new ArrayList<>();

		Funcion f = esFuncion();

		while (f != null) {
			funciones.add(f);
			f = esFuncion();
		}

		return funciones;
	}

	/**
	 * <Funcion> ::= function <tipoRetorno> identificador "«" [<ListaParametros>]
	 * "»" "{" [<ListaSentencias>] "}"
	 */
	public Funcion esFuncion() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("¿function")) {
			obtenerSgteToken();

			Token tipoRetorno = null;
			tipoRetorno = esTipoRetorno();

			if (tipoRetorno != null) {
				obtenerSgteToken();
				if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
					Token nombreFuncion = tokenActual;
					obtenerSgteToken();
					if (tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
						obtenerSgteToken();

						ArrayList<Parametro> parametros = null;

						if (tokenActual.getCategoria() != Categoria.PARENTESIS_DER) {
							parametros = esListaParametros();
						}

						if (tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {
							obtenerSgteToken();
							if (tokenActual.getCategoria() == Categoria.LLAVE_IZQ) {
								obtenerSgteToken();

								ArrayList<Sentencia> sentencias = esListaSentencias();

								if (tokenActual.getCategoria() == Categoria.LLAVE_DER) {
									obtenerSgteToken();
									return new Funcion(nombreFuncion, parametros, tipoRetorno, sentencias);
								} else {
									reportarError("Falta llave derecha");
								}

							} else {
								reportarError("Falta llave izquierda");
							}
						} else {
							reportarError("Falta paréntesis derecho");
						}
					} else {
						reportarError("Falta paréntesis izquierdo");
					}
				} else {
					reportarError("Falta el identificador");
				}
			} else {
				reportarError("Falta el tipo de retorno");
			}

		}

		return null;
	}

	/**
	 * <ListaSentencias> ::= <Sentencia>[<ListaSentencias>]
	 */
	public ArrayList<Sentencia> esListaSentencias() {

		ArrayList<Sentencia> sentencias = new ArrayList<>();

		Sentencia s = esSentencia();

		while (s != null) {
			sentencias.add(s);
			s = esSentencia();
		}

		return sentencias;
	}

	/**
	 * <Sentencia> ::= <Decision> | <Declaracion> | <Invocacion> | <Impresion> |
	 * <Ciclo> | <Retorno> | <Lectura>| <ExpresionAsignacion>|
	 */
	public Sentencia esSentencia() {

		//Sentencia s = esCiclo();

		Sentencia declaracion = esDeclaracion();
		if (declaracion != null) {
			return declaracion;
		}
		

		return null;
	}

	/**
	 * <Ciclo> ::= ¿ciclo "«"<ExpresionRelacional>"»" do "["[<listaSentencias>]"]"
	 */
	public Ciclo esCiclo() {

		return null;
	}

	public Declaracion esDeclaracion() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("¿var")) {
			obtenerSgteToken();

			Token tipoDato = null;
			tipoDato = esTipoDato();

			if (tipoDato != null) {
				obtenerSgteToken();

				if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
					Token nombre = tokenActual;
					obtenerSgteToken();					
					if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
						obtenerSgteToken();
						return new Declaracion(tipoDato,nombre);
					} else {
						reportarError("Falta el fin de sentencia");
					}
				} else {
					reportarError("Falta el identificador");
				}

			} else {
				reportarError("Falta el tipo de dato");
			}
		}

		return null;
	}

	/**
	 * <ListaParametros> ::= <Parametro>[","<ListaParametros>]
	 */
	public ArrayList<Parametro> esListaParametros() {

		ArrayList<Parametro> parametros = new ArrayList<>();

		Parametro p = esParametro();

		while (p != null) {
			parametros.add(p);

			if (tokenActual.getCategoria() == Categoria.COMA) {
				obtenerSgteToken();
				p = esParametro();
			} else {
				p = null;
			}

		}

		return parametros;
	}

	/**
	 * <Parametro> ::= <TipoDeDato> identificador
	 */
	public Parametro esParametro() {
		Token tipoDato = esTipoDato();

		if (tipoDato != null) {
			obtenerSgteToken();
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token nombre = tokenActual;
				obtenerSgteToken();
				return new Parametro(tipoDato, nombre);

			} else {
				reportarError("Falta identificador en lista de parametros");
			}
		} else {
			reportarError("Falta tipo de dato en lista de parametros");
		}
		return null;
	}

	/**
	 * Metodo que analiza si es parametro cuando en la lista hay mas de un parametro
	 * 
	 * @return
	 */
	public Parametro esParametroVarios() {
		Token tipoDato = esTipoDato();

		if (tipoDato != null) {
			obtenerSgteToken();
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token nombre = tokenActual;
				obtenerSgteToken();
				return new Parametro(tipoDato, nombre);

			} else {
				reportarError("Falta identificador en lista de parametros");
			}

		} else {
			reportarError("Falta tipo de dato en lista de parametros");
		}
		return null;
	}

	/**
	 * Metodo que permite agregar un error a la lista de errores
	 * 
	 * @param mensaje que se muestra al usuario
	 */
	public void reportarError(String mensaje) {
		tablaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
	}

	/**
	 * <TipoRetorno> ::= ¿numeric | ¿string | ¿trufa | ¿none
	 */
	public Token esTipoRetorno() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA) {

			if (tokenActual.getPalabra().equals("¿numeric") || tokenActual.getPalabra().equals("¿string")
					|| tokenActual.getPalabra().equals("¿trufa") || tokenActual.getPalabra().equals("¿none")) {
				return tokenActual;
			}

		}
		return null;
	}

	/**
	 * <TipoDato> ::= ¿numeric | ¿string | ¿trufa
	 */
	public Token esTipoDato() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA) {

			if (tokenActual.getPalabra().equals("¿numeric") || tokenActual.getPalabra().equals("¿string")
					|| tokenActual.getPalabra().equals("¿trufa")) {
				return tokenActual;
			}

		}
		return null;
	}

	public void obtenerSgteToken() {
		posActual++;

		if (posActual < tablaToken.size()) {
			tokenActual = tablaToken.get(posActual);
		} else {
			tokenActual = new Token("", Categoria.ERROR, 0, 0);
		}

	}

	/**
	 * Obtiene el token en la posicion indicada sin alterar el token actual de la
	 * ejecucion.
	 */
	public Token obtenerTokenPosicionN(int posicionToken) {
		Token tokenEncontrado;

		if (posicionToken < tablaToken.size()) {
			tokenEncontrado = tablaToken.get(posicionToken);
		} else {
			tokenEncontrado = null;
		}
		return tokenEncontrado;
	}

	public UnidadDeCompilacion getUnidadDeCompilacion() {
		return unidadDeCompilacion;
	}

	public ArrayList<ErrorSintactico> getTablaErrores() {
		return tablaErrores;
	}

}