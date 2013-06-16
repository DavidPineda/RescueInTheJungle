package co.edu.uniminuto.mundo;

import android.graphics.drawable.Drawable;
import android.view.View;
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
		setPosX(getPosX() + x);
		setPosY(getPosY() + y);
	}

}
