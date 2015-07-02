package com.example.paodao;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by jingdongqi on 6/30/15.
 */
public class BaseActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ((MyApplication)getApplication()).addPaoDaoView();


    }


    @Override
    protected void onResume() {
        super.onResume();





    }

    @Override
    protected void onPause() {
        super.onPause();
//        ((MyApplication) getApplication()).removePaoDaoView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();




    }
}
