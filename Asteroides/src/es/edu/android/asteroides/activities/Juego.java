package es.edu.android.asteroides.activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import es.edu.android.asteroides.R;
import es.edu.android.asteroides.graphics.VistaJuego;

public class Juego extends Activity {
	private VistaJuego vistaJuego;
	MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_juego);
		
		/** Musica **/
//		mp = new MediaPlayer();
//		mp.start();
		/************/
		
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
		
		mp.pause();
		
		vistaJuego.getThread().pausar();
		vistaJuego.getmSensorManager().unregisterListener(vistaJuego);
	}
	@Override
	protected void onResume() {
		super.onResume();
		
		mp.start();
		
		vistaJuego.getThread().reanudar();
		vistaJuego.setmSensorManager();
	}
	@Override
	protected void onDestroy() {
		vistaJuego.getThread().parar();
		vistaJuego.getmSensorManager().unregisterListener(vistaJuego);
		super.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		if (mp != null) {
//			int pos = mp.getCurrentPosition();
//			savedInstanceState.putInt("mpPos", pos);
		}
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null && mp != null) {
//			int pos = (Integer) savedInstanceState.get("mpPos");
//			mp.seekTo(pos);
		}
	}
	

}
