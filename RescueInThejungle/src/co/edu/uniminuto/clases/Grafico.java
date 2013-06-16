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

	public Grafico(Drawable drawable, View view) {
		this.drawable = drawable;
		this.view = view;
		ancho = drawable.getIntrinsicWidth();
		alto = drawable.getIntrinsicHeight();
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

}
