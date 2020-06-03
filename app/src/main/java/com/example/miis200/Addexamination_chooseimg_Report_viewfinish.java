package com.example.miis200;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Addexamination_chooseimg_Report_viewfinish extends AppCompatActivity {

    DatabaseHelper myDB;
    ArrayList<User> userList;
    ListView listView;
    User user;
    int x;

    private String AIimg1path,AIimg2path,AIimg3path,AIimg4path,AIimg5path,AIimg6path,AIimg7path,AIimg8path,
            AIimg9path,AIimg10path,AIimg11path,AIimg12path,AIimg13path,AIimg14path,AIimg15path,AIimg16path;
    private String img1path,img2path,img3path,img4path,img5path,img6path,img7path,img8path,img9path,img10path;


    private String Ddesc1,Ddesc2,Ddesc3,Ddesc4,Ddesc5,Ddesc6,Ddesc7,Ddesc8,Ddesc9,Ddesc10,Ddesc11,Ddesc12,Ddesc13,
            Ddesc14,Ddesc15,Ddesc16,Ddesc17,Ddesc18,Ddesc19,Ddesc20,Ddesc21,Ddesc22,Ddesc23,Ddesc24,Ddesc25,Ddesc26;

    private String pName,pID,pBir,pGender,pNum,chTime,age;

    ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10;
    ImageView AIimg1,AIimg2,AIimg3,AIimg4,AIimg5,AIimg6,AIimg7,AIimg8,AIimg9,AIimg10,AIimg11,AIimg12,AIimg13,AIimg14,AIimg15,AIimg16;

    TextView getDesc1,getDesc2,getDesc3,getDesc4,getDesc5,getDesc6,getDesc7,getDesc8,getDesc9,getDesc10,getDesc11,getDesc12,getDesc13,getDesc14,
            getDesc15,getDesc16,getDesc17,getDesc18,getDesc19,getDesc20,getDesc21,getDesc22,getDesc23,getDesc24,getDesc25,getDesc26;
    Button newprinter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexamination_chooseimg_report_viewfinish);
        System.out.println("DDDDDDDddwwwwww");

        //===============================================================================================
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Choose_image_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //===============================================================================================

        Button cancelchoose = (Button) findViewById(R.id.cancelchoose);

        Intent intent = this.getIntent();


        pName=intent.getStringExtra("a");
        pID=intent.getStringExtra("b");
        pBir=intent.getStringExtra("c");
        pGender=intent.getStringExtra("d");
        pNum=intent.getStringExtra("e");
        chTime=intent.getStringExtra("f");
        age = intent.getStringExtra("age");

        Log.i("asdasdasd",pName + pID + pBir + pGender + pNum + chTime);



        Log.i("DDDDIIIII",pID);

        myDB = new DatabaseHelper(this);
        userList = new ArrayList<>();

        SQLiteDatabase idDB = myDB.getReadableDatabase();
        Cursor cursor = idDB.query("users_data", null, "PATIENTID=?", new String[]{pID},null,null,null);
        cursor.moveToFirst();

        img1path = cursor.getString(cursor.getColumnIndex("LEFTIMAGEPATH"));
        img2path = cursor.getString(cursor.getColumnIndex("RIGHTIMAGEPATH"));
        img3path = cursor.getString(cursor.getColumnIndex("EYEPATH1"));
        img4path = cursor.getString(cursor.getColumnIndex("EYEPATH2"));
        img5path = cursor.getString(cursor.getColumnIndex("EYEPATH3"));
        img6path = cursor.getString(cursor.getColumnIndex("EYEPATH4"));
        img7path = cursor.getString(cursor.getColumnIndex("EYEPATH5"));
        img8path = cursor.getString(cursor.getColumnIndex("EYEPATH6"));
        img9path = cursor.getString(cursor.getColumnIndex("EYEPATH7"));
        img10path = cursor.getString(cursor.getColumnIndex("EYEPATH8"));

        AIimg1path = cursor.getString(cursor.getColumnIndex("AIIMG1"));
        AIimg2path = cursor.getString(cursor.getColumnIndex("AIIMG2"));
        AIimg3path = cursor.getString(cursor.getColumnIndex("AIIMG3"));
        AIimg4path = cursor.getString(cursor.getColumnIndex("AIIMG4"));
        AIimg5path = cursor.getString(cursor.getColumnIndex("AIIMG5"));
        AIimg6path = cursor.getString(cursor.getColumnIndex("AIIMG6"));
        AIimg7path = cursor.getString(cursor.getColumnIndex("AIIMG7"));
        AIimg8path = cursor.getString(cursor.getColumnIndex("AIIMG8"));
        AIimg9path = cursor.getString(cursor.getColumnIndex("AIIMG9"));
        AIimg10path = cursor.getString(cursor.getColumnIndex("AIIMG10"));
        AIimg11path = cursor.getString(cursor.getColumnIndex("AIIMG11"));
        AIimg12path = cursor.getString(cursor.getColumnIndex("AIIMG12"));
        AIimg13path = cursor.getString(cursor.getColumnIndex("AIIMG13"));
        AIimg14path = cursor.getString(cursor.getColumnIndex("AIIMG14"));
        AIimg15path = cursor.getString(cursor.getColumnIndex("AIIMG15"));
        AIimg16path = cursor.getString(cursor.getColumnIndex("AIIMG16"));

        Ddesc1 = cursor.getString(cursor.getColumnIndex("DOCTORDESC"));
        Ddesc2 = cursor.getString(cursor.getColumnIndex("DOCTORDESC2"));
        Ddesc3 = cursor.getString(cursor.getColumnIndex("DOCTORDESC3"));
        Ddesc4 = cursor.getString(cursor.getColumnIndex("DOCTORDESC4"));
        Ddesc5 = cursor.getString(cursor.getColumnIndex("DOCTORDESC5"));
        Ddesc6 = cursor.getString(cursor.getColumnIndex("DOCTORDESC6"));
        Ddesc7 = cursor.getString(cursor.getColumnIndex("DOCTORDESC7"));
        Ddesc8 = cursor.getString(cursor.getColumnIndex("DOCTORDESC8"));
        Ddesc9 = cursor.getString(cursor.getColumnIndex("DOCTORDESC9"));
        Ddesc10 = cursor.getString(cursor.getColumnIndex("DOCTORDESC10"));
        Ddesc11 = cursor.getString(cursor.getColumnIndex("DOCTORDESC11"));
        Ddesc12 = cursor.getString(cursor.getColumnIndex("DOCTORDESC12"));
        Ddesc13 = cursor.getString(cursor.getColumnIndex("DOCTORDESC13"));
        Ddesc14 = cursor.getString(cursor.getColumnIndex("DOCTORDESC14"));
        Ddesc15 = cursor.getString(cursor.getColumnIndex("DOCTORDESC15"));
        Ddesc16 = cursor.getString(cursor.getColumnIndex("DOCTORDESC16"));
        Ddesc17 = cursor.getString(cursor.getColumnIndex("DOCTORDESC17"));
        Ddesc18 = cursor.getString(cursor.getColumnIndex("DOCTORDESC18"));
        Ddesc19 = cursor.getString(cursor.getColumnIndex("DOCTORDESC19"));
        Ddesc20 = cursor.getString(cursor.getColumnIndex("DOCTORDESC20"));
        Ddesc21 = cursor.getString(cursor.getColumnIndex("DOCTORDESC21"));
        Ddesc22 = cursor.getString(cursor.getColumnIndex("DOCTORDESC22"));
        Ddesc23 = cursor.getString(cursor.getColumnIndex("DOCTORDESC23"));
        Ddesc24 = cursor.getString(cursor.getColumnIndex("DOCTORDESC24"));
        Ddesc25 = cursor.getString(cursor.getColumnIndex("DOCTORDESC25"));
        Ddesc26 = cursor.getString(cursor.getColumnIndex("DOCTORDESC26"));


        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        img6 = findViewById(R.id.img6);
        img7 = findViewById(R.id.img7);
        img8 = findViewById(R.id.img8);
        img9 = findViewById(R.id.img9);
        img10 = findViewById(R.id.img10);

        AIimg1 = findViewById(R.id.AIimg1);
        AIimg2 = findViewById(R.id.AIimg2);
        AIimg3 = findViewById(R.id.AIimg3);
        AIimg4 = findViewById(R.id.AIimg4);
        AIimg5 = findViewById(R.id.AIimg5);
        AIimg6 = findViewById(R.id.AIimg6);
        AIimg7 = findViewById(R.id.AIimg7);
        AIimg8 = findViewById(R.id.AIimg8);
        AIimg9 = findViewById(R.id.AIimg9);
        AIimg10 = findViewById(R.id.AIimg10);
        AIimg11 = findViewById(R.id.AIimg11);
        AIimg12 = findViewById(R.id.AIimg12);
        AIimg13 = findViewById(R.id.AIimg13);
        AIimg14 = findViewById(R.id.AIimg14);
        AIimg15 = findViewById(R.id.AIimg15);
        AIimg16 = findViewById(R.id.AIimg16);

        getDesc1 = findViewById(R.id.desc1);
        getDesc2 = findViewById(R.id.desc2);
        getDesc3 = findViewById(R.id.desc3);
        getDesc4 = findViewById(R.id.desc4);
        getDesc5 = findViewById(R.id.desc5);
        getDesc6 = findViewById(R.id.desc6);
        getDesc7 = findViewById(R.id.desc7);
        getDesc8 = findViewById(R.id.desc8);
        getDesc9 = findViewById(R.id.desc9);
        getDesc10 = findViewById(R.id.desc10);
        getDesc11 = findViewById(R.id.desc11);
        getDesc12 = findViewById(R.id.desc12);
        getDesc13 = findViewById(R.id.desc13);
        getDesc14 = findViewById(R.id.desc14);
        getDesc15 = findViewById(R.id.desc15);
        getDesc16 = findViewById(R.id.desc16);
        getDesc17 = findViewById(R.id.desc17);
        getDesc18 = findViewById(R.id.desc18);
        getDesc19 = findViewById(R.id.desc19);
        getDesc20 = findViewById(R.id.desc20);
        getDesc21 = findViewById(R.id.desc21);
        getDesc22 = findViewById(R.id.desc22);
        getDesc23 = findViewById(R.id.desc23);
        getDesc24 = findViewById(R.id.desc24);
        getDesc25 = findViewById(R.id.desc25);
        getDesc26 = findViewById(R.id.desc26);



        if(img1path != null){
            img1.setVisibility(View.VISIBLE);
            getDesc1.setVisibility(View.VISIBLE);
            img1.setImageURI(Uri.parse(img1path));
            int width = img1.getDrawable().getIntrinsicWidth();
            int heigh = img1.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + img1path).resize(width/1,heigh/1).into(img1);
            getDesc1.setText(Ddesc1);
        }

        if(img2path != null){
            img2.setVisibility(View.VISIBLE);
            getDesc2.setVisibility(View.VISIBLE);
            img2.setImageURI(Uri.parse(img2path));
            int width = img2.getDrawable().getIntrinsicWidth();
            int heigh = img2.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + img2path).resize(width/1,heigh/1).into(img2);
            getDesc2.setText(Ddesc2);
        }

        if(img3path != null){
            img3.setVisibility(View.VISIBLE);
            getDesc3.setVisibility(View.VISIBLE);
            img3.setImageURI(Uri.parse(img3path));
            int width = img3.getDrawable().getIntrinsicWidth();
            int heigh = img3.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + img3path).resize(width/1,heigh/1).into(img3);
            getDesc3.setText(Ddesc3);
        }

        if(img4path != null){
            img4.setVisibility(View.VISIBLE);
            getDesc4.setVisibility(View.VISIBLE);
            img4.setImageURI(Uri.parse(img4path));
            int width = img4.getDrawable().getIntrinsicWidth();
            int heigh = img4.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + img4path).resize(width/1,heigh/1).into(img4);
            getDesc4.setText(Ddesc4);
        }

        if(img5path != null){
            img5.setVisibility(View.VISIBLE);
            getDesc5.setVisibility(View.VISIBLE);
            img5.setImageURI(Uri.parse(img5path));
            int width = img5.getDrawable().getIntrinsicWidth();
            int heigh = img5.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + img5path).resize(width/1,heigh/1).into(img5);
            getDesc5.setText(Ddesc5);
        }

        if(img6path != null){
            img6.setVisibility(View.VISIBLE);
            getDesc6.setVisibility(View.VISIBLE);
            img6.setImageURI(Uri.parse(img6path));
            int width = img6.getDrawable().getIntrinsicWidth();
            int heigh = img6.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + img6path).resize(width/1,heigh/1).into(img6);
            getDesc6.setText(Ddesc6);
        }

        if(img7path != null){
            img7.setVisibility(View.VISIBLE);
            getDesc7.setVisibility(View.VISIBLE);
            img7.setImageURI(Uri.parse(img7path));
            int width = img7.getDrawable().getIntrinsicWidth();
            int heigh = img7.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + img7path).resize(width/1,heigh/1).into(img7);
            getDesc7.setText(Ddesc7);
        }

        if(img8path != null){
            img8.setVisibility(View.VISIBLE);
            getDesc8.setVisibility(View.VISIBLE);
            img8.setImageURI(Uri.parse(img8path));
            int width = img8.getDrawable().getIntrinsicWidth();
            int heigh = img8.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + img8path).resize(width/1,heigh/1).into(img8);
            getDesc8.setText(Ddesc8);
        }

        if(img9path != null){
            img9.setVisibility(View.VISIBLE);
            getDesc9.setVisibility(View.VISIBLE);
            img9.setImageURI(Uri.parse(img9path));
            int width = img9.getDrawable().getIntrinsicWidth();
            int heigh = img9.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + img9path).resize(width/1,heigh/1).into(img9);
            getDesc9.setText(Ddesc9);
        }

        if(img10path != null){
            img10.setVisibility(View.VISIBLE);
            getDesc10.setVisibility(View.VISIBLE);
            img10.setImageURI(Uri.parse(img10path));
            int width = img10.getDrawable().getIntrinsicWidth();
            int heigh = img10.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + img10path).resize(width/1,heigh/1).into(img10);
            getDesc10.setText(Ddesc10);
        }


        if(!AIimg1path.equals(" ")){
            AIimg1.setVisibility(View.VISIBLE);
            getDesc11.setVisibility(View.VISIBLE);
            AIimg1.setImageURI(Uri.parse(AIimg1path));
            int width = AIimg1.getDrawable().getIntrinsicWidth();
            int heigh = AIimg1.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg1path).resize(width/1,heigh/1).into(AIimg1);
            getDesc11.setText(Ddesc11);
        }

        Log.i("jijijijijij",AIimg2path);
        if(!AIimg2path.equals(" ")){
            AIimg2.setVisibility(View.VISIBLE);
            getDesc12.setVisibility(View.VISIBLE);
            AIimg2.setImageURI(Uri.parse(AIimg2path));
            int width = AIimg2.getDrawable().getIntrinsicWidth();
            int heigh = AIimg2.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg2path).resize(width/1,heigh/1).into(AIimg2);
            getDesc12.setText(Ddesc12);
        }


        if(!AIimg3path.equals(" ")){
            AIimg3.setVisibility(View.VISIBLE);
            getDesc13.setVisibility(View.VISIBLE);
            AIimg3.setImageURI(Uri.parse(AIimg3path));
            int width = AIimg3.getDrawable().getIntrinsicWidth();
            int heigh = AIimg3.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg3path).resize(width/1,heigh/1).into(AIimg3);
            getDesc13.setText(Ddesc13);
        }

        if(!AIimg4path.equals(" ")){
            AIimg4.setVisibility(View.VISIBLE);
            getDesc14.setVisibility(View.VISIBLE);
            AIimg4.setImageURI(Uri.parse(AIimg4path));
            int width = AIimg4.getDrawable().getIntrinsicWidth();
            int heigh = AIimg4.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg4path).resize(width/1,heigh/1).into(AIimg4);
            getDesc14.setText(Ddesc14);
        }

        if(!AIimg5path.equals(" ")){
            AIimg5.setVisibility(View.VISIBLE);
            getDesc15.setVisibility(View.VISIBLE);
            AIimg5.setImageURI(Uri.parse(AIimg5path));
            int width = AIimg5.getDrawable().getIntrinsicWidth();
            int heigh = AIimg5.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg5path).resize(width/1,heigh/1).into(AIimg5);
            getDesc15.setText(Ddesc15);
        }

        if(!AIimg6path.equals(" ")){
            AIimg6.setVisibility(View.VISIBLE);
            getDesc16.setVisibility(View.VISIBLE);
            AIimg6.setImageURI(Uri.parse(AIimg6path));
            int width = AIimg6.getDrawable().getIntrinsicWidth();
            int heigh = AIimg6.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg6path).resize(width/1,heigh/1).into(AIimg6);
            getDesc16.setText(Ddesc16);
        }

        if(!AIimg7path.equals(" ")){
            AIimg7.setVisibility(View.VISIBLE);
            getDesc17.setVisibility(View.VISIBLE);
            AIimg7.setImageURI(Uri.parse(AIimg7path));
            int width = AIimg7.getDrawable().getIntrinsicWidth();
            int heigh = AIimg7.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg7path).resize(width/1,heigh/1).into(AIimg7);
            getDesc17.setText(Ddesc17);
        }

        if(!AIimg8path.equals(" ")){
            AIimg8.setVisibility(View.VISIBLE);
            getDesc18.setVisibility(View.VISIBLE);
            AIimg8.setImageURI(Uri.parse(AIimg8path));
            int width = AIimg8.getDrawable().getIntrinsicWidth();
            int heigh = AIimg8.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg8path).resize(width/1,heigh/1).into(AIimg8);
            getDesc18.setText(Ddesc18);
        }

        if(!AIimg9path.equals(" ")){
            AIimg9.setVisibility(View.VISIBLE);
            getDesc19.setVisibility(View.VISIBLE);
            AIimg9.setImageURI(Uri.parse(AIimg9path));
            int width = AIimg9.getDrawable().getIntrinsicWidth();
            int heigh = AIimg9.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg9path).resize(width/1,heigh/1).into(AIimg9);
            getDesc19.setText(Ddesc19);
        }

        if(!AIimg10path.equals(" ")){
            AIimg10.setVisibility(View.VISIBLE);
            getDesc20.setVisibility(View.VISIBLE);
            AIimg10.setImageURI(Uri.parse(AIimg10path));
            int width = AIimg10.getDrawable().getIntrinsicWidth();
            int heigh = AIimg10.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg10path).resize(width/1,heigh/1).into(AIimg10);
            getDesc20.setText(Ddesc20);
        }

        if(!AIimg11path.equals(" ")){
            AIimg11.setVisibility(View.VISIBLE);
            getDesc21.setVisibility(View.VISIBLE);
            AIimg11.setImageURI(Uri.parse(AIimg11path));
            int width = AIimg11.getDrawable().getIntrinsicWidth();
            int heigh = AIimg11.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg11path).resize(width/1,heigh/1).into(AIimg11);
            getDesc21.setText(Ddesc21);
        }

        if(!AIimg12path.equals(" ")){
            AIimg12.setVisibility(View.VISIBLE);
            getDesc22.setVisibility(View.VISIBLE);
            AIimg12.setImageURI(Uri.parse(AIimg12path));
            int width = AIimg12.getDrawable().getIntrinsicWidth();
            int heigh = AIimg12.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg12path).resize(width/1,heigh/1).into(AIimg12);
            getDesc22.setText(Ddesc22);
        }

        if(!AIimg13path.equals(" ")){
            AIimg13.setVisibility(View.VISIBLE);
            getDesc23.setVisibility(View.VISIBLE);
            AIimg13.setImageURI(Uri.parse(AIimg13path));
            int width = AIimg13.getDrawable().getIntrinsicWidth();
            int heigh = AIimg13.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg13path).resize(width/1,heigh/1).into(AIimg13);
            getDesc23.setText(Ddesc23);
        }

        if(!AIimg14path.equals(" ")){
            AIimg14.setVisibility(View.VISIBLE);
            getDesc24.setVisibility(View.VISIBLE);
            AIimg14.setImageURI(Uri.parse(AIimg14path));
            int width = AIimg14.getDrawable().getIntrinsicWidth();
            int heigh = AIimg14.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg14path).resize(width/1,heigh/1).into(AIimg14);
            getDesc24.setText(Ddesc24);
        }

        if(!AIimg15path.equals(" ")){
            AIimg15.setVisibility(View.VISIBLE);
            getDesc25.setVisibility(View.VISIBLE);
            AIimg15.setImageURI(Uri.parse(AIimg15path));
            int width = AIimg15.getDrawable().getIntrinsicWidth();
            int heigh = AIimg15.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg15path).resize(width/1,heigh/1).into(AIimg15);
            getDesc25.setText(Ddesc25);
        }

        if(!AIimg16path.equals(" ")){
            AIimg16.setVisibility(View.VISIBLE);
            getDesc26.setVisibility(View.VISIBLE);
            AIimg16.setImageURI(Uri.parse(AIimg16path));
            int width = AIimg16.getDrawable().getIntrinsicWidth();
            int heigh = AIimg16.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://" + AIimg16path).resize(width/1,heigh/1).into(AIimg16);
            getDesc26.setText(Ddesc26);
        }

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!img1.isSelected()){
                    img1.setPadding(5,5,5,5);
                    img1.setBackgroundResource(R.drawable.chooseimgborder);
                    img1.setSelected(true);
                    x++;
                } else{
                    img1.setPadding(0,0,0,0);
                    img1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    img1.setSelected(false);
                    x--;
                }
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!img2.isSelected()){
                    img2.setPadding(5,5,5,5);
                    img2.setBackgroundResource(R.drawable.chooseimgborder);
                    img2.setSelected(true);
                    x++;
                } else{
                    img2.setPadding(0,0,0,0);
                    img2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    img2.setSelected(false);
                    x--;
                }
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!img3.isSelected()){
                    img3.setPadding(5,5,5,5);
                    img3.setBackgroundResource(R.drawable.chooseimgborder);
                    img3.setSelected(true);
                    x++;
                } else{
                    img3.setPadding(0,0,0,0);
                    img3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    img3.setSelected(false);
                    x--;
                }
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!img4.isSelected()){
                    img4.setPadding(5,5,5,5);
                    img4.setBackgroundResource(R.drawable.chooseimgborder);
                    img4.setSelected(true);
                    x++;
                } else{
                    img4.setPadding(0,0,0,0);
                    img4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    img4.setSelected(false);
                    x--;
                }
            }
        });

        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!img5.isSelected()){
                    img5.setPadding(5,5,5,5);
                    img5.setBackgroundResource(R.drawable.chooseimgborder);
                    img5.setSelected(true);
                    x++;
                } else{
                    img5.setPadding(0,0,0,0);
                    img5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    img5.setSelected(false);
                    x--;
                }
            }
        });

        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!img6.isSelected()){
                    img6.setPadding(5,5,5,5);
                    img6.setBackgroundResource(R.drawable.chooseimgborder);
                    img6.setSelected(true);
                    x++;
                } else{
                    img6.setPadding(0,0,0,0);
                    img6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    img6.setSelected(false);
                    x--;
                }
            }
        });

        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!img7.isSelected()){
                    img7.setPadding(5,5,5,5);
                    img7.setBackgroundResource(R.drawable.chooseimgborder);
                    img7.setSelected(true);
                    x++;
                } else{
                    img7.setPadding(0,0,0,0);
                    img7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    img7.setSelected(false);
                    x--;
                }
            }
        });

        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!img8.isSelected()){
                    img8.setPadding(5,5,5,5);
                    img8.setBackgroundResource(R.drawable.chooseimgborder);
                    img8.setSelected(true);
                    x++;
                } else{
                    img8.setPadding(0,0,0,0);
                    img8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    img8.setSelected(false);
                    x--;
                }
            }
        });

        img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!img9.isSelected()){
                    img9.setPadding(5,5,5,5);
                    img9.setBackgroundResource(R.drawable.chooseimgborder);
                    img9.setSelected(true);
                    x++;
                } else{
                    img9.setPadding(0,0,0,0);
                    img9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    img9.setSelected(false);
                    x--;
                }
            }
        });

        img10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!img10.isSelected()){
                    img10.setPadding(5,5,5,5);
                    img10.setBackgroundResource(R.drawable.chooseimgborder);
                    img10.setSelected(true);
                    x++;
                } else{
                    img10.setPadding(0,0,0,0);
                    img10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    img10.setSelected(false);
                    x--;
                }
            }
        });

        AIimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg1.isSelected()){
                    AIimg1.setPadding(5,5,5,5);
                    AIimg1.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg1.setSelected(true);
                    x++;
                } else{
                    AIimg1.setPadding(0,0,0,0);
                    AIimg1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg1.setSelected(false);
                    x--;
                }
            }
        });

        AIimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg2.isSelected()){
                    AIimg2.setPadding(5,5,5,5);
                    AIimg2.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg2.setSelected(true);
                    x++;
                } else{
                    AIimg2.setPadding(0,0,0,0);
                    AIimg2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg2.setSelected(false);
                    x--;
                }
            }
        });

        AIimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg3.isSelected()){
                    AIimg3.setPadding(5,5,5,5);
                    AIimg3.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg3.setSelected(true);
                    x++;
                } else{
                    AIimg3.setPadding(0,0,0,0);
                    AIimg3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg3.setSelected(false);
                    x--;
                }
            }
        });

        AIimg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg4.isSelected()){
                    AIimg4.setPadding(5,5,5,5);
                    AIimg4.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg4.setSelected(true);
                    x++;
                } else{
                    AIimg4.setPadding(0,0,0,0);
                    AIimg4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg4.setSelected(false);
                    x--;
                }
            }
        });

        AIimg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg5.isSelected()){
                    AIimg5.setPadding(5,5,5,5);
                    AIimg5.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg5.setSelected(true);
                    x++;
                } else{
                    AIimg5.setPadding(0,0,0,0);
                    AIimg5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg5.setSelected(false);
                    x--;
                }
            }
        });

        AIimg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg6.isSelected()){
                    AIimg6.setPadding(5,5,5,5);
                    AIimg6.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg6.setSelected(true);
                    x++;
                } else{
                    AIimg6.setPadding(0,0,0,0);
                    AIimg6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg6.setSelected(false);
                    x--;
                }
            }
        });

        AIimg7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg7.isSelected()){
                    AIimg7.setPadding(5,5,5,5);
                    AIimg7.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg7.setSelected(true);
                    x++;
                } else{
                    AIimg7.setPadding(0,0,0,0);
                    AIimg7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg7.setSelected(false);
                    x--;
                }
            }
        });

        AIimg8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg8.isSelected()){
                    AIimg8.setPadding(5,5,5,5);
                    AIimg8.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg8.setSelected(true);
                    x++;
                } else{
                    AIimg8.setPadding(0,0,0,0);
                    AIimg8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg8.setSelected(false);
                    x--;
                }
            }
        });

        AIimg9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg9.isSelected()){
                    AIimg9.setPadding(5,5,5,5);
                    AIimg9.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg9.setSelected(true);
                    x++;
                } else{
                    AIimg9.setPadding(0,0,0,0);
                    AIimg9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg9.setSelected(false);
                    x--;
                }
            }
        });

        AIimg10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg10.isSelected()){
                    AIimg10.setPadding(5,5,5,5);
                    AIimg10.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg10.setSelected(true);
                    x++;
                } else{
                    AIimg10.setPadding(0,0,0,0);
                    AIimg10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg10.setSelected(false);
                    x--;
                }
            }
        });

        AIimg11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg11.isSelected()){
                    AIimg11.setPadding(5,5,5,5);
                    AIimg11.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg11.setSelected(true);
                    x++;
                } else{
                    AIimg11.setPadding(0,0,0,0);
                    AIimg11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg11.setSelected(false);
                    x--;
                }
            }
        });

        AIimg12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg12.isSelected()){
                    AIimg12.setPadding(5,5,5,5);
                    AIimg12.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg12.setSelected(true);
                    x++;
                } else{
                    AIimg12.setPadding(0,0,0,0);
                    AIimg12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg12.setSelected(false);
                    x--;
                }
            }
        });

        AIimg13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg13.isSelected()){
                    AIimg13.setPadding(5,5,5,5);
                    AIimg13.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg13.setSelected(true);
                    x++;
                } else{
                    AIimg13.setPadding(0,0,0,0);
                    AIimg13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg13.setSelected(false);
                    x--;
                }
            }
        });

        AIimg14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg14.isSelected()){
                    AIimg14.setPadding(5,5,5,5);
                    AIimg14.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg14.setSelected(true);
                    x++;
                } else{
                    AIimg14.setPadding(0,0,0,0);
                    AIimg14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg14.setSelected(false);
                    x--;
                }
            }
        });

        AIimg15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg15.isSelected()){
                    AIimg15.setPadding(5,5,5,5);
                    AIimg15.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg15.setSelected(true);
                    x++;
                } else{
                    AIimg15.setPadding(0,0,0,0);
                    AIimg15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg15.setSelected(false);
                    x--;
                }
            }
        });

        AIimg16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AIimg16.isSelected()){
                    AIimg16.setPadding(5,5,5,5);
                    AIimg16.setBackgroundResource(R.drawable.chooseimgborder);
                    AIimg16.setSelected(true);
                    x++;
                } else{
                    AIimg16.setPadding(0,0,0,0);
                    AIimg16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    AIimg16.setSelected(false);
                    x--;
                }
            }
        });

        cancelchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_chooseimg_Report_viewfinish.this);
                a_builder.setMessage(R.string.Cancel_message)
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SQLiteDatabase idDB = myDB.getReadableDatabase();
                                ContentValues values = new ContentValues();
                                values.put("STATUS","0");
                                values.put("LEFTIMAGEPATH"," ");
                                values.put("RIGHTIMAGEPATH"," ");
                                values.put("Eyepath1"," ");
                                values.put("Eyepath2"," ");
                                values.put("Eyepath3"," ");
                                values.put("Eyepath4"," ");
                                values.put("Eyepath5"," ");
                                values.put("Eyepath6"," ");
                                values.put("Eyepath7"," ");
                                values.put("Eyepath8"," ");
                                values.put("AIIMG1"," ");
                                values.put("AIIMG2"," ");
                                values.put("AIIMG3"," ");
                                values.put("AIIMG4"," ");
                                values.put("AIIMG5"," ");
                                values.put("AIIMG6"," ");
                                values.put("AIIMG7"," ");
                                values.put("AIIMG8"," ");
                                values.put("AIIMG9"," ");
                                values.put("AIIMG10"," ");
                                values.put("AIIMG11"," ");
                                values.put("AIIMG12"," ");
                                values.put("AIIMG13"," ");
                                values.put("AIIMG14"," ");
                                values.put("AIIMG15"," ");
                                values.put("AIIMG16"," ");
                                values.put("DOCTORDESC"," ");
                                values.put("DOCTORDESC2"," ");
                                values.put("DOCTORDESC3"," ");
                                values.put("DOCTORDESC4"," ");
                                values.put("DOCTORDESC5"," ");
                                values.put("DOCTORDESC6"," ");
                                values.put("DOCTORDESC7"," ");
                                values.put("DOCTORDESC8"," ");
                                values.put("DOCTORDESC9"," ");
                                values.put("DOCTORDESC10"," ");
                                values.put("DOCTORDESC11"," ");
                                values.put("DOCTORDESC12"," ");
                                values.put("DOCTORDESC13"," ");
                                values.put("DOCTORDESC14"," ");
                                values.put("DOCTORDESC15"," ");
                                values.put("DOCTORDESC16"," ");
                                values.put("DOCTORDESC17"," ");
                                values.put("DOCTORDESC18"," ");
                                values.put("DOCTORDESC19"," ");
                                values.put("DOCTORDESC20"," ");
                                values.put("DOCTORDESC21"," ");
                                values.put("DOCTORDESC22"," ");
                                values.put("DOCTORDESC23"," ");
                                values.put("DOCTORDESC24"," ");
                                values.put("DOCTORDESC25"," ");
                                values.put("DOCTORDESC26"," ");
                                idDB.update("users_data",values, "PATIENTID=?", new String[]{pID.toString()});
                                Intent intent = new Intent(getApplicationContext(), Option.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }) ;
                AlertDialog alert = a_builder.create();
                alert.setTitle(R.string.Cancel_title);
                alert.show();
            }
        });

        newprinter = findViewById(R.id.newprinter);
        newprinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    if(x > 4){
                        Toast.makeText(Addexamination_chooseimg_Report_viewfinish.this, R.string.Choose_image_max_4_image, Toast.LENGTH_LONG).show();
                    } else if(x == 0){
                        Toast.makeText(Addexamination_chooseimg_Report_viewfinish.this, R.string.Choose_image_not_choose, Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(); //登入成功轉跳至首頁
                        intent.setClass(Addexamination_chooseimg_Report_viewfinish.this, Addexamination_report_viewfinish.class);

                        intent.putExtra("a",pName);
                        intent.putExtra("b",pID);
                        intent.putExtra("c",pBir);
                        intent.putExtra("d",pGender);
                        intent.putExtra("e",pNum);
                        intent.putExtra("f",chTime);
                        intent.putExtra("age",age);

                        if(img1.isSelected() == true){
                            intent.putExtra("img1path", img1path);
                            intent.putExtra("Ddesc1", Ddesc1);
                        }
                        if(img2.isSelected() == true){
                            intent.putExtra("img2path",img2path);
                            intent.putExtra("Ddesc2", Ddesc2);
                        }
                        if(img3.isSelected() == true){
                            intent.putExtra("img3path",img3path);
                            intent.putExtra("Ddesc3", Ddesc3);
                        }
                        if(img4.isSelected() == true){
                            intent.putExtra("img4path",img4path);
                            intent.putExtra("Ddesc4", Ddesc4);
                        }
                        if(img5.isSelected() == true){
                            intent.putExtra("img5path",img5path);
                            intent.putExtra("Ddesc5", Ddesc5);
                        }
                        if(img6.isSelected() == true){
                            intent.putExtra("img6path",img6path);
                            intent.putExtra("Ddesc6", Ddesc6);
                        }
                        if(img7.isSelected() == true){
                            intent.putExtra("img7path",img7path);
                            intent.putExtra("Ddesc7", Ddesc7);
                        }
                        if(img8.isSelected() == true){
                            intent.putExtra("img8path",img8path);
                            intent.putExtra("Ddesc8", Ddesc8);
                        }
                        if(img9.isSelected() == true){
                            intent.putExtra("img9path",img9path);
                            intent.putExtra("Ddesc9", Ddesc9);
                        }
                        if(img10.isSelected() == true){
                            intent.putExtra("img10path",img10path);
                            intent.putExtra("Ddesc10", Ddesc10);
                        }
                        if(AIimg1.isSelected() == true){
                            intent.putExtra("AIimg1path",AIimg1path);
                            intent.putExtra("Ddesc11", Ddesc11);
                        }
                        if(AIimg2.isSelected() == true){
                            intent.putExtra("AIimg2path",AIimg2path);
                            intent.putExtra("Ddesc12", Ddesc12);
                        }
                        if(AIimg3.isSelected() == true){
                            intent.putExtra("AIimg3path",AIimg3path);
                            intent.putExtra("Ddesc13", Ddesc13);
                        }
                        if(AIimg4.isSelected() == true){
                            intent.putExtra("AIimg4path",AIimg4path);
                            intent.putExtra("Ddesc14", Ddesc14);
                        }
                        if(AIimg5.isSelected() == true){
                            intent.putExtra("AIimg5path",AIimg5path);
                            intent.putExtra("Ddesc15", Ddesc15);
                        }
                        if(AIimg6.isSelected() == true){
                            intent.putExtra("AIimg6path",AIimg6path);
                            intent.putExtra("Ddesc16", Ddesc16);
                        }
                        if(AIimg7.isSelected() == true){
                            intent.putExtra("AIimg7path",AIimg7path);
                            intent.putExtra("Ddesc17", Ddesc17);
                        }
                        if(AIimg8.isSelected() == true){
                            intent.putExtra("AIimg8path",AIimg8path);
                            intent.putExtra("Ddesc18", Ddesc18);
                        }
                        if(AIimg9.isSelected() == true){
                            intent.putExtra("AIimg9path",AIimg9path);
                            intent.putExtra("Ddesc19", Ddesc19);
                        }
                        if(AIimg10.isSelected() == true){
                            intent.putExtra("AIimg10path",AIimg10path);
                            intent.putExtra("Ddesc20", Ddesc20);
                        }
                        if(AIimg11.isSelected() == true){
                            intent.putExtra("AIimg11path",AIimg11path);
                            intent.putExtra("Ddesc21", Ddesc21);
                        }
                        if(AIimg12.isSelected() == true){
                            intent.putExtra("AIimg12path",AIimg12path);
                            intent.putExtra("Ddesc22", Ddesc22);
                        }
                        if(AIimg13.isSelected() == true){
                            intent.putExtra("AIimg13path",AIimg13path);
                            intent.putExtra("Ddesc23", Ddesc23);
                        }
                        if(AIimg14.isSelected() == true){
                            intent.putExtra("AIimg14path",AIimg14path);
                            intent.putExtra("Ddesc24", Ddesc24);
                        }
                        if(AIimg15.isSelected() == true){
                            intent.putExtra("AIimg15path",AIimg15path);
                            intent.putExtra("Ddesc25", Ddesc25);
                        }
                        if(AIimg16.isSelected() == true){
                            intent.putExtra("AIimg16path",AIimg16path);
                            intent.putExtra("Ddesc26", Ddesc26);
                        }

                        startActivity(intent);

                    }
                }
            }
        });



    }


    //===============================================================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.download:

                AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_chooseimg_Report_viewfinish.this);
                a_builder.setMessage(R.string.Go_home_message)
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SQLiteDatabase idDB = myDB.getReadableDatabase();
                                ContentValues values = new ContentValues();
                                values.put("STATUS","0");
                                values.put("LEFTIMAGEPATH"," ");
                                values.put("RIGHTIMAGEPATH"," ");
                                values.put("Eyepath1"," ");
                                values.put("Eyepath2"," ");
                                values.put("Eyepath3"," ");
                                values.put("Eyepath4"," ");
                                values.put("Eyepath5"," ");
                                values.put("Eyepath6"," ");
                                values.put("Eyepath7"," ");
                                values.put("Eyepath8"," ");
                                values.put("AIIMG1"," ");
                                values.put("AIIMG2"," ");
                                values.put("AIIMG3"," ");
                                values.put("AIIMG4"," ");
                                values.put("AIIMG5"," ");
                                values.put("AIIMG6"," ");
                                values.put("AIIMG7"," ");
                                values.put("AIIMG8"," ");
                                values.put("AIIMG9"," ");
                                values.put("AIIMG10"," ");
                                values.put("AIIMG11"," ");
                                values.put("AIIMG12"," ");
                                values.put("AIIMG13"," ");
                                values.put("AIIMG14"," ");
                                values.put("AIIMG15"," ");
                                values.put("AIIMG16"," ");
                                values.put("DOCTORDESC"," ");
                                values.put("DOCTORDESC2"," ");
                                values.put("DOCTORDESC3"," ");
                                values.put("DOCTORDESC4"," ");
                                values.put("DOCTORDESC5"," ");
                                values.put("DOCTORDESC6"," ");
                                values.put("DOCTORDESC7"," ");
                                values.put("DOCTORDESC8"," ");
                                values.put("DOCTORDESC9"," ");
                                values.put("DOCTORDESC10"," ");
                                values.put("DOCTORDESC11"," ");
                                values.put("DOCTORDESC12"," ");
                                values.put("DOCTORDESC13"," ");
                                values.put("DOCTORDESC14"," ");
                                values.put("DOCTORDESC15"," ");
                                values.put("DOCTORDESC16"," ");
                                values.put("DOCTORDESC17"," ");
                                values.put("DOCTORDESC18"," ");
                                values.put("DOCTORDESC19"," ");
                                values.put("DOCTORDESC20"," ");
                                values.put("DOCTORDESC21"," ");
                                values.put("DOCTORDESC22"," ");
                                values.put("DOCTORDESC23"," ");
                                values.put("DOCTORDESC24"," ");
                                values.put("DOCTORDESC25"," ");
                                values.put("DOCTORDESC26"," ");
                                idDB.update("users_data",values, "PATIENTID=?", new String[]{pID.toString()});
                                Intent intent = new Intent(getApplicationContext(), Option.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }) ;
                AlertDialog alert = a_builder.create();
                alert.setTitle(R.string.Go_home_title);
                alert.show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //===============================================================================================

    public void onBackPressed(){
        finish();
    }

}
