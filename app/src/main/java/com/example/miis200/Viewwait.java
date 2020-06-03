package com.example.miis200;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Viewwait extends AppCompatActivity {

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



    DatabaseHelper myDB;
    ArrayList<User> userList;
    ListView listView;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        loadData();
        updateViews();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewwait);

        //===============================================================================================
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Viewwait_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //===============================================================================================


        myDB = new DatabaseHelper(this);

        userList = new ArrayList<>();
        Cursor data = myDB.getListContents1();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(Viewwait.this,R.string.Printerwait_not_client_wait_view,Toast.LENGTH_SHORT).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                user = new User(data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5),data.getString(6),data.getString(7),data.getString(8),data.getString(9),data.getString(10),data.getString(11),data.getString(12),data.getString(13),data.getString(14),data.getString(15),data.getString(16),data.getString(17),data.getString(18),data.getString(19)
                        ,data.getString(20),data.getString(21),data.getString(22),data.getString(23),data.getString(24),data.getString(25),data.getString(26),data.getString(27),data.getString(28),data.getString(29),data.getString(30),data.getString(31),data.getString(32),data.getString(33),data.getString(34),data.getString(35));
                userList.add(i,user);
                System.out.println(data.getString(1)+" "+data.getString(2)+" "+data.getString(3)+" "+data.getString(4)+" "+data.getString(5)+" "+data.getString(6)+" "+data.getString(7)+" "+data.getString(8)+" "+data.getString(9)+" "+data.getString(10)+" "+data.getString(11)+" "+data.getString(12)+" "+data.getString(13)+" "+data.getString(14)+" "+data.getString(15)+" "+data.getString(16)+" "+data.getString(17)+" "+data.getString(18)+" "+data.getString(19));
                System.out.println(userList.get(i).getPatientName());
                i++;
            }
            ThreeColumn_ListAdapter adapter =  new ThreeColumn_ListAdapter(this,R.layout.list_adapter_view, userList);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long j) {

                    if (!Button_Click.isFastDoubleClick()) {

                        Log.i("ZZZZZZZZZZZZZZZZZ",String.valueOf(i));
                        Intent intent = new Intent(Viewwait.this, Viewwait_patientInfo.class);
                        intent.putExtra("a",userList.get(i).getPatientName());
                        intent.putExtra("b",userList.get(i).getPatientID());
                        intent.putExtra("c",userList.get(i).getPatientBir());
                        intent.putExtra("d",userList.get(i).getPatientGender());
                        intent.putExtra("e",userList.get(i).getPatientPnum());
                        intent.putExtra("f",userList.get(i).getPeyeLpath());
                        intent.putExtra("g",userList.get(i).getPeyeRpath());
                        intent.putExtra("h",userList.get(i).getPatientStatus());
                        intent.putExtra("Ext",userList.get(i).getExTime());

                        intent.putExtra("wvEpath1",userList.get(i).getEyePath1());
                        intent.putExtra("wvEpath2",userList.get(i).getEyePath2());
                        intent.putExtra("wvEpath3",userList.get(i).getEyePath3());
                        intent.putExtra("wvEpath4",userList.get(i).getEyePath4());
                        intent.putExtra("wvEpath5",userList.get(i).getEyePath5());
                        intent.putExtra("wvEpath6",userList.get(i).getEyePath6());
                        intent.putExtra("wvEpath7",userList.get(i).getEyePath7());
                        intent.putExtra("wvEpath8",userList.get(i).getEyePath8());

                        intent.putExtra("age",userList.get(i).getPatientAge());


                        startActivity(intent);
                        finish();
                    }
                }
            });
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
                Intent intent = new Intent(getApplicationContext(), Option.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //===============================================================================================

    public void onBackPressed(){
        finish();
    }

}
