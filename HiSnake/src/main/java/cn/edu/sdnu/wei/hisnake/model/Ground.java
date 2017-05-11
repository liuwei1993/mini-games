package cn.edu.sdnu.wei.hisnake.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import cn.edu.sdnu.wei.hisnake.utils.Constants;

/**
 * Created by wei on 2015/8/17.
 */
public class Ground {

    private boolean[][] chessboard;//这是一个矩阵，true代表有石头，false代表没有
    {
        chessboard = new boolean[Constants.HEIGHT_ITEM_COUNTS][Constants.WIDTH_ITEM_COUNTS];
        for (int i = 0; i < Constants.HEIGHT_ITEM_COUNTS; i++) {
            chessboard[i][0] = true;
            chessboard[i][Constants.WIDTH_ITEM_COUNTS-1] = true;
        }
        for (int i = 0; i < Constants.WIDTH_ITEM_COUNTS; i++) {
            chessboard[0][i] = true;
            chessboard[Constants.HEIGHT_ITEM_COUNTS-1][i] = true;
        }
    }

    public boolean isAtBrick(int x,int y){
        return chessboard[y][x];
    }

    public void drawMe(Canvas canvas,Paint paint,int w,int h){
        paint.setColor(Color.GREEN);
        for (int i = 0; i < Constants.HEIGHT_ITEM_COUNTS; i++) {
            for (int j = 0; j < Constants.WIDTH_ITEM_COUNTS; j++) {
                if(chessboard[i][j]){
                    canvas.drawRect(j*w,i*h,(j+1)*w,(i+1)*h,paint);
                }
            }
        }
    }


}
