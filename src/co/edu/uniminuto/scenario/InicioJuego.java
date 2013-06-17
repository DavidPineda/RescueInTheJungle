package co.edu.uniminuto.scenario;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import co.edu.uniminuto.vista.MainJuego;
import co.edu.uniminuto.vista.R;

public class InicioJuego extends Activity {
	int progreso = 0;
	ProgressBar barraProgreso;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.introduccion);
		barraProgreso = (ProgressBar) findViewById(R.id.progressBar);
		new TareaSegundoPlano().execute();

	}

	public class TareaSegundoPlano extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			barraProgreso.setProgress(progreso);
			barraProgreso.setMax(30);
		}

		@Override
		protected Void doInBackground(Void... params) {
			int n = 0;
			for (progreso = n; progreso <= 30; progreso++) {
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {
				}

				publishProgress();
			}

			return null;
		}

		protected void onProgressUpdate(Void... values) {
			barraProgreso.setProgress(progreso);
		}

		protected void onPostExecute(Void result) {
			lanzarMainJuego();
		}
	}

	private void lanzarMainJuego() {
		Bundle extra = this.getIntent().getExtras();
		Intent intent = new Intent(this, MainJuego.class);
		intent.putExtra("nombre_jugador", extra.getString("nombre_jugador"));
		startActivity(intent);
		finish();

	}
}
