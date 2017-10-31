package com.example.bulletinboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Logan on 2017-10-14.
 */
// Extension to get arrayadapter to work with a Post
public class PostsAdapter extends ArrayAdapter<Post> {
    public PostsAdapter(Context context, ArrayList<Post> posts){
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View tempView = convertView;
        // get the item at this position
        Post post = getItem(position);
        // inflate the view if the existing view isn't being reused
        if(convertView == null)
            tempView = LayoutInflater.from(getContext()).inflate(R.layout.item_post, parent, false);

        // get and post data in field
        TextView title = (TextView) tempView.findViewById(R.id.title);
        TextView description = (TextView) tempView.findViewById(R.id.description);
        TextView showPhone = (TextView) tempView.findViewById(R.id.showPhone);
        TextView showEmail = (TextView) tempView.findViewById(R.id.showEmail);
        TextView date = (TextView) tempView.findViewById(R.id.date);

        title.setText(post.getTitle());
        description.setText(post.getDescription());
        showPhone.setText(Boolean.toString(post.getShowPhone()));
        showEmail.setText(Boolean.toString(post.getShowEmail()));
        date.setText(post.getDate());

        // Return the rendered view
        return tempView;
    }
}
