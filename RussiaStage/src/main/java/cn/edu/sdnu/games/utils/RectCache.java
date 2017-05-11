package cn.edu.sdnu.games.utils;

import android.graphics.Rect;

import java.util.LinkedList;

/**
 * rect cache
 * Created by simon liu on 17-5-11.
 */

public class RectCache {

    private static final int MAX_SIZE = 5;

    static {
        rectCache = new LinkedList<>();
    }

    private static LinkedList<Rect> rectCache;

    public static Rect getRect() {
        Rect poll = rectCache.poll();
        if (poll != null) {
            return poll;
        } else {
            return new Rect();
        }
    }

    public static void cache(Rect rect) {
        if (rectCache.size() < MAX_SIZE) {
            rectCache.push(rect);
        }
    }

}
