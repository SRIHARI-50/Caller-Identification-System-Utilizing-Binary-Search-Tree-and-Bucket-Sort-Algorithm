package com.example.miniproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class addStuContact extends AppCompatActivity {

    private EditText nameEt,regEt,yrEt,phoneEt,emailEt,deptEt,altEt;

    private DbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_add_edit_contact);

        nameEt = findViewById(R.id.name);
        phoneEt = findViewById(R.id.phno);
        emailEt = findViewById(R.id.email);
        deptEt = findViewById(R.id.dept);
        altEt = findViewById(R.id.alt_phno);
        regEt = findViewById(R.id.reg);
        yrEt = findViewById(R.id.yr);

        Button ins = findViewById(R.id.insert);

        dbHelper = new DbHelper(this);

        ins.setOnClickListener(v -> saveDat());
    }


    private void saveDat() {

        String uname,uphone,uyr,ureg,uemail,udept,alt;

        uname = nameEt.getText().toString();
        ureg = regEt.getText().toString();
        uyr = yrEt.getText().toString();
        udept = deptEt.getText().toString();
        uemail = emailEt.getText().toString();
        uphone = phoneEt.getText().toString();
        alt = altEt.getText().toString();

        if (!uname.isEmpty() || !uphone.isEmpty() || !uemail.isEmpty() || !udept.isEmpty()){

            long id =  dbHelper.insertStuContact(
                    ""+uname,
                    ""+ureg,
                    ""+uyr,
                    ""+udept,
                    ""+uemail,
                    ""+uphone,
                    ""+alt
            );

            Toast.makeText(getApplicationContext(), "Inserted Successfully.... ", Toast.LENGTH_SHORT).show();

        }

    }

}
