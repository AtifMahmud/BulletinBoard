package com.example.bulletinboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;

import com.example.bulletinboard.network.GetJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;

import org.json.JSONObject;

public class ShowPost extends AppCompatActivity {

    private ListView listView;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("All");
        spec.setContent(R.id.tab1);
        spec.setIndicator("All");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Favs");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Favourites");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Mine");
        spec.setContent(R.id.tab3);
        spec.setIndicator("My Posts");
        host.addTab(spec);

        listView = (ListView) findViewById(R.id.post_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ITEM", "ITEM CLICKED");
            }
        });

        updatePosts();
    }

    private void updatePosts(){
        Posts posts = Posts.getInstance();

        GetJSONObjectRequest request = GetJSONObjectRequest.getAllPosts(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                posts.addPosts(response);
                displayPosts(posts);
            }

            @Override
            public void onFailure() {
                Log.d("failed","Failure");
            }
        });

        request.send();
    }

    private void displayPosts(Posts posts){
        PostsAdapter adapter = new PostsAdapter(this, posts.getPosts());
        listView.setAdapter(adapter);
    }


}
