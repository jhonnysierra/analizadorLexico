package uniquindio.compiladores.analizadorSintactico;

import java.util.ArrayList;
import uniquindio.compiladores.analizadorlexico.Categoria;
import uniquindio.compiladores.analizadorlexico.Token;

/**
 * Clase que representa el Analizador Sintáctico o Gramatical del compilador
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
	 * <Funcion> ::= function <tipoRetorno> identificador "�" [<ListaParametros>]
	 * "�" "{" [<ListaSentencias>] "}"
	 */
	public Funcion esFuncion() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("�function")) {
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

								ArrayList<Sentencia> sentencias = null;

								if (tokenActual.getCategoria() != Categoria.LLAVE_DER) {
									sentencias = esListaSentencias();
								}

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
							reportarError("Falta par�ntesis derecho");
						}
					} else {
						reportarError("Falta par�ntesis izquierdo");
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

		// Sentencia s = esCiclo();

		Sentencia declaracion = esDeclaracion();

		if (declaracion != null) {
			return declaracion;
		}

		Sentencia expresionAsigancion = esExpresionAsignacion();

		if (expresionAsigancion != null) {
			return expresionAsigancion;
		}

		Sentencia impresion = esImpresion();
		
		if (impresion != null) {
			return impresion;
		}
		
		Sentencia lectura = esLectura();
		
		if (lectura != null) {
			return lectura;
		}

		Sentencia retorno = esRetorno();
		
		if (retorno != null) {
			return retorno;
		}
		
		return null;
	}

	/**
	 * <Ciclo> ::= �ciclo "�"<ExpresionRelacional>"�" do "["[<listaSentencias>]"]"
	 */
	public Ciclo esCiclo() {

		return null;
	}

	/**
	 * <Impresion>::= �PRINT <ExpresionCadena> "_"
	 */
	public Impresion esImpresion() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("�PRINT")) {
			obtenerSgteToken();

			ExpresionCadena cadena = esExpresionCadena();

			if (cadena != null) {
				if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
					obtenerSgteToken();
					return new Impresion(cadena);
				} else {
					reportarError("Falta fin de sentencia en la impresi�n");
				}

			} else {
				reportarError("Falta la expresi�n cadena en la impresi�n");
			}

		}
		return null;
	}

	/**
	 * <Lectura>::= �READ identificador "_"
	 */
	public Lectura esLectura() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("�READ")) {
			obtenerSgteToken();
			
			if (tokenActual.getCategoria()==Categoria.IDENTIFICADOR) {
				Token nombre = tokenActual;
				obtenerSgteToken();
				if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
					obtenerSgteToken();
					return new Lectura(nombre);
				} else {
					reportarError("Falta fin de sentencia en la Lectura");
				}

			} else {
				reportarError("Falta la identificador en la lectura");
			}

		}

		return null;
	}
	
	/**
	 * <Rertorno>::= �GIVE <Expresion> "_"
	 */
	public Retorno esRetorno() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("�GIVE")) {
			obtenerSgteToken();

			Expresion expresion = esExpresion();

			if (expresion != null) {
				if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
					obtenerSgteToken();
					return new Retorno(expresion);
				} else {
					reportarError("Falta fin de sentencia en el retorno");
				}

			} else {
				reportarError("Falta la expresi�n en el retorno");
			}

		}
		return null;
	}

	/**
	 * <Declaracion>::= �var <TipoDato> identificador "_"
	 */
	public Declaracion esDeclaracion() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("�var")) {
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
						return new Declaracion(tipoDato, nombre);
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
	 * Metodo que permite agregar un error a la lista de errores
	 * 
	 * @param mensaje que se muestra al usuario
	 */
	public void reportarError(String mensaje) {
		tablaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
	}

	/**
	 * <TipoRetorno> ::= �numeric | �string | �trufa | �none
	 */
	public Token esTipoRetorno() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA) {

			if (tokenActual.getPalabra().equals("�numeric") || tokenActual.getPalabra().equals("�string")
					|| tokenActual.getPalabra().equals("�trufa") || tokenActual.getPalabra().equals("�none")) {
				return tokenActual;
			}

		}
		return null;
	}

	/**
	 * <TipoDato> ::= �numeric | �string | �trufa
	 */
	public Token esTipoDato() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA) {

			if (tokenActual.getPalabra().equals("�numeric") || tokenActual.getPalabra().equals("�string")
					|| tokenActual.getPalabra().equals("�trufa")) {
				return tokenActual;
			}

		}
		return null;
	}

	/**
	 * <Termino>::= identificador | numero
	 * 
	 */
	public Token esTermino() {
		if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR || tokenActual.getCategoria() == Categoria.ENTERO
				|| tokenActual.getCategoria() == Categoria.REAL) {
			return tokenActual;
		}
		return null;
	}

	/**
	 * <Expresion>::= <ExpresionAritmetica> | <ExpresionRelacional> |
	 * <ExpresionCadena>
	 */
	public Expresion esExpresion() {

		ExpresionAritmetica expAritmetica = esExpresionAritmetica();

		if (expAritmetica != null) {
			return expAritmetica;
		}

		ExpresionRelacional expRelacional = esExpresionRelacional();

		if (expRelacional != null) {
			return expRelacional;
		}

		ExpresionCadena expCadena = esExpresionCadena();
		if (expCadena != null) {
			return expCadena;
		}

		return null;
	}

	/**
	 * <ExpresionA>::= <Termino> | <Expresion> | <tru> | <fa>
	 */
	public Expresion esExpresionA() {

		Expresion expresion = esExpresion();
		Token termino = esTermino();

		if (expresion != null) {
			return expresion;
		}

		return null;
	}

	/**
	 * <ExpresionAritmetica>::= <Termino> operador aritmetico <Termino>
	 */
	public ExpresionAritmetica esExpresionAritmetica() {
		Token termino1 = esTermino();

		if (termino1 != null) {
			obtenerSgteToken();
			if (tokenActual.getCategoria() == Categoria.OPERADOR_ARITMETICO) {
				Token operador = tokenActual;
				obtenerSgteToken();
				Token termino2 = esTermino();

				if (termino2 != null) {
					obtenerSgteToken();
					return new ExpresionAritmetica(termino1, operador, termino2);
				} else {
					reportarError("Falta segundo t�rmino en la expresi�n aritm�tica");
				}
			} else {
				// Backtracking para que sea analizado por otro metodo que empiece por termino
				obtenerTokenPosicionN(posActual - 1);
				// reportarError("Falta el operador aritm�tico en la expresi�n");
			}
		}
		return null;
	}

	/**
	 * <ExpresionRelacional>::= <Termino> operador relacional <Termino>
	 */
	public ExpresionRelacional esExpresionRelacional() {
		Token termino1 = esTermino();

		if (termino1 != null) {
			obtenerSgteToken();
			if (tokenActual.getCategoria() == Categoria.OPERADOR_RELACIONAL) {
				Token operador = tokenActual;
				obtenerSgteToken();
				Token termino2 = esTermino();

				if (termino2 != null) {
					obtenerSgteToken();
					return new ExpresionRelacional(termino1, operador, termino2);
				} else {
					reportarError("Falta segundo t�rmino en la expresi�n aritm�tica");
				}
			} else {
				// Backtracking para que sea analizado por otro metodo que empiece por termino
				obtenerTokenPosicionN(posActual - 1);
				// reportarError("Falta el operador aritm�tico en la expresi�n");
			}
		}
		return null;
	}

	/**
	 * <ExpresionCadena>::= cadena caracteres ["-"<ExpresionCadena>] | identificador
	 */
	public ExpresionCadena esExpresionCadena() {
		ArrayList<Token> cadenas = new ArrayList<>();

		ExpresionCadena cadena = null;

		while (tokenActual.getCategoria() == Categoria.IDENTIFICADOR
				|| tokenActual.getCategoria() == Categoria.CADENA_CARACTERES) {
			cadenas.add(tokenActual);
			obtenerSgteToken();
			if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
				obtenerSgteToken();
				if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR
						|| tokenActual.getCategoria() == Categoria.CADENA_CARACTERES) {
					cadenas.add(tokenActual);
					obtenerSgteToken();
				} else {
					reportarError("Falta concatenar en la cadena de caracteres");
				}
			} else {
				break;
			}
		}

		cadena = new ExpresionCadena(cadenas);

		return cadena;
	}

	/**
	 * <ExpresionAsignacion>::= identificador operadorAsignacion <ExpresionA>
	 */
	public ExpresionAsignacion esExpresionAsignacion() {
		if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
			Token nombre = tokenActual;
			obtenerSgteToken();

			if (tokenActual.getCategoria() == Categoria.OPERADOR_ASIGNACION) {
				obtenerSgteToken();

				Expresion expresion = esExpresion();
				Token termino = esTermino();

				if (expresion != null) {
					if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
						obtenerSgteToken();
						return new ExpresionAsignacion(nombre, expresion);
					} else {
						reportarError("Falta fin de sentencia en la expresi�n de asignaci�n");
					}

				} else if (termino != null) {
					obtenerSgteToken();
					if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
						obtenerSgteToken();
						return new ExpresionAsignacion(nombre, termino);
					} else {
						reportarError("Falta fin de sentencia en la expresi�n de asignaci�n");
					}
				} else if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
						&& tokenActual.getPalabra().equals("�tru")
						|| tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
								&& tokenActual.getPalabra().equals("�fa")) {
					Token expresionA = tokenActual;
					obtenerSgteToken();
					if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
						obtenerSgteToken();
						return new ExpresionAsignacion(nombre, expresionA);
					} else {
						reportarError("Falta fin de sentencia en la expresi�n de asignaci�n");
					}

				} else {
					reportarError("Falta expresi�n en la expresi�n de asignaci�n");
				}

			} else {
				reportarError("Falta el operador de asignaci�n en la expresi�n de asignaci�n");
			}
		}
		return null;
	}

	/**
	 * Obtiene el siguiente token de la tabla de tokens
	 */
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
	public void obtenerTokenPosicionN(int posicionToken) {
		posActual = posicionToken;
		if (posicionToken < tablaToken.size()) {
			tokenActual = tablaToken.get(posicionToken);
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