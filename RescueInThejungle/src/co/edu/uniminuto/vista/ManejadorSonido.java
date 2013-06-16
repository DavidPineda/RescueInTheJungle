package co.edu.uniminuto.vista;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import co.edu.uniminuto.clases.ReproductorSonido;

public class ManejadorSonido extends Activity {

	ReproductorSonido reproductorSonido;
	OnSeekBarChangeListener barChange;
	ImageButton b_atras, b_iniciar_conf_helicoptero, b_iniciar_conf_explosion,
			b_iniciar_conf_disparo;
	int helicoptero;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Toma toda la pantalla para el video juego
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.configuracion_sonido);

		reproductorSonido = new ReproductorSonido(getApplicationContext());
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

		b_atras = (ImageButton) findViewById(R.id.Boton_Atras_conf_sonido);
		b_iniciar_conf_helicoptero = (ImageButton) findViewById(R.id.sonido_conf_helicoptero);
		b_iniciar_conf_disparo = (ImageButton) findViewById(R.id.sonido_conf_disparo);
		b_iniciar_conf_explosion = (ImageButton) findViewById(R.id.sonido_conf_explosion);

		// cargar la configuracion de las barras de progreso

		barChange = new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				switch (seekBar.getId()) {
				case R.id.VolumenBar1:
					reproductorSonido.setVolume((float) progress / 100.0f);
					break;

				case R.id.EquilibrarBar:
					reproductorSonido
							.setBalanceProgress((float) progress / 100.0f);
					break;

				case R.id.AcelerarBar:
					reproductorSonido.setSpeed((float) progress / 100.0f);
					break;
				}
			}
		};

		SeekBar sb;
		sb = (SeekBar) findViewById(R.id.AcelerarBar);
		sb.setOnSeekBarChangeListener(barChange);

		sb = (SeekBar) findViewById(R.id.EquilibrarBar);
		sb.setOnSeekBarChangeListener(barChange);

		sb = (SeekBar) findViewById(R.id.VolumenBar1);
		sb.setOnSeekBarChangeListener(barChange);

		b_atras.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				reproductorSonido.unloadAll();
				lanzaropciones();
			}
		});

		b_iniciar_conf_helicoptero.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				iniciarconfiguracion(1);
			}
		});

		b_iniciar_conf_disparo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				iniciarconfiguracion(2);
			}
		});

		b_iniciar_conf_explosion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				iniciarconfiguracion(3);
			}
		});
	}

	public void lanzaropciones() {
		if (reproductorSonido.getRate() == 1.0
				&& reproductorSonido.getMasterVolume() == 1.0
				&& reproductorSonido.getLeftVolume() == 1.0
				&& reproductorSonido.getRightVolume() == 1.0
				&& reproductorSonido.getBalance() == 0.5) {
            reproductorSonido.unloadAll();
			finish();
		} else {
			reproductorSonido.escribirArchivo();
			reproductorSonido.unloadAll();
			finish();
		}
	}

	public void iniciarconfiguracion(int id) {
		if (id == 1)
			reproductorSonido.play(reproductorSonido.getHelicoptero(), 0);
		if (id == 2)
			reproductorSonido.play(reproductorSonido.getAmetralladora(), 0);
		if (id == 3)
			reproductorSonido.play(reproductorSonido.getExplosion(), 0);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			lanzaropciones();
			return true;
		}
		return false;
	}
}