package uniquindio.compiladores.analizadorlexico;

import java.util.ArrayList;

/**
 * Clase que representa un Analizador Lexico del compilador
 * 
 * @author JHONNY_JORGE_CARLOS
 *
 */
public class AnalizadorLexico {

	private ArrayList<Token> listaTokens;
	private ArrayList<ErrorLexico> listaErrores;
	private ArrayList<String> palabrasReservadas;
	private String codigoFuente;
	private int posActual, filaActual, columnaActual;
	private char caracterActual;

	/**
	 * Metodo constructor clase AnalizadorLexico
	 * 
	 * @param codigoFuente para analizar
	 */
	public AnalizadorLexico(String codigoFuente) {
		this.codigoFuente = codigoFuente;
		this.listaTokens = new ArrayList<>();
		this.listaErrores = new ArrayList<>();
		this.palabrasReservadas = new ArrayList<>();
		this.palabrasReservadas.add("¿function");
		this.palabrasReservadas.add("¿JHONNY");
		this.palabrasReservadas.add("¿JORGE");
		this.palabrasReservadas.add("¿CARLOS");
		this.palabrasReservadas.add("¿function");
		this.palabrasReservadas.add("¿numeric");
		this.palabrasReservadas.add("¿string");
		this.palabrasReservadas.add("¿trufa");
		this.palabrasReservadas.add("¿none");
		this.palabrasReservadas.add("¿var");
		this.palabrasReservadas.add("¿CICLO");
		this.palabrasReservadas.add("¿DO");
		this.palabrasReservadas.add("¿PRINT");
		this.palabrasReservadas.add("¿READ");
		this.palabrasReservadas.add("¿GIVE");
		this.palabrasReservadas.add("¿MAKE");
		this.palabrasReservadas.add("¿none");
		this.palabrasReservadas.add("¿tru");
		this.palabrasReservadas.add("¿fa");
		this.caracterActual = codigoFuente.charAt(0);
		// El caracter del entero 0 representa un caracter nulo
		this.posActual = 0;
		filaActual = 0;
		columnaActual = 0;
	}

	/**
	 * Metodo que permite analizar el codigo e ir agregando a la lista de token la
	 * palabra identificada con su categoria y ubicacion donde fue encontrada. Fila
	 * y columna donde inicia
	 */
	public void analizar() {
		/*
		 * Invocacion a todos los automatas. Recorre todo el codigo fuente analizando
		 * caracter a caracter y verificando si forman un token valido para el lenguaje
		 * JORJHO
		 */

		while (posActual < codigoFuente.length()) {
			if (caracterActual == ' ' || caracterActual == '\n' || caracterActual == '\t') {
				darSiguienteCaracter();
			}

			if (esEntero())
				continue;
			if (esReal())
				continue;
			if (esIdentificador())
				continue;
			if (esOperadorArirmetico())
				continue;
			if (esOperadorRelacional())
				continue;
			if (esOperadorLogico())
				continue;
			if (esOperadorAsignacion())
				continue;
			if (esParentesis())
				continue;
			if (esLlave())
				continue;
			if (esTerminal())
				continue;
			if (esSeparador())
				continue;
			if (esHexadecimal())
				continue;
			if (esHora())
				continue;
			if (esCadena())
				continue;
			if (esCaracter())
				continue;
			if (esComentario())
				continue;
			if (esPunto())
				continue;
			if (esComa())
				continue;
			if (esFinSentencia())
				continue;
			if (esOperadorIncremento())
				continue;
			if (esOperadorDecremento())
				continue;
			if (esCorchete())
				continue;
			if (esComentarioLinea())
				continue;

			/*
			 * Si no se forma ningun token valido con la secuencia de caracteres el caracter
			 * se clasifica como desconocido
			 */
			if (posActual < codigoFuente.length()) {

				/*
				 * Condición para corregir error que si no encuentra categoría para un espacio
				 * no lo ponga como desconocido. Ocurre cuanto se retorna false y continúa la
				 * secuencia
				 */
				if (caracterActual == ' ' || caracterActual == '\n' || caracterActual == '\t') {
					darSiguienteCaracter();
				} else {
					listaTokens.add(new Token("" + caracterActual, Categoria.DESCONOCIDO, filaActual, columnaActual));
					darSiguienteCaracter();
				}

			}
		}
	}

