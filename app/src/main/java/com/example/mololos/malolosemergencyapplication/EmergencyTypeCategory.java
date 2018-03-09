package com.example.mololos.malolosemergencyapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class EmergencyTypeCategory extends AppCompatActivity implements View.OnClickListener {
    String emergencyType;
    LinearLayout crimeLayout, fireLayout, rescueLayout, medicalLayout;
    Button btnHeartAttack,btnStroke,btnPoisoning,btnAnimeBites,btnFractureFalling,btnOBCases,btnVehicularAccident,btnOtherMedical;
    Button btnDrowning,btnSuicideAttempt,btnFloodVictims,btnEarthquakeVictims,btnTyphoonVictims,btnOtherSearchRescue;
    Button btnResidentialFire,btnCommercialFire,btnWildfire,btnSceneAccident,btnOtherFire;
    Button btnKidnapHostage,btnRobberyHoldup,btnMurderHomicide,btnRapeMolest,btnSceneAccidentCrime,btnOtherCrime;
    TextView textViewEmergencyType;

    String longhitude, latitude;

    Bitmap mImageBitmap = null;
    String mCurrentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_type_category);

        initialized_views();
        locationUpdate(); //update location
        gettingExtraData();
        broadcast();

    }

    @Override
    public void onClick(View v)
    {
        String selectedCategory = ((Button)v).getText().toString();

        if (checkAllRequirements() && (checkMobileDataConnection() || checkWifiState()))
        {
            askUserTakePicture();
            Toast.makeText(this, longhitude+",  "+latitude, Toast.LENGTH_LONG).show();
        }
        else
        {
            send_message(" Requesting for " + selectedCategory +" "+DataHolder.emergencyType + " Emergency Response", DataHolder.number);
            Toast.makeText(this, "text sent", Toast.LENGTH_SHORT).show();
        }
    }


    public void gettingExtraData()
    {
        emergencyType = DataHolder.emergencyType;
        switch (emergencyType)
        {
            case "Fire" :
                fireLayout.setVisibility(View.VISIBLE);
                textViewEmergencyType.setText("FIRE EMERGENCY");
                break;
            case "Medical" :
                medicalLayout.setVisibility(View.VISIBLE);
                textViewEmergencyType.setText("MEDICAL EMERGENCY");
                break;
            case "Crime" :
                crimeLayout.setVisibility(View.VISIBLE);
                textViewEmergencyType.setText("CRIME EMERGENCY");
                break;
            case "Rescue" :
                rescueLayout.setVisibility(View.VISIBLE);
                textViewEmergencyType.setText("RESCUE AND SEARCH EMERGENCY");
                break;
        }
    }


    public void initialized_views()
    {
        medicalLayout           = findViewById(R.id.medicalLayout);
        crimeLayout             = findViewById(R.id.crimeLayout);
        rescueLayout            = findViewById(R.id.rescueLayout);
        fireLayout              = findViewById(R.id.fireLayout);
        textViewEmergencyType   = findViewById(R.id.textViewEmergencyType);

        btnHeartAttack          = findViewById(R.id.buttonHeartAttack);
        btnStroke               = findViewById(R.id.buttonStroke);
        btnPoisoning            = findViewById(R.id.buttonPoisoning);
        btnAnimeBites           = findViewById(R.id.buttonAnimalBites);
        btnFractureFalling      = findViewById(R.id.buttonFracture);
        btnOBCases              = findViewById(R.id.buttonOBCases);
        btnVehicularAccident    = findViewById(R.id.buttonVehicularAccident);
        btnOtherMedical         = findViewById(R.id.buttonOtherMedical);
        btnDrowning             = findViewById(R.id.buttonDrowning);
        btnSuicideAttempt       = findViewById(R.id.buttonSuicideAttempt);
        btnFloodVictims         = findViewById(R.id.buttonFloodVictims);
        btnEarthquakeVictims    = findViewById(R.id.buttonEarthquakeVictims);
        btnTyphoonVictims       = findViewById(R.id.buttonTyphoonVictims);
        btnOtherSearchRescue    = findViewById(R.id.buttonOtherRescue);
        btnResidentialFire      = findViewById(R.id.buttonResidentialFire);
        btnCommercialFire       = findViewById(R.id.buttonCommercialFire);
        btnWildfire             = findViewById(R.id.buttonWildFire);
        btnSceneAccident        = findViewById(R.id.buttonSceneAccidentCrime);
        btnOtherFire            = findViewById(R.id.buttonOtherFire);
        btnKidnapHostage        = findViewById(R.id.buttonKidnapHostage);
        btnRobberyHoldup        = findViewById(R.id.buttonRobberyHoldup);
        btnMurderHomicide       = findViewById(R.id.buttonMurderHomicide);
        btnRapeMolest           = findViewById(R.id.buttonRapeMolest);
        btnSceneAccidentCrime   = findViewById(R.id.buttonSceneAccidentCrime);
        btnOtherCrime           = findViewById(R.id.buttonOtherCrime);


        btnHeartAttack.setOnClickListener(this);
        btnStroke.setOnClickListener(this);
        btnPoisoning.setOnClickListener(this);
        btnAnimeBites.setOnClickListener(this);
        btnFractureFalling.setOnClickListener(this);
        btnOBCases.setOnClickListener(this);
        btnVehicularAccident.setOnClickListener(this);
        btnOtherMedical.setOnClickListener(this);
        btnDrowning.setOnClickListener(this);
        btnSuicideAttempt.setOnClickListener(this);
        btnFloodVictims.setOnClickListener(this);
        btnEarthquakeVictims.setOnClickListener(this);
        btnTyphoonVictims.setOnClickListener(this);
        btnOtherSearchRescue.setOnClickListener(this);
        btnResidentialFire.setOnClickListener(this);
        btnCommercialFire.setOnClickListener(this);
        btnWildfire.setOnClickListener(this);
        btnSceneAccident.setOnClickListener(this);
        btnOtherFire.setOnClickListener(this);
        btnKidnapHostage.setOnClickListener(this);
        btnRobberyHoldup.setOnClickListener(this);
        btnMurderHomicide.setOnClickListener(this);
        btnRapeMolest.setOnClickListener(this);
        btnSceneAccidentCrime.setOnClickListener(this);
        btnOtherCrime.setOnClickListener(this);

    }

    //Start reverse geocoding finding current location (latitude and longhitude)
    public void locationUpdate()
    {
        SmartLocation.with(EmergencyTypeCategory.this).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location)
            {
                location.getLatitude();
                location.getLongitude();
                longhitude = location.getLongitude() + "";
                latitude   = location.getLatitude() + "";
            }
        });
    }


    public boolean checkAllRequirements()
    {
        boolean a = true;
        boolean b = true;
        boolean c = true;
        boolean d = true;
        boolean e = true;
        boolean f = true;
        boolean g = true;

        // Check if the location services are enabled
        a = SmartLocation.with(getApplicationContext()).location().state().locationServicesEnabled();

        // Check if the location services are enabled
        b = SmartLocation.with(getApplicationContext()).location().state().locationServicesEnabled();

        // Check if any provider (network or gps) is enabled
        c = SmartLocation.with(getApplicationContext()).location().state().isAnyProviderAvailable();

        // Check if GPS is available
        d = SmartLocation.with(getApplicationContext()).location().state().isGpsAvailable();

        // Check if Network is available
        e = SmartLocation.with(getApplicationContext()).location().state().isNetworkAvailable();

        // Check if the passive provider is available
        f = SmartLocation.with(getApplicationContext()).location().state().isPassiveAvailable();

        // Check if the location is mocked
        g = SmartLocation.with(getApplicationContext()).location().state().isMockSettingEnabled();

        Log.d("debugging", a+" "+b+" "+c+" "+d+" "+e+" "+f +" "+g);
        return (a && b && c && d && e && f );
    }

    //Start Message
    public void send_message(String Message, String contactNumber)
    {
        android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
        List<String> messages = sms.divideMessage(Message);

        for (String message : messages)
        {
//            status.setTextColor(Color.BLUE);
            sms.sendTextMessage(contactNumber, null, message,
                    PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("This is SMS"), 0), null);
        }
    }

    public void broadcast() {
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String messageReport="";
                boolean error=true;

                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        messageReport="message sent";
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        messageReport="error! You may be don't have enough load";
                        error= false;
                        break;

                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        messageReport="Error:No Service!";
                        error= false;
                        break;

                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        messageReport="Error: No PDU!";
                        error= false;
                        break;

                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        messageReport="Error: No Sim Card detected!";
                        error= false;
                        break;
                }


