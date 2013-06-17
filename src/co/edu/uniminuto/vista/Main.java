package co.edu.uniminuto.vista;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import co.edu.uniminuto.clases.ReproductorSonido;

public class Main extends Activity {

	// Guardará un numero entero que será el numero de segundos que ha pasado
	// desde que hemos inicializado la aplicación.
	int progreso = 0;
	// Declaración de la barra de progreso.
	ProgressBar barraProgreso;
	// Declaración de atributo soundpool para reproducir el sonido
	ReproductorSonido reproductorSonido;
	private boolean saliracividad = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Toma toda la pantalla para el video juego
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		if (savedInstanceState != null) {
			progreso = savedInstanceState.getInt("barraprogreso");
		}

		// Relacionamos el la barra de progreso del layout con el de java
		barraProgreso = (ProgressBar) findViewById(R.id.progressBar);

		reproductorSonido = new ReproductorSonido(getApplicationContext());
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

		// Iniciamos la tarea en segundo plano, en este caso no necesitamos
		// pasarle nada.
		new TareaSegundoPlano().execute();
	}

	public boolean isSalirAcividad() {
		return saliracividad;
	}

	public void setSalirAcividad(boolean salir_acividad) {
		this.saliracividad = salir_acividad;
	}
	
	// Clase interna que extiende de AsyncTask
	public class TareaSegundoPlano extends AsyncTask<Void, Void, Void> {

		// Método que se ejecutará antes de la tarea en segundo plano,
		// normalmente utilizado para iniciar el entorno gráfico
		protected void onPreExecute() {
			// Ponemos la barra de progreso a 0 ó si se cambio de orientacion se
			// inicia donde iba
			barraProgreso.setProgress(progreso);
			// barraProgreso.setProgress(0);
			// El máximo de la barra de progreso son 50, de los 50 segundo que
			// va a durar la tarea en segundo plano.
			barraProgreso.setMax(50);
		}

		// Este método se ejecutará después y será el que ejecute el código en
		// segundo plano
		@Override
		protected Void doInBackground(Void... params) {
			// verificamos si hubo un cambio de orientacion en el movil
			if (progreso != 0) {
				int n = progreso;
				// Creamos un for de 1 a 50 que irá contando los segundos.
				for (progreso = n; progreso <= 50; progreso++) {

					// A mitad de carga suena la explosion
					if (progreso == 17) {
						reproductorSonido.play(
								reproductorSonido.getExplosion(), 1);
					}
					try {
						// Esto lo que hace es ralentizar este proceso un
						// segundo
						// (el tiempo que se pone entre paréntesis es en
						// milisegundos) tiene que ir entre try y catch
						Thread.sleep(80);
					} catch (InterruptedException e) {
					}
					// Actualizamos el progreso, es decir al llamar a este
					// proceso
					// en realidad estamos llamamos al método
					// onProgresssUpdate()
					publishProgress();
				}
			} else {
				// se ejecuta este cuando no hay cambio en la orientacion del
				// telefono
				for (progreso = 1; progreso <= 50; progreso++) {

					// A mitad de carga suena la explosion
					if (progreso == 17) {
						reproductorSonido.play(
								reproductorSonido.getExplosion(), 1);
					}
					try {
						// Esto lo que hace es ralentizar este proceso un
						// segundo
						// (el tiempo que se pone entre paréntesis es en
						// milisegundos) tiene que ir entre try y catch
						Thread.sleep(80);
					} catch (InterruptedException e) {
					}
					// Actualizamos el progreso, es decir al llamar a este
					// proceso
					// en realidad estamos llamamos al método
					// onProgresssUpdate()
					publishProgress();
				}
			}
			// Al llegar aquí, no devolvemos nada y acaba la tarea en segundo
			// plano y se llama al método onPostExecute().
			return null;
		}

		protected void onProgressUpdate(Void... values) {
			// Actualizamos la barra de progreso con los segundos que vayan.
			barraProgreso.setProgress(progreso);
		}

		// A este método se le llama cada vez que termine la tarea en segundo
		// plano.
		protected void onPostExecute(Void result) {
			lanzarMenu();
		}
	}

	/*
	 * Guardamos el estado actual de la acitvidad (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Guardar el estado del usuario actual del juego
		savedInstanceState.putInt("barraprogreso", progreso);
		reproductorSonido.unloadAll();

		// Siempre llame a la superclase para que pueda guardar el estado de
		// jerarquía de la vista
		super.onSaveInstanceState(savedInstanceState);
	}

	/*
	 * llamamos a este metodo para que cambie al menu y finalize el SoundPool y
	 * la actividad
	 */
	public void lanzarMenu() {
		if (isSalirAcividad() == false) {
			try {
				reproductorSonido.unloadAll();
				finish();
				Thread.sleep(28);
			} catch (Exception e) {
			}

		} else {
			reproductorSonido.unloadAll();
			finish();
			Intent i = new Intent(this, Menu.class);
			startActivity(i);
		}
	}

	/*
	 * Metodo se ecarga de validar los eventos que pueda ejercer el jugador
	 */

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		try {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				reproductorSonido.unloadAll();
				setSalirAcividad(false);
				finish();
				return true;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
