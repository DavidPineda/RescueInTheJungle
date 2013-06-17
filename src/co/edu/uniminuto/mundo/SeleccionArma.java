package co.edu.uniminuto.mundo;

import java.util.LinkedList;
import java.util.ListIterator;

import android.graphics.drawable.Drawable;
import android.view.View;
import co.edu.uniminuto.clases.Grafico;

public class SeleccionArma extends Grafico {

	public enum Sentido {
		izquierda, derecha, centro;
	}

	private Sentido sentido;
	private LinkedList<Arma> armasImagen;

	public LinkedList<Arma> getArmasImagen() {
		return armasImagen;
	}

	public void setArmasImagen(LinkedList<Arma> armasImagen) {
		this.armasImagen.addAll(armasImagen);
	}

	public void agregarImagen(Arma grafico) {
		this.armasImagen.addLast(grafico);
	}

	public Sentido getSentido() {
		return sentido;
	}

	public void setSentido(Sentido sentido) {
		this.sentido = sentido;
	}

	public SeleccionArma(Drawable drawable, View view) {
		super(drawable, view);
		sentido = null;
		armasImagen = new LinkedList<Arma>();
	}

	public SeleccionArma(Drawable drawable, View view, Sentido sentido) {
		super(drawable, view);
		this.sentido = sentido;
		armasImagen = null;
	}

	public Arma imagenMostrar(Sentido sentido, int pos) {
		Arma imagen = null;
		ListIterator<Arma> listIterator = armasImagen.listIterator(pos);
		if (armasImagen.size() >= 2) {
			if (pos == 0 && sentido == Sentido.izquierda
					|| pos == armasImagen.size() - 1
					&& sentido == Sentido.derecha) {
				return listIterator.next();
			} else {
				switch (sentido) {
				case izquierda:
					imagen = listIterator.previous();
					break;
				case derecha:
					listIterator.next();
					imagen = listIterator.next();
					break;
				default:
					break;
				}
			}
		}
		return imagen;
	}
}
