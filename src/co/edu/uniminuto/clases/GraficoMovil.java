package co.edu.uniminuto.clases;


public interface GraficoMovil {
	
	/**
	 * Mueve el objeto de posicion
	 * @param x Nueva posicion en x
	 * @param y Nueva posicion en y
	 */
	public void move(int x, int y);
	
	/**
	 * Aumenta o disminuye la cantidad de vida de un objeto
	 * @param cantVida cantidad a aumentar o disminuir
	 * @param tipCambio si tipoCambio = 1 aumenta vida, si tipoCambio = 2 disminuye vida 
	 */
	public void manejoVida(int cantVida, int tipCambio);
}
