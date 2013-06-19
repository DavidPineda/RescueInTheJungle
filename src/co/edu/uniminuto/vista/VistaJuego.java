package co.edu.uniminuto.vista;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import co.edu.uniminuto.clases.Coordenada;
import co.edu.uniminuto.clases.Grafico;
import co.edu.uniminuto.clases.ReproductorSonido;
import co.edu.uniminuto.clases.ThreadCrometro;
import co.edu.uniminuto.clases.VistaJuegoThread;
import co.edu.uniminuto.mundo.Arma;
import co.edu.uniminuto.mundo.Arma.Alcance;
import co.edu.uniminuto.mundo.Arma.CapacidadDano;
import co.edu.uniminuto.mundo.Arma.Tipo;
import co.edu.uniminuto.mundo.BaseAnalogo;
import co.edu.uniminuto.mundo.BotonAnalogo;
import co.edu.uniminuto.mundo.Casa;
import co.edu.uniminuto.mundo.Enemigo;
import co.edu.uniminuto.mundo.Enemigo.alcanceDeAtaque;
import co.edu.uniminuto.mundo.Helicoptero;
import co.edu.uniminuto.mundo.Jugador;
import co.edu.uniminuto.mundo.ObjetosPuntuacion;
import co.edu.uniminuto.mundo.Persona;
import co.edu.uniminuto.mundo.SeleccionArma;
import co.edu.uniminuto.mundo.SeleccionArma.Sentido;
import co.edu.uniminuto.persistencia.DbSqliteRescueJungle;
import co.edu.uniminuto.scenario.Escenario;

public class VistaJuego extends SurfaceView implements SurfaceHolder.Callback {

	ReproductorSonido reproductorSonido;
	private VistaJuegoThread paintThread;
	private ArrayList<Arma> armasEnPantalla;
	private ArrayList<Enemigo> enemigosEnPantalla;
	private ArrayList<Arma> disparosEnemigo;

	private Grafico armaActiva, prisionero;
	private Grafico elementoActivo = null;
	private Grafico btnAnalogo, ctrAnalogo, rescueHelicoptero, btnDisparador;
	private Grafico armaIzquierda, armaDerecha, recuadro, recuadroPrisionero;
	private Grafico p_reloj, vidaHelicoptero, enemigo, casas;
	private ThreadCrometro tCrometro;
	private Jugador jugador;

	private Arma armaDisparada;
	private int origenY, origenX, armaPos;
	private int anchoPant, altoPant;
	private int posOrigX, posOrigY;
	private int x, y;
	private int angulo;
	private int direccion = 0;
	private int cantidadLiberados;
	private int nivel;

	private boolean moviendo = false;
	private boolean disparoActivo = false;
	private boolean gameover = false;

	private Context context;

	private Rect rec;
	private Paint p;
	private Escenario escenario;

