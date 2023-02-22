package com.example.contactpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.LinkedList;

public class Home extends AppCompatActivity implements View.OnClickListener{
    TextView username;
    private ImageButton add;
    private ImageButton menu;
    private Button logout;
    private String email;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;
    LinkedList<Contact> contacts;
    RecyclerView contactsRecycler;
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
        contactsRecycler=(RecyclerView)findViewById(R.id.list_contacts);
        db = FirebaseFirestore.getInstance();
        contacts= new LinkedList<Contact>();

        getContacts();
    }

    void getContacts() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("users").document(currentUser.getEmail());
        docRef.collection("contacts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Contact c = new Contact(document.get("nom").toString(), document.get("prenom").toString(), document.get("tel").toString(), document.get("email").toString(), document.get("service").toString(), document.get("url").toString());
                                contacts.add(c);
                            }
                            // use this setting to improve performance if you know that changes
// in content do not change the layout size of the RecyclerView
                            contactsRecycler.setHasFixedSize(true);
// use a linear layout manager
                           LinearLayoutManager layoutManager = new LinearLayoutManager(Home.this);
                            contactsRecycler.setLayoutManager(layoutManager);
// specify an adapter (see also next example)
                            MyAdapter myAdapter = new MyAdapter(contacts,Home.this);
                            contactsRecycler.setAdapter(myAdapter);

                        } else {
                            Log.d("not ok", "Error getting documents: ", task.getException());
                        }
                    }
                });
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