package co.edu.uniminuto.clases;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import co.edu.uniminuto.vista.VistaJuego;

public class VistaJuegoThread extends Thread {

	private SurfaceHolder sh;
	private VistaJuego view;
	private boolean run;
	private static final long FPS = 10;

	public VistaJuegoThread(SurfaceHolder sh, VistaJuego view) {
		this.sh = sh;
		this.view = view;
		run = false;
	}

	public void setRunning(boolean run) {
		this.run = run;
	}

	public void run() {
		long framePS = 1000 / FPS;
		long tiempoInicial;
		long tiempoDormir;
		while (run) {
			Canvas canvas = null;
			tiempoInicial = System.currentTimeMillis();
			try {
				canvas = sh.lockCanvas(null);
				synchronized (sh) {
					view.moverHelicoptero();
					view.manejoDisparo();
					view.verificaAtaque();
					view.onDraw(canvas);
				}
			} finally {
				if (canvas != null)
					sh.unlockCanvasAndPost(canvas);
			}
			tiempoDormir = framePS - (System.currentTimeMillis() - tiempoInicial);
			try {
				if (tiempoDormir > 0) {
					sleep(tiempoDormir);
				} else {
					sleep(10);
				}
			} catch (Exception e) {
			}
		}
	}
}
