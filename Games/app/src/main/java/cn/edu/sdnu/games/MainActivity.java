package cn.edu.sdnu.games;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.edu.sdnu.games.view.RussiaStage;


public class MainActivity extends AppCompatActivity {

    private RussiaStage rs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rs = (RussiaStage) findViewById(R.id.rs);
    }
    public void turn(View v){
        rs.turn();
    }
    public void start(View v){
        rs.start();
    }
    public void left(View v){
        rs.left();
    }
    public void right(View v){
        rs.right();
    }
    @Override
    protected void onStop() {
        super.onStop();
        rs.stop();
    }
}
