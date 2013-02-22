package es.edu.android.asteroides.graphics;

import java.util.Iterator;
import java.util.Vector;

import es.edu.android.asteroides.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

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
    
    
	public VistaJuego(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		Drawable drawableNave, drawableAsteroide, drawableMisil;
		drawableAsteroide = getResources().getDrawable(R.drawable.asteroide1);
		drawableNave = getResources().getDrawable(R.drawable.nave_small);
		nave = new Grafico(this, drawableNave);
		
		Asteroides = new Vector<Grafico>();
		
		for (int i=0; i<numAsteroides; i++) {
			Grafico asteroide = new Grafico(this, drawableAsteroide);
			asteroide.setIncX(Math.random() * 4 - 2);
			asteroide.setIncY(Math.random() * 4 - 2);
			asteroide.setAngulo((int) (Math.random() * 360));
			asteroide.setRotacion((int) (Math.random() * 8 - 4));
			Asteroides.add(asteroide);
		}
	}
	
	@Override
	protected void onSizeChanged(int ancho, int alto, int ancho_anter, int alto_anter) {
		super.onSizeChanged(ancho, alto, ancho_anter, alto_anter);
		
		nave.setPosX(ancho / 2 -48);
		nave.setPosY(alto / 2 -48);
		
		// Una vez que conocemos nuestro ancho y alto.
		for(Grafico asteroide : Asteroides) {
			do {
				asteroide.setPosX(Math.random() * (ancho-asteroide.getAncho()));
				asteroide.setPosY(Math.random() * (alto-asteroide.getAlto()));
			} while(asteroide.distancia(nave) < (alto+ancho)/5);
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		nave.dibujaGrafico(canvas);
		
		for(Grafico asteroide : Asteroides) {
			asteroide.dibujaGrafico(canvas);
		}
	}

}
