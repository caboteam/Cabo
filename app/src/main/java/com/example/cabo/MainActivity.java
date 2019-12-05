package com.example.cabo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

//import android.content.Context;
import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.wifi.p2p.WifiP2pManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

//    private final IntentFilter intentFilter = new IntentFilter();
//    WifiP2pManager.Channel channel;
//    WifiP2pManager manager;

//    public void playerLayout4(View view) {
//        Intent intent = new Intent(getApplicationContext(), playerLayout4.class);
//        startActivity(intent);
//    }

    public void playerLayout5(View view) {
        Intent intent = new Intent(getApplicationContext(), playerLayout5.class);
        startActivity(intent);
    }

//    public void playerLayout6(View view) {
//        Intent intent = new Intent(getApplicationContext(), playerLayout6.class);
//        startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout background = (ConstraintLayout) findViewById(R.id.background);
        AnimationDrawable animation = (AnimationDrawable) background.getBackground();

        animation.setEnterFadeDuration(0);
        animation.setExitFadeDuration(3000);

        animation.start();


//        // Indicates a change in the Wi-Fi P2P status.
//        //intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
//
//        // Indicates a change in the list of available peers.
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
//
//        // Indicates the state of Wi-Fi P2P connectivity has changed.
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
//
//        // Indicates this device's details have changed.
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
//
//        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
//        channel = manager.initialize(this, getMainLooper(), null);
    }
}
