package com.example.opengl;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MyTDView m_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.m_View = new MyTDView(this);
        this.m_View.requestFocus();
        this.m_View.setFocusableInTouchMode(true);
        this.setContentView(this.m_View);
    }


    protected void onPause () {

        super.onPause();
        this.m_View.onPause();
    }

    protected void onResume () {

        super.onResume();
        this.m_View.onResume();
    }
}
