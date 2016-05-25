package com.example.bgm.geekchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.content.Intent;

import java.util.ArrayList;

import io.kimo.lib.faker.Faker;

public class Users extends AppCompatActivity   {

    private ListView usersListView;
    private ArrayList<User> users = new ArrayList<>();
    int counter;
    private int mLastFirstVisibleItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        setTitle("Search friends");
        counter=0;
        usersListView=(ListView)findViewById(R.id.usersList);

        final UsersAdapter usersAdapter = new UsersAdapter(this,R.layout.item_user,getUsers());
        usersListView.setAdapter(usersAdapter);
        AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if((firstVisibleItem + visibleItemCount) >= totalItemCount-10)
                {

                    getUsers();
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
                User user = usersAdapter.getItem(i);
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
        counter+=20;

        for (int i = 0; i < 20; i++) {

            users.add(new User(Faker.with(this).Internet.email(), Faker.with(this).Name.fullName(),Faker.with(this).Number.number(2),Faker.with(this).Url.avatar()));

        }
        if(!users.isEmpty()) {
            users.remove(0);
            users.remove(1);

        }
        return users;
    }
}
