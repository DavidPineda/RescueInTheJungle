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
import android.widget.TextView;

public class Opciones extends Activity {

	ImageButton button_atras, button_estadisticas, button_sonido,
			button_Informacion, button_controles;
	TextView textView_estadisticas_informacion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.opciones);
		textView_estadisticas_informacion = (TextView) findViewById(R.id.Estadisticas_Informacion);
		button_atras = (ImageButton) findViewById(R.id.Boton_Atras);
		button_estadisticas = (ImageButton) findViewById(R.id.boton_estadisticas);
		button_sonido = (ImageButton) findViewById(R.id.boton_conf_sonido);
		button_Informacion = (ImageButton) findViewById(R.id.boton_informacion);
		button_controles = (ImageButton) findViewById(R.id.boton_controles);

		button_atras.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				regresar();
			}
		});

		button_estadisticas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mostrarEstadisticas();
			}
		});

		button_Informacion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mostrarInformacion();
			}
		});

		button_sonido.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lanzarconfiguracionsonido();
			}
		});
	}

	public void regresar() {
		startActivity(new Intent(this, Menu.class));
		finish();
	}

	public void lanzarconfiguracionsonido() {
		startActivity(new Intent(this, ManejadorSonido.class));
	}

	public void mostrarEstadisticas() {
		textView_estadisticas_informacion.setText("");
		textView_estadisticas_informacion
				.append("En este scrollview  se mostraran los juagadores con las estadistica de los "
						+ "jugadores POR EL MOMENTO ESTARA EN CONSTRUCION HASTA COMPLETAR EL MODULO QUE LLEVARA ESTA INFO");
	}

	public void mostrarInformacion() {
		textView_estadisticas_informacion.setText("");
		textView_estadisticas_informacion
				.append("“Rescue in the jungle” consistió en el estudio, análisis y puesta en práctica el desarrollo de"
						+ "  un videojuego 2D sobre la plataforma android, y las herramientas y conceptos disponibles que se utilizaron para "
						+ "desarrollar esta aplicación que funcionará con la plataforma android 2.2 en adelante");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			regresar();
			return true;
		}
		return false;
	}

}
