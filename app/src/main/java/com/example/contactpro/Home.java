package com.example.contactpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Home extends AppCompatActivity implements View.OnClickListener{
    private ImageButton add;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        add= (ImageButton) findViewById(R.id.add);
        add.setOnClickListener(this);
        Bundle extras=getIntent().getExtras();
        email=extras.getString("email");
    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.add:
                Intent NewContact= new Intent(Home.this, New.class);
                NewContact.putExtra("email",email);
                startActivity(NewContact);
                break;
        }
    }
}