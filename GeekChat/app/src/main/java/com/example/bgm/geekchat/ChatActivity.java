package com.example.bgm.geekchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.ListView;

import com.firebase.client.Firebase;

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


         user = new User("vzir@sd","Bartek",2,Faker.Url.avatar(),"0"

         );
         messageAdapter = new MessageAdapter(this,R.layout.item_message,Messages);

        chatListView.setAdapter(messageAdapter);
        chatListView.setDivider(null);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View ) {


                Log.d("inputOut",""+input.getText());
                Messages.add(new Message(user,System.currentTimeMillis(),input.getText().toString()));
                messageAdapter.notifyDataSetChanged();
                input.setText("");
                Log.d("Size",""+Messages.size());

            }
        });


    }


}
