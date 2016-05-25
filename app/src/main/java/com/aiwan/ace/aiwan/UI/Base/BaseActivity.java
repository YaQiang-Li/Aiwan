package com.aiwan.ace.aiwan.UI.Base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiwan.ace.aiwan.R;

/**
 * Created by ACE on 2016/3/8.
 */
public abstract class BaseActivity extends Activity {
    protected ImageView topLeftBtn;
    protected ImageView topRightBtn;
    protected TextView topTitleTxt;
    protected TextView leftTitleTxt;
    protected ViewGroup topBar;
    protected ViewGroup topContentView;
    protected LinearLayout baseAct;
    protected float x1, y1, x2, y2 = 0;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        topContentView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.activity_base, null);
        topBar = (ViewGroup) topContentView.findViewById(R.id.topbar);
        topTitleTxt = (TextView) topContentView.findViewById(R.id.base_act_title);
        topLeftBtn = (ImageView) topContentView.findViewById(R.id.left_btn);
        topRightBtn = (ImageView) topContentView.findViewById(R.id.right_btn);
        leftTitleTxt = (TextView) topContentView.findViewById(R.id.left_txt);
        baseAct = (LinearLayout) topContentView.findViewById(R.id.act_base);

        topTitleTxt.setVisibility(View.GONE);
        topRightBtn.setVisibility(View.GONE);
        leftTitleTxt.setVisibility(View.GONE);
        topLeftBtn.setVisibility(View.GONE);

        setContentView(topContentView);
    }

    protected void setLeftText(String text) {
        if (null == text) {
            return;
        }
        leftTitleTxt.setText(text);
        leftTitleTxt.setVisibility(View.VISIBLE);
    }

    protected void setTitle(String title) {
        if (null == title) {
            return;
        }
        if (title.length() > 12) {
            title = title.substring(0, 11) + "...";
        }
        topTitleTxt.setText(title);
        topTitleTxt.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTitle(int id) {
        String strTitle = getResources().getString(id);
        setTitle(strTitle);
    }

    protected void setTopLeftButton(int resID) {
        if (resID < 0) {
            return;
        }

        topLeftBtn.setImageResource(resID);
        topLeftBtn.setVisibility(View.VISIBLE);
    }

    protected void setRightButton(int resID) {
        if (resID < 0) {
            return;
        }

        topRightBtn.setImageResource(resID);
        topRightBtn.setVisibility(View.VISIBLE);
    }

    protected void setTopBar(int resID) {
        if (resID < 0) {
            return;
        }

        topBar.setBackgroundResource(resID);
    }
}