	/**
	 * Metodo que permite verificar si un numero es entero
	 * 
	 * @return true si es entero false si no
	 */
	public boolean esEntero() {

		if (Character.isDigit(caracterActual)) {

			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;
			int posicionInicial = posActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			if (caracterActual != '.') {
				// Ciclo donde solo se aceptan digitos
				while (Character.isDigit(caracterActual) && posActual < codigoFuente.length()) {
					palabra += caracterActual;
					darSiguienteCaracter();
					if (caracterActual == '.') {
						// Se hace BT si dentro del ciclo hay un punto
						hacerBT(posicionInicial, columnaInicio, filaInicio);
						return false;
					}
				}

				listaTokens.add(new Token(palabra, Categoria.ENTERO, filaInicio, columnaInicio));
				return true;
			} else {
				// Si es punto se hace BT
				hacerBT(posicionInicial, columnaInicio, filaInicio);
				return false;
			}

		}

		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite verificar si una cadena es un numero real
	 * 
	 * @return true si es real false si no
	 */
	public boolean esReal() {

		if (Character.isDigit(caracterActual) || caracterActual == '.') {

			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;
			int posicionInicial = posActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			// Se verifica si el primer caracter es un punto
			if (palabra.contains(".")) {
				// Cerradura estrella
				while (Character.isDigit(caracterActual) && posActual < codigoFuente.length()) {
					palabra += caracterActual;
					darSiguienteCaracter();
				}

				// Se valida si la cadena solo es un punto, si es así se retorna falso
				if (palabra.length() == 1 && palabra.contains(".")) {
					// Se realiza backtrackig para que lo valide otro automata que coincide con este
					// caracter.
					darAnteriorCaracter(columnaInicio, filaInicio);
					return false;
				} else {
					listaTokens.add(new Token(palabra, Categoria.REAL, filaInicio, columnaInicio));
					return true;
				}

			} else {
				while ((Character.isDigit(caracterActual) || caracterActual == '.')
						&& posActual < codigoFuente.length()) {

					if (palabra.contains(".") && caracterActual == '.') {
						break;
					} else {
						palabra += caracterActual;
						darSiguienteCaracter();

						// Si la palabra no contiene punto y el caracter actual es diferente de punto y
						// diferente de digito se debe hacer BT
						if (caracterActual != '.' && !(Character.isDigit(caracterActual)) && !(palabra.contains("."))) {
							hacerBT(posicionInicial, columnaInicio, filaInicio);
							return false;
						}

					}
				}
				listaTokens.add(new Token(palabra, Categoria.REAL, filaInicio, columnaInicio));
				return true;
			}

		}

		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Permite ver si una cadena es un identificador. Un identificador inicia con ¿
	 * seguido del conjunto L. Dentro de esta categoria estan las siguientes
	 * palabras reservadas(en mayusculas) ¿JHONNY ¿JORGE ¿CARLOS
	 * 
	 * @return true si es identificador false si no
	 */
	public boolean esIdentificador() {

		if (caracterActual == '¿') {

			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			if (Character.isLetter(caracterActual)) {
				// Cerradura estrella
				while (Character.isLetter(caracterActual) && posActual < codigoFuente.length()) {
					palabra += caracterActual;
					darSiguienteCaracter();
				}

				if (!palabrasReservadas.contains(palabra)) {
					listaTokens.add(new Token(palabra, Categoria.IDENTIFICADOR, filaInicio, columnaInicio));
					return true;
				} else {
					listaTokens.add(new Token(palabra, Categoria.PALABRA_RESERVADA, filaInicio, columnaInicio));
					return true;
				}

			} else {

				// Reporte de error si es fin de codigo o cos
				palabra += caracterActual;
				listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
				darSiguienteCaracter();
				return true;
			}
		}

		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite identificar si una cadena es operador aritmetico
	 * 
	 * @return true si es operador aritmetico o false si no
	 */
	public boolean esOperadorArirmetico() {

		if (caracterActual == '%') {

			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			// Transicion del conjunto O={S,R,M,D}
			if (caracterActual == 'S' || caracterActual == 'R' || caracterActual == 'M' || caracterActual == 'D') {
				palabra += caracterActual;
				darSiguienteCaracter();
				listaTokens.add(new Token(palabra, Categoria.OPERADOR_ARITMETICO, filaInicio, columnaInicio));
				return true;
			} else {

				// Reporte de error si es fin de codigo o cos
				palabra += caracterActual;
				listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
				darSiguienteCaracter();
				return true;

				/*
				 * darAnteriorCaracter(columnaInicio, filaInicio); return false;
				 */
			}

		}

		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite identificar si una cadena es un operador relacional METODO
	 * HELP
	 * 
	 * @return true si es operador relacional o false si no
	 */
	public boolean esOperadorRelacional() {

		if (caracterActual == '&') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			if (caracterActual == '>') {
				palabra += caracterActual;
				darSiguienteCaracter();

				if (caracterActual == '=') {
					palabra += caracterActual;
					listaTokens.add(new Token(palabra, Categoria.OPERADOR_RELACIONAL, filaInicio, columnaInicio));
					darSiguienteCaracter();
					return true;
				} else {
					listaTokens.add(new Token(palabra, Categoria.OPERADOR_RELACIONAL, filaInicio, columnaInicio));
					return true;
				}

			} else if (caracterActual == '<') {
				palabra += caracterActual;
				darSiguienteCaracter();

				if (caracterActual == '=') {
					palabra += caracterActual;
					listaTokens.add(new Token(palabra, Categoria.OPERADOR_RELACIONAL, filaInicio, columnaInicio));
					darSiguienteCaracter();
					return true;
				} else if (caracterActual == '>') {
					palabra += caracterActual;
					listaTokens.add(new Token(palabra, Categoria.OPERADOR_RELACIONAL, filaInicio, columnaInicio));
					darSiguienteCaracter();
					return true;
				} else {
					listaTokens.add(new Token(palabra, Categoria.OPERADOR_RELACIONAL, filaInicio, columnaInicio));
					return true;
				}
			} else if (caracterActual == '=') {
				palabra += caracterActual;
				darSiguienteCaracter();
				if (caracterActual == '=') {
					palabra += caracterActual;
					listaTokens.add(new Token(palabra, Categoria.OPERADOR_RELACIONAL, filaInicio, columnaInicio));
					darSiguienteCaracter();
					return true;
				} else {
					// Reporte de error si es fin de codigo o cos
					palabra += caracterActual;
					listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
					darSiguienteCaracter();
					return true;

					/*
					 * darAnteriorCaracter(columnaInicio, filaInicio);
					 * darAnteriorCaracter(columnaInicio, filaInicio); return false;
					 */

				}
			} else {
				// Reporte de error si es fin de codigo o cos
				palabra += caracterActual;
				listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
				darSiguienteCaracter();
				return true;

				/*
				 * darAnteriorCaracter(columnaInicio, filaInicio); return false;
				 */
			}
		}

		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite identificar si una cadena es un operador logico
	 * 
	 * @return true si es un operador logico false si no
	 */
	public boolean esOperadorLogico() {

		if (caracterActual == '|') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			if (caracterActual == 'Y' || caracterActual == 'O' || caracterActual == '-') {
				palabra += caracterActual;
				listaTokens.add(new Token(palabra, Categoria.OPERADOR_LOGICO, filaInicio, columnaInicio));
				darSiguienteCaracter();
				return true;
			} else {

				// Reporte de error si es fin de codigo o cos
				palabra += caracterActual;
				listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
				darSiguienteCaracter();
				return true;
				/*
				 * darAnteriorCaracter(columnaInicio, filaInicio); return false;
				 */
			}
		}

		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite identificar si una cadena es un operador de asignacion
	 * 
	 * @return true si es un operador relacional o false si no
	 */
	public boolean esOperadorAsignacion() {
		if (caracterActual == ':') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			if (caracterActual == ':') {
				palabra += caracterActual;
				darSiguienteCaracter();
				if (caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '/'
						|| caracterActual == '%') {
					palabra += caracterActual;
					listaTokens.add(new Token(palabra, Categoria.OPERADOR_ASIGNACION, filaInicio, columnaInicio));
					darSiguienteCaracter();
					return true;
				} else {
					listaTokens.add(new Token(palabra, Categoria.OPERADOR_ASIGNACION, filaInicio, columnaInicio));
					return true;
				}
			} else {

				// Reporte de error si es fin de codigo o cos
				palabra += caracterActual;
				listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
				darSiguienteCaracter();
				return true;
			}
		}

		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite identificar si un caracter es parentesis que izquierdo o
	 * derecho. Parentesis que abre ASCII(174) y parentesis que cierra ASCII(175)
	 * 
	 * @return true si es parentesis false si no
	 */
	public boolean esParentesis() {
		String palabra = "";
		int filaInicio = filaActual;
		int columnaInicio = columnaActual;

		if (caracterActual == '«') {
			palabra += caracterActual;
			listaTokens.add(new Token(palabra, Categoria.PARENTESIS_IZQ, filaInicio, columnaInicio));
			darSiguienteCaracter();
			return true;
		} else if (caracterActual == '»') {
			palabra += caracterActual;
			listaTokens.add(new Token(palabra, Categoria.PARENTESIS_DER, filaInicio, columnaInicio));
			darSiguienteCaracter();
			return true;
		}

		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite identificar si un caracter es una llave izquerda o derecha
	 * 
	 * @return true si es una llave o false si no
	 */
	public boolean esLlave() {
		String palabra = "";
		int filaInicio = filaActual;
		int columnaInicio = columnaActual;

		if (caracterActual == '{') {
			palabra += caracterActual;
			listaTokens.add(new Token(palabra, Categoria.LLAVE_IZQ, filaInicio, columnaInicio));
			darSiguienteCaracter();
			return true;
		} else if (caracterActual == '}') {
			palabra += caracterActual;
			listaTokens.add(new Token(palabra, Categoria.LLAVE_DER, filaInicio, columnaInicio));
			darSiguienteCaracter();
			return true;
		}

		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite identificar si un caracter es una corchete izquerdo o
	 * derecho
	 * 
	 * @return true si es una corchete o false si no
	 */
	public boolean esCorchete() {
		String palabra = "";
		int filaInicio = filaActual;
		int columnaInicio = columnaActual;

		if (caracterActual == '[') {
			palabra += caracterActual;
			listaTokens.add(new Token(palabra, Categoria.CORCHETE_IZQ, filaInicio, columnaInicio));
			darSiguienteCaracter();
			return true;
		} else if (caracterActual == ']') {
			palabra += caracterActual;
			listaTokens.add(new Token(palabra, Categoria.CORCHETE_DER, filaInicio, columnaInicio));
			darSiguienteCaracter();
			return true;
		}

		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite identificar si un caracter es un terminal de linea
	 * 
	 * @return true si es terminal de linea o false si no
	 */
	public boolean esTerminal() {
		if (caracterActual == ';') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			palabra += caracterActual;
			listaTokens.add(new Token(palabra, Categoria.TERMINAL, filaInicio, columnaInicio));
			darSiguienteCaracter();
			return true;
		}

		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite identificar si un caracter es un separador
	 * 
	 * @return true si es separador o false si no
	 */
	public boolean esSeparador() {

		if (caracterActual == '-') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			palabra += caracterActual;
			listaTokens.add(new Token(palabra, Categoria.SEPARADOR, filaInicio, columnaInicio));
			darSiguienteCaracter();
			return true;
		}
		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite identificar si una cadena es un numero Hexadecimal
	 * 
	 * @return true si es hexadecimal false si no
	 */
	public boolean esHexadecimal() {
		if (caracterActual == '#') {
			String palabra = "", caracter;
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			caracter = String.valueOf(caracterActual);

			if (Character.isDigit(caracterActual) || (caracter.codePointAt(0) >= 65 && caracter.codePointAt(0) <= 70)) {
				palabra += caracterActual;
				darSiguienteCaracter();

				caracter = String.valueOf(caracterActual);

				while (Character.isDigit(caracterActual)
						|| (caracter.codePointAt(0) >= 65 && caracter.codePointAt(0) <= 70)
								&& posActual < codigoFuente.length()) {
					palabra += caracterActual;
					darSiguienteCaracter();
					caracter = String.valueOf(caracterActual);
				}

				listaTokens.add(new Token(palabra, Categoria.HEXADECIMAL, filaInicio, columnaInicio));
				return true;
			} else {
				darAnteriorCaracter(columnaInicio, filaInicio);
				return false;
			}
		}
		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite verificar si una cadena es una hora valida. Solo se
	 * verifica la estructura, no se valida si las partes de la hora son validas
	 * 
	 * @return true si es una estructura de hora valida false si no
	 */
	public boolean esHora() {
		if (caracterActual == '@') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			if (Character.isDigit(caracterActual)) {
				// Transicion
				palabra += caracterActual;
				darSiguienteCaracter();

				if (Character.isDigit(caracterActual)) {
					palabra += caracterActual;
					darSiguienteCaracter();

					if (caracterActual == ':') {
						palabra += caracterActual;
						darSiguienteCaracter();

						if (Character.isDigit(caracterActual)) {
							palabra += caracterActual;
							darSiguienteCaracter();

							if (Character.isDigit(caracterActual)) {
								palabra += caracterActual;
								listaTokens.add(new Token(palabra, Categoria.HORA, filaInicio, columnaInicio));
								darSiguienteCaracter();
								return true;
							} else {
								listaTokens.add(new Token(palabra, Categoria.HORA, filaInicio, columnaInicio));
								return true;
							}
						} else {
							palabra += caracterActual;
							listaTokens.add(new Token(palabra, Categoria.ERROR, filaInicio, columnaInicio));
							darSiguienteCaracter();
							return false;
						}
					} else {
						palabra += caracterActual;
						listaTokens.add(new Token(palabra, Categoria.ERROR, filaInicio, columnaInicio));
						darSiguienteCaracter();
						return false;
					}

				}

				else if (caracterActual == ':') {
					palabra += caracterActual;
					darSiguienteCaracter();

					if (Character.isDigit(caracterActual)) {
						palabra += caracterActual;
						darSiguienteCaracter();

						if (Character.isDigit(caracterActual)) {
							palabra += caracterActual;
							listaTokens.add(new Token(palabra, Categoria.HORA, filaInicio, columnaInicio));
							darSiguienteCaracter();
							return true;
						} else {
							listaTokens.add(new Token(palabra, Categoria.HORA, filaInicio, columnaInicio));
							return true;
						}
					} else {
						palabra += caracterActual;
						listaTokens.add(new Token(palabra, Categoria.ERROR, filaInicio, columnaInicio));
						darSiguienteCaracter();
						return false;
					}

				} else {
					palabra += caracterActual;
					listaTokens.add(new Token(palabra, Categoria.ERROR, filaInicio, columnaInicio));
					darSiguienteCaracter();
					return false;
				}

			} else {
				darAnteriorCaracter(columnaInicio, filaInicio);
				return false;
			}
		}

		// RE - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite verificar si una cadena es una cadena de caracteres. El
	 * caracter que encierra una cadena es ASCII(250)
	 * 
	 * @return true si es una cadena de caracteres false si no
	 */
	public boolean esCadena() {
		if (caracterActual == '·') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			while (posActual < codigoFuente.length()) {
				if (!(caracterActual == '\\')) {
					if (!(caracterActual == '·')) {
						palabra += caracterActual;
						darSiguienteCaracter();
					} else {
						palabra += caracterActual;
						darSiguienteCaracter();
						break;
					}

				} else {
					palabra += caracterActual;
					darSiguienteCaracter();
					if (caracterActual == '·') {
						palabra += caracterActual;
						darSiguienteCaracter();
					} else {
						palabra += caracterActual;
						listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
						darSiguienteCaracter();
						return true;
					}
				}
			}

			/*
			 * Valida si la cadena es una cadena correcta es decir que esta encerrada entre
			 * ASCII(250). Si no lo esta marca la cadena como error
			 */
			if (palabra.length() > 1 && palabra.endsWith("·")) {
				listaTokens.add(new Token(palabra, Categoria.CADENA_CARACTERES, filaInicio, columnaInicio));
				return true;
			} else if (palabra.length() == 1) {
				listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
				return false;
			} else {
				listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
				return false;
			}

		}

		// RE - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite identificar si una cadena es tipo caracter. El caracter
	 * valido debe estar encerrado entre ASCII(184).
	 * 
	 * @return true si es caracter false si no
	 */
	public boolean esCaracter() {
		if (caracterActual == '©') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			if (!(caracterActual == '\\')) {
				if (!(caracterActual == '©') && !(caracterActual == '\n')) {
					palabra += caracterActual;
					darSiguienteCaracter();

					if (caracterActual == '©') {
						palabra += caracterActual;
						listaTokens.add(new Token(palabra, Categoria.CARACTER, filaInicio, columnaInicio));
						darSiguienteCaracter();
						return true;
					} else {
						palabra += caracterActual;
						darSiguienteCaracter();
						listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
						return true;
					}
				} else {
					// RE - Reporte de error
					palabra += caracterActual;
					listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
					darSiguienteCaracter();
					return true;
				}

			} else {
				palabra += caracterActual;
				darSiguienteCaracter();
				if (caracterActual == '©') {
					palabra += caracterActual;
					darSiguienteCaracter();

					if (caracterActual == '©') {
						palabra += caracterActual;
						darSiguienteCaracter();
						listaTokens.add(new Token(palabra, Categoria.CARACTER, filaInicio, columnaInicio));
						return true;
					} else {
						// RE - reporte de error
						palabra += caracterActual;
						listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
						darSiguienteCaracter();
						return true;
					}
				} else {
					// RE - Reporte de error
					palabra += caracterActual;
					listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
					darSiguienteCaracter();
					return true;
				}
			}
		}

		// RE - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite verificar si una cadena es un comentario de bloque. Un
	 * comentario valido debe estar encerrado entre ASCII(94)
	 * 
	 * @return true si es comentario de bloque o false si no
	 */
	public boolean esComentario() {
		if (caracterActual == '^') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			while (posActual < codigoFuente.length()) {
				if (!(caracterActual == '\\')) {
					if (!(caracterActual == '^')) {
						palabra += caracterActual;
						darSiguienteCaracter();
					} else {
						palabra += caracterActual;
						darSiguienteCaracter();
						break;
					}

				} else {
					palabra += caracterActual;
					darSiguienteCaracter();
					if (caracterActual == '^') {
						palabra += caracterActual;
						darSiguienteCaracter();
					} else {
						palabra += caracterActual;
						listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
						darSiguienteCaracter();
						return true;
					}
				}
			}

			/*
			 * Valida si la cadena es una cadena correcta es decir que esta encerrada entre
			 * ASCII(94). Si no lo esta marca la cadena como error
			 */

			if (palabra.length() > 1 && palabra.endsWith("^")) {
				listaTokens.add(new Token(palabra, Categoria.COMENTARIO_BLOQUE, filaInicio, columnaInicio));
				return true;
			} else if (palabra.length() == 1) {
				listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
				return true;
			} else {
				listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
				return true;
			}

		}

		// RE - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite verificar si una cadena es un comentario de linea
	 * 
	 * @return true si es un comentario de linea false en caso contrario
	 */
	public boolean esComentarioLinea() {
		if (caracterActual == '$') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			while (posActual < codigoFuente.length()) {
				if (!(caracterActual == '\\')) {
					if (!(caracterActual == '\n')) {
						palabra += caracterActual;
						darSiguienteCaracter();
					} else {
						// AA - Si hay salto de linea
						palabra += caracterActual;
						darSiguienteCaracter();
						listaTokens.add(new Token(palabra, Categoria.COMENTARIO_LINEA, filaInicio, columnaInicio));
						return true;
					}
				} else {
					palabra += caracterActual;
					darSiguienteCaracter();
					if (caracterActual == '$') {
						palabra += caracterActual;
						darSiguienteCaracter();
					} else {
						// RE - Reporte de error
						palabra += caracterActual;
						listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
						darSiguienteCaracter();
						return true;
					}
				}

			}

			if (posActual >= codigoFuente.length()) {
				// RE - Reporte de error fin de codigo
				palabra += caracterActual;
				darSiguienteCaracter();
				listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
				return true;
			}

		}

		// RI - Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite validar si el token es sl punto
	 * 
	 * @return true si es punto, false en caso contrario
	 */
	public boolean esPunto() {
		if (caracterActual == '.') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			palabra += caracterActual;
			darSiguienteCaracter();

			if (Character.isDigit(caracterActual)) {
				// Hacer backtracking para verificar si el real
				darAnteriorCaracter(columnaInicio, filaInicio);
				return false;
			} else {
				listaTokens.add(new Token(palabra, Categoria.PUNTO, filaInicio, columnaInicio));
				return true;
			}

		}
		// Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite validar si el token es una coma
	 * 
	 * @return true si es coma, false en caso contrario
	 */
	public boolean esComa() {
		if (caracterActual == ',') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			palabra += caracterActual;
			darSiguienteCaracter();
			listaTokens.add(new Token(palabra, Categoria.COMA, filaInicio, columnaInicio));
			return true;
		}
		// Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite validar si el token es el fin de sentencia
	 * 
	 * @return true si es fin de sentencia, false en caso contrario
	 */
	public boolean esFinSentencia() {
		if (caracterActual == '_') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			palabra += caracterActual;
			darSiguienteCaracter();
			listaTokens.add(new Token(palabra, Categoria.FIN_DE_SENTENCIA, filaInicio, columnaInicio));
			return true;
		}
		// Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite verificar si el token es un operador de incremento
	 * 
	 * @return true si es un operador de incremento, false en caso contrario.
	 */
	public boolean esOperadorIncremento() {

		if (caracterActual == '¡') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			if (caracterActual == '+') {
				palabra += caracterActual;
				darSiguienteCaracter();
				if (caracterActual == '+') {
					palabra += caracterActual;
					listaTokens.add(new Token(palabra, Categoria.OPERADOR_INCREMENTO, filaInicio, columnaInicio));
					darSiguienteCaracter();
					return true;
				} else {
					// Reporte de error
					palabra += caracterActual;
					listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
					darSiguienteCaracter();
					return true;
				}

			} else {
				// Reporte de error
				palabra += caracterActual;
				listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
				darSiguienteCaracter();
				return true;
			}
		}

		// Rechazo inmediato
		return false;
	}

	/**
	 * Metodo que permite verificar si el token es un operador de decremento
	 * 
	 * @return true si es un operador de decremento, false en caso contrario.
	 */
	public boolean esOperadorDecremento() {

		if (caracterActual == '!') {
			String palabra = "";
			int filaInicio = filaActual;
			int columnaInicio = columnaActual;

			// Transicion
			palabra += caracterActual;
			darSiguienteCaracter();

			if (caracterActual == '-') {
				palabra += caracterActual;
				darSiguienteCaracter();
				if (caracterActual == '-') {
					palabra += caracterActual;
					listaTokens.add(new Token(palabra, Categoria.OPERADOR_DECREMENTO, filaInicio, columnaInicio));
					darSiguienteCaracter();
					return true;
				} else {
					// Reporte de error
					palabra += caracterActual;
					listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
					darSiguienteCaracter();
					return true;
				}

			} else {
				// Reporte de error
				palabra += caracterActual;
				listaErrores.add(new ErrorLexico(palabra, Categoria.ERROR, filaInicio, columnaInicio));
				darSiguienteCaracter();
				return true;
			}
		}

		// Rechazo inmediato
		return false;
	}

	/**
	 * Permite obtener el siguiente caracter de una cadena respecto a la posicion
	 * actual. De igual manera permite obtener la fila y la columna donde se ubica
	 * el caracter. Es la transicion del automata
	 */
	public void darSiguienteCaracter() {
		posActual++;
		if (posActual < codigoFuente.length()) {
			if (!(caracterActual == '\n')) {
				columnaActual++;
			} else {
				filaActual++;
				columnaActual = 0;
			}
			caracterActual = codigoFuente.charAt(posActual);
		} else {
			caracterActual = 0;
		}

	}

	/**
	 * Permite obtener el anterior caracter de una cadena respecto a la posicion
	 * actual. Actualiza la fila y la columna del caracter a anterior
	 */
	public void darAnteriorCaracter(int columna, int fila) {
		posActual--;
		if (posActual < codigoFuente.length()) {
			caracterActual = codigoFuente.charAt(posActual);
			columnaActual = columna;
			filaActual = fila;

		}

	}

	/**
	 * Permite hacer backtracking cuando sea el caso
	 * 
	 * @param posicionN posicion del caracter en la cadena
	 * @param columna   columna donde se ubica el caracter
	 * @param fila      fila donde se ubica el caracter
	 */
	public void hacerBT(int posicionN, int columna, int fila) {
		posActual = posicionN;
		if (posActual < codigoFuente.length()) {
			caracterActual = codigoFuente.charAt(posActual);
			columnaActual = columna;
			filaActual = fila;
		}

	}

	/**
	 * Devuelve la lista de tokens generados
	 * 
	 * @return Lista de objetos de la clase Token
	 */
	public ArrayList<Token> getListaTokens() {
		return listaTokens;
	}

	/**
	 * Devuelve la lista de errores encontrados
	 * 
	 * @return Lista de objetos de clase Error
	 */
	public ArrayList<ErrorLexico> getListaErrores() {
		return listaErrores;
	}

}
