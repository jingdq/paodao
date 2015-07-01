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

public class MyActivity extends BaseActivity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    TextView textView;
    TextView textView1;

    int srceenWidth;

    LinearLayout ll_content;
    Button bt2;//全球数据
    Button bt3;//本地数据


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test);
        bt2 = (Button) findViewById(R.id.button2);
        bt3 = (Button) findViewById(R.id.button3);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        srceenWidth = mDisplayMetrics.widthPixels;


    }

    private void initTextView() {

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

        switch (v.getId()) {
//            case  R.id.button:
////                ((MyApplication)getApplication()).paoDaoView.areaAnimate();
//                break;
//
//            case R.id.button1:
////                ((MyApplication)getApplication()).paoDaoView.localAnimate();
//                break;

            case R.id.button2://全国数据
                SlideManager.getInstance().setmContext(this);
                SlideManager.getInstance().addNewBroadcastBroadcastGlobalForTest();

                break;
            case R.id.button3://本地数据
                SlideManager.getInstance().setmContext(this);
                SlideManager.getInstance().addNewBroadcastBroadcastLocalForTest();
                break;

        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        ((MyApplication) getApplication()).addPaoDaoView();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ((MyApplication) getApplication()).removePaoDaoView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MyApplication) getApplication()).removePaoDaoView();
    }


}
