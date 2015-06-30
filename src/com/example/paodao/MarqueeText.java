package com.example.paodao;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by jingdongqi on 6/29/15.
 */
public class MarqueeText extends TextView {


    public MarqueeText(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }


    public MarqueeText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }


    public MarqueeText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    @Override
    public boolean isFocused() {
        // TODO Auto-generated method stub
        return true;
    }
}