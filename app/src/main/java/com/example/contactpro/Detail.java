package com.example.contactpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class Detail extends AppCompatActivity {
    TextView nom;
    TextView prenom;
    TextView tel;
    TextView email;
    TextView service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nom= (TextView) findViewById(R.id.nom);
        prenom= (TextView) findViewById(R.id.prenom);
        tel= (TextView) findViewById(R.id.tel);
        email= (TextView) findViewById(R.id.email);
        service= (TextView) findViewById(R.id.service);
        Bundle extras=getIntent().getExtras();
        nom.setText("nom :"+" "+extras.getString("nom"));
        prenom.setText("prenom :"+" "+extras.getString("prenom"));
        tel.setText("tel :"+" "+extras.getString("tel"));
        email.setText("email :"+" "+extras.getString("email"));
        service.setText("service :"+" "+extras.getString("service"));
    }
}