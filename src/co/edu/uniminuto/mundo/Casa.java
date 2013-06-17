package co.edu.uniminuto.mundo;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.view.View;
import co.edu.uniminuto.clases.Grafico;
import co.edu.uniminuto.clases.GraficoMovil;

public class Casa extends Grafico implements GraficoMovil {
	
	enum TipoCasa{
		cambuche, torre, casas;
	}
	
	private TipoCasa tipo;
	private boolean estado;
	private ArrayList<Casa> casas1;
	
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public TipoCasa getTipo() {
		return tipo;
	}

	public void setTipo(TipoCasa tipo) {
		this.tipo = tipo;
	}
	
	public ArrayList<Casa> getCasas() {
		return casas1;
	}
	
	public void setCasas(ArrayList<Casa> casas) {
		this.casas1.addAll(casas);
	}
	
	public Casa(Drawable drawable, View view, boolean estado,ArrayList<Arma> armas) {
		super(drawable, view);
		this.estado = estado;
		this.casas1 = new ArrayList<Casa>();
		this.casas1.addAll(casas1);
	}
	
	public Casa(Drawable drawable, View view, boolean estado){
		super(drawable, view);
		this.estado = estado;
	}
	
	public Casa(Drawable drawable, View view, TipoCasa tipoCasa){
		super(drawable, view);
		this.tipo = tipoCasa;
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
