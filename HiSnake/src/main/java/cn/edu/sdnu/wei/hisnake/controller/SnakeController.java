package cn.edu.sdnu.wei.hisnake.controller;

import cn.edu.sdnu.wei.hisnake.model.Food;
import cn.edu.sdnu.wei.hisnake.model.Ground;
import cn.edu.sdnu.wei.hisnake.model.Snake;
import cn.edu.sdnu.wei.hisnake.utils.U;
import cn.edu.sdnu.wei.hisnake.view.StageView;

/**
 * 蛇的控制器
 * Created by simon liu on 2015/8/17.
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

        //检测有没有吃到食物
        if(snake.isGetFood(food)){
            snake.eatFood();
            stageView.addScore();//加分
            U.createOneFood(snake,food);
        }

        //检测有没有死掉
        if(snake.checkDie(ground)){
            snake.stop();
        }


        stageView.invalidate();
    }

    @Override
    public void turnUp() {
        snake.changeDirection(Snake.UP);
    }

    @Override
    public void turnDown() {
        snake.changeDirection(Snake.DOWN);

    }

    @Override
    public void turnLeft() {
        snake.changeDirection(Snake.LEFT);
    }

    @Override
    public void turnRight() {
        snake.changeDirection(Snake.RIGHT);
    }
}
