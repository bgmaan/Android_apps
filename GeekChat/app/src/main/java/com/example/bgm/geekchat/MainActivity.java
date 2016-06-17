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
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import io.kimo.lib.faker.Faker;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    ListView friendsListView;
    public static ArrayList<User> friends;
    private Firebase mRef;
    private UsersAdapter usrAdapter;
    private Button requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.my_friends);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        friends = new ArrayList<>();
        requests = (Button) findViewById(R.id.requests_button);
        mRef = new Firebase(MyConstants.FIREBASE_URL + "/users/" + MyConstants.idUser + "/");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent usersIntent = new Intent(MainActivity.this, Users.class);
                    startActivity(usersIntent);
                }
            });
        }
        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent usersIntent = new Intent(MainActivity.this, Requests.class);
                startActivity(usersIntent);
            }
        });

        friendsListView = (ListView) findViewById(R.id.myFriendsList);
        usrAdapter = new UsersAdapter(this, R.layout.item_friend, friends);
        friendsListView.setAdapter(usrAdapter);


        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent chatIntent = new Intent(MainActivity.this, ChatActivity.class);
                chatIntent.putExtra("name", usrAdapter.getItem(i).getName());
                chatIntent.putExtra("avatar", usrAdapter.getItem(i).getImgSrc());
                chatIntent.putExtra("id", usrAdapter.getItem(i).getUserId());
                startActivity(chatIntent);
            }
        });
       checkRequests();
    }

    private void checkRequests() {

        mRef.child("requests").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {


                if(!snapshot.hasChildren()) {

                    requests.setBackgroundResource(R.drawable.ic_face_white_18dp);
                }

                else {
                    System.out.println("JestRequ");
                    requests.setBackgroundResource(R.drawable.ic_face_light_green_600_18dp);
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        mRef.child("requests").addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {


                requests.setBackgroundResource(R.drawable.ic_face_light_green_600_18dp);


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



    private ArrayList<User> getFriends() {

        friends.clear();


        mRef.child("friends").addListenerForSingleValueEvent(new ValueEventListener() {

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
    @Override
    public void onResume(){
        super.onResume();
        getFriends();
        usrAdapter.notifyDataSetChanged();
    }


    public static boolean ifExist(String id) {
    boolean ifEx = false;
        for(User user : friends) {
            if(user.getUserId().equals(id)){ifEx=true; break;}

        }

        return ifEx;
    }
}
