package com.example.bgm.geekchat;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.*;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by bgm on 14.05.16.
 */
public class UsersAdapter extends ArrayAdapter<User>{

    public UsersAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
    }

    public UsersAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        User user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }


        TextView name = (TextView)convertView.findViewById(R.id.itemUsersName);
        ImageView avatar = (ImageView)convertView.findViewById(R.id.itemUserAvatar);
        name.setText(user.getName());
        Picasso.with(getContext()).load(user.getImgSrc()).into(avatar);
        return convertView;
    }
}

