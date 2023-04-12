package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class addContact extends AppCompatActivity {

    private EditText nameEt,phoneEt,emailEt,deptEt,altEt,intEt;

    //String variable;
    private String uname,uphone,uemail,udept,alt,interco;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_contact);

        nameEt = findViewById(R.id.username);
        phoneEt = findViewById(R.id.phno);
        emailEt = findViewById(R.id.email);
        deptEt = findViewById(R.id.dept);
        altEt = findViewById(R.id.alt_phno);
        intEt = findViewById(R.id.intercom);

        Button ins = findViewById(R.id.insert);

        dbHelper = new DbHelper(this);

        ins.setOnClickListener(v -> saveData());

    }

    private void saveData() {

        uname = nameEt.getText().toString();
        uphone = phoneEt.getText().toString();
        uemail = emailEt.getText().toString();
        udept = deptEt.getText().toString();
        alt = altEt.getText().toString();
        interco = intEt.getText().toString();

        if (!uname.isEmpty() || !uphone.isEmpty() || !uemail.isEmpty() || !udept.isEmpty()){

            long id =  dbHelper.insertContact(
                    ""+uname,
                    ""+udept,
                    ""+uphone,
                    ""+interco,
                    ""+alt,
                    ""+uemail
            );

            Toast.makeText(getApplicationContext(), "Inserted Successfully.... ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(),homepage.class);
            startActivity(intent);

        }
    }

}

