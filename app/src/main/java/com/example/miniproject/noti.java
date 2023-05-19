package com.example.miniproject;

import java.util.Date;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.webkit.WebView;
import android.view.KeyEvent;
import android.util.Log;
import android.widget.Toast;

public class noti extends AppCompatActivity {


    public static final int MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 0;
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    public static final int MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS = 2;

    CallReceiver callReceiver;
    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(noti.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(noti.this, new String[]{Manifest.permission.READ_CALL_LOG}, MY_PERMISSIONS_REQUEST_READ_CALL_LOG);
        }

        // dynamically register CallReceiver
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(callReceiver, intentFilter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // manually unregister CallReceiver
        if (callReceiver != null) {
            unregisterReceiver(callReceiver);
            callReceiver = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CALL_LOG: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    Log.d("###", "READ_CALL_LOG granted!");
                    // check READ_PHONE_STATE permission only when READ_CALL_LOG is granted
                    if (ContextCompat.checkSelfPermission(noti.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // We do not have this permission. Let's ask the user
                        ActivityCompat.requestPermissions(noti.this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                    }
                } else {
                    // permission denied or has been cancelled
                    Log.d("###", "READ_CALL_LOG denied!");
                    Toast.makeText(getApplicationContext(), "missing READ_CALL_LOG", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    Log.d("###", "READ_PHONE_STATE granted!");
                    // check PROCESS_OUTGOING_CALLS permission only when READ_PHONE_STATE is granted
                    if (ContextCompat.checkSelfPermission(noti.this, Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED) {
                        // We do not have this permission. Let's ask the user
                        ActivityCompat.requestPermissions(noti.this, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS}, MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS);
                    }
                } else {
                    // permission denied or has been cancelled
                    Log.d("###", "READ_PHONE_STATE denied!");
                    Toast.makeText(getApplicationContext(), "missing READ_PHONE_STATE", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    Log.d("###", "PROCESS_OUTGOING_CALLS granted!");
                } else {
                    // permission denied or has been cancelled
                    Log.d("###", "PROCESS_OUTGOING_CALLS denied!");
                    Toast.makeText(getApplicationContext(), "missing PROCESS_OUTGOING_CALLS", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    class CallReceiver extends PhonecallReceiver {

        @Override
        protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
//            String msg = "start outgoing call: " + number + " at " + start;
//
//            Log.d("###", msg);
//            Toast.makeText(ctx.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
//            String msg = "end outgoing call: " + number + " at " + end;
//
//            Log.d("###", msg);
//            Toast.makeText(ctx.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onIncomingCallStarted(Context ctx, String number, Date start) {
            String msg = "start incoming call: " + number + " at " + start;

            Log.d("###", msg);
            Toast.makeText(ctx.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
            String msg = "end incoming call: " + number + " at " + end;

            Log.d("###", msg);
            Toast.makeText(ctx.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onMissedCall(Context ctx, String number, Date missed) {
//            String msg = "missed call: " + number + " at " + missed;
//
//            Log.d("###", msg);
//            Toast.makeText(ctx.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

        }
    }

}
