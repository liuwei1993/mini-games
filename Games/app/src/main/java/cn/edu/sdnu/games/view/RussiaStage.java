package cn.edu.sdnu.games.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import cn.edu.sdnu.games.bean.Model;
import cn.edu.sdnu.games.utils.L;


/**
 * 用于展示俄罗斯游戏的舞台
 * Created by Administrator on 2015/8/11.
 */
public class RussiaStage extends View {

    public final static int WIDTH_ITEM_COUNTS = 12;
    public final static int HEIGHT_ITEM_COUNTS = 20;



    private boolean[][] chessboard;//这是一个矩阵，true代表有方块，false代表没有
    //使用链表来实现矩阵的功能
    private Paint paint;
    private float viewHeight;
    private float viewWidth;
    private float itemWidth;
    private float itemHeight;
    private boolean running;
    private boolean isOver;//游戏是否结束
    private int mainScore;

    private Thread downThread = new DownThread();

    private final Model modelForShow = new Model();//专门用来显示的model
    private final Model modelForDown = new Model();//down线程用来碰撞测试的model
    private final Model modelForHorizontal = new Model();//左右移动碰撞测试使用的
    {
        chessboard = new boolean[HEIGHT_ITEM_COUNTS][WIDTH_ITEM_COUNTS];
        paint = new Paint();
        paint.setAntiAlias(true);
        modelForShow.init(WIDTH_ITEM_COUNTS);
        invalidate();//重绘
    }

    public RussiaStage(Context context) {
        super(context);
    }

