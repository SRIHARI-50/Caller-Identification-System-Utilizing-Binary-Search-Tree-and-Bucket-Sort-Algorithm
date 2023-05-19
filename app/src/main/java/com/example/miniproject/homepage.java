package com.example.miniproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Date;
import java.util.Objects;

public class homepage extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 0;
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    public static final int MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS = 2;
    private RecyclerView contactRv;

    private DbHelper dbHelper;

    private facAdapter adapterContact;

    private DrawerLayout draw;

    private stuAdapter adapterContac;

    String s;

    public Db d;

    Button cs,ee,ec,me,cv,all;

    CallReceiver callReceiver;
    FloatingActionButton add;

    WebView webView;

    NotificationManager notificationManager;

    NotificationChannel notificationChannel;

    RemoteViews customView;

    int notificationCounter = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        if (ContextCompat.checkSelfPermission(homepage.this, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(homepage.this, new String[]{Manifest.permission.READ_CALL_LOG}, MY_PERMISSIONS_REQUEST_READ_CALL_LOG);
        }

        // dynamically register CallReceiver
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(callReceiver, intentFilter);

        cs = findViewById(R.id.cse);
        ee = findViewById(R.id.eee);
        ec = findViewById(R.id.ece);
        all = findViewById(R.id.all);
        me = findViewById(R.id.mech);
        cv = findViewById(R.id.civil);

        add = findViewById(R.id.add);

        d = new Db(this);

        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).apply();
            d.create();
        }

        if(SaveSharedPreference.getUserName(this).length() == 0)
        {
            Intent lo = new Intent(getApplicationContext(),login_page.class);
            startActivity(lo);
        }
        if (SaveSharedPreference.getKeep(this).equals("0"))
            SaveSharedPreference.clearUserName(this);

        draw = findViewById(R.id.draw);
        NavigationView nav = findViewById(R.id.nav_view);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.fac:
                        s= "fac";
                        loadData();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new facFragment()).commit();
                        nav.setCheckedItem(R.id.fac);
                        break;

                    case R.id.stu:
                        s = "stu";
                        stuloadData();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new stuFragment()).commit();
                        nav.setCheckedItem(R.id.stu);
                        break;

                    case R.id.logout:
                        SaveSharedPreference.clearUserName(getApplicationContext());
                        Intent out = new Intent(getApplicationContext(),login_page.class);
                        startActivity(out);
                        break;

                    case R.id.csv:
                        Intent csv = new Intent(getApplicationContext(),upload.class);
                        startActivity(csv);
                        break;
                }

                draw.closeDrawer(GravityCompat.START);

                return false;
            }
        });

        dbHelper = new DbHelper(this);

        contactRv = findViewById(R.id.contactRv);

        contactRv.setHasFixedSize(true);


        s= "fac";
        loadData();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new facFragment()).commit();
        nav.setCheckedItem(R.id.fac);
        s = "fac";

        cs.setOnClickListener(view -> {
            if (s.equals("fac"))
                filterContact("CSE");
            else
                stuFilterContact("CSE");
        });

        ee.setOnClickListener(view -> {
            if (s.equals("fac"))
                filterContact("EEE");
            else
                stuFilterContact("EEE");
        });

        ec.setOnClickListener(view -> {
            if (s.equals("fac"))
                filterContact("ECE");
            else
                stuFilterContact("ECE");
        });

        me.setOnClickListener(view -> {
            if (s.equals("fac"))
                filterContact("MECH");
            else
                stuFilterContact("MECH");
        });

        cv.setOnClickListener(view -> {
            if (s.equals("fac"))
                filterContact("CIVIL");
            else
                stuFilterContact("CIVIL");
        });

        all.setOnClickListener(view -> {
            if (s.equals("fac"))
                loadData();
            else
                stuloadData();
        });

        add.setOnClickListener(view -> {

            if (s.equals("fac")) {
                Intent intent = new Intent(getApplicationContext(), addContact.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(getApplicationContext(), addStuContact.class);
                startActivity(intent);
            }

        });

    }

    public void onBackPressed(){
        if (draw.isDrawerOpen(GravityCompat.START))
            draw.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    private void loadData() {
        adapterContact = new facAdapter(this,dbHelper.getAllData());
        contactRv.setAdapter(adapterContact);

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);

        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        MenuItem item1 = menu.findItem(R.id.searchContact);
        MenuItem item2 = menu.findItem(R.id.menu);

        SearchView searchView = (SearchView) item1.getActionView();
        Toolbar toolbar = (Toolbar) item2.getActionView();

        searchView.setMaxWidth(685);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,draw,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (s.equals("fac"))
                    searchContact(query);
                else
                    stusearchContact(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (s.equals("fac"))
                    searchContact(newText);
                else
                    stusearchContact(newText);
                return true;
            }
        });

        return true;

    }

    private void stuFilterContact(String query){
        adapterContac = new stuAdapter(this,dbHelper.getStuFilterContact(query));
        contactRv.setAdapter(adapterContac);
    }

    private void filterContact(String query){
        adapterContact = new facAdapter(this,dbHelper.getFilterContact(query));
        contactRv.setAdapter(adapterContact);
    }

    private void searchContact(String query) {
        adapterContact = new facAdapter(this,dbHelper.getSearchContact(query));
        contactRv.setAdapter(adapterContact);
    }

    private void stusearchContact(String query) {
        adapterContac = new stuAdapter(this,dbHelper.getstuContact(query));
        contactRv.setAdapter(adapterContac);
    }
    private void stuloadData() {
        adapterContac = new stuAdapter(this,dbHelper.getstuData());
        contactRv.setAdapter(adapterContac);
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
                    Log.d("###", "READ_CALL_LOG granted!");
                    if (ContextCompat.checkSelfPermission(homepage.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(homepage.this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                    }
                } else {
                    Log.d("###", "READ_CALL_LOG denied!");
                    Toast.makeText(getApplicationContext(), "missing READ_CALL_LOG", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("###", "READ_PHONE_STATE granted!");
                    if (ContextCompat.checkSelfPermission(homepage.this, Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(homepage.this, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS}, MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS);
                    }
                } else {
                    Log.d("###", "READ_PHONE_STATE denied!");
                    Toast.makeText(getApplicationContext(), "missing READ_PHONE_STATE", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("###", "PROCESS_OUTGOING_CALLS granted!");
                } else {
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
        }

        @Override
        protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        }

        @SuppressLint("RestrictedApi")
        @RequiresApi(api = Build.VERSION_CODES.S)
        @Override
        protected void onIncomingCallStarted(Context ctx, String number, Date start) {

            String n = getCaller(number.substring(3));

            customView = new RemoteViews(getPackageName(), R.layout.call_noti);
            customView.setTextViewText(R.id.name,n);
            customView.setTextViewText(R.id.no,number);

            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(homepage.this,createNotificationChannels().getId());
            builder.setSmallIcon(R.drawable.ic_launcher_background);
            builder.setContentTitle("Incoming call...");
            builder.setContentText(n);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setSmallIcon(R.drawable.phone);
            builder.setAutoCancel(true);
            builder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
            builder.setCustomContentView(customView);
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            Intent notify = new Intent(homepage.this,homepage.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(homepage.this,0,notify,PendingIntent.FLAG_MUTABLE);
            builder.setContentIntent(pendingIntent);

            notificationManager.notify(notificationCounter,builder.build());
            notificationCounter = notificationCounter + 1;

        }

        @Override
        protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        }

        @Override
        protected void onMissedCall(Context ctx, String number, Date missed) {
        }
    }

    String Channel_ID = "Notification ID";
    String Channel_Name = "Notification Name";
    private NotificationChannel createNotificationChannels() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(Channel_ID, Channel_Name, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
            return notificationChannel;}
        return null;
    }

    String getCaller(String n){
        String res;

        dbHelper = new DbHelper(this);

        res = dbHelper.name(n);
        return res;
    }

}