package cn.edu.sdnu.wei.hisnake.controller;

import cn.edu.sdnu.wei.hisnake.model.Food;
import cn.edu.sdnu.wei.hisnake.model.Ground;
import cn.edu.sdnu.wei.hisnake.model.Snake;
import cn.edu.sdnu.wei.hisnake.view.StageView;

/**
 * Created by wei on 2015/8/17.
 */
public class SnakeController implements SnakeMoveListener{
    private Snake snake;
    private StageView stageView;
    private Food food;
    private Ground ground;

    public SnakeController(Snake snake, StageView stageView, Food food, Ground ground) {
        this.snake = snake;
        this.stageView = stageView;
        this.food = food;
        this.ground = ground;
    }

    @Override
    public void onMove() {
        stageView.invalidate();
    }

    @Override
    public void onUp() {

    }

    @Override
    public void onDown() {

    }

    @Override
    public void onLeft() {

    }

    @Override
    public void onRight() {

    }
}
