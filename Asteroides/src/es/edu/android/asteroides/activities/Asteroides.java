package es.edu.android.asteroides.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import es.edu.android.asteroides.R;
import es.edu.android.asteroides.entities.AlmacenPuntuacionesArray;
import es.edu.android.asteroides.helpers.MyMediaPlayer;
import es.edu.android.asteroides.interfaces.AlmacenPuntuaciones;

/**
 * Actividad representativa de la pantalla principal
 * 
 * @author e.astolfi
 */
public class Asteroides extends Activity {
	public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();
	Button btnPlay, btnAbout, btnSettings, btnRating, btnExit;
	ImageView btnMusic;
	MyMediaPlayer media;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		media = new MyMediaPlayer(this, R.raw.audio);
		
		/************ Eventos de Botones ************/
		btnPlay = (Button) findViewById(R.id.btnPlay);
		btnPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lanzarJuego(v);
			}
		});
		
		btnAbout = (Button) findViewById(R.id.btnAbout);
		btnAbout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lanzarAcercaDe(v);
			}
		});
		
		btnExit = (Button) findViewById(R.id.btnExit);
		btnExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		btnSettings = (Button) findViewById(R.id.btnConfig);
		btnSettings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lanzarPreferencias(v);
			}
		});
		
		btnRating = (Button) findViewById(R.id.btnRating);
		btnRating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lanzarPuntuaciones(v);
			}
		});
		
		btnMusic = (ImageView) findViewById(R.id.btnMusic);
		btnMusic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Cada vez que hagamos click, si no existía la preferencia (es false) la creamos a true,
				//si no la borramos. Arrancamos o paramos la música también
				SharedPreferences prefManager = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				Editor edit = prefManager.edit();
				if (!prefManager.getBoolean("musica", false)) {
					edit.putBoolean("musica", true);
					btnMusic.setImageResource(R.drawable.btn_music_on);
					media.start();
				}
				else {
					edit.remove("musica");
					btnMusic.setImageResource(R.drawable.btn_music_off);
					media.pause();
				}
				edit.commit();
			}
		});
		/********************************************/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			lanzarAcercaDe(null);
			break;
		case R.id.menu_config:
			lanzarPreferencias(null);
			break;
		}
		return true;
	}
	
	
	public void lanzarJuego(View view) {
		Intent i = new Intent(this, Juego.class);
		startActivity(i);
	}
	
	public void lanzarAcercaDe(View view) {
		Intent i = new Intent(this, AcercaDe.class);
		startActivity(i);
	}
	
	public void lanzarPreferencias(View view) {
		Intent i = new Intent(this, Preferencias.class);
		startActivity(i);
	}

	public void lanzarPuntuaciones(View view) {
		Intent i = new Intent(this, Puntuaciones.class);
		startActivity(i);
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		media.pause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//Arrancamos la música si está marcada la preferencia
		SharedPreferences prefManager = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		if (prefManager.getBoolean("musica", false)) {
			btnMusic.setImageResource(R.drawable.btn_music_on);
			media.start();
		}
		else {
			btnMusic.setImageResource(R.drawable.btn_music_off);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		//Guardamos la posición de la reproducción
		if (media != null) {
			int pos = media.getCurrentPosition();
			savedInstanceState.putInt("mpPos", pos);
		}
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		//Recargamos la posición de la reproducción
		if (savedInstanceState != null && media != null) {
			int pos = (Integer) savedInstanceState.get("mpPos");
			media.seekTo(pos);
		}
	}
}
