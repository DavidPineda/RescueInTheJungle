package co.edu.uniminuto.mundo;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.view.View;
import co.edu.uniminuto.clases.Grafico;
import co.edu.uniminuto.clases.GraficoMovil;

public class Enemigo extends Grafico implements GraficoMovil{

	private final static int VIDA_MAXIMA = 20;
	private alcanceDeAtaque alcanceDeAtaque;
	private boolean esMovil;
	private ArrayList<Arma> armas;
 	private int nivelVida;
	
	public enum alcanceDeAtaque{
		bajo, medio, alto;
	}	
	
	public alcanceDeAtaque getAlcanceDeAtaque() {
		return alcanceDeAtaque;
	}

	public void setAlcanceDeAtaque(alcanceDeAtaque alcanceDeAtaque) {
		this.alcanceDeAtaque = alcanceDeAtaque;
	}

	public boolean isEsMovil() {
		return esMovil;
	}

	public void setEsMovil(boolean esMovil) {
		this.esMovil = esMovil;
	}

	public ArrayList<Arma> getArmas() {
		return armas;
	}

	public void setArmas(ArrayList<Arma> armas) {
		this.armas = armas;
	}

	public void agregarArma(Arma arma){
		this.armas.add(arma);
	}
	
	public int getNivelVida() {
		return nivelVida;
	}

	public void setNivelVida(int nivelVida) {
		this.nivelVida = nivelVida;
	}
	
	public Enemigo(Drawable drawable, View view, alcanceDeAtaque ataque, boolean esMovil) {
		super(drawable, view);
		setAlcanceDeAtaque(ataque);
		setEsMovil(esMovil);
		this.armas = new ArrayList<Arma>();
	}

	public Enemigo(Drawable drawable, View view, alcanceDeAtaque ataque, boolean esMovil, ArrayList<Arma> armas) {
		super(drawable, view);
		setAlcanceDeAtaque(ataque);
		setEsMovil(esMovil);
		setArmas(armas);
		this.armas = armas;
	}

	@Override
	public void move(int x, int y) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void manejoVida(int cantVida, int tipCambio) {
		// TODO Auto-generated method stub	
	}
	
	

}
