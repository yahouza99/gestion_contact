package com.example.contactpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button btnup;
    Button btnsignup;
    LinearLayout login;
    LinearLayout sign;
    private FirebaseAuth auth;
    private EditText username, password_log;
    private Button btnlog;
    private TextView signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnup=(Button)findViewById(R.id.btnup);
        btnsignup=(Button)findViewById(R.id.btnsignup);
        login=findViewById(R.id.login_layout);
        sign=findViewById(R.id.sign_layout);
       /* menu= (ImageButton)findViewById(R.id.menu);
        home_page=findViewById(R.id.home_page);*/
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.GONE);
                sign.setVisibility(View.VISIBLE);
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.VISIBLE);
                sign.setVisibility(View.GONE);
            }
        });
        auth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        password_log = findViewById(R.id.password_log);
        btnlog = findViewById(R.id.btnlog);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString();
                String pass = password_log.getText().toString();
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!pass.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this, Main2Activity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        password_log.setError("Empty fields are not allowed");
                    }
                } else if (email.isEmpty()) {
                    username.setError("Empty fields are not allowed");
                } else {
                    username.setError("Please enter correct email");
                }
            }
        });

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.info)
        {
            System.out.println("info");
        } else if (item.getItemId()==R.id.help) {
            System.out.println("help");
        } else if (item.getItemId()==R.id.night) {
            int color= Color.BLACK;
            home_page.setBackgroundColor(color);
        } else if (item.getItemId()==R.id.theme) {
            home_page.setBackgroundColor(Color.WHITE);
        }
        return true;
    }
    public void onClick(View view){
        if (view.getId()==R.id.btnup)
        {
            login.setVisibility(View.INVISIBLE);
            sign.setVisibility(View.VISIBLE);
        }
    }*/
}