package com.example.bgm.geekchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by bgm on 04.06.16.
 */
public class Profile_Activity extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private ImageView avatar;
    private Button addButton;
    private TextView friendNum;
    private int friends = 0;
    private Firebase mRef;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(getIntent().getStringExtra("name"));
        Firebase.setAndroidContext(this);
        mRef = new Firebase(MyConstants.FIREBASE_URL+"/users/");
        name = (TextView) findViewById(R.id.nameProfile);
        email = (TextView) findViewById(R.id.emailProfile);
        friendNum = (TextView) findViewById(R.id.friendCounter);
        avatar = (ImageView) findViewById(R.id.profileAvatar);
        addButton = (Button) findViewById((R.id.addFriendButton));

        name.setText(getIntent().getStringExtra("name"));
        email.setText(getIntent().getStringExtra("email"));
        userId = getIntent().getStringExtra("id");
        friendNum.setText(String.valueOf(getIntent().getIntExtra("friendsNum", 0)));
        Picasso.with(this).load(getIntent().getStringExtra("image")).into(avatar);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Profile_Activity.this, getString(R.string.friend_added), Toast.LENGTH_SHORT).show();
                 addFriend();
            }
        });
    }
    private void addFriend() {

        mRef.child(MyConstants.idUser).child("friends").child(userId).setValue("");

    }
}