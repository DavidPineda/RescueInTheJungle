package co.edu.uniminuto.mundo;

public class Jugador {

	private String usuario;
	private int puntos;
	private int timeNivel;
	private int vidasHeli;
	private int numeroEnemigos;
	private int nuemroSecuestrados;
	private int numeroRescatados;
	private int nivel;
	private int vidaHelicoptero;
	
	public Jugador(String usuario, int puntos, int timeNivel, int vidasHeli,
			int numeroEnemigos, int nuemroSecuestrados, int numeroRescatados,
			int nivel, int vidaHelicoptero) {
		super();
		this.usuario = usuario;
		this.puntos = puntos;
		this.timeNivel = timeNivel;
		this.vidasHeli = vidasHeli;
		this.numeroEnemigos = numeroEnemigos;
		this.nuemroSecuestrados = nuemroSecuestrados;
		this.numeroRescatados = numeroRescatados;
		this.nivel = nivel;
		this.vidaHelicoptero = vidaHelicoptero;
	}

	public int getVidaHelicoptero() {
		return vidaHelicoptero;
	}

	public void setVidaHelicoptero(int vidaHelicoptero) {
		this.vidaHelicoptero -= vidaHelicoptero;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getTimeNivel() {
		return timeNivel;
	}

	public void setTimeNivel(int timeNivel) {
		this.timeNivel = timeNivel;
	}

	public int getVidasHeli() {
		return vidasHeli;
	}

	public void setVidasHeli(int vidasHeli) {
		this.vidasHeli = vidasHeli;
	}

	public int getNumeroEnemigos() {
		return numeroEnemigos;
	}

	public void setNumeroEnemigos(int numeroEnemigos) {
		this.numeroEnemigos = numeroEnemigos;
	}

	public int getNuemroSecuestrados() {
		return nuemroSecuestrados;
	}

	public void setNuemroSecuestrados(int nuemroSecuestrados) {
		this.nuemroSecuestrados = nuemroSecuestrados;
	}

	public int getNumeroRescatados() {
		return numeroRescatados;
	}

	public void setNumeroRescatados(int numeroRescatados) {
		this.numeroRescatados = numeroRescatados;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public void restablecerVidaHelicoptero(int vidaHelicoptero) {
		this.vidaHelicoptero = vidaHelicoptero;
		
	}
	
}
