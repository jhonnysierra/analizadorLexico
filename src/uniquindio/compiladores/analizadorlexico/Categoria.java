package uniquindio.compiladores.analizadorlexico;

/**
 * Definicion de las categorias lexicas del lenguaje JHOSOCA
 * @author JHONNY_SORAYA_CAMILO
 *
 */
public enum Categoria {

	ENTERO,
	REAL,
	CADENA_CARACTERES,
	PARENTESIS_IZQ,
	PARENTESIS_DER,
	IDENTIFICADOR,
	LLAVE_IZQ,
	LLAVE_DER,
	CORCHETE_IZQ,
	CORCHETE_DER,
	OPERADOR_ARITMETICO,
	OPERADOR_RELACIONAL,
	OPERADOR_LOGICO,
	OPERADOR_ASIGNACION,
	OPERADOR_INCREMENTO,
	OPERADOR_DECREMENTO,
	TERMINAL,
	SEPARADOR,
	HEXADECIMAL,
	ERROR,
	HORA,
	CARACTER,
	COMENTARIO_BLOQUE,
	COMENTARIO_LINEA,
	PALABRA_RESERVADA,
	PUNTO,
	COMA,
	FIN_DE_SENTENCIA,
	DESCONOCIDO
	
}