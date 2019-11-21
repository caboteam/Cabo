package com.example.cabo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void playerLayout4(View view) {
        Intent intent = new Intent(getApplicationContext(), playerLayout4.class);
        startActivity(intent);
    }

    public void playerLayout5(View view) {
        Intent intent = new Intent(getApplicationContext(), playerLayout5.class);
        startActivity(intent);
    }

    public void playerLayout6(View view) {
        Intent intent = new Intent(getApplicationContext(), playerLayout6.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout background = (ConstraintLayout) findViewById(R.id.background);
        AnimationDrawable animation = (AnimationDrawable) background.getBackground();

        animation.setEnterFadeDuration(0);
        animation.setExitFadeDuration(3000);

        animation.start();

//        AnimationDrawable butt_anim = (AnimationDrawable) findViewById(R.id.button2).getBackground();
//        butt_anim.setEnterFadeDuration(3000);
//        butt_anim.setExitFadeDuration(6000);
//        butt_anim.start();Q


    }
}
