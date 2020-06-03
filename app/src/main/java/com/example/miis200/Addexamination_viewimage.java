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
import android.graphics.Path;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
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
import com.example.miis200.utils.PermissionsChecker;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;


public class Addexamination_viewimage extends BaseActivity {

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
    private String twoMemory;

    private String IP300;

    private Boolean Callfail = false;

    private int AIimgSizeW,AIimgSizeH;


    private void aiWifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(AS_DIBSSID, AS_DIBSSIDPWD);
    }


    private void HUBWifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(AP_HUBSSID, AP_HUBSSIDPWD);
    }

    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private final int RESULT_CODE_LOCATION = 0x001;
    //定位权限,获取app内常用权限
    String[] permsLocation = {"android.permission.ACCESS_WIFI_STATE"
            , "android.permission.CHANGE_WIFI_STATE"
            , "android.permission.ACCESS_COARSE_LOCATION"
            , "android.permission.ACCESS_FINE_LOCATION"};



    private WifiManager mWifiManager;
    private List<ScanResult> mScanResultList;//wifi列表
    private List<WifiListBean> wifiListBeanList;

    ProgressDialog DIBdialog;

    Button cancelViewimg;
    String message = " ", message2 = " ", message3 = " ", message4 = " ", message5 = " ", message6 = " ", message7 = " ", message8 = " ", message9 = " ", message10 = " ";


    private String printName = "MiiS-OA";
    private String printPwd = "vacationoa";
    private String xxx = "[]";

    int rtn = 1;
    int rtn2 = 2;


    DatabaseHelper myDB;
    ArrayList<User> userList;
    ListView listView;
    User user;

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

    public String FiveClass,TwoClass,DrLesion="111";


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
            Toast.makeText(Addexamination_viewimage.this, newsTemp, Toast.LENGTH_LONG).show();
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

    public static final String TAG = "Addexamination_viewimage";
    String Leyepath,Reyepath,eyepath1,eyepath2,eyepath3,eyepath4,eyepath5,eyepath6,eyepath7,eyepath8;
    String ViewPhotoImg1,ViewPhotoImg2,ViewPhotoImg3,ViewPhotoImg4,ViewPhotoImg5,ViewPhotoImg6,ViewPhotoImg7,ViewPhotoImg8,ViewPhotoImg9,ViewPhotoImg10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

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



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexamination_viewimage);
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



        ViewPhotoImg1 = getIntent().getExtras().getString("PhotoImg1");
        ViewPhotoImg2 = getIntent().getExtras().getString("PhotoImg2");
        ViewPhotoImg3 = getIntent().getExtras().getString("PhotoImg3");
        ViewPhotoImg4 = getIntent().getExtras().getString("PhotoImg4");
        ViewPhotoImg5 = getIntent().getExtras().getString("PhotoImg5");
        ViewPhotoImg6 = getIntent().getExtras().getString("PhotoImg6");
        ViewPhotoImg7 = getIntent().getExtras().getString("PhotoImg7");
        ViewPhotoImg8 = getIntent().getExtras().getString("PhotoImg8");
        ViewPhotoImg9 = getIntent().getExtras().getString("PhotoImg9");
        ViewPhotoImg10 = getIntent().getExtras().getString("PhotoImg10");

        cancelViewimg = (Button)findViewById(R.id.cancelViewimg);
        cancelViewimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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


                System.out.println("ssssssssssssssssssddddd : " + message.length());
                System.out.println("ssssssssssssssssssddddd : " + message2.length());

                final String pid = getIntent().getExtras().getString("b");
                SQLiteDatabase idDB = myDB.getReadableDatabase();
                Cursor cursor = idDB.query("users_data", null, "PATIENTID=?", new String[]{pid},null,null,null);
                cursor.moveToFirst();
                String AIimg1path = cursor.getString(cursor.getColumnIndex("AIIMG1"));

                if(message.length() == 0 && message2.length() == 0 && message3.length() == 0 && message4.length() == 0 && message5.length() == 0 &&
                        message6.length() == 0 && message7.length() == 0 && message8.length() == 0 && message9.length() == 0 && message10.length() == 0 && AIimg1path.equals(" ")) {
                    finish();
                } else {
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_viewimage.this);
                    a_builder.setMessage(R.string.Cancel_message)
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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


        if (ActivityCompat.checkSelfPermission(Addexamination_viewimage.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Addexamination_viewimage.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        File fileport = Environment.getExternalStorageDirectory();
        File imagefile = new File(fileport,"1.MiiS/2.IMAGE");

        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDd : " + getIntent().getStringArrayListExtra("list"));

        if ( getIntent().getStringArrayListExtra("list") != null) {
            ArrayList<String> imageList = (ArrayList<String>) getIntent().getStringArrayListExtra("list");
            for (int i = 0; i < imageList.size(); i++) {
                Log.d("RECDATA_viewimage", imageList.get(i));
                if (i == 0) {
                    if(imageList.get(0).indexOf("/") == 0){
                        Leyepath = imageList.get(0);
                    } else {
                        Leyepath = imagefile + "/" + imageList.get(0);
                    }
                    System.out.println("iiiiiiiiimage1 : " + Leyepath);
                    System.out.println("iiiiiiiiimage1 : " + imageList.get(0).length());
                    readLeye.setVisibility(View.VISIBLE);
                    readReye.setVisibility(View.GONE);
                    readeye1.setVisibility(View.GONE);
                    readeye2.setVisibility(View.GONE);
                    readeye3.setVisibility(View.GONE);
                    readeye4.setVisibility(View.GONE);
                    readeye5.setVisibility(View.GONE);
                    readeye6.setVisibility(View.GONE);
                    readeye7.setVisibility(View.GONE);
                    readeye8.setVisibility(View.GONE);

                    path = Leyepath;

                    readLeye.setImageURI(Uri.parse(Leyepath));
                    int width1 = readLeye.getDrawable().getIntrinsicWidth();
                    int heigh1 = readLeye.getDrawable().getIntrinsicHeight();
                    Picasso.get().load("file://"+Leyepath).resize(width1/3,heigh1/3).into(readLeye);

                } else if (i == 1) {
                    if(imageList.get(1).indexOf("/") == 0){
                        Reyepath = imageList.get(1);
                    } else {
                        Reyepath = imagefile + "/" + imageList.get(1);
                    }



                    System.out.println("iiiiiiiiimage2 : " + Reyepath);
                    System.out.println("iiiiiiiiimage2 : " + imageList.get(1).length());
                    readLeye.setVisibility(View.VISIBLE);
                    readReye.setVisibility(View.VISIBLE);
                    readeye1.setVisibility(View.GONE);
                    readeye2.setVisibility(View.GONE);
                    readeye3.setVisibility(View.GONE);
                    readeye4.setVisibility(View.GONE);
                    readeye5.setVisibility(View.GONE);
                    readeye6.setVisibility(View.GONE);
                    readeye7.setVisibility(View.GONE);
                    readeye8.setVisibility(View.GONE);
                    path = Reyepath;

                    readReye.setImageURI(Uri.parse(Reyepath));
                    int width1 = readReye.getDrawable().getIntrinsicWidth();
                    int heigh1 = readReye.getDrawable().getIntrinsicHeight();
                    Picasso.get().load("file://"+Reyepath).resize(width1/3,heigh1/3).into(readReye);

                } else if (i == 2) {
                    if(imageList.get(2).indexOf("/") == 0){
                        eyepath1 = imageList.get(2);
                    } else {
                        eyepath1 = imagefile + "/" + imageList.get(2);
                    }


                    readLeye.setVisibility(View.VISIBLE);
                    readReye.setVisibility(View.VISIBLE);
                    readeye1.setVisibility(View.VISIBLE);
                    readeye2.setVisibility(View.GONE);
                    readeye3.setVisibility(View.GONE);
                    readeye4.setVisibility(View.GONE);
                    readeye5.setVisibility(View.GONE);
                    readeye6.setVisibility(View.GONE);
                    readeye7.setVisibility(View.GONE);
                    readeye8.setVisibility(View.GONE);
                    path = eyepath1;

                    readeye1.setImageURI(Uri.parse(eyepath1));
                    int width1 = readeye1.getDrawable().getIntrinsicWidth();
                    int heigh1 = readeye1.getDrawable().getIntrinsicHeight();
                    Picasso.get().load("file://"+eyepath1).resize(width1/3,heigh1/3).into(readeye1);


                } else if (i == 3) {
                    if(imageList.get(3).indexOf("/") == 0){
                        eyepath2 = imageList.get(3);
                    } else {
                        eyepath2 = imagefile + "/" + imageList.get(3);
                    }


                    readLeye.setVisibility(View.VISIBLE);
                    readReye.setVisibility(View.VISIBLE);
                    readeye1.setVisibility(View.VISIBLE);
                    readeye2.setVisibility(View.VISIBLE);
                    readeye3.setVisibility(View.GONE);
                    readeye4.setVisibility(View.GONE);
                    readeye5.setVisibility(View.GONE);
                    readeye6.setVisibility(View.GONE);
                    readeye7.setVisibility(View.GONE);
                    readeye8.setVisibility(View.GONE);
                    path = eyepath2;

                    readeye2.setImageURI(Uri.parse(eyepath2));
                    int width1 = readeye2.getDrawable().getIntrinsicWidth();
                    int heigh1 = readeye2.getDrawable().getIntrinsicHeight();
                    Picasso.get().load("file://"+eyepath2).resize(width1/3,heigh1/3).into(readeye2);


                } else if (i == 4) {
                    if(imageList.get(4).indexOf("/") == 0){
                        eyepath3 = imageList.get(4);
                    } else {
                        eyepath3 = imagefile + "/" + imageList.get(4);
                    }


                    readLeye.setVisibility(View.VISIBLE);
                    readReye.setVisibility(View.VISIBLE);
                    readeye1.setVisibility(View.VISIBLE);
                    readeye2.setVisibility(View.VISIBLE);
                    readeye3.setVisibility(View.VISIBLE);
                    readeye4.setVisibility(View.GONE);
                    readeye5.setVisibility(View.GONE);
                    readeye6.setVisibility(View.GONE);
                    readeye7.setVisibility(View.GONE);
                    readeye8.setVisibility(View.GONE);
                    path = eyepath3;

                    readeye3.setImageURI(Uri.parse(eyepath3));
                    int width1 = readeye3.getDrawable().getIntrinsicWidth();
                    int heigh1 = readeye3.getDrawable().getIntrinsicHeight();
                    Picasso.get().load("file://"+eyepath3).resize(width1/3,heigh1/3).into(readeye3);


                } else if (i == 5) {
                    if(imageList.get(5).indexOf("/") == 0){
                        eyepath4 = imageList.get(5);
                    } else {
                        eyepath4 = imagefile + "/" + imageList.get(5);
                    }

                    readLeye.setVisibility(View.VISIBLE);
                    readReye.setVisibility(View.VISIBLE);
                    readeye1.setVisibility(View.VISIBLE);
                    readeye2.setVisibility(View.VISIBLE);
                    readeye3.setVisibility(View.VISIBLE);
                    readeye4.setVisibility(View.VISIBLE);
                    readeye5.setVisibility(View.GONE);
                    readeye6.setVisibility(View.GONE);
                    readeye7.setVisibility(View.GONE);
                    readeye8.setVisibility(View.GONE);
                    path = eyepath4;
                    readeye4.setImageURI(Uri.parse(eyepath4));
                    int width1 = readeye4.getDrawable().getIntrinsicWidth();
                    int heigh1 = readeye4.getDrawable().getIntrinsicHeight();
                    Picasso.get().load("file://"+eyepath4).resize(width1/3,heigh1/3).into(readeye4);

                } else if (i == 6) {
                    if(imageList.get(6).indexOf("/") == 0){
                        eyepath5 = imageList.get(6);
                    } else {
                        eyepath5 = imagefile + "/" + imageList.get(6);
                    }


                    readLeye.setVisibility(View.VISIBLE);
                    readReye.setVisibility(View.VISIBLE);
                    readeye1.setVisibility(View.VISIBLE);
                    readeye2.setVisibility(View.VISIBLE);
                    readeye3.setVisibility(View.VISIBLE);
                    readeye4.setVisibility(View.VISIBLE);
                    readeye5.setVisibility(View.VISIBLE);
                    readeye6.setVisibility(View.GONE);
                    readeye7.setVisibility(View.GONE);
                    readeye8.setVisibility(View.GONE);
                    path = eyepath5;

                    readeye5.setImageURI(Uri.parse(eyepath5));
                    int width1 = readeye5.getDrawable().getIntrinsicWidth();
                    int heigh1 = readeye5.getDrawable().getIntrinsicHeight();
                    Picasso.get().load("file://"+eyepath5).resize(width1/3,heigh1/3).into(readeye5);

                } else if (i == 7) {
                    if(imageList.get(7).indexOf("/") == 0){
                        eyepath6 = imageList.get(7);
                    } else {
                        eyepath6 = imagefile + "/" + imageList.get(7);
                    }


                    readLeye.setVisibility(View.VISIBLE);
                    readReye.setVisibility(View.VISIBLE);
                    readeye1.setVisibility(View.VISIBLE);
                    readeye2.setVisibility(View.VISIBLE);
                    readeye3.setVisibility(View.VISIBLE);
                    readeye4.setVisibility(View.VISIBLE);
                    readeye5.setVisibility(View.VISIBLE);
                    readeye6.setVisibility(View.VISIBLE);
                    readeye7.setVisibility(View.GONE);
                    readeye8.setVisibility(View.GONE);
                    path = eyepath6;

                    readeye6.setImageURI(Uri.parse(eyepath6));
                    int width1 = readeye6.getDrawable().getIntrinsicWidth();
                    int heigh1 = readeye6.getDrawable().getIntrinsicHeight();
                    Picasso.get().load("file://"+eyepath6).resize(width1/3,heigh1/3).into(readeye6);

                } else if (i == 8) {
                    if(imageList.get(8).indexOf("/") == 0){
                        eyepath7 = imageList.get(8);
                    } else {
                        eyepath7 = imagefile + "/" + imageList.get(8);
                    }

                    readLeye.setVisibility(View.VISIBLE);
                    readReye.setVisibility(View.VISIBLE);
                    readeye1.setVisibility(View.VISIBLE);
                    readeye2.setVisibility(View.VISIBLE);
                    readeye3.setVisibility(View.VISIBLE);
                    readeye4.setVisibility(View.VISIBLE);
                    readeye5.setVisibility(View.VISIBLE);
                    readeye6.setVisibility(View.VISIBLE);
                    readeye7.setVisibility(View.VISIBLE);
                    readeye8.setVisibility(View.GONE);
                    path = eyepath7;

                    readeye7.setImageURI(Uri.parse(eyepath7));
                    int width1 = readeye7.getDrawable().getIntrinsicWidth();
                    int heigh1 = readeye7.getDrawable().getIntrinsicHeight();
                    Picasso.get().load("file://"+eyepath7).resize(width1/3,heigh1/3).into(readeye7);

                } else if (i == 9) {
                    if(imageList.get(9).indexOf("/") == 0){
                        eyepath8 = imageList.get(9);
                    } else {
                        eyepath8 = imagefile + "/" + imageList.get(9);
                    }

                    readLeye.setVisibility(View.VISIBLE);
                    readReye.setVisibility(View.VISIBLE);
                    readeye1.setVisibility(View.VISIBLE);
                    readeye2.setVisibility(View.VISIBLE);
                    readeye3.setVisibility(View.VISIBLE);
                    readeye4.setVisibility(View.VISIBLE);
                    readeye5.setVisibility(View.VISIBLE);
                    readeye6.setVisibility(View.VISIBLE);
                    readeye7.setVisibility(View.VISIBLE);
                    readeye8.setVisibility(View.VISIBLE);
                    path = eyepath8;

                    readeye8.setImageURI(Uri.parse(eyepath8));
                    int width1 = readeye8.getDrawable().getIntrinsicWidth();
                    int heigh1 = readeye8.getDrawable().getIntrinsicHeight();
                    Picasso.get().load("file://"+eyepath8).resize(width1/3,heigh1/3).into(readeye8);

                }
            }
        }

        Button newReport = (Button)findViewById(R.id.Viewprinter);
        newReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Button_Click.isFastDoubleClick()) {
                    aiWifi();



                    Intent intent = new Intent(Addexamination_viewimage.this, Addexamination_chooseimg_Report_viewfinish.class);

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


                    final String Lepath = Leyepath;
                    final String Repath = Reyepath;
                    final String eypath1 = eyepath1;
                    final String eypath2 = eyepath2;
                    final String eypath3 = eyepath3;
                    final String eypath4 = eyepath4;
                    final String eypath5 = eyepath5;
                    final String eypath6 = eyepath6;
                    final String eypath7 = eyepath7;
                    final String eypath8 = eyepath8;

                    final String page = getIntent().getExtras().getString("age");





                    Log.i("IIIIIIIInnnnnntttttt",pname);
                    Log.i("IIIIIIIInnnnnntttttt",pid);
                    Log.i("IIIIIIIInnnnnntttttt",pbir);
                    Log.i("IIIIIIIInnnnnntttttt",pgender);
                    Log.i("IIIIIIIInnnnnntttttt",pphone);
                    Log.i("IIIIIIIInnnnnntttttt",checkTime);
                    intent.putExtra("a",pname);
                    intent.putExtra("b",pid);
                    intent.putExtra("e",pphone);


                    intent.putExtra("Leyepath",Lepath);
                    intent.putExtra("Reyepath",Repath);
                    intent.putExtra("eyepath1",eypath1);
                    intent.putExtra("eyepath2",eypath2);
                    intent.putExtra("eyepath3",eypath3);
                    intent.putExtra("eyepath4",eypath4);
                    intent.putExtra("eyepath5",eypath5);
                    intent.putExtra("eyepath6",eypath6);
                    intent.putExtra("eyepath7",eypath7);
                    intent.putExtra("eyepath8",eypath8);

                    intent.putExtra("ViewPhotoImg1",ViewPhotoImg1);
                    intent.putExtra("ViewPhotoImg2",ViewPhotoImg2);
                    intent.putExtra("ViewPhotoImg3",ViewPhotoImg3);
                    intent.putExtra("ViewPhotoImg4",ViewPhotoImg4);
                    intent.putExtra("ViewPhotoImg5",ViewPhotoImg5);
                    intent.putExtra("ViewPhotoImg6",ViewPhotoImg6);
                    intent.putExtra("ViewPhotoImg7",ViewPhotoImg7);
                    intent.putExtra("ViewPhotoImg8",ViewPhotoImg8);
                    intent.putExtra("ViewPhotoImg9",ViewPhotoImg9);
                    intent.putExtra("ViewPhotoImg10",ViewPhotoImg10);


                    SQLiteDatabase idDB = myDB.getReadableDatabase();
                    ContentValues values = new ContentValues();
                    if(Leyepath != null){
                        values.put("LEFTIMAGEPATH",Leyepath);
                        values.put("RIGHTIMAGEPATH",Reyepath);
                        values.put("Eyepath1",eyepath1);
                        values.put("Eyepath2",eyepath2);
                        values.put("Eyepath3",eyepath3);
                        values.put("Eyepath4",eyepath4);
                        values.put("Eyepath5",eyepath5);
                        values.put("Eyepath6",eyepath6);
                        values.put("Eyepath7",eyepath7);
                        values.put("Eyepath8",eyepath8);
                    } else {
                        values.put("LEFTIMAGEPATH",ViewPhotoImg1);
                        values.put("RIGHTIMAGEPATH",ViewPhotoImg2);
                        values.put("Eyepath1",ViewPhotoImg3);
                        values.put("Eyepath2",ViewPhotoImg4);
                        values.put("Eyepath3",ViewPhotoImg5);
                        values.put("Eyepath4",ViewPhotoImg6);
                        values.put("Eyepath5",ViewPhotoImg7);
                        values.put("Eyepath6",ViewPhotoImg8);
                        values.put("Eyepath7",ViewPhotoImg9);
                        values.put("Eyepath8",ViewPhotoImg10);
                    }

                    values.put("EXTIME",checkTime);
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
                    Log.i("IIIIIIIInnnnnntttttt",pid);

                    startActivity(intent);
                }
            }
        });

        Button newfinish = (Button)findViewById(R.id.Viewfinish);
        newfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_viewimage.this);
                a_builder.setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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

                                Intent intent = new Intent(Addexamination_viewimage.this, Option.class);
                                final String pid = getIntent().getExtras().getString("b");
                                final String checkTime = getIntent().getExtras().getString("f");


                                SQLiteDatabase idDB = myDB.getReadableDatabase();
                                ContentValues values = new ContentValues();
                                values.put("STATUS","2");

                                values.put("LEFTIMAGEPATH",Leyepath);
                                values.put("RIGHTIMAGEPATH",Reyepath);
                                values.put("Eyepath1",eyepath1);
                                values.put("Eyepath2",eyepath2);
                                values.put("Eyepath3",eyepath3);
                                values.put("Eyepath4",eyepath4);
                                values.put("Eyepath5",eyepath5);
                                values.put("Eyepath6",eyepath6);
                                values.put("Eyepath7",eyepath7);
                                values.put("Eyepath8",eyepath8);

                                values.put("EXTIME",checkTime);
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
                                Log.i("IIIIIIIInnnnnntttttt",pid);

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

                message=editText.getText().toString();


                if(message.length() == 0) {
                    Intent intent = new Intent(getApplicationContext(), Option.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    final String pid = getIntent().getExtras().getString("b");

                    AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_viewimage.this);
                    a_builder.setMessage(R.string.Go_home_message)
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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

                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //===============================================================================================
    public void mess(View v)
    {
        Intent intent = this.getIntent();
        //initialize image view object
        PhotoView im=(PhotoView)findViewById(R.id.readEye);
        //get clicked button id from view object
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        String LPath=Leyepath;
        String RPath=Reyepath;
        String eyeP1=eyepath1;
        String eyeP2=eyepath2;
        String eyeP3=eyepath3;
        String eyeP4=eyepath4;
        String eyeP5=eyepath5;
        String eyeP6=eyepath6;
        String eyeP7=eyepath7;
        String eyeP8=eyepath8;

        final String ViewPhotoImg1 = getIntent().getExtras().getString("PhotoImg1");
        final String ViewPhotoImg2 = getIntent().getExtras().getString("PhotoImg2");
        final String ViewPhotoImg3 = getIntent().getExtras().getString("PhotoImg3");
        final String ViewPhotoImg4 = getIntent().getExtras().getString("PhotoImg4");
        final String ViewPhotoImg5 = getIntent().getExtras().getString("PhotoImg5");
        final String ViewPhotoImg6 = getIntent().getExtras().getString("PhotoImg6");
        final String ViewPhotoImg7 = getIntent().getExtras().getString("PhotoImg7");
        final String ViewPhotoImg8 = getIntent().getExtras().getString("PhotoImg8");
        final String ViewPhotoImg9 = getIntent().getExtras().getString("PhotoImg9");
        final String ViewPhotoImg10 = getIntent().getExtras().getString("PhotoImg10");


if(LPath != null){
    im=(PhotoView)findViewById(R.id.readEye);
        switch(v.getId()) {
            case R.id.readLeye:
                closekeyboard();
                Log.i("DEC200Img1", LPath);
                Log.i("DEC200Img1", "PPPPPPPPPPPP");
                //if button1 is clicked than set image1
                Bitmap bmL = BitmapFactory.decodeFile(LPath, options);
                im.setImageBitmap(bmL);
                path = LPath;
                AIimgSizeW = im.getWidth();
                AIimgSizeH = im.getHeight();
                Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));

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
                Log.i("DEC200Img1", RPath);
                //if button2 is clicked than set image2
                Bitmap bmR = BitmapFactory.decodeFile(RPath, options);
                im.setImageBitmap(bmR);
                path = RPath;
                AIimgSizeW = im.getWidth();
                AIimgSizeH = im.getHeight();
                Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));

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
                Log.i("DEC200Img1", eyeP1);
                //if button2 is clicked than set image2
                Bitmap bm1 = BitmapFactory.decodeFile(eyeP1, options);
                im.setImageBitmap(bm1);
                path = eyeP1;
                AIimgSizeW = im.getWidth();
                AIimgSizeH = im.getHeight();
                Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
                Log.i("DEC200Img1", eyeP2);
                //if button2 is clicked than set image2
                Bitmap bm2 = BitmapFactory.decodeFile(eyeP2, options);
                im.setImageBitmap(bm2);
                path = eyeP2;
                AIimgSizeW = im.getWidth();
                AIimgSizeH = im.getHeight();
                Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
                Log.i("DEC200Img1", eyeP3);
                //if button2 is clicked than set image2
                Bitmap bm3 = BitmapFactory.decodeFile(eyeP3, options);
                im.setImageBitmap(bm3);
                path = eyeP3;
                AIimgSizeW = im.getWidth();
                AIimgSizeH = im.getHeight();
                Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
                Log.i("DEC200Img1", eyeP4);
                //if button2 is clicked than set image2
                Bitmap bm4 = BitmapFactory.decodeFile(eyeP4, options);
                im.setImageBitmap(bm4);
                path = eyeP4;
                AIimgSizeW = im.getWidth();
                AIimgSizeH = im.getHeight();
                Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
                Log.i("DEC200Img1", eyeP5);
                //if button2 is clicked than set image2
                Bitmap bm5 = BitmapFactory.decodeFile(eyeP5, options);
                im.setImageBitmap(bm5);
                path = eyeP5;
                AIimgSizeW = im.getWidth();
                AIimgSizeH = im.getHeight();
                Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
                Log.i("DEC200Img1", eyeP6);
                //if button2 is clicked than set image2
                Bitmap bm6 = BitmapFactory.decodeFile(eyeP6, options);
                im.setImageBitmap(bm6);
                path = eyeP6;
                AIimgSizeW = im.getWidth();
                AIimgSizeH = im.getHeight();
                Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
                Log.i("DEC200Img1", eyeP7);
                //if button2 is clicked than set image2
                Bitmap bm7 = BitmapFactory.decodeFile(eyeP7, options);
                im.setImageBitmap(bm7);
                path = eyeP7;
                AIimgSizeW = im.getWidth();
                AIimgSizeH = im.getHeight();
                Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
                Log.i("DEC200Img1", eyeP8);
                //if button2 is clicked than set image2
                Bitmap bm8 = BitmapFactory.decodeFile(eyeP8, options);
                im.setImageBitmap(bm8);
                path = eyeP8;
                AIimgSizeW = im.getWidth();
                AIimgSizeH = im.getHeight();
                Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
        }else{
    switch(v.getId()) {
        case R.id.readLeye:
            closekeyboard();
            Log.i("DEC200Img1", ViewPhotoImg1);
            Log.i("DEC200Img1", "LLLLLLLLLLLL");
            //if button1 is clicked than set image1
            Bitmap bmL = BitmapFactory.decodeFile(ViewPhotoImg1, options);
            im.setImageBitmap(bmL);
            path = ViewPhotoImg1;
            AIimgSizeW = im.getWidth();
            AIimgSizeH = im.getHeight();
            Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
            Log.i("DEC200Img1", ViewPhotoImg2);
            //if button2 is clicked than set image2
            Bitmap bmR = BitmapFactory.decodeFile(ViewPhotoImg2, options);
            im.setImageBitmap(bmR);
            path = ViewPhotoImg2;
            AIimgSizeW = im.getWidth();
            AIimgSizeH = im.getHeight();
            Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
            Log.i("DEC200Img1", ViewPhotoImg3);
            //if button2 is clicked than set image2
            Bitmap bm1 = BitmapFactory.decodeFile(ViewPhotoImg3, options);
            im.setImageBitmap(bm1);
            path = ViewPhotoImg3;
            AIimgSizeW = im.getWidth();
            AIimgSizeH = im.getHeight();
            Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
            Log.i("DEC200Img1", ViewPhotoImg4);
            //if button2 is clicked than set image2
            Bitmap bm2 = BitmapFactory.decodeFile(ViewPhotoImg4, options);
            im.setImageBitmap(bm2);
            path = ViewPhotoImg4;
            AIimgSizeW = im.getWidth();
            AIimgSizeH = im.getHeight();
            Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
            Log.i("DEC200Img1", ViewPhotoImg5);
            //if button2 is clicked than set image2
            Bitmap bm3 = BitmapFactory.decodeFile(ViewPhotoImg5, options);
            im.setImageBitmap(bm3);
            path = ViewPhotoImg5;
            AIimgSizeW = im.getWidth();
            AIimgSizeH = im.getHeight();
            Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
            Log.i("DEC200Img1", ViewPhotoImg6);
            //if button2 is clicked than set image2
            Bitmap bm4 = BitmapFactory.decodeFile(ViewPhotoImg6, options);
            im.setImageBitmap(bm4);
            path = ViewPhotoImg6;
            AIimgSizeW = im.getWidth();
            AIimgSizeH = im.getHeight();
            Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
            Log.i("DEC200Img1", ViewPhotoImg7);
            //if button2 is clicked than set image2
            Bitmap bm5 = BitmapFactory.decodeFile(ViewPhotoImg7, options);
            im.setImageBitmap(bm5);
            path = ViewPhotoImg7;
            AIimgSizeW = im.getWidth();
            AIimgSizeH = im.getHeight();
            Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
            Log.i("DEC200Img1", ViewPhotoImg8);
            //if button2 is clicked than set image2
            Bitmap bm6 = BitmapFactory.decodeFile(ViewPhotoImg8, options);
            im.setImageBitmap(bm6);
            path = ViewPhotoImg8;
            AIimgSizeW = im.getWidth();
            AIimgSizeH = im.getHeight();
            Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
            Log.i("DEC200Img1", ViewPhotoImg9);
            //if button2 is clicked than set image2
            Bitmap bm7 = BitmapFactory.decodeFile(ViewPhotoImg9, options);
            im.setImageBitmap(bm7);
            path = ViewPhotoImg9;
            AIimgSizeW = im.getWidth();
            AIimgSizeH = im.getHeight();
            Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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
            Log.i("DEC200Img1", ViewPhotoImg10);
            //if button2 is clicked than set image2
            Bitmap bm8 = BitmapFactory.decodeFile(ViewPhotoImg10, options);
            im.setImageBitmap(bm8);
            path = ViewPhotoImg10;
            AIimgSizeW = im.getWidth();
            AIimgSizeH = im.getHeight();
            Log.i("kmkmkmkmkmkm",String.valueOf(AIimgSizeW) + "  " + String.valueOf(AIimgSizeH));
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


    //获取权限
    private void getPerMission() {
        mPermissionsChecker = new PermissionsChecker(Addexamination_viewimage.this);
        if (mPermissionsChecker.lacksPermissions(permsLocation)) {
            ActivityCompat.requestPermissions(Addexamination_viewimage.this, permsLocation, RESULT_CODE_LOCATION);
        }
    }


    private void DIBinitView() {
        wifiListBeanList = new ArrayList<>();
        mScanResultList = new ArrayList<>();
    }


    @Override
    public void initView() {
        setContentView(R.layout.activity_addexamination_viewimage);
        AIButton = (ImageButton) findViewById(R.id.AIButton);
        initPermission();

        AIButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {

                if (!Button_Click.isFastDoubleClick()) {
                    //AP MODE Click
                    if( savedRadioIndex == 0){

                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        final String ssid = wifiInfo.getSSID();


                        if (mWifiManager == null) {
                            mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                        }
                        getPerMission();//权限
                        DIBinitView();//控件初始化

                        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
                        scannedResult = new ArrayList();

                        wifiListBeanList.clear();

                        //开启wifi
                        MyWifiManager.openWifi(mWifiManager);
                        //获取到wifi列表
                        mScanResultList = MyWifiManager.getWifiList(mWifiManager);

                        for (int i = 0; i < mScanResultList.size(); i++) {
                            scannedResult.add( mScanResultList.get(i).SSID);
                        }

                        if(scannedResult.indexOf(AP_HUBSSID)<0){
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_viewimage.this);
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
                                        final ProgressDialog dialog= ProgressDialog.show(context1,getResources().getString(R.string.Viewimage_Analyzing), getResources().getString(R.string.Please_wait),true);
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
                                                    intent.setClass(Addexamination_viewimage.this, Addexamination_aianal.class);
                                                    String pname = getIntent().getExtras().getString("a");
                                                    String pid = getIntent().getExtras().getString("b");
                                                    intent.putExtra("a", pname);
                                                    intent.putExtra("b", pid);

                                                    intent.putExtra("five", FiveClass);
                                                    intent.putExtra("two", TwoClass);
                                                    intent.putExtra("dr", DrLesion);
                                                    intent.putExtra("imagepath", path);

                                                    intent.putExtra("AIimgSizeW", AIimgSizeW);
                                                    intent.putExtra("AIimgSizeH", AIimgSizeH);
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
                                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_viewimage.this);
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
                                            intent.setClass(Addexamination_viewimage.this, Addexamination_aianal.class);
                                            String pname = getIntent().getExtras().getString("a");
                                            String pid = getIntent().getExtras().getString("b");
                                            intent.putExtra("a", pname);
                                            intent.putExtra("b", pid);

                                            intent.putExtra("five", FiveClass);
                                            intent.putExtra("two", TwoClass);
                                            intent.putExtra("dr", DrLesion);
                                            intent.putExtra("imagepath", path);

                                            intent.putExtra("AIimgSizeW", AIimgSizeW);
                                            intent.putExtra("AIimgSizeH", AIimgSizeH);
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
                                AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_viewimage.this);
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
                                        intent.setClass(Addexamination_viewimage.this, Addexamination_aianal.class);
                                        String pname = getIntent().getExtras().getString("a");
                                        String pid = getIntent().getExtras().getString("b");
                                        intent.putExtra("a", pname);
                                        intent.putExtra("b", pid);

                                        intent.putExtra("five", FiveClass);
                                        intent.putExtra("two", TwoClass);
                                        intent.putExtra("dr", DrLesion);
                                        intent.putExtra("imagepath", path);

                                        intent.putExtra("AIimgSizeW", AIimgSizeW);
                                        intent.putExtra("AIimgSizeH", AIimgSizeH);
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
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_viewimage.this);
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

                        getPerMission();//权限
                        DIBinitView();//控件初始化

                        scannedResult = new ArrayList();

                        wifiListBeanList.clear();

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

                            final ProgressDialog dialog = ProgressDialog.show(context1, getResources().getString(R.string.Viewimage_Analyzing), getResources().getString(R.string.Please_wait), true);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    while (!DIB_SSID().equals("success")) {
                                        try {
                                            aiWifi();
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
                                        intent.setClass(Addexamination_viewimage.this, Addexamination_aianal.class);
                                        String pname = getIntent().getExtras().getString("a");
                                        String pid = getIntent().getExtras().getString("b");
                                        intent.putExtra("a", pname);
                                        intent.putExtra("b", pid);

                                        intent.putExtra("five", FiveClass);
                                        intent.putExtra("two", TwoClass);
                                        intent.putExtra("dr", DrLesion);
                                        intent.putExtra("imagepath", path);

                                        intent.putExtra("AIimgSizeW", AIimgSizeW);
                                        intent.putExtra("AIimgSizeH", AIimgSizeH);
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
                                        intent.setClass(Addexamination_viewimage.this, Addexamination_aianal.class);
                                        String pname = getIntent().getExtras().getString("a");
                                        String pid = getIntent().getExtras().getString("b");
                                        intent.putExtra("a", pname);
                                        intent.putExtra("b", pid);

                                        intent.putExtra("five", FiveClass);
                                        intent.putExtra("two", TwoClass);
                                        intent.putExtra("dr", DrLesion);
                                        intent.putExtra("imagepath", path);

                                        intent.putExtra("AIimgSizeW", AIimgSizeW);
                                        intent.putExtra("AIimgSizeH", AIimgSizeH);
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
        twoMemory = jsonObject.getString("message");
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

    Object lock = new Object();
    private int fiveclass(final String path) {
        synchronized(lock){

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
                    b.putString("msg", "失敗");
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
                b.putString("msg", "失敗");
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

                    //Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2RGB);
                    //image = BBOX2(image, responseData);
                    //Bitmap bitmap = Bitmap.createBitmap(image.cols()/2, image.rows()/2, Bitmap.Config.RGB_565);
                    //Utils.matToBitmap(image, bitmap);

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
                b.putString("msg", "失敗");
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

                    //Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2RGB);
                    //image = BBOXdr(image, responseData);
                    //Bitmap bitmap = Bitmap.createBitmap(image.cols()/2, image.rows()/2, Bitmap.Config.RGB_565);
                    //Utils.matToBitmap(image, bitmap);

                    //Toasthandler.sendMessage(m);
                } catch (Exception e) {

                }

            }
        });
    }

    public void onBackPressed() {

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


        System.out.println("ssssssssssssssssssddddd : " + message.length());
        System.out.println("ssssssssssssssssssddddd : " + message2.length());

        final String pid = getIntent().getExtras().getString("b");
        SQLiteDatabase idDB = myDB.getReadableDatabase();
        Cursor cursor = idDB.query("users_data", null, "PATIENTID=?", new String[]{pid},null,null,null);
        cursor.moveToFirst();
        String AIimg1path = cursor.getString(cursor.getColumnIndex("AIIMG1"));

        if(message.length() == 0 && message2.length() == 0 && message3.length() == 0 && message4.length() == 0 && message5.length() == 0 &&
                message6.length() == 0 && message7.length() == 0 && message8.length() == 0 && message9.length() == 0 && message10.length() == 0 && AIimg1path.equals(" ")) {
            finish();
        } else {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(Addexamination_viewimage.this);
            a_builder.setMessage(R.string.Back_message)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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

                            idDB.update("users_data",values, "PATIENTID=?", new String[]{pid.toString()});



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
            getPerMission();//权限
            DIBinitView();//控件初始化

            wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
            scannedResult = new ArrayList();

            wifiListBeanList.clear();

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
