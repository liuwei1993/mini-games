package cn.edu.sdnu.wei.hisnake.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by wei on 2015/8/17.
 */
public class Food extends Point {


    public void newFood(int x,int y){
        this.set(x,y);
    }


    public void drawMe(Canvas canvas,Paint paint,int w,int h){
        paint.setColor(Color.GRAY);
        canvas.drawRect(x*w,y*h,(x+1)*w,(y+1)*h,paint);
    }

}
