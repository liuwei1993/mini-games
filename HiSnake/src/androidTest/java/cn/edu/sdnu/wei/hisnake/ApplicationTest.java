package cn.edu.sdnu.wei.hisnake;

import android.app.Application;
import android.test.ApplicationTestCase;

import cn.edu.sdnu.wei.hisnake.model.Food;
import cn.edu.sdnu.wei.hisnake.model.Snake;
import cn.edu.sdnu.wei.hisnake.utils.L;
import cn.edu.sdnu.wei.hisnake.utils.U;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testRandom(){
        Snake  snake = new Snake();
        Food food = new Food();
        snake.init();
        for (int i = 0; i < 100; i++) {
            L.d(getClass(), "test", "" + U.createOneFood(snake, food));
        }
    }
}