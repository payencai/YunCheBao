package com.example.yunchebao.view.dialog;

import java.util.Observable;


/**
 * Author: yangweichao
 * Date:   2018/8/14 上午10:37
 * Description: 全局dialog 被观察者
 */


public class DialogObservable extends Observable {

    public void showDialog(String msg) {
        //改变数据
        setChanged();
        //通知
        notifyObservers(msg);
    }
}
