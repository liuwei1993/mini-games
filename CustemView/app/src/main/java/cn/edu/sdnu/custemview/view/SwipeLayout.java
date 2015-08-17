package cn.edu.sdnu.custemview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 *
 * Created by wei on 2015/8/17.
 */
public class SwipeLayout extends FrameLayout {

    private View contentView;
    private View menuView;
    private int contentWidth,menuWidth,viewHeight;
    private Scroller scroller;

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    /**
     * 得到子视图
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        menuView = getChildAt(1);
    }

    /**
     * 在onMeasure方法中获取视图宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        contentWidth = contentView.getMeasuredWidth();
        menuWidth = menuView.getMeasuredWidth();
        viewHeight = menuView.getMeasuredHeight();
    }

    /**
     * 对子视图进行重新布局
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        menuView.layout(contentWidth,0,contentWidth+menuWidth,viewHeight);
    }


    /**
     *
     * @param event
     * @return
     */

    private int lastX,downX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
            int eventX = (int) event.getRawX();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = eventX;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = eventX - lastX;
                //计算滑动目标偏移量
                int scrollToX = getScrollX() - dx;
                //限制偏移量
                if(scrollToX<0){
                    scrollToX = 0;
                }
                if(scrollToX>menuWidth){
                    scrollToX = menuWidth;
                }
                //滑动
                scrollTo(scrollToX,getScrollY());
                lastX = eventX;
                break;
            case MotionEvent.ACTION_UP:
                //抬起时，如果偏移量超过menuWidth一半则平滑打开，否则平滑关闭
                if(getScrollX()>menuWidth/2){
                    open();
                }else{
                    close();
                }
                break;
        }
        return true;
    }

    private void close() {
        scroller.startScroll(getScrollX(),getScrollY(),-getScrollX(),getScrollY());
        invalidate();
    }

    private void open() {
        scroller.startScroll(getScrollX(),getScrollY(),menuWidth-getScrollX(),getScrollY());
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //当滑动超过5像素的时候
        int eventX = (int) ev.getRawX();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = eventX;
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(downX-eventX)>5){
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return false;
    }
}
