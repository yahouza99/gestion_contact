package com.example.contactpro;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnup;
    Button btnsignup;
    EditText nom;
    EditText prenom;
    EditText tel;
    EditText email;
    EditText password;
    EditText service;
    LinearLayout login;
    LinearLayout sign;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private EditText username, password_log;
    private Button btnlog;
    private TextView signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Reccuperation des bouttons
        btnup=(Button)findViewById(R.id.btnup);
        btnsignup=(Button)findViewById(R.id.btnsignup);
        btnlog=(Button)findViewById(R.id.btnlog);
        //Reccuperation des champs de pour l'inscription
        nom=(EditText)findViewById(R.id.nom);
        prenom=(EditText)findViewById(R.id.prenom);
        tel=(EditText)findViewById(R.id.tel) ;
        email=(EditText)findViewById(R.id.email) ;
        password=(EditText)findViewById(R.id.password) ;
        service=(EditText)findViewById(R.id.service) ;
        //Reccuperation des layouts
        login=findViewById(R.id.login_layout);
        sign=findViewById(R.id.sign_layout);
        //instances firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        //mettre les boutons en ecoute
        btnup.setOnClickListener(this);
        btnsignup.setOnClickListener(this);
        btnlog.setOnClickListener(this);
        ///
        username = findViewById(R.id.username);
        password_log = findViewById(R.id.password_log);
        signupRedirectText = findViewById(R.id.signupRedirectText);

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
    }*/
    private  void login(){
        String email = username.getText().toString();
        String pass = password_log.getText().toString();
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!pass.isEmpty()) {
               db.collection("users")
                       .get()
                       .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                               if (task.isSuccessful()) {
                                   for (QueryDocumentSnapshot doc : task.getResult()) {
                                       String e = doc.getString("email");
                                       String e1 = username.getText().toString().trim();
                                       String p = doc.getString("password");
                                       String p1 = password_log.getText().toString().trim();
                                       if (e.equalsIgnoreCase(e1) & p.equalsIgnoreCase(p1)) {
                                           Intent home = new Intent(MainActivity.this, Home.class);
                                           home.putExtra("email",e);
                                           startActivity(home);
                                           Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                           break;
                                       } else {
                                           Toast.makeText(MainActivity.this, "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               }
                           }
                       });

            }else{
                password_log.setError("Empty fields are not allowed");
            }
        }else if (email.isEmpty()) {
            username.setError("Empty fields are not allowed");
        } else {
            username.setError("Please enter correct email");
        }
    }
    private void createAccount() {
        // Create a new user
        Map<String, Object> user = new HashMap<>();
        user.put("nom",nom.getText().toString());
        user.put("prenom)",prenom.getText().toString());
        user.put("tel",tel.getText().toString());
        user.put("email",email.getText().toString());
        user.put("password",password.getText().toString());
        user.put("service",service.getText().toString());
        DocumentReference docRef=db.collection("users").document(email.getText().toString());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists())
                {
                    Toast.makeText(MainActivity.this, "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                }else{
                    String myId = docRef.getId();
                    db.collection("users").document(email.getText().toString())
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failed documetn", Toast.LENGTH_SHORT).show();
                    }
                });
           }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btnsignup:
                createAccount();
                sign.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
                break;
            case R.id.btnup:
                login.setVisibility(View.GONE);
                sign.setVisibility(View.VISIBLE);
                break;
            case R.id.btnlog:
                login();
                break;
        }
    }
}