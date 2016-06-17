package com.example.bgm.geekchat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import io.kimo.lib.faker.Faker;

public class ChatActivity extends AppCompatActivity {


    private ListView chatListView;
    private  ArrayList<Message> Messages;
    private ImageButton sendButton;
    private EditText input;
    private TextView time;
    private MessageAdapter messageAdapter;
    private User user;
    private String nameFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setTitle("Chat");
        nameFriend = getIntent().getStringExtra("name");
        Firebase.setAndroidContext(this);
        final Firebase rootRef = new Firebase("https://glowing-fire-8710.firebaseio.com/");

        Messages = new ArrayList<Message>();
        input = (EditText)findViewById(R.id.youMessageChat);
        chatListView=(ListView)findViewById(R.id.messagelist);
        sendButton = (ImageButton)findViewById(R.id.sendButton);


         user = new User(getIntent().getStringExtra("email"),getIntent().getStringExtra("name"),"0",getIntent().getStringExtra("avatar"),"0");
         final User thisUser = new User(MyConstants.getEmail(),MyConstants.getName(),"0",MyConstants.getAvatarId(),"0");
         messageAdapter = new MessageAdapter(this,R.layout.item_message,Messages);

        chatListView.setAdapter(messageAdapter);
        chatListView.setDivider(null);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View ) {


             
                Messages.add(new Message(thisUser,System.currentTimeMillis(),input.getText().toString()));
                messageAdapter.notifyDataSetChanged();
                input.setText("");
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


            }
        });

        Firebase ref = new Firebase(MyConstants.FIREBASE_URL+"/activeChats/"+MyConstants.idUser+"/friends/"+"");
        ref.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
            //... ChildEventListener also defines onChildChanged, onChildRemoved,
            //    onChildMoved and onCanceled, covered in later sections.
        });
    }

    private void sendMessage() {



    }

    private void hideKeyboard() {



    }
    private void getMessages() {



    }


}