	public VistaJuego(Context context, String nombre_jugador) {
		super(context);
		getHolder().addCallback(this);
		this.context = context;
		reproductorSonido = new ReproductorSonido(context);
		p = new Paint();
		escenario = new Escenario(getResources());
		cargarNivel(nombre_jugador);
		this.jugador = cargarAvancesJugador(nombre_jugador);
		armaPos = 0;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		anchoPant = w;
		altoPant = h;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		tCrometro = new ThreadCrometro();
		escenario.cargarEscenario(nivel);
		Drawable analogo, control, helicoptero, reloj;
		reloj = context.getResources().getDrawable(R.drawable.reloj);
		analogo = context.getResources().getDrawable(
				R.drawable.onscreen_control_knob);
		control = context.getResources().getDrawable(
				R.drawable.onscreen_control_base);
		helicoptero = context.getResources()
				.getDrawable(R.drawable.helicoptero);
		ctrAnalogo = new BaseAnalogo(control, this);
		btnAnalogo = new BotonAnalogo(analogo, this);
		ctrAnalogo.setPosX(0);
		ctrAnalogo.setPosY(altoPant - control.getIntrinsicHeight());
		btnAnalogo.setPosX(control.getIntrinsicWidth() / 4);
		btnAnalogo.setPosY(altoPant
				- (btnAnalogo.getAlto() + (btnAnalogo.getAlto() / 2)));
		posOrigX = btnAnalogo.getPosX();
		posOrigY = btnAnalogo.getPosY();
		btnDisparador = new BotonAnalogo(analogo, this);
		btnDisparador.setPosX(anchoPant - (control.getIntrinsicWidth())
				+ btnDisparador.getAncho() / 2);
		btnDisparador.setPosY(altoPant
				- (btnAnalogo.getAlto() + (btnAnalogo.getAlto() / 2)));
		rescueHelicoptero = new Helicoptero(helicoptero, this, 10, 10, 100);
		rescueHelicoptero.setPosX(anchoPant / 3);
		rescueHelicoptero.setPosY(altoPant / 3);
		rescueHelicoptero.setAngulo(0);
		rescueHelicoptero.setRotacion(3);
		vidaHelicoptero();

		p_reloj = new ObjetosPuntuacion(reloj, this);
		p_reloj.setPosX(((anchoPant / 110) * 2));
		p_reloj.setPosY(((altoPant / 60) * 1));

		rec = new Rect((posOrigX - 25), (posOrigY - 25), (posOrigX
				+ btnAnalogo.getAncho() + 25),
				(posOrigY + btnAnalogo.getAlto() + 25));
		armaDisparada = null;
		armasEnPantalla = new ArrayList<Arma>();
		armarHelicoptero();
		armarRecuadro();
		enemigosEnPantalla = new ArrayList<Enemigo>();
		disparosEnemigo = new ArrayList<Arma>();
		cargarEnemigos();
		paintThread = new VistaJuegoThread(getHolder(), this);
		paintThread.setRunning(true);
		paintThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		paintThread.setRunning(false);
		while (retry) {
			try {
				paintThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	public void agregarEnemigos(Enemigo enemigo) {
		this.enemigosEnPantalla.add(enemigo);
	}

	public void eliminarEnemigo(Enemigo enemigo) {
		this.enemigosEnPantalla.remove(enemigo);
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {

		DisplayMetrics metrics = new DisplayMetrics();
		int screenHeight = metrics.heightPixels;
		int screenWidth = metrics.widthPixels;
		screenWidth = getWidth();
		screenHeight = getHeight();

		if (!gameover) {
			Helicoptero h = (Helicoptero) rescueHelicoptero;
			if (tCrometro.getMinutos() != jugador.getTimeNivel()
			// && jugador.getVidaHelicoptero() >= 0) {
					&& h.getCantidadVida() >= 0) {
				escenario.draw(canvas, p, screenWidth, screenHeight,
						jugador.getNivel());
				p_reloj.dibujarGrafico(canvas);
				vidaHelicoptero.dibujarGrafico(canvas);
				canvas.drawText(tCrometro.runTime(jugador.getTimeNivel()),
						((anchoPant / 100) * 2), ((altoPant / 60) * 7), p);

				ctrAnalogo.dibujarGrafico(canvas);
				btnAnalogo.dibujarGrafico(canvas);
				btnDisparador.dibujarGrafico(canvas);
				recuadroPrisionero.dibujarGrafico(canvas);
				prisionero.dibujarGrafico(canvas);
				canvas.drawText(
						cantidadLiberados + "",
						recuadroPrisionero.getPosX()
								+ (recuadroPrisionero.getAncho() / 2)
								- (prisionero.getAncho() / 2),
						prisionero.getPosY() + p.getTextSize() * 2, p);
				rescueHelicoptero.dibujarGrafico(canvas);
				armaIzquierda.dibujarGrafico(canvas);
				recuadro.dibujarGrafico(canvas);
				armaDerecha.dibujarGrafico(canvas);
				armaActiva.dibujarGrafico(canvas);

				if (disparoActivo) {
					for (int i = 0; i < armasEnPantalla.size(); i++) {
						armaDisparada = armasEnPantalla.get(i);
						armaDisparada.dibujarGrafico(canvas);
					}
				}
				for (int i = 0; i < enemigosEnPantalla.size(); i++) {
					enemigo = enemigosEnPantalla.get(i);
					enemigo.dibujarGrafico(canvas);
				}
				for (int i = 0; i < disparosEnemigo.size(); i++) {
					armaDisparada = disparosEnemigo.get(i);
					armaDisparada.dibujarGrafico(canvas);
				}
				/* se pintan las casas */
				if (escenario.getPosicionX_1() >= -180 && escenario.getPosicionX_1() > -600) {
					for (int i = 0; i < 3; i++) {
						casas = cargarCasas().get(i);
						casas.setPosX(escenario.getPosicionX_1()
								+ casas.getPosX());
						casas.dibujarGrafico(canvas);
						casas = null;
					}
				}
				if (escenario.getPosicionX_1() <= -240 && escenario.getPosicionX_1() > -1100) {
					int desplazamiento = escenario.getPosicionX_1() + 370;
					for (int i = 3; i < 6; i++) {
						casas = cargarCasas().get(i);
						casas.setPosX(desplazamiento
								+ casas.getPosX());
						casas.dibujarGrafico(canvas);
						casas = null;
					}
				}
				if (escenario.getPosicionX_1() <= -760 && escenario.getPosicionX_1() > -1605) {
					int desplazamiento = escenario.getPosicionX_1() + 900;
					for (int i = 6; i < cargarCasas().size(); i++) {
						casas = cargarCasas().get(i);
						casas.setPosX(desplazamiento
								+ casas.getPosX());
						casas.dibujarGrafico(canvas);
						casas = null;
					}
				}	
				/************/
			} else {
				gameover = true;
				tCrometro.setEstado(true);
			}
		} else {
			tCrometro.segundosCambioNivel();
			tCrometro.setMinutos(00);
			tCrometro.setCentesimas(00);
			escenario.escenarioNull(nivel);
			tCrometro.setDetenido(true);
			tCrometro.setPausado(false);
			if (tCrometro.getSegun() <= 2) {
				escenario.draw(canvas, p, screenWidth, screenHeight, 50);
			} else {
				escenario.cargarEscenario(jugador.getNivel());
				// jugador.restablecerVidaHelicoptero(200);
				Helicoptero h = (Helicoptero) rescueHelicoptero;
				h.setCantidadVida(100);
				vidaHelicoptero();
				// escenario.escenarioNull(50);
				tCrometro.setDetenido(false);
				tCrometro.setPausado(true);
				tCrometro.setEstado(false);
				tCrometro.setMinutos(00);
				tCrometro.setCentesimas(00);
				tCrometro.setSegundos(0);
				tCrometro.setCente(0);
				tCrometro.setSegun(0);
				// updateNivelCero(nivel);
				// obtenerDatosNivel(nivel);
				gameover = false;
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Retorna el puntero del dedo que genero el evento
		int pointerID = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;

		y = (int) event.getY();
		x = (int) event.getX();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// hemos pulsado
			if (btnAnalogo.getRectElemento().contains(x, y)) {
				elementoActivo = btnAnalogo;
				origenY = y;
				origenX = x;
				moviendo = true;
			}
			if (btnDisparador.getRectElemento().contains(x, y)) {
				disparoActivo = true;
				Arma s = (Arma) armaActiva;
				armaEnPantalla(s.getTipo());
			}
			if (armaIzquierda.getRectElemento().contains(x, y)) {
				Arma s = (Arma) armaActiva;
				armaSeleccionada(Sentido.izquierda, armaPos);
				if (s != armaActiva)
					armaPos--;
			}
			if (armaDerecha.getRectElemento().contains(x, y)) {
				Arma s = (Arma) armaActiva;
				armaSeleccionada(Sentido.derecha, armaPos);
				if (s != armaActiva)
					armaPos++;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			// hemos arrastrado
			if (elementoActivo != null) {
				BotonAnalogo r = (BotonAnalogo) elementoActivo;
				if (btnAnalogo.getRectElemento().contains(x, y)) {
					if (r.puedoMover(x - origenX, y - origenY, rec)) {
						r.move(x - origenX, y - origenY);
					}
				} else {
					r.retornar(posOrigX, posOrigY);
					moviendo = false;
				}
			}
			origenY = y;
			origenX = x;
			break;
		case MotionEvent.ACTION_POINTER_2_DOWN:
			if (pointerID == 1) {
				if (btnDisparador.getRectElemento().contains(
						(int) event.getX(pointerID),
						(int) event.getY(pointerID))) {
					disparoActivo = true;
					Arma s = (Arma) armaActiva;
					armaEnPantalla(s.getTipo());
				}
				if (armaIzquierda.getRectElemento().contains(
						(int) event.getX(pointerID),
						(int) event.getY(pointerID))) {
					Arma s = (Arma) armaActiva;
					armaSeleccionada(Sentido.izquierda, armaPos);
					if (s != armaActiva)
						armaPos--;
				}
				if (armaDerecha.getRectElemento().contains(
						(int) event.getX(pointerID),
						(int) event.getY(pointerID))) {
					Arma s = (Arma) armaActiva;
					armaSeleccionada(Sentido.derecha, armaPos);
					if (s != armaActiva)
						armaPos++;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			// hemos levantado
			if (elementoActivo != null) {
				BotonAnalogo r = (BotonAnalogo) elementoActivo;
				r.retornar(posOrigX, posOrigY);
				elementoActivo = null;
				moviendo = false;
				Helicoptero h = (Helicoptero) rescueHelicoptero;
				angulo = 0;
				h.setAngulo(angulo);
			}
			break;
		}
		return true;
	}

	public void armarRecuadro() {
		Drawable recuadroArma, flechaCambioArma;
		Drawable bomba, misil, metralleta, prisionero_pantalla;
		recuadroArma = context.getResources().getDrawable(
				R.drawable.recuadro_armas);
		prisionero_pantalla = context.getResources().getDrawable(
				R.drawable.prisionero);
		recuadroPrisionero = new SeleccionArma(recuadroArma, this);
		recuadroPrisionero.setPosX(anchoPant / 2
				+ (recuadroPrisionero.getAncho() / 2));
		recuadroPrisionero.setPosY(10);
		prisionero = new Persona(prisionero_pantalla, this);
		prisionero.setPosX(recuadroPrisionero.getPosX()
				+ (recuadroPrisionero.getAncho() / 2)
				- (prisionero.getAncho() / 2));
		prisionero.setPosY(5);
		recuadro = new SeleccionArma(recuadroArma, this);
		recuadro.setPosX(anchoPant - (recuadro.getAncho() * 4));
		recuadro.setPosY(10);
		flechaCambioArma = context.getResources().getDrawable(
				R.drawable.cambio_arma_izquierda);
		armaIzquierda = new SeleccionArma(flechaCambioArma, this,
				Sentido.izquierda);
		flechaCambioArma = context.getResources().getDrawable(
				R.drawable.cambio_arma_derecha);
		armaDerecha = new SeleccionArma(flechaCambioArma, this, Sentido.derecha);
		armaDerecha.setPosX((recuadro.getPosX() + recuadro.getAncho()) + 7);
		armaDerecha.setPosY(10);
		armaIzquierda.setPosX(recuadro.getPosX() - recuadro.getAncho() - 7);
		armaIzquierda.setPosY(10);
		bomba = context.getResources().getDrawable(R.drawable.bomba_recuadro);
		misil = context.getResources().getDrawable(R.drawable.misil_recuadro);
		metralleta = context.getResources().getDrawable(
				R.drawable.metralleta_recuadro);
		SeleccionArma s = (SeleccionArma) recuadro;
		s.agregarImagen(new Arma(bomba, this, Tipo.bomba));
		s.agregarImagen(new Arma(metralleta, this, Tipo.metralla));
		s.agregarImagen(new Arma(misil, this, Tipo.misil));
		armaSeleccionada(Sentido.derecha, armaPos);
		armaPos++;
	}

	public void armaSeleccionada(Sentido sentido, int pos) {
		SeleccionArma s = (SeleccionArma) recuadro;
		armaActiva = s.imagenMostrar(sentido, pos);
		armaActiva.setPosX(recuadro.getPosX() + (recuadro.getAncho() / 2)
				- (armaActiva.getAncho() / 2));
		armaActiva.setPosY(recuadro.getPosY() + (recuadro.getAlto() / 2)
				- (armaActiva.getAlto() / 2));
	}

	public void moverHelicoptero() {
		if (moviendo) {
			boolean dirHelicopteroX = true;
			Helicoptero h = (Helicoptero) rescueHelicoptero;
			int mitadX = (int) (posOrigX + btnAnalogo.getAncho())
					- (btnAnalogo.getAncho() / 2);
			int posX = h.getPosX(), posY = h.getPosY();
			if (btnAnalogo.getPosX() + (btnAnalogo.getAncho() / 2) > mitadX + 3) {
				if (posX < (anchoPant - h.getAncho())) {
					posX = h.getPosX() + h.getVelX();
				}
			} else if (btnAnalogo.getPosX() + (btnAnalogo.getAncho() / 2) < mitadX - 3) {
				if (posX > 0) {
					posX = h.getPosX() - h.getVelX();
					dirHelicopteroX = false;
				}
			} else {
				angulo = 0;
				h.setAngulo(angulo);
			}
			int mitadY = (int) (posOrigY + btnAnalogo.getAlto())
					- (btnAnalogo.getAlto() / 2);
			if (btnAnalogo.getPosY() + (btnAnalogo.getAlto() / 2) > (mitadY + 3)) {
				if (posY < (altoPant - h.getAlto())) {
					posY = h.getPosY() + h.getVelY();
					if (direccion != 1) {
						direccion = 1;
						angulo = 0;
					}
					if (angulo < 15) {
						h.setAngulo(h.getAngulo() + 1);
						angulo++;
					}
				}
			} else if (btnAnalogo.getPosY() + (btnAnalogo.getAlto() / 2) < (mitadY - 3)) {
				if (posY > 0) {
					posY = h.getPosY() - h.getVelY();
					if (direccion != 2) {
						direccion = 2;
						angulo = 0;
					}
					if (angulo < 15) {
						h.setAngulo(h.getAngulo() - 1);
						angulo++;
					}
				}
			} else {
				if (angulo < 15) {
					if (dirHelicopteroX) {
						h.setAngulo(h.getAngulo() + 1);
					} else {
						h.setAngulo(h.getAngulo() - 1);
					}
					angulo++;
				}
			}
			h.move(posX, posY);
			int porcentajeLimite = (anchoPant * 50) / 100;
			if (h.getPosX() > getWidth() - porcentajeLimite) {
				escenario.mover(20, true);
			} else if (h.getPosX() < porcentajeLimite) {
				escenario.mover(20, false);
			}
		}
	}

	public void armaEnPantalla(Tipo tipoArma) {
		if (disparoActivo) {
			Helicoptero h = (Helicoptero) rescueHelicoptero;

			reproductorSonido.play(reproductorSonido.getAmetralladora(), 1);

			for (int i = 0; i < h.getArmas().size(); i++) {
				if (h.getArmas().get(i).getTipo() == tipoArma) {
					armaDisparada = h.getArmas().get(i);
					armaDisparada.setPosX(h.getPosX() + (h.getAncho() / 2));
					armaDisparada.setPosY(h.getPosY() + h.getAlto() / 2
							+ armaDisparada.getAlto());
					armaDisparada.setAngulo(h.getAngulo());
					h.getArmas().remove(i);
					break;
				}
			}
			armasEnPantalla.add(armaDisparada);
		}
	}

	public void manejoDisparo() {
		for (int i = 0; i < armasEnPantalla.size(); i++) {
			armaDisparada = armasEnPantalla.get(i);
			switch (armaDisparada.getTipo()) {
			case bomba:
				armaDisparada.move(0, 12);
				break;
			case metralla:
				armaDisparada.move(30, 30);
				break;
			case misil:
				armaDisparada.move(20, 1);
				break;
			}
		}
		for (int i = 0; i < disparosEnemigo.size(); i++) {
			armaDisparada = disparosEnemigo.get(i);
			switch (armaDisparada.getTipo()) {
			case bomba:
				armaDisparada.move(0, 12);
				break;
			case metralla:
				armaDisparada.move(30, 30);
				break;
			case misil:
				int x;
				int y2 = armaDisparada.getPunto1().getY();
				int x2 = armaDisparada.getPunto1().getX();
				int y1 = armaDisparada.getPunto2().getY();
				int x1 = armaDisparada.getPunto2().getX();
				int pendiente = (y2-y1) / (x2-x1);
				if(pendiente > 0){
					x = armaDisparada.getPosX() - armaDisparada.getVelX();					
				}else if(pendiente < 0){
					x = armaDisparada.getPosX() + armaDisparada.getVelX();					
				}else{
					x = 0;
				}
				int y = y1 + ((y2 - y1) / (x2 - x1)) * (x - x1);
				if(x > 0 && x < anchoPant && y > 0 && y < altoPant){
					armaDisparada.move(x, y);					
				}else{
					disparosEnemigo.remove(armaDisparada);
				}
				break;
			}
		}
	}

	public void armarHelicoptero() {
		Drawable misil_1, bomba_1, balaMetralla_1;
		Helicoptero h = (Helicoptero) rescueHelicoptero;
		ArrayList<Arma> armas = new ArrayList<Arma>();
		Grafico misil1 = null, bomba1 = null, balaMetralleta = null;
		misil_1 = context.getResources().getDrawable(R.drawable.misil1);
		bomba_1 = context.getResources().getDrawable(R.drawable.bomba1);
		balaMetralla_1 = context.getResources().getDrawable(
				R.drawable.bala_ametralladora);
		for (int i = 0; i < 5; i++) {
			misil1 = new Arma(Tipo.misil, CapacidadDano.alto, Alcance.alto,
					misil_1, this);
			armas.add((Arma) misil1);
			bomba1 = new Arma(Tipo.bomba, CapacidadDano.alto, Alcance.alto,
					bomba_1, this);
			armas.add((Arma) bomba1);
		}
		for (int x = 0; x < 100; x++) {
			balaMetralleta = new Arma(Tipo.metralla, CapacidadDano.medio,
					Alcance.alto, balaMetralla_1, this);
			armas.add((Arma) balaMetralleta);
		}
		h.setArmas(armas);
	}

	/**
	 * Cambia la cantidad de vida del helicoptero mostrada en la barra segun la
	 * cantidad que posea el helicoptero
	 */
	public void vidaHelicoptero() {
		Drawable vidaHelico = null;
		Helicoptero h = (Helicoptero) rescueHelicoptero;
		switch (h.porcentajeVida()) {
		case 0:
			vidaHelico = context.getResources().getDrawable(R.drawable.cero);
			break;
		case 1:
			vidaHelico = context.getResources().getDrawable(R.drawable.uno);
			break;
		case 2:
			vidaHelico = context.getResources().getDrawable(R.drawable.dos);
			break;
		case 3:
			vidaHelico = context.getResources().getDrawable(R.drawable.tres);
			break;
		case 4:
			vidaHelico = context.getResources().getDrawable(R.drawable.cuatro);
			break;
		case 5:
			vidaHelico = context.getResources().getDrawable(R.drawable.cinco);
			break;
		case 6:
			vidaHelico = context.getResources().getDrawable(R.drawable.seis);
			break;
		case 7:
			vidaHelico = context.getResources().getDrawable(R.drawable.siete);
			break;
		case 8:
			vidaHelico = context.getResources().getDrawable(R.drawable.ocho);
			break;
		case 9:
			vidaHelico = context.getResources().getDrawable(R.drawable.nueve);
			break;
		case 10:
			vidaHelico = context.getResources().getDrawable(R.drawable.diez);
			break;
		}
		vidaHelicoptero = new ObjetosPuntuacion(vidaHelico, this);
		vidaHelicoptero.setPosX((anchoPant / 100) * 8);
		vidaHelicoptero.setPosY((altoPant / 74) * 2);
	}

	public void cargarNivel(String nomJugador) {
		DbSqliteRescueJungle dbJungle = new DbSqliteRescueJungle(getContext());
		SQLiteDatabase db = dbJungle.getWritableDatabase();
		ArrayList<Integer> puntos = new ArrayList<Integer>();
		Cursor cursor;

		try {
			cursor = db.rawQuery(
					"select  numero_secuestrados, numero_rescatados "
							+ "from rescuejungle where usuario='" + nomJugador
							+ "'", null);

			if (cursor.moveToFirst()) {
				do {
					puntos.add(cursor.getInt(0));
					puntos.add(cursor.getInt(1));
				} while (cursor.moveToNext());
			}
			db.close();
			db = null;
			dbJungle = null;
			cursor.close();
			for (int i = 0; i < puntos.size(); i++) {
				if (puntos.get(i) != puntos.get(i + 1)) {
					this.nivel = 1;
					break;
				} else if (puntos.get(i + 2) != puntos.get(i + 3)) {
					this.nivel = 2;
					// falta comporbar aca el else if cambio de nivel que
					// funcione bien?
					break;
				} else if (puntos.get(i + 4) != puntos.get(i + 5)) {
					this.nivel = 3;
					// falta comporbar aca el else if cambio de nivel que
					// funcione bien?
					break;
				} else if (puntos.get(i + 6) != puntos.get(i + 7)) {
					this.nivel = 4;
					// falta comporbar aca el else if cambio de nivel que
					// funcione bien?
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Revisar este metodo por que carga todo al jugador sin se neceario
	// completamente
	public Jugador cargarAvancesJugador(String nomJugador) {
		DbSqliteRescueJungle dbJungle = new DbSqliteRescueJungle(getContext());
		SQLiteDatabase db = dbJungle.getWritableDatabase();
		ArrayList<Integer> avances = new ArrayList<Integer>();
		Cursor cursor;
		try {

			cursor = db
					.rawQuery(
							"select puntos,time_nivel,vidas_heli,numero_enemigos,numero_secuestrados,numero_rescatados from rescuejungle where usuario='"
									+ nomJugador
									+ "' and nivel='"
									+ nivel
									+ "'", null);

			if (cursor.moveToFirst()) {
				do {
					avances.add(cursor.getInt(0));
					avances.add(cursor.getInt(1));
					avances.add(cursor.getInt(2));
					avances.add(cursor.getInt(3));
					avances.add(cursor.getInt(4));
					avances.add(cursor.getInt(5));
				} while (cursor.moveToNext());
			}
			db.close();
			db = null;
			dbJungle = null;
			cursor.close();
			jugador = new Jugador(nomJugador, avances.get(0), avances.get(1),
					avances.get(2), avances.get(3), avances.get(4),
					avances.get(5), nivel);
			return jugador;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Verifica si un objeto enemigo esta en posicion de ataque
	 */
	public void verificaAtaque() {
		Helicoptero h = (Helicoptero) rescueHelicoptero;
		for (Enemigo enemigo : enemigosEnPantalla) {
			if (enemigo.alcanceAtaque(h, RadioDistanciaAtaque(enemigo))) {
				disparoEnemigo(enemigo);
			}
		}
	}

	/**
	 * Retorna el perimetro de disparo posible de un objeto
	 * 
	 * @param enemigo
	 *            El objeto del cual se quiere obtener su perimetro de disparo
	 * @return Perimetro de disparo del objeto
	 */
	public Rect RadioDistanciaAtaque(Enemigo enemigo) {
		Rect radio = null;
		int x1 = enemigo.getPosX();
		int x2 = x1 + enemigo.getAncho();
		switch (enemigo.getAlcanceDeAtaque()) {
		case bajo:
			int distanciaBaja = (anchoPant * 20) / 100; // Equivale al 20% de la
														// pantalla
			radio = new Rect(x1 - distanciaBaja, 0, x2 + distanciaBaja,
					altoPant);
			break;
		case medio:
			int distanciaMedia = (anchoPant * 40) / 100; // Equivale al 40% de
															// la pantalla
			radio = new Rect(x1 - distanciaMedia, 0, x2 + distanciaMedia,
					altoPant);
			break;
		case alto:
			int distanciaAlta = (anchoPant * 60) / 100; // Equivale al 60% de la
														// pantalla
			radio = new Rect(x1 - distanciaAlta, 0, x2 + distanciaAlta,
					altoPant);
			break;
		}
		return radio;
	}

	/**
	 * Ejecuta el disparo hacia el helicoptero
	 * 
	 * @param enemigo
	 *            Objeto que realiza el ataque
	 */
	public void disparoEnemigo(Enemigo enemigo) {
		Helicoptero h = (Helicoptero) rescueHelicoptero;
		int x1 = h.getPosX();
		int y1 = h.getPosY();
		int x2 = enemigo.getPosX();
		int y2 = enemigo.getPosY();
		int hipotenusa = (int) enemigo.distancia(h);
		int co = y2 - y1;
		double anguloDisparo = (Math.toDegrees(Math.asin((double) (co)
				/ (double) (hipotenusa))));
		// int direccion = (int) Math.toDegrees(Math.atan((double) (y2 - y1)
		// / (double) (x2 - x1)));
		// int pendiente = (int)((y1 - y2) / (x1 - x2));
		Coordenada cor1 = new Coordenada(x1, y1);
		Coordenada cor2 = new Coordenada(x2, y2);
		if (enemigo.getArmas().size() > 0) {
			tCrometro.setEstado(true);
			tCrometro.segundosCambioNivel();
			if (tCrometro.getSegun() < 1) {
				// aca espera no coloque nda mientras avanzan las centesimas
			} else {
				tCrometro.setEstado(false);
				tCrometro.setSegun(00);
				tCrometro.setCente(00);
				Arma armasEnemigo = enemigo.getArmas().get(0);
				if (x1 < x2) {
					// Si el helicoptero esta a la izquierda del enemigo
					armasEnemigo.setAngulo((int) anguloDisparo + 180);
				} else if (x1 > x2) {
					// Si el elicoptero esta a la derecha del enemigo
					armasEnemigo.setAngulo(360 - (int) anguloDisparo);
				} else {
					armasEnemigo.setAngulo((int) anguloDisparo);
				}
				armasEnemigo.setPosX(enemigo.getPosX()
						+ (enemigo.getAncho() / 2));
				armasEnemigo.setPosY(enemigo.getPosY());
				armasEnemigo.setPunto1(cor1);
				armasEnemigo.setPunto2(cor2);
				disparosEnemigo.add(armasEnemigo);
				enemigo.getArmas().remove(0);
			}

		}

	}

	public void cargarEnemigos() {
		Drawable eneDrawable, disparo;
		eneDrawable = getContext().getResources().getDrawable(
				R.drawable.gerrillo_1);
		disparo = getContext().getResources().getDrawable(R.drawable.misil1);
		Enemigo e = new Enemigo(eneDrawable, this, alcanceDeAtaque.medio, false);
		for (int i = 0; i < 50; i++) {
			Arma a = new Arma(disparo, this, Tipo.misil);
			e.agregarArma(a);
		}
		e.setPosX((anchoPant / 2));
		e.setPosY(altoPant - 100);
		for (int i = 0; i < 1; i++) {
			enemigosEnPantalla.add(e);
		}
	}
	
	/**
	 * carga de los objetos de las casas
	 * 
	 * @param casas
	 *            de los enemigos
	 * 
	 */

	public ArrayList<Grafico> cargarCasas() {

		ArrayList<Grafico> casas = new ArrayList<Grafico>();

		Drawable casa, casa_dos, cambuche, casabuena, casadanada, casadanada_uno;
		Drawable torre_unobuena, torrepequena, torre_danada, torre_peque_destruida;
		Grafico g_casa, g_casa_dos, g_cambuche, g_casabuena, g_casadanada, g_casadanada_uno;
		Grafico g_torre_unobuena, g_torrepequena, g_torre_danada, g_torre_peque_destruida;

		casa = getContext().getResources().getDrawable(R.drawable.casa);
		casa_dos = getContext().getResources().getDrawable(R.drawable.casa_dos);
		casabuena = getContext().getResources().getDrawable(
				R.drawable.casabuena);
		casadanada = getContext().getResources().getDrawable(
				R.drawable.casadanada);
		casadanada_uno = getContext().getResources().getDrawable(
				R.drawable.casadanada_uno);
		cambuche = getContext().getResources().getDrawable(R.drawable.cambuche);
		torre_unobuena = getContext().getResources().getDrawable(
				R.drawable.torre1buena);
		torrepequena = getContext().getResources().getDrawable(
				R.drawable.torrepequenas);
		torre_danada = getContext().getResources().getDrawable(
				R.drawable.torre1danada);
		torre_peque_destruida = getContext().getResources().getDrawable(
				R.drawable.torrepequenasdestruidas);

		g_casa = new Casa(casa, this);
		g_casa.setPosX((anchoPant / 100) * 45);
		g_casa.setPosY((altoPant / 100) * 93);
		casas.add(g_casa);

		g_casa_dos = new Casa(casa_dos, this);
		g_casa_dos.setPosX((anchoPant / 100) * 34);
		g_casa_dos.setPosY((altoPant / 100) * 84);
		casas.add(g_casa_dos);

		g_cambuche = new Casa(cambuche, this);
		g_cambuche.setPosX((anchoPant / 100) * 50);
		g_cambuche.setPosY((altoPant / 100) * 87);
		casas.add(g_cambuche);

		g_casabuena = new Casa(casabuena, this);
		casas.add(g_casabuena);
		g_casabuena.setPosX((anchoPant / 100) * 100);
		g_casabuena.setPosY((altoPant / 100) * 90);

		g_casadanada = new Casa(casadanada, this);
		casas.add(g_casadanada);
		g_casadanada.setPosX((anchoPant / 100) * 70);
		g_casadanada.setPosY((altoPant / 100) * 76);

		g_casadanada_uno = new Casa(casadanada_uno, this);
		casas.add(g_casadanada_uno);
		g_casadanada_uno.setPosX((anchoPant / 100) * 90);
		g_casadanada_uno.setPosY((altoPant / 100) * 86);

		g_torre_unobuena = new Casa(torre_unobuena, this);
		g_torre_unobuena.setPosX((anchoPant / 100) * 84);
		g_torre_unobuena.setPosY((altoPant / 100) * 93);
		casas.add(g_torre_unobuena);

		g_torrepequena = new Casa(torrepequena, this);
		g_torrepequena.setPosX((anchoPant / 100) * 93);
		g_torrepequena.setPosY((altoPant / 100) * 85);
		casas.add(g_torrepequena);

		g_torre_danada = new Casa(torre_danada, this);
		g_torre_danada.setPosX((anchoPant / 100) * 76);
		g_torre_danada.setPosY((altoPant / 100) * 74);
		casas.add(g_torre_danada);

		g_torre_peque_destruida = new Casa(torre_peque_destruida, this);
		g_torre_peque_destruida.setPosX((anchoPant / 100) * 80);
		g_torre_peque_destruida.setPosY((altoPant / 100) * 74);
		casas.add(g_torre_peque_destruida);

		return casas;
	}

}
