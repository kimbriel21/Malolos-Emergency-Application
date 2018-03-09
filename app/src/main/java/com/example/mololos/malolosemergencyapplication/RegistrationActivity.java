package com.example.mololos.malolosemergencyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {
    Button register;
    EditText etFirstName, etLastName,etContactNumber,etAddress,etEMail;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        initializedViews();
        registerButton();
    }

    public void initializedViews()
    {
        etFirstName     = findViewById(R.id.etFirstName);
        etLastName      = findViewById(R.id.etLastName);
        etContactNumber = findViewById(R.id.etContactNumber);
        etAddress       = findViewById(R.id.etAddress);
        etEMail         = findViewById(R.id.etEMail);
    }

    public void registerButton()
    {
        register = findViewById(R.id.buttonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String contactNumber = etContactNumber.getText().toString();
                String address = etAddress.getText().toString();
                String email = etEMail.getText().toString();
                //insertData
                databaseHelper.insertUser(firstName,lastName,contactNumber,address,email);
                Intent intent = new Intent(RegistrationActivity.this,EmergencyTypeMenu.class);
                startActivity(intent);

            }
        });
    }


}
