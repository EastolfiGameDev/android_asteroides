package es.edu.android.asteroides.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import es.edu.android.asteroides.R;
import es.edu.android.asteroides.graphics.VistaJuego;

public class Juego extends Activity {
	private VistaJuego vistaJuego;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_juego);
		
		vistaJuego = (VistaJuego) findViewById(R.id.vistaJuego1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_juego, menu);
		return true;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		vistaJuego.getThread().pausar();
		
		if (vistaJuego.sensor)
			vistaJuego.getmSensorManager().unregisterListener(vistaJuego);
	}
	@Override
	protected void onResume() {
		super.onResume();
		
		vistaJuego.getThread().reanudar();
		
		if (vistaJuego.sensor)
			vistaJuego.setmSensorManager();
	}
	@Override
	protected void onDestroy() {
		vistaJuego.getThread().parar();
		
		if (vistaJuego.sensor)
			vistaJuego.getmSensorManager().unregisterListener(vistaJuego);
		super.onDestroy();
	}

}
