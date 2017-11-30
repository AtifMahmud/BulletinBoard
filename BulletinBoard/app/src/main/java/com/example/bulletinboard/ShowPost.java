package com.example.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.AdapterView.*;

import com.example.bulletinboard.network.Connection;
import com.example.bulletinboard.network.GetJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;

import org.json.JSONObject;

public class ShowPost extends AppCompatActivity {

    private ListView listView;
    private ListView favListView;
    private ListView myListView;

    private Posts allPosts;
    private Posts favPosts;
    private Posts myPosts;

    private PostsAdapter allAdapter;
    private PostsAdapter favAdapter;
    private PostsAdapter myAdapter;

    private SwipeRefreshLayout tab1;
    private SwipeRefreshLayout tab2;
    private SwipeRefreshLayout tab3;

    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        TabHost host = (TabHost) findViewById(R.id.tabHost);
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

        allPosts = new Posts();
        favPosts = new Posts();
        myPosts = new Posts();

        listView = (ListView) findViewById(R.id.post_list);
        favListView = (ListView) findViewById(R.id.post_list_favs);
        myListView = (ListView) findViewById(R.id.post_list_mine);

        allAdapter = new PostsAdapter(this, allPosts.getPosts());
        listView.setAdapter(allAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("IS THIS WORKING", "YES");
                Post clickedPost = allAdapter.getItem(position);
                Intent intent = new Intent(ShowPost.this, PostDisplayActivity.class);
                Bundle b = new Bundle();
                b.putString("id", clickedPost.getId());
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        favAdapter = new PostsAdapter(this, favPosts.getPosts());
        favListView.setAdapter(favAdapter);

        favListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("IS THIS WORKING", "YES");
                Post clickedPost = favAdapter.getItem(position);
                Intent intent = new Intent(ShowPost.this, PostDisplayActivity.class);
                Bundle b = new Bundle();
                b.putString("id", clickedPost.getId());
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        myAdapter = new PostsAdapter(this, myPosts.getPosts());
        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("IS THIS WORKING", "YES");
                Post clickedPost = myAdapter.getItem(position);
                Intent intent = new Intent(ShowPost.this, PostDisplayActivity.class);
                Bundle b = new Bundle();
                b.putString("id", clickedPost.getId());
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        tab1 = (SwipeRefreshLayout) findViewById(R.id.tab1);
        tab2 = (SwipeRefreshLayout) findViewById(R.id.tab2);
        tab3 = (SwipeRefreshLayout) findViewById(R.id.tab3);

        tab1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateAllPosts();
            }
        });
        tab2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateFavPosts();
            }
        });
        tab3.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateMyPosts();
            }
        });

        updateAllPosts();
        updateFavPosts();
        updateMyPosts();
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent intent = new Intent(this, ShowPost.class);
        startActivity(intent);
    }

    private void updateAllPosts() {

        GetJSONObjectRequest request = GetJSONObjectRequest.getAllPosts(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                allPosts.addPosts(response);
                allAdapter.clear();
                allAdapter.addAll(allPosts.getPosts());
                allAdapter.notifyDataSetChanged();
                tab1.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                Log.d("failed", "Failure");
                toastError();
            }
        });

        request.send();
    }

    private void updateFavPosts() {

        GetJSONObjectRequest request2 = GetJSONObjectRequest.getFavsPosts(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                favPosts.addPosts(response);
                favAdapter.clear();
                favAdapter.addAll(favPosts.getPosts());
                favAdapter.notifyDataSetChanged();
                tab2.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                Log.d("failed", "Failure");
                toastError();
            }
        }, Connection.get().getUserId());

        request2.send();
    }

    private void updateMyPosts() {
        Log.d("MY POSTS","Retreiving posts");
        GetJSONObjectRequest request3 = GetJSONObjectRequest.getMyPosts(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("MY POSTS",response.toString());
                myPosts.addPosts(response);
                myAdapter.clear();
                myAdapter.addAll(myPosts.getPosts());
                myAdapter.notifyDataSetChanged();
                tab3.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                Log.d("failed", "Failure");
                toastError();
            }
        });

        request3.send();

    }

    private void toastError() {
        Context context = getApplicationContext();
        CharSequence text = "Error while retreving data";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    /*
    public void showSamplePost(View view){
        Intent intent = new Intent(this, PostDisplayActivity.class);
        Bundle b = new Bundle();
        b.putString("id","5a13454662c6c404f9e99ddc");
        intent.putExtras(b);
        startActivity(intent);
    }*/

    public void createPostScreen(View view) {
        Intent intent = new Intent(this, CreateNewPost.class);
        startActivity(intent);
    }
}
