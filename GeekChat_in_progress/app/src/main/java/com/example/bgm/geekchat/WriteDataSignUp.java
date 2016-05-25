package com.example.bgm.geekchat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_data_sign_up);
        Firebase.setAndroidContext(this);
        mRef = new Firebase(MyConstants.FIREBASE_URL);
        avatar = (ImageView)findViewById(R.id.newprofileAvatar);
        next = (Button)findViewById(R.id.firstDataNext);
        name = (EditText)findViewById(R.id.firstNameProfile);
        leftRandom = (ImageButton)findViewById(R.id.lefRandom);
        rightRandom = (ImageButton)findViewById(R.id.rightRandom);
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

                if(addData()) {
                   startMain();

                }

            }
        });
    }

    private void startMain() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }

    private boolean addData() {




        ifAdded = false;
        String nameWritten = name.getText().toString();
        // On complete call either onLoginSuccess or onLoginFailed
        if(!nameWritten.isEmpty()) {
            //String userId = Login_activity.idUser;



            mRef.child("users").child(MyConstants.idUser).child("Name").setValue(nameWritten);
            mRef.child("users").child(MyConstants.idUser).child("AvatarId").setValue(avatarSrc);
            ifAdded =true;

        }





        return ifAdded;
    }
}
