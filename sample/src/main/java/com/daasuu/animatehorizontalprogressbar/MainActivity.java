package com.daasuu.animatehorizontalprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.daasuu.ahp.AnimateHorizontalProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.animate_progress_bar)
    AnimateHorizontalProgressBar mProgressBarAnimProgress;
    @Bind(R.id.txt_progress)
    TextView mTxtProgress;
    @Bind(R.id.txt_max)
    TextView mTxtMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mProgressBarAnimProgress.setMax(1000);
        mProgressBarAnimProgress.setProgress(400);

        mProgressBarAnimProgress.setAnimateProgressListener(new AnimateHorizontalProgressBar.AnimateProgressListener() {
            @Override
            public void onAnimationStart(int progress, int max) {
                // do nothing
            }

            @Override
            public void onAnimationEnd(int progress, int max) {
                mTxtProgress.setText(getString(R.string.progress, progress));
                mTxtMax.setText(getString(R.string.max, max));
            }
        });

    }

    @OnClick(R.id.btn_progress_anim_0)
    public void onClickProgress0() {
        mProgressBarAnimProgress.setProgressWithAnim(800);
    }

    @OnClick(R.id.btn_progress_anim_1)
    public void onClickProgress1() {
        mProgressBarAnimProgress.setProgressWithAnim(400);
    }

    @OnClick(R.id.btn_max_anim_0)
    public void onClickMax0() {
        mProgressBarAnimProgress.setMaxWithAnim(2000);
    }

    @OnClick(R.id.btn_max_anim_1)
    public void onClickMax1() {
        mProgressBarAnimProgress.setMaxWithAnim(3000);
    }

    @OnClick(R.id.btn_max_anim_2)
    public void onClickMax2() {
        mProgressBarAnimProgress.setMaxWithAnim(1000);
    }

}
