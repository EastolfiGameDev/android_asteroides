package es.edu.android.asteroides.graphics;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import es.edu.android.asteroides.R;

/**
 * Clase manejadora de la pantalla donde se ejecutara el juego en sí
 * 
 * @author e.astolfi
 */
public class VistaJuego extends View {
	/********* ASTEROIDES **********/
    private Vector<Grafico> Asteroides; // Vector con los Asteroides
    private int numAsteroides= 5; // Número inicial de asteroides
    private int numFragmentos= 3; // Fragmentos en que se divide
    /********* NAVE **********/
    private Grafico nave;// Gráfico de la nave
    private int giroNave; // Incremento de dirección
    private float aceleracionNave; // aumento de velocidad
    // Incremento estándar de giro y aceleración
    private static final int PASO_GIRO_NAVE = 5;
    private static final float PASO_ACELERACION_NAVE = 0.5f;
	 /********** THREAD Y TIEMPO ***********/
    // Thread encargado de procesar el juego
    private ThreadJuego thread = new ThreadJuego();
    // Cada cuanto queremos procesar cambios (ms)
    private static int PERIODO_PROCESO = 50;
    // Cuando se realizó el último proceso
    private long ultimoProceso = 0;
    
    /**
     * Inicializamos cada objeto y vista que necesitaremos luego
     */
	public VistaJuego(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		//Creamos la nave, asteroides y misiles a partir de un Drawable
		Drawable drawableNave, drawableAsteroide, drawableMisil;
		drawableAsteroide = getResources().getDrawable(R.drawable.asteroide1);
		drawableNave = getResources().getDrawable(R.drawable.nave_small);
		
		//Inicializamos la nave
		nave = new Grafico(this, drawableNave);
		
		//Creamos el Vector que contendrá los asteroides
		Asteroides = new Vector<Grafico>();
		
		//Iniciamos y añadimos cada Asteroide al Vector
		for (int i=0; i<numAsteroides; i++) {
			//Inicializamos el asteroides
			Grafico asteroide = new Grafico(this, drawableAsteroide);
			//Añadimos al asteroide una velocidad aleatoria,
			//así como el ángulo y la rotación
			asteroide.setIncX(Math.random() * 4 - 2);
			asteroide.setIncY(Math.random() * 4 - 2);
			asteroide.setAngulo((int) (Math.random() * 360));
			asteroide.setRotacion((int) (Math.random() * 8 - 4));
			//Añadimos el asteroide al vector
			Asteroides.add(asteroide);
		}
	}
	
	@Override
	protected void onSizeChanged(int ancho, int alto, int ancho_anter, int alto_anter) {
		super.onSizeChanged(ancho, alto, ancho_anter, alto_anter);
		
		//Colocamos la nave en el centro de la pantalla
		//(la mitad de la pantalla menos el tamaño de la imagen)
		nave.setPosX(ancho / 2 - nave.getAncho() / 2);
		nave.setPosY(alto / 2 - nave.getAlto() / 2);
		
		
		//Colocamos cada asteroide en un lugar aleatorio,
		//según el tamaño de la pantall, y sin que coincida con la nave
		for(Grafico asteroide : Asteroides) {
			do {
				asteroide.setPosX(Math.random() * (ancho-asteroide.getAncho()));
				asteroide.setPosY(Math.random() * (alto-asteroide.getAlto()));
			} while(asteroide.distancia(nave) < (alto+ancho)/5);
		}
		
		//Iniciamos el hilo de ejecución del juego
		ultimoProceso = System.currentTimeMillis();
		thread.start();
	}
	
	@Override
	synchronized
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//Dibujamos la nave en la pantalla
		nave.dibujaGrafico(canvas);
		
		//dibujamos cada asteroide en la pantalla
		for(Grafico asteroide : Asteroides) {
			asteroide.dibujaGrafico(canvas);
		}
	}
	
	synchronized
	public void actualizaFisica() {
		long ahora = System.currentTimeMillis();
		// No hagas nada si el período de proceso no se ha cumplido.
		if (ultimoProceso + PERIODO_PROCESO > ahora) {
			return;
		}
		// Para una ejecución en tiempo real calculamos retardo
		double retardo = (ahora - ultimoProceso) / PERIODO_PROCESO;
		ultimoProceso = ahora;	// Para la próxima vez
		// Actualizamos velocidad y dirección de la nave a partir de 
		// giroNave y aceleracionNave (según la entrada del jugador)
		nave.setAngulo((int) (nave.getAngulo() + giroNave * retardo));
		double nIncX = nave.getIncX() + aceleracionNave *
					Math.cos(Math.toRadians(nave.getAngulo())) * retardo;
		double nIncY = nave.getIncY() + aceleracionNave *
					Math.sin(Math.toRadians(nave.getAngulo())) * retardo;
		// Actualizamos si el módulo de la velocidad no excede el máximo
		if (Math.hypot(nIncX, nIncY) <= Grafico.MAX_VELOCIDAD) {
			nave.setIncX(nIncX);
			nave.setIncY(nIncY);
		}
		// Actualizamos posiciones X e Y
		nave.incrementaPos(retardo);
		for (Grafico asteroide : Asteroides) {
			asteroide.incrementaPos(retardo);
		}
	}
	
	class ThreadJuego extends Thread {
		@Override
		public void run() {
			while (true) {
				actualizaFisica();
			}
		}
	}

}
