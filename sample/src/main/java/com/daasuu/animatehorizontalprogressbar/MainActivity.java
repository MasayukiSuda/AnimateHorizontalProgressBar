package com.daasuu.animatehorizontalprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daasuu.library.AnimateHorizontalProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.animate_progress_bar)
    AnimateHorizontalProgressBar mAnimateHorizontalProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mAnimateHorizontalProgressBar.setMax(1000);
        mAnimateHorizontalProgressBar.setProgressWithAnim(800);
    }

}
