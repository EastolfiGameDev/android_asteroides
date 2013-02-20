package es.edu.android.asteroides.activities;

import android.app.Activity;
import android.os.Bundle;
import es.edu.android.asteroides.R;

/**
 * Actividad representativa del dialogo de Acerca De
 * 
 * @author e.astolfi
 */
public class AcercaDe extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about); 
	}
}
