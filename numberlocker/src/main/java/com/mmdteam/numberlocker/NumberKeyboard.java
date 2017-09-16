package com.mmdteam.numberlocker;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by brain on 2017/9/16.
 * 密码数字键盘
 */

public class NumberKeyboard extends ViewGroup {
    public NumberKeyboard(Context context) {
        super(context);
    }

    public NumberKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumberKeyboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NumberKeyboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_keyboard, this, false);
        GridView gridView = rootView.findViewById(R.id.keyboard_grid_view);
        gridView.setAdapter(new NumberAdapter());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
