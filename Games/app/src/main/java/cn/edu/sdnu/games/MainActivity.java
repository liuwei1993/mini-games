package cn.edu.sdnu.games;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.edu.sdnu.games.view.RussiaStage;


public class MainActivity extends AppCompatActivity {

    private RussiaStage rs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rs = (RussiaStage) findViewById(R.id.rs);
    }
    public void restart(View v){
        rs.init();
    }
    public void turn(View v){
        rs.turn();
    }
    public void start(View v){
        if(rs.isRunning()){
            rs.pause();
            ((Button)v).setText("开始");
        }else{
            rs.start();
            ((Button)v).setText("暂停");
        }

    }
    public void left(View v){
        rs.left();
    }
    public void right(View v){
        rs.right();
    }
    public void down(View v){
        rs.downQuickly();
    }
}
