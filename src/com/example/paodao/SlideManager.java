package com.example.paodao;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.*;

/**
 * Created by jingdongqi on 7/1/15.
 */
public class SlideManager {

    public static ArrayList<SlideModel> showCacheQueueArrForLocal;
    public static ArrayList<SlideModel> showCacheQueueArrForGlobal;
    public static ArrayList<SlideModel> listCacheQueueArr;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private Context mContext;


    private static SlideManager instance;

    private SlideManager() {
        initSlideData();
    }

    private void initSlideData() {
        showCacheQueueArrForLocal = new ArrayList<SlideModel>();
        showCacheQueueArrForGlobal = new ArrayList<SlideModel>();
        listCacheQueueArr = new ArrayList<SlideModel>();


    }

    public static SlideManager getInstance() {
        if (instance == null) {
            instance = new SlideManager();
        }
        return instance;
    }


    public void addNewBroadcastBroadcastLocalForTest() {

        SlideModel model = SlideModel.initWithSomeSample(SlideModel.AEAR_SHOW_TYPE.LOCAL_SHOW);
        this.showCacheQueueArrForLocal.add(model);
        if (SlideManager.getInstance().showCacheQueueArrForGlobal.size()==0){
            Intent it = new Intent();
            it.setAction(Constant.Notification_refreshBroadcatView);
            it.putExtra("show_type", 0);
            mContext.sendBroadcast(it);
        }



    }

    Timer senderTimer;


    public void addNewBroadcastBroadcastGlobalForTest() {

        SlideModel model = SlideModel.initWithSomeSample(SlideModel.AEAR_SHOW_TYPE.GLOBAL_SHOW);
        this.showCacheQueueArrForGlobal.add(model);
        if (showCacheQueueArrForGlobal.size() > 1) {//晚一秒钟再发送 ，这里创建一个线程,每隔一秒中发送一次广播

            if (senderTimer == null) {
                senderTimer = new Timer();
                senderTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (showCacheQueueArrForGlobal.size() > 1) {
                            Intent it = new Intent();
                            it.setAction(Constant.Notification_refreshBroadcatView);
                            it.putExtra("show_type", 1);
                            mContext.sendBroadcast(it);
                            Log.e("jingdq", " >>>>>  showCacheQueueArrForGlobal.size() " + showCacheQueueArrForGlobal.size() + " +addNewBroadcastBroadcastGlobalForTest ");
                        } else {
                            senderTimer.cancel();
                            senderTimer = null;
                        }


                    }
                }, 1000, 1000);
            }


        } else {
            Intent it = new Intent();
            it.setAction(Constant.Notification_refreshBroadcatView);
            it.putExtra("show_type", 1);
            mContext.sendBroadcast(it);


        }


    }


    public void addNewHistoryBroadcast(SlideModel model) {

        if (listCacheQueueArr.contains(model))return;

        while (listCacheQueueArr.size()>4){
            listCacheQueueArr.remove(listCacheQueueArr.size()-1);
        }

            listCacheQueueArr.add(0,model);
    }

    public SlideModel getCurrentShowmodel() {
        if (showCacheQueueArrForGlobal.size() != 0) {
            return showCacheQueueArrForGlobal.get(0);
        }
        if (showCacheQueueArrForLocal.size() != 0) {

            return showCacheQueueArrForLocal.get(0);
        }
        return listCacheQueueArr.get(listCacheQueueArr.size()-1);
    }


}
