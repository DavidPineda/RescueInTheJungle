package co.edu.uniminuto.clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Environment;
import android.util.Log;
import co.edu.uniminuto.vista.R;

public class ReproductorSonido {

	FileInputStream fileInputStream;
	private Context pContext;
	private SoundPool sndPool;
	private float rate;
	private float masterVolume;
	private float leftVolume;
	private float rightVolume;
	private float balance;
	private boolean loaded;
	private int helicoptero = 0;
	private int explosion = 0;
	private int ametralladora = 0;
	public String nombre_archivo = "sonido.txt";
	ArrayList<String> sonido;
	
	
	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	// Constructor, inicia la configuracion y carga los sonidos que se van a
	// reproducir

	public ReproductorSonido(Context appContext) {
		// sndPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);
		sndPool = new SoundPool(50, AudioManager.STREAM_MUSIC, 100);
		pContext = appContext;
		sndPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				loaded = true;
			}
		});
		
		this.rate = 1.0f;
		this.masterVolume = 1.0f;
		this.leftVolume = 1.0f;
		this.rightVolume = 1.0f;
		this.balance = 0.5f;

		this.explosion = sndPool.load(pContext, R.raw.explosion, 1);
		this.helicoptero = sndPool.load(pContext, R.raw.helicoptero_juego, 0);
		this.ametralladora = sndPool.load(pContext, R.raw.ametralladora, 1);
	}

	// Load up a sound and return the id
	public int load(int sound_id) {
		return sndPool.load(pContext, sound_id, 1);
	}

	// Play a sound
	public void play(int sound_id, int loop) {
		sndPool.play(sound_id, getLeftVolume(), getRightVolume(), 1, loop,getRate());
	}

	/*
	 * Configura el volumen con que se reprocira el volumen
	 */
	public void setVolume(float vol) {

		setMasterVolume(vol);

		if (getBalance() < 1.0f) {
			setLeftVolume(getMasterVolume());
			setRightVolume(getMasterVolume() * getBalance());
		} else {
			setRightVolume(getMasterVolume());
			setLeftVolume(getMasterVolume() * (2.0f - getBalance()));
		}

	}

	/*
	 * Configura la velocidad del sonido conque se reproducira
	 */
	public void setSpeed(float speed) {
		setRate(speed);
		// Speed of zero is invalid
		if (getRate() < 0.01f) {
			setRate(0.01f);
		}
		// Speed has a maximum of 2.0
		if (getRate() > 2.0f) {
			setRate(2.0f);
		}

	}

	/*
	 * Configura el balance de los parlantes
	 */
	public void setBalanceProgress(float balVal) {

		setBalance(balVal);
		setVolume(getMasterVolume());
	}

	public int getHelicoptero() {
		return helicoptero;
	}

	public int getExplosion() {
		return explosion;
	}

	public void setExplosion(int explosion) {
		this.explosion = explosion;
	}

	public void setHelicoptero(int helicoptero) {
		this.helicoptero = helicoptero;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getMasterVolume() {
		return masterVolume;
	}

	public void setMasterVolume(float masterVolume) {
		this.masterVolume = masterVolume;
	}

	public float getLeftVolume() {
		return leftVolume;
	}

	public void setLeftVolume(float leftVolume) {
		this.leftVolume = leftVolume;
	}

	public float getRightVolume() {
		return rightVolume;
	}

	public void setRightVolume(float rightVolume) {
		this.rightVolume = rightVolume;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public int getAmetralladora() {
		return ametralladora;
	}

	public void setAmetralladora(int ametralladora) {
		this.ametralladora = ametralladora;
	}

	// Free ALL the things!
	public void unloadAll() {
		sndPool.release();
	}

	public void pause(int id_sonido) {
		sndPool.pause(id_sonido);
	}

	public void resume(int id_sonido) {
		sndPool.resume(id_sonido);
	}

	public void stop(int id_sonido) {
		sndPool.stop(id_sonido);
	}

	/*
	 * mira si si la memoria se puede escribir
	 */
	public static boolean isExternalStorageReadOnly() {
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
			return true;
		}
		return false;
	}

	/*
	 * buscar si la memoria esta puesta
	 */
	public static boolean isExternalStorageAvailable() {
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
			return true;
		}
		return false;
	}

	
	public void escribirArchivo() {

		try {
			if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
				File file = new File(Environment.getExternalStorageDirectory(),
						nombre_archivo);
				OutputStreamWriter outw = new OutputStreamWriter(
						new FileOutputStream(file));
				outw.write(getRate() + "\n");
				outw.write(getMasterVolume() + "\n");
				outw.write(getLeftVolume() + "\n");
				outw.write(getRightVolume() + "\n");
				outw.write(getBalance() + "\n");
				outw.close();
			}
		} catch (Exception e) {
		}
	}

	public void leerArchivo() {
		sonido = new ArrayList<String>();
		try {
			File ruta_sd = Environment.getExternalStorageDirectory();
			File f = new File(ruta_sd.getAbsolutePath(), nombre_archivo);
			BufferedReader fin = new BufferedReader(new InputStreamReader(
					new FileInputStream(f)));
			String sonidos = fin.readLine();
			int i = 0;
			while (i < 5) {
				sonido.add(sonidos);
				sonidos = fin.readLine();
				i++;
			}
			fin.close();
		} catch (Exception ex) {
			Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
		}

	}

	public void cargarConfiguracionSonido() {
		float a;
		for (int i = 0; i < 5; i++) {

			if (i == 0) {
				a = Float.parseFloat(sonido.get(i));
				setRate(a);
			}
			if (i == 1) {
				a = Float.parseFloat(sonido.get(i));
				setMasterVolume(a);
			}
			if (i == 2) {
				a = Float.parseFloat(sonido.get(i));
				setLeftVolume(a);
			}
			if (i == 3) {
				a = Float.parseFloat(sonido.get(i));
				setRightVolume(a);
			}
			if (i == 4) {
				a = Float.parseFloat(sonido.get(i));
				setBalance(a);
			}
		}
	}
	
}
