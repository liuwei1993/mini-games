package cn.edu.sdnu.games.utils;

import android.util.Log;

/**
 * Created by Administrator on 2015/8/7.
 */
public class L {
    public static boolean LOG_OPEN = true;
    public static void e(Class clazz,String tag,String log){
        if(LOG_OPEN)
        Log.e(tag,clazz.getSimpleName()+"----->>>>>>"+log);
    }
    public static void d(Class clazz,String tag,String log){
        if(LOG_OPEN)
        Log.d(tag,clazz.getName()+"----->>>>>>"+log);
    }
}
