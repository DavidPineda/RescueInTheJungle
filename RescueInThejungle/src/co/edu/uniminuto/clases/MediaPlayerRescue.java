package co.edu.uniminuto.clases;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerRescue {
	private MediaPlayer mediaPlayer;
	private boolean estaSonando = false;
	private boolean enLoop = false;
	private boolean isplaying;

	private int posicion;

	public MediaPlayerRescue(Context ctx, int resID) {
		mediaPlayer = MediaPlayer.create(ctx, resID);

		mediaPlayer
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						estaSonando = false;
						if (enLoop) {
							mp.start();
						}
					}
				});
		mediaPlayer.start();

	}

	public boolean isEnLoop() {
		return enLoop;
	}

	public void setEnLoop(boolean enLoop) {
		this.enLoop = enLoop;
	}

	public boolean isIsplaying() {
		return isplaying;
	}

	public void setIsplaying(boolean isplaying) {
		this.isplaying = isplaying;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	/*
	 * metodo encargado de reproducir el sonido
	 */
	public synchronized void play() {
		if (estaSonando) {
			setIsplaying(true);
			return;
		}
		if (mediaPlayer != null) {
			estaSonando = true;
			mediaPlayer.start();
			setIsplaying(true);
		}
	}

	/*
	 * metodo encargado de parar el media player
	 */
	public synchronized void stop() {
		try {
			enLoop = false;
			if (estaSonando) {
				estaSonando = false;
				mediaPlayer.pause();
				setIsplaying(false);
			}
		} catch (Exception e) {
			System.err.println("Error");
		}
	}

	/*
	 * obtiene la posicion del sonido
	 */
	public int seeto() {
		try {
			posicion = mediaPlayer.getCurrentPosition();
			return posicion;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * metodo encargado de ejecutar el sonido en modo infinito
	 */

	public synchronized void loop() {

		if (!isIsplaying()) {
			mediaPlayer.start();
		}
		enLoop = true;
		estaSonando = true;
		mediaPlayer.seekTo(getPosicion());
		mediaPlayer.start();
		setIsplaying(true);
	}

	/*
	 * metodo encargado de destruir los objetos creados con el media player
	 */
	public void release() {
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
}