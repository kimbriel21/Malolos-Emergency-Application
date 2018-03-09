package com.example.mololos.malolosemergencyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectOfficeBranchActivity extends AppCompatActivity {
    Button btnOffice1,btnOffice2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_office_branch);

        btnOffice1 = findViewById(R.id.buttonOffice1);
        btnOffice2 = findViewById(R.id.buttonOffice2);
        onSelectOfficeEvent();

    }

    public void onSelectOfficeEvent()
    {
            btnOffice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startIntent("Office 1","09183441889");
                }
            });

            btnOffice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startIntent("Office 2","09183441889");
                }
            });
    }

    public void startIntent(String location,String office_number)
    {
        Intent intent = new Intent(getApplicationContext(), EmergencyTypeCategory.class);
        DataHolder.emergencyBranchLocation = location;
        DataHolder.number = office_number;
        startActivity(intent);
    }
}
