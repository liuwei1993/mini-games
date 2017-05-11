package cn.edu.sdnu.games.utils;

/**
 * 生成随机数
 * Created by SimonLiu on 2015/8/12.
 */
public class U {

    public static int randomInt(int min,int max){
        return (int)(Math.random()*(max-min+1))+min;
    }
}
