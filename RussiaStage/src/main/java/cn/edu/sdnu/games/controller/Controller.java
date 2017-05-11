package cn.edu.sdnu.games.controller;

import android.os.Handler;
import android.os.Message;

import cn.edu.sdnu.games.view.RussiaStage;

/**
 * controller
 * Created by simon liu on 2015/8/16.
 */
public class Controller extends Handler {

    public static final int GO = 0;

    private RussiaStage rs;

    public Controller(RussiaStage rs) {
        this.rs = rs;
    }

    public void go() {
        sendEmptyMessage(Controller.GO);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case GO:
                rs.go();
                break;
        }
    }
}
