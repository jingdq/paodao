package com.example.paodao;

//import android.animation.PropertyValuesHolder;
//import android.animation.ValueAnimator;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.LinkedList;

/**
 * Created by jingdongqi on 6/30/15.
 */
public class PaoDaoView extends View implements View.OnClickListener {
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

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    Context mContext;

    ValueAnimator valueAnimator;

    ValueAnimator valueAnimator1;


    public enum MSGSTATE {
        AREA, LOCAL
    }


    public void setCurrentState(MSGSTATE currentState) {

        if (this.currentState != currentState) {
            this.currentState = currentState;
            if (this.currentState == MSGSTATE.LOCAL) {
                localAnimate();
            } else if (this.currentState == MSGSTATE.AREA) {
                areaAnimate();
            }
        }

    }

    private MSGSTATE currentState;


    private ViewGroup mParentView;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.Notification_refreshBroadcatView)) {
                Toast.makeText(mContext, "处理 Notification_refreshBroadcatView 广播", Toast.LENGTH_SHORT).show();
                final int showType = intent.getIntExtra("show_type", -1);
                if (showType == 1) {//全国
//                    if (SlideManager.getInstance().showCacheQueueArrForLocal.size() != 0) {
//                        SlideModel model = SlideManager.getInstance().showCacheQueueArrForLocal.get(0);
//                        collectSlideModel(model, SlideManager.getInstance().showCacheQueueArrForLocal);
//
//
//                        setUpAllSlide(showType);
//
//                        if (SlideManager.getInstance().showCacheQueueArrForGlobal.size() >= 2) {
//
//
//                        }
//                        startAreaAnimate();
//
//
//                    }
                    setUpAllSlide(showType);
                    startAreaAnimate();

                } else if (showType == 0) {//本地
//                    if (SlideManager.getInstance().showCacheQueueArrForGlobal.size() == 0) {
//
//                        if (SlideManager.getInstance().showCacheQueueArrForLocal.size() >= 2) {
//                            SlideModel model = SlideManager.getInstance().showCacheQueueArrForLocal.get(0);
//                            collectSlideModel(model, SlideManager.getInstance().showCacheQueueArrForLocal);
//                        }
//
//
//                        setUpAllSlide(showType);
//                        startLocalAnimate();
//
//
//                    }

                    setUpAllSlide(showType);
                    startLocalAnimate();


                }

            }
        }

    };

    private void setUpAllSlide(int showType) {

        ll_content.removeAllViews();
        mParentView.removeAllViews();
        ll_content = null;

        addNewView();
        if (showType == 0) {//本地
            tv_senderLocation.setVisibility(View.GONE);
            tv_receiverLocation.setVisibility(View.GONE);

        } else if (showType == 1) {//全国


        }


    }


    private void setupSlide() {
//        SlideModel model = SlideManager.getInstance().getCurrentShowmodel();


    }


//    private void collectSlideModel(SlideModel model, LinkedList<SlideModel> arr) {
//        SlideManager.getInstance().addNewHistoryBroadcast(model);
//        if (SlideManager.getInstance().listCacheQueueArr.size() >= 6) {
//            SlideManager.getInstance().listCacheQueueArr.removeLast();
//        }
//
//        //TODO 下拉内容的处理
//
//        arr.remove(model);
//
//
//    }

    public PaoDaoView(Context context) {
        this(context, null);
    }

    public PaoDaoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaoDaoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initWindowParam(context);
        initView();
        addNewView();
//        startLocalAnimate();

//        currentState = MSGSTATE.LOCAL;

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
        wm.addView(mParentView, wl);
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
        ;
        tv_receiverLocation = (TextView) addView.findViewById(R.id.tv_receiverLocation);
        ;
        tv_time = (TextView) addView.findViewById(R.id.tv_time);
        ;
        tv_flower = (TextView) addView.findViewById(R.id.tv_flower);

        mParentView.addView(addView);


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
        wl.y = 20;
        wl.width = WindowManager.LayoutParams.FILL_PARENT;
        wl.height = 100;
