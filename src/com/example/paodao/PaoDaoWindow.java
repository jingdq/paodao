package com.example.paodao;

//import android.animation.PropertyValuesHolder;
//import android.animation.ValueAnimator;

//import android.animation.ObjectAnimator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by jingdongqi on 6/30/15.
 */
public class PaoDaoWindow implements View.OnClickListener {
    private View addView;
    private int srceenWidth;
    private LinearLayout ll_content;
    private  Button button;
    private WindowManager wm;
    private WindowManager.LayoutParams wl;
    private TextView senderTv;
    private TextView receiverTv;
    private TextView tv_senderLocation;
    private TextView tv_receiverLocation;
    private TextView tv_time;
    private TextView tv_flower;

    private Button historyBt;
    private ImageView historyIv;

    private  boolean showDropDown = false;



    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private Context mContext;

    public enum MSGSTATE {
        AREA, LOCAL
    }

    private MSGSTATE currentState;
    private ViewGroup mParentView;
    private ListView historyListView;
    private FrameLayout.LayoutParams historyListViewParam;
    private static final int localOATime = 5000;
    private static final int areaOATime = 3000;


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.Notification_refreshBroadcatView)) {
                final int showType = intent.getIntExtra("show_type", -1);
                if (showType == 1) {//全国
                    setUpAllSlide(showType);
                } else if (showType == 0) {//本地
                    if (SlideManager.getInstance().showCacheQueueArrForGlobal.size() == 0) {
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


    }

    private void showHistoryDataView() {

        ll_content.clearAnimation();
        ll_content.removeAllViews();
        mParentView.removeView(ll_content);
        ll_content = null;
        addNewView();
        SlideModel model = SlideManager.getInstance().listCacheQueueArr.get(0);
        tv_time.setText(model.getShowTime());
        senderTv.setText(model.getFromNickname());
        receiverTv.setText(model.getToNickname());
        tv_flower.setText(model.getAmount() + "朵花");

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        ll_content.measure(w, h);
        int height = ll_content.getMeasuredHeight();
        int width = ll_content.getMeasuredWidth();

        if (model.getShowType() == SlideModel.AEAR_SHOW_TYPE.GLOBAL_SHOW) {//全国
            tv_senderLocation.setVisibility(View.VISIBLE);
            tv_receiverLocation.setVisibility(View.VISIBLE);
            tv_senderLocation.setText(model.getFromCity());
            tv_receiverLocation.setText(model.getToCity());

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ll_content.getLayoutParams();
            params.gravity = Gravity.CENTER_HORIZONTAL;
            ll_content.setLayoutParams(params);
            ll_content.setGravity(Gravity.CENTER_HORIZONTAL);


            ObjectAnimator areaOA = ObjectAnimator.ofFloat(ll_content, "TranslationX", ll_content.getPivotX(), -srceenWidth).setDuration(areaOATime);
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

            ObjectAnimator localOA = ObjectAnimator.ofFloat(ll_content, "TranslationX", srceenWidth, -width).setDuration(localOATime);
            localOA.setRepeatCount(ValueAnimator.INFINITE);
            localOA.setRepeatMode(ValueAnimator.RESTART);
            localOA.start();

        }


    }


    private void setupSlide(int showType) {

        SlideModel model;
        if (showType == 0) {//本地
            model = SlideManager.getInstance().showCacheQueueArrForLocal.get(SlideManager.getInstance().showCacheQueueArrForLocal.size() - 1);
        } else {//全国
            model = SlideManager.getInstance().showCacheQueueArrForGlobal.get(0);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ll_content.getLayoutParams();
            params.gravity = Gravity.CENTER_HORIZONTAL;
            ll_content.setLayoutParams(params);
            ll_content.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        tv_time.setText(model.getShowTime());
        senderTv.setText(model.getFromNickname());
        receiverTv.setText(model.getToNickname());
        tv_flower.setText(model.getAmount() + "朵花");


        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        ll_content.measure(w, h);
        int height = ll_content.getMeasuredHeight();
        int width = ll_content.getMeasuredWidth();


        if (showType == 0) {//本地
            tv_senderLocation.setVisibility(View.GONE);
            tv_receiverLocation.setVisibility(View.GONE);
            ObjectAnimator localOA = ObjectAnimator.ofFloat(ll_content, "TranslationX", srceenWidth, -width).setDuration(localOATime);
            localOA.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {


                    if (SlideManager.getInstance().showCacheQueueArrForLocal.size() == 0) {
                        showHistoryDataView();

                    } else {

                        for (int i = 0; i < SlideManager.getInstance().showCacheQueueArrForLocal.size(); i++) {
                            Log.e("aaa", "showCacheQueueArrForLocal.size() : " + SlideManager.getInstance().showCacheQueueArrForLocal.size());
                            SlideModel model = SlideManager.getInstance().showCacheQueueArrForLocal.get(i);
                            SlideManager.getInstance().addNewHistoryBroadcast(model);
                            updateHistoryView();
                            SlideManager.getInstance().showCacheQueueArrForLocal.remove(model);

                        }

                        if (SlideManager.getInstance().showCacheQueueArrForLocal.size() == 0) {
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
            localOA.start();

        } else if (showType == 1) {//全国
            tv_senderLocation.setText(model.getFromCity());
            tv_receiverLocation.setText(model.getToCity());
            ObjectAnimator animator = ObjectAnimator.ofFloat(ll_content, "TranslationX", ll_content.getPivotX(), -srceenWidth).setDuration(areaOATime);
            animator.setStartDelay(1000);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                    if (SlideManager.getInstance().showCacheQueueArrForGlobal.size() != 0) {
                        SlideModel model = SlideManager.getInstance().showCacheQueueArrForGlobal.get(0);
                        SlideManager.getInstance().addNewHistoryBroadcast(model);
                        updateHistoryView();
                        SlideManager.getInstance().showCacheQueueArrForGlobal.remove(model);
                        Log.e("aaa", ">>>>showCacheQueueArrForGlobal.size() " + SlideManager.getInstance().showCacheQueueArrForGlobal.size());

                    }

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


        }


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

        historyIv = new ImageView(mContext);
        historyIv.setMaxWidth(50);
        historyIv.setMaxHeight(50);
        historyIv.setBackgroundResource(R.drawable.find_filter_arrow);
        historyIv.setOnClickListener(this);


        historyListView = new ListView(mContext);
        historyListView.setBackgroundColor(Color.BLACK);
        historyListView.setVisibility(View.GONE);
        mParentView.addView(historyListView);


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin=8;
        params.rightMargin=8;
        params.gravity = Gravity.RIGHT;
        mParentView.addView(historyIv, params);
        wm.addView(mParentView, wl);
    }

    public void updateHistoryView() {


            historyListViewParam = (FrameLayout.LayoutParams) historyListView.getLayoutParams();
        if (historyListViewParam.topMargin ==0){
            historyListViewParam.topMargin = ll_content.getBottom();
            historyListView.setLayoutParams(historyListViewParam);

        }
            SlideAdapter adapter = new SlideAdapter(mContext, SlideManager.getInstance().listCacheQueueArr);
            historyListView.setAdapter(adapter);

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
        wl.x = 20;
        wl.y = 80;
        wl.width = WindowManager.LayoutParams.FILL_PARENT;
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wl.gravity = Gravity.TOP;
//        wl.type = 2002;
        wl.type = WindowManager.LayoutParams.TYPE_PHONE;
//        wl.format=1;
        /**
         *这里的flags也很关键
         *代码实际是wl.flags |= FLAG_NOT_FOCUSABLE;
         *40的由来是wl的默认属性（32）+ FLAG_NOT_FOCUSABLE（8）
         */
//        wl.flags = 40;


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
        if (v == historyIv) {

            if (historyListView == null ) {
                updateHistoryView();
            }


            if (historyListView.getVisibility() == View.VISIBLE) {
                showDropDown = false;
                ObjectAnimator.ofFloat(v, "rotation", 180, 0).setDuration(500).start();
//                ObjectAnimator o =  ObjectAnimator.ofFloat(historyListView, "TranslationY", historyListView.getBottom(), 0).setDuration(500);
//                o.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
                        historyListView.setVisibility(View.GONE);
//                    }
//                });
//
//                o.start();

            } else {
                showDropDown = true;
                ObjectAnimator.ofFloat(v, "rotation", 0, 180).setDuration(500).start();
//                ObjectAnimator oa =ObjectAnimator.ofFloat(historyListView, "TranslationY", 0, historyListView.getBottom()).setDuration(500);
//                oa.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
                        historyListView.setVisibility(View.VISIBLE);
//                    }
//                });
//                oa.start();

            }


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
