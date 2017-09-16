package com.mmdteam.numberlocker;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by brain on 2017/9/16.
 * number keyboard adapter
 */

public class NumberAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView textView = new TextView(view.getContext());
        switch (i) {
            case 0:
                textView.setText("1");
                break;
            case 1:
                textView.setText("2");
                break;
            case 2:
                textView.setText("3");
                break;
            case 3:
                textView.setText("4");
                break;
            case 4:
                textView.setText("5");
                break;
            case 5:
                textView.setText("6");
                break;
            case 6:
                textView.setText("7");
                break;
            case 7:
                textView.setText("8");
                break;
            case 8:
                textView.setText("9");
                break;
            case 9:
                textView.setText("");
                break;
            case 10:
                textView.setText("0");
                break;
            case 11:
                textView.setText("");
                break;
        }
        return textView;
    }
}
