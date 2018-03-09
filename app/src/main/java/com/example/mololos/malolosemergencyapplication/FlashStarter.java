package com.example.mololos.malolosemergencyapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class FlashStarter extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_starter);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        timer();

        addPermissionRequestSendSMS();
        addPermissionRequestWriteExternal();
        addPermissionRequestLocation();

        if (addPermissionRequestSendSMS() && addPermissionRequestWriteExternal() && addPermissionRequestLocation())
        {
        }
    }

    //ask for Write External
    public boolean addPermissionRequestWriteExternal()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(FlashStarter.this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(FlashStarter.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
            else
            {
                ActivityCompat.requestPermissions(FlashStarter.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
            return false;
        }
        else
        {
            addPermissionRequestLocation();
            return true;
        }
    }

    //ask for sms permission
    public boolean addPermissionRequestSendSMS()
    {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED))
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(FlashStarter.this,Manifest.permission.SEND_SMS))
            {
                ActivityCompat.requestPermissions(FlashStarter.this,new String[]{Manifest.permission.SEND_SMS}, 1);
            }
            else
            {
                ActivityCompat.requestPermissions(FlashStarter.this,new String[]{Manifest.permission.SEND_SMS}, 1);
            }
            return false;
        }
        else
        {
            addPermissionRequestWriteExternal();
            return true;
        }
    }

    //ask for location permission
    public boolean addPermissionRequestLocation()
    {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.LOCATION_HARDWARE) != PackageManager.PERMISSION_GRANTED))
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(FlashStarter.this,Manifest.permission.LOCATION_HARDWARE))
            {
                ActivityCompat.requestPermissions(FlashStarter.this,new String[]{Manifest.permission.LOCATION_HARDWARE}, 3);
            }
            else
            {
                ActivityCompat.requestPermissions(FlashStarter.this,new String[]{Manifest.permission.LOCATION_HARDWARE}, 3);
            }
            return false;
        }
        else
        {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(FlashStarter.this,Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this, "Send SMS Permission Granted!", Toast.LENGTH_SHORT).show();
                        addPermissionRequestWriteExternal();
                    }
                    else
                    {
                        Toast.makeText(this, "No Permission Granted!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
            case 2:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(FlashStarter.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this, "Write External Storage Permission Granted!", Toast.LENGTH_SHORT).show();
                        timer();
                        addPermissionRequestLocation();
                    }
                    else
                    {
                        Toast.makeText(this, "No Permission Granted!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
            case 3:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(FlashStarter.this,Manifest.permission.LOCATION_HARDWARE) == PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this, "Location Hardware Permission Granted!", Toast.LENGTH_SHORT).show();
                        timer();
                        addPermissionRequestLocation();
                    }
                    else
                    {
                        Toast.makeText(this, "No Permission Granted!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    }

    public void timer()
    {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Cursor user_info = databaseHelper.getUserInfo();
                Intent mainIntent = null;

                if(user_info.getCount() > 0)
                {
                    mainIntent = new Intent(FlashStarter.this,EmergencyTypeMenu.class);
                }
                else
                {
                    mainIntent = new Intent(FlashStarter.this,RegistrationActivity.class);
                }


                FlashStarter.this.startActivity(mainIntent);
                FlashStarter.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);


    }
}
