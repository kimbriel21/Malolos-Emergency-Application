package com.example.mololos.malolosemergencyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class EmergencyTypeMenu extends AppCompatActivity {
    ImageButton fire,medical,crime,rescue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergenct_type_menu);
        fire = findViewById(R.id.imageButtonFire);
        medical = findViewById(R.id.imageButtonMedical);
        crime = findViewById(R.id.imageButtonCrime);
        rescue = findViewById(R.id.imageButtonEmergencyRescue);

        onSelectTypeEmergency();
    }

    public void onSelectTypeEmergency()
    {
       fire.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startIntent("Fire");
           }
       });

       medical.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startIntent("Medical");
           }
       });

       crime.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startIntent("Crime");
           }
       });

       rescue.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startIntent("Rescue");
           }
       });

    }

    public void startIntent(String type)
    {
        Intent intent = new Intent(EmergencyTypeMenu.this, SelectOfficeBranchActivity.class);
        intent.putExtra("EmergencyType", type);
        DataHolder.emergencyType = type;
        startActivity(intent);
    }
}
