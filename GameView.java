package com.example.andrew.ballbuster;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.MainThread;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class GameView extends SurfaceView implements Runnable {

    private volatile boolean running;
    private Thread gameThread = null;

    private boolean gameOver = false;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    private BallManager ballManager;

    private Rect r = new Rect();

    int score = 0;


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
        if(ballManager.getPlayStatus() && !gameOver) {
            ballManager.update();
        }
        for(Ball ball : ballManager.balls) {
            if(ball.getRectangle().top >= Constants.SCREEN_HEIGHT) {
                gameOver = true;
            }
        }
    }

    public void draw() {

        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            paint.setColor(Color.argb(255,0,0,0));
            canvas.drawColor(Color.argb(255,0,0,0));

            paint.setTextSize(150);
            paint.setColor(Color.argb(255,255,255,255));
            canvas.drawText("score: " + score, 10, 100, paint);

            ballManager.draw(canvas);


            if(!ballManager.getPlayStatus() && !gameOver) {
                Paint paint1 = new Paint();
                paint1.setTextSize(100);
                paint1.setColor(Color.WHITE);
                drawCenter(canvas, paint1, "Touch to Start");
            }

            if(gameOver) {
//                Paint flushScreen = new Paint();
//                paint.setColor(Color.BLACK);
//                canvas.drawRect(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT, flushScreen);
                drawCenter(canvas, paint, "Game Over");
            }

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
        if(!ballManager.getPlayStatus()) {
            ballManager.switchPlayStatus();
        }

        switch(motionEvent.getAction()) {

            case(MotionEvent.ACTION_DOWN):
                Log.d("onTouch", "click");
                int x = (int)motionEvent.getX();
                int y = (int)motionEvent.getY();

                for(Iterator<Ball> ite = ballManager.balls.listIterator(); ite.hasNext();) {
                    Ball ball = ite.next();
                    if(ball.getRectangle().contains(x,y)){
                        try {
                            ite.remove();
                            score++;
                        } catch (Exception e) {
                            Log.d("GameView", "Error in onTouch");
                            e.printStackTrace();
                            break;
                        }
                    }


                }
            }

        return true;
    }

    private void drawCenter(Canvas canvas, Paint paint, String text) {
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
