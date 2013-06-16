package co.edu.uniminuto.vista;

import co.edu.uniminuto.persistencia.DbSqliteRescueJungle;
import co.edu.uniminuto.scenario.InicioJuego;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class JugadorNuevo extends Activity {

	DbSqliteRescueJungle dbJungle;
	SQLiteDatabase db;
	Cursor cursor;
	private ImageButton b_Ok;
	private ImageButton b_Atras;
	EditText editText;
	String nombre = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.usuario_nuevo);
		editText = (EditText) findViewById(R.id.Usuario_Nuevo);

		if (savedInstanceState != null) {
			nombre = savedInstanceState.getString("usuario");
			editText.setText(nombre);
		}
		b_Ok = (ImageButton) findViewById(R.id.Boton_Crear_Usuario);
		b_Atras = (ImageButton) findViewById(R.id.Boton_Atras);

		b_Ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				nombre = (String) editText.getText().toString();
				if (!nombre.equals("")) {

					if (buscarJugador(nombre).equals(nombre)) {
						Toast.makeText(getBaseContext(), "Ya existe usurio",
								Toast.LENGTH_SHORT).show();
						try {
							Thread.sleep(100);
							mostrarJugadores();
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						editText.setText("");
						crearJugador(nombre);
					}

				} else {
					Toast.makeText(getBaseContext(),
							"Digite nombre de usuario", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		b_Atras.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				regresar();
			}
		});
	}

	public String buscarJugador(String nombre) {
	    String nombreDb = "";
		DbSqliteRescueJungle dbJungle = new DbSqliteRescueJungle(
				getBaseContext());
		SQLiteDatabase db = dbJungle.getWritableDatabase();
		try {
			cursor = db.rawQuery(
					"select usuario from rescuejungle where usuario='" + nombre
							+ "'", null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				nombreDb = cursor.getString(0);
				cursor.moveToNext();
				
			}
            db.close();
            db = null;
            cursor.close();
            cursor = null;
			return nombreDb;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public void crearJugador(String nombre) {

		try {
			DbSqliteRescueJungle dbJungle = new DbSqliteRescueJungle(
					getBaseContext());
			SQLiteDatabase db = dbJungle.getWritableDatabase();
			int id = obtenerNumRegistros();
			if (id != -1 || id == 0) {
				db.execSQL("insert into rescuejungle (id, usuario, puntos, time_nivel, vidas_heli, numero_enemigos, numero_secuestrados, numero_rescatados, nivel) values('"
						+ (id++) + "','" + nombre + "',0,3,2,10,6,0,1)");
				db.execSQL("insert into rescuejungle (id, usuario, puntos, time_nivel, vidas_heli, numero_enemigos, numero_secuestrados, numero_rescatados, nivel) values('"
						+ (id++) + "','" + nombre + "',0,4,3,13,9,0,2)");
				db.execSQL("insert into rescuejungle (id, usuario, puntos, time_nivel, vidas_heli, numero_enemigos, numero_secuestrados, numero_rescatados, nivel) values('"
						+ (id++) + "','" + nombre + "',0,5,3,16,12,0,3)");
				db.execSQL("insert into rescuejungle (id, usuario, puntos, time_nivel, vidas_heli, numero_enemigos, numero_secuestrados, numero_rescatados, nivel) values('"
						+ (id++) + "','" + nombre + "',0,6,4,19,15,0,4)");
				db.close();
				db = null;
				dbJungle = null;
				cursor.close();
				lanzarJuego(nombre);
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int obtenerNumRegistros() {
		int contador = 0;
		DbSqliteRescueJungle dbJungle = new DbSqliteRescueJungle(
				getBaseContext());
		SQLiteDatabase db = dbJungle.getWritableDatabase();
		try {
			cursor = db.rawQuery("select * from rescuejungle ", null);
			while (!cursor.isAfterLast()) {
				contador++;
				cursor.moveToNext();
			}
			if (contador != 0) {
				db.close();
				db = null;
				dbJungle = null;
				cursor.close();
				return contador;
			} else {
				db.close();
				db = null;
				dbJungle = null;
				cursor.close();
				return 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void lanzarJuego(String nombre) {
		Intent intent = new Intent(this, InicioJuego.class);
		intent.putExtra("nombre_jugador", nombre);
		startActivity(intent);
		finish();
	}

	public void mostrarJugadores() {
		finish();
		startActivity(new Intent(this, MostrarUsuarios.class));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			regresar();
			return true;
		}
		return false;
	}

	public void regresar() {
		finish();
		startActivity(new Intent(this, Menu.class));
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("usuario", editText.getText().toString());
		super.onSaveInstanceState(savedInstanceState);
	}
}
