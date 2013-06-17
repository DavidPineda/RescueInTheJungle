package co.edu.uniminuto.vista;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CargarUsuario extends BaseAdapter {
	  
	protected Activity activity;
	protected ArrayList<String> usuarios;

	public CargarUsuario(Activity activity, ArrayList<String> usuarios) {
		this.activity = activity;
		this.usuarios = usuarios;
	}

	@Override
	public int getCount() {
		return usuarios.size();
	}

	@Override
	public Object getItem(int position) {
		return usuarios.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_jugador, null);
		}
		
		String usuario = usuarios.get(position);
		
		TextView nombre = (TextView) view.findViewById(R.id.Item_centro);
		nombre.setText(usuario);
		
		TextView tipo = (TextView) view.findViewById(R.id.Tipo_Usuario);
		tipo.setText("bueno");
	
		return view;
	}

}
