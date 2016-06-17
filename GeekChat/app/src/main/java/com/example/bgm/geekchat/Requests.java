package com.example.bgm.geekchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by bg on 14.06.16.
 */
public class Requests  extends AppCompatActivity {

    private ListView usersListView;
    private ArrayList<User> users = new ArrayList<>();
    private static ArrayList<User> friends;
    private Firebase mRef;
    private UsersAdapter usrAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_users);
        Firebase.setAndroidContext(this);
        mRef = new Firebase(MyConstants.FIREBASE_URL+"/users/"+MyConstants.idUser);
        setTitle("New requests");
        friends = new ArrayList<>();
        usersListView=(ListView)findViewById(R.id.requestUsers);

        usrAdapter = new UsersAdapter(this, R.layout.item_friend, friends);
        usersListView.setAdapter(usrAdapter);

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                User user = friends.get(i);
                Intent profileIntent = new Intent(Requests.this,Request_Profile.class);
                profileIntent.putExtra("name",user.getName());
                profileIntent.putExtra("friends",user.getFriendsNumber());
                profileIntent.putExtra("email",user.getEmail());
                profileIntent.putExtra("image",user.getImgSrc());
                profileIntent.putExtra("id",user.getUserId());
                startActivity(profileIntent);
                users.clear();
                Requests.this.finish();
            }
        });
    }



    @Override
    public void onResume(){
        super.onResume();
        getFriends();
        usrAdapter.notifyDataSetChanged();
    }

    private ArrayList<User> getFriends() {

        friends.clear();


        mRef.child("requests").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {




                for (DataSnapshot ssnapshot : snapshot.getChildren()) {

                    Firebase  ref = new Firebase(MyConstants.FIREBASE_URL+"/users/"+ssnapshot.getKey().toString()+"/");


                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshotT) {
                            String friendCount;
                            System.out.println(snapshotT.child("email").getValue().toString());
                            if(snapshotT.child("friends").exists())friendCount=snapshotT.child("friends").getValue().toString();
                            else friendCount="0";
                            friends.add(new User(snapshotT.child("email").getValue().toString(), snapshotT.child("name").getValue().toString(), friendCount, snapshotT.child("avatarId").getValue().toString(),snapshotT.getKey().toString()));
                            usrAdapter.notifyDataSetChanged();

                        }


                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            System.out.println("The read failed: " + firebaseError.getMessage());
                        }


                    });

                }






            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });



        return friends;
    }
}

