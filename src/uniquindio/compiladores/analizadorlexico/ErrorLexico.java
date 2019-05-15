package uniquindio.compiladores.analizadorlexico;

/**
 * Clase que representa un error lexico del lenguaje
 * @author JHONNY_JORGE
 *
 */
public class ErrorLexico {

	private String palabra;
	private Categoria categoria;
	private int fila, columna;

	/**
	 * Constructor de la clase
	 * @param palabra
	 * @param categoria
	 * @param fila
	 * @param columna
	 */
	public ErrorLexico(String palabra, Categoria categoria, int fila, int columna) {
		super();
		this.palabra = palabra;
		this.categoria = categoria;
		this.fila = fila;
		this.columna = columna;
	}

	/**
	 * Metodo get palabra clase Token
	 * 
	 * @return palabra 
	 */
	public String getPalabra() {
		return palabra;
	}
	
	/**
	 * Metodo set palabra clase Token
	 * 
	 * @param palabra
	 */
	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}
	
	/**
	 * Metodo get categoria clase Token
	 * 
	 * @return categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}
	
	/**
	 * Metodo set categoria clase Token
	 * 
	 * @param categoria
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	/**
	 * Metodo get fila clase Token
	 * 
	 * @return fila
	 */
	public int getFila() {
		return fila;
	}
	
	/**
	 * Metodo set fila clase Token
	 * @param fila
	 */
	public void setFila(int fila) {
		this.fila = fila;
	}
	
	/**
	 * Metodo get columna clase Token
	 * 
	 * @return columna
	 */
	public int getColumna() {
		return columna;
	}
	
	/**
	 * Metodo set columna clase Token
	 * 
	 * @param columna
	 */
	public void setColumna(int columna) {
		this.columna = columna;
	}
	
	@Override
	public String toString() {
		return "Token [palabra=" + palabra + ", categoria=" + categoria + ", fila=" + fila + ", columna=" + columna
				+ "]";
	}

}
