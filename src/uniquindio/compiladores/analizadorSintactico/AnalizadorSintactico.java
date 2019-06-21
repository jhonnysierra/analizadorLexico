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

		Sentencia ciclo = esCiclo();

		if (ciclo != null) {
			return ciclo;
		}

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

		Sentencia incremento = esIncremento();

		if (incremento != null) {
			return incremento;
		}

		Sentencia decremento = esDecremento();

		if (decremento != null) {
			return decremento;
		}

		Sentencia invocacion = esInvocacion();

		if (invocacion != null) {
			return invocacion;
		}

		Sentencia asignarFuncion = esAsignarFuncion();

		if (asignarFuncion != null) {
			return asignarFuncion;
		}

		Sentencia decision = esDecision();

		if (decision != null) {
			return decision;
		}

		return null;
	}

	/**
	 * <Ciclo> ::= ¿CICLO "«"<ExpresionRelacional>"»" DO "["[<listaSentencias>]"]"
	 */
	public Ciclo esCiclo() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("¿CICLO")) {
			obtenerSgteToken();
			if (tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
				obtenerSgteToken();
				Expresion expresionR = esExpresionRelacional();

				if (expresionR != null) {
					if (tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {
						obtenerSgteToken();
						if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
								&& tokenActual.getPalabra().equals("¿DO")) {

							obtenerSgteToken();

							if (tokenActual.getCategoria() == Categoria.CORCHETE_IZQ) {
								obtenerSgteToken();

								ArrayList<Sentencia> sentencias = null;

								if (tokenActual.getCategoria() != Categoria.CORCHETE_DER) {
									sentencias = esListaSentencias();
								}

								if (tokenActual.getCategoria() == Categoria.CORCHETE_DER) {
									obtenerSgteToken();
									return new Ciclo(expresionR, sentencias);
								} else {
									reportarError("Falta corchete derecho en el ciclo");
								}

							} else {
								reportarError("Falta corchete izquierdo en el ciclo");
							}

						} else {
							reportarError("Falta palabra DO en el ciclo");
						}

					} else {
						reportarError("Falta parentesis derecho en el ciclo");
					}
				} else {
					reportarError("Falta expresion relacional en el ciclo");
				}
			} else {
				reportarError("Falta parentesis izquierdo en el ciclo");
			}
		}

		return null;
	}

	/**
	 * <Decision>::= ¿SI "«" <ExpresionRelacional> "»" "[" [<ListaSentencias>] "]"
	 * [¿NO "[" [<ListaSentencias>] "]"]
	 */
	public Decision esDecision() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("¿SI")) {
			obtenerSgteToken();
			if (tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
				obtenerSgteToken();
				Expresion expresionR = esExpresionRelacional();

				if (expresionR != null) {
					if (tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {
						obtenerSgteToken();

						if (tokenActual.getCategoria() == Categoria.CORCHETE_IZQ) {
							obtenerSgteToken();

							ArrayList<Sentencia> sentenciasSI = null;

							if (tokenActual.getCategoria() != Categoria.CORCHETE_DER) {
								sentenciasSI = esListaSentencias();
							}

							if (tokenActual.getCategoria() == Categoria.CORCHETE_DER) {
								obtenerSgteToken();

								if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
										&& tokenActual.getPalabra().equals("¿NO")) {
									obtenerSgteToken();

									if (tokenActual.getCategoria() == Categoria.CORCHETE_IZQ) {
										obtenerSgteToken();

										ArrayList<Sentencia> sentenciasNO = null;

										if (tokenActual.getCategoria() != Categoria.CORCHETE_DER) {
											sentenciasNO = esListaSentencias();
										}

										if (tokenActual.getCategoria() == Categoria.CORCHETE_DER) {
											obtenerSgteToken();
											return new Decision(expresionR, sentenciasSI, sentenciasNO);
										} else {
											reportarError("Falta corchete derecho en el NO de la decisión");
										}

									} else {
										reportarError("Falta corchete izquierdo en el NO de la decisión");
									}

								} else {
									return new Decision(expresionR, sentenciasSI, null);
								}

							} else {
								reportarError("Falta corchete derecho en el SI de la decisión");
							}

						} else {
							reportarError("Falta corchete izquierdo en el SI de la decisión");
						}

					} else {
						reportarError("Falta parentesis derecho en el SI de la decisión");
					}
				} else {
					reportarError("Falta expresion relacional en la decisión");
				}
			} else {
				reportarError("Falta parentesis izquierdo en la decisión");
			}
		}

		return null;
	}

	/**
	 * <Impresion>::= ¿PRINT <ExpresionCadena> "_"
	 */
	public Impresion esImpresion() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("¿PRINT")) {
			obtenerSgteToken();

			ExpresionCadena cadena = esExpresionCadena();

			if (cadena != null) {
				if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
					obtenerSgteToken();
					return new Impresion(cadena);
				} else {
					reportarError("Falta fin de sentencia en la impresión");
				}

			} else {
				reportarError("Falta la expresión cadena en la impresión");
			}

		}
		return null;
	}

	/**
	 * <Lectura>::= ¿READ identificador "_"
	 */
	public Lectura esLectura() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("¿READ")) {
			obtenerSgteToken();

			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
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
	 * <Rertorno>::= ¿GIVE <Expresion> "_"
	 */
	public Retorno esRetorno() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("¿GIVE")) {
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
				reportarError("Falta la expresión en el retorno");
			}

		}
		return null;
	}

	/**
	 * <Declaracion>::= ¿var <TipoDato> identificador "_"
	 */
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
	 * <ExpresionA>::= <Termino> | <Expresion> | ¿tru | ¿fa
	 */
	public ExpresionA esExpresionA() {

		Expresion expresion = esExpresion();

		if (expresion != null) {
			return new ExpresionA(expresion);
		}

		Token termino = esTermino();

		if (termino != null) {
			obtenerSgteToken();
			return new ExpresionA(termino);
		}

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("¿tru")
				|| tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
						&& tokenActual.getPalabra().equals("¿fa")) {
			Token token = tokenActual;
			obtenerSgteToken();
			return new ExpresionA(token);

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
					reportarError("Falta segundo término en la expresión aritmética");
				}
			} else {
				// Backtracking para que sea analizado por otro metodo que empiece por termino
				obtenerTokenPosicionN(posActual - 1);
				// reportarError("Falta el operador aritmético en la expresión");
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
					reportarError("Falta segundo término en la expresión aritmética");
				}
			} else {
				// Backtracking para que sea analizado por otro metodo que empiece por termino
				obtenerTokenPosicionN(posActual - 1);
				// reportarError("Falta el operador aritmético en la expresión");
			}
		}
		return null;
	}

	/**
	 * <ExpresionCadena>::= cadena caracteres ["-"<ExpresionCadena>] | identificador
	 */
	public ExpresionCadena esExpresionCadena() {
		ArrayList<Token> cadenas = new ArrayList<>();

		// ExpresionCadena cadena = null;

		if (tokenActual.getCategoria() == Categoria.CADENA_CARACTERES) {
			cadenas.add(tokenActual);
			obtenerSgteToken();

			while (tokenActual.getCategoria() == Categoria.SEPARADOR) {
				obtenerSgteToken();
				if (tokenActual.getCategoria() == Categoria.CADENA_CARACTERES) {
					cadenas.add(tokenActual);
					obtenerSgteToken();
				} else {
					reportarError("Falta concatenar en la cadena de caracteres");
				}
			}

			return new ExpresionCadena(cadenas);
		}

		/*
		 * while (tokenActual.getCategoria() == Categoria.CADENA_CARACTERES) {
		 * cadenas.add(tokenActual); obtenerSgteToken(); if (tokenActual.getCategoria()
		 * == Categoria.SEPARADOR) { obtenerSgteToken(); if (tokenActual.getCategoria()
		 * == Categoria.CADENA_CARACTERES) { cadenas.add(tokenActual);
		 * obtenerSgteToken(); } else {
		 * reportarError("Falta concatenar en la cadena de caracteres"); } } else {
		 * //return new ExpresionCadena(cadenas); } }
		 */

		return null;

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
						reportarError("Falta fin de sentencia en la expresión de asignación");
					}

				} else if (termino != null) {
					obtenerSgteToken();
					if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
						obtenerSgteToken();
						return new ExpresionAsignacion(nombre, termino);
					} else {
						reportarError("Falta fin de sentencia en la expresión de asignación");
					}
				} else if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
						&& tokenActual.getPalabra().equals("¿tru")
						|| tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
								&& tokenActual.getPalabra().equals("¿fa")) {
					Token expresionA = tokenActual;
					obtenerSgteToken();
					if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
						obtenerSgteToken();
						return new ExpresionAsignacion(nombre, expresionA);
					} else {
						reportarError("Falta fin de sentencia en la expresión de asignación");
					}

				} else {
					reportarError("Falta expresión en la expresión de asignación");
				}

			} else {
				// Backtracking para que sea analizado por otro metodo que empiece por termino
				obtenerTokenPosicionN(posActual - 1);
				// reportarError("Falta el operador de asignación en la expresión de
				// asignación");
			}
		}
		return null;
	}

	/**
	 * <Incremento>::=operadorincremento identificador "_"
	 */
	public Incremento esIncremento() {
		if (tokenActual.getCategoria() == Categoria.OPERADOR_INCREMENTO) {
			Token operador = tokenActual;
			obtenerSgteToken();
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token nombre = tokenActual;
				obtenerSgteToken();
				if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
					obtenerSgteToken();
					return new Incremento(nombre, operador);
				} else {
					reportarError("Falta fin de sentencia en el incremento");
				}
			} else {
				reportarError("Falta identificador en el incremento");
			}
		}

		return null;
	}

	/**
	 * <Decremento>::=operadordecremento identificador "_"
	 */
	public Decremento esDecremento() {
		if (tokenActual.getCategoria() == Categoria.OPERADOR_DECREMENTO) {
			Token operador = tokenActual;
			obtenerSgteToken();
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token nombre = tokenActual;
				obtenerSgteToken();
				if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
					obtenerSgteToken();
					return new Decremento(nombre, operador);
				} else {
					reportarError("Falta fin de sentencia en el decremento");
				}
			} else {
				reportarError("Falta identificador en el decremento");
			}
		}

		return null;
	}

	/**
	 * <ListaArgumentos> ::= <Argumento>[","<ListaArgumentos>]
	 */
	public ArrayList<Argumento> esListaArgumentos() {

		ArrayList<Argumento> argumentos = new ArrayList<>();

		Argumento a = esArgumento();

		while (a != null) {
			argumentos.add(a);

			if (tokenActual.getCategoria() == Categoria.COMA) {
				obtenerSgteToken();
				a = esArgumento();
			} else {
				a = null;
			}

		}

		return argumentos;
	}

	/**
	 * <Argumento> ::= <ExpresionA>
	 */
	public Argumento esArgumento() {
		ExpresionA expresionA = esExpresionA();

		if (expresionA != null) {
			// obtenerSgteToken();
			return new Argumento(expresionA);
		}

		return null;
	}

	/**
	 * <Invocacion>::= ¿CALL identificador "<<" [<ListaArgumentos>] ">>" "_"
	 */
	public Invocacion esInvocacion() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("¿CALL")) {
			obtenerSgteToken();
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token nombreFuncion = tokenActual;
				obtenerSgteToken();

				if (tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
					obtenerSgteToken();

					ArrayList<Argumento> argumentos = null;

					if (tokenActual.getCategoria() != Categoria.PARENTESIS_DER) {
						argumentos = esListaArgumentos();
					}

					if (tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {
						obtenerSgteToken();
						if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
							obtenerSgteToken();
							return new Invocacion(nombreFuncion, argumentos);
						} else {
							reportarError("Falta fin de sentencia en la invocación");
						}
					} else {
						reportarError("Falta paréntesis derecho en la invocación");
					}
				} else {
					reportarError("Falta paréntesis izquierdo en la invocación");
				}

			} else {
				reportarError("Falta identificador en la invocación");
			}
		}

		return null;
	}

	/**
	 * <AsignarFuncion>::= ¿MAKE identificador <InvocacionFuncion> "_"
	 */
	public AsignarFuncion esAsignarFuncion() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("¿MAKE")) {
			obtenerSgteToken();

			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token nombre = tokenActual;
				obtenerSgteToken();

				Invocacion invocacionF = esInvocacion();

				if (invocacionF != null) {
					if (tokenActual.getCategoria() == Categoria.FIN_DE_SENTENCIA) {
						obtenerSgteToken();
						return new AsignarFuncion(nombre, invocacionF);
					} else {
						reportarError("Falta fin de sentencia en la asignación de función");
					}
				} else {
					reportarError("Falta invocación en la asignación de función");
				}
			} else {
				reportarError("Falta identificador en la asignación de función");
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