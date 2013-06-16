package co.edu.uniminuto.vista;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import co.edu.uniminuto.clases.MediaPlayerRescue;
import co.edu.uniminuto.vista.VistaJuego;

public class MainJuego extends Activity {
	private MediaPlayerRescue mediaPlayerRescue;
	private static final int DIALOGO_SALIR = 1;
	private static final int DIALOGO_PAUSA = 2;
	public int sonido_estado = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Bundle extra = this.getIntent().getExtras();
		setContentView(new VistaJuego(this, extra.getString("nombre_jugador")));
		mediaPlayerRescue = getAudioConstante(R.raw.helicoptero_menu);
		mediaPlayerRescue.loop();
	}

	protected MediaPlayerRescue getAudioConstante(int id) {
		return new MediaPlayerRescue(getBaseContext(), id);
	}

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			 sonido_estado = mediaPlayerRescue.seeto();
			 mediaPlayerRescue.stop();
			showDialog(DIALOGO_SALIR);
		}
		return super.onKeyDown(keyCode, event);
	}

	protected Dialog onCreateDialog(int id) {
		mediaPlayerRescue.stop();
		Dialog cuadroDialogo = null;
		switch (id) {

		case DIALOGO_SALIR:
			
			AlertDialog.Builder salirBuilder = new AlertDialog.Builder(this);
			salirBuilder.setTitle("Salir y guardar");
			salirBuilder.setIcon(android.R.drawable.ic_menu_save);
			salirBuilder.setCancelable(false);
			salirBuilder.setMessage("Seguro que desea regresar?");
			salirBuilder.setPositiveButton("Si",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int arg1) {
							dialog.dismiss();
							mediaPlayerRescue.release();
							finish();
						}
					});

			salirBuilder.setNegativeButton("NO",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int arg1) {
							dialog.dismiss();
							mediaPlayerRescue.setPosicion(sonido_estado);
							mediaPlayerRescue.loop();
						}
					});
			cuadroDialogo = salirBuilder.create();

			break;
		case DIALOGO_PAUSA:
			AlertDialog.Builder pausaBuilder = new AlertDialog.Builder(this);
			pausaBuilder.setTitle("Pausa");
			pausaBuilder.setIcon(android.R.drawable.ic_menu_save);
			pausaBuilder.setCancelable(false);
			pausaBuilder.setMessage("PAUSA");
			pausaBuilder.setPositiveButton("Resume",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int arg1) {

							dialog.dismiss();
						}
					});

			pausaBuilder.setNegativeButton("Quitar",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int arg1) {
							dialog.dismiss();
							finish();
						}
					});
			cuadroDialogo = pausaBuilder.create();

			break;

		default:
			break;
		}
		return cuadroDialogo;
	}

	@Override
	protected void onStop() {
		super.onStop();
		mediaPlayerRescue.stop();
	}

	@Override
	protected void onRestart(){
		super.onRestart();
		mediaPlayerRescue.loop();
	}
	
	@Override
	 protected void onPause(){
		super.onPause();
		mediaPlayerRescue.stop();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		mediaPlayerRescue.release();
	}
	
}
