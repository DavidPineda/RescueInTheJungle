package co.edu.uniminuto.vista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class Multijugador extends Activity {

	ImageButton b_atras;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Toma toda la pantalla para el video juego
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.multijugador);
		
		b_atras = (ImageButton) findViewById(R.id.Boton_Atras);
		
		b_atras.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				regresar();
			}
		});
	}
	
	public void regresar() {
		startActivity(new Intent(this, Menu.class));
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	finish();
	    	startActivity(new Intent(this, Menu.class));
	    	return true;
	        }
	    return false;
	    }
}
