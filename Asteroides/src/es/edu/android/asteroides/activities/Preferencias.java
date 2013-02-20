package es.edu.android.asteroides.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import es.edu.android.asteroides.R;

/**
 * Actividad representativa de la pantalla de preferencias
 * 
 * @author e.astolfi
 */
public class Preferencias extends PreferenceActivity {
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencias);
	}
}
