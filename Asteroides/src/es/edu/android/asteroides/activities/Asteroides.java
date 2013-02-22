package es.edu.android.asteroides.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import es.edu.android.asteroides.R;
import es.edu.android.asteroides.entities.AlmacenPuntuacionesArray;
import es.edu.android.asteroides.interfaces.AlmacenPuntuaciones;

/**
 * Actividad representativa de la pantalla principal
 * 
 * @author e.astolfi
 */
public class Asteroides extends Activity {
	public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();
	Button btnPlay, btnAbout, btnSettings, btnRating, btnExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		/************ Eventos de Botones ************/
		/* Eventos movidos del layout a codigo para simplificarlo 
		 * al usar distintas pantallas (-land, -xlarge, ...) 
		 */
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

}
