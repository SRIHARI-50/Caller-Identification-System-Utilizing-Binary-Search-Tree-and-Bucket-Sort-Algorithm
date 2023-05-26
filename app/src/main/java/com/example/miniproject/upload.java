package com.example.miniproject;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.documentfile.provider.DocumentFile;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class upload extends AppCompatActivity {

    private Db db;

    private String type;

    Button stu,fac;

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);

        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        fac = findViewById(R.id.b1);
        stu = findViewById(R.id.b2);

        fac.setOnClickListener(view ->
        {
            type = "fac";
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {

                    Intent intent = new Intent();
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select CSV File "), 101);
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivity(intent);
                }
            } else {

                Intent intent = new Intent();
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);

                ActivityCompat.requestPermissions(upload.this, new String[]{WRITE_EXTERNAL_STORAGE}, 102);


            }
        });

        stu.setOnClickListener(view ->
        {
            type = "stu";
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {

                    Intent intent = new Intent();
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select CSV File "), 101);
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivity(intent);
                }
            } else {

                Intent intent = new Intent();
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);

                ActivityCompat.requestPermissions(upload.this, new String[]{WRITE_EXTERNAL_STORAGE}, 102);


            }
        });
    }

    Uri fileuri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && data!=null){
            fileuri=data.getData();
            readCSVFile(getFilePathFromUri(fileuri),type);
        }
    }

    public String getFilePathFromUri(Uri uri){

        String filepath=uri.getPath();
        String[] filePath1=filepath.split(":");

        System.out.println(filePath1[1]);
        return Environment.getExternalStorageDirectory().getPath() + "/" + filePath1[1];
    }


    public void readCSVFile(String path,String t){

        //File file=new File("/storage/emulated/0/Download/test.csv");

        File file=new File("/storage/emulated/0/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Documents/test.csv");

        try {

            CSVReader reader = new CSVReader(new FileReader(file));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {

                db = new Db(this);

                if(t.equals("stu"))
                    db.add(nextLine);
                else
                    db.facadd(nextLine);

            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(upload.this,"Error",Toast.LENGTH_SHORT).show();
        }
    }
}
