package com.example.miis200;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.net.wifi.WifiManager;;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Addexamination_aianal extends AppCompatActivity {

    WifiManager wifiManager;
    List scannedResult;

    DatabaseHelper myDB;
    ArrayList<User> userList;
    ListView listView;
    User user;

    int imageHeight,imageWidth;

    ZoomLinearLayout zoom_linear_layout;

    private String AI_pdf;



    final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";
    int savedRadioIndex;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String DECSSID = "DECSSID";
    public static final String DECSSIDPWD = "DECSSIDPWD";
    public static final String DIBSSID = "DIBSSID";
    public static final String DIBSSIDPWD = "DIBSSIDPWD";
    public static final String PRINTERSSID = "PRINTERSSID";
    public static final String PRINTERSSIDPWD = "PRINTERSSIDPWD";


    public static final String AP300IP = "AP300IP";
    public static final String APDIBIP = "APDIBIP";
    public static final String APHUBSSID = "APHUBSSID";
    public static final String APHUBSSIDPWD = "APHUBSSIDPWD";

    public static final String MH300IP = "MH300IP";
    public static final String MHDIBIP = "MHDIBIP";


    public static final String AS300SSID = "AS300SSID";
    public static final String AS300SSIDPWD = "AS300SSIDPWD";
    public static final String ASDIBSSID = "ASDIBSSID";
    public static final String ASDIBSSIDPWD = "ASDIBSSIDPWD";
    public static final String ASPRINTERSSID = "ASPRINTERSSID";
    public static final String ASPRINTERSSIDPWD = "ASPRINTERSSIDPWD";
    public static final String ASHUBSSID = "ASHUBSSID";
    public static final String ASHUBSSIDPWD = "ASHUBSSIDPWD";


    private String DecSSID;
    private String DecSSIDpwd;
    private String DibSSID;
    private String DibSSIDpwd;
    private String PrinterSSID;
    private String PrinterSSIDpwd;

    private String AP_300IP;
    private String AP_DIBIP;
    private String AP_HUBSSID;
    private String AP_HUBSSIDPWD;

    private String MH_300IP;
    private String MH_DIBIP;

    private String AS_300SSID;
    private String AS_300SSIDPWD;
    private String AS_DIBSSID;
    private String AS_DIBSSIDPWD;
    private String AS_PRINTERSSID;
    private String AS_PRINTERSSIDPWD;
    private String AS_HUBSSID;
    private String AS_HUBSSIDPWD;


    private String AIimgPath;
    private String AIimgColumn;
    private int AIfor = 1;


    TextView tvfive,tvtwo,tvdr;
    String stfive;
    String sttwo;
    String stdr;
    String path;
    ProgressBar PBnodr;
    ProgressBar PBmildnpdr;
    ProgressBar PBmodnpdr;
    ProgressBar PBservenpdr;
    ProgressBar PBpdr;

    TextView TVnodr;
    TextView TVmildnpdr;
    TextView TVmodnpdr;
    TextView TVservenpdr;
    TextView TVpdr;

    FrameLayout Analimg;
    ImageView screenShort;
    Button capture;



    Bitmap bitmap;
    public static final SimpleDateFormat sdf5 = new SimpleDateFormat("yyyyMMdd");
    Date current = new Date();
    public String today = sdf5.format(current).toString();


    EditText editText;

    ImageView MAimage, HEMimage, HEimage, CWSimage;


    ProgressBar PBReferableDR;
    TextView TVReferableDR;

    Switch MA;
    Switch HEM;
    Switch HE;
    Switch CWS;
    ImageView imageView4;

    static {
        System.loadLibrary("opencv_java3");
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexamination_aianal);

        myDB = new DatabaseHelper(this);

        userList = new ArrayList<>();

        editText = findViewById(R.id.editText);

        Analimg = findViewById(R.id.Analimg);
        //screenShort=findViewById(R.id.screenShort);
        capture=findViewById(R.id.Capture);

        zoom_linear_layout = findViewById(R.id.zoom_linear_layout);

        capture.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (!Button_Click.isFastDoubleClick()) {
                    zoom_linear_layout.applyScaleAndTranslationaa();


                    screenShot(Analimg);

                    Intent intent = getIntent();
                    String pName=intent.getStringExtra("a");
                    String pID=intent.getStringExtra("b");
                    SQLiteDatabase idDB = myDB.getReadableDatabase();
                    ContentValues values = new ContentValues();
                    Log.i("idididididid",pID);
                    Log.i("idididididid",AIimgPath);


                    Cursor cursor = idDB.query("users_data", null, "PATIENTID=?", new String[]{pID},null,null,null);
                    cursor.moveToFirst();
                    String AIimgData = "AIIMG" + AIfor;
                    AIimgColumn = cursor.getString(cursor.getColumnIndex(AIimgData));

                    String AIimg1 = cursor.getString(cursor.getColumnIndex("AIIMG1"));
                    String AIimg2 = cursor.getString(cursor.getColumnIndex("AIIMG2"));
                    String AIimg3 = cursor.getString(cursor.getColumnIndex("AIIMG3"));
                    String AIimg4 = cursor.getString(cursor.getColumnIndex("AIIMG4"));
                    String AIimg5 = cursor.getString(cursor.getColumnIndex("AIIMG5"));
                    String AIimg6 = cursor.getString(cursor.getColumnIndex("AIIMG6"));
                    String AIimg7 = cursor.getString(cursor.getColumnIndex("AIIMG7"));
                    String AIimg8 = cursor.getString(cursor.getColumnIndex("AIIMG8"));
                    String AIimg9 = cursor.getString(cursor.getColumnIndex("AIIMG9"));
                    String AIimg10 = cursor.getString(cursor.getColumnIndex("AIIMG10"));
                    String AIimg11 = cursor.getString(cursor.getColumnIndex("AIIMG11"));
                    String AIimg12 = cursor.getString(cursor.getColumnIndex("AIIMG12"));
                    String AIimg13 = cursor.getString(cursor.getColumnIndex("AIIMG13"));
                    String AIimg14 = cursor.getString(cursor.getColumnIndex("AIIMG14"));
                    String AIimg15 = cursor.getString(cursor.getColumnIndex("AIIMG15"));
                    String AIimg16 = cursor.getString(cursor.getColumnIndex("AIIMG16"));
                    Log.i("ascascas",AIimg1);
                    Log.i("ascascas",AIimg2);
                    Log.i("ascascas",AIimg3);
                    Log.i("ascascas",AIimg4);
                    Log.i("ascascas",AIimg5);
                    Log.i("ascascas",AIimg6);
                    Log.i("ascascas",AIimg7);
                    Log.i("ascascas",AIimg8);
                    Log.i("ascascas",AIimg9);
                    Log.i("ascascas",AIimg10);
                    Log.i("ascascas",AIimg11);
                    Log.i("ascascas",AIimg12);
                    Log.i("ascascas",AIimg13);
                    Log.i("ascascas",AIimg14);
                    Log.i("ascascas",AIimg15);
                    Log.i("ascascas",AIimg16);

                    String message = editText.getText().toString();

                    if(AIimg16.equals(" ")) {
                        screenShot(Analimg);
                        if (AIimg1.equals(" ")) {
                            values.put("AIIMG1", AIimgPath);
                            values.put("DOCTORDESC11", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg2.equals(" ")){
                            values.put("AIIMG2", AIimgPath);
                            values.put("DOCTORDESC12", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg3.equals(" ")){
                            values.put("AIIMG3", AIimgPath);
                            values.put("DOCTORDESC13", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg4.equals(" ")){
                            values.put("AIIMG4", AIimgPath);
                            values.put("DOCTORDESC14", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg5.equals(" ")){
                            values.put("AIIMG5", AIimgPath);
                            values.put("DOCTORDESC15", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg6.equals(" ")){
                            values.put("AIIMG6", AIimgPath);
                            values.put("DOCTORDESC16", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg7.equals(" ")){
                            values.put("AIIMG7", AIimgPath);
                            values.put("DOCTORDESC17", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg8.equals(" ")){
                            values.put("AIIMG8", AIimgPath);
                            values.put("DOCTORDESC18", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg9.equals(" ")){
                            values.put("AIIMG9", AIimgPath);
                            values.put("DOCTORDESC19", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg10.equals(" ")){
                            values.put("AIIMG10", AIimgPath);
                            values.put("DOCTORDESC20", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg11.equals(" ")){
                            values.put("AIIMG11", AIimgPath);
                            values.put("DOCTORDESC21", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg12.equals(" ")){
                            values.put("AIIMG12", AIimgPath);
                            values.put("DOCTORDESC22", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg13.equals(" ")){
                            values.put("AIIMG13", AIimgPath);
                            values.put("DOCTORDESC23", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg14.equals(" ")){
                            values.put("AIIMG14", AIimgPath);
                            values.put("DOCTORDESC24", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg15.equals(" ")){
                            values.put("AIIMG15", AIimgPath);
                            values.put("DOCTORDESC25", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }
                        else if(AIimg16.equals(" ")){
                            values.put("AIIMG16", AIimgPath);
                            values.put("DOCTORDESC26", message);
                            idDB.update("users_data", values, "PATIENTID=?", new String[]{pID});
                            Toast.makeText(Addexamination_aianal.this, R.string.Add_success, Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(Addexamination_aianal.this, R.string.Addex_aianal_client_data_full, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //===============================================================================================
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Addex_aianal_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //===============================================================================================

        //tvfive = findViewById(R.id.five);
        //tvtwo = findViewById(R.id.two);
        //tvdr = findViewById(R.id.dr);

        MA = (Switch) findViewById(R.id.MA);
        HEM = (Switch) findViewById(R.id.HEM);
        HE = (Switch) findViewById(R.id.HE);
        CWS = (Switch) findViewById(R.id.CWS);
        imageView4 = findViewById(R.id.imageView4);
        imageView4.setEnabled(false);



        stfive = getIntent().getExtras().getString("five");
        sttwo = getIntent().getExtras().getString("two");
        stdr = getIntent().getExtras().getString("dr");
        path = getIntent().getExtras().getString("imagepath");

        int AIimgSizeW = getIntent().getIntExtra("AIimgSizeW",0);
        int AIimgSizeH = getIntent().getIntExtra("AIimgSizeH",0);
        Log.i("sadmaksldmakl",String.valueOf(AIimgSizeW));
        Log.i("sadmaksldmakl",String.valueOf(AIimgSizeH));

        MAimage = (ImageView) findViewById(R.id.MAimage);
        HEMimage = (ImageView) findViewById(R.id.HEMimage);
        HEimage = (ImageView) findViewById(R.id.HEimage);
        CWSimage = (ImageView) findViewById(R.id.CWSimage);

        Button share = (Button) findViewById( R.id.AIshare );
        share.setOnClickListener( sharepdf );

        Button view_pdf = (Button) findViewById( R.id.AIview_pdf );
        view_pdf.setOnClickListener( seepdf );

        Button save_pdf = (Button) findViewById(R.id.AIsave_pdf);
        save_pdf.setOnClickListener(savepdf);

        Button cancel = (Button) findViewById(R.id.AIcancel);
        cancel.setOnClickListener(nextcancel);

        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        scannedResult = new ArrayList();

        if(path != null) {
            imageView4.setImageURI(Uri.parse(path));
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Uri uri = Uri.parse(path);
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
        imageHeight = options.outHeight;
        imageWidth = options.outWidth;

        Log.i("sadasdasdasdadasd",String.valueOf(imageWidth));
        Log.i("sadasdasdasdadasd",String.valueOf(imageHeight));



        ImageView circleborder = (ImageView)findViewById(R.id.circleborder);
        if (imageWidth == 1716 && imageHeight == 1632){
            circleborder.setImageResource(R.drawable.circleborder17161632);
            Log.i("circleborder17161632","circleborder17161632");
            int width1 = circleborder.getDrawable().getIntrinsicWidth();
            int heigh1 = circleborder.getDrawable().getIntrinsicHeight();
            Picasso.get().load(R.drawable.circleborder17161632).resize(width1/3,heigh1/3).into(circleborder);
        }
        else if (imageWidth == 1801 && imageHeight == 1201){
            circleborder.setImageResource(R.drawable.circleborder18011201);
            Log.i("circleborder18011201","circleborder18011201");
            int width1 = circleborder.getDrawable().getIntrinsicWidth();
            int heigh1 = circleborder.getDrawable().getIntrinsicHeight();
            Picasso.get().load(R.drawable.circleborder18011201).resize(width1/3,heigh1/3).into(circleborder);
        }
        else if (imageWidth == 1956 && imageHeight == 1934){
            circleborder.setImageResource(R.drawable.circleborder19561934);
            Log.i("circleborder19561934","circleborder19561934");
            int width1 = circleborder.getDrawable().getIntrinsicWidth();
            int heigh1 = circleborder.getDrawable().getIntrinsicHeight();
            Picasso.get().load(R.drawable.circleborder19561934).resize(width1/3,heigh1/3).into(circleborder);
        }
        else if (imageWidth == 2304 && imageHeight == 1728){
            circleborder.setImageResource(R.drawable.circleborder23041728);
            Log.i("circleborder23041728","circleborder23041728");
            int width1 = circleborder.getDrawable().getIntrinsicWidth();
            int heigh1 = circleborder.getDrawable().getIntrinsicHeight();
            Picasso.get().load(R.drawable.circleborder23041728).resize(width1/3,heigh1/3).into(circleborder);
        }
        else if (imageWidth == 2400 && imageHeight == 2448){
            circleborder.setImageResource(R.drawable.circleborder24002448);
            Log.i("circleborder24002448","circleborder24002448");
            int width1 = circleborder.getDrawable().getIntrinsicWidth();
            int heigh1 = circleborder.getDrawable().getIntrinsicHeight();
            Picasso.get().load(R.drawable.circleborder24002448).resize(width1/3,heigh1/3).into(circleborder);
        }
        else if (imageWidth == 2560 && imageHeight == 1920){
            circleborder.setImageResource(R.drawable.circleborder25601921);
            Log.i("circleborder25601921","circleborder25601921");
            int width1 = circleborder.getDrawable().getIntrinsicWidth();
            int heigh1 = circleborder.getDrawable().getIntrinsicHeight();
            Picasso.get().load(R.drawable.circleborder25601921).resize(width1/3,heigh1/3).into(circleborder);
        }
        else if (imageWidth == 3200 && imageHeight == 2550){
            circleborder.setImageResource(R.drawable.circleborder32002550);
            Log.i("circleborder32002550","circleborder32002550");
            int width1 = circleborder.getDrawable().getIntrinsicWidth();
            int heigh1 = circleborder.getDrawable().getIntrinsicHeight();
            Picasso.get().load(R.drawable.circleborder32002550).resize(width1/3,heigh1/3).into(circleborder);
        }
        else if (imageWidth == 3696 && imageHeight == 2448){
            circleborder.setImageResource(R.drawable.circleborder36962448);
            Log.i("circleborder36962448","circleborder36962448");
            int width1 = circleborder.getDrawable().getIntrinsicWidth();
            int heigh1 = circleborder.getDrawable().getIntrinsicHeight();
            Picasso.get().load(R.drawable.circleborder36962448).resize(width1/3,heigh1/3).into(circleborder);
        }
        else if (imageWidth == 3888 && imageHeight == 2592){
            circleborder.setImageResource(R.drawable.circleborder38882592);
            Log.i("circleborder38882592","circleborder38882592");
            int width1 = circleborder.getDrawable().getIntrinsicWidth();
            int heigh1 = circleborder.getDrawable().getIntrinsicHeight();
            Picasso.get().load(R.drawable.circleborder38882592).resize(width1/3,heigh1/3).into(circleborder);
        }
        else if (imageWidth == 768 && imageHeight == 768){
            circleborder.setImageResource(R.drawable.circleborder768768);
            Log.i("circleborder768768","circleborder768768");
            int width1 = circleborder.getDrawable().getIntrinsicWidth();
            int heigh1 = circleborder.getDrawable().getIntrinsicHeight();
            Picasso.get().load(R.drawable.circleborder768768).resize(width1/3,heigh1/3).into(circleborder);
        }



        try {
            MAdr();
        } catch (JSONException e) {

        }

        try {
            HEMdr();
        } catch (JSONException e) {

        }

        try {
            HEdr();
        } catch (JSONException e) {

        }

        try {
            CWSdr();
        } catch (JSONException e) {

        }

        MA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    MAimage.setVisibility(View.VISIBLE);
                }else {
                    MAimage.setVisibility(View.INVISIBLE);
                }
            }
        });

        HEM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    HEMimage.setVisibility(View.VISIBLE);
                }else {
                    HEMimage.setVisibility(View.INVISIBLE);
                }
            }
        });

        HE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    HEimage.setVisibility(View.VISIBLE);
                }else {
                    HEimage.setVisibility(View.INVISIBLE);
                }
            }
        });

        CWS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    CWSimage.setVisibility(View.VISIBLE);
                }else {
                    CWSimage.setVisibility(View.INVISIBLE);
                }
            }
        });


        // 五分類
        try {
            PBnodr = (ProgressBar)findViewById(R.id.NoDRprogressBar);
            PBmildnpdr = (ProgressBar)findViewById(R.id.MildNPDRprogressBar);
            PBmodnpdr = (ProgressBar)findViewById(R.id.ModerateNPDRprogressBar);
            PBservenpdr = (ProgressBar)findViewById(R.id.SevereNPDRprogressBar);
            PBpdr = (ProgressBar)findViewById(R.id.PDRprogressBar);

            TVnodr = (TextView)findViewById(R.id.NoDRtext);
            TVmildnpdr = (TextView)findViewById(R.id.MildNPDRtext);
            TVmodnpdr = (TextView)findViewById(R.id.ModerateNPDRtext);
            TVservenpdr = (TextView)findViewById(R.id.SevereNPDRtext);
            TVpdr = (TextView)findViewById(R.id.PDRtext);

            JSONObject jsonObject = new JSONObject(stfive);
            JSONArray array= jsonObject.getJSONArray("obj");
            PBnodr.setProgress((int)(array.getDouble(0)*100));
            PBmildnpdr.setProgress((int)(array.getDouble(1)*100));
            PBmodnpdr.setProgress((int)(array.getDouble(2)*100));
            PBservenpdr.setProgress((int)(array.getDouble(3)*100));
            PBpdr.setProgress((int)(array.getDouble(4)*100));

            String nodr = String.valueOf((int)(array.getDouble(0)*100) +"%");
            String mildnpdr = String.valueOf((int)(array.getDouble(1)*100) +"%");
            String modnpdr = String.valueOf((int)(array.getDouble(2)*100) +"%");
            String servenpdr = String.valueOf((int)(array.getDouble(3)*100) +"%");
            String pdr = String.valueOf((int)(array.getDouble(4)*100) +"%");

            TVnodr.setText(nodr);
            TVmildnpdr.setText(mildnpdr);
            TVmodnpdr.setText(modnpdr);
            TVservenpdr.setText(servenpdr);
            TVpdr.setText(pdr);


            Log.i("PBnodr",String.valueOf((int)(array.getDouble(3)*100)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 二分類
        try{
            PBReferableDR = (ProgressBar)findViewById(R.id.ReferableDRprogressBar);
            TVReferableDR = (TextView)findViewById(R.id.ReferableDRtext);
            JSONObject jsonObject = new JSONObject(sttwo);
            JSONArray array= jsonObject.getJSONArray("obj");
            PBReferableDR.setProgress((int)(array.getDouble(1)*100));
            String ReferableDR = String.valueOf((int)(array.getDouble(1)*100) +"%");
            TVReferableDR.setText(ReferableDR);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //tvfive.setText(stfive);
        //tvtwo.setText(sttwo);





    }

    public void screenShot(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(),
                v.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        //screenShort.setImageBitmap(bitmap);
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/req_images");
        myDir.mkdirs();
//        Random generator = new Random();
//        int n = 10000;
//        n = generator.nextInt(n);

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

        String date = sDateFormat.format(new java.util.Date());

        String fname = "Image-" + date + ".png";
        File file = new File(myDir, fname);
        Log.i("llllllllllllll", "" + file);
        AIimgPath = file.toString();
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        DecSSID = sharedPreferences.getString(DECSSID, "flashair_ec21e54ca69e");
        DecSSIDpwd = sharedPreferences.getString(DECSSIDPWD, "12345678");
        DibSSID = sharedPreferences.getString(DIBSSID, "DIB-00044b8d480c");
        DibSSIDpwd = sharedPreferences.getString(DIBSSIDPWD, "Aa123456");
        PrinterSSID = sharedPreferences.getString(PRINTERSSID, "MiiS-OA");
        PrinterSSIDpwd = sharedPreferences.getString(PRINTERSSIDPWD, "vacationoa");

        AP_300IP = sharedPreferences.getString(AP300IP, "192.168.0.100");
        AP_DIBIP = sharedPreferences.getString(APDIBIP, "192.168.0.123");
        AP_HUBSSID = sharedPreferences.getString("APHUBSSID", "MiiS-FAB");
        AP_HUBSSIDPWD = sharedPreferences.getString(APHUBSSIDPWD, "vacationfab");

        MH_300IP = sharedPreferences.getString(MH300IP, "192.168.0.102");
        MH_DIBIP = sharedPreferences.getString(MHDIBIP, "192.168.0.103");


        AS_300SSID = sharedPreferences.getString("AS300SSID", "123");
        AS_300SSIDPWD = sharedPreferences.getString(AS300SSIDPWD, "300ssidpwd");
        AS_DIBSSID = sharedPreferences.getString("ASDIBSSID", "DIBssid");
        AS_DIBSSIDPWD = sharedPreferences.getString(ASDIBSSIDPWD, "DIBssidpwd");
        AS_PRINTERSSID = sharedPreferences.getString("ASPRINTERSSID", "PRINTERssid");
        AS_PRINTERSSIDPWD = sharedPreferences.getString(ASPRINTERSSIDPWD, "PRINTERssidpwd");
        AS_HUBSSID = sharedPreferences.getString("ASHUBSSID", "HUBssid");
        AS_HUBSSIDPWD = sharedPreferences.getString(ASHUBSSIDPWD, "HUBssidpwd");

        SharedPreferences sharedPreferences1 = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        savedRadioIndex = sharedPreferences1.getInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0);
    }
    public void updateViews() {

        Log.i("DecSSID",String.valueOf(DecSSID));
        Log.i("DecSSIDpwd",String.valueOf(DecSSIDpwd));
        Log.i("DibSSID",String.valueOf(DibSSID));
        Log.i("DibSSIDpwd",String.valueOf(DibSSIDpwd));
        Log.i("PrinterSSID",String.valueOf(PrinterSSID));
        Log.i("PrinterSSIDpwd",String.valueOf(PrinterSSIDpwd));

        Log.i("AP_300IP",String.valueOf(AP_300IP));
        Log.i("AP_DIBIP",String.valueOf(AP_DIBIP));
        Log.i("AP_HUBSSID",String.valueOf(AP_HUBSSID));
        Log.i("AP_HUBSSIDPWD",String.valueOf(AP_HUBSSIDPWD));

        Log.i("MH_300IP",String.valueOf(MH_300IP));
        Log.i("MH_DIBIP",String.valueOf(MH_DIBIP));

        Log.i("AS_300SSID",String.valueOf(AS_300SSID));
        Log.i("AS_300SSIDPWD",String.valueOf(AS_300SSIDPWD));
        Log.i("AS_DIBSSID",String.valueOf(AS_DIBSSID));
        Log.i("AS_DIBSSIDPWD",String.valueOf(AS_DIBSSIDPWD));
        Log.i("AS_PRINTERSSID",String.valueOf(AS_PRINTERSSID));
        Log.i("AS_PRINTERSSIDPWD",String.valueOf(AS_PRINTERSSIDPWD));
        Log.i("AS_HUBSSID",String.valueOf(AS_HUBSSID));
        Log.i("AS_HUBSSIDPWD",String.valueOf(AS_HUBSSIDPWD));

        Log.i("MODE_NUMBER",String.valueOf(savedRadioIndex));


    }

    private Button.OnClickListener sharepdf =
            new Button.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (!Button_Click.isFastDoubleClick()) {
                        sharPDF();
                    }
                }
            };

    private Button.OnClickListener savepdf =
            new Button.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (!Button_Click.isFastDoubleClick()) {
                        createPDF();
                    }
                }
            };

    private Button.OnClickListener nextcancel =
            new Button.OnClickListener(){
                @Override
                public void onClick(View v){

                    finish();

                }
            };

    private void sharPDF(){
        createPDF();

        File fileport = Environment.getExternalStorageDirectory();
        File file = new File( fileport,"1.MiiS/1.PDF"+ "/" + AI_pdf + ".pdf" );
        if (file.exists())
        {

            file.mkdir();

            try
            {

                Intent intent1=new Intent(Intent.ACTION_SEND);
                Uri tempUri = null;
                intent1.setDataAndType(tempUri, "application/pdf");

                Uri uri = FileProvider.getUriForFile(this, "com.example.miis200.fileprovider", file);
                Log.d("URL", "URL:" + uri);

                intent1.putExtra(Intent.EXTRA_STREAM, uri);
                intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent1.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                final String pid = getIntent().getExtras().getString("b");
                Log.i("AAAAAAAA",pid);
                SQLiteDatabase idDB = myDB.getReadableDatabase();
                idDB.delete("users_data", "PATIENTID=?", new String[]{pid});
                startActivity(Intent.createChooser(intent1, "Share PDF using.."));

                /*
                Intent intent=new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                */
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(Addexamination_aianal.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }

        }

    }

    private Button.OnClickListener seepdf =
            new Button.OnClickListener(){
                @Override
                public void onClick(final View v) {
                    if (!Button_Click.isFastDoubleClick()) {
                        viewPDF();
                    }
                }

            };

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

                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_aianal.this);
                    a_builder.setMessage(R.string.Go_home_message)
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = getIntent();
                                    String pID=intent.getStringExtra("b");
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


                                    idDB.update("users_data",values, "PATIENTID=?", new String[]{pID});
                                    Intent intentOption = new Intent(getApplicationContext(), Option.class);
                                    intentOption.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intentOption);
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

    public Mat MABBOX(Mat src_image, String stdr) throws JSONException {
        JSONObject jsonObject = new JSONObject(stdr);
        JSONArray array = jsonObject.getJSONArray("obj");
        for (int i = 0; i < array.length(); i++) {
            JSONObject annotation = new JSONObject(array.getString(i));
            JSONArray bbox = annotation.getJSONArray("bbox");
            int x0 = bbox.getInt(0)/2;
            int y0 = bbox.getInt(1)/2;
            int x1 = bbox.getInt(2)/2 + x0;
            int y1 = bbox.getInt(3)/2 + y0;
            int category_id = annotation.getInt("category_id");

            if (category_id == 1)
                if(imageWidth > 768){
                    Imgproc.rectangle(src_image, new Point(x0, y0), new Point(x1, y1), new Scalar(255, 255, 0), 3);
                } else {
                    Imgproc.rectangle(src_image, new Point(x0, y0), new Point(x1, y1), new Scalar(255, 255, 0), 1);
                }


        }
        return src_image;
    }

    public Mat HEMBBOX(Mat src_image, String stdr) throws JSONException {
        JSONObject jsonObject = new JSONObject(stdr);
        JSONArray array = jsonObject.getJSONArray("obj");
        for (int i = 0; i < array.length(); i++) {
            JSONObject annotation = new JSONObject(array.getString(i));
            JSONArray bbox = annotation.getJSONArray("bbox");
            int x0 = bbox.getInt(0)/2;
            int y0 = bbox.getInt(1)/2;
            int x1 = bbox.getInt(2)/2 + x0;
            int y1 = bbox.getInt(3)/2 + y0;
            int category_id = annotation.getInt("category_id");

            if (category_id == 2)
                if(imageWidth > 768){
                    Imgproc.rectangle(src_image, new Point(x0, y0), new Point(x1, y1), new Scalar(0, 255, 0), 3);
                } else{
                    Imgproc.rectangle(src_image, new Point(x0, y0), new Point(x1, y1), new Scalar(0, 255, 0), 1);
                }

        }
        return src_image;
    }

    public Mat HEBBOX(Mat src_image, String stdr) throws JSONException {
        JSONObject jsonObject = new JSONObject(stdr);
        JSONArray array = jsonObject.getJSONArray("obj");
        for (int i = 0; i < array.length(); i++) {
            JSONObject annotation = new JSONObject(array.getString(i));
            JSONArray bbox = annotation.getJSONArray("bbox");
            int x0 = bbox.getInt(0)/2;
            int y0 = bbox.getInt(1)/2;
            int x1 = bbox.getInt(2)/2+ x0;
            int y1 = bbox.getInt(3)/2 + y0;
            int category_id = annotation.getInt("category_id");

           if (category_id == 3)
               if(imageWidth > 768){
                   Imgproc.rectangle(src_image, new Point(x0, y0), new Point(x1, y1), new Scalar(0, 51, 255), 3);
               } else {
                   Imgproc.rectangle(src_image, new Point(x0, y0), new Point(x1, y1), new Scalar(0, 51, 255), 1);
               }

        }
        return src_image;
    }

    public Mat CWSBBOX(Mat src_image, String stdr) throws JSONException {
        JSONObject jsonObject = new JSONObject(stdr);
        JSONArray array = jsonObject.getJSONArray("obj");
        for (int i = 0; i < array.length(); i++) {
            JSONObject annotation = new JSONObject(array.getString(i));
            JSONArray bbox = annotation.getJSONArray("bbox");
            int x0 = bbox.getInt(0)/2;
            int y0 = bbox.getInt(1)/2;
            int x1 = bbox.getInt(2)/2 + x0;
            int y1 = bbox.getInt(3)/2 + y0;
            int category_id = annotation.getInt("category_id");

          if (category_id == 5)
              if(imageWidth > 768){
                  Imgproc.rectangle(src_image, new Point(x0, y0), new Point(x1, y1), new Scalar(0, 255, 255), 3);
              } else {
                  Imgproc.rectangle(src_image, new Point(x0, y0), new Point(x1, y1), new Scalar(0, 255, 255), 1);
              }


        }
        return src_image;
    }

public void MAdr() throws JSONException {
    String responseData = stdr;

    Log.i("uuuuu",path);
    Mat image = Imgcodecs.imread(path);
    Mat Maimage;
    Maimage = Mat.zeros(image.rows()/2, image.cols()/2, CvType.CV_8UC4);

    Maimage = MABBOX(Maimage, responseData);
    Bitmap bitmap = Bitmap.createBitmap(image.cols()/2, image.rows()/2, Bitmap.Config.ARGB_8888);

    Utils.matToBitmap(Maimage, bitmap);

    MAimage.setImageBitmap(bitmap); //设置Bitmap

}

    public void HEMdr() throws JSONException {
        String responseData = stdr;

        Mat image = Imgcodecs.imread(path);
        Mat Maimage;
        Maimage = Mat.zeros(image.rows()/2, image.cols()/2, CvType.CV_8UC4);

        Maimage = HEMBBOX(Maimage, responseData);
        Bitmap bitmap = Bitmap.createBitmap(image.cols()/2, image.rows()/2, Bitmap.Config.ARGB_8888);

        Utils.matToBitmap(Maimage, bitmap);

        HEMimage.setImageBitmap(bitmap); //设置Bitmap

    }

    public void HEdr() throws JSONException {
        String responseData = stdr;

        Mat image = Imgcodecs.imread(path);
        Mat Maimage;
        Maimage = Mat.zeros(image.rows()/2, image.cols()/2, CvType.CV_8UC4);

        Maimage = HEBBOX(Maimage, responseData);
        Bitmap bitmap = Bitmap.createBitmap(image.cols()/2, image.rows()/2, Bitmap.Config.ARGB_8888);

        Utils.matToBitmap(Maimage, bitmap);

        HEimage.setImageBitmap(bitmap); //设置Bitmap

    }

    public void CWSdr() throws JSONException {
        String responseData = stdr;

        Mat image = Imgcodecs.imread(path);
        Mat Maimage;
        Maimage = Mat.zeros(image.rows()/2, image.cols()/2, CvType.CV_8UC4);

        Maimage = CWSBBOX(Maimage, responseData);
        Bitmap bitmap = Bitmap.createBitmap(image.cols()/2, image.rows()/2, Bitmap.Config.ARGB_8888);

        Utils.matToBitmap(Maimage, bitmap);

        CWSimage.setImageBitmap(bitmap); //设置Bitmap

    }

    private void viewPDF() {
        createPDF();
        Intent intent = this.getIntent();
        String pName=intent.getStringExtra("a");
        String pID=intent.getStringExtra("b");

        File fileport = Environment.getExternalStorageDirectory();
        File file = new File( fileport,"1.MiiS/1.PDF"+ "/" + AI_pdf + ".pdf" );
        if (file.exists())
        {
            file.mkdir();


            try
            {

                Intent intent1=new Intent(Intent.ACTION_VIEW);
                Uri uri = FileProvider.getUriForFile(this, "com.example.miis200.fileprovider", file);
                Log.d("URL", "URL:" + uri);
                intent1.setDataAndType(uri, "application/pdf");
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent1);


                /*
                Intent intent=new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                */
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(Addexamination_aianal.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void createPDF() {

        LinearLayout viewreport_page = (LinearLayout) findViewById( R.id.report_page );
        bitmap = loadBitmapFromView(viewreport_page, viewreport_page.getWidth(), viewreport_page.getHeight());

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getMetrics(displaymetrics);
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float width = viewreport_page.getWidth();
        float hight = viewreport_page.getHeight();
        int convertWidth = (int) width, convertHighet = (int) hight;

        //Resources mResources = getResources();
        //Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertHighet,convertWidth*2, 1).create();
        //PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder( PrintAttributes.MediaSize.ISO_B5.getWidthMils() * 72 / 1000, PrintAttributes.MediaSize.ISO_B5.getHeightMils() * 72 / 1000, 1 ).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        //Paint paint = new Paint();
        //canvas.drawPaint(paint);

        float degrees = 90;//rotation degree
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, viewreport_page.getWidth(), viewreport_page.getHeight(), matrix, true);

        //bitmap = Bitmap.createScaledBitmap(bitmap,  PrintAttributes.MediaSize.ISO_B5.getWidthMils() * 72 / 1000,  PrintAttributes.MediaSize.ISO_B5.getHeightMils() * 72 / 1000, true);
        //paint.setColor(Color.WHITE);

        canvas.drawBitmap(bitmap, 0, 0 , null);

        document.finishPage(page);


        // Judament System version information
        if(android.os.Build.VERSION.SDK_INT>= android.os.Build.VERSION_CODES.KITKAT) {
            //File sdcarddir = android.os.Environment.getExternalStorageDirectory();
            FileOutputStream outputStream =null;
            //先实例化一个file对象，参数为路径名
            File fileport = Environment.getExternalStorageDirectory();
            //创建一个新的文件
            File file = new File( fileport,"1.MiiS/1.PDF");
            //先创建文件夹
            file.mkdirs();
            Intent intent = this.getIntent();
            String pName=intent.getStringExtra("a");
            String pID=intent.getStringExtra("b");
            SimpleDateFormat formatter = new   SimpleDateFormat   ("HHmmss");
            Date curDate =  new Date(System.currentTimeMillis());
            String str = formatter.format(curDate);

            try{

                AI_pdf = today + pName + pID + str;

                outputStream =new FileOutputStream( file + "/" + AI_pdf + ".pdf");
                document.writeTo(outputStream);
                document.close();
                Toast.makeText(Addexamination_aianal.this, R.string.PDF_create_success, Toast.LENGTH_SHORT).show();
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }catch(IOException e) {
                e.printStackTrace();
                Toast.makeText(Addexamination_aianal.this, R.string.PDF_create_fail, Toast.LENGTH_SHORT).show();
            }

        }
    }


    public static Bitmap loadBitmapFromView(View view, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawARGB(100, 225, 225, 255);
        view.draw(canvas);
        return bitmap;
    }

    public void openDialog() {
        DiaLogPrinter diaLogPrinter = new DiaLogPrinter();
        diaLogPrinter.show(getSupportFragmentManager(), "example dialog");
    }

}
