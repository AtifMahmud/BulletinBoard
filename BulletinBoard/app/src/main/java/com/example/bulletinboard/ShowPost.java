package com.example.bulletinboard;

import android.content.Intent;
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
    private ListView favListView;
    private ListView myListView;

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
        favListView = (ListView) findViewById(R.id.post_list_favs);
        myListView = (ListView) findViewById(R.id.post_list_mine);

        updatePosts();
    }

    private void updatePosts(){
        Posts posts = Posts.getInstance();

        GetJSONObjectRequest request = GetJSONObjectRequest.getAllPosts(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                posts.addPosts(response);
                displayPosts(posts, listView);
            }

            @Override
            public void onFailure() {
                Log.d("failed","Failure");
            }
        });

        GetJSONObjectRequest request2 = GetJSONObjectRequest.getFavsPosts(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                posts.addPosts(response);
                displayPosts(posts, favListView);
            }

            @Override
            public void onFailure() {
                Log.d("failed","Failure");
            }
        }, "5a1e1ec72e323670225b0abd");

        GetJSONObjectRequest request3 = GetJSONObjectRequest.getMyPosts(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                posts.addPosts(response);
                displayPosts(posts, myListView);
            }

            @Override
            public void onFailure() {
                Log.d("failed","Failure");
            }
        }, "5a1e1ec72e323670225b0abd");

        request.send();
        request2.send();
        request3.send();
    }

    private void displayPosts(Posts posts, ListView listView){
        PostsAdapter adapter = new PostsAdapter(this, posts.getPosts());
        listView.setAdapter(adapter);

        /*listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShowPost.this, PostDisplayActivity.class);
                Bundle b = new Bundle();
                b.putString("id","5a13454662c6c404f9e99ddc");
                intent.putExtras(b);
                startActivity(intent);
            }
        });*/
    }

    public void showSamplePost(View view){
        Intent intent = new Intent(this, PostDisplayActivity.class);
        Bundle b = new Bundle();
        b.putString("id","5a13454662c6c404f9e99ddc");
        intent.putExtras(b);
        startActivity(intent);
    }

    public void createPostScreen(View view){
        Intent intent = new Intent(this, CreateNewPost.class);
        startActivity(intent);
    }


}
