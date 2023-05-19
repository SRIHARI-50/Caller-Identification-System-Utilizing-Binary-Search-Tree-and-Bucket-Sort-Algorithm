package com.example.miniproject;

import android.app.VoiceInteractor;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.Console;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(@Nullable Context context) {
        super(context, "test", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean authenticate(String username, String password){

        String pass = "";

        boolean auth;

        String query = "select password from user_credentials where username = '" + username + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst())
            pass = cursor.getString(cursor.getColumnIndexOrThrow("password"));

        if (pass.equals(password))
            auth = true;
        else
            auth = false;

        return auth;
    }

    public ArrayList<facmodel> getSearchContact(String query){

        // it will return arraylist of modelContact class
        ArrayList<facmodel> contactList = new ArrayList<>();

        // get readable database
        SQLiteDatabase db = getReadableDatabase();

        //query for search
        String queryToSearch = "SELECT * FROM fac_details WHERE username LIKE '%" +query+"%'";

        Cursor cursor = db.rawQuery(queryToSearch,null);

        // looping through all record and add to list
        if (cursor.moveToFirst()){
            do {
                facmodel modelContact = new facmodel(

                        ""+cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("username")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("dept")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("phno")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("intercom")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("alt_phno"))
                );
                contactList.add(modelContact);
            }while (cursor.moveToNext());
        }
        db.close();
        return contactList;

    }

    public ArrayList<stumodel> getstuContact(String query){

        // it will return arraylist of modelContact class
        ArrayList<stumodel> contactList = new ArrayList<>();

        // get readable database
        SQLiteDatabase db = getReadableDatabase();

        //query for search
        String queryToSearch = "SELECT * FROM stu_details WHERE name LIKE '%" +query+"%'";

        Cursor cursor = db.rawQuery(queryToSearch,null);

        // looping through all record and add to list
        if (cursor.moveToFirst()){
            do {
                stumodel modelContact = new stumodel(

                        ""+cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow("reg")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("yr")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("dept")),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow("email")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("phno")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("alt_phno"))
                );
                contactList.add(modelContact);
            }while (cursor.moveToNext());
        }
        db.close();
        return contactList;

    }

    public ArrayList<facmodel> getAllData(){
        //create arrayList
        ArrayList<facmodel> arrayList = new ArrayList<>();
        //sql command query
        String selectQuery = "SELECT * FROM fac_details";

        //get readable db
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        // looping through all record and add to list
        if (cursor.moveToFirst()){
            do {
                facmodel modelContact = new facmodel(

                        ""+cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("username")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("dept")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("phno")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("intercom")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("alt_phno")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("email"))
                );
                arrayList.add(modelContact);
            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }

    public ArrayList<stumodel> getstuData(){
        //create arrayList
        ArrayList<stumodel> arrayList = new ArrayList<>();
        //sql command query
        String selectQuery = "SELECT * FROM stu_details";

        //get readable db
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        // looping through all record and add to list
        if (cursor.moveToFirst()){
            do {
                stumodel modelContact = new stumodel(

                        ""+cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow("reg")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("yr")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("dept")),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow("email")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("phno")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("alt_phno"))
                );
                arrayList.add(modelContact);
            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }

    public ArrayList<stumodel> getStuFilterContact(String query){

        // it will return arraylist of modelContact class
        ArrayList<stumodel> contactList = new ArrayList<>();

        // get readable database
        SQLiteDatabase db = getReadableDatabase();

        //query for search
        String queryToSearch = "SELECT * FROM stu_details WHERE dept = '" +query+"'";

        Cursor cursor = db.rawQuery(queryToSearch,null);

        // looping through all record and add to list
        if (cursor.moveToFirst()){
            do {
                stumodel modelContact = new stumodel(
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow("reg")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("yr")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("dept")),
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow("email")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("phno")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("alt_phno"))
                );
                contactList.add(modelContact);
            }while (cursor.moveToNext());
        }
        db.close();
        return contactList;
    }

    public ArrayList<facmodel> getFilterContact(String query){

        // it will return arraylist of modelContact class
        ArrayList<facmodel> contactList = new ArrayList<>();

        // get readable database
        SQLiteDatabase db = getReadableDatabase();

        //query for search
        String queryToSearch = "SELECT * FROM fac_details WHERE dept = '" +query+"'";

        Cursor cursor = db.rawQuery(queryToSearch,null);

        // looping through all record and add to list
        if (cursor.moveToFirst()){
            do {
                facmodel modelContact = new facmodel(

                        ""+cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("username")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("dept")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("phno")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("intercom")),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow("alt_phno"))
                );
                contactList.add(modelContact);
            }while (cursor.moveToNext());
        }
        db.close();
        return contactList;
    }

    public long insertContact(String name,String dept,String phone,String intercom,String alt,String email){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("username",name);
        contentValues.put("dept",dept);
        contentValues.put("phno",phone);
        contentValues.put("intercom",intercom);
        contentValues.put("alt_phno",alt);
        contentValues.put("email",email);

        long id = db.insert("fac_details",null,contentValues);

        db.close();

        return id;

    }

    public long insertStuContact(String name,String reg,String yr,String dept,String email,String phone,String alt){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("name",name);
        contentValues.put("reg",reg);
        contentValues.put("yr",yr);
        contentValues.put("dept",dept);
        contentValues.put("email",email);
        contentValues.put("phno",phone);
        contentValues.put("alt_phno",alt);

        long id = db.insert("stu_details",null,contentValues);

        db.close();

        return id;

    }

    String name(String n){

        String name = "";
        String a;

        SQLiteDatabase db = getReadableDatabase();

        String queryToSearch = "SELECT name,yr,dept FROM stu_details WHERE phno = '" + n +"'";

        Cursor cursor = db.rawQuery(queryToSearch,null);

        if (cursor.moveToFirst()){
                name = ""+cursor.getString(cursor.getColumnIndexOrThrow("name"));
                a = ""+cursor.getString(cursor.getColumnIndexOrThrow("yr"));

                switch (a){
                    case "1":
                        name += ", "+a+"st yr, ";
                        break;
                    case "2":
                        name += ", "+a+"nd yr, ";
                        break;
                    case "3":
                        name += ", "+a+"rd yr, ";
                        break;
                    case "4":
                        name += ", "+a+"th yr, ";
                        break;
                }
                name += ""+cursor.getString(cursor.getColumnIndexOrThrow("dept"));
        }

        if(name.equals("")){

            queryToSearch = "SELECT username,dept FROM fac_details WHERE phno = '" + n +"'";

            cursor = db.rawQuery(queryToSearch,null);

            if (cursor.moveToFirst()){
                    name =  ""+cursor.getString(cursor.getColumnIndexOrThrow("username"))+", "+
                            ""+cursor.getString(cursor.getColumnIndexOrThrow("dept"));
            }
        }

        db.close();

        if(name.equals(""))
            name = "Unknown number";

        return name;

    }

}
