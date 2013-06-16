package co.edu.uniminuto.vista;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import co.edu.uniminuto.persistencia.DbSqliteRescueJungle;
import co.edu.uniminuto.scenario.InicioJuego;

public class MostrarUsuarios extends Activity {

	DbSqliteRescueJungle dbJungle;
	SQLiteDatabase db;
	Cursor cursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.lista_jugadores);
		ListView lv = (ListView) findViewById(R.id.ListView);
		ArrayList<String> usuarios = leerJugadores();
		final ArrayList<String> usuariositemseleccionado = leerJugadores();
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, usuariositemseleccionado));

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				lanzarJuego(buscarjugadorseleccionado(position));
			}

			private String buscarjugadorseleccionado(int position) {
				for (int i = 0; i <= usuariositemseleccionado.size(); i++) {
					if (i == position) {
						return (usuariositemseleccionado.get(i));
					}
				}
				return null;
			}
		});

		if (usuarios.size() == 0) {
			Toast.makeText(this, "¡NO HAY USARIOS GUARDADOS!",
					Toast.LENGTH_LONG).show();
			lanzaMenu();
		}

		CargarUsuario adapter = new CargarUsuario(this, usuarios);
		lv.setAdapter(adapter);
	}

	public ArrayList<String> leerJugadores() {
		DbSqliteRescueJungle dbJungle = new DbSqliteRescueJungle(
				getBaseContext());
		SQLiteDatabase db = dbJungle.getWritableDatabase();
		ArrayList<String> jugadores = new ArrayList<String>();
		ArrayList<String> usuarios = new ArrayList<String>();
		try {
			cursor = db.rawQuery("select usuario from rescuejungle", null);

			if (cursor.moveToFirst()) {
				do {
					jugadores.add(cursor.getString(0));
				} while (cursor.moveToNext());
			}

			for (int i = 0; i < jugadores.size(); i += 4) {
				usuarios.add(jugadores.get(i));
			}
			db.close();
			db = null;
			dbJungle = null;
			cursor.close();
			return usuarios;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (jugadores = null);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			startActivity(new Intent(this, Menu.class));
			return true;
		}
		return false;
	}

	private void lanzaMenu() {
		finish();
		startActivity(new Intent(this, Menu.class));
	}

	public void lanzarJuego(String nomJugador) {
		Intent intent = new Intent(this, InicioJuego.class);
		intent.putExtra("nombre_jugador", nomJugador);
		startActivity(intent);
		finish();
	}

}
