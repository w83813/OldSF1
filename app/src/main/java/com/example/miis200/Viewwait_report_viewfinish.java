package com.example.miis200;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.print.PrintAttributes;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miis200.utils.WifiUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Viewwait_report_viewfinish extends AppCompatActivity{


    WifiManager wifiManager;
    List scannedResult;


    private void printWifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(PrinterSSID, PrinterSSIDpwd);
    }


    private void aiWifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(DibSSID, DibSSIDpwd);
    }


    private void DEC200Wifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(DecSSID, DecSSIDpwd);
    }

    private void HubWifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(HubSSID, HubSSIDpwd);
    }

    DatabaseHelper myDB;
    ArrayList<User> userList;
    ListView listView;
    User user;

    final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";
    int savedRadioIndex;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String DECSSID = "DECSSID";
    public static final String DECSSIDPWD = "DECSSIDPWD";
    public static final String DIBSSID = "DIBSSID";
    public static final String DIBSSIDPWD = "DIBSSIDPWD";
    public static final String PRINTERSSID = "PRINTERSSID";
    public static final String PRINTERSSIDPWD = "PRINTERSSIDPWD";
    public static final String HUBSSID = "HUBSSID";
    public static final String HUBSSIDPWD = "HUBSSIDPWD";

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
    private String HubSSID;
    private String HubSSIDpwd;

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



    private String aimWifiName = "MiiS-OA";
    private String aimWifiPwd = "vavationoa";

    private void changeWifi2() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(aimWifiName, aimWifiPwd);

    }

    TextView pname,pid,pbir,pgender,pphone,ctime;

    public static final String TAG = "Viewwait_report_viewfinish";
    private PackageManager mPackageManager;
    private List<ResolveInfo> mAllApps;

    private Context context;
    private Uri uri;
    private String title;
    ImageView VRimg1,VRimg2,wvRP1eyeImg1,wvRP1eyeImg2,wvRP1eyeImg3,wvRP1eyeImg4,wvRP1eyeImg5,wvRP1eyeImg6,wvRP1eyeImg7,wvRP1eyeImg8;

    private ImageView reportIMG1,reportIMG2,reportIMG3,reportIMG4;

    private TextView Desc1,Desc2,Desc3,Desc4;

    private TextView imgnum1,imgnum2,imgnum3,imgnum4;
    private LinearLayout memo1,memo2,memo3,memo4;

    private TextView reportdata;

    private String ImagePath1,ImagePath2,ImagePath3,ImagePath4;


    Bitmap bitmap;

    public static final SimpleDateFormat sdf5 = new SimpleDateFormat("yyyyMMdd");
    Date current = new Date();
    public String today = sdf5.format(current).toString();
    public String folder0 = "/DCIM/"+sdf5.format(current).toString();
    String rootDir = "/DCIM/"+today;
    String directoryName = rootDir;

    public static final SimpleDateFormat time = new SimpleDateFormat("HHmmss");
    public String newtime = time.format(current).toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        myDB = new DatabaseHelper(this);

        userList = new ArrayList<>();
        Cursor data = myDB.getListContents1();

        loadData();
        updateViews();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy年MM月dd日 ");
        Date date = new Date( System.currentTimeMillis() );

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_viewwait_report_viewfinish);

        //===============================================================================================
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Report_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //===============================================================================================

        pname = (TextView) findViewById( R.id.get_name );
        Log.i("WWWWWWWWWWW",String.valueOf(pname));
        pid = (TextView) findViewById( R.id.get_number );
        pbir = (TextView) findViewById( R.id.get_age );
        pgender = (TextView) findViewById( R.id.get_rad );
        pphone = (TextView) findViewById( R.id.phone_num );
        ctime = (TextView) findViewById( R.id.get_time );

        reportdata = (TextView) findViewById(R.id.get_report_data);
        reportdata.setText( simpleDateFormat.format( date )  );

        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        scannedResult = new ArrayList();

        Desc1 = findViewById(R.id.Desc1);
        Desc2 = findViewById(R.id.Desc2);
        Desc3 = findViewById(R.id.Desc3);
        Desc4 = findViewById(R.id.Desc4);

        imgnum1 = (TextView) findViewById(R.id.imgnum1);
        imgnum2 = (TextView) findViewById(R.id.imgnum2);
        imgnum3 = (TextView) findViewById(R.id.imgnum3);
        imgnum4 = (TextView) findViewById(R.id.imgnum4);

        memo1 = (LinearLayout) findViewById(R.id.memo1);
        memo2 = (LinearLayout) findViewById(R.id.memo2);
        memo3 = (LinearLayout) findViewById(R.id.memo3);
        memo4 = (LinearLayout) findViewById(R.id.memo4);



        Intent intent = this.getIntent();
        String pName=intent.getStringExtra("a");
        String pID=intent.getStringExtra("b");
        String pBir=intent.getStringExtra("c");
        String pGender=intent.getStringExtra("d");
        String pNum=intent.getStringExtra("e");
        String chTime=intent.getStringExtra("f");

        String pAge = intent.getStringExtra("age");
        String edittext=intent.getStringExtra("edit");





        pname.setText(pName);
        pid.setText( pID );
        pbir.setText( pAge );
        pgender.setText( pGender );
        pphone.setText( pNum );
        ctime.setText(chTime);


        String reportIMGpath1 = intent.getStringExtra("img1path");
        String reportIMGpath2 = intent.getStringExtra("img2path");
        String reportIMGpath3 = intent.getStringExtra("img3path");
        String reportIMGpath4 = intent.getStringExtra("img4path");
        String reportIMGpath5 = intent.getStringExtra("img5path");
        String reportIMGpath6 = intent.getStringExtra("img6path");
        String reportIMGpath7 = intent.getStringExtra("img7path");
        String reportIMGpath8 = intent.getStringExtra("img8path");
        String reportIMGpath9 = intent.getStringExtra("img9path");
        String reportIMGpath10 = intent.getStringExtra("img10path");
        String reportIMGpath11 = intent.getStringExtra("AIimg1path");
        String reportIMGpath12 = intent.getStringExtra("AIimg2path");
        String reportIMGpath13 = intent.getStringExtra("AIimg3path");
        String reportIMGpath14 = intent.getStringExtra("AIimg4path");
        String reportIMGpath15 = intent.getStringExtra("AIimg5path");
        String reportIMGpath16 = intent.getStringExtra("AIimg6path");
        String reportIMGpath17 = intent.getStringExtra("AIimg7path");
        String reportIMGpath18 = intent.getStringExtra("AIimg8path");
        String reportIMGpath19 = intent.getStringExtra("AIimg9path");
        String reportIMGpath20 = intent.getStringExtra("AIimg10path");
        String reportIMGpath21 = intent.getStringExtra("AIimg11path");
        String reportIMGpath22 = intent.getStringExtra("AIimg12path");
        String reportIMGpath23 = intent.getStringExtra("AIimg13path");
        String reportIMGpath24 = intent.getStringExtra("AIimg14path");
        String reportIMGpath25 = intent.getStringExtra("AIimg15path");
        String reportIMGpath26 = intent.getStringExtra("AIimg16path");


        String reportDdesc1 = intent.getStringExtra("Ddesc1");
        String reportDdesc2 = intent.getStringExtra("Ddesc2");
        String reportDdesc3 = intent.getStringExtra("Ddesc3");
        String reportDdesc4 = intent.getStringExtra("Ddesc4");
        String reportDdesc5 = intent.getStringExtra("Ddesc5");
        String reportDdesc6 = intent.getStringExtra("Ddesc6");
        String reportDdesc7 = intent.getStringExtra("Ddesc7");
        String reportDdesc8 = intent.getStringExtra("Ddesc8");
        String reportDdesc9 = intent.getStringExtra("Ddesc9");
        String reportDdesc10 = intent.getStringExtra("Ddesc10");
        String reportDdesc11 = intent.getStringExtra("Ddesc11");
        String reportDdesc12 = intent.getStringExtra("Ddesc12");
        String reportDdesc13 = intent.getStringExtra("Ddesc13");
        String reportDdesc14 = intent.getStringExtra("Ddesc14");
        String reportDdesc15 = intent.getStringExtra("Ddesc15");
        String reportDdesc16 = intent.getStringExtra("Ddesc16");
        String reportDdesc17 = intent.getStringExtra("Ddesc17");
        String reportDdesc18 = intent.getStringExtra("Ddesc18");
        String reportDdesc19 = intent.getStringExtra("Ddesc19");
        String reportDdesc20 = intent.getStringExtra("Ddesc20");
        String reportDdesc21 = intent.getStringExtra("Ddesc21");
        String reportDdesc22 = intent.getStringExtra("Ddesc22");
        String reportDdesc23 = intent.getStringExtra("Ddesc23");
        String reportDdesc24 = intent.getStringExtra("Ddesc24");
        String reportDdesc25 = intent.getStringExtra("Ddesc25");
        String reportDdesc26 = intent.getStringExtra("Ddesc26");



        reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
        reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
        reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
        reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);

        imgnum1 = (TextView) findViewById(R.id.imgnum1);
        imgnum2 = (TextView) findViewById(R.id.imgnum2);
        imgnum3 = (TextView) findViewById(R.id.imgnum3);
        imgnum4 = (TextView) findViewById(R.id.imgnum4);

        memo1 = (LinearLayout) findViewById(R.id.memo1);
        memo2 = (LinearLayout) findViewById(R.id.memo2);
        memo3 = (LinearLayout) findViewById(R.id.memo3);
        memo4 = (LinearLayout) findViewById(R.id.memo4);

        if(reportIMGpath1 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath1));
                ImagePath1 = reportIMGpath1;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc1);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath1));
                ImagePath2 = reportIMGpath1;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc1);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath1));
                ImagePath3 = reportIMGpath1;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc1);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath1));
                ImagePath4 = reportIMGpath1;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc1);
            }

        }

        if(reportIMGpath2 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath2));
                ImagePath1 = reportIMGpath2;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc2);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath2));
                ImagePath2 = reportIMGpath2;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc2);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath2));
                ImagePath3 = reportIMGpath2;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc2);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath2));
                ImagePath4 = reportIMGpath2;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc2);

            }


        }
        if(reportIMGpath3 != null){
            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath3));
                ImagePath1 = reportIMGpath3;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc3);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath3));
                ImagePath2 = reportIMGpath3;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc3);


            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath3));
                ImagePath3 = reportIMGpath3;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc3);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath3));
                ImagePath4 = reportIMGpath3;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc3);

            }

        }

        if(reportIMGpath4 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath4));
                ImagePath1 = reportIMGpath4;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc4);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath4));
                ImagePath2 = reportIMGpath4;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc4);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath4));
                ImagePath3 = reportIMGpath4;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc4);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath4));
                ImagePath4 = reportIMGpath4;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc4);

            }
        }
        if(reportIMGpath5 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath5));
                ImagePath1 = reportIMGpath5;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc5);


            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath5));
                ImagePath2 = reportIMGpath5;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc5);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath5));
                ImagePath3 = reportIMGpath5;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc5);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath5));
                ImagePath4 = reportIMGpath5;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc5);

            }
        }
        if(reportIMGpath6 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath6));
                ImagePath1 = reportIMGpath6;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc6);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath6));
                ImagePath2 = reportIMGpath6;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc6);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath6));
                ImagePath3 = reportIMGpath6;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc6);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath6));
                ImagePath4 = reportIMGpath6;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc6);

            }
        }
        if(reportIMGpath7 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath7));
                ImagePath1 = reportIMGpath7;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc7);


            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath7));
                ImagePath2 = reportIMGpath7;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc7);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath7));
                ImagePath3 = reportIMGpath7;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc7);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath7));
                ImagePath4 = reportIMGpath7;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc7);

            }
        }
        if(reportIMGpath8 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath8));
                ImagePath1 = reportIMGpath8;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc8);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath8));
                ImagePath2 = reportIMGpath8;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc8);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath8));
                ImagePath3 = reportIMGpath8;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc8);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath8));
                ImagePath4 = reportIMGpath8;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc8);

            }
        }
        if(reportIMGpath9 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath9));
                ImagePath1 = reportIMGpath9;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc9);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath9));
                ImagePath2 = reportIMGpath9;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc9);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath9));
                ImagePath3 = reportIMGpath9;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc9);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath9));
                ImagePath4 = reportIMGpath9;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc9);

            }
        }
        if(reportIMGpath10 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath10));
                ImagePath1 = reportIMGpath10;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc10);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath10));
                ImagePath2 = reportIMGpath10;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc10);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath10));
                ImagePath3 = reportIMGpath10;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc10);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath10));
                ImagePath4 = reportIMGpath10;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc10);

            }
        }




        if(reportIMGpath11 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath11));
                ImagePath1 = reportIMGpath11;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc11);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath11));
                ImagePath2 = reportIMGpath11;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc11);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath11));
                ImagePath3 = reportIMGpath11;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc11);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath11));
                ImagePath4 = reportIMGpath11;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc11);

            }
        }
        if(reportIMGpath12 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath12));
                ImagePath1 = reportIMGpath12;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc12);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath12));
                ImagePath2 = reportIMGpath12;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc12);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath12));
                ImagePath3 = reportIMGpath12;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc12);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath12));
                ImagePath4 = reportIMGpath12;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc12);

            }
        }
        if(reportIMGpath13 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath13));
                ImagePath1 = reportIMGpath13;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc13);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath13));
                ImagePath2 = reportIMGpath13;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc13);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath13));
                ImagePath3 = reportIMGpath13;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc13);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath13));
                ImagePath4 = reportIMGpath13;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc13);

            }
        }
        if(reportIMGpath14 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath14));
                ImagePath1 = reportIMGpath14;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc14);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath14));
                ImagePath2 = reportIMGpath14;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc14);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath14));
                ImagePath3 = reportIMGpath14;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc14);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath14));
                ImagePath4 = reportIMGpath14;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc14);

            }
        }
        if(reportIMGpath15 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath15));
                ImagePath1 = reportIMGpath15;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc15);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath15));
                ImagePath2 = reportIMGpath15;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc15);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath15));
                ImagePath3 = reportIMGpath15;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc15);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath15));
                ImagePath4 = reportIMGpath15;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc15);

            }
        }
        if(reportIMGpath16 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath16));
                ImagePath1 = reportIMGpath16;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc16);


            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath16));
                ImagePath2 = reportIMGpath16;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc16);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath16));
                ImagePath3 = reportIMGpath16;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc16);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath16));
                ImagePath4 = reportIMGpath16;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc16);

            }
        }
        if(reportIMGpath17 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath17));
                ImagePath1 = reportIMGpath17;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc17);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath17));
                ImagePath2 = reportIMGpath17;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc17);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath17));
                ImagePath3 = reportIMGpath17;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc17);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath17));
                ImagePath4 = reportIMGpath17;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc17);

            }
        }
        if(reportIMGpath18 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath18));
                ImagePath1 = reportIMGpath18;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc18);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath18));
                ImagePath2 = reportIMGpath18;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc18);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath18));
                ImagePath3 = reportIMGpath18;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc18);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath18));
                ImagePath4 = reportIMGpath18;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc18);

            }
        }
        if(reportIMGpath19 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath19));
                ImagePath1 = reportIMGpath19;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc19);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath19));
                ImagePath2 = reportIMGpath19;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc19);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath19));
                ImagePath3 = reportIMGpath19;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc19);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath19));
                ImagePath4 = reportIMGpath19;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc19);

            }
        }
        if(reportIMGpath20 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath20));
                ImagePath1 = reportIMGpath20;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc20);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath20));
                ImagePath2 = reportIMGpath20;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc20);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath20));
                ImagePath3 = reportIMGpath20;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc20);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath20));
                ImagePath4 = reportIMGpath20;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc20);

            }
        }
        if(reportIMGpath21 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath21));
                ImagePath1 = reportIMGpath21;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc21);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath21));
                ImagePath2 = reportIMGpath21;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc21);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath21));
                ImagePath3 = reportIMGpath21;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc21);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath21));
                ImagePath4 = reportIMGpath21;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc21);

            }
        }
        if(reportIMGpath22 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath22));
                ImagePath1 = reportIMGpath22;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc22);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath22));
                ImagePath2 = reportIMGpath22;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc22);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath22));
                ImagePath3 = reportIMGpath22;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc22);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath22));
                ImagePath4 = reportIMGpath22;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc22);

            }
        }
        if(reportIMGpath23 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath23));
                ImagePath1 = reportIMGpath23;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc23);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath23));
                ImagePath2 = reportIMGpath23;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc23);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath23));
                ImagePath3 = reportIMGpath23;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc23);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath23));
                ImagePath4 = reportIMGpath23;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc23);

            }
        }
        if(reportIMGpath24 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath24));
                ImagePath1 = reportIMGpath24;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc24);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath24));
                ImagePath2 = reportIMGpath24;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc24);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath24));
                ImagePath3 = reportIMGpath24;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc24);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath24));
                ImagePath4 = reportIMGpath24;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc24);

            }
        }
        if(reportIMGpath25 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath25));
                ImagePath1 = reportIMGpath25;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc25);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath25));
                ImagePath2 = reportIMGpath25;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc25);


            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath25));
                ImagePath3 = reportIMGpath25;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc25);


            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath25));
                ImagePath4 = reportIMGpath25;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc25);

            }
        }
        if(reportIMGpath26 != null){

            if(reportIMG1.getVisibility()==View.GONE){
                reportIMG1 = (ImageView) findViewById(R.id.reportIMG1);
                reportIMG1.setVisibility(View.VISIBLE);
                reportIMG1.setImageURI(Uri.parse(reportIMGpath26));
                ImagePath1 = reportIMGpath26;
                Desc1.setVisibility(View.VISIBLE);
                Desc1.setText(reportDdesc26);

            } else if (reportIMG2.getVisibility()==View.GONE){
                reportIMG2 = (ImageView) findViewById(R.id.reportIMG2);
                reportIMG2.setVisibility(View.VISIBLE);
                reportIMG2.setImageURI(Uri.parse(reportIMGpath26));
                ImagePath2 = reportIMGpath26;
                Desc2.setVisibility(View.VISIBLE);
                Desc2.setText(reportDdesc26);

            } else if (reportIMG3.getVisibility()==View.GONE){
                reportIMG3 = (ImageView) findViewById(R.id.reportIMG3);
                reportIMG3.setVisibility(View.VISIBLE);
                reportIMG3.setImageURI(Uri.parse(reportIMGpath26));
                ImagePath3 = reportIMGpath26;
                Desc3.setVisibility(View.VISIBLE);
                Desc3.setText(reportDdesc26);

            } else if (reportIMG4.getVisibility()==View.GONE){
                reportIMG4 = (ImageView) findViewById(R.id.reportIMG4);
                reportIMG4.setVisibility(View.VISIBLE);
                reportIMG4.setImageURI(Uri.parse(reportIMGpath26));
                ImagePath4 = reportIMGpath26;
                Desc4.setVisibility(View.VISIBLE);
                Desc4.setText(reportDdesc26);

            }

        }

        ImageView circleborder1 = (ImageView) findViewById(R.id.circleborder1);
        ImageView circleborder2 = (ImageView) findViewById(R.id.circleborder2);
        ImageView circleborder3 = (ImageView) findViewById(R.id.circleborder3);
        ImageView circleborder4 = (ImageView) findViewById(R.id.circleborder4);

        if(ImagePath1 != null){

            imgnum1.setVisibility(View.VISIBLE);
            memo1.setVisibility(View.VISIBLE);

            circleborder1.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            Uri uri = Uri.parse(ImagePath1);
            BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;

            if (imageWidth == 1716 && imageHeight == 1632){
                circleborder1.setImageResource(R.drawable.circleborder17161632);
                Log.i("circleborder17161632","circleborder17161632");
                int width1 = circleborder1.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder1.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder17161632).resize(width1/3,heigh1/3).into(circleborder1);
            }
            else if (imageWidth == 1801 && imageHeight == 1201){
                circleborder1.setImageResource(R.drawable.circleborder18011201);
                Log.i("circleborder18011201","circleborder18011201");
                int width1 = circleborder1.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder1.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder18011201).resize(width1/3,heigh1/3).into(circleborder1);
            }
            else if (imageWidth == 1956 && imageHeight == 1934){
                circleborder1.setImageResource(R.drawable.circleborder19561934);
                Log.i("circleborder19561934","circleborder19561934");
                int width1 = circleborder1.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder1.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder19561934).resize(width1/3,heigh1/3).into(circleborder1);

            }
            else if (imageWidth == 2304 && imageHeight == 1728){
                circleborder1.setImageResource(R.drawable.circleborder23041728);
                Log.i("circleborder23041728","circleborder23041728");
                int width1 = circleborder1.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder1.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder23041728).resize(width1/3,heigh1/3).into(circleborder1);
            }
            else if (imageWidth == 2400 && imageHeight == 2448){
                circleborder1.setImageResource(R.drawable.circleborder24002448);
                Log.i("circleborder24002448","circleborder24002448");
                int width1 = circleborder1.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder1.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder24002448).resize(width1/3,heigh1/3).into(circleborder1);
            }
            else if (imageWidth == 2560 && imageHeight == 1920){
                circleborder1.setImageResource(R.drawable.circleborder25601921);
                Log.i("circleborder25601921","circleborder25601921");
                int width1 = circleborder1.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder1.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder25601921).resize(width1/3,heigh1/3).into(circleborder1);
            }
            else if (imageWidth == 3200 && imageHeight == 2550){
                circleborder1.setImageResource(R.drawable.circleborder32002550);
                Log.i("circleborder32002550","circleborder32002550");
                int width1 = circleborder1.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder1.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder32002550).resize(width1/3,heigh1/3).into(circleborder1);
            }
            else if (imageWidth == 3696 && imageHeight == 2448){
                circleborder1.setImageResource(R.drawable.circleborder36962448);
                Log.i("circleborder36962448","circleborder36962448");
                int width1 = circleborder1.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder1.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder36962448).resize(width1/3,heigh1/3).into(circleborder1);
            }
            else if (imageWidth == 3888 && imageHeight == 2592){
                circleborder1.setImageResource(R.drawable.circleborder38882592);
                Log.i("circleborder38882592","circleborder38882592");
                int width1 = circleborder1.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder1.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder38882592).resize(width1/3,heigh1/3).into(circleborder1);
            }
            else if (imageWidth == 768 && imageHeight == 768){
                circleborder1.setImageResource(R.drawable.circleborder768768);
                Log.i("circleborder768768","circleborder768768");
                int width1 = circleborder1.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder1.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder768768).resize(width1/3,heigh1/3).into(circleborder1);
            }
        }

        if(ImagePath2 != null){

            imgnum2.setVisibility(View.VISIBLE);
            memo2.setVisibility(View.VISIBLE);

            circleborder2.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            Uri uri = Uri.parse(ImagePath2);
            BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;

            if (imageWidth == 1716 && imageHeight == 1632){
                circleborder2.setImageResource(R.drawable.circleborder17161632);
                Log.i("circleborder17161632","circleborder17161632");
                int width1 = circleborder2.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder2.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder17161632).resize(width1/3,heigh1/3).into(circleborder2);
            }
            else if (imageWidth == 1801 && imageHeight == 1201){
                circleborder2.setImageResource(R.drawable.circleborder18011201);
                Log.i("circleborder18011201","circleborder18011201");
                int width1 = circleborder2.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder2.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder18011201).resize(width1/3,heigh1/3).into(circleborder2);
            }
            else if (imageWidth == 1956 && imageHeight == 1934){
                circleborder2.setImageResource(R.drawable.circleborder19561934);
                Log.i("circleborder19561934","circleborder19561934");
                int width1 = circleborder2.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder2.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder19561934).resize(width1/3,heigh1/3).into(circleborder2);
            }
            else if (imageWidth == 2304 && imageHeight == 1728){
                circleborder2.setImageResource(R.drawable.circleborder23041728);
                Log.i("circleborder23041728","circleborder23041728");
                int width1 = circleborder2.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder2.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder23041728).resize(width1/3,heigh1/3).into(circleborder2);
            }
            else if (imageWidth == 2400 && imageHeight == 2448){
                circleborder2.setImageResource(R.drawable.circleborder24002448);
                Log.i("circleborder24002448","circleborder24002448");
                int width1 = circleborder2.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder2.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder24002448).resize(width1/3,heigh1/3).into(circleborder2);
            }
            else if (imageWidth == 2560 && imageHeight == 1920){
                circleborder2.setImageResource(R.drawable.circleborder25601921);
                Log.i("circleborder25601921","circleborder25601921");
                int width1 = circleborder2.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder2.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder25601921).resize(width1/3,heigh1/3).into(circleborder2);
            }
            else if (imageWidth == 3200 && imageHeight == 2550){
                circleborder2.setImageResource(R.drawable.circleborder32002550);
                Log.i("circleborder32002550","circleborder32002550");
                int width1 = circleborder2.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder2.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder32002550).resize(width1/3,heigh1/3).into(circleborder2);
            }
            else if (imageWidth == 3696 && imageHeight == 2448){
                circleborder2.setImageResource(R.drawable.circleborder36962448);
                Log.i("circleborder36962448","circleborder36962448");
                int width1 = circleborder2.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder2.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder36962448).resize(width1/3,heigh1/3).into(circleborder2);
            }
            else if (imageWidth == 3888 && imageHeight == 2592){
                circleborder2.setImageResource(R.drawable.circleborder38882592);
                Log.i("circleborder38882592","circleborder38882592");
                int width1 = circleborder2.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder2.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder38882592).resize(width1/3,heigh1/3).into(circleborder2);
            }
            else if (imageWidth == 768 && imageHeight == 768){
                circleborder2.setImageResource(R.drawable.circleborder768768);
                Log.i("circleborder768768","circleborder768768");
                int width1 = circleborder2.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder2.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder768768).resize(width1/3,heigh1/3).into(circleborder2);
            }
        }

        if(ImagePath3 != null){

            imgnum3.setVisibility(View.VISIBLE);
            memo3.setVisibility(View.VISIBLE);

            circleborder3.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            Uri uri = Uri.parse(ImagePath3);
            BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;

            if (imageWidth == 1716 && imageHeight == 1632){
                circleborder3.setImageResource(R.drawable.circleborder17161632);
                Log.i("circleborder17161632","circleborder17161632");
                int width1 = circleborder3.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder3.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder17161632).resize(width1/3,heigh1/3).into(circleborder3);
            }
            else if (imageWidth == 1801 && imageHeight == 1201){
                circleborder3.setImageResource(R.drawable.circleborder18011201);
                Log.i("circleborder18011201","circleborder18011201");
                int width1 = circleborder3.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder3.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder18011201).resize(width1/3,heigh1/3).into(circleborder3);
            }
            else if (imageWidth == 1956 && imageHeight == 1934){
                circleborder3.setImageResource(R.drawable.circleborder19561934);
                Log.i("circleborder19561934","circleborder19561934");
                int width1 = circleborder3.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder3.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder19561934).resize(width1/3,heigh1/3).into(circleborder3);
            }
            else if (imageWidth == 2304 && imageHeight == 1728){
                circleborder3.setImageResource(R.drawable.circleborder23041728);
                Log.i("circleborder23041728","circleborder23041728");
                int width1 = circleborder3.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder3.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder23041728).resize(width1/3,heigh1/3).into(circleborder3);
            }
            else if (imageWidth == 2400 && imageHeight == 2448){
                circleborder3.setImageResource(R.drawable.circleborder24002448);
                Log.i("circleborder24002448","circleborder24002448");
                int width1 = circleborder3.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder3.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder24002448).resize(width1/3,heigh1/3).into(circleborder3);
            }
            else if (imageWidth == 2560 && imageHeight == 1920){
                circleborder3.setImageResource(R.drawable.circleborder25601921);
                Log.i("circleborder25601921","circleborder25601921");
                int width1 = circleborder3.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder3.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder25601921).resize(width1/3,heigh1/3).into(circleborder3);
            }
            else if (imageWidth == 3200 && imageHeight == 2550){
                circleborder3.setImageResource(R.drawable.circleborder32002550);
                Log.i("circleborder32002550","circleborder32002550");
                int width1 = circleborder3.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder3.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder32002550).resize(width1/3,heigh1/3).into(circleborder3);
            }
            else if (imageWidth == 3696 && imageHeight == 2448){
                circleborder3.setImageResource(R.drawable.circleborder36962448);
                Log.i("circleborder36962448","circleborder36962448");
                int width1 = circleborder3.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder3.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder36962448).resize(width1/3,heigh1/3).into(circleborder3);
            }
            else if (imageWidth == 3888 && imageHeight == 2592){
                circleborder3.setImageResource(R.drawable.circleborder38882592);
                Log.i("circleborder38882592","circleborder38882592");
                int width1 = circleborder3.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder3.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder38882592).resize(width1/3,heigh1/3).into(circleborder3);
            }
            else if (imageWidth == 768 && imageHeight == 768){
                circleborder3.setImageResource(R.drawable.circleborder768768);
                Log.i("circleborder768768","circleborder768768");
                int width1 = circleborder3.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder3.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder768768).resize(width1/3,heigh1/3).into(circleborder3);
            }
        }

        if(ImagePath4 != null){

            imgnum4.setVisibility(View.VISIBLE);
            memo4.setVisibility(View.VISIBLE);

            circleborder4.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            Uri uri = Uri.parse(ImagePath4);
            BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;

            if (imageWidth == 1716 && imageHeight == 1632){
                circleborder4.setImageResource(R.drawable.circleborder17161632);
                Log.i("circleborder17161632","circleborder17161632");
                int width1 = circleborder4.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder4.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder17161632).resize(width1/3,heigh1/3).into(circleborder4);
            }
            else if (imageWidth == 1801 && imageHeight == 1201){
                circleborder4.setImageResource(R.drawable.circleborder18011201);
                Log.i("circleborder18011201","circleborder18011201");
                int width1 = circleborder4.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder4.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder18011201).resize(width1/3,heigh1/3).into(circleborder4);
            }
            else if (imageWidth == 1956 && imageHeight == 1934){
                circleborder4.setImageResource(R.drawable.circleborder19561934);
                Log.i("circleborder19561934","circleborder19561934");
                int width1 = circleborder4.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder4.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder19561934).resize(width1/3,heigh1/3).into(circleborder4);
            }
            else if (imageWidth == 2304 && imageHeight == 1728){
                circleborder4.setImageResource(R.drawable.circleborder23041728);
                Log.i("circleborder23041728","circleborder23041728");
                int width1 = circleborder4.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder4.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder23041728).resize(width1/3,heigh1/3).into(circleborder4);
            }
            else if (imageWidth == 2400 && imageHeight == 2448){
                circleborder4.setImageResource(R.drawable.circleborder24002448);
                Log.i("circleborder24002448","circleborder24002448");
                int width1 = circleborder4.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder4.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder24002448).resize(width1/3,heigh1/3).into(circleborder4);
            }
            else if (imageWidth == 2560 && imageHeight == 1920){
                circleborder4.setImageResource(R.drawable.circleborder25601921);
                Log.i("circleborder25601921","circleborder25601921");
                int width1 = circleborder4.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder4.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder25601921).resize(width1/3,heigh1/3).into(circleborder4);
            }
            else if (imageWidth == 3200 && imageHeight == 2550){
                circleborder4.setImageResource(R.drawable.circleborder32002550);
                Log.i("circleborder32002550","circleborder32002550");
                int width1 = circleborder4.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder4.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder32002550).resize(width1/3,heigh1/3).into(circleborder4);
            }
            else if (imageWidth == 3696 && imageHeight == 2448){
                circleborder4.setImageResource(R.drawable.circleborder36962448);
                Log.i("circleborder36962448","circleborder36962448");
                int width1 = circleborder4.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder4.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder36962448).resize(width1/3,heigh1/3).into(circleborder4);
            }
            else if (imageWidth == 3888 && imageHeight == 2592){
                circleborder4.setImageResource(R.drawable.circleborder38882592);
                Log.i("circleborder38882592","circleborder38882592");
                int width1 = circleborder4.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder4.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder38882592).resize(width1/3,heigh1/3).into(circleborder4);
            }
            else if (imageWidth == 768 && imageHeight == 768){
                circleborder4.setImageResource(R.drawable.circleborder768768);
                Log.i("circleborder768768","circleborder768768");
                int width1 = circleborder4.getDrawable().getIntrinsicWidth();
                int heigh1 = circleborder4.getDrawable().getIntrinsicHeight();
                Picasso.get().load(R.drawable.circleborder768768).resize(width1/3,heigh1/3).into(circleborder4);
            }
        }


        Button share = (Button) findViewById( R.id.share );
        share.setOnClickListener( sharepdf );

        Button view_pdf = (Button) findViewById( R.id.view_pdf );
        view_pdf.setOnClickListener( seepdf );

        Button save_pdf = (Button) findViewById(R.id.save_pdf);
        save_pdf.setOnClickListener(savepdf);

        Button cancel = (Button) findViewById(R.id.cancelViewreport);
        cancel.setOnClickListener(cancelViewR1);



        //設定TextView
        TextView get_time = (TextView) findViewById( R.id.get_time );
        TextView get_number = (TextView) findViewById( R.id.get_number );
        //TextView get_phone = (TextView) findViewById( R.id.get_phone );
        TextView get_age = (TextView) findViewById( R.id.get_age );
        TextView get_rad = (TextView) findViewById( R.id.get_rad );






        //資料夾路徑
        File fileport = Environment.getExternalStorageDirectory();
        File imagefile = new File(fileport,"1.MiiS/2.IMAGE");

        // get Diagnosis Activity Data
        ArrayList<String> imageList = (ArrayList<String>) getIntent().getStringArrayListExtra("list");

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
        Log.i("HubSSID",String.valueOf(HubSSID));
        Log.i("HubSSIDpwd",String.valueOf(HubSSIDpwd));

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
                AlertDialog.Builder a_builder = new AlertDialog.Builder(Viewwait_report_viewfinish.this);
                a_builder.setMessage(R.string.Report_go_home_message)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

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

        return super.onOptionsItemSelected(item);
    }
    //===============================================================================================




    //點選Buttom動作 - 首頁按鈕
    private Button.OnClickListener gohome =
            new Button.OnClickListener(){
                @Override
                public void onClick(View v){
                    //轉跳至首頁
                    Intent intent = new Intent(Viewwait_report_viewfinish.this , Option.class);
                    startActivity(intent);
                    Viewwait_report_viewfinish.this.finish();

                }
            };


    //點選Buttom動作 - 列印按鈕
    private Button.OnClickListener goprint =
            new Button.OnClickListener(){
                @Override
                public void onClick(View v){
                    changeWifi2();
                    createPDF();

                    Context context = v.getContext();
                    final ProgressDialog dialog= ProgressDialog.show(context,getResources().getString(R.string.Report_linking_printer), getResources().getString(R.string.Please_wait),true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(4000);
                                dialog.dismiss();
                            }
                            catch(InterruptedException ex){
                                ex.printStackTrace();
                            }
                        }
                    }).start();



                    Handler handler = new Handler();


                    handler.postDelayed(new Runnable(){

                        @Override
                        public void run() {
                            //轉跳EPSON APP
                            Intent intent = new Intent();
                            intent.setClassName("epson.print", "epson.print.WellcomeScreenActivity");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }}, 4000);




                }


            };

    private Button.OnClickListener savepdf =
            new Button.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (!Button_Click.isFastDoubleClick()) {
                        createPDF();
                        final String pid = getIntent().getExtras().getString("b");
                        Log.i("AAAAAAAA",pid);
                        SQLiteDatabase idDB = myDB.getReadableDatabase();
                        idDB.delete("users_data", "PATIENTID=?", new String[]{pid});
                    }
                }
            };


    private Button.OnClickListener seepdf =
            new Button.OnClickListener(){
                @Override
                public void onClick(final View v){

                    if (!Button_Click.isFastDoubleClick()) {

                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        final String ssid = wifiInfo.getSSID();
                        //startRepeating(v);


                        if (scannedResult.size() > 0){
                            scannedResult.clear();
                        }
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                    1);
                        }
                        if (wifiManager.isWifiEnabled()){
                            new AsyncTask<Void, String, String>(){
                                @Override
                                protected String doInBackground(Void... voids) {
                                    List<ScanResult> scanResultList = wifiManager.getScanResults();
                                    for (int i = 0; i < scanResultList.size(); i++){
                                        scannedResult.add(scanResultList.get(i).SSID+"\n");
                                    }
                                    return scannedResult.toString();
                                }

                                @Override
                                protected void onPostExecute(String s) {
                                    Context context1 = v.getContext();
                                    Log.i("s3",s);




                                    String PrinterSSID1 =  "\""+PrinterSSID +"\"";


                                    if(s.indexOf(PrinterSSID)<0){
                                        openDialog();
                                    }
                                    else if(!ssid.equals(PrinterSSID1)){

                                        final ProgressDialog dialog= ProgressDialog.show(context1,getResources().getString(R.string.Report_linking_printer), getResources().getString(R.string.Please_wait),true);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(5000);
                                                    dialog.dismiss();
                                                }
                                                catch(InterruptedException ex){
                                                    ex.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        android.os.Handler handler = new Handler();
                                        handler.postDelayed(new Runnable(){

                                            @Override
                                            public void run() {
                                                viewPDF();
                                                final String pid = getIntent().getExtras().getString("b");
                                                Log.i("AAAAAAAA",pid);
                                                SQLiteDatabase idDB = myDB.getReadableDatabase();
                                                idDB.delete("users_data", "PATIENTID=?", new String[]{pid});
                                            }}, 5500);
                                    } else {
                                        viewPDF();
                                        final String pid = getIntent().getExtras().getString("b");
                                        Log.i("AAAAAAAA",pid);
                                        SQLiteDatabase idDB = myDB.getReadableDatabase();
                                        idDB.delete("users_data", "PATIENTID=?", new String[]{pid});
                                    }

                                }
                            }.execute();
                        }
                    }
                }
            };



    private Button.OnClickListener sharepdf =
            new Button.OnClickListener(){
                @Override
                public void onClick(View v){

                    if (!Button_Click.isFastDoubleClick()) {
                        sharPDF();
                        final String pid = getIntent().getExtras().getString("b");
                        Log.i("AAAAAAAA",pid);
                        SQLiteDatabase idDB = myDB.getReadableDatabase();
                        idDB.delete("users_data", "PATIENTID=?", new String[]{pid});
                    }
                }
            };


    private Button.OnClickListener cancelViewR1 =
            new Button.OnClickListener(){
                @Override
                public void onClick(View v){

                    createPDF();
                    final String pid = getIntent().getExtras().getString("b");
                    Log.i("AAAAAAAA",pid);
                    SQLiteDatabase idDB = myDB.getReadableDatabase();
                    idDB.delete("users_data", "PATIENTID=?", new String[]{pid});
                    Intent intent = new Intent(getApplicationContext(), Option.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            };

    private void test2() {
        LinearLayout report_page = (LinearLayout) findViewById( R.id.report_page );
        bitmap = loadBitmapFromView(report_page, report_page.getWidth(), report_page.getHeight());

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float width = displaymetrics.widthPixels ;
        float hight = displaymetrics.heightPixels ;
        int convertWidth = (int) width, convertHighet = (int) hight;

        //Resources mResources = getResources();
        //Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        //PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder( PrintAttributes.MediaSize.ISO_B5.getWidthMils() * 72 / 1000, PrintAttributes.MediaSize.ISO_B5.getHeightMils() * 72 / 1000, 1 ).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        //Paint paint = new Paint();
        //canvas.drawPaint(paint);


        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
        //bitmap = Bitmap.createScaledBitmap(bitmap,  PrintAttributes.MediaSize.ISO_B5.getWidthMils() * 72 / 1000,  PrintAttributes.MediaSize.ISO_B5.getHeightMils() * 72 / 1000, true);
        //paint.setColor(Color.WHITE);

        canvas.drawBitmap(bitmap, 0, 0 , null);
        Matrix matrix = new Matrix(  );
        matrix.postScale(convertWidth, convertHighet);
        canvas.rotate( 90 );
        document.finishPage(page);

    }
    private void test() {

        //Create PDF document
        PdfDocument document = new PdfDocument();

        // set UI range
        LinearLayout linearLayout = (LinearLayout) findViewById( R.id.report_page );




        // Set LinearLayout  Width、Height  in PDF Document
        // PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder( linearLayout.getMu inimumWidth(),linearLayout.getMinimumHeight(), 1).create();
        //PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder( 499, 709,1).create();


        // crate a page description

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(
                PrintAttributes.MediaSize.ISO_A5.getWidthMils() * 72 / 1000,
                PrintAttributes.MediaSize.ISO_A5.getHeightMils() * 72 / 1000,
                1 ).create();


        PdfDocument.Page page = document.startPage(pageInfo);


        document.finishPage(page);


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
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        //PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder( PrintAttributes.MediaSize.ISO_B5.getWidthMils() * 72 / 1000, PrintAttributes.MediaSize.ISO_B5.getHeightMils() * 72 / 1000, 1 ).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        //Paint paint = new Paint();
        //canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
        //bitmap = Bitmap.createScaledBitmap(bitmap,  PrintAttributes.MediaSize.ISO_B5.getWidthMils() * 72 / 1000,  PrintAttributes.MediaSize.ISO_B5.getHeightMils() * 72 / 1000, true);
        //paint.setColor(Color.WHITE);

        canvas.drawBitmap(bitmap, 0, 0 , null);
        Matrix matrix = new Matrix(  );
        matrix.postScale(convertWidth, convertHighet);
        canvas.rotate( 90 );
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

            try{

                outputStream =new FileOutputStream( file + "/" + today + pName + pID + ".pdf");
                document.writeTo(outputStream);
                document.close();
                Toast.makeText(Viewwait_report_viewfinish.this, R.string.PDF_create_success, Toast.LENGTH_SHORT).show();
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }catch(IOException e) {
                e.printStackTrace();
                Toast.makeText(Viewwait_report_viewfinish.this, R.string.PDF_create_fail, Toast.LENGTH_SHORT).show();
            }

        }
    }



    public static Bitmap loadBitmapFromView(View view, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    private void viewPDF() {


        createPDF();
        Intent intent = this.getIntent();
        String pName=intent.getStringExtra("a");
        String pID=intent.getStringExtra("b");

        File fileport = Environment.getExternalStorageDirectory();
        File file = new File( fileport,"1.MiiS/1.PDF"+ "/" + today + pName + pID + ".pdf" );
        if (file.exists())
        {

            file.mkdir();


            try
            {

                Intent intent1=new Intent(Intent.ACTION_SEND);

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
                Toast.makeText(Viewwait_report_viewfinish.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }

        }
    }


    private void sharPDF(){


        createPDF();
        Intent intent = this.getIntent();
        String pName=intent.getStringExtra("a");
        String pID=intent.getStringExtra("b");

        File fileport = Environment.getExternalStorageDirectory();
        File file = new File( fileport,"1.MiiS/1.PDF"+ "/" + today + pName + pID + ".pdf" );
        if (file.exists())
        {

            file.mkdir();

            try
            {

                Intent intent1=new Intent(Intent.ACTION_SEND);
                intent1.setDataAndType(uri, "application/pdf");

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
                Toast.makeText(Viewwait_report_viewfinish.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void onBackPressed() {
        finish();
    }

    public void openDialog() {
        DiaLogPrinter diaLogPrinter = new DiaLogPrinter();
        diaLogPrinter.show(getSupportFragmentManager(), "example dialog");
    }

}





