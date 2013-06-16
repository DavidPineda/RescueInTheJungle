package co.edu.uniminuto.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DbSqliteRescueJungle extends SQLiteOpenHelper {

	

	private static int version = 1;
	private static String name = "RescueInTheJungle";
	private static CursorFactory factory = null;
	private static final String sqltabla = "create table if not exists"+" "
			+ "rescuejungle(id interger," 
			+ "usuario TEXT," 
			+ "puntos integer,"
			+ "time_nivel integer," 
			+ "vidas_heli interger,"
			+ "numero_enemigos interger," 
			+ "numero_secuestrados interger,"
			+ "numero_rescatados interger,"
			+ "nivel integer)";

	public DbSqliteRescueJungle(Context context) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqltabla);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(DbSqliteRescueJungle.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS rescuejungle");
		onCreate(database);
	}

}