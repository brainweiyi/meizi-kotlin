package com.mmdteam.numberlocker;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by brain on 2017/9/16.
 * 密码数字键盘
 */

public class NumberKeyboard extends LinearLayout {
    public NumberKeyboard(Context context) {
        super(context);
        init();
    }

    private TextView passwordOne;

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

    private void init() {
        inflate(getContext(), R.layout.layout_keyboard, this);
        GridView gridView = findViewById(R.id.keyboard_grid_view);
        gridView.setAdapter(new NumberAdapter(getContext()));
        passwordOne = findViewById(R.id.password_one);
        passwordOne.setSelected(true);
    }

}
