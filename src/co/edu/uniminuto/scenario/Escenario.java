package co.edu.uniminuto.scenario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import co.edu.uniminuto.vista.R;


public class Escenario {

	@SuppressWarnings("unused")
	private Bitmap vidas, escenario_1, escenario_2, escenario_3, escenario_4, game_over;
	private int posicionX_1 = 0, posicionY;
	int posx = 0;
	private Resources admRecursos;

	public Escenario(Resources admRecursos) {

		this.admRecursos = admRecursos;

	}

	public void cargarEscenario(int nivel) {
		this.posicionX_1 = 0;

		vidas = BitmapFactory.decodeResource(admRecursos,
				R.drawable.vidashelicoptero);
		
		game_over = BitmapFactory.decodeResource(admRecursos,
				R.drawable.gameover);
		if (nivel == 1) {
			escenario_1 = BitmapFactory.decodeResource(admRecursos,
					R.drawable.escenario_1);
			posicionY = -10;
		}
	}

	public int getPosicionX_1() {
		return posicionX_1;
	}

	public void setPosicionX_1(int posicionX_1) {
		this.posicionX_1 = posicionX_1;
	}

	public void draw(Canvas canvas, Paint p, int screenWidth, int screenHeight,
			int nivel) {
		int n = 0;
		int posicion_vida = 120;
		if (nivel == 1) {
			escenario_1 = Bitmap.createScaledBitmap(escenario_1, 3000,
					screenHeight, true);
			canvas.drawBitmap(escenario_1, posicionX_1, 0, p);
			while (n < 3) {
				canvas.drawBitmap(vidas, posicion_vida += 20, 10, p);
				n++;
			}
		} else if (nivel == 50) {
			game_over = Bitmap.createScaledBitmap(game_over, screenWidth,
					screenHeight, true);
			canvas.drawBitmap(game_over, 0, 0, p);
		}

	}

	public void mover(int incrementoX, boolean movimientox) {
		// hacia la derecha
		if (movimientox) {
			if (posicionX_1 > -1610) {
				if (posicionX_1 == -1610) {
					posicionX_1 += incrementoX;
				} else {
					posicionX_1 -= incrementoX;
					setPosicionX_1(posicionX_1);
				}
			}
		}
		// hacia la izquierda
		if (!movimientox) {
			if (posicionX_1 == 0) {

			} else {
				posicionX_1 += incrementoX;
				setPosicionX_1(posicionX_1);
			}
		}
	}

	
	public void escenarioNull(int nivel) {
		if (nivel == 1) {
			escenario_1 = null;
		} else if (nivel == 2) {
			escenario_2 = null;
		} else if (nivel == 3) {
			escenario_3 = null;
		} else if (nivel == 4) {
			escenario_4 = null;
		} else if ( nivel == 50){
			game_over = null;
		}
	}
	
	/*
	 * public int getUbicacionCasaPosY(int altoPant) { int ubicacion = 0;
	 * 
	 * if (altoPant > 260 && altoPant < 350) { ubicacion = ((altoPant * 76) /
	 * 100); return ubicacion; } else if (altoPant > 351 && altoPant < 420) {
	 * ubicacion = ((altoPant * 73) / 100); return ubicacion; } else if
	 * (altoPant > 421 && altoPant < 600) { ubicacion = ((altoPant * 74) / 100);
	 * return ubicacion; } else if (altoPant > 601 && altoPant < 800) {
	 * ubicacion = ((altoPant * 75) / 100); return ubicacion; } return 0; }
	 * 
	 * public int getUbicacionCasasX(int anchoPant) {
	 * 
	 * if (anchoPant > 400 && anchoPant < 500) { posx = (int) Math.random() *
	 * 100 + 130; return posx += getPosicionX_1();
	 * 
	 * } else if (anchoPant > 500 && anchoPant < 750) { posx = (int)
	 * Math.random() * 120 + 140; return posx += getPosicionX_1();
	 * 
	 * } else if (anchoPant > 750 && anchoPant < 1200) { posx = (int)
	 * Math.random() * 130 + 155; return posx += getPosicionX_1(); } return 0; }
	 */
}