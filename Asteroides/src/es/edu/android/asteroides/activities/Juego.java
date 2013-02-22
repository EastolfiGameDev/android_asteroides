package es.edu.android.asteroides.activities;

import es.edu.android.asteroides.R;
import es.edu.android.asteroides.R.layout;
import es.edu.android.asteroides.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Juego extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_juego);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_juego, menu);
		return true;
	}

}
