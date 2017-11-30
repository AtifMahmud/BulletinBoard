package com.example.bulletinboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.bulletinboard.User.getUserById;

/**
 * Created by Logan on 2017-10-14.
 */
// Extension to get arrayadapter to work with a Post
public class PostsAdapter extends ArrayAdapter<Post> {
    public PostsAdapter(Context context, ArrayList<Post> posts){
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View tempView = convertView;
        // get the item at this position
        Post post = getItem(position);

        User owner = getUserById(post.getUserId());
        // inflate the view if the existing view isn't being reused
        if (convertView == null)
            tempView = LayoutInflater.from(getContext()).inflate(R.layout.item_post, parent, false);

        // get and post data in field
        TextView title = (TextView) tempView.findViewById(R.id.title);
        TextView description = (TextView) tempView.findViewById(R.id.description);
        TextView phone = (TextView) tempView.findViewById(R.id.showPhone);
        TextView email = (TextView) tempView.findViewById(R.id.showEmail);
        TextView date = (TextView) tempView.findViewById(R.id.date);
        TextView phoneHeader = (TextView) tempView.findViewById(R.id.showPhoneHeader);
        TextView emailHeader = (TextView) tempView.findViewById(R.id.showEmailHeader);

        title.setText(post.getTitle());
        description.setText(post.getDescription());
        if(!post.getShowPhone()) {
            phone.setVisibility(View.INVISIBLE);
            phoneHeader.setVisibility(View.INVISIBLE);
        }
        phone.setText(owner.getPhone());
        if(!post.getShowEmail()) {
            phone.setVisibility(View.INVISIBLE);
            emailHeader.setVisibility(View.INVISIBLE);
        }
        email.setText(owner.getEmail());
        date.setText(post.getDate());

        // Return the rendered view
        return tempView;
    }

}
