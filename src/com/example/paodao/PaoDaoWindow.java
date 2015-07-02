package com.example.paodao;

//import android.animation.PropertyValuesHolder;
//import android.animation.ValueAnimator;

//import android.animation.ObjectAnimator;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.animation.LinearInterpolator;
import android.widget.*;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.LinkedList;

/**
 * Created by jingdongqi on 6/30/15.
 */
public class PaoDaoWindow implements View.OnClickListener {
    private View addView;
    int srceenWidth;

    LinearLayout ll_content;
    Button button;
    WindowManager wm;
    WindowManager.LayoutParams wl;
    TextView senderTv;
    TextView receiverTv;
    TextView tv_senderLocation;
    TextView tv_receiverLocation;
    TextView tv_time;
    TextView tv_flower;

    Button historyBt;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    Context mContext;

    public enum MSGSTATE {
        AREA, LOCAL
    }


    private MSGSTATE currentState;


    private ViewGroup mParentView;

    private ListView historyListView;

    private FrameLayout.LayoutParams historyListViewParam;


    private boolean isCanClearn = false;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.Notification_refreshBroadcatView)) {
                final int showType = intent.getIntExtra("show_type", -1);
                if (showType == 1) {//全国
//                    if (SlideManager.getInstance().showCacheQueueArrForLocal.size() != 0) {
//                        SlideModel model = SlideManager.getInstance().showCacheQueueArrForLocal.get(0);
//                        collectSlideModel(model, SlideManager.getInstance().showCacheQueueArrForLocal);


                    setUpAllSlide(showType);


//                    }

                } else if (showType == 0) {//本地
                    if (SlideManager.getInstance().showCacheQueueArrForGlobal.size() == 0) {

                        if (SlideManager.getInstance().showCacheQueueArrForLocal.size() >= 2) {
                            SlideModel model = SlideManager.getInstance().showCacheQueueArrForLocal.get(0);
                            collectSlideModel(model, SlideManager.getInstance().showCacheQueueArrForLocal);
                        }
                        setUpAllSlide(showType);


                    }


                }

            }
        }

    };


    private void setUpAllSlide(final int showType) {


        ll_content.clearAnimation();
        ll_content.removeAllViews();
        mParentView.removeView(ll_content);
        ll_content = null;

        addNewView();

        setupSlide(showType);

        if (showType == 0) {//本地
            tv_senderLocation.setVisibility(View.GONE);
            tv_receiverLocation.setVisibility(View.GONE);
            ObjectAnimator localOA = ObjectAnimator.ofFloat(ll_content, "TranslationX", srceenWidth, -srceenWidth).setDuration(6000);
            localOA.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    SlideModel model = SlideManager.getInstance().showCacheQueueArrForLocal.getFirst();
                    SlideManager.getInstance().addNewHistoryBroadcast(model);
                    SlideManager.getInstance().showCacheQueueArrForLocal.remove(model);

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    if (SlideManager.getInstance().showCacheQueueArrForLocal.size() == 0) {
                        showHistoryDataView();

                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            localOA.start();

        } else if (showType == 1) {//全国
//            if (SlideManager.getInstance().showCacheQueueArrForGlobal.size() == 1) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(ll_content, "TranslationX", ll_content.getPivotX(), -srceenWidth).setDuration(3000);
            animator.setStartDelay(1000);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {


                    SlideModel model = SlideManager.getInstance().showCacheQueueArrForGlobal.getFirst();
                    SlideManager.getInstance().addNewHistoryBroadcast(model);
                    SlideManager.getInstance().showCacheQueueArrForGlobal.remove(model);
                    Log.e("jingdq", ">>>>showCacheQueueArrForGlobal.size() " + SlideManager.getInstance().showCacheQueueArrForGlobal.size());
                }

                @Override
                public void onAnimationEnd(Animator animator) {


                    if (SlideManager.getInstance().showCacheQueueArrForGlobal.size() == 0) {
                        if (SlideManager.getInstance().showCacheQueueArrForLocal.size() != 0) {
                            Intent it = new Intent();
                            it.setAction(Constant.Notification_refreshBroadcatView);
                            it.putExtra("show_type", 0);
                            mContext.sendBroadcast(it);
                        } else {
                            showHistoryDataView();

                        }

                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }

            });
            animator.start();
//            }

//            else if(SlideManager.getInstance().showCacheQueueArrForGlobal.size() > 1){
//
//
//                ObjectAnimator animator1 =   ObjectAnimator.ofFloat(ll_content,"alpha",1,0).setDuration(1000);
//                animator1.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animator) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animator) {
//                        setUpAllSlide(showType);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animator) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animator) {
//
//                    }
//
//                });
//                animator1.start();
//
//
//            }


        }


    }

    private void showHistoryDataView() {

        ll_content.clearAnimation();
        ll_content.removeAllViews();
        mParentView.removeView(ll_content);
        ll_content = null;
        addNewView();
        SlideModel model = SlideManager.getInstance().listCacheQueueArr.getLast();
        tv_time.setText(model.getShowTime());
        senderTv.setText(model.getFromNickname());
        receiverTv.setText(model.getToNickname());
        tv_flower.setText(model.getAmount() + "朵花");


        if (model.getShowType() == SlideModel.AEAR_SHOW_TYPE.GLOBAL_SHOW) {//全国
            tv_senderLocation.setVisibility(View.VISIBLE);
            tv_receiverLocation.setVisibility(View.VISIBLE);
            tv_senderLocation.setText(model.getFromCity());
            tv_receiverLocation.setText(model.getToCity());

            ObjectAnimator areaOA = ObjectAnimator.ofFloat(ll_content, "TranslationX", ll_content.getPivotX(), -srceenWidth).setDuration(3000);
            areaOA.setStartDelay(1000);
            areaOA.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                    animator.cancel();
                    animator.setStartDelay(1000);
                    animator.start();


                }
            });
            areaOA.setRepeatCount(ValueAnimator.INFINITE);
            areaOA.setRepeatMode(ValueAnimator.RESTART);
            areaOA.start();

        } else {
            tv_senderLocation.setVisibility(View.GONE);
            tv_receiverLocation.setVisibility(View.GONE);

            ObjectAnimator localOA = ObjectAnimator.ofFloat(ll_content, "TranslationX", srceenWidth, -srceenWidth).setDuration(6000);
            localOA.setRepeatCount(ValueAnimator.INFINITE);
            localOA.setRepeatMode(ValueAnimator.RESTART);
            localOA.start();

        }


    }


    private void setupSlide(int showType) {
        SlideModel model;
        if (showType == 0) {
            model = SlideManager.getInstance().showCacheQueueArrForLocal.getFirst();

        } else {
            model = SlideManager.getInstance().showCacheQueueArrForGlobal.getFirst();
        }
        tv_time.setText(model.getShowTime());
        senderTv.setText(model.getFromNickname());
        receiverTv.setText(model.getToNickname());
        tv_flower.setText(model.getAmount() + "朵花");


        if (showType == 1) {//全国
            tv_senderLocation.setText(model.getFromCity());
            tv_receiverLocation.setText(model.getToCity());


        } else {

        }


    }


    private void collectSlideModel(SlideModel model, LinkedList<SlideModel> arr) {
        SlideManager.getInstance().addNewHistoryBroadcast(model);
        if (SlideManager.getInstance().listCacheQueueArr.size() >= 6) {
            SlideManager.getInstance().listCacheQueueArr.removeLast();
        }

        //TODO 下拉内容的处理


        arr.remove(model);


    }


    public PaoDaoWindow(Context context) {
        this.mContext = context;
        initWindowParam(context);
        initView();
        addNewView();
        registerBoradcastReceiver();
    }


    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constant.Notification_refreshBroadcatView);
        //注册广播
        mContext.registerReceiver(mBroadcastReceiver, myIntentFilter);

    }


    private void initView() {
        mParentView = new FrameLayout(mContext);
        mParentView.setBackgroundColor(Color.GRAY);

        historyBt = new Button(mContext);
        historyBt.setText("MSG");
        historyBt.setWidth(20);
        historyBt.setHeight(20);
        historyBt.setBackgroundColor(Color.YELLOW);
        historyBt.setOnClickListener(this);
//        historyBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ListView listView = new ListView(mContext);
//                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 100);
//                params.gravity=Gravity.BOTTOM;
//                mParentView.addView(listView,params);
//                ObjectAnimator.ofFloat(listView,"TranslationY",0,listView.getHeight()).setDuration(1000).start();
//
//            }
//        });


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
//        mParentView.addView(historyBt, params);
        wm.addView(mParentView, wl);
    }

    private void addHistoryView() {
        if (historyListView == null) {
            historyListView = new ListView(mContext);
            historyListView.setBackgroundColor(Color.BLUE);
            historyListViewParam = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            historyListViewParam.topMargin = ll_content == null ? 0 : ll_content.getBottom();
            historyListView.setVisibility(View.GONE);
            mParentView.addView(historyListView, historyListViewParam);
        }

        SlideAdapter adapter = new SlideAdapter(mContext, SlideManager.getInstance().listCacheQueueArr);
        historyListView.setAdapter(adapter);

    }


    /**
     * 增加全国数据
     * <p/>
     * 逻辑：如果当前视图中正在显示本地数据，则立即显示本条全国数据
     * 如果当前试图中正在显示全国数据 ：分两种情况 1. 还没开始动画，则替换当前数据。2。已经开始动画，则停止动画，显示新的内容并且暂停一秒。
     *
     * @param data
     */
    public void addNewAreaData(String data) {


    }


    /**
     * 添加本地数据
     * 逻辑：
     * 如果当前正在显示全国数据，则等待当前的全国数据走场完毕，接着显示本地数据。
     * 如果当前显示本地数据，则撤销正在显示的本地数据动画，开启新的本地数据动画。
     */
    public void addLocalAreaData() {


    }


    public void addNewView() {

        addView = LayoutInflater.from(mContext).inflate(R.layout.main,
                null);
        ll_content = (LinearLayout) addView.findViewById(R.id.ll_content);
        senderTv = (TextView) addView.findViewById(R.id.tv_sender);
        senderTv.setOnClickListener(this);
        receiverTv = (TextView) addView.findViewById(R.id.tv_receiver);
        receiverTv.setOnClickListener(this);

        tv_senderLocation = (TextView) addView.findViewById(R.id.tv_senderLocation);
        tv_receiverLocation = (TextView) addView.findViewById(R.id.tv_receiverLocation);
        tv_time = (TextView) addView.findViewById(R.id.tv_time);
        tv_flower = (TextView) addView.findViewById(R.id.tv_flower);

        mParentView.addView(addView);

//        addHistoryView();

//        mParentView.bringChildToFront(historyBt);


    }

    private void initWindowParam(Context context) {
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
        srceenWidth = mDisplayMetrics.widthPixels;


        wl = new WindowManager.LayoutParams();
        wl.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wl.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY | WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        wl.x = 20;
        wl.y = 70;
        wl.width = WindowManager.LayoutParams.FILL_PARENT;
//        wl.height = 100;
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wl.gravity = Gravity.TOP;
    }


    public void removeChildView() {
        if (null != ll_content) {
            for (int i = 0; i < ll_content.getChildCount(); i++) {
                View childView = ll_content.getChildAt(i);
                mParentView.removeView(childView);
                childView = null;
            }
            mParentView.removeView(ll_content);
            ll_content = null;
        }


    }


    public void removeView() {
        if (addView != null)
            wm.removeView(mParentView);


    }

    @Override
    public void onClick(View v) {
        if (v == historyBt) {

//            addHistoryView();

            if (historyListView.getVisibility() == View.VISIBLE) {
                historyListView.setVisibility(View.GONE);
            } else {
                historyListView.setVisibility(View.VISIBLE);

            }

//            ObjectAnimator.ofFloat(listView,"TranslationY",0,listView.getHeight()).setDuration(1000).start();

            return;
        }

        switch (v.getId()) {
            case R.id.tv_sender:
                Log.i("PaoDaoView", "奔腾岁月");
            case R.id.tv_receiver:
                Log.i("PaoDaoView", "七号呆瓜");

                Intent it = new Intent(mContext, SecondActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(it);
                break;


        }


    }
}
