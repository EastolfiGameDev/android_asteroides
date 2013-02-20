package es.edu.android.asteroides.interfaces;

import java.util.Vector;

/**
 * Interfaz que gestiona las puntuaciones mediante un {@link Vector}
 * 
 * @author e.astolfi
 */
public interface AlmacenPuntuaciones {
	
	/**
	 * Guarda una puntuación de partida
	 * 
	 * @param puntos conseguidos
	 * @param nombre del usuario
	 * @param fecha de la partida
	 */
	public void guardarPuntuacion(int puntos, String nombre, long fecha);

	/**
	 * Lista las puntuaciones guardadas
	 * 
	 * @param cantidad de puntuaciones a mostrar
	 * @return {@link Vector} vector con las puntuaciones
	 */
	public Vector<String> listaPuntuaciones(int cantidad);
}
