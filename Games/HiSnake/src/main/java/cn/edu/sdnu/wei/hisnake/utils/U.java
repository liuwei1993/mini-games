package cn.edu.sdnu.wei.hisnake.utils;

import cn.edu.sdnu.wei.hisnake.model.Food;
import cn.edu.sdnu.wei.hisnake.model.Snake;

/**
 * Created by Administrator on 2015/8/12.
 */
public class U {

    public static int randomInt(int min,int max){
        return (int)(Math.random()*(max-min+1))+min;
    }

    public static void createOneFood(Snake snake,Food food){
        do{
            food.newFood(randomInt(1,Constants.WIDTH_ITEM_COUNTS-2),randomInt(1,Constants.HEIGHT_ITEM_COUNTS-2));
        }while (snake.isTheFoodOnMe(food));
    }
}
