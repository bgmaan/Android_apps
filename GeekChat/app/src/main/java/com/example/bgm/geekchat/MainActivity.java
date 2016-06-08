package com.example.bgm.geekchat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import io.kimo.lib.faker.Faker;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    ListView friendsListView;
    ArrayList<User> friends;
    private Firebase mRef;
    private  UsersAdapter usrAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.my_friends);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        friends = new ArrayList<>();
        mRef = new Firebase(MyConstants.FIREBASE_URL+"/users/"+MyConstants.idUser+"/");
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent usersIntent = new Intent(MainActivity.this, Users.class);
                    startActivity(usersIntent);
                }
            });
        }

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        friendsListView = (ListView)findViewById(R.id.myFriendsList);
        usrAdapter = new UsersAdapter(this,R.layout.item_user,getFriends());
        friendsListView.setAdapter(usrAdapter);
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent chatIntent = new Intent(MainActivity.this,ChatActivity.class);
                chatIntent.putExtra("name",usrAdapter.getItem(i).getName());
                startActivity(chatIntent);
            }
        });

    }

    private ArrayList<User> getFriends() {
         friends.clear();

        //pobierz wszystkich znajomych

        //jezeli null

        //jezeli nie dodaj

        mRef.child("friends").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {




                    for (DataSnapshot ssnapshot : snapshot.getChildren()) {


                      Firebase  ref = new Firebase(MyConstants.FIREBASE_URL+"/users/"+ssnapshot.getKey().toString()+"/");

                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                String friendCount;
                                System.out.println(snapshot.child("email").getValue().toString());
                                if(snapshot.child("friends").exists())friendCount=snapshot.child("friends").getValue().toString();
                                else friendCount="0";
                                friends.add(new User(snapshot.child("email").getValue().toString(), snapshot.child("name").getValue().toString(), Integer.parseInt(friendCount),
                                        snapshot.child("avatarId").getValue().toString(),snapshot.getKey().toString()));
                                System.out.println(friends.size());
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
    @Override
    public void onResume(){
        super.onResume();
        getFriends();

    }

}