//        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wl.gravity = Gravity.TOP | Gravity.RIGHT;
    }


    /**
     * 本地动画
     */
    public void localAnimate() {
//        if (currentState == MSGSTATE.LOCAL)
//            return;
        removeChildView();
        initView();
        if (valueAnimator1 != null) {
            valueAnimator1.cancel();
            valueAnimator1.removeAllListeners();
            valueAnimator1.end();
            valueAnimator1 = null;

        }

        addNewView();
        startLocalAnimate();
        currentState = MSGSTATE.LOCAL;

    }

    /**
     * 全国动画
     */
    public void areaAnimate() {

//        if (currentState == MSGSTATE.AREA)
//            return;

        removeChildView();
        initView();

        if (valueAnimator != null) {
            valueAnimator.cancel();
            valueAnimator.removeAllListeners();
            valueAnimator.end();
            valueAnimator = null;

        }

        addNewView();
        startAreaAnimate();
        currentState = MSGSTATE.AREA;

    }

    public void startLocalAnimate() {

        PropertyValuesHolder p = PropertyValuesHolder.ofFloat("TranslationX", srceenWidth, -srceenWidth);
        valueAnimator = ValueAnimator.ofPropertyValuesHolder(p);
        valueAnimator.setDuration(6000);

        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.e("aaa", "onAnimationStart");
                ll_content.postInvalidate();

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.e("aaa", "onAnimationEnd");

                ll_content.setLeft(srceenWidth / 2 - ll_content.getWidth() / 2);


            }

            @Override
            public void onAnimationCancel(Animator animator) {
                Log.e("aaa", "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                Log.e("aaa", "onAnimationRepeat");
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                            @Override
                                            public void onAnimationUpdate(ValueAnimator animation) {
                                                final float x = (Float) animation
                                                        .getAnimatedValue();


//
                                                if (null != ll_content)
                                                    ll_content.setTranslationX(x);

                                                //3.0以下

//                                                ViewHelper.setTranslationX(ll_content, x);
//                                                Log.i("jingdq", "x : " + x + " width : " + ll_content.getWidth());
//                                                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ll_content.getLayoutParams();
//                                                params.leftMargin = (int) x;
//                                                ll_content.setLayoutParams(params);
//                                                ll_content.postInvalidate();

                                            }


                                        }

        );


        valueAnimator.start();
    }


    public void startAreaAnimate() {


        PropertyValuesHolder p = PropertyValuesHolder.ofFloat("TranslationX", ll_content.getPivotX(), -srceenWidth);
        valueAnimator1 = ValueAnimator.ofPropertyValuesHolder(p);
        valueAnimator1.setDuration(3000);

        valueAnimator1.setInterpolator(new LinearInterpolator());
//        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

//        valueAnimator1.setRepeatCount(ValueAnimator.INFINITE);
//        valueAnimator1.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator1.addListener(new Animator.AnimatorListener() {
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

                if (currentState == MSGSTATE.AREA) {
                    animator.end();
                    animator.setStartDelay(1000);
                    animator.start();
                } else {
                    animator.end();
                }


            }
        });

        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                             @Override
                                             public void onAnimationUpdate(ValueAnimator animation) {
                                                 final float x = (Float) animation
                                                         .getAnimatedValue();


                                                 if (null != ll_content)
                                                     ll_content.setTranslationX(x);
//                                                ViewHelper.setTranslationX(ll_content, x);

                                                 //3.0以下
//                                                 Log.i("jingdq", "valueAnimator1 x : " + x + " width : " + ll_content.getWidth());
//                                                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ll_content.getLayoutParams();
//                                                params.leftMargin = (int) x;
//                                                ll_content.setLayoutParams(params);
//                                                ll_content.postInvalidate();

                                             }


                                         }

        );


        valueAnimator1.setStartDelay(1000);
        valueAnimator1.start();
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
        switch (v.getId()) {
            case R.id.tv_sender:
                Log.i("PaoDaoView", "奔腾岁月");


                break;

            case R.id.tv_receiver:
                Log.i("PaoDaoView", "七号呆瓜");
                break;
        }
        Intent it = new Intent(mContext, SecondActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(it);

    }
}
