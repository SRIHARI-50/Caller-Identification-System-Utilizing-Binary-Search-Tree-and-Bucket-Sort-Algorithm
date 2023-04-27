package com.example.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class homepage extends AppCompatActivity {

    private RecyclerView contactRv;

    private DbHelper dbHelper;

    private facAdapter adapterContact;

    private DrawerLayout draw;

    private stuAdapter adapterContac;

    String s;

    public Db d;

    Button cs,ee,ec,me,cv,all;

    FloatingActionButton add;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

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

}