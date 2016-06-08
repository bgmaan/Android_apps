package com.example.bgm.geekchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.kimo.lib.faker.Faker;

public class WriteDataSignUp extends AppCompatActivity {
    private Firebase mRef;
    private ImageView avatar;
    private EditText name;
    private ImageButton leftRandom;
    private ImageButton rightRandom;
    private Button next;
    private String avatarSrc;
    private boolean ifAdded;
    private boolean ifEx;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_data_sign_up);
        Firebase.setAndroidContext(this);
        mRef = new Firebase(MyConstants.FIREBASE_URL);
        avatar = (ImageView) findViewById(R.id.newprofileAvatar);
        next = (Button) findViewById(R.id.firstDataNext);
        name = (EditText) findViewById(R.id.firstNameProfile);
        leftRandom = (ImageButton) findViewById(R.id.lefRandom);
        rightRandom = (ImageButton) findViewById(R.id.rightRandom);

        final Context con = this;

        avatarSrc = Faker.with(con)
                .Url
                .avatar();
        Picasso.with(con).load(avatarSrc).into(avatar);
        leftRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatarSrc = Faker.with(con)
                        .Url
                        .avatar();
                Picasso.with(con).load(avatarSrc).into(avatar);
            }
        });
        rightRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatarSrc = Faker.with(con)
                        .Url
                        .avatar();
                Picasso.with(con).load(avatarSrc).into(avatar);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameWritten = name.getText().toString();
                // On complete call either onLoginSuccess or onLoginFailed
                if (!nameWritten.isEmpty()) ifNameExist(nameWritten);



            }
        });
    }

    private void startMain() {


        this.finish();
        Intent intent = new Intent(WriteDataSignUp.this, MainActivity.class);
        startActivity(intent);


    }

    private void ifNameExist(final String nameUser) {


        final Firebase ref = new Firebase(MyConstants.FIREBASE_URL + "/names/" + nameUser);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (!snapshot.exists()) {

                    Log.d("Dobre imie",""+snapshot.exists());
                    mRef.child("users").child(MyConstants.idUser).child("name").setValue(nameUser);
                    mRef.child("users").child(MyConstants.idUser).child("avatarId").setValue(avatarSrc);
                    mRef.child("names").child(nameUser).setValue("");

                    startMain();

                }
                else {
                    Log.d("Zle imie",""+snapshot.exists());
                    name.setError("Choose other name");
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }



}
