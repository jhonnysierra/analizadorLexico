package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;

import uniquindio.compiladores.analizadorlexico.Categoria;
import uniquindio.compiladores.analizadorlexico.Token;

/**
 * Clase que representa el Analizador Sint√°ctico o Gramatical del compilador
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
	 * <Funcion> ::= function <tipoRetorno> identificador "(" [<ListaParametros>]
	 * ")" "{" [<ListaSentencias>] "}"
	 */
	public Funcion esFuncion() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("øfunction")) {
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

						ArrayList<Parametro> parametros = esListaParametros();

						if (tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {
							obtenerSgteToken();
							if (tokenActual.getCategoria() == Categoria.LLAVE_IZQ) {
								obtenerSgteToken();	
								
								ArrayList<Sentencia> sentencias = esListaSentencias();
								
								if (tokenActual.getCategoria()==Categoria.LLAVE_DER) {
									obtenerSgteToken();
									return new Funcion(nombreFuncion, parametros, tipoRetorno, sentencias);	
								}else {
									reportarError("Falta llave derecha");
								}
								
							}else {
								reportarError("Falta llave izquierda");	
							}
						} else {
							reportarError("Falta parÈntesis derecho");
						}
					} else {
						reportarError("Falta parÈntesis izquierdo");
					}
				} else {
					reportarError("Falta el identificador");
				}
			} else {
				reportarError("Falta el tipo de retorno");
			}

			/// --------------------
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token nombreFuncion = tokenActual;
				obtenerSgteToken();

				if (tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
					obtenerSgteToken();

					ArrayList<Parametro> parametros = esListaParametros();

					if (tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {
						obtenerSgteToken();
						// Token tipoRetorno = null;

						if (tokenActual.getCategoria() == Categoria.DOS_PUNTOS) {
							obtenerSgteToken();

							tipoRetorno = esTipoRetorno();

							if (tipoRetorno != null) {
								obtenerSgteToken();
							} else {
								reportarError("Falta el tipo de retorno");
							}

						}

						if (tokenActual.getCategoria() == Categoria.LLAVE_IZQ) {
							obtenerSgteToken();

							ArrayList<Sentencia> sentencias = esListaSentencias();

							if (tokenActual.getCategoria() == Categoria.LLAVE_DER) {
								obtenerSgteToken();
								return new Funcion(nombreFuncion, parametros, tipoRetorno, sentencias);
							} else {
								reportarError("Falta la llave derecha");
							}

						} else {
							reportarError("Falta la llave izquierda");
						}

					} else {
						reportarError("Falta el par√©ntesis derecho");
					}

				} else {
					reportarError("Falta el par√©ntesis izquierdo");
				}
			} else {
				reportarError("Falta el nombre de la funci√≥n");
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
	 * <Sentencia> ::= <Ciclo>
	 */
	public Sentencia esSentencia() {

		Sentencia s = esCiclo();

		if (s != null) {
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
	 * <Parametro> ::= identificador":"<TipoDeDato> <Parametro> ::= <TipoDeDato>
	 * identificador
	 */
	public Parametro esParametro() {
		Token tipoDato = esTipoDato();

		if (tipoDato != null) {
			Token nombre = tokenActual;
			obtenerSgteToken();

			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				obtenerSgteToken();

				return new Parametro(nombre, tipoDato);

			} else {
				reportarError("Falta identificador");
			}

		}else {
			reportarError("Falta tipo dato");
		}

		return null;
	}

	public void reportarError(String mensaje) {
		tablaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
	}

	/**
	 * <TipoRetorno> ::= ønumeric | østring | øtrufa | ønone
	 */
	public Token esTipoRetorno() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA) {

			if (tokenActual.getPalabra().equals("ønumeric") || tokenActual.getPalabra().equals("østring")
					|| tokenActual.getPalabra().equals("øtrufa") || tokenActual.getPalabra().equals("ønone")) {
				return tokenActual;
			}

		}
		return null;
	}

	/**
	 * <TipoDato> ::= ønumeric | østring | øtrufa
	 */
	public Token esTipoDato() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA) {

			if (tokenActual.getPalabra().equals("ønumeric") || tokenActual.getPalabra().equals("østring")
					|| tokenActual.getPalabra().equals("øtrufa")) {
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

	public UnidadDeCompilacion getUnidadDeCompilacion() {
		return unidadDeCompilacion;
	}

	public ArrayList<ErrorSintactico> getTablaErrores() {
		return tablaErrores;
	}

}