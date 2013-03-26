package es.edu.android.asteroides.helpers;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Clase manejadora de la reproducción en segundo plano de la música
 * 
 * @author e.astolfi
 */
public class MyMediaPlayer extends MediaPlayer {
	private Context ctx;
	private MediaPlayer mp;
	private int resId;
	
	/**
	 * Constructor en el que iniciamos las variables de la clase
	 * @param ctx
	 * @param resId
	 */
	public MyMediaPlayer(Context ctx, int resId) {
		this.ctx = ctx;
		this.resId = resId;
		
		mp = MediaPlayer.create(ctx, resId);
	}
	
	@Override
	public void start() {
		if (mp == null) {
			mp = MediaPlayer.create(ctx, resId);
		}
		if (!mp.isPlaying()) {
			mp.start();
		}
	}
	
	@Override
	public void pause() {
		if (mp != null && mp.isPlaying()) {
			mp.pause();
		}
	}
	
	@Override
	public void stop() {
		if (mp != null && mp.isPlaying()) {
			mp.stop();
		}
	}
}
