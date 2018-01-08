package com.example.andrew.ballbuster;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.nfc.Tag;
import android.support.annotation.MainThread;
import android.util.Log;

import java.util.ArrayList;

public class BallManager {

    private ArrayList<Ball> balls;
    private long startTime;
    private long initTime;
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
        if(isPlaying) {
            int elapsedTime = (int)(System.currentTimeMillis() - startTime);
            startTime = System.currentTimeMillis();
            float speed = (float)(-0.5 + Math.sqrt((startTime - initTime)/1500.0))*Constants.SCREEN_HEIGHT/(10000.0f);
            for(Ball ball : balls) {
                ball.incrementY(speed * elapsedTime);
            }
            if(balls.get(balls.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
                int startX = (int) (Math.random() * (Constants.SCREEN_WIDTH - ballHeight));
                balls.add(0, new Ball(startX, (int) (balls.get(0).getRectangle().top - ballHeight - ballGap), ballHeight, color));
            }
        }
    }

    public void draw(Canvas canvas) {
        for(Ball ball : balls) {
            ball.draw(canvas);
        }
    }

    public void switchPlayStatus() {
        isPlaying = !isPlaying;
    }

    public boolean getPlayStatus() {
        return isPlaying;
    }
}
