package com.example.bgm.geekchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.content.Intent;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;

import io.kimo.lib.faker.Faker;

public class Users extends AppCompatActivity   {

    private ListView usersListView;
    private ArrayList<User> users = new ArrayList<>();
    private AutoCompleteTextView searchBar;
    private Firebase mRef;
    private ArrayList<String> userNames;
    int counter;
    private int mLastFirstVisibleItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        Firebase.setAndroidContext(this);
        mRef = new Firebase(MyConstants.FIREBASE_URL+"/users/");
        setTitle("Search friends");
        userNames = new ArrayList<String>();
        counter=0;
        usersListView=(ListView)findViewById(R.id.usersList);
        searchBar = (AutoCompleteTextView) findViewById(R.id.searchFriendsBar);
        ArrayAdapter<String> adapterSearchBar = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,userNames);
        searchBar.setAdapter(adapterSearchBar);
        final UsersAdapter usersAdapter = new UsersAdapter(this,R.layout.item_user,getUsers());
        usersListView.setAdapter(usersAdapter);
        searchBar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                searchBar.setText("");
            }
        });
        AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if((firstVisibleItem + visibleItemCount) >= totalItemCount-10)
                {


                    usersAdapter.notifyDataSetChanged();
                    //here start next 10 items request
                }
                if(mLastFirstVisibleItem>firstVisibleItem)
                {

                }
                mLastFirstVisibleItem=firstVisibleItem;
            }
        };
        usersListView.setOnScrollListener(scrollListener);

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                /*
                Intent profileIntent = new Intent(Users.this,Profile_Activity.class);
                profileIntent.putExtra("name",user.getName());
                profileIntent.putExtra("friends",user.getFriendsNumber());
                profileIntent.putExtra("email",user.getEmail());
                profileIntent.putExtra("image",user.getImgSrc());
                startActivity(profileIntent);
                */
            }
        });
}


    private ArrayList<User> getUsers() {
        Query queryRef;
        if(!users.isEmpty()) {
            queryRef = mRef.orderByChild("name").startAt(users.get(users.size()-1).getName()).limitToFirst(20);
        }
        else {
            queryRef = mRef.orderByChild("name").limitToFirst(20);
        }
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                System.out.println(snapshot.getKey());
                if(snapshot.hasChild("Name")&!snapshot.getKey().toString().equals(MyConstants.idUser)) {

                    users.add(new User(snapshot.child("email").getValue().toString(),snapshot.child("Name").getValue().toString(),0,snapshot.child("AvatarId").getValue().toString()));
                    userNames.add(snapshot.child("Name").getValue().toString());
                }
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
            // ....
        });


        //pobierz pierwsze 20 ludzi



        return users;
    }
}
