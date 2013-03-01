package es.edu.android.asteroides.graphics;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import es.edu.android.asteroides.R;

/**
 * Clase manejadora de la pantalla donde se ejecutara el juego en sí
 * 
 * @author e.astolfi
 */
public class VistaJuego extends View implements SensorEventListener {
	/********* ASTEROIDES **********/
    private Vector<Grafico> Asteroides; // Vector con los Asteroides
    private int numAsteroides= 5; // Número inicial de asteroides
    private int numFragmentos= 3; // Fragmentos en que se divide
    /********* NAVE **********/
    private Grafico nave;// Gráfico de la nave
    private int giroNave; // Incremento de dirección
    private float aceleracionNave; // aumento de velocidad
    private float mX = 0, mY = 0;
    private boolean disparo = false;
    // Incremento estándar de giro y aceleración
    private static final int PASO_GIRO_NAVE = 5;
    private static final float PASO_ACELERACION_NAVE = 0.5f;
    // //// MISIL //////
    private Grafico misil;
    private static int PASO_VELOCIDAD_MISIL = 12;
    private boolean misilActivo = false;
    private int tiempoMisil;
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
		
		SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		if (!sensorList.isEmpty()) {
			Sensor orientationSensor = sensorList.get(0);
			mSensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_GAME);
			
		}
		
		//Creamos la nave, asteroides y misiles a partir de un Drawable
		Drawable drawableNave, drawableAsteroide, drawableMisil;
		drawableAsteroide = getResources().getDrawable(R.drawable.asteroide1);
		drawableNave = getResources().getDrawable(R.drawable.nave_small);
		ShapeDrawable dMisil = new ShapeDrawable(new RectShape());
		dMisil.getPaint().setColor(Color.WHITE);
		dMisil.getPaint().setStyle(Style.STROKE);
		dMisil.setIntrinsicWidth(15);
		dMisil.setIntrinsicHeight(3);
		drawableMisil = dMisil;
		
		//Inicializamos la nave
		nave = new Grafico(this, drawableNave);
		
		//Inicializamos el misil
		misil = new Grafico(this, drawableMisil);
		
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
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	private boolean hasInitialVal = false;
    private float initialVal;
	@Override
	public void onSensorChanged(SensorEvent event) {
		float valor = event.values[1];
		if (!hasInitialVal) {
			initialVal = valor;
			hasInitialVal = true;
		}
		giroNave = (int) ((valor - initialVal) / 3);
	}
	
	@Override
	public boolean onTouchEvent (MotionEvent event) {
		super.onTouchEvent(event);
		
		float x = event.getX();
		float y = event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			disparo = true;
			break;
		case MotionEvent.ACTION_MOVE:
			float dX = Math.abs(x - mX);
			float dY = Math.abs(y - mY);
			if (dY < 3 && dX > 3) {
				giroNave = Math.round((x - mX) / 5);
				disparo = false;
			}
			else if (dX < 3 && dY > 3) {
				aceleracionNave = Math.round((mY - y) / 30);
				disparo = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			giroNave = 0;
			aceleracionNave = 0;
			if (disparo) {
				ActivaMisil();
			}
			break;
		}
		mX = x;
		mY = y; 
//		actualizaFisica();
		return true;
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
		
		//Si existe, dibujamos el misil
		if (misilActivo) misil.dibujaGrafico(canvas);
		
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
		// Actualizamos posiciones X e Y de la nave
		nave.incrementaPos(retardo);
		
		//Actualizamos posiciones X e Y del misil
		if (misilActivo) {
			misil.incrementaPos(retardo);
			tiempoMisil -= retardo;
			if (tiempoMisil < 0) {
				misilActivo = false;
			}
			else {
				for (int i=0; i<Asteroides.size(); i++) {
					if (misil.verificaColision(Asteroides.elementAt(i))) {
						destruyeAsteroide(i);
						break;
					}
				}
			}
		}
		
		for (Grafico asteroide : Asteroides) {
			asteroide.incrementaPos(retardo);
		}
	}
	
	private void destruyeAsteroide(int pos) {
		Asteroides.remove(pos);
		misilActivo = false;
	}
	
	private void ActivaMisil() {
		misil.setPosX(nave.getPosX() + nave.getAncho() / 2 - misil.getAncho() / 2);
		misil.setPosY(nave.getPosY() + nave.getAlto() / 2 - misil.getAlto() / 2);
		misil.setAngulo(nave.getAngulo());
		misil.setIncX(Math.cos(Math.toRadians(misil.getAngulo())) * PASO_VELOCIDAD_MISIL);
		misil.setIncY(Math.sin(Math.toRadians(misil.getAngulo())) * PASO_VELOCIDAD_MISIL);
		
		tiempoMisil = (int) Math.min(this.getWidth() / Math.abs(misil.getIncX()),
							this.getHeight() / Math.abs(misil.getIncY())) - 2;
		misilActivo = true;
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
