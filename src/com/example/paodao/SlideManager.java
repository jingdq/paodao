package com.example.paodao;

import android.content.Context;
import android.content.Intent;

import java.util.LinkedList;

/**
 * Created by jingdongqi on 7/1/15.
 */
public class SlideManager {

    public LinkedList<SlideModel> showCacheQueueArrForLocal;
    public LinkedList<SlideModel> showCacheQueueArrForGlobal;
    public LinkedList<SlideModel> listCacheQueueArr;
    public boolean couldBeShowGlobalRightNow;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private Context mContext;


    private static SlideManager instance;
    private  SlideManager(){
        initSlideData();
    }

    private void initSlideData() {
        showCacheQueueArrForLocal = new LinkedList<SlideModel>();
        showCacheQueueArrForGlobal = new LinkedList<SlideModel>();
        listCacheQueueArr = new LinkedList<SlideModel>();
        couldBeShowGlobalRightNow = true;

    }

    public static SlideManager getInstance(){
        if (instance == null){
            instance = new SlideManager();
        }
        return instance;
    }


    public void addNewBroadcastBroadcastLocalForTest(){

        SlideModel model = SlideModel.initWithSomeSample(SlideModel.AEAR_SHOW_TYPE.LOCAL_SHOW);
        if (this.showCacheQueueArrForGlobal.size() <=1){
            this.showCacheQueueArrForLocal.add(model);
        }


        Intent it = new Intent();
        it.setAction(Constant.Notification_refreshBroadcatView);
        it.putExtra("show_type",0);
        mContext.sendBroadcast(it);

    }

    public  void addNewBroadcastBroadcastGlobalForTest(){

        SlideModel model = SlideModel.initWithSomeSample(SlideModel.AEAR_SHOW_TYPE.GLOBAL_SHOW);
        this.showCacheQueueArrForGlobal.add(model);
        Intent it = new Intent();
        it.setAction(Constant.Notification_refreshBroadcatView);
        it.putExtra("show_type",1);
        mContext.sendBroadcast(it);




    }


    public void addNewHistoryBroadcast(SlideModel model){
        listCacheQueueArr.add(0, model);
    }

    public SlideModel getCurrentShowmodel(){
        if (showCacheQueueArrForGlobal.size()!=0){
            return showCacheQueueArrForGlobal.pop();
        }
        if (showCacheQueueArrForLocal.size()!=0){

            return showCacheQueueArrForLocal.pop();
        }
        return  listCacheQueueArr.getFirst();
    }



}
