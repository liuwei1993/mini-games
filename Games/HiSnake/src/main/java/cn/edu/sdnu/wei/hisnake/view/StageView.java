package cn.edu.sdnu.wei.hisnake.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.sdnu.wei.hisnake.controller.SnakeController;
import cn.edu.sdnu.wei.hisnake.model.Food;
import cn.edu.sdnu.wei.hisnake.model.Ground;
import cn.edu.sdnu.wei.hisnake.model.Snake;
import cn.edu.sdnu.wei.hisnake.utils.Constants;
import cn.edu.sdnu.wei.hisnake.utils.L;
import cn.edu.sdnu.wei.hisnake.utils.U;

/**
 * Created by wei on 2015/8/17.
 */
public class StageView  extends View{

    private Paint paint;
    private Ground ground;
    private Food food;
    private Snake snake;
    private int score;

    private int itemWidth,itemHeight,viewWidth,viewHeight;
    private SnakeController controller;
    {
        ground = new Ground();
        snake = new Snake();
        food = new Food();
        controller = new SnakeController(snake,this,food,ground);
        snake.setListener(controller);
        paint = new Paint();
        paint.setAntiAlias(true);
        init();
    }
    public void init(){
        snake.init();
        U.createOneFood(snake, food);
        score = 0;
    }
    public void addScore(){
        score+=100;
    }
    public StageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        itemHeight = viewHeight/Constants.HEIGHT_ITEM_COUNTS;
        itemWidth = viewWidth/Constants.WIDTH_ITEM_COUNTS;
    }

    private int downX,downY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventX = (int) event.getRawX();
        int eventY = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                L.d(getClass(),"event","down "+eventX+" "+eventY);
                downX = eventX;
                downY = eventY;
                break;
            case MotionEvent.ACTION_UP:
                L.d(getClass(),"event","up "+eventX+" "+eventY);
                int dx = eventX-downX;
                int dy = eventY-downY;
                int absX = Math.abs(dx);
                int absY = Math.abs(dy);
                if(absX>50||absY>50){
                    if(absX>absY){
                        //水平滑动
                        if(dx>0){
                            //向右
                            controller.turnRight();
                        }else {
                            //向左
                            controller.turnLeft();
                        }
                    }else {
                        //垂直滑动
                        if(dy>0){
                            //向下
                            controller.turnDown();
                        }else {
                            //向上
                            controller.turnUp();
                        }
                    }
                }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画蛇
        snake.drawMe(canvas,paint,itemWidth,itemHeight);
        //画食物
        food.drawMe(canvas,paint,itemWidth,itemHeight);
        //画地砖
        ground.drawMe(canvas, paint, itemWidth, itemHeight);
        //画网格
        paint.setColor(Color.GRAY);
        for (int h = 0; h <= Constants.HEIGHT_ITEM_COUNTS; h++) {
            canvas.drawLine(0, h * itemHeight, viewWidth, h * itemHeight, paint);
        }
        for (int w = 0; w <= Constants.WIDTH_ITEM_COUNTS; w++) {
            canvas.drawLine(w * itemWidth, 0, w * itemWidth, viewHeight, paint);
        }
        //画分数
        paint.setColor(Color.RED);
        paint.setTextSize(40);
        String scr = String.valueOf(score);
        Rect bounds = new Rect();
        paint.getTextBounds(scr,0,scr.length(),bounds);
        canvas.drawText(scr,viewWidth-bounds.width()-6,bounds.height()+3,paint);
        //game over
        if(!snake.isAlive()){
            paint.setColor(Color.RED);
            paint.setTextSize(100);
            String text = "Game over!";
            Rect bound = new Rect();
            paint.getTextBounds(text,0,text.length(),bound);
            canvas.drawText(text,viewWidth/2-bound.width()/2,viewHeight/2+bounds.height()/2,paint);
        }
    }
}
