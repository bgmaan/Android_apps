package com.example.bgm.geekchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import io.kimo.lib.faker.Faker;

public class Users extends AppCompatActivity   {

    private ListView usersListView;
    private ArrayList<User> users = new ArrayList<>();
    private AutoCompleteTextView searchBar;
    private Firebase mRef;
    private ArrayList<String> userNames;
    private UsersAdapter usersAdapter;
    private ArrayAdapter<String> adapterSearchBar;
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
         adapterSearchBar = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,userNames);
        searchBar.setAdapter(adapterSearchBar);
        usersAdapter = new UsersAdapter(this,R.layout.item_user,getUsers(""));
        usersListView.setAdapter(usersAdapter);
        searchBar.setText("");
        searchBar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                searchBar.setText("");
            }
        });
        AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (usersListView.getLastVisiblePosition() >= usersListView.getCount() - 5) {
                        getUsers("");
                    }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if((firstVisibleItem + 10) >= visibleItemCount)
                {



                }
                if(mLastFirstVisibleItem>firstVisibleItem)
                {

                }
                mLastFirstVisibleItem=firstVisibleItem;
            }
        };
        searchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                users.clear();
                userNames.clear();
                getUsers(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });
        usersListView.setOnScrollListener(scrollListener);

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                User user = users.get(i);
                Intent profileIntent = new Intent(Users.this,Profile_Activity.class);
                profileIntent.putExtra("name",user.getName());
                profileIntent.putExtra("friends",user.getFriendsNumber());
                profileIntent.putExtra("email",user.getEmail());
                profileIntent.putExtra("image",user.getImgSrc());
                profileIntent.putExtra("id",user.getUserId());
                startActivity(profileIntent);
                users.clear();
            }
        });
}

    private void getUsersAfterChangeScroll() {
        int sizeWords = searchBar.getText().length();
        Query queryRef = mRef.orderByKey().startAt(users.get(users.size() - 1).getName().substring(0,sizeWords)).limitToFirst(20);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ssnapshot) {
                for (DataSnapshot snapshot : ssnapshot.getChildren()) {

                    if(snapshot.hasChild("name")&!snapshot.getKey().toString().equals(MyConstants.idUser)) {
                        int size=users.size();
                        if(size==0) {
                            String friendCount;
                            if(snapshot.child("friends").exists())friendCount=snapshot.child("friends").getValue().toString();
                            else friendCount="0";
                            users.add(new User(snapshot.child("email").getValue().toString(), snapshot.child("name").getValue().toString(), Integer.parseInt(friendCount),
                                    snapshot.child("avatarId").getValue().toString(),snapshot.getKey().toString()));
                            usersAdapter.notifyDataSetChanged();
                            userNames.add(snapshot.child("name").getValue().toString());
                            adapterSearchBar.notifyDataSetChanged();
                        }
                        if(size>0){
                            if(users.get(size-1).getEmail().equals(snapshot.child("email").getValue().toString())) {



                            }

                            else {
                                String friendCount;
                                if(snapshot.child("friends").exists())friendCount=snapshot.child("friends").getValue().toString();
                                else friendCount="0";
                                users.add(new User(snapshot.child("email").getValue().toString(), snapshot.child("name").getValue().toString(), Integer.parseInt(friendCount),
                                        snapshot.child("avatarId").getValue().toString(),snapshot.getKey().toString()));
                                usersAdapter.notifyDataSetChanged();
                                userNames.add(snapshot.child("name").getValue().toString());
                                adapterSearchBar.notifyDataSetChanged();
                            }}
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




    }

    @Override
    public void onResume(){
        super.onResume();
        getUsers("");

    }


    private ArrayList<User> getUsers(String text) {
        final Query queryRef;
        if(!text.isEmpty()) {
            queryRef = mRef.orderByChild("name").startAt((text)).endAt(text+"\uf8ff").limitToFirst(20);
        }
        else {
            if (!users.isEmpty()) {
                queryRef = mRef.orderByChild("name").startAt(users.get(users.size() - 1).getName()).limitToFirst(20);
            } else {
                queryRef = mRef.orderByChild("name").limitToFirst(20);
            }
        }

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(DataSnapshot ssnapshot) {
                                                     for (DataSnapshot snapshot : ssnapshot.getChildren()) {

                                                         if(snapshot.hasChild("name")&!snapshot.getKey().toString().equals(MyConstants.idUser)) {
                                                             int size=users.size();
                                                             if(size==0) {
                                                                 String friendCount;
                                                                 if(snapshot.child("friends").exists())friendCount=snapshot.child("friends").getValue().toString();
                                                                 else friendCount="0";
                                                                 users.add(new User(snapshot.child("email").getValue().toString(), snapshot.child("name").getValue().toString(), Integer.parseInt(friendCount),
                                                                         snapshot.child("avatarId").getValue().toString(),snapshot.getKey().toString()));
                                                                 usersAdapter.notifyDataSetChanged();
                                                                 userNames.add(snapshot.child("name").getValue().toString());
                                                                 adapterSearchBar.notifyDataSetChanged();
                                                             }
                                                             if(size>0){
                                                             if(users.get(size-1).getEmail().equals(snapshot.child("email").getValue().toString())) {



                                                             }

                                                                else {
                                                                 String friendCount;
                                                                 if(snapshot.child("friends").exists())friendCount=snapshot.child("friends").getValue().toString();
                                                                 else friendCount="0";
                                                                 users.add(new User(snapshot.child("email").getValue().toString(), snapshot.child("name").getValue().toString(), Integer.parseInt(friendCount),
                                                                         snapshot.child("avatarId").getValue().toString(),snapshot.getKey().toString()));
                                                                 usersAdapter.notifyDataSetChanged();
                                                                 userNames.add(snapshot.child("name").getValue().toString());
                                                                 adapterSearchBar.notifyDataSetChanged();
                                                             }}
                                                             }
                                                     }
                                                 }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        return users;
    }
}
