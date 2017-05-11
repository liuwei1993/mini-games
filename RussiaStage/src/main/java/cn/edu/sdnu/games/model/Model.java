package cn.edu.sdnu.games.model;

import android.graphics.Point;

import cn.edu.sdnu.games.utils.L;
import cn.edu.sdnu.games.utils.U;

/**
 * 游戏中下落的模块的数据模型
 * Created by simon liu on 2015/8/11.
 */
public class Model {
    public final Point[] items = new Point[4];
    public Model(){}

    public final static int TYPE_1 = 0;
    public final static int TYPE_2 = 1;
    public final static int TYPE_3 = 2;
    public final static int TYPE_4 = 3;
    public final static int TYPE_5 = 4;


    private int type = -1;

    {
        for (int i =0;i<items.length;i++) {
            items[i] = new Point();
        }
    }



    public void init(int StageWidth){
            Point baseP = this.items[0];
            int randomX = U.randomInt(2, StageWidth - 3);
            int y = -2;
            baseP.set(randomX, y);
            L.d(this.getClass(), "init", "basePoint坐标：（" + randomX + ":" + y + ")");
            type = U.randomInt(0,4);
            initByType(type);
    }

    /**
     * 向下移动一步
     */
    public void downOneStep(){
            for(Point p:items){
                p.y++;
            }
        L.d(getClass(), "move", "类型" + type + "移动一步，base坐标" + items[0]);
    }

    /**
     * 拷贝源Model数据覆盖当前Model数据
     * @param src
     */
    public void copy(Model src){
        this.type = src.type;
        for(int i = 0;i<items.length;i++){
            items[i].x = src.items[i].x;
            items[i].y = src.items[i].y;
        }
    }

    /**
     * 初始化
     * @param type
     */
    private void initByType(int type) {
        switch (type){
            case TYPE_1:
                //横条
                L.d(this.getClass(),"init","创建横条");
                items[1].set(items[0].x-1,items[0].y);
                items[2].set(items[0].x+1,items[0].y);
                items[3].set(items[0].x + 2, items[0].y);
                break;
            case TYPE_2:
                //三角
                L.d(this.getClass(),"init","创建三角");
                items[1].set(items[0].x,items[0].y-1);
                items[2].set(items[0].x-1,items[0].y);
                items[3].set(items[0].x+1,items[0].y);
                break;
            case TYPE_3:
                //L
                L.d(this.getClass(),"init","创建L");
                items[1].set(items[0].x-1,items[0].y);
                items[2].set(items[0].x+1,items[0].y);
                items[3].set(items[0].x+1,items[0].y-1);
                break;
            case TYPE_4:
                //Z
                L.d(this.getClass(),"init","创建Z");
                items[1].set(items[0].x+1,items[0].y);
                items[2].set(items[0].x,items[0].y-1);
                items[3].set(items[0].x-1,items[0].y-1);
                break;
            case TYPE_5:
                L.d(this.getClass(),"init","创建正方形的");
                items[1].set(items[0].x+1,items[0].y);
                items[2].set(items[0].x+1,items[0].y+1);
                items[3].set(items[0].x,items[0].y+1);
        }
    }
    //旋转90度
    public void turn(){
        if(type == TYPE_5){
            //type5不需要旋转
            return;
        }
        for (int i=1;i<items.length;i++){
            turnOneItem(items[0],items[i]);
        }
    }
    //传1是向左移动，传-1是向右
    public void moveX(int dx){
        for(int i=0;i<items.length;i++){
            items[i].x+=dx;
        }
    }


    private void turnOneItem(Point baseItem, Point tagItem) {
        int dx = tagItem.x - baseItem.x;
        int dy = tagItem.y - baseItem.y;
        // 右 右下 下 左下 左 左上 上 右上
        if(dx > 0 && dy ==0){
            //说明在正右方，旋转到正下方   y++
            tagItem.x = baseItem.x;
            tagItem.y = baseItem.y+dx;
        }else if(dx > 0 && dy >0){
            //说明在右下方，旋转到左下方     base的基础上 x-- y++
            tagItem.x = baseItem.x - dx;
            tagItem.y = baseItem.y + dx;
        }else if(dx == 0 && dy > 0){
            //说明在正下方，旋转到正左方     base的基础上 x--
            tagItem.x = baseItem.x - dy;
            tagItem.y = baseItem.y;
        }else if(dx < 0 && dy > 0){
            //说明在左下方，旋转到左上方     base的基础上 x-- y--
            tagItem.y = baseItem.y - dy;
            tagItem.y = baseItem.y - dy;
        }else if(dx < 0 && dy == 0){
            //说明在左方，旋转到上方         base的基础上 y--
            tagItem.x = baseItem.x;
            tagItem.y = baseItem.y + dx;
        }else if(dx < 0 && dy < 0){
            //说明在左上方，旋转到右上方     base的基础上 x++ y--
            tagItem.x = baseItem.x - dy;
            tagItem.y = baseItem.y + dy;
        }else if(dx == 0 && dy < 0){
            //说明在上方，旋转到右方        base的基础上  x++
            tagItem.x = baseItem.x - dy;
            tagItem.y = baseItem.y;
        }else if(dx > 0 && dy < 0){
            //说明在右上方，旋转到右下方     base的基础上 x++ y++
            tagItem.x = baseItem.x - dy;
            tagItem.y = baseItem.y - dy;
        }

    }

    @Override
    public String toString() {
        return items.toString();
    }
}
