package com.daasuu.animatehorizontalprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daasuu.library.AnimateHorizontalProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.animate_progress_bar)
    AnimateHorizontalProgressBar mProgressBarAnimProgress;
    @Bind(R.id.animate_max_progress_bar)
    AnimateHorizontalProgressBar mProgressBarAnimMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mProgressBarAnimProgress.setMax(1000);
        mProgressBarAnimProgress.setProgress(200);
        mProgressBarAnimMax.setMax(1000);
        mProgressBarAnimMax.setProgress(800);
    }

    @OnClick(R.id.btn_progress_anim)
    public void onClickProgress() {
        mProgressBarAnimProgress.setProgressWithAnim(800);
    }

    @OnClick(R.id.btn_max_anim)
    public void onClickMax() {
        mProgressBarAnimMax.setMaxWithAnim(2000);
    }

}
