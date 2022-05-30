package com.example.carwashapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.carwashapp.model.UserManagment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    CardView actProfile;
    CardView actReserve;
    CardView actService;
    CardView actUserCar;
    TextView username;
    FloatingActionButton btnlogout;
    UserManagment userManagment;
    String nombre, apellido;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        actProfile = findViewById(R.id.actProfile);
        actReserve = findViewById(R.id.actReserve);
        actService = findViewById(R.id.actListServices);
        actUserCar = findViewById(R.id.actCarsUsers);
        username = findViewById(R.id.txtUsername);
        btnlogout = findViewById(R.id.btnLogout);

        userManagment = new UserManagment(this);
        userManagment.checkLogin();

        HashMap<String, String> user = userManagment.userDetails();
        nombre = user.get(userManagment.NAME);
        apellido = user.get(userManagment.APE);
        id = user.get(userManagment.ID);

        int codcli = Integer.parseInt(id);

//        codcli = getIntent().getIntExtra("codcli", 0);


        username.setText(nombre + " " + apellido);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        actProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("codcli", codcli);
                startActivity(intent);
            }
        });

        actReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ListMyReservesActivity.class);
                intent.putExtra("codcli", codcli);
                startActivity(intent);
            }
        });

        actService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ListServicesActivity.class);
                intent.putExtra("codcli", codcli);
                startActivity(intent);
            }
        });

        actUserCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ListMyCarActivity.class);
                intent.putExtra("codcli", codcli);
                startActivity(intent);
            }
        });

        int nightModeFlags = HomeActivity.this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                /* si esta activo el modo oscuro lo desactiva */
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
    }

    public void logout(){
        userManagment.logout();

    }
}