    public RussiaStage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RussiaStage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = getMeasuredHeight() - 4;
        viewWidth = getMeasuredWidth() - 4;
        itemHeight = viewHeight / HEIGHT_ITEM_COUNTS;
        itemWidth = viewWidth / WIDTH_ITEM_COUNTS;
    }



    private void drawRect(int x, int y, Canvas canvas) {
        canvas.drawRect(x * itemWidth, y * itemHeight, x * itemWidth + itemWidth, y * itemHeight + itemHeight, paint);
    }


    private void drawModel(Canvas canvas) {
        for (int i = 0; i < modelForShow.items.length; i++) {
            int x = modelForShow.items[i].x;
            int y = modelForShow.items[i].y;

            if (x >= 0 && x < WIDTH_ITEM_COUNTS && y >= 0 && y < HEIGHT_ITEM_COUNTS) {
                drawRect(x, y, canvas);
            }
        }
    }


    public void turn() {
        modelForHorizontal.copy(modelForShow);//拷贝
        modelForHorizontal.turn();
        if(checkCanChange(modelForHorizontal)){
            modelForShow.turn();
            invalidate();//重绘
        }
    }


    public void start() {
        if (!running) {
            downThread.start();
            running = true;
        }
    }

    public void stop() {
        running = false;
    }

    public void puase(){

    }

    public void left(){
        modelForHorizontal.copy(modelForShow);
        modelForHorizontal.moveX(-1);
        if(checkCanChange(modelForHorizontal)){
            modelForShow.moveX(-1);
            invalidate();
        }
    }
    public void right(){
        modelForHorizontal.copy(modelForShow);
        modelForHorizontal.moveX(1);
        if(checkCanChange(modelForHorizontal)){
            modelForShow.moveX(1);
            invalidate();
        }
    }

    /**
     * 传入显示的model，判断当前model是否可以被吃掉
     * @param showModel
     * @return canEat 可以吃返回true，否则返回false
     */
    private boolean checkCanEat(Model showModel){
        for(int i=0;i<showModel.items.length;i++){
            Point p = showModel.items[i];
            if(p.y+1==HEIGHT_ITEM_COUNTS){
                return true;//可以被吃掉
            }
            if(p.y+1>=0&&chessboard[p.y+1][p.x]){
                return true;//可以被吃掉
            }
        }
        //否则不能被吃
        return false;
    }

    /**
     * 传一个预计变化后的model，检测变化后的model是否合法
     * @param checkModel 预计变化后的model
     * @return state 可以变化返回true，不能则返回false
     */
    private boolean checkCanChange(Model checkModel){
        for(int i=0;i< checkModel.items.length;i++){
            if(!checkCanChange(checkModel.items[i])){
                //剩下两个状态一票否决
                return false;
            }
        }
        return true;
    }
    private boolean checkCanChange(Point p){
        //检测左右上下边界
        if(p.x<0||p.x>=WIDTH_ITEM_COUNTS||p.y>=HEIGHT_ITEM_COUNTS){
            return false;//返回不能变化
        }
        if(p.y<0){
            return true;//如果y值小于0，直接返回true因为棋牌外一定没有方块
        }
        //检测有没有和已经保留在棋盘上的方块有水平方向上的碰撞
        if(chessboard[p.y][p.x]){
            return false;//返回不能变化
        }
        return true;
    }

    /**
     * 将展示Model吃到棋盘中并显示，如果吃掉的model存在棋盘外的元素，表示game over
     * @param showModel
     * @return isOver 游戏结束返回true，未结束返回false
     */
    private boolean eat(Model showModel){
        for(int i=0;i<showModel.items.length;i++){
            if(showModel.items[i].y<0){
                return true;
            }
            chessboard[showModel.items[i].y][showModel.items[i].x] = true;
        }
        return false;
    }



    private int remove(){
        int score = 0;
        for (int i = 0;i<HEIGHT_ITEM_COUNTS;i++){
            boolean canRemove = true;//默认该行可以消除
            for(int j = 0;j<WIDTH_ITEM_COUNTS;j++){
                if(!chessboard[i][j]){
                    canRemove = false;//如果该行有一个空元素，则不能消除
                }
            }
            if(canRemove){
                score+=100;
                boolean[] temp = chessboard[i];
                for(int l = 0;l<temp.length;l++){
                    temp[l] = false;
                    SystemClock.sleep(20);
                    postInvalidate();
                }
                for (int k = i;k>1;k--){
                    chessboard[k] = chessboard[k-1];
                }
                chessboard[0] = temp;
            }
        }
        return score;
    }
    @Override
    protected void onDraw(Canvas canvas) {

        //画方块
        paint.setColor(Color.BLUE);
        drawModel(canvas);

        for (int i = 0; i < HEIGHT_ITEM_COUNTS; i++) {
            for (int j = 0; j < WIDTH_ITEM_COUNTS; j++) {
                if (chessboard[i][j]) {
                    drawRect(j, i, canvas);
                }
            }
        }

        //画网格
        paint.setColor(Color.GRAY);
        for (int h = 0; h <= HEIGHT_ITEM_COUNTS; h++) {
            canvas.drawLine(0, h * itemHeight, viewWidth, h * itemHeight, paint);
        }
        for (int w = 0; w <= WIDTH_ITEM_COUNTS; w++) {
            canvas.drawLine(w * itemWidth, 0, w * itemWidth, viewHeight, paint);
        }

        //如果游戏结束，屏幕中显示Game Over
        paint.setColor(Color.RED);
        if(isOver){
            paint.setTextSize(60);
            String over = "Game is Over ! !";
            Rect bounds = new Rect();
            paint.getTextBounds(over, 0, over.length(), bounds);
            canvas.drawText(over, viewWidth / 2 - bounds.width() / 2, viewHeight / 2 + bounds.height(), paint);
        }
        paint.setTextSize(20);
        Rect scoreBounds = new Rect();
        String score = mainScore + "";
        paint.getTextBounds(score, 0, score.length(),scoreBounds);
        canvas.drawText(score, viewWidth - scoreBounds.width() - 5, scoreBounds.height() + 5, paint);

    }


    class DownThread extends Thread {
        @Override
        public void run() {
            while (running&&!isOver) {
                //判断一下是否能移动
                modelForDown.copy(modelForShow);
                modelForDown.downOneStep();
                if(checkCanChange(modelForDown)){
                    L.d(getClass(), "check", "检测可以向下移动" + modelForDown.items[0]);
                    modelForShow.downOneStep();
                }else{
                    L.d(getClass(),"check","检测   不  可以向下移动"+modelForDown.items[0]);
                    if(checkCanEat(modelForShow)){
                        L.d(getClass(),"check","检测可以被吃掉");
                        if(eat(modelForShow)){
                            isOver = true;
                            running = false;
                            postInvalidate();
                            break;
                        }
                        //吃掉以后，重新初始化showModel
                        mainScore+=remove();
                        modelForShow.init(WIDTH_ITEM_COUNTS);
                    }
                }
                postInvalidate();
                SystemClock.sleep(400);
            }
        }
    }


}
