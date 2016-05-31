package com.example.bgm.geekchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
        ifEx=true;
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
    private boolean ifNameExist(String nameUser) {

        Firebase mRef = new Firebase(MyConstants.FIREBASE_URL+"/names/"+nameUser);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                if(!snapshot.exists()) {
                    Log.d(" bool ",""+snapshot.exists());
                    ifEx = false;

                }




            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

       return ifEx;
    }
    private boolean addData() {




        ifAdded = false;
        String nameWritten = name.getText().toString();
        // On complete call either onLoginSuccess or onLoginFailed
        if(!nameWritten.isEmpty())new MyAsyncTask(nameWritten).execute();
        return ifAdded;
    }
    private class MyAsyncTask extends AsyncTask<Void, Void, Void>
    {
        String name;
        public MyAsyncTask(String name) {
            super();
            // do stuff
            this.name = name;
        }
        @Override
        protected Void doInBackground(Void... params) {
            ifNameExist(name);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

                //String userId = Login_activity.idUser;

                if(ifEx) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WriteDataSignUp.this);
                    builder.setMessage("Choose other name")
                            .setTitle(R.string.login_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    ifEx=true;
                }
                else {
                    mRef.child("users").child(MyConstants.idUser).child("Name").setValue(name);
                    mRef.child("users").child(MyConstants.idUser).child("AvatarId").setValue(avatarSrc);
                    mRef.child("names").child(name).setValue("");
                    ifAdded = true;

            }

        }
    }
}
