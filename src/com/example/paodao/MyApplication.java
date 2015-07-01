package com.example.paodao;

import android.app.Application;
import android.app.Service;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by jingdongqi on 6/30/15.
 */
public class MyApplication extends Application {

    public static PaoDaoWindow paoDaoView;

    @Override
    public void onCreate() {
        super.onCreate();

    }




    @Override
    public void onTerminate() {
        super.onTerminate();

        removePaoDaoView();
    }

    public void addPaoDaoView(){
        if (paoDaoView == null){
            paoDaoView = new PaoDaoWindow(this);
//            paoDaoView.startAreaAnimate();


        }}


        public void removePaoDaoView(){
            if (paoDaoView !=null){
              paoDaoView.removeView();
                paoDaoView = null;
            }


    }

    }










