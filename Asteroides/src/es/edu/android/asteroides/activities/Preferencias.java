package es.edu.android.asteroides.activities;

import java.util.Locale;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import es.edu.android.asteroides.R;

/**
 * Actividad representativa de la pantalla de preferencias
 * 
 * @author e.astolfi
 */
public class Preferencias extends PreferenceActivity {
	MediaPlayer mp;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencias);
		
		ListPreference lst = (ListPreference) findPreference("idiomas");
		lst.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				cambiarIdioma(new Locale(newValue.toString()));
				return true;
			}
		});
	}
	
	private void cambiarIdioma(Locale locale) {
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getApplicationContext().getResources().updateConfiguration(config, getApplicationContext().getResources().getDisplayMetrics());
	}
}
