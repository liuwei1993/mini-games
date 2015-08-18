package cn.edu.sdnu.wei.hisnake.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import cn.edu.sdnu.wei.hisnake.controller.SnakeController;
import cn.edu.sdnu.wei.hisnake.model.Food;
import cn.edu.sdnu.wei.hisnake.model.Ground;
import cn.edu.sdnu.wei.hisnake.model.Snake;
import cn.edu.sdnu.wei.hisnake.utils.Constants;
import cn.edu.sdnu.wei.hisnake.utils.U;

/**
 * Created by wei on 2015/8/17.
 */
public class StageView  extends View{

    private Paint paint;
    private Ground ground;
    private Food food;
    private Snake snake;

    private int itemWidth,itemHeight,viewWidth,viewHeight;
    private SnakeController controller;
    {
        ground = new Ground();
        snake = new Snake();
        food = new Food();
        controller = new SnakeController(snake,this,food,ground);
        snake.init(controller);
        U.createOneFood(snake, food);
        paint = new Paint();
        paint.setAntiAlias(true);
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
    }
}
