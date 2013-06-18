package co.edu.uniminuto.clases;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

public abstract class Grafico {

	private Drawable drawable;
	private View view;
	private int posX, posY;
	private int ancho, alto;
	private int velX = 8, velY = 8;
	private int angulo, rotacion;
	private int radioColision; //Para determinar si hay colision

	public int getAngulo() {
		return angulo;
	}

	public void setAngulo(int angulo) {
		this.angulo = angulo;
	}

	public int getRotacion() {
		return rotacion;
	}

	public void setRotacion(int rotacion) {
		this.rotacion = rotacion;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
	
	public int getRadioColision(){
		return radioColision;
	}
	
	public void setRadioColision(int rColision){
		radioColision = rColision;
	}

	public Grafico(Drawable drawable, View view) {
		this.drawable = drawable;
		this.view = view;
		this.ancho = drawable.getIntrinsicWidth();
		this.alto = drawable.getIntrinsicHeight();
		this.radioColision = (alto + ancho) / 4; // Radio de colision del objeto 
	}

	public Rect getRectElemento() {
		return new Rect(posX, posY, posX + ancho, posY + alto);
	}

	public boolean puedoMover(int x, int y, Rect screen) {
		return screen.contains(posX + x, posY + y, posX + ancho + x, posY
				+ alto + y);
	}

	public void dibujarGrafico(Canvas canvas) {
		canvas.save();
		int x = (int) (posX + ancho);
		int y = (int) (posY + alto);
		canvas.rotate((int) angulo, x, y);
		drawable.setBounds((int) posX, (int) posY, (int) posX + ancho,
				(int) posY + alto);
		drawable.draw(canvas);
		canvas.restore();
	}

	/**
	 * Evalua la distancia entre dos objetos
	 * @param grafico grafico contra el cual comparar la distancia
	 * @return Distancia del grafica respecto a otro
	 */
	public double distancia(Grafico grafico){
		return distanciaE(this.getPosX(), this.getPosY(), grafico.getPosX(), grafico.getPosY());
	}
	
	/**
	 * Permite conocer la distancia entre dos objetos
	 * dependiendo de sus cordenadas x y y
	 * @param x Posición x del objeto 
	 * @param x2 Posición en x del objeto contra el cula se toma la distancia
	 * @param y Posición en y del objeto
	 * @param y2 Posición en y del objeto contra el cula se toma la distancia
	 * @return Distanacia entre dos objetos del mapa
	 */
	public double distanciaE(double x, double y, double x2, double y2){
		return Math.sqrt((x-x2)*(x-x2) + (y-y2)*(y-y2));
	}
	
	/**
	 * Verifica si se genero una colision en pantalla
	 * @param grafico Grafico contra el cual comparar si hay colision
	 * @return True o False dependiendo si hay o no colision
	 */
	public boolean verificarColision(Grafico grafico){
		return (distancia(grafico) < this.getRadioColision()+grafico.getRadioColision());
	}
	
	/**
	 * Valida si el objeto se ecnuentra en zona de alcance
	 * para dispararle
	 * @param g Grafico al que se busca disparar 
	 * @param screen el rectangulo del objeto que pretende generar el disparo
	 * @return True si es visible para disparar o false de lo contrario
	 */
	public boolean alcanceAtaque(Grafico g, Rect screen){
		return screen.contains(g.getRectElemento());
	}
	
}
