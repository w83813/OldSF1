package com.example.miis200;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import com.example.miis200.utils.WifiUtil;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Viewwait_patientInfo extends AppCompatActivity {


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

    private String printName = "MiiS-OA";
    private String printPwd = "vacationoa";

    private void printWifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(printName, printPwd);
    }

    private void aiWifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(AS_DIBSSID, AS_DIBSSIDPWD);
    }





    String eyeLp,eyeRp;


    DatabaseHelper myDB;
    ArrayList<User> userList;
    ListView listView;
    User user;


    TextView pname,pid,pbir,pgender,pphone;
    Button btnreadimg;
    ImageView Leye,Reye,WaitVeyeP1,WaitVeyeP2,WaitVeyeP3,WaitVeyeP4,WaitVeyeP5,WaitVeyeP6,WaitVeyeP7,WaitVeyeP8;
    String LeyePath,ReyePath,eyPath1,eyPath2,eyPath3,eyPath4,eyPath5,eyPath6,eyPath7,eyPath8;
    String page;

    public Context context;
    public static final String TAG = "Viewwait_patientInfo";

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    TextView dateBirthday;

    //科別選單
    //男、女按鈕
    private TextView checkTime;


    private RecyclerView recyclerView;
    private List<Addexamination_ItemRecycler> listRecycler = new ArrayList<Addexamination_ItemRecycler>(  );
    private MyAdapter myAdapter;
    int checkInterval = 2000; //set return time
    Handler updateHandler; // return time
    boolean viewingList; // return time

    public static final SimpleDateFormat sdf5 = new SimpleDateFormat("yyyyMMdd");
    Date current = new Date();
    public String today = sdf5.format(current).toString();
    public String folder0 = "/DCIM/"+sdf5.format(current).toString();
    String rootDir = "/DCIM/"+today;
    String directoryName = rootDir;
    private String mLastPictureName = null;

    //get new time
    public static final SimpleDateFormat time = new SimpleDateFormat("HHmmss");
    public String newtimetoday = time.format( current ).toString();
    int intnewtimetoday = Integer.parseInt( newtimetoday );

    Intent intent_viewphot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loadData();
        updateViews();


        myDB = new DatabaseHelper(this);

        userList = new ArrayList<>();
        Cursor data = myDB.getListContents1();
        int numRows = data.getCount();


        //抓取手機上時間
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy年MM月dd日 ");
        Date date = new Date( System.currentTimeMillis() );
        //顯示在檢查日期上


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewwait_patientinfo);

        //===============================================================================================
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Viewimage_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //===============================================================================================

        Intent intent = this.getIntent();
        String pName=intent.getStringExtra("a");
        String pID=intent.getStringExtra("b");
        String pBir=intent.getStringExtra("c");
        String pGender=intent.getStringExtra("d");
        String pNum=intent.getStringExtra("e");
        String pAge=intent.getStringExtra("age");

        intent_viewphot = new Intent(this,Addexamination_examination_viewphoto.class);


        LeyePath=intent.getStringExtra("f");
        ReyePath=intent.getStringExtra("g");

        eyPath1 = intent.getStringExtra("wvEpath1");
        eyPath2 = intent.getStringExtra("wvEpath2");
        eyPath3 = intent.getStringExtra("wvEpath3");
        eyPath4 = intent.getStringExtra("wvEpath4");
        eyPath5 = intent.getStringExtra("wvEpath5");
        eyPath6 = intent.getStringExtra("wvEpath6");
        eyPath7 = intent.getStringExtra("wvEpath7");
        eyPath8 = intent.getStringExtra("wvEpath8");


        String eXT = intent.getStringExtra("Ext");



        pname = (TextView) findViewById( R.id.CheckPatientName );
        pid = (TextView) findViewById( R.id.CheckPatientID );
        pbir = (TextView) findViewById( R.id.CheckPatientBir );
        pgender = (TextView) findViewById( R.id.CheckPatientGender );
        pphone = (TextView) findViewById( R.id.CheckPatientPhone );



        checkTime = (TextView) findViewById( R.id.Check_Time );
        Leye = (ImageView) findViewById(R.id.Leye);
        Reye = (ImageView) findViewById(R.id.Reye);
        WaitVeyeP1 = (ImageView) findViewById(R.id.waitVeyeP1);
        WaitVeyeP2 = (ImageView) findViewById(R.id.waitVeyeP2);
        WaitVeyeP3 = (ImageView) findViewById(R.id.waitVeyeP3);
        WaitVeyeP4 = (ImageView) findViewById(R.id.waitVeyeP4);
        WaitVeyeP5 = (ImageView) findViewById(R.id.waitVeyeP5);
        WaitVeyeP6 = (ImageView) findViewById(R.id.waitVeyeP6);
        WaitVeyeP7 = (ImageView) findViewById(R.id.waitVeyeP7);
        WaitVeyeP8 = (ImageView) findViewById(R.id.waitVeyeP8);

        if(LeyePath != null) {

            Leye.setImageURI(Uri.parse(LeyePath));
            int width = Leye.getDrawable().getIntrinsicWidth();
            int heigh = Leye.getDrawable().getIntrinsicHeight();
            Log.i("WWWWWW",String.valueOf(width));
            Log.i("WWWWWW",String.valueOf(heigh));
            Picasso.get().load("file://"+LeyePath).resize(width/3,heigh/3).into(Leye);

        } else{
            Leye.setVisibility(View.GONE);
        }
        if(ReyePath != null) {
            Reye.setImageURI(Uri.parse(ReyePath));
            int width = Reye.getDrawable().getIntrinsicWidth();
            int heigh = Reye.getDrawable().getIntrinsicHeight();
            Log.i("WWWWWW",String.valueOf(width));
            Log.i("WWWWWW",String.valueOf(heigh));
            Picasso.get().load("file://"+ReyePath).resize(width/3,heigh/3).into(Reye);
        } else{
            Reye.setVisibility(View.GONE);
        }
        if(eyPath1 != null) {
            WaitVeyeP1.setImageURI(Uri.parse(eyPath1));
            int width = WaitVeyeP1.getDrawable().getIntrinsicWidth();
            int heigh = WaitVeyeP1.getDrawable().getIntrinsicHeight();
            Log.i("WWWWWW",String.valueOf(width));
            Log.i("WWWWWW",String.valueOf(heigh));
            Picasso.get().load("file://"+eyPath1).resize(width/3,heigh/3).into(WaitVeyeP1);
        } else{
            WaitVeyeP1.setVisibility(View.GONE);
        }
        if(eyPath2 != null) {
            WaitVeyeP2.setImageURI(Uri.parse(eyPath2));
            int width = WaitVeyeP2.getDrawable().getIntrinsicWidth();
            int heigh = WaitVeyeP2.getDrawable().getIntrinsicHeight();
            Log.i("WWWWWW",String.valueOf(width));
            Log.i("WWWWWW",String.valueOf(heigh));
            Picasso.get().load("file://"+eyPath2).resize(width/3,heigh/3).into(WaitVeyeP2);
        }else{
            WaitVeyeP2.setVisibility(View.GONE);
        }
        if(eyPath3 != null) {
            WaitVeyeP3.setImageURI(Uri.parse(eyPath3));
            int width = WaitVeyeP3.getDrawable().getIntrinsicWidth();
            int heigh = WaitVeyeP3.getDrawable().getIntrinsicHeight();
            Log.i("WWWWWW",String.valueOf(width));
            Log.i("WWWWWW",String.valueOf(heigh));
            Picasso.get().load("file://"+eyPath3).resize(width/3,heigh/3).into(WaitVeyeP3);
        }else{
            WaitVeyeP3.setVisibility(View.GONE);
        }
        if(eyPath4 != null) {
            WaitVeyeP4.setImageURI(Uri.parse(eyPath4));
            int width = WaitVeyeP4.getDrawable().getIntrinsicWidth();
            int heigh = WaitVeyeP4.getDrawable().getIntrinsicHeight();
            Log.i("WWWWWW",String.valueOf(width));
            Log.i("WWWWWW",String.valueOf(heigh));
            Picasso.get().load("file://"+eyPath4).resize(width/3,heigh/3).into(WaitVeyeP4);
        }else{
            WaitVeyeP4.setVisibility(View.GONE);
        }
        if(eyPath5 != null) {
            WaitVeyeP5.setImageURI(Uri.parse(eyPath5));
            int width = WaitVeyeP5.getDrawable().getIntrinsicWidth();
            int heigh = WaitVeyeP5.getDrawable().getIntrinsicHeight();
            Log.i("WWWWWW",String.valueOf(width));
            Log.i("WWWWWW",String.valueOf(heigh));
            Picasso.get().load("file://"+eyPath5).resize(width/3,heigh/3).into(WaitVeyeP5);
        }else{
            WaitVeyeP5.setVisibility(View.GONE);
        }
        if(eyPath6 != null) {
            WaitVeyeP6.setImageURI(Uri.parse(eyPath6));
            int width = WaitVeyeP6.getDrawable().getIntrinsicWidth();
            int heigh = WaitVeyeP6.getDrawable().getIntrinsicHeight();
            Log.i("WWWWWW",String.valueOf(width));
            Log.i("WWWWWW",String.valueOf(heigh));
            Picasso.get().load("file://"+eyPath6).resize(width/3,heigh/3).into(WaitVeyeP6);
        }else{
            WaitVeyeP6.setVisibility(View.GONE);
        }
        if(eyPath7 != null) {
            WaitVeyeP7.setImageURI(Uri.parse(eyPath7));
            int width = WaitVeyeP7.getDrawable().getIntrinsicWidth();
            int heigh = WaitVeyeP7.getDrawable().getIntrinsicHeight();
            Log.i("WWWWWW",String.valueOf(width));
            Log.i("WWWWWW",String.valueOf(heigh));
            Picasso.get().load("file://"+eyPath7).resize(width/3,heigh/3).into(WaitVeyeP7);
        }else{
            WaitVeyeP7.setVisibility(View.GONE);
        }
        if(eyPath8 != null) {
            WaitVeyeP8.setImageURI(Uri.parse(eyPath8));
            int width = WaitVeyeP8.getDrawable().getIntrinsicWidth();
            int heigh = WaitVeyeP8.getDrawable().getIntrinsicHeight();
            Log.i("WWWWWW",String.valueOf(width));
            Log.i("WWWWWW",String.valueOf(heigh));
            Picasso.get().load("file://"+eyPath8).resize(width/3,heigh/3).into(WaitVeyeP8);
        }else{
            WaitVeyeP8.setVisibility(View.GONE);
        }

        Leye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_viewphot.putExtra("Image_path",LeyePath);
                startActivity(intent_viewphot);
            }
        });

        Reye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_viewphot.putExtra("Image_path",ReyePath);
                startActivity(intent_viewphot);
            }
        });

        //==============================================================

        WaitVeyeP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_viewphot.putExtra("Image_path",eyPath1);
                startActivity(intent_viewphot);
            }
        });

        WaitVeyeP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_viewphot.putExtra("Image_path",eyPath2);
                startActivity(intent_viewphot);
            }
        });

        WaitVeyeP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_viewphot.putExtra("Image_path",eyPath3);
                startActivity(intent_viewphot);
            }
        });

        WaitVeyeP4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_viewphot.putExtra("Image_path",eyPath4);
                startActivity(intent_viewphot);
            }
        });

        WaitVeyeP5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_viewphot.putExtra("Image_path",eyPath5);
                startActivity(intent_viewphot);
            }
        });

        WaitVeyeP6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_viewphot.putExtra("Image_path",eyPath6);
                startActivity(intent_viewphot);
            }
        });

        WaitVeyeP7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_viewphot.putExtra("Image_path",eyPath7);
                startActivity(intent_viewphot);
            }
        });

        WaitVeyeP8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_viewphot.putExtra("Image_path",eyPath8);
                startActivity(intent_viewphot);
            }
        });


        //===============================================================




        pname.setText(pName);
        pid.setText( pID );
        pbir.setText( pBir );
        pgender.setText( pGender );
        pphone.setText( pNum );
        checkTime.setText(eXT);
        page = pAge;

        Button enter = (Button) findViewById(R.id.printer);
        enter.setOnClickListener(nextenter);

        Button view = (Button) findViewById(R.id.viewimage);
        view.setOnClickListener(nextview);

        Button cancelWaitviewimg = (Button) findViewById(R.id.cancelWaitviewimg);
        cancelWaitviewimg.setOnClickListener(nextcancel);



        ArrayAdapter<CharSequence> adapterBalls = ArrayAdapter.createFromResource(
                this, R.array.Branch,android.R.layout.simple_spinner_item);
        // 設定Spinner顯示的格式
        adapterBalls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 設定Spinner的資料來源

        // 設定 spnPrefer 元件 ItemSelected 事件的 listener 為  spnPreferListener



        // Start out viewing the list.    return time
        viewingList = true;
        try {

        }catch (Exception e){
            Log.e("ERROR", "ERROR: " + e.toString());
            e.printStackTrace();
        }
        updateHandler = new Handler(); // return time


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
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //===============================================================================================

    private Spinner.OnItemSelectedListener spnPreferListener = new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            String sel=parent.getSelectedItem().toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    };
    //return time   Start
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            viewingList = true;
        }
        else {
            viewingList = false;
        }
    }
    public boolean checkIfListView() {
        // Check if user is viewing a content list
        if(viewingList) {
            return true;
        }
        return false;
    }
    //return time   End


    public void refreshImageList(){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAdapter.notifyDataSetChanged();
            }
        });
    }




    //點選Buttom動作(確認鍵)
    private Button.OnClickListener nextenter = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!Button_Click.isFastDoubleClick()) {
                if( savedRadioIndex == 0){

                    //轉跳至輸出報告頁
                    Intent intent = new Intent( Viewwait_patientInfo.this, Viewwait_chooseimg_onlyimg.class );
                    intent.putExtra("a",pname.getText());
                    intent.putExtra("b",pid.getText());
                    intent.putExtra("c",pbir.getText());
                    intent.putExtra("d",pgender.getText());
                    intent.putExtra("e",pphone.getText());
                    intent.putExtra("f",checkTime.getText());

                    intent.putExtra("age",page);


                    //宣告一個Bundle代數
                    Bundle bundle = new Bundle(  );

                    //取得各個物件

                    //將診斷頁面的資料傳遞至書出報告，並將每個物件資料設定一個ID

                    //性別
                    String stringrad ="";



                    //讓intent抓取bundle的資料(否則會為空值)
                    intent.putExtra( "name",  bundle);
                    intent.putExtra( "number",bundle );
                    intent.putExtra( "phone",bundle );
                    intent.putExtra( "birthday",bundle );
                    intent.putExtra( "time",bundle );
                    intent.putExtra( "spinner",bundle );
                    intent.putExtra( "radgroup",bundle );
                    startActivity( intent );
                    finish();
                }
                else if(savedRadioIndex == 1){

                    //轉跳至輸出報告頁
                    Intent intent = new Intent( Viewwait_patientInfo.this, Viewwait_chooseimg_onlyimg.class );
                    intent.putExtra("a",pname.getText());
                    intent.putExtra("b",pid.getText());
                    intent.putExtra("c",pbir.getText());
                    intent.putExtra("d",pgender.getText());
                    intent.putExtra("e",pphone.getText());
                    intent.putExtra("f",checkTime.getText());

                    intent.putExtra("age",page);


                    //宣告一個Bundle代數
                    Bundle bundle = new Bundle(  );

                    //取得各個物件

                    //將診斷頁面的資料傳遞至書出報告，並將每個物件資料設定一個ID

                    //性別
                    String stringrad ="";



                    //讓intent抓取bundle的資料(否則會為空值)
                    intent.putExtra( "name",  bundle);
                    intent.putExtra( "number",bundle );
                    intent.putExtra( "phone",bundle );
                    intent.putExtra( "birthday",bundle );
                    intent.putExtra( "time",bundle );
                    intent.putExtra( "spinner",bundle );
                    intent.putExtra( "radgroup",bundle );
                    startActivity( intent );
                    finish();

                }
                else if(savedRadioIndex == 2){
                    printWifi();
                    //轉跳至輸出報告頁
                    Intent intent = new Intent( Viewwait_patientInfo.this, Viewwait_chooseimg_onlyimg.class );
                    intent.putExtra("a",pname.getText());
                    intent.putExtra("b",pid.getText());
                    intent.putExtra("c",pbir.getText());
                    intent.putExtra("d",pgender.getText());
                    intent.putExtra("e",pphone.getText());
                    intent.putExtra("f",checkTime.getText());

                    intent.putExtra("age",page);


                    //宣告一個Bundle代數
                    Bundle bundle = new Bundle(  );

                    //取得各個物件

                    //將診斷頁面的資料傳遞至書出報告，並將每個物件資料設定一個ID

                    //性別
                    String stringrad ="";



                    //讓intent抓取bundle的資料(否則會為空值)
                    intent.putExtra( "name",  bundle);
                    intent.putExtra( "number",bundle );
                    intent.putExtra( "phone",bundle );
                    intent.putExtra( "birthday",bundle );
                    intent.putExtra( "time",bundle );
                    intent.putExtra( "spinner",bundle );
                    intent.putExtra( "radgroup",bundle );
                    startActivity( intent );
                    finish();
                }
            }
        }
    };

    private Button.OnClickListener nextview = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!Button_Click.isFastDoubleClick()) {
                if( savedRadioIndex == 0){

                    Intent intent = new Intent( Viewwait_patientInfo.this, Viewwait_viewimage.class );


                    intent.putExtra("a",pname.getText());
                    intent.putExtra("b",pid.getText());
                    intent.putExtra("c",pbir.getText());
                    intent.putExtra("d",pgender.getText());
                    intent.putExtra("e",pphone.getText());
                    intent.putExtra("f",checkTime.getText());
                    intent.putExtra("g",LeyePath);
                    intent.putExtra("h",ReyePath);

                    intent.putExtra("wvEpathA1",eyPath1);
                    intent.putExtra("wvEpathA2",eyPath2);
                    intent.putExtra("wvEpathA3",eyPath3);
                    intent.putExtra("wvEpathA4",eyPath4);
                    intent.putExtra("wvEpathA5",eyPath5);
                    intent.putExtra("wvEpathA6",eyPath6);
                    intent.putExtra("wvEpathA7",eyPath7);
                    intent.putExtra("wvEpathA8",eyPath8);

                    intent.putExtra("age",page);


                    Log.i("LLLLPPPP",String.valueOf(LeyePath));
                    Log.i("RRRPPPP",String.valueOf(ReyePath));



                    //宣告一個Bundle代數
                    Bundle bundle = new Bundle(  );

                    startActivity( intent );


                }
                else if(savedRadioIndex == 1){

                    Intent intent = new Intent( Viewwait_patientInfo.this, Viewwait_viewimage.class );


                    intent.putExtra("a",pname.getText());
                    intent.putExtra("b",pid.getText());
                    intent.putExtra("c",pbir.getText());
                    intent.putExtra("d",pgender.getText());
                    intent.putExtra("e",pphone.getText());
                    intent.putExtra("f",checkTime.getText());
                    intent.putExtra("g",LeyePath);
                    intent.putExtra("h",ReyePath);

                    intent.putExtra("wvEpathA1",eyPath1);
                    intent.putExtra("wvEpathA2",eyPath2);
                    intent.putExtra("wvEpathA3",eyPath3);
                    intent.putExtra("wvEpathA4",eyPath4);
                    intent.putExtra("wvEpathA5",eyPath5);
                    intent.putExtra("wvEpathA6",eyPath6);
                    intent.putExtra("wvEpathA7",eyPath7);
                    intent.putExtra("wvEpathA8",eyPath8);

                    intent.putExtra("age",page);


                    Log.i("LLLLPPPP",String.valueOf(LeyePath));
                    Log.i("RRRPPPP",String.valueOf(ReyePath));



                    //宣告一個Bundle代數
                    Bundle bundle = new Bundle(  );

                    startActivity( intent );


                }
                else if(savedRadioIndex == 2){
                    aiWifi();
                    Intent intent = new Intent( Viewwait_patientInfo.this, Viewwait_viewimage.class );


                    intent.putExtra("a",pname.getText());
                    intent.putExtra("b",pid.getText());
                    intent.putExtra("c",pbir.getText());
                    intent.putExtra("d",pgender.getText());
                    intent.putExtra("e",pphone.getText());
                    intent.putExtra("f",checkTime.getText());
                    intent.putExtra("g",LeyePath);
                    intent.putExtra("h",ReyePath);

                    intent.putExtra("wvEpathA1",eyPath1);
                    intent.putExtra("wvEpathA2",eyPath2);
                    intent.putExtra("wvEpathA3",eyPath3);
                    intent.putExtra("wvEpathA4",eyPath4);
                    intent.putExtra("wvEpathA5",eyPath5);
                    intent.putExtra("wvEpathA6",eyPath6);
                    intent.putExtra("wvEpathA7",eyPath7);
                    intent.putExtra("wvEpathA8",eyPath8);

                    intent.putExtra("age",page);


                    Log.i("LLLLPPPP",String.valueOf(LeyePath));
                    Log.i("RRRPPPP",String.valueOf(ReyePath));



                    //宣告一個Bundle代數
                    Bundle bundle = new Bundle(  );

                    startActivity( intent );

                }
            }
        }
    };


    private Button.OnClickListener nextcancel = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
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
    };

    public void onBackPressed() {
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

}