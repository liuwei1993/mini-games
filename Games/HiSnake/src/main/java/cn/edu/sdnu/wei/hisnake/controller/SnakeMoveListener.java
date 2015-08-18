package cn.edu.sdnu.wei.hisnake.controller;

/**
 * Created by wei on 2015/8/17.
 */
public interface SnakeMoveListener {
    void onMove();
    void turnUp();
    void turnDown();
    void turnLeft();
    void turnRight();
}
