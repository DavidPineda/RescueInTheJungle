package co.edu.uniminuto.vista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import co.edu.uniminuto.clases.MediaPlayerRescue;

public class Menu extends Activity implements OnClickListener {
	MediaPlayerRescue mediaPlayerRescue;
	int posicion = 0;
	private int sonidoHelicoptero = 2;
	private ImageButton b_cont_volumen;
	private Button b_nuevo_juego;
	private Button b_continuar;
	private Button b_multijugador;
	private Button b_opciones;
	private ImageButton b_salir;
	private boolean estadoBtnSonido = true;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Toma toda la pantalla el fuul screan para el video juego
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.menu);

		mediaPlayerRescue = getAudioConstante(R.raw.helicoptero_menu);

		if (savedInstanceState != null) {
			estadoBtnSonido = savedInstanceState.getBoolean("estadoBtnSonido");
			if (estadoBtnSonido) {
				posicion = savedInstanceState.getInt("sonido");
				mediaPlayerRescue.setPosicion(posicion);
				mediaPlayerRescue.loop();
			}else{
				mediaPlayerRescue.stop();
			}
		}else{
			mediaPlayerRescue.loop();
		}

		// Declaracion de botones
		b_nuevo_juego = (Button) findViewById(R.id.Boton_Juego_Nuevo);
		b_continuar = (Button) findViewById(R.id.Boton_Continuar);
		b_multijugador = (Button) findViewById(R.id.Boton_Multijugador);
		b_opciones = (Button) findViewById(R.id.Boton_Opciones);
		b_salir = (ImageButton) findViewById(R.id.Boton_Salir);
		if (estadoBtnSonido) {
			b_cont_volumen = (ImageButton) findViewById(R.id.Boton_Control_Volumen);
		} else {
			
			b_cont_volumen = (ImageButton) findViewById(R.id.Boton_Control_Volumen_Apagado);
		}
		b_cont_volumen.setVisibility(View.VISIBLE);
		b_cont_volumen.setOnClickListener(this);

		b_nuevo_juego.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lanzaNuevoJugador();
			}
		});

		b_continuar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lanzarContinuarJuego();
			}
		});

		b_multijugador.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lanzarMultijugador();
			}
		});

		b_opciones.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lanzaropciones();
			}
		});

		b_salir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mediaPlayerRescue.release();
				finish();
			}
		});
	}

	public int isSonidoHelicoptero() {
		return sonidoHelicoptero;
	}

	public void setSonidoHelicoptero(int sonidoHelicoptero) {
		this.sonidoHelicoptero = sonidoHelicoptero;
	}

	/*
	 * Se retorna la instancia de la clase MediaplayerRescue con su metodo
	 * getBaseContex para su destruccion con el onDestroy
	 */
	protected MediaPlayerRescue getAudioConstante(int id) {
		return new MediaPlayerRescue(getBaseContext(), id);
	}

	public void lanzaNuevoJugador() {
		Intent i = new Intent(this, JugadorNuevo.class);
		startActivity(i);
		mediaPlayerRescue.release();
		finish();
	}

	public void lanzarContinuarJuego() {
		startActivity(new Intent(this, MostrarUsuarios.class));
		mediaPlayerRescue.release();
		finish();
	}

	public void lanzarMultijugador() {
		startActivity(new Intent(this, Multijugador.class));
		mediaPlayerRescue.release();
		finish();
	}

	public void lanzaropciones() {
		startActivity(new Intent(this, Opciones.class));
		mediaPlayerRescue.release();
		finish();
	}

	/*
	 * se captura los eventos de los botones de android (non-Javadoc)
	 */
	@Override
	public void onClick(View v) {
		if (estadoBtnSonido) { 
			mediaPlayerRescue.stop();
			estadoBtnSonido = false;
			b_cont_volumen.setVisibility(View.INVISIBLE);
			b_cont_volumen = (ImageButton) findViewById(R.id.Boton_Control_Volumen_Apagado);
			b_cont_volumen.setVisibility(View.VISIBLE);
			b_cont_volumen.setOnClickListener(this);
		} else {
			mediaPlayerRescue.loop();
			estadoBtnSonido = true;
			b_cont_volumen.setVisibility(View.INVISIBLE);
			b_cont_volumen = (ImageButton) findViewById(R.id.Boton_Control_Volumen);
			b_cont_volumen.setVisibility(View.VISIBLE);
			b_cont_volumen.setOnClickListener(this);
		}
	}

	/*
	 * intercepta el evento de la techa back (atras)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			mediaPlayerRescue.release();
			finish();
			return true;
		}
		return false;
	}

	/*
	 * Guardamos el estado actual de la acitvidad (non-Javadoc)
	 */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt("sonido", mediaPlayerRescue.seeto());
		savedInstanceState.putBoolean("estadoBtnSonido", estadoBtnSonido);
		mediaPlayerRescue.release();
		super.onSaveInstanceState(savedInstanceState);
	}

}
