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

    SQLiteDatabase db = this.getReadableDatabase();
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void create(){

        String fac = "CREATE TABLE \"fac_details\" (\n" +
                "\t\"id\"\t INTEGER ,\n" +
                "\t\"username\"\t TEXT NOT NULL,\n" +
                "\t\"dept\"\t TEXT,\n" +
                "\t\"phno\"\t TEXT NOT NULL,\n" +
                "\t\"intercom\"\t TEXT NOT NULL,\n" +
                "\t\"alt_phno\"\t TEXT,\n" +
                "\t\"email\"\t TEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ");";

        String user = "CREATE TABLE \"user_credentials\" (\n" +
                "\t\"id\"\tINTEGER NOT NULL,\n" +
                "\t\"username\"\tTEXT NOT NULL,\n" +
                "\t\"password\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")";

        String stu = "CREATE TABLE \"stu_details\" (\n" +
                "\t\"id\"\t INTEGER,\n" +
                "\t\"name\"\tTEXT NOT NULL,\n" +
                "\t\"reg\"\t TEXT NOT NULL,\n" +
                "\t\"yr\"\tTEXT NOT NULL,\n" +
                "\t\"dept\"\tTEXT NOT NULL,\n" +
                "\t\"email\"\t TEXT NOT NULL,\n" +
                "\t\"phno\"\tTEXT NOT NULL,\n" +
                "\t\"alt_phno\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")";


        db.execSQL(user);
        db.execSQL(stu);
        db.execSQL(fac);

        String[][] login = {
                {"user","1234"},
                {"Sri","sri"},
                {"Sakthi","sakthi"},
                {"Janani","janani"}
        };

        String[][] fac_data = {
                {"Mahavishnu","CSE","8015514070","1","","mvvc@psgitech.ac.in"},
                {"Aravindhraj","CSE","9487883339","2","","aravindhraj@psgitech.ac.in"},
                {"Paldurai","ECE","8973277670","3","","paldurai.k@psgitech.ac.in"},
                {"Santhanamari","ECE","9994229737","4","","gsm@psgitech.ac.in"},
                {"Vilasini","CSE","9677819117","5","","vilasini@psgitech.ac.in"}
        };

        String[][] stu_data = {
                {"Janani","715520104013","3","CSE","d20z113@psgitech.ac.in","9080359005",""},
                {"Bhuvaneshwari","715520106005","3","ECE","d20l112@psgitech.ac.in","9790673402",""},
                {"Kareshmaa","715519104016","4","CSE","d19z123@psgitech.ac.in","9361261095",""},
                {"Mirnalani","715520106018","3","ECE","d20l213@psgitech.ac.in","7358894368",""}
        };

        for(int i=0;i< login.length;i++){
            db.execSQL("insert into user_credentials(username,password) values ( '"+ login[i][0] +"','"+ login[i][1] +"' );");
        }

        for(int i=0;i< fac_data.length;i++){
            db.execSQL("insert into fac_details(username,dept,phno,intercom,alt_phno,email) values ( '" + fac_data[i][0] + "','"+ fac_data[i][1] +"','"+ fac_data[i][2] +"','"+ fac_data[i][3] +"','"+ fac_data[i][4] +"','"+ fac_data[i][5] +"');");
        }

        for(int i=0;i< stu_data.length;i++){
            db.execSQL("insert into stu_details(name,reg,yr,dept,email,phno,alt_phno) values ( '" + stu_data[i][0] + "','"+ stu_data[i][1] +"','"+ stu_data[i][2] +"','"+ stu_data[i][3] +"','"+ stu_data[i][4] +"','"+ stu_data[i][5] +"','"+ stu_data[i][6] +"');");
        }

    }

    public void add(String a,String b,String c,String d,String e,String f,String g){
        db.execSQL("insert into stu_details(name,reg,yr,dept,email,phno,alt_phno) values ( '" + a + "','"+ b +"','"+ c +"','"+ d +"','"+ e +"','"+ f +"','"+ g +"');");

    }

}
