package com.example.contactpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity implements View.OnClickListener{
    TextView username;
    private ImageButton add;
    private ImageButton menu;
    private Button logout;
    private String email;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        username=(TextView)findViewById(R.id.username);
        add= (ImageButton) findViewById(R.id.add);
        menu= (ImageButton) findViewById(R.id.menu);
        logout= (Button) findViewById(R.id.logout);
        add.setOnClickListener(this);
        logout.setOnClickListener(this);
        Bundle extras=getIntent().getExtras();
        email=extras.getString("Email");
        username.setText("Welcome to "+" "+ email);
    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.add:
                Intent NewContact= new Intent(Home.this, New.class);
                NewContact.putExtra("Email",email);
                startActivity(NewContact);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent out= new Intent(Home.this, MainActivity.class);
                startActivity(out);
        }
    }
}