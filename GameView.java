package com.example.andrew.ballbuster;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.MainThread;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private volatile boolean running;
    private Thread gameThread = null;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    private BallManager ballManager;

    Context context;
    long startFrameTime;
    long timeThisFrame;
    long fps;

    public GameView(Context context) {
        super(context);
        this.context = context;

        ourHolder = getHolder();
        paint = new Paint();

        ballManager = new BallManager(Color.WHITE, 100, 200);
    }

    @Override
    public void run() {

        while(running) {
            startFrameTime = System.currentTimeMillis();

            update();
            draw();

            //calculate fps
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if(timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    public void update() {
        //add collision and game over
        ballManager.update();
    }

    public void draw() {

        //super.draw(canvas);
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            paint.setColor(Color.argb(255,0,0,0));
            canvas.drawColor(Color.argb(255,0,0,0));

            paint.setTextSize(150);
            paint.setColor(Color.argb(255,255,255,255));
            canvas.drawText("score: ", 10, 100, paint);

            ballManager.draw(canvas);

            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        running = false;
        try{
            gameThread.join();
        } catch(InterruptedException e) {
            Log.e("error", "failed to pause thread");
        }
    }

    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }
}
