package com.example.miis200;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPatient extends AppCompatActivity {

    EditText etFirstName,etLastName,etFavFood;
    RadioButton rbMan,rbWomen;
    Button btnAdd,btnView;
    DatabaseHelper myDB;
    ArrayList<User> userList;
    private EditText etBir;
    EditText etPstatus,etPeyeL,etPeyeR,etDdesc;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    TextView dateBirthday;
    String Age;
    Button Cancel;
    String Cname = " ",Cid,Cnum,Cbir;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpatient);

        //===============================================================================================
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("新增客戶");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //===============================================================================================


        etFavFood = (EditText) findViewById(R.id.etFavFood);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        dateBirthday = (TextView) findViewById( R.id.dateBirthday );
        dateBirthday.setClickable(true);

        Cid = etFirstName.getText().toString();
        Cnum = etLastName.getText().toString();
        Cbir = dateBirthday.getText().toString();
        Log.i("wwwww",Cname);




        dateBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                closekeyboard();
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // DatePickerDialog  Max Day is today
                Calendar maxDate = Calendar.getInstance();
                maxDate.set(year, month, dayOfMonth);
                // DatePickerDialog  Min Day -150 year
                Calendar minDate = Calendar.getInstance();
                minDate.set(year - 150, month, dayOfMonth);


                datePickerDialog = new DatePickerDialog(AddPatient.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        SimpleDateFormat format = new SimpleDateFormat("yyyy");
                        int NowYear = Integer.parseInt(format.format(new Date()));
                        Age = ( (NowYear - year) + "歲");


                        dateBirthday.setText( year + "年" + (month + 1) + "月" +dayOfMonth + "日" );
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
                //  Set  Max、Min Day
                final DatePicker datePicker = datePickerDialog.getDatePicker();
                datePicker.setMinDate(minDate.getTimeInMillis());
                datePicker.setMaxDate(maxDate.getTimeInMillis());
            }
        });



        rbMan = (RadioButton) findViewById(R.id.radioMan);
        rbWomen = (RadioButton) findViewById(R.id.radioWomen);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        Cancel = (Button) findViewById(R.id.cancel);
        myDB = new DatabaseHelper(this);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("cccccccc",Cname);
                String pName = etFirstName.getText().toString();
                String pId = etLastName.getText().toString();
                String pPnum = etFavFood.getText().toString();
                String pGender=rbMan.isChecked()?"男":"女";
                String pBir = dateBirthday.getText().toString();


                if(pName.length() == 0 && pId.length() == 0 && pPnum.length() == 0 && pBir.length() == 0){
                    finish();
                } else{
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(AddPatient.this);
                    a_builder.setMessage(R.string.Cancel_message)
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
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



            }
        });





        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Button_Click.isFastDoubleClick()) {
                    userList = new ArrayList<>();
                    SQLiteDatabase idDB = myDB.getReadableDatabase();




                    String pName = etFirstName.getText().toString();
                    String pId = etLastName.getText().toString();
                    String pPnum = etFavFood.getText().toString();
                    String pGender=rbMan.isChecked()?"男":"女";
                    String pBir = dateBirthday.getText().toString();
                    String eyeLpath = " ";
                    String eyeRpath = " ";
                    String pStatus = "0";
                    String dDesc = " ";
                    String eXtime = " ";
                    String ePath1 = " ";
                    String ePath2 = " ";
                    String ePath3 = " ";
                    String ePath4 = " ";
                    String ePath5 = " ";
                    String ePath6 = " ";
                    String ePath7 = " ";
                    String ePath8 = " ";
                    String pAge = Age;
                    String aImg1 = " ";
                    String aImg2 = " ";
                    String aImg3 = " ";
                    String aImg4 = " ";
                    String aImg5 = " ";
                    String aImg6 = " ";
                    String aImg7 = " ";
                    String aImg8 = " ";
                    String aImg9 = " ";
                    String aImg10 = " ";
                    String aImg11 = " ";
                    String aImg12 = " ";
                    String aImg13 = " ";
                    String aImg14 = " ";
                    String aImg15 = " ";
                    String aImg16 = " ";

                    String dDesc2 = " ";
                    String dDesc3 = " ";
                    String dDesc4 = " ";
                    String dDesc5 = " ";
                    String dDesc6 = " ";
                    String dDesc7 = " ";
                    String dDesc8 = " ";
                    String dDesc9 = " ";
                    String dDesc10 = " ";
                    String dDesc11 = " ";
                    String dDesc12 = " ";
                    String dDesc13 = " ";
                    String dDesc14 = " ";
                    String dDesc15 = " ";
                    String dDesc16 = " ";
                    String dDesc17 = " ";
                    String dDesc18 = " ";
                    String dDesc19 = " ";
                    String dDesc20 = " ";
                    String dDesc21 = " ";
                    String dDesc22 = " ";
                    String dDesc23 = " ";
                    String dDesc24 = " ";
                    String dDesc25 = " ";
                    String dDesc26 = " ";



                    Cursor cursor = idDB.rawQuery("select * from users_data where PATIENTID=? ", new String[]{pId});
                    int checkID = cursor.getCount();;
                    Log.i("sadasdasdasdasdasd",String.valueOf(checkID));


                    if(checkID > 0){
                        Toast.makeText(AddPatient.this,R.string.AddPatient_this_patient_unfinish,Toast.LENGTH_LONG).show();
                    }
                    else if(pName.length() != 0 && pId.length() != 0 && pPnum.length() != 0 && pBir.length() != 0 && pGender.length() != 0){
                        AddData(pName,pId, pPnum,pGender,pBir,eyeLpath,eyeRpath, pStatus, dDesc,eXtime ,ePath1 ,ePath2 , ePath3 ,ePath4 ,ePath5 ,ePath6 ,ePath7 ,ePath8,pAge,
                                aImg1,aImg2,aImg3,aImg4,aImg5,aImg6,aImg7,aImg8,aImg9,aImg10,aImg11,aImg12,aImg13,aImg14,aImg15,aImg16,dDesc2,dDesc3,dDesc4,dDesc5,dDesc6,dDesc7,dDesc8,
                                dDesc9,dDesc10,dDesc11,dDesc12,dDesc13,dDesc14,dDesc15,dDesc16,dDesc17,dDesc18,dDesc19,dDesc20,dDesc21,dDesc22,dDesc23,dDesc24,dDesc25,dDesc26);
                        etFavFood.setText("");
                        etLastName.setText("");
                        etFirstName.setText("");
                        rbMan.setText("");
                        dateBirthday.setText("");
                        Toast.makeText(AddPatient.this,R.string.Add + pName + R.string.Success,Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(AddPatient.this,R.string.AddPatient_info_incomplete,Toast.LENGTH_LONG).show();
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
                Log.i("cccccccc",Cname);
                String pName = etFirstName.getText().toString();
                String pId = etLastName.getText().toString();
                String pPnum = etFavFood.getText().toString();
                String pGender=rbMan.isChecked()?"男":"女";
                String pBir = dateBirthday.getText().toString();


                if(pName.length() == 0 && pId.length() == 0 && pPnum.length() == 0 && pBir.length() == 0){
                    startActivity(new Intent(AddPatient.this, Option.class));
                } else{
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(AddPatient.this);
                    a_builder.setMessage(R.string.Go_home_message)
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
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
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //===============================================================================================

    public void AddData(String patientName,String patientID, String patientPnum , String patientGender, String patientBir, String eyeLpath, String eyeRpath, String pStatus,String dDesc,String eXtime,
                        String ePath1,String ePath2,String ePath3,String ePath4,String ePath5,String ePath6,String ePath7,String ePath8,String patientAge,
                        String aIimg1,String aIimg2,String aIimg3,String aIimg4,String aIimg5,String aIimg6,String aIimg7,String aIimg8,String aIimg9,String aIimg10,
                        String aIimg11,String aIimg12,String aIimg13,String aIimg14,String aIimg15,String aIimg16,String dDesc2,String dDesc3,String dDesc4,String dDesc5,String dDesc6,String dDesc7,String dDesc8,
                        String dDesc9,String dDesc10,String dDesc11,String dDesc12,String dDesc13,String dDesc14,String dDesc15,String dDesc16,String dDesc17,String dDesc18,String dDesc19,String dDesc20,String dDesc21,
                        String dDesc22,String dDesc23,String dDesc24,String dDesc25,String dDesc26){
        boolean insertData = myDB.addData(patientName,patientID,patientPnum,patientGender,patientBir,eyeLpath,eyeRpath,pStatus,dDesc,eXtime,ePath1,ePath2,ePath3,ePath4,ePath5,ePath6,ePath7,ePath8,patientAge,
                aIimg1,aIimg2,aIimg3,aIimg4,aIimg5,aIimg6,aIimg7,aIimg8,aIimg9,aIimg10,aIimg11,aIimg12,aIimg13,aIimg14,aIimg15,aIimg16,dDesc2,dDesc3,dDesc4,dDesc5,dDesc6,dDesc7,dDesc8,dDesc9,dDesc10,dDesc11,dDesc12,dDesc13,
                dDesc14, dDesc15,dDesc16,dDesc17,dDesc18,dDesc19,dDesc20,dDesc21,dDesc22,dDesc23,dDesc24,dDesc25,dDesc26);

        if(insertData ==false){
            Toast.makeText(AddPatient.this,"Something went wrong :(.",Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        Log.i("cccccccc",Cname);
        String pName = etFirstName.getText().toString();
        String pId = etLastName.getText().toString();
        String pPnum = etFavFood.getText().toString();
        String pGender=rbMan.isChecked()?"男":"女";
        String pBir = dateBirthday.getText().toString();


        if(pName.length() == 0 && pId.length() == 0 && pPnum.length() == 0 && pBir.length() == 0){
            finish();
        } else{
            AlertDialog.Builder a_builder = new AlertDialog.Builder(AddPatient.this);
            a_builder.setMessage(R.string.Back_message)
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }) ;
            AlertDialog alert = a_builder.create();
            alert.setTitle(R.string.Back_title);
            alert.show();
        }



    }

    private  void closekeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


}