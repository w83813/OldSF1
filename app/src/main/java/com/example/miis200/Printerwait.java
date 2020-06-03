package com.example.miis200;

import android.content.Intent;
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

import com.example.miis200.utils.WifiUtil;

import java.util.ArrayList;

public class Printerwait extends AppCompatActivity {
    private String printName = "MiiS-OA";
    private String printPwd = "vacationoa";

    private void printWifi() {
        WifiUtil.getIns().init(getApplicationContext());
        WifiUtil.getIns().changeToWifi(printName, printPwd);
    }


    DatabaseHelper myDB;
    ArrayList<User> userList;
    ListView listView;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        printWifi();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printerwait);


        //===============================================================================================
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Printerwait_title);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //===============================================================================================

        myDB = new DatabaseHelper(this);

        userList = new ArrayList<>();
        Cursor data = myDB.getListContents2();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(Printerwait.this,R.string.Printerwait_not_client_wait_printer,Toast.LENGTH_LONG).show();
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
                        Intent intent = new Intent(Printerwait.this, Printerwait_chooseimg_Report.class);
                        intent.putExtra("a",userList.get(i).getPatientName());
                        intent.putExtra("b",userList.get(i).getPatientID());
                        startActivity(intent);
                    }

                }
            });
        }
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
}
