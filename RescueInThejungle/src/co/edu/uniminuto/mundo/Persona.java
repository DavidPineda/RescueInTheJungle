package co.edu.uniminuto.mundo;

import android.graphics.drawable.Drawable;
import android.view.View;
import co.edu.uniminuto.clases.Grafico;
import co.edu.uniminuto.clases.GraficoMovil;

public class Persona extends Grafico implements GraficoMovil {

	enum Genero {
		masculino, femenino;
	}

	enum TipoPersonaje {
		gerrillero, soldado, liberado;
	}

	private TipoPersonaje tipo;
	private Genero genero;
	private int vida;

	public TipoPersonaje getTipo() {
		return tipo;
	}

	public void setTipo(TipoPersonaje tipo) {
		this.tipo = tipo;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public Persona(Drawable drawable, View view) {
		super(drawable, view);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move(int x, int y) {
		// TODO Auto-generated method stub

	}
}
