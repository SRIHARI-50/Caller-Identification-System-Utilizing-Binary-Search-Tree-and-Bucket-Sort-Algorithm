package com.example.miniproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Db extends SQLiteOpenHelper {

    public Db(@Nullable Context context) {
        super(context, "test", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void create(){

        String fac = "CREATE TABLE \"fac_details\" (\n" +
                "\t\"id\"\tTEXT,\n" +
                "\t\"username\"\tTEXT NOT NULL,\n" +
                "\t\"dept\"\tTEXT,\n" +
                "\t\"phno\"\tTEXT NOT NULL,\n" +
                "\t\"intercom\"\tTEXT NOT NULL,\n" +
                "\t\"alt_phno\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\")\n" +
                ")";

        String user = "CREATE TABLE \"user_credentials\" (\n" +
                "\t\"id\"\tTEXT NOT NULL,\n" +
                "\t\"username\"\tTEXT NOT NULL,\n" +
                "\t\"password\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"id\")\n" +
                ")";

        String stu = "CREATE TABLE \"stu_details\" (\n" +
                "\t\"id\"\tTEXT NOT NULL,\n" +
                "\t\"name\"\tTEXT NOT NULL,\n" +
                "\t\"yr\"\tTEXT NOT NULL,\n" +
                "\t\"dept\"\tTEXT NOT NULL,\n" +
                "\t\"phno\"\tTEXT NOT NULL,\n" +
                "\t\"alt_phno\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"id\")\n" +
                ")";

        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL(user);
        db.execSQL(stu);
        db.execSQL(fac);

        String[][] login = {
                {"1","user","1234"}
        };

        String[][] fac_data = {
                {"1","abc","CSE","9876543210","1",""},
                {"2","xyz","EEE","1234567890","2",""}
        };

        String[][] stu_data = {
                {"1","abcd","3","CSE","1234567890",""},
                {"2","lmno","2","ECE","9876543102",""}
        };

        for(int i=0;i< login.length;i++){
            db.execSQL("insert into user_credentials values ( '" + login[i][0] + "','"+ login[i][1] +"','"+ login[i][2] +"' );");
        }

        for(int i=0;i< fac_data.length;i++){
            db.execSQL("insert into fac_details values ( '" + fac_data[i][0] + "','"+ fac_data[i][1] +"','"+ fac_data[i][2] +"','"+ fac_data[i][3] +"','"+ fac_data[i][4] +"','"+ fac_data[i][5] +"');");
        }

        for(int i=0;i< stu_data.length;i++){
            db.execSQL("insert into stu_details values ( '" + stu_data[i][0] + "','"+ stu_data[i][1] +"','"+ stu_data[i][2] +"','"+ stu_data[i][3] +"','"+ stu_data[i][4] +"','"+ stu_data[i][5] +"');");
        }

    }

}
