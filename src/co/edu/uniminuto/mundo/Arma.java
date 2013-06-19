package co.edu.uniminuto.mundo;

import android.graphics.drawable.Drawable;
import android.view.View;
import co.edu.uniminuto.clases.Coordenada;
import co.edu.uniminuto.clases.Grafico;
import co.edu.uniminuto.clases.GraficoMovil;

public class Arma extends Grafico implements GraficoMovil {

	public enum Tipo {
		bomba, misil, metralla;
	}

	public enum CapacidadDano {
		alto, medio, bajo;
	}

	public enum Alcance {
		alto, medio, bajo;
	}

	private Tipo tipo;
	private CapacidadDano capacidadDano;
	private Alcance alcance;
	private Coordenada punto1;
	private Coordenada punto2;

	public Coordenada getPunto1() {
		return punto1;
	}

	public void setPunto1(Coordenada punto1) {
		this.punto1 = punto1;
	}

	public Coordenada getPunto2() {
		return punto2;
	}

	public void setPunto2(Coordenada punto2) {
		this.punto2 = punto2;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public CapacidadDano getCapacidadDano() {
		return capacidadDano;
	}

	public void setCapacidadDano(CapacidadDano capacidadDano) {
		this.capacidadDano = capacidadDano;
	}

	public Alcance getAlcance() {
		return alcance;
	}

	public void setAlcance(Alcance alcance) {
		this.alcance = alcance;
	}

	public Arma(Arma arma) {
		super(arma.getDrawable(), arma.getView());
	}

	public Arma(Drawable drawable, View view, Tipo tipo) {
		super(drawable, view);
		this.tipo = tipo;
		this.capacidadDano = null;
		this.alcance = null;
	}

	public Arma(Tipo tipo, CapacidadDano capacidadDano, Alcance alcance,
			Drawable drawable, View view) {
		super(drawable, view);
		this.tipo = tipo;
		this.capacidadDano = capacidadDano;
		this.alcance = alcance;
	}

	@Override
	public void move(int x, int y) {
		setPosX(x);
		setPosY(y);
	}

	@Override
	public void manejoVida(int cantVida, int tipCambio) {
		// TODO Auto-generated method stub
		
	}

}
