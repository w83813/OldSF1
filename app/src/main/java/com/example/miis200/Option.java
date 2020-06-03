package com.example.miis200;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.miis200.utils.WifiUtil;

import java.util.ArrayList;
import java.util.List;

public class Option extends AppCompatActivity {

    public static final String TAG = "Option";

    WifiManager wifiManager;
    List scannedResult;

    LinearLayout addexamination,addpatient,WaitVimg,WaitPrinter,PatientPDFlist,Setting;
    DatabaseHelper myDB;


    private String aimWifiName = "flashair_ec21e54ca69e";
    private String aimWifiPwd = "12345678";

    private void changeWifi2() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(aimWifiName, aimWifiPwd);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("選單");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        addexamination = (LinearLayout) findViewById(R.id.addexamination);
        addpatient = (LinearLayout) findViewById(R.id.addpatient);
        WaitVimg = (LinearLayout) findViewById(R.id.BtnWaitVimg);
        WaitPrinter = (LinearLayout) findViewById(R.id.BtnWaitPrinter);
        PatientPDFlist = (LinearLayout) findViewById(R.id.patientpdflist);
        Setting = (LinearLayout) findViewById(R.id.setting);
        myDB = new DatabaseHelper(this);
        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        scannedResult = new ArrayList();




        addexamination.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(final View view) {
                if (!Button_Click.isFastDoubleClick()) {
                    Intent intent = new Intent(Option.this, Addexamination.class);
                    startActivity(intent);
                }
            }
        });

        addpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    Intent intent = new Intent(Option.this, AddPatient.class);
                    startActivity(intent);
                }
            }
        });

        WaitVimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    Intent intent = new Intent(Option.this, Viewwait.class);
                    startActivity(intent);
                }
            }
        });

        WaitPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    Intent intent = new Intent(Option.this, Printerwait.class);
                    startActivity(intent);
                }
            }
        });

        PatientPDFlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    Intent intent = new Intent(Option.this, Review.class);
                    startActivity(intent);
                }
            }
        });

        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Button_Click.isFastDoubleClick()) {
                    Intent intent = new Intent(Option.this, Modesetting.class);
                    startActivity(intent);
                }
            }
        });




    }

    public void AddData(String patientName,String patientID, String patientPnum , String patientGender, String patientBir, String eyeLpath, String eyeRpath, String pStatus,String dDesc,String eXtime,
                        String ePath1,String ePath2,String ePath3,String ePath4,String ePath5,String ePath6,String ePath7,String ePath8,String patientAge,
                        String aIimg1,String aIimg2,String aIimg3,String aIimg4,String aIimg5,String aIimg6,String aIimg7,String aIimg8,String aIimg9,String aIimg10,
                        String aIimg11,String aIimg12,String aIimg13,String aIimg14,String aIimg15,String aIimg16,String dDesc2,String dDesc3,String dDesc4,String dDesc5,String dDesc6,String dDesc7,String dDesc8,
                        String dDesc9,String dDesc10,String dDesc11,String dDesc12,String dDesc13,String dDesc14,String dDesc15,String dDesc16,String dDesc17,String dDesc18,String dDesc19,String dDesc20,String dDesc21,
                        String dDesc22,String dDesc23,String dDesc24,String dDesc25,String dDesc26){
        boolean insertData = myDB.addData(patientName,patientID,patientPnum,patientGender,patientBir,eyeLpath,eyeRpath,pStatus,dDesc,eXtime,ePath1,ePath2,ePath3,ePath4,ePath5,ePath6,ePath7,ePath8,patientAge,
                aIimg1,aIimg2,aIimg3,aIimg4,aIimg5,aIimg6,aIimg7,aIimg8,aIimg9,aIimg10,aIimg11,aIimg12,aIimg13,aIimg14,aIimg15,aIimg16,dDesc2,dDesc3,dDesc4,dDesc5,dDesc6,dDesc7,dDesc8,dDesc9,dDesc10,dDesc11,dDesc12,dDesc13,
                dDesc14, dDesc15,dDesc16,dDesc17,dDesc18,dDesc19,dDesc20,dDesc21,dDesc22,dDesc23,dDesc24,dDesc25,dDesc26);

        if(insertData==true){
            Toast.makeText(Option.this,"Successfully Entered Data!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(Option.this,"Something went wrong :(.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Option.this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(R.string.Exit)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

}