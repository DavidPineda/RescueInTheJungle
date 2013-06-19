package co.edu.uniminuto.mundo;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.view.View;
import co.edu.uniminuto.clases.Grafico;

public class Casa extends Grafico  {
	
	private ArrayList<Casa> casas1;
	
	public ArrayList<Casa> getCasas() {
		return casas1;
	}
	
	public void setCasas(ArrayList<Casa> casas) {
		this.casas1.addAll(casas);
	}
	
	public Casa(Drawable drawable, View view, ArrayList<Casa> armas) {
		super(drawable, view);
		this.casas1 = new ArrayList<Casa>();
		this.casas1.addAll(casas1);
	}
	
	public Casa(Drawable drawable, View view){
		super(drawable, view);
	}
	

}
