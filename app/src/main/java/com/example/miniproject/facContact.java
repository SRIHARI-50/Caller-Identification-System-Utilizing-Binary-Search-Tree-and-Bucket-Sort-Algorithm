package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class facContact extends AppCompatActivity {

    private TextView nameTv,phoneTv,deptTv,interTv,altTv,emailTv;

    private String id;

    private DbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fac_details);

        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        ImageView call = findViewById(R.id.call);
        ImageView wa = findViewById(R.id.whatsapp);

        dbHelper = new DbHelper(this);

        //get data from intent
        Intent intent = getIntent();
        id = intent.getStringExtra("contactId");

        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);
        deptTv = findViewById(R.id.deptTv);
        interTv = findViewById(R.id.interTv);
        altTv = findViewById(R.id.altTv);
        emailTv = findViewById(R.id.emailTv);

        String phone = loadDataById();

        call.setOnClickListener(view -> {

            String no = "tel:" + phone;

            Uri number = Uri.parse(no);
            Intent callIntent = new Intent(Intent.ACTION_DIAL,number);

            startActivity(callIntent);

        });

        wa.setOnClickListener(view -> {

            try {
                String num = "91" + phone;

                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + num));
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }
            catch (Exception e) {
                Toast.makeText(this, "Whatsapp not installed", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private String loadDataById() {

        String phone = null;

        String selectQuery =  "SELECT * FROM fac_details WHERE id =\"" + id + "\"";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {

                String name =  ""+cursor.getString(cursor.getColumnIndexOrThrow("username"));
                phone = ""+cursor.getString(cursor.getColumnIndexOrThrow("phno"));
                String dept = ""+cursor.getString(cursor.getColumnIndexOrThrow("dept"));
                String email = ""+cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String intercom = ""+cursor.getString(cursor.getColumnIndexOrThrow("intercom"));
                String alt = ""+cursor.getString(cursor.getColumnIndexOrThrow("alt_phno"));


                nameTv.setText(name);
                phoneTv.setText(phone);
                deptTv.setText(dept);
                emailTv.setText(email);
                interTv.setText(intercom);
                altTv.setText(alt);

            }while (cursor.moveToNext());
        }

        db.close();

        return phone;
    }
}
