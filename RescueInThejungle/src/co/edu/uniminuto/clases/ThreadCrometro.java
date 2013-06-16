package co.edu.uniminuto.clases;

public class ThreadCrometro {

	public boolean pausado, detenido, gameover, estado;
	private int centesimas, minutos, segundos;
	private int cente, segun;
	String cron;

	public ThreadCrometro() {
		this.pausado = true;
		this.detenido = false;
		this.gameover = false;
		this.minutos = 00;
		this.segundos = 00;
		this.centesimas = 00;
		this.cente = 0;
		this.segun = 0;
		this.estado = true;
	}

	public String runTime(int timenivel) {
		while (!getDetenido()) {
			while (pausado) {
				try {
					if (timenivel != minutos) {
						if (centesimas == 16) {
							centesimas = 00;
							segundos++;
							setSegundos(segundos);
						}
						if (segundos == 59) {
							segundos = 00;
							minutos++;
							setMinutos(minutos);
						}
						centesimas++;
						cron = minutos + " : " + segundos;
						return cron;
					} else {
						this.gameover = true;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return "";
	}

	public int segundosCambioNivel() {
		while (getEstado()) {
			try {
				if (cente == 16) {
					cente = 00;
					segun++;
				}
				if (segun == 59) {
					segun = 00;
				}
				cente++;
				return segun;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return -1;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public boolean getDetenido() {
		return detenido;
	}

	public boolean getPuasado() {
		return pausado;
	}

	public void setDetenido(boolean detenido) {
		this.detenido = detenido;
	}

	public int getCente() {
		return cente;
	}

	public void setCente(int cente) {
		this.cente = cente;
	}

	public int getSegun() {
		return segun;
	}

	public void setSegun(int segun) {
		this.segun = segun;
	}

	public void setPausado(boolean pausado) {
		this.pausado = pausado;
	}

	public int getCentesimas() {
		return centesimas;
	}

	public void setCentesimas(int centesimas) {
		this.centesimas = centesimas;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	public int getSegundos() {
		return segundos;
	}

	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}

}
