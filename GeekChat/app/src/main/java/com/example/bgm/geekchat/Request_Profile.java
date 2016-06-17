package com.example.bgm.geekchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

public class Request_Profile extends AppCompatActivity {

    private Button noAdd;
    private Button yesAdd;
    private User user;
    private Firebase mRef;
    private TextView name;
    private TextView email;
    private ImageView avatar;
    private TextView friendNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_profile);
        Firebase.setAndroidContext(this);
        user = new User(getIntent().getStringExtra("email"),getIntent().getStringExtra("name"),getIntent().getStringExtra("friends"),
                getIntent().getStringExtra("image"),getIntent().getStringExtra("id")
                );
        noAdd= (Button) findViewById(R.id.noReqButton);
        yesAdd= (Button) findViewById(R.id.addFriendRequestButton);
        name = (TextView) findViewById(R.id.nameProfileReq);
        email = (TextView) findViewById(R.id.emailProfileReq);
        friendNum = (TextView) findViewById(R.id.friendCounterReq);
        avatar = (ImageView) findViewById(R.id.profileAvatarReq);
        mRef = new Firebase(MyConstants.FIREBASE_URL + "/users/" + MyConstants.idUser + "/friends/");
        name.setText(user.getName());
        email.setText(user.getEmail());

        friendNum.setText(user.getFriendsNumber());
        Picasso.with(this).load(user.getImgSrc()).into(avatar);
        noAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteRequest();
            }
        });
        yesAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addFriend();
            }
        });

    }

   private void addFriend() {

       mRef.child(user.getUserId()).setValue("");
       Firebase nRef = new Firebase(MyConstants.FIREBASE_URL + "/users/" + user.getUserId() + "/friends/");
       nRef.child(MyConstants.idUser).setValue("");
       deleteRequest();

   }
    private void deleteRequest() {
        mRef =new Firebase(MyConstants.FIREBASE_URL + "/users/" + MyConstants.idUser + "/");
        mRef.child("requests").child(user.getUserId()).setValue(null);
        this.finish();
    }
}





