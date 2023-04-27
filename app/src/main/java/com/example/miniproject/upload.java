package com.example.miniproject;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class upload extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);

        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        if(SDK_INT >= Build.VERSION_CODES.R)
        {
            if(Environment.isExternalStorageManager()){
                //choosing csv file
                Intent intent=new Intent();
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE,true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select CSV File "),101);
            }
            else{
                //getting permission from user
                Intent intent=new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                Uri uri=Uri.fromParts("package",getPackageName(),null);
                startActivity(intent);
            }
        }
        else{
            // for below android 11

            Intent intent=new Intent();
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE,true);
            intent.setAction(Intent.ACTION_GET_CONTENT);

            ActivityCompat.requestPermissions(upload.this,new String[] {WRITE_EXTERNAL_STORAGE},102);


        }
    }

    Uri fileuri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && data!=null){
            fileuri=data.getData();
            readCSVFile(getFilePathFromUri(fileuri));
        }
    }


    // this method is used for getting file path from uri
    public String getFilePathFromUri(Uri uri){
        String[] filename1;
        String filepath=uri.getPath();
        String filePath1[]=filepath.split(":");
        return filePath1[1];
    }

    public void readCSVFile(String path){

        ArrayList<String> arrayList = new ArrayList<>();
        File file=new File(path);

        System.out.println(path);

        try {

            CSVReader reader = new CSVReader(new FileReader(file));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {

                arrayList.add(nextLine[0]);

            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(upload.this,"Error",Toast.LENGTH_SHORT).show();
        }

        final ListView list = findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(arrayAdapter);

    }
}
