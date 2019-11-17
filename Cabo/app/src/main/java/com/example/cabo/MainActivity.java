package com.example.cabo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    }
}
