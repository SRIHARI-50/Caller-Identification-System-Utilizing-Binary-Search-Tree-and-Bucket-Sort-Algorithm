package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class login_page extends AppCompatActivity {

    Button b;

    private DbHelper dbHelper;
    TextInputEditText username,password;

    AppCompatCheckBox keep;

    String k;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DbHelper(this);

        b=findViewById(R.id.login_button);

        username=findViewById(R.id.user);
        password=findViewById(R.id.pass);

        keep=findViewById(R.id.keep);

        b.setOnClickListener(view ->{

            if (dbHelper.authenticate(username.getText().toString(), password.getText().toString())) {

                if (keep.isChecked())
                    k = "1";
                else
                    k = "0";

                SaveSharedPreference.setUserName(this,username.getText().toString(),k);

                Toast.makeText(this, "Login successful!!!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),homepage.class);
                startActivity(intent);

            }
            else
                Toast.makeText(this, "Username or Password is incorrect...", Toast.LENGTH_SHORT).show();

        } );
    }
}