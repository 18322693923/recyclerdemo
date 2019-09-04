package com.test.recyclerdemo.inter;

import android.view.View;

import com.test.recyclerdemo.bean.Province;

public interface OnItemViewClickListener {
    void setOnViewClickListener(View view, int position, Province.City city);
}
