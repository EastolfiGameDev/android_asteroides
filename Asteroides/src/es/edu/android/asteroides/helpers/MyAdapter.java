package es.edu.android.asteroides.helpers;

import java.util.Vector;

import es.edu.android.asteroides.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adaptador para mostrar las puntuaciones mediante vistas
 * 
 * @author e.astolfi
 */
public class MyAdapter extends BaseAdapter {
	private final Activity activity;
	private final Vector<String> list;
	
	public MyAdapter(Activity activity, Vector<String> list) {
		super();
		this.activity = activity;
		this.list = list;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(R.layout.elemento_lista, null);
		TextView txtTitle = (TextView) view.findViewById(R.id.ratingTitle);
		txtTitle.setText(list.elementAt(position));
		ImageView imgIcon = (ImageView) view.findViewById(R.id.ratingIcon);
		
		switch (Math.round((float)Math.random()*2)) {
		case 0:
			imgIcon.setImageResource(R.drawable.asteroide1);
			break;
		case 1:
			imgIcon.setImageResource(R.drawable.asteroide2);
			break;
		case 2:
			imgIcon.setImageResource(R.drawable.asteroide3);
			break;
		}
		
		return view;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.elementAt(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
