package com.example.andrew.ballbuster;


import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.nfc.Tag;
import android.support.annotation.MainThread;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class BallManager{

    public ArrayList<Ball> balls;
    public ListIterator<Ball> ite;
    private long startTime;
    private long initTime;
    private int elapsedTime;
    int color;
    int ballHeight;
    int ballGap;

    private boolean isPlaying = false;

    public BallManager(int color, int ballHeight, int ballGap) {
        this.color = color;
        this.ballHeight = ballHeight;
        this.ballGap = ballGap;

        startTime = initTime = System.currentTimeMillis();

        balls = new ArrayList<>();

        populateBalls();
    }

    private void populateBalls() {
        int currY = -5*Constants.SCREEN_HEIGHT/4;
        while(currY < 0) {
            int startX = (int)(Math.random()*(Constants.SCREEN_WIDTH - ballHeight));
            balls.add(new Ball(startX, currY, ballHeight, color));
            currY += ballHeight + ballGap;
        }
    }

    public void update() {
//            elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float) (-0.5 + Math.sqrt((startTime - initTime) / 1500.0)) * Constants.SCREEN_HEIGHT / (10000.0f);
        try {
            for (Ball ball : balls) {
                ball.incrementY(speed * 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (balls.get(balls.size() - 1).getRectangle().top >= 0) {
            int startX = (int) (Math.random() * (Constants.SCREEN_WIDTH - ballHeight));
            balls.add(0, new Ball(startX, (int) (balls.get(0).getRectangle().top - ballHeight - ballGap), ballHeight, color));
        }
    }

    public void draw(Canvas canvas) {
        for(Iterator<Ball> ite = balls.listIterator(); ite.hasNext();){
            try {
                Ball ball = ite.next();
                ball.draw(canvas);
            } catch (Exception e) {
                //breaks out of for loop on error
                Log.d("BallManager", "Error in draw");
                break;
            }
        }
    }


    public void switchPlayStatus() {
        isPlaying = !isPlaying;
    }

    public boolean getPlayStatus() {
        return isPlaying;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }
}
