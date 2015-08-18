package cn.edu.sdnu.wei.hisnake.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;

import java.util.LinkedList;

import cn.edu.sdnu.wei.hisnake.controller.SnakeMoveListener;
import cn.edu.sdnu.wei.hisnake.utils.Constants;
import cn.edu.sdnu.wei.hisnake.utils.L;

/**
 * Created by wei on 2015/8/17.
 */
public class Snake {

    public final static int UP = 1;
    public final static int DOWN = -1;
    public final static int LEFT = -2;
    public final static int RIGHT = 2;


    private LinkedList<Point> body;
    private SnakeMoveListener listener;
    private boolean isAlive = true;
    private int currDirection, newDirection;//当前前进方向和下一步的方向
    private SnakeHandler handler = new SnakeHandler();
    //初始化一条蛇


    public void setListener(SnakeMoveListener listener) {
        this.listener = listener;
    }

    public void init() {
        int x = Constants.WIDTH_ITEM_COUNTS / 2;
        int y = Constants.HEIGHT_ITEM_COUNTS / 2;
        body = new LinkedList<>();
        body.add(new Point(x, y));
        body.add(new Point(--x, y));
        body.add(new Point(--x, y));
        currDirection = newDirection = RIGHT;//初始化为向右走
        handler.startWork();
    }

    /**
     * 启动
     */
    public void start(){
        handler.startWork();
    }
    /**
     * 停止
     */
    public void stop(){
        handler.stopWork();
    }

    /**
     * 新生成的食物是否在我身上
     *
     * @param food
     * @return
     */
    public boolean isTheFoodOnMe(Food food) {
        boolean flag = false;
        for (Point point : body) {
            if (point.equals(food.x, food.y)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 设置下一步方向
     *
     * @param direction
     */
    public void changeDirection(int direction) {
        newDirection = direction;
        L.d(getClass(), "event", "方向改变" + direction);
    }

    private Point oldTail;

    public void move() {
        if (newDirection + currDirection != 0) {
            currDirection = newDirection;//只有当两个方向不相反，才会更改当前方向
        }
        //1.去尾
        oldTail = body.removeLast();

        int x = body.getFirst().x;
        int y = body.getFirst().y;
        switch (currDirection) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
            default:
                break;

        }
        Point newHead = new Point(x, y);
        //根据方向计算新蛇头的坐标
        //2.加头
        body.addFirst(newHead);


    }

    /**
     * move之后调用，将丢掉的尾巴拿回来即可。
     */
    public void eatFood() {
        body.addLast(oldTail);
    }

    /**
     * 检测死亡
     * @return
     */
    public boolean checkDie(Ground ground) {
        if (isEatBody()) {
            isAlive = false;
            return true;
        } else if (isEatBrick(ground)) {
            isAlive = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断蛇是否活着
     * @return 活着返回true 否则false
     */
    public boolean isAlive(){
        return isAlive;
    }

    /**
     * 判断是否吃了自己的身体
     *
     * @return
     */
    private boolean isEatBody() {
        Point head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            if (body.get(i).equals(head.x, head.y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否吃到了砖头
     *
     * @param ground
     * @return
     */
    private boolean isEatBrick(Ground ground) {
        Point head = body.getFirst();
        return ground.isAtBrick(head.x, head.y);
    }

    /**
     * 提供给自定义view来绘制自己
     * @param canvas 画布
     * @param paint 画笔
     * @param w 单位方块的宽
     * @param h 单位方块的高
     */
    public void drawMe(Canvas canvas, Paint paint, int w, int h) {
        paint.setColor(Color.BLUE);
        for (Point p : body) {
            canvas.drawRect(p.x * w, p.y * h, p.x * w + w, p.y * h + h, paint);
        }
    }

    /**
     * 检测有没有得到食物
     * @param food 食物
     * @return
     */
    public boolean isGetFood(Food food) {
        return  body.getFirst().equals(food.x,food.y);
    }

    class SnakeHandler extends Handler {
        private boolean handlerWork;

        @Override
        public void handleMessage(Message msg) {
            if (handlerWork) {
                move();
                if (listener != null) {
                    listener.onMove();
                }
                sendEmptyMessageDelayed(0, 700);
            }
        }

        public void startWork() {
            handlerWork = true;
            sendEmptyMessageDelayed(0, 700);
        }

        public void stopWork() {
            handlerWork = false;
        }

    }
}
