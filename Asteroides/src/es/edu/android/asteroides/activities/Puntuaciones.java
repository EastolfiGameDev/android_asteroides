package es.edu.android.asteroides.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import es.edu.android.asteroides.R;
import es.edu.android.asteroides.adapter.MyAdapter;

/**
 * Actividad representativa de la pantalla de puntuaciones
 * 
 * @author e.astolfi
 */
public class Puntuaciones extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puntuaciones);
		
		MyAdapter adapter = new MyAdapter(this,
							Asteroides.almacen.listaPuntuaciones(5));
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);
		Object o = getListAdapter().getItem(position);
		Toast.makeText(this, "Selección: " + Integer.toString(position)
		          +  " - " + o.toString(),Toast.LENGTH_SHORT).show();
	}

}
