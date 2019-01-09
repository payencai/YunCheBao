package com.payencai.library.http.task;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by payencai on 2018/5/20.
 */

public class PosterHandler extends Handler {
    private static PosterHandler instance;
    public static PosterHandler getInstance(){
        if(instance==null)
            synchronized (PosterHandler.class){
                if (instance==null)
                    instance=new PosterHandler();
            }

        return instance;
    }
    private PosterHandler(){
        super(Looper.getMainLooper());
    }
}
