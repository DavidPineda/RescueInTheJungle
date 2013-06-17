package co.edu.uniminuto.mundo;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.view.View;
import co.edu.uniminuto.clases.Grafico;
import co.edu.uniminuto.clases.GraficoMovil;

public class Helicoptero extends Grafico implements GraficoMovil {

	private final static int VIDA_MAXIMA = 100;
	private int capacidadCarga, cantidadGasolina, cantidadVida;
	private ArrayList<Arma> armas;
	private ArrayList<Persona> liberados;

	public int getCapacidadCarga() {
		return capacidadCarga;
	}

	public void setCapacidadCarga(int capacidadCarga) {
		this.capacidadCarga = capacidadCarga;
	}

	public int getCantidadGasolina() {
		return cantidadGasolina;
	}

	public void setCantidadGasolina(int cantidadGasolina) {
		this.cantidadGasolina = cantidadGasolina;
	}

	public int getCantidadVida() {
		return cantidadVida;
	}

	public void setCantidadVida(int cantidadVida) {
		this.cantidadVida = cantidadVida;
	}

	public ArrayList<Arma> getArmas() {
		return armas;
	}

	public void setArmas(ArrayList<Arma> armas) {
		this.armas.addAll(armas);
	}

	public void agregarArma(Arma arma) {
		armas.add(arma);
	}

	public ArrayList<Persona> getLiberados() {
		return liberados;
	}

	public void setLiberados(ArrayList<Persona> liberados) {
		this.liberados = liberados;
	}

	public Helicoptero(Drawable drawable, View view, int capacidadCarga,
			int cantidadGasolina, int cantidaadVida) {
		super(drawable, view);
		this.capacidadCarga = capacidadCarga;
		this.cantidadGasolina = cantidadGasolina;
		this.cantidadVida = cantidaadVida;
		this.armas = new ArrayList<Arma>();
		this.liberados = new ArrayList<Persona>();
	}

	public Helicoptero(Drawable drawable, View view, int capacidadCarga,
			int cantidadGasolina, int cantidaadVida, ArrayList<Arma> armas,
			ArrayList<Persona> liberados) {
		super(drawable, view);
		this.capacidadCarga = capacidadCarga;
		this.cantidadGasolina = cantidadGasolina;
		this.cantidadVida = cantidaadVida;
		this.armas = new ArrayList<Arma>();
		this.armas.addAll(armas);
		this.liberados = new ArrayList<Persona>();
		this.liberados.addAll(liberados);
	}

	public Helicoptero(Drawable drawable, View view) {
		super(drawable, view);
		this.armas = new ArrayList<Arma>();
		this.liberados = new ArrayList<Persona>();
	}

	@Override
	public void move(int x, int y) {
		setPosY(y);
		setPosX(x);
	}

	public int porcentajeVida(){
		return (getCantidadVida() * 10) / 100;
	}
	
	@Override
	public void manejoVida(int cantVida, int tipCambio){
		switch (tipCambio) {
		case 1: // Aumneta cantidad de vida
			if (getCantidadVida() < VIDA_MAXIMA){
				setCantidadVida(getCantidadVida()+cantVida);
				if (getCantidadVida() > VIDA_MAXIMA){
					setCantidadVida(VIDA_MAXIMA);
				}
			}
			break;
		case 2: // Disminuye cantida de vida
			if (getCantidadVida() > 0){
				setCantidadVida(getCantidadVida()-cantVida);
				if (getCantidadVida() < 0){
					setCantidadVida(0);
				}
			}
			break;
		}
	}
}
