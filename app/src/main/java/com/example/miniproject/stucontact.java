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

public class stucontact extends AppCompatActivity {

    private TextView nameTv,phoneTv,deptTv,yearTv,altTv,emailTv,regTv;

    private String id;

    private DbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_details);

        ImageView call = findViewById(R.id.call);
        ImageView wa = findViewById(R.id.whatsapp);

        //init db
        dbHelper = new DbHelper(this);

        //get data from intent
        Intent intent = getIntent();
        id = intent.getStringExtra("contactId");

        //init view
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);
        deptTv = findViewById(R.id.deptTv);
        yearTv = findViewById(R.id.yearTv);
        altTv = findViewById(R.id.altTv);
        emailTv = findViewById(R.id.emailTv);
        regTv = findViewById(R.id.regTv);

        String[] nos = loadDataById();

        String phone = nos[0];
        String alt = nos[1];

        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        call.setOnClickListener(view -> {

            String no = "tel:" + phone;

            Uri number = Uri.parse(no);
            Intent callIntent = new Intent(Intent.ACTION_DIAL,number);

            startActivity(callIntent);

        });

        altTv.setOnClickListener(view -> {

            String no = "tel:" + alt;

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

    private String[] loadDataById() {

        String phone = "",alt = "";

        String selectQuery =  "SELECT * FROM stu_details WHERE id =\"" + id + "\"";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {

                String name =  ""+cursor.getString(cursor.getColumnIndexOrThrow("name"));
                phone = ""+cursor.getString(cursor.getColumnIndexOrThrow("phno"));
                String dept = ""+cursor.getString(cursor.getColumnIndexOrThrow("dept"));
                String reg = ""+cursor.getString(cursor.getColumnIndexOrThrow("reg"));
                String email = ""+cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String year = ""+cursor.getString(cursor.getColumnIndexOrThrow("yr"));
                alt = ""+cursor.getString(cursor.getColumnIndexOrThrow("alt_phno"));


                nameTv.setText(name);
                phoneTv.setText(phone);
                regTv.setText(reg);
                emailTv.setText(email);
                deptTv.setText(dept);
                yearTv.setText(year);
                altTv.setText(alt);

            }while (cursor.moveToNext());
        }

        db.close();

        return new String[]{phone, alt};

    }
}
