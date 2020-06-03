package com.example.miis200;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.miis200.base.BaseActivity;
import com.example.miis200.base.OnPermissionCallbackListener;
import com.example.miis200.bean.WifiListBean;
import com.example.miis200.utils.MyWifiManager;
import com.example.miis200.utils.WifiUtil;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Viewwait_viewimage extends BaseActivity {

    Timer AI_timer;//宣告一個時間函示
    int CountDown_time = 10;//設置初始秒數

    WifiManager wifiManager;
    List scannedResult;

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


    public String fiveResult = "";
    public String twoResult = "";
    public String drResult = "";
    private String ssid;
    private String fiveMemory = "";

    private String IP300;

    private Boolean Callfail = false;

    String message = " ", message2 = " ", message3 = " ", message4 = " ", message5 = " ", message6 = " ", message7 = " ", message8 = " ", message9 = " ", message10 = " ";

    EditText editText;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText7;
    EditText editText8;
    EditText editText9;
    EditText editText10;



    private void DIBWifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(AS_DIBSSID, AS_DIBSSIDPWD);
    }


    private void HUBWifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(AP_HUBSSID, AP_HUBSSIDPWD);
    }


    DatabaseHelper myDB;
    ArrayList<User> userList;
    ListView listView;
    User user;


    int rtn = 1;
    int rtn2 = 2;

    Button cancel;

    private WifiManager mWifiManager;
    private List<ScanResult> mScanResultList;//wifi列表
    private List<WifiListBean> wifiListBeanList;

    public String FiveClass,TwoClass,DrLesion="111";

    ProgressDialog DIBdialog;


    public ImageButton AIButton;
    String myURL;
    static {
        System.loadLibrary("opencv_java3");
    }

    Handler Toasthandler = new android.os.Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b = msg.getData();
            String newsTemp = b.getString("msg");
            Log.i("Tag", "handle" + b.getString("msg"));
            Log.i("Tag", "newsTemp" + newsTemp);
            //  initData(b.getString("msg"));
            Toast.makeText(Viewwait_viewimage.this, newsTemp, Toast.LENGTH_LONG).show();
            //   Result.setText(newsTemp);

        }

    };

    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    String path;

    public static final String TAG = "Waitviewimga";
    String Leyepath,Reyepath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewwait_viewimage);
        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        scannedResult = new ArrayList();

        loadData();
        updateViews();

        if( savedRadioIndex == 0){
            myURL = "http://" + AP_DIBIP +":5001/api/";
            IP300 = AP_DIBIP;
        }
        else if(savedRadioIndex == 1){
            myURL = "http://" + MH_DIBIP +":5001/api/";
            IP300 = MH_DIBIP;
        }
        else if(savedRadioIndex == 2){
            myURL = "http://192.168.28.11:5001/api/";
        }

        myDB = new DatabaseHelper(this);

        userList = new ArrayList<>();
        Cursor data = myDB.getListContents1();

        Intent intent = this.getIntent();
        String LeyeP=intent.getStringExtra("g");
        String ReyeP=intent.getStringExtra("h");

        String wvEp1=intent.getStringExtra("wvEpathA1");
        String wvEp2=intent.getStringExtra("wvEpathA2");
        String wvEp3=intent.getStringExtra("wvEpathA3");
        String wvEp4=intent.getStringExtra("wvEpathA4");
        String wvEp5=intent.getStringExtra("wvEpathA5");
        String wvEp6=intent.getStringExtra("wvEpathA6");
        String wvEp7=intent.getStringExtra("wvEpathA7");
        String wvEp8=intent.getStringExtra("wvEpathA8");








        //===============================================================================================
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Viewimage_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //===============================================================================================

        installData();
        initView();
        final ImageView readLeye = (ImageView) findViewById( R.id.readLeye );
        ImageView readReye = (ImageView) findViewById( R.id.readReye );
        final ImageView readeye1 = (ImageView) findViewById( R.id.readeye1 );
        ImageView readeye2 = (ImageView) findViewById( R.id.readeye2 );
        ImageView readeye3 = (ImageView) findViewById( R.id.readeye3 );
        ImageView readeye4 = (ImageView) findViewById( R.id.readeye4 );
        ImageView readeye5 = (ImageView) findViewById( R.id.readeye5 );
        ImageView readeye6 = (ImageView) findViewById( R.id.readeye6 );
        ImageView readeye7 = (ImageView) findViewById( R.id.readeye7 );
        ImageView readeye8 = (ImageView) findViewById( R.id.readeye8 );

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText6 = (EditText) findViewById(R.id.editText6);
        editText7 = (EditText) findViewById(R.id.editText7);
        editText8 = (EditText) findViewById(R.id.editText8);
        editText9 = (EditText) findViewById(R.id.editText9);
        editText10 = (EditText) findViewById(R.id.editText10);

        if(LeyeP == null) {
            readLeye.setVisibility(View.GONE);
        }
        if(ReyeP == null) {
            readReye.setVisibility(View.GONE);
        }
        if(wvEp1 == null) {
            readeye1.setVisibility(View.GONE);
        }
        if(wvEp2 == null) {
            readeye2.setVisibility(View.GONE);
        }
        if(wvEp3 == null) {
            readeye3.setVisibility(View.GONE);
        }
        if(wvEp4 == null) {
            readeye4.setVisibility(View.GONE);
        }
        if(wvEp5 == null) {
            readeye5.setVisibility(View.GONE);
        }
        if(wvEp6 == null) {
            readeye6.setVisibility(View.GONE);
        }
        if(wvEp7 == null) {
            readeye7.setVisibility(View.GONE);
        }
        if(wvEp8 == null) {
            readeye8.setVisibility(View.GONE);
        }


        if(LeyeP != null) {
            readLeye.setImageURI(Uri.parse(LeyeP));
            int width = readLeye.getDrawable().getIntrinsicWidth();
            int heigh = readLeye.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://"+LeyeP).resize(width/3,heigh/3).into(readLeye);
        }
        if(ReyeP != null) {
            readReye.setImageURI(Uri.parse(ReyeP));
            int width = readReye.getDrawable().getIntrinsicWidth();
            int heigh = readReye.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://"+ReyeP).resize(width/3,heigh/3).into(readReye);
        }

        if(wvEp1 != null) {
            readeye1.setImageURI(Uri.parse(wvEp1));
            int width = readeye1.getDrawable().getIntrinsicWidth();
            int heigh = readeye1.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://"+wvEp1).resize(width/3,heigh/3).into(readeye1);
        }
        if(wvEp2 != null) {
            readeye2.setImageURI(Uri.parse(wvEp2));
            int width = readeye2.getDrawable().getIntrinsicWidth();
            int heigh = readeye2.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://"+wvEp2).resize(width/3,heigh/3).into(readeye2);
        }
        if(wvEp3 != null) {
            readeye3.setImageURI(Uri.parse(wvEp3));
            int width = readeye3.getDrawable().getIntrinsicWidth();
            int heigh = readeye3.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://"+wvEp3).resize(width/3,heigh/3).into(readeye3);
        }
        if(wvEp4 != null) {
            readeye4.setImageURI(Uri.parse(wvEp4));
            int width = readeye4.getDrawable().getIntrinsicWidth();
            int heigh = readeye4.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://"+wvEp4).resize(width/3,heigh/3).into(readeye4);
        }
        if(wvEp5 != null) {
            readeye5.setImageURI(Uri.parse(wvEp5));
            int width = readeye5.getDrawable().getIntrinsicWidth();
            int heigh = readeye5.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://"+wvEp5).resize(width/3,heigh/3).into(readeye5);
        }
        if(wvEp6 != null) {
            readeye6.setImageURI(Uri.parse(wvEp6));
            int width = readeye6.getDrawable().getIntrinsicWidth();
            int heigh = readeye6.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://"+wvEp6).resize(width/3,heigh/3).into(readeye6);
        }
        if(wvEp7 != null) {
            readeye7.setImageURI(Uri.parse(wvEp7));
            int width = readeye7.getDrawable().getIntrinsicWidth();
            int heigh = readeye7.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://"+wvEp7).resize(width/3,heigh/3).into(readeye7);
        }
        if(wvEp8 != null) {
            readeye8.setImageURI(Uri.parse(wvEp8));
            int width = readeye8.getDrawable().getIntrinsicWidth();
            int heigh = readeye8.getDrawable().getIntrinsicHeight();
            Picasso.get().load("file://"+wvEp8).resize(width/3,heigh/3).into(readeye8);
        }




        if (ActivityCompat.checkSelfPermission(Viewwait_viewimage.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Viewwait_viewimage.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }







        Button newReport = (Button)findViewById(R.id.Viewprinter);
        newReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Button_Click.isFastDoubleClick()) {
                    Intent intent = new Intent(Viewwait_viewimage.this, Viewwait_chooseimg_viewfinish.class);

                    message=editText.getText().toString();
                    message2=editText2.getText().toString();
                    message3=editText3.getText().toString();
                    message4=editText4.getText().toString();
                    message5=editText5.getText().toString();
                    message6=editText6.getText().toString();
                    message7=editText7.getText().toString();
                    message8=editText8.getText().toString();
                    message9=editText9.getText().toString();
                    message10=editText10.getText().toString();


                    intent.putExtra("edit",message);

                    final String pname = getIntent().getExtras().getString("a");
                    final String pid = getIntent().getExtras().getString("b");
                    final String pbir = getIntent().getExtras().getString("c");
                    final String pgender = getIntent().getExtras().getString("d");
                    final String pphone = getIntent().getExtras().getString("e");
                    final String checkTime = getIntent().getExtras().getString("f");

                    final String page = getIntent().getExtras().getString("age");





                    Log.i("IIIIIIIInnnnnntttttt",pname);
                    Log.i("IIIIIIIInnnnnntttttt",pid);
                    Log.i("IIIIIIIInnnnnntttttt",pbir);
                    Log.i("IIIIIIIInnnnnntttttt",pgender);
                    Log.i("IIIIIIIInnnnnntttttt",pphone);
                    Log.i("IIIIIIIInnnnnntttttt",checkTime);
                    intent.putExtra("a",pname);
                    intent.putExtra("b",pid);
                    intent.putExtra("c",pbir);
                    intent.putExtra("d",pgender);
                    intent.putExtra("e",pphone);
                    intent.putExtra("f",checkTime);
                    intent.putExtra("age",page);



                    SQLiteDatabase idDB = myDB.getReadableDatabase();
                    ContentValues values = new ContentValues();

                    values.put("DOCTORDESC",message);
                    values.put("DOCTORDESC2",message2);
                    values.put("DOCTORDESC3",message3);
                    values.put("DOCTORDESC4",message4);
                    values.put("DOCTORDESC5",message5);
                    values.put("DOCTORDESC6",message6);
                    values.put("DOCTORDESC7",message7);
                    values.put("DOCTORDESC8",message8);
                    values.put("DOCTORDESC9",message9);
                    values.put("DOCTORDESC10",message10);
                    idDB.update("users_data",values, "PATIENTID=?", new String[]{pid});

                    startActivity(intent);
                }
            }
        });




        Button newfinish = (Button)findViewById(R.id.Viewfinish);
        newfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder a_builder = new AlertDialog.Builder(Viewwait_viewimage.this);
                a_builder.setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final String pid = getIntent().getExtras().getString("b");
                                final String Lepath = getIntent().getExtras().getString("g");
                                final String Repath = getIntent().getExtras().getString("h");

                                SQLiteDatabase idDB = myDB.getReadableDatabase();
                                ContentValues values = new ContentValues();
                                values.put("STATUS","2");
                                values.put("LEFTIMAGEPATH",Lepath);
                                values.put("RIGHTIMAGEPATH",Repath);
                                values.put("DOCTORDESC",message);
                                values.put("DOCTORDESC2",message2);
                                values.put("DOCTORDESC3",message3);
                                values.put("DOCTORDESC4",message4);
                                values.put("DOCTORDESC5",message5);
                                values.put("DOCTORDESC6",message6);
                                values.put("DOCTORDESC7",message7);
                                values.put("DOCTORDESC8",message8);
                                values.put("DOCTORDESC9",message9);
                                values.put("DOCTORDESC10",message10);

                                idDB.update("users_data",values, "PATIENTID=?", new String[]{pid});
                                Intent intent = new Intent(getApplicationContext(), Option.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle(R.string.Viewimage_view_finish_check);
                alert.show();





                //===========================================


            }
        });

        Button viewimgAcnacel = (Button)findViewById(R.id.cancelViewimg);
        viewimgAcnacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pid = getIntent().getExtras().getString("b");

                message=editText.getText().toString();
                message2=editText2.getText().toString();
                message3=editText3.getText().toString();
                message4=editText4.getText().toString();
                message5=editText5.getText().toString();
                message6=editText6.getText().toString();
                message7=editText7.getText().toString();
                message8=editText8.getText().toString();
                message9=editText9.getText().toString();
                message10=editText10.getText().toString();


                SQLiteDatabase idDB = myDB.getReadableDatabase();
                Cursor cursor = idDB.query("users_data", null, "PATIENTID=?", new String[]{pid},null,null,null);
                cursor.moveToFirst();
                String AIimg1path = cursor.getString(cursor.getColumnIndex("AIIMG1"));


                if(message.length() == 0 && message2.length() == 0 && message3.length() == 0 && message4.length() == 0 && message5.length() == 0 &&
                        message6.length() == 0 && message7.length() == 0 && message8.length() == 0 && message9.length() == 0 && message10.length() == 0 && AIimg1path.equals(" ")){
                    Intent intent = new Intent(getApplicationContext(), Option.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {

                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Viewwait_viewimage.this);
                    a_builder.setMessage(R.string.Cancel_message)
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String pid = getIntent().getExtras().getString("b");

                                    SQLiteDatabase idDB = myDB.getReadableDatabase();
                                    ContentValues values = new ContentValues();

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
                                    idDB.update("users_data",values, "PATIENTID=?", new String[]{pid.toString()});

                                    Intent intent = new Intent(getApplicationContext(), Option.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle(R.string.Cancel_title);

                    alert.show();
                }

            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readLeye.performClick();
            }
        }, 0);

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


                String pid = getIntent().getExtras().getString("b");

                message=editText.getText().toString();
                message2=editText2.getText().toString();
                message3=editText3.getText().toString();
                message4=editText4.getText().toString();
                message5=editText5.getText().toString();
                message6=editText6.getText().toString();
                message7=editText7.getText().toString();
                message8=editText8.getText().toString();
                message9=editText9.getText().toString();
                message10=editText10.getText().toString();


                SQLiteDatabase idDB = myDB.getReadableDatabase();
                Cursor cursor = idDB.query("users_data", null, "PATIENTID=?", new String[]{pid},null,null,null);
                cursor.moveToFirst();
                String AIimg1path = cursor.getString(cursor.getColumnIndex("AIIMG1"));


                if(message.length() == 0 && message2.length() == 0 && message3.length() == 0 && message4.length() == 0 && message5.length() == 0 &&
                        message6.length() == 0 && message7.length() == 0 && message8.length() == 0 && message9.length() == 0 && message10.length() == 0 && AIimg1path.equals(" ")){
                    Intent intent = new Intent(getApplicationContext(), Option.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {

                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Viewwait_viewimage.this);
                    a_builder.setMessage(R.string.Go_home_message)
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String pid = getIntent().getExtras().getString("b");

                                    SQLiteDatabase idDB = myDB.getReadableDatabase();
                                    ContentValues values = new ContentValues();

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
                                    idDB.update("users_data",values, "PATIENTID=?", new String[]{pid.toString()});

                                    Intent intent = new Intent(getApplicationContext(), Option.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle(R.string.Go_home_title);

                    alert.show();
                }

        }

        return super.onOptionsItemSelected(item);
    }
    //===============================================================================================
    public void mess(View v)
    {
        Intent intent = this.getIntent();
        String LeyeP=intent.getStringExtra("g");
        String ReyeP=intent.getStringExtra("h");

        String eyeP1=intent.getStringExtra("wvEpathA1");
        String eyeP2=intent.getStringExtra("wvEpathA2");
        String eyeP3=intent.getStringExtra("wvEpathA3");
        String eyeP4=intent.getStringExtra("wvEpathA4");
        String eyeP5=intent.getStringExtra("wvEpathA5");
        String eyeP6=intent.getStringExtra("wvEpathA6");
        String eyeP7=intent.getStringExtra("wvEpathA7");
        String eyeP8=intent.getStringExtra("wvEpathA8");



        //initialize image view object
        PhotoView im=(PhotoView)findViewById(R.id.readEye);
        //get clicked button id from view object
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        String LPath=Leyepath;
        String RPath=Reyepath;

        switch(v.getId())
        {
            case R.id.readLeye:
                closekeyboard();
                Log.i("DEC200Img1",LeyeP);
                //if button1 is clicked than set image1
                Bitmap bmL = BitmapFactory.decodeFile(LeyeP,options);
                im.setImageBitmap(bmL);
                path = LeyeP;

                editText.setVisibility(View.VISIBLE);
                editText2.setVisibility(View.GONE);
                editText3.setVisibility(View.GONE);
                editText4.setVisibility(View.GONE);
                editText5.setVisibility(View.GONE);
                editText6.setVisibility(View.GONE);
                editText7.setVisibility(View.GONE);
                editText8.setVisibility(View.GONE);
                editText9.setVisibility(View.GONE);
                editText10.setVisibility(View.GONE);


                break;
            case R.id.readReye:
                closekeyboard();
                Log.i("DEC200Img1",ReyeP);
                //if button2 is clicked than set image2
                Bitmap bmR = BitmapFactory.decodeFile(ReyeP,options);
                im.setImageBitmap(bmR);
                path = ReyeP;
                editText.setVisibility(View.GONE);
                editText2.setVisibility(View.VISIBLE);
                editText3.setVisibility(View.GONE);
                editText4.setVisibility(View.GONE);
                editText5.setVisibility(View.GONE);
                editText6.setVisibility(View.GONE);
                editText7.setVisibility(View.GONE);
                editText8.setVisibility(View.GONE);
                editText9.setVisibility(View.GONE);
                editText10.setVisibility(View.GONE);

                break;

            case R.id.readeye1:
                closekeyboard();
                Log.i("DEC200Img1",eyeP1);
                //if button2 is clicked than set image2
                Bitmap bm1 = BitmapFactory.decodeFile(eyeP1,options);
                im.setImageBitmap(bm1);
                path = eyeP1;
                editText.setVisibility(View.GONE);
                editText2.setVisibility(View.GONE);
                editText3.setVisibility(View.VISIBLE);
                editText4.setVisibility(View.GONE);
                editText5.setVisibility(View.GONE);
                editText6.setVisibility(View.GONE);
                editText7.setVisibility(View.GONE);
                editText8.setVisibility(View.GONE);
                editText9.setVisibility(View.GONE);
                editText10.setVisibility(View.GONE);

                break;
            case R.id.readeye2:
                closekeyboard();
                Log.i("DEC200Img1",eyeP2);
                //if button2 is clicked than set image2
                Bitmap bm2 = BitmapFactory.decodeFile(eyeP2,options);
                im.setImageBitmap(bm2);
                path = eyeP2;
                editText.setVisibility(View.GONE);
                editText2.setVisibility(View.GONE);
                editText3.setVisibility(View.GONE);
                editText4.setVisibility(View.VISIBLE);
                editText5.setVisibility(View.GONE);
                editText6.setVisibility(View.GONE);
                editText7.setVisibility(View.GONE);
                editText8.setVisibility(View.GONE);
                editText9.setVisibility(View.GONE);
                editText10.setVisibility(View.GONE);

                break;

            case R.id.readeye3:
                closekeyboard();
                Log.i("DEC200Img1",eyeP3);
                //if button2 is clicked than set image2
                Bitmap bm3 = BitmapFactory.decodeFile(eyeP3,options);
                im.setImageBitmap(bm3);
                path = eyeP3;
                editText.setVisibility(View.GONE);
                editText2.setVisibility(View.GONE);
                editText3.setVisibility(View.GONE);
                editText4.setVisibility(View.GONE);
                editText5.setVisibility(View.VISIBLE);
                editText6.setVisibility(View.GONE);
                editText7.setVisibility(View.GONE);
                editText8.setVisibility(View.GONE);
                editText9.setVisibility(View.GONE);
                editText10.setVisibility(View.GONE);

                break;
            case R.id.readeye4:
                closekeyboard();
                Log.i("DEC200Img1",eyeP4);
                //if button2 is clicked than set image2
                Bitmap bm4 = BitmapFactory.decodeFile(eyeP4,options);
                im.setImageBitmap(bm4);
                path = eyeP4;
                editText.setVisibility(View.GONE);
                editText2.setVisibility(View.GONE);
                editText3.setVisibility(View.GONE);
                editText4.setVisibility(View.GONE);
                editText5.setVisibility(View.GONE);
                editText6.setVisibility(View.VISIBLE);
                editText7.setVisibility(View.GONE);
                editText8.setVisibility(View.GONE);
                editText9.setVisibility(View.GONE);
                editText10.setVisibility(View.GONE);

                break;

            case R.id.readeye5:
                closekeyboard();
                Log.i("DEC200Img1",eyeP5);
                //if button2 is clicked than set image2
                Bitmap bm5 = BitmapFactory.decodeFile(eyeP5,options);
                im.setImageBitmap(bm5);
                path = eyeP5;
                editText.setVisibility(View.GONE);
                editText2.setVisibility(View.GONE);
                editText3.setVisibility(View.GONE);
                editText4.setVisibility(View.GONE);
                editText5.setVisibility(View.GONE);
                editText6.setVisibility(View.GONE);
                editText7.setVisibility(View.VISIBLE);
                editText8.setVisibility(View.GONE);
                editText9.setVisibility(View.GONE);
                editText10.setVisibility(View.GONE);

                break;
            case R.id.readeye6:
                closekeyboard();
                Log.i("DEC200Img1",eyeP6);
                //if button2 is clicked than set image2
                Bitmap bm6 = BitmapFactory.decodeFile(eyeP6,options);
                im.setImageBitmap(bm6);
                path = eyeP6;
                editText.setVisibility(View.GONE);
                editText2.setVisibility(View.GONE);
                editText3.setVisibility(View.GONE);
                editText4.setVisibility(View.GONE);
                editText5.setVisibility(View.GONE);
                editText6.setVisibility(View.GONE);
                editText7.setVisibility(View.GONE);
                editText8.setVisibility(View.VISIBLE);
                editText9.setVisibility(View.GONE);
                editText10.setVisibility(View.GONE);

                break;

            case R.id.readeye7:
                closekeyboard();
                Log.i("DEC200Img1",eyeP7);
                //if button2 is clicked than set image2
                Bitmap bm7 = BitmapFactory.decodeFile(eyeP7,options);
                im.setImageBitmap(bm7);
                path = eyeP7;
                editText.setVisibility(View.GONE);
                editText2.setVisibility(View.GONE);
                editText3.setVisibility(View.GONE);
                editText4.setVisibility(View.GONE);
                editText5.setVisibility(View.GONE);
                editText6.setVisibility(View.GONE);
                editText7.setVisibility(View.GONE);
                editText8.setVisibility(View.GONE);
                editText9.setVisibility(View.VISIBLE);
                editText10.setVisibility(View.GONE);

                break;
            case R.id.readeye8:
                closekeyboard();
                Log.i("DEC200Img1",eyeP8);
                //if button2 is clicked than set image2
                Bitmap bm8 = BitmapFactory.decodeFile(eyeP8,options);
                im.setImageBitmap(bm8);
                path = eyeP8;
                editText.setVisibility(View.GONE);
                editText2.setVisibility(View.GONE);
                editText3.setVisibility(View.GONE);
                editText4.setVisibility(View.GONE);
                editText5.setVisibility(View.GONE);
                editText6.setVisibility(View.GONE);
                editText7.setVisibility(View.GONE);
                editText8.setVisibility(View.GONE);
                editText9.setVisibility(View.GONE);
                editText10.setVisibility(View.VISIBLE);

                break;
        }
    }

    private void initPermission() {
        onRequestPermission(new String[]{
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,

        }, new OnPermissionCallbackListener() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
            }
        });
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_addexamination_viewimage);
        AIButton = (ImageButton) findViewById(R.id.AIButton);
        initPermission();

        AIButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {

                //AP MODE Click
                if( savedRadioIndex == 0){

                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    final String ssid = wifiInfo.getSSID();


                    if (mWifiManager == null) {
                        mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                    }

                    wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
                    scannedResult = new ArrayList();

                    //获取到wifi列表
                    mScanResultList = MyWifiManager.getWifiList(mWifiManager);

                    for (int i = 0; i < mScanResultList.size(); i++) {
                        scannedResult.add( mScanResultList.get(i).SSID);
                    }

                    if(scannedResult.indexOf(AP_HUBSSID)<0){
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Viewwait_viewimage.this);
                        a_builder.setMessage(R.string.Viewimage_1_check_ap_2_check_ssid_setting)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle(R.string.Alert);
                        alert.show();
                    }
                    else if(!ssid.equals("\"" + AP_HUBSSID + "\"")){

                        HUBWifi();
                        Context context1 = v.getContext();
                        DIBdialog= ProgressDialog.show(context1,getResources().getString(R.string.Linking_AP), getResources().getString(R.string.Please_wait),true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        }).start();

                        Handler handlerd = new Handler();
                        handlerd.postDelayed(new Runnable(){

                            @Override
                            public void run() {

                                DIBdialog.dismiss();
                                String DSCping = Ping(IP300);
                                System.out.println("PINGPINGPING : " + DSCping);

                                if(DSCping.equals("success")){
                                    Context context1 = v.getContext();
                                    final ProgressDialog dialog= ProgressDialog.show(context1,"分析中", "請稍等....",true);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");

                                            Date curDate = new Date(System.currentTimeMillis()) ;

                                            fiveclass(path);

                                            while (!fiveResult.equals("true")) {
                                                try {
                                                    Thread.sleep(1000);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                if (fiveMemory.equals("Error: Can not get 5_class prediction! [Errno 12] Cannot allocate memory")) {
                                                    DimMemoryDialog();
                                                    break;
                                                } else if (fiveResult.equals("false")) {
                                                    DiaLogTryagin();
                                                    break;
                                                } else if (Callfail){
                                                    dialog.dismiss();
                                                    Callfail=false;
                                                    break;
                                                }
                                            }



                                            twoclass(path);

                                            while (!twoResult.equals("true")) {
                                                try {
                                                    Thread.sleep(1000);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            drlesion(path);

                                            while (!drResult.equals("true")) {
                                                try {
                                                    Thread.sleep(1000);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }


                                            if (fiveResult.equals("true") && twoResult.equals("true") && drResult.equals("true")) {
                                                final Intent intent = new Intent(); //登入成功轉跳至首頁
                                                intent.setClass(Viewwait_viewimage.this, Viewwait_aianal.class);
                                                String pname = getIntent().getExtras().getString("a");
                                                String pid = getIntent().getExtras().getString("b");
                                                intent.putExtra("a",pname);
                                                intent.putExtra("b",pid);

                                                intent.putExtra("five",FiveClass);
                                                intent.putExtra("two",TwoClass);
                                                intent.putExtra("dr",DrLesion);
                                                intent.putExtra("imagepath",path);
                                                startActivity(intent);
                                            }

                                            fiveResult = "";
                                            twoResult = "";
                                            drResult = "";

                                            try {
                                                Thread.sleep(0);
                                                dialog.dismiss();
                                            } catch (InterruptedException ex) {
                                                ex.printStackTrace();
                                            }

                                        }
                                    }).start();

                                    AI_timer = new Timer();//時間函示初始化
                                    //這邊開始跑時間執行緒
                                    final TimerTask task = new TimerTask() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    CountDown_time--;//時間倒數
                                                    Log.i("json",String.valueOf(CountDown_time));
                                                    //if判斷示裡面放置在時間結束後想要完成的事件
                                                    if (CountDown_time < 1) {
                                                        AI_timer.cancel();
                                                        dialog.dismiss();
                                                        CountDown_time = 10; //讓時間執行緒保持輪迴
                                                    }
                                                }
                                            });
                                        }
                                    };
                                    AI_timer.schedule(task, 1000, 1000);//時間在幾毫秒過後開始以多少毫秒執行



                                } else if(DSCping.equals("faild")){
                                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Viewwait_viewimage.this);
                                    a_builder.setMessage(R.string.Viewimage_1_IP_invalid_2_check_DIB_status_3_check_DIB_IP)
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            });
                                    AlertDialog alert = a_builder.create();
                                    alert.setTitle(R.string.Alert);
                                    alert.show();
                                }

                            }}, 6000);


                    }
                    else if(ssid.equals("\"" + AP_HUBSSID + "\"")){

                        String DSCping = Ping(IP300);
                        System.out.println("AP_PINGPINGPING : " + DSCping);

                        if(DSCping.equals("success")){
                            Context context1 = v.getContext();
                            final ProgressDialog dialog= ProgressDialog.show(context1,getResources().getString(R.string.Viewimage_Analyzing), getResources().getString(R.string.Please_wait),true);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");

                                    Date curDate = new Date(System.currentTimeMillis()) ;
                                    String str = formatter.format(curDate);


                                    fiveclass(path);

                                    while (!fiveResult.equals("true")) {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        if (fiveMemory.equals("Error: Can not get 5_class prediction! [Errno 12] Cannot allocate memory")) {
                                            DimMemoryDialog();
                                            break;
                                        } else if (fiveResult.equals("false")) {
                                            DiaLogTryagin();
                                            break;
                                        } else if (Callfail){
                                            dialog.dismiss();
                                            Callfail=false;
                                            break;
                                        }
                                    }



                                    twoclass(path);

                                    while (!twoResult.equals("true")) {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    drlesion(path);

                                    while (!drResult.equals("true")) {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }


                                    if (fiveResult.equals("true") && twoResult.equals("true") && drResult.equals("true")) {
                                        final Intent intent = new Intent(); //登入成功轉跳至首頁
                                        intent.setClass(Viewwait_viewimage.this, Viewwait_aianal.class);
                                        String pname = getIntent().getExtras().getString("a");
                                        String pid = getIntent().getExtras().getString("b");
                                        intent.putExtra("a",pname);
                                        intent.putExtra("b",pid);

                                        intent.putExtra("five",FiveClass);
                                        intent.putExtra("two",TwoClass);
                                        intent.putExtra("dr",DrLesion);
                                        intent.putExtra("imagepath",path);
                                        startActivity(intent);
                                    }

                                    fiveResult = "";
                                    twoResult = "";
                                    drResult = "";

                                    try {
                                        Thread.sleep(0);
                                        dialog.dismiss();
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }

                                }
                            }).start();

                            AI_timer = new Timer();//時間函示初始化
                            //這邊開始跑時間執行緒
                            final TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            CountDown_time--;//時間倒數
                                            Log.i("json",String.valueOf(CountDown_time));
                                            //if判斷示裡面放置在時間結束後想要完成的事件
                                            if (CountDown_time < 1) {
                                                AI_timer.cancel();
                                                dialog.dismiss();
                                                CountDown_time = 10; //讓時間執行緒保持輪迴
                                            }
                                        }
                                    });
                                }
                            };
                            AI_timer.schedule(task, 1000, 1000);//時間在幾毫秒過後開始以多少毫秒執行



                        } else if(DSCping.equals("faild")){
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(Viewwait_viewimage.this);
                            a_builder.setMessage(R.string.Viewimage_1_check_DIB_status_2_check_DIP_IP)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                            AlertDialog alert = a_builder.create();
                            alert.setTitle(R.string.Alert);
                            alert.show();
                        }


                    }


                }

                //Mobile Hospot Mode Click
                else if(savedRadioIndex == 1){

                    String DIB_status = Ping(MH_DIBIP);
                    System.out.println("MH_PINGPINGPING : " + DIB_status);
                    if(DIB_status.equals("success")){
                        Context context1 = v.getContext();
                        final ProgressDialog dialog= ProgressDialog.show(context1,getResources().getString(R.string.Viewimage_Analyzing), getResources().getString(R.string.Please_wait),true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");

                                Date curDate = new Date(System.currentTimeMillis()) ;
                                String str = formatter.format(curDate);


                                fiveclass(path);

                                Log.i("wwweeee","dddddddddddddddwwww");

                                while (!fiveResult.equals("true")) {
                                    Log.i("wwweeee","wwweeeeeeqqqq");
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    if (fiveMemory.equals("Error: Can not get 5_class prediction! [Errno 12] Cannot allocate memory")) {
                                        DimMemoryDialog();
                                        break;
                                    } else if (fiveResult.equals("false")) {
                                        DiaLogTryagin();
                                        break;
                                    } else if (Callfail){
                                        dialog.dismiss();
                                        Callfail = false;
                                        break;
                                    }
                                }



                                twoclass(path);

                                while (!twoResult.equals("true")) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                drlesion(path);

                                while (!drResult.equals("true")) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }


                                if (fiveResult.equals("true") && twoResult.equals("true") && drResult.equals("true")) {
                                    final Intent intent = new Intent(); //登入成功轉跳至首頁
                                    intent.setClass(Viewwait_viewimage.this, Viewwait_aianal.class);
                                    String pname = getIntent().getExtras().getString("a");
                                    String pid = getIntent().getExtras().getString("b");
                                    intent.putExtra("a",pname);
                                    intent.putExtra("b",pid);

                                    intent.putExtra("five",FiveClass);
                                    intent.putExtra("two",TwoClass);
                                    intent.putExtra("dr",DrLesion);
                                    intent.putExtra("imagepath",path);
                                    startActivity(intent);
                                }

                                fiveResult = "";
                                twoResult = "";
                                drResult = "";

                                try {
                                    Thread.sleep(0);
                                    dialog.dismiss();
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }

                            }
                        }).start();

                        AI_timer = new Timer();//時間函示初始化
                        //這邊開始跑時間執行緒
                        final TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountDown_time--;//時間倒數
                                        Log.i("json",String.valueOf(CountDown_time));
                                        //if判斷示裡面放置在時間結束後想要完成的事件
                                        if (CountDown_time < 1) {
                                            AI_timer.cancel();
                                            dialog.dismiss();
                                            CountDown_time = 10; //讓時間執行緒保持輪迴
                                        }
                                    }
                                });
                            }
                        };
                        AI_timer.schedule(task, 1000, 1000);//時間在幾毫秒過後開始以多少毫秒執行
                    }
                    else {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Viewwait_viewimage.this);
                        a_builder.setMessage(R.string.Viewimage_1_check_hotsopt_ONorOFF_2_check_DIB_status_3_check_IP)
                                .setCancelable(false)
                                .setPositiveButton("ok",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle(R.string.Alert);
                        alert.show();
                    }
                }

                //Auto switch Mode Click
                else if(savedRadioIndex == 2) {

                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    String ssid = wifiInfo.getSSID();
                    Context context1 = v.getContext();

                    if (mWifiManager == null) {
                        mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                    }

                    scannedResult = new ArrayList();



                    //开启wifi
                    MyWifiManager.openWifi(mWifiManager);
                    //获取到wifi列表
                    mScanResultList = MyWifiManager.getWifiList(mWifiManager);

                    for (int i = 0; i < mScanResultList.size(); i++) {
                        scannedResult.add( mScanResultList.get(i).SSID);
                    }

                    if (scannedResult.indexOf(AS_DIBSSID) < 0) {
                        openDialog();
                    }
                    else if (!ssid.equals("\"" + AS_DIBSSID + "\"")) {

                        final ProgressDialog dialog = ProgressDialog.show(context1, "分析中", "請稍等....", true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                while (!DIB_SSID().equals("success")) {
                                    try {
                                        DIBWifi();
                                        DIB_SSID();
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                fiveclass(path);

                                while (!fiveResult.equals("true")) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    if (fiveMemory.equals("Error: Can not get 5_class prediction! [Errno 12] Cannot allocate memory")) {
                                        DimMemoryDialog();
                                        break;
                                    } else if (fiveResult.equals("false")) {
                                        DiaLogTryagin();
                                        break;
                                    } else if (Callfail){
                                        dialog.dismiss();
                                        Callfail = false;
                                        break;
                                    }
                                }



                                twoclass(path);

                                while (!twoResult.equals("true")) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                drlesion(path);

                                while (!drResult.equals("true")) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }



                                if (fiveResult.equals("true") && twoResult.equals("true") && drResult.equals("true")) {
                                    final Intent intent = new Intent(); //登入成功轉跳至首頁
                                    intent.setClass(Viewwait_viewimage.this, Viewwait_aianal.class);
                                    String pname = getIntent().getExtras().getString("a");
                                    String pid = getIntent().getExtras().getString("b");
                                    intent.putExtra("a",pname);
                                    intent.putExtra("b",pid);

                                    intent.putExtra("five",FiveClass);
                                    intent.putExtra("two",TwoClass);
                                    intent.putExtra("dr",DrLesion);
                                    intent.putExtra("imagepath",path);
                                    startActivity(intent);
                                }

                                fiveResult = "";
                                twoResult = "";
                                drResult = "";

                                try {
                                    Thread.sleep(500);
                                    dialog.dismiss();
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }

                            }
                        }).start();

                        AI_timer = new Timer();//時間函示初始化
                        //這邊開始跑時間執行緒
                        final TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountDown_time--;//時間倒數
                                        Log.i("json",String.valueOf(CountDown_time));
                                        //if判斷示裡面放置在時間結束後想要完成的事件
                                        if (CountDown_time < 1) {
                                            AI_timer.cancel();
                                            dialog.dismiss();
                                            CountDown_time = 10; //讓時間執行緒保持輪迴
                                        }
                                    }
                                });
                            }
                        };
                        AI_timer.schedule(task, 1000, 1000);//時間在幾毫秒過後開始以多少毫秒執行

                    }
                    else {

                        final ProgressDialog dialog = ProgressDialog.show(context1, getResources().getString(R.string.Viewimage_Analyzing), getResources().getString(R.string.Please_wait), true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {


                                fiveclass(path);

                                while (!fiveResult.equals("true")) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    if (fiveMemory.equals("Error: Can not get 5_class prediction! [Errno 12] Cannot allocate memory")) {
                                        DimMemoryDialog();
                                        break;
                                    } else if (fiveResult.equals("false")) {
                                        DiaLogTryagin();
                                        break;
                                    } else if (Callfail) {
                                        dialog.dismiss();
                                        Callfail = false;
                                        break;
                                    }

                                }



                                twoclass(path);

                                while (!twoResult.equals("true")) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                drlesion(path);

                                while (!drResult.equals("true")) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }


                                if (fiveResult.equals("true") && twoResult.equals("true") && drResult.equals("true")) {
                                    final Intent intent = new Intent(); //登入成功轉跳至首頁
                                    intent.setClass(Viewwait_viewimage.this, Viewwait_aianal.class);
                                    String pname = getIntent().getExtras().getString("a");
                                    String pid = getIntent().getExtras().getString("b");
                                    intent.putExtra("a",pname);
                                    intent.putExtra("b",pid);

                                    intent.putExtra("five",FiveClass);
                                    intent.putExtra("two",TwoClass);
                                    intent.putExtra("dr",DrLesion);
                                    intent.putExtra("imagepath",path);
                                    startActivity(intent);
                                }

                                fiveResult = "";
                                twoResult = "";
                                drResult = "";

                                try {
                                    Thread.sleep(0);
                                    dialog.dismiss();
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }

                            }
                        }).start();

                        AI_timer = new Timer();//時間函示初始化
                        //這邊開始跑時間執行緒
                        final TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountDown_time--;//時間倒數
                                        Log.i("json",String.valueOf(CountDown_time));
                                        //if判斷示裡面放置在時間結束後想要完成的事件
                                        if (CountDown_time < 1) {
                                            AI_timer.cancel();
                                            dialog.dismiss();
                                            CountDown_time = 10; //讓時間執行緒保持輪迴
                                        }
                                    }
                                });
                            }
                        };
                        AI_timer.schedule(task, 1000, 1000);//時間在幾毫秒過後開始以多少毫秒執行
                    }


                }

            }
        });
    }

    private String aimWifiName = "DIB-00044b8d480c";
    private String aimWifiPwd = "Aa123456";

    private void changeWifi2() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(aimWifiName, aimWifiPwd);

    }

    @Override
    public void installData() {

    }
    public Mat BBOX5(Mat src_image, String jsonResponse) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonResponse);
        FiveClass = jsonObject.toString();
        fiveResult = (jsonObject.getBoolean("success") ) ?"true":"false";
        fiveMemory = jsonObject.getString("message");
        Log.i("bbbbbbbb",fiveResult);
        Log.i("FiveJson",FiveClass);

        return src_image;
    }

    public Mat BBOX2(Mat src_image, String jsonResponse) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        TwoClass = jsonObject.toString();
        twoResult = (jsonObject.getBoolean("success") ) ?"true":"false";
        Log.i("TwoJson",TwoClass);

        return src_image;
    }

    public Mat BBOXdr(Mat src_image, String jsonResponse) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        DrLesion = jsonObject.toString();
        drResult = (jsonObject.getBoolean("success") ) ?"true":"false";
        Log.i("ffffffffff",drResult);
        Log.i("DrLesionJson",DrLesion);


        return src_image;
    }


    private int fiveclass(final String path) {


        final String myurl = myURL + "v1/5-class";
        File file = new File(path);//imgUrl为图片位置
        Log.i("ppppppath",String.valueOf(path));

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        Log.i("fileBody",fileBody.toString());
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", path, fileBody)
                //.addFormDataPart("imagetype", imageType)
                .build();

        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(myurl)
                .post(requestBody)
                .build();

        //                                            hui
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure (Call call, IOException e){
                Message m = new Message();
                Bundle b = new Bundle();
                b.putString("msg", "失败");
                m.setData(b);
                Callfail = true;
                Toasthandler.sendMessage(m);
                Log.i("ddd", e.toString());
            }

            @Override
            public void onResponse (Call call, Response response) throws IOException {
                try {

                    String responseData = response.body().string();
                    Message m = new Message();
                    Bundle b = new Bundle();
                    b.putString("msg", responseData);
                    m.setData(b);

                    Mat image = Imgcodecs.imread(path);
                    BBOX5(image, responseData);

                    //Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2RGB);
                    //image = BBOX5(image, responseData);
                    //Bitmap bitmap = Bitmap.createBitmap(image.cols()/2, image.rows()/2, Bitmap.Config.RGB_565);
                    //Utils.matToBitmap(image, bitmap);

                    //Toasthandler.sendMessage(m);
                } catch (Exception e) {

                }

            }
        });
        return 0;
    }

    private int twoclass(final String path) {


        final String myurl = myURL + "v1/2-class";
        File file = new File(path);//imgUrl为图片位置
        Log.i("ppppppath",String.valueOf(path));
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        Log.i("fileBody",fileBody.toString());
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", path, fileBody)
                //.addFormDataPart("imagetype", imageType)
                .build();

        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(myurl)
                .post(requestBody)
                .build();

        //                                            hui
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure (Call call, IOException e){
                Message m = new Message();
                Bundle b = new Bundle();
                b.putString("msg", "失败");
                m.setData(b);
                Toasthandler.sendMessage(m);
                Log.i("ddd", e.toString());
            }

            @Override
            public void onResponse (Call call, Response response) throws IOException {
                try {

                    String responseData = response.body().string();
                    Message m = new Message();
                    Bundle b = new Bundle();
                    b.putString("msg", responseData);
                    m.setData(b);

                    Mat image = Imgcodecs.imread(path);
                    BBOX2(image, responseData);

//                    Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2RGB);
//                    image = BBOX2(image, responseData);
//                    Bitmap bitmap = Bitmap.createBitmap(image.cols()/2, image.rows()/2, Bitmap.Config.RGB_565);
//                    Utils.matToBitmap(image, bitmap);

                    //Toasthandler.sendMessage(m);
                } catch (Exception e) {

                }

            }
        });
        return 0;
    }

    private void drlesion(final String path) {


        final String myurl = myURL + "v1/dr_lesion_inference";
        File file = new File(path);//imgUrl为图片位置
        Log.i("ppppppath",String.valueOf(path));
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        Log.i("fileBody",fileBody.toString());
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", path, fileBody)
                //.addFormDataPart("imagetype", imageType)
                .build();

        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(myurl)
                .post(requestBody)
                .build();

        //                                            hui
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure (Call call, IOException e){
                Message m = new Message();
                Bundle b = new Bundle();
                b.putString("msg", "失败");
                m.setData(b);
                Toasthandler.sendMessage(m);
                Log.i("ddd", e.toString());
            }

            @Override
            public void onResponse (Call call, Response response) throws IOException {
                try {

                    String responseData = response.body().string();
                    Message m = new Message();
                    Bundle b = new Bundle();
                    b.putString("msg", responseData);
                    m.setData(b);

                    Mat image = Imgcodecs.imread(path);
                    BBOXdr(image, responseData);

//                    Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2RGB);
//                    image = BBOXdr(image, responseData);
//                    Bitmap bitmap = Bitmap.createBitmap(image.cols()/2, image.rows()/2, Bitmap.Config.RGB_565);
//                    Utils.matToBitmap(image, bitmap);

                    //Toasthandler.sendMessage(m);
                } catch (Exception e) {

                }

            }
        });
    }

    public void onBackPressed() {

        String pid = getIntent().getExtras().getString("b");

        message=editText.getText().toString();
        message2=editText2.getText().toString();
        message3=editText3.getText().toString();
        message4=editText4.getText().toString();
        message5=editText5.getText().toString();
        message6=editText6.getText().toString();
        message7=editText7.getText().toString();
        message8=editText8.getText().toString();
        message9=editText9.getText().toString();
        message10=editText10.getText().toString();


        SQLiteDatabase idDB = myDB.getReadableDatabase();
        Cursor cursor = idDB.query("users_data", null, "PATIENTID=?", new String[]{pid},null,null,null);
        cursor.moveToFirst();
        String AIimg1path = cursor.getString(cursor.getColumnIndex("AIIMG1"));


        if(message.length() == 0 && message2.length() == 0 && message3.length() == 0 && message4.length() == 0 && message5.length() == 0 &&
                message6.length() == 0 && message7.length() == 0 && message8.length() == 0 && message9.length() == 0 && message10.length() == 0 && AIimg1path.equals(" ")){
            finish();
        } else {

            AlertDialog.Builder a_builder = new AlertDialog.Builder(Viewwait_viewimage.this);
            a_builder.setMessage(R.string.Back_message)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String pid = getIntent().getExtras().getString("b");

                            SQLiteDatabase idDB = myDB.getReadableDatabase();
                            ContentValues values = new ContentValues();

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
                            idDB.update("users_data",values, "PATIENTID=?", new String[]{pid.toString()});
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle(R.string.Back_title);

            alert.show();
        }
    }


    public void openDialog() {
        DiaLogDIB diaLogDIB = new DiaLogDIB();
        diaLogDIB.show(getSupportFragmentManager(), "example dialog");
    }

    public void DimMemoryDialog() {
        DialogMemory memoryDialog = new DialogMemory();
        memoryDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void DiaLogTryagin() {
        DiaLogTryagin diaLogTryagin = new DiaLogTryagin();
        diaLogTryagin.show(getSupportFragmentManager(), "example dialog");
    }

    private  void closekeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    public String Ping(String str) {
        String resault = "";
        Process p;
        try {
            //ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
            p = Runtime.getRuntime().exec("ping -c 1 -w 2 " + str);
            int status = p.waitFor();
            System.out.println("dddddddddddd" + status);

            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            System.out.println("Return ============" + buffer.toString());

            if (status == 0) {
                resault = "success";
            } else {
                resault = "faild";
            }
            System.out.println("dddddddddddd" + resault);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return resault;
    }


    public String DIB_SSID() {
        String resault = "";
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        final String DIB_ssid = wifiInfo.getSSID();

        try {

            System.out.println("SSSSSSSSSSSSSSSSid : " + ssid);

            if (mWifiManager == null) {
                mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            }

            wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
            scannedResult = new ArrayList();



            //开启wifi
            MyWifiManager.openWifi(mWifiManager);
            //获取到wifi列表
            mScanResultList = MyWifiManager.getWifiList(mWifiManager);

            for (int i = 0; i < mScanResultList.size(); i++) {
                scannedResult.add( mScanResultList.get(i).SSID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (DIB_ssid.equals("\"" + AS_DIBSSID + "\"")) {
            resault = "success";
        } else {
            resault = "faild";
        }

        System.out.println("dddddddddddd" + resault);

        return resault;
    }


}