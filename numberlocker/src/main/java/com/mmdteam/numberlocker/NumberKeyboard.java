package com.mmdteam.numberlocker;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by brain on 2017/9/16.
 * 密码数字键盘
 */

public class NumberKeyboard extends LinearLayout implements AdapterView.OnItemClickListener {
    public NumberKeyboard(Context context) {
        super(context);
        init();
    }

    private TextView passwordOne, passwordTwo, passwordThree, passwordFour;
    private int currentNum = 0;
    private int pOne, pTwo, pThree, pFour;

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
        passwordTwo = findViewById(R.id.password_two);
        passwordThree = findViewById(R.id.password_three);
        passwordFour = findViewById(R.id.password_four);
        gridView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (currentNum > 4) {
            return;
        }

        int cacheNum = 0;
        switch (i) {
            case 0:
                cacheNum = 1;
                break;
            case 1:
                cacheNum = 2;
                break;
            case 2:
                cacheNum = 3;
                break;
            case 3:
                cacheNum = 4;
                break;
            case 4:
                cacheNum = 5;
                break;
            case 5:
                cacheNum = 6;
                break;
            case 6:
                cacheNum = 7;
                break;
            case 7:
                cacheNum = 8;
                break;
            case 8:
                cacheNum = 9;
                break;
            case 9:
                break;
            case 10:
                cacheNum = 0;
                break;
            case 11:
                break;
        }
        currentNum++;
        takePassword(currentNum, cacheNum);
        if (currentNum == 4) {
            checkPassword(pOne, pTwo, pThree, pFour);
        }
    }

    private void takePassword(int index, int value) {
        switch (index) {
            case 1:
                pOne = value;
                passwordOne.setSelected(true);
                break;
            case 2:
                pTwo = value;
                passwordTwo.setSelected(true);
                break;
            case 3:
                pThree = value;
                passwordThree.setSelected(true);
                break;
            case 4:
                pFour = value;
                passwordFour.setSelected(true);
                break;
        }
    }

    private void checkPassword(int... password) {
    }
}
