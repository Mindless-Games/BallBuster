package com.example.andrew.ballbuster;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Ball implements GameObject {

    private Rect r;
    private int color;
    private Paint paint;

    private int startX;
    private int startY;
    private int ballHeight;

    RectF a;

    Ball(int startX, int startY, int ballHeight, int color) {
        this.color = color;
        this.startX = startX;
        this.startY = startY;
        this.ballHeight = ballHeight;

        a = new RectF(startX, startY,startX + ballHeight,startY + ballHeight);
    }

    public RectF getRectangle() {
        return a;
    }

    public void incrementY(float y) {
        a.top += y;
        a.bottom += y;
    }

    @Override
    public void draw(Canvas canvas) {
        paint = new Paint();
        paint.setColor(color);
        canvas.drawOval(a, paint);
    }

    @Override
    public void update() {

    }
}
