package co.edu.uniminuto.mundo;

import android.graphics.drawable.Drawable;
import android.view.View;
import co.edu.uniminuto.clases.Grafico;
import co.edu.uniminuto.clases.GraficoMovil;

public class BotonAnalogo extends Grafico implements GraficoMovil {

	public BotonAnalogo(Drawable drawable, View view) {
		super(drawable, view);
	}

	@Override
	public void move(int x, int y) {
		setPosY(getPosY() + y);
		setPosX(getPosX() + x);
	}

	public void retornar(int posOrigX, int posOrigY) {
		setPosY(posOrigY);
		setPosX(posOrigX);
	}

	@Override
	public void manejoVida(int cantVida, int tipCambio) {
		// TODO Auto-generated method stub
		
	}
}
