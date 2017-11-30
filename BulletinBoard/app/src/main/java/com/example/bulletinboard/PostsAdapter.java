package com.example.bulletinboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bulletinboard.network.GetJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Logan on 2017-10-14.
 */
// Extension to get arrayadapter to work with a Post
public class PostsAdapter extends ArrayAdapter<Post> {
    public PostsAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View tempView = convertView;
        // get the item at this position
        Post post = getItem(position);

        // inflate the view if the existing view isn't being reused
        if (convertView == null)
            tempView = LayoutInflater.from(getContext()).inflate(R.layout.item_post, parent, false);

        // get and post data in field
        TextView title = (TextView) tempView.findViewById(R.id.title);
        TextView description = (TextView) tempView.findViewById(R.id.description);
        TextView user = (TextView) tempView.findViewById(R.id.user);
        TextView date = (TextView) tempView.findViewById(R.id.date);

        getUserData(post.getUserId(),user);

        title.setText(post.getTitle());
        description.setText(post.getDescription());

        date.setText(post.getDate());

        // Return the rendered view
        return tempView;
    }

    private void getUserData(String id, TextView user){
        GetJSONObjectRequest request = GetJSONObjectRequest.getUser(id, new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

                    JSONObject data = response.getJSONObject("user");
                    user.setText(" " + data.getString("first_name") + " " + data.getString("last_name"));
                }
                catch (org.json.JSONException e){

                }
            }

            @Override
            public void onFailure() {
                Log.d("Failure","Counldn't get User Info");
            }
        });

        request.send();
    }


}
