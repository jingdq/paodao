package com.example.paodao;

import android.animation.ObjectAnimator;
//import android.animation.PropertyValuesHolder;
//import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.*;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.animation.AnimatorProxy;

import javax.xml.transform.Transformer;

public class MyActivity extends BaseActivity implements View.OnClickListener{
    /**
     * Called when the activity is first created.
     */
    TextView textView;
    TextView textView1;

    int srceenWidth;

    LinearLayout ll_content;
    Button bt;//全球
    Button bt1;//本地
    Button bt2;//全球数据
    Button bt3;//本地数据


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test);
        bt = (Button) findViewById(R.id.button);
        bt1 = (Button) findViewById(R.id.button1);
        bt2 = (Button) findViewById(R.id.button2);
        bt3 = (Button) findViewById(R.id.button3);
        bt.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        srceenWidth = mDisplayMetrics.widthPixels;
//        startAnimate();

        ((MyApplication)getApplication()).addPaoDaoView();

        }

    private void initTextView() {
//        textView = (TextView) findViewById(R.id.textView);
//        textView.setOnClickListener(this);
//        textView1 = (TextView) findViewById(R.id.textView1);
//        textView1.setOnClickListener(this);

        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        srceenWidth = mDisplayMetrics.widthPixels;
//        int height = mDisplayMetrics.heightPixels;
//        float density = mDisplayMetrics.density;
//        int densityDpi = mDisplayMetrics.densityDpi;
//        LinearLayout.LayoutParams parmas = (LinearLayout.LayoutParams) textView.getLayoutParams();
//        parmas.leftMargin=srceenWidth;
//        textView.setLayoutParams(parmas);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case  R.id.button:
                ((MyApplication)getApplication()).paoDaoView.areaAnimate();
                break;

            case R.id.button1:
                ((MyApplication)getApplication()).paoDaoView.localAnimate();
                break;

            case R.id.button2://全国数据

                break;
            case R.id.button3://本地数据

                break;

        }


    }

//    private void animate() {
//        PropertyValuesHolder p = PropertyValuesHolder.ofFloat("TranslationX", srceenWidth ,-700);
//        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(p);
//        valueAnimator.setDuration(5000);
//        valueAnimator.setInterpolator(new LinearInterpolator());
////        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                                            @Override
//                                            public void onAnimationUpdate(ValueAnimator animation) {
//                                                float x = (Float) animation
//                                                        .getAnimatedValue();
//                                                ll_content.setTranslationX(x);
//
//                                            }
//
//
//                                        }
//
//        );
//
//
//        valueAnimator.start();
//    }

    @Override
    protected void onPause() {
        super.onPause();

//        ((MyApplication)getApplication()).removePaoDaoView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MyApplication)getApplication()).removePaoDaoView();
    }


    //    public void startAnimate() {
//
////        ValueAnimator squashAnim1 = com.nineoldandroids.animation.ObjectAnimator.ofFloat(bt, "x", srceenWidth,
////                -srceenWidth);
//        final FrameLayout.LayoutParams oldParams = (FrameLayout.LayoutParams) ll_content.getLayoutParams();
//
//        PropertyValuesHolder p = PropertyValuesHolder.ofFloat("TranslationX", srceenWidth ,-srceenWidth);
//        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(p);
//        valueAnimator.setDuration(6000);
//        valueAnimator.setInterpolator(new LinearInterpolator());
////        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
////        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        valueAnimator.setRepeatCount(10);
////        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                                            @Override
//                                            public void onAnimationUpdate(ValueAnimator animation) {
////                                                float x = (Float) animation
////                                                        .getAnimatedValue();
//                                                float x = (float) animation.getAnimatedValue("TranslationX");
////                                                ll_content.setTranslationX(x);
////                                                ll_content.setX(x);
//
//                                                ViewHelper.setTranslationX(ll_content,x);
//                                                ll_content.postInvalidate();
//
//                                                Log.i("jingdq", "x : " + x);
//
////                                                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ll_content.getLayoutParams();
////                                                params.leftMargin = (int) x;
////                                                ll_content.setLayoutParams(params);
////                                                ll_content.postInvalidate();
////                                                if (x == -srceenWidth) {
////                                                   ll_content.setLayoutParams(oldParams);
////                                                }
//
//
//                                            }
//
//
//                                        }
//
//        );
//
//
//        valueAnimator.start();
//    }


}
