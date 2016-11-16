package com.jayce.week7homeworktea01.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.jayce.week7homeworktea01.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        splash = (ImageView) findViewById(R.id.splash);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.2f, 1, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(2500);

        scaleAnimation.setFillAfter(true);

        //设置监听
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束进行跳转
                //跳转到导航界面

                //取出sp中数据
                SharedPreferences sp = getSharedPreferences("appConfig",MODE_PRIVATE);

                boolean isFirst = sp.getBoolean("isFirst",true);

                if(isFirst){
                    //Ctrl+Shift+上下键
                    SplashActivity.this
                            .startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                }else {//不是第一次，用户已经导航过
                    SplashActivity.this
                            .startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }
                SplashActivity.this.finish();//当前的Activity消亡
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //启动动画
        splash.startAnimation(scaleAnimation);

    }
}