//                status.setText(messageReport);
//                status.setTextColor(error ? Color.GREEN:Color.RED);

            }
        },new IntentFilter("This is SMS"));
    }

    /* Start PICTURES FUNCTIONS*/
    public void takePicture()
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, 1);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1)
        {
            //result for getting image capture
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                try {
                    mImageBitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(mCurrentPhotoPath));
                    mImageBitmap = Bitmap.createScaledBitmap(mImageBitmap, 320, 240, true);


//                        stud_pic.setImageBitmap(rotateImage(BITMAP_RESIZER(mImageBitmap, 320, 240), 90));
//                        mImageBitmap = rotateImage(BITMAP_RESIZER(mImageBitmap, 320, 240), 90);

//                    image.setImageBitmap(BITMAP_RESIZER(mImageBitmap, 320, 240));
                    mImageBitmap = BITMAP_RESIZER(mImageBitmap, 320, 240);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Bitmap BITMAP_RESIZER(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    public static Bitmap rotateImage(Bitmap src, float degree) {
        // create new matrix
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bmp;
    }

    private File createImageFile() throws IOException {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  // prefix
                    ".jpg",         // suffix
                    storageDir      // directory
            );

            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = "file:" + image.getAbsolutePath();
            return image;
        } catch (IOException ex) {
            Log.i("sample", "" + ex);
        }
        return null;
    }

    //Start checker
    public boolean checkMobileDataConnection()
    {
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean)method.invoke(cm);
        } catch (Exception e) {
            // Some problem accessible private API
            // TODO do whatever error handling you want here
        }

        return mobileDataEnabled;
    }

    public boolean checkWifiState()
    {
        WifiManager mng = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return mng.isWifiEnabled();
    }


    //dialog yes or no
    public void askUserTakePicture()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Take Picture");
        builder.setMessage("Do you want to take picture for your request?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                takePicture();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }
}
