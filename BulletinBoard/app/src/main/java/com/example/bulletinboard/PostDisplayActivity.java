package com.example.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bulletinboard.network.Connection;
import com.example.bulletinboard.network.GetJSONObjectRequest;
import com.example.bulletinboard.network.PostJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;
import com.example.bulletinboard.User;

import org.json.JSONObject;

import static com.example.bulletinboard.User.updateRating;

public class PostDisplayActivity extends AppCompatActivity {

    private static Context context;

    private Post post;
    private String phone;
    private String mail;
    private TextView description;
    private Toolbar toolbar;
    private RatingBar ratingBar;
    private Boolean isFav = false;

    private static PostDisplayActivity me = new PostDisplayActivity();

    public PostDisplayActivity getInstance() {
        return me;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_display);

        Bundle b = getIntent().getExtras();
        String id = ""; // or other values
        if (b != null)
            id = b.getString("id");

        this.description = (TextView) findViewById(R.id.postDisplayDescr);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        GetJSONObjectRequest request = GetJSONObjectRequest.getPostById(id, new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                Log.d("RESPONSE", response.toString());

                try {

                    JSONObject data = response.getJSONObject("post");

                    post = new Post(data.getString("title"),
                            data.getString("description"),
                            data.getString("_id"),
                            data.getString("user_id"),
                            data.getBoolean("showPhone"),
                            data.getBoolean("showEmail"),
                            data.getString("date"));

                    displayText(post, description);
                    displayTitle(post, toolbar);
                    // Show user rating in the rating bar
                    ratingBar = (RatingBar) findViewById(R.id.ratingBar);
                    updateUserInfo(post.getUserId(), ratingBar);
                    updateFav();

                    if(!post.getShowEmail()){
                        View email = findViewById(R.id.sendEmailButton);
                        email.setVisibility(View.GONE);
                    }

                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                Log.d("failed", "Failure");
            }
        });

        request.send();
    }

    public static void displayText(Post p, TextView tv) {
        tv.setText(p.getDescription());
    }

    public static void displayTitle(Post p, Toolbar tb) {
        tb.setTitle(p.getTitle());
    }

    private void updateUserInfo(String id, RatingBar ratingBar){
        TextView user = (TextView) findViewById(R.id.userName);
        GetJSONObjectRequest request = GetJSONObjectRequest.getUser(id, new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {

                    JSONObject data = response.getJSONObject("user");
                    user.setText(" " + data.getString("first_name") + " " + data.getString("last_name"));
                    ratingBar.setRating((float) data.getDouble("rating"));
                    phone = data.getString("phone");
                    mail = data.getString("email");
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

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent intent = new Intent(this, ShowPost.class);
        startActivity(intent);
    }

    public void favAction(View view) {
        if(isFav){
            PostJSONObjectRequest request = PostJSONObjectRequest.remFav(new VolleyCallback<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {
                    isFav = false;
                    setFav(isFav);
                }

                @Override
                public void onFailure() {
                    isFav = false;
                    setFav(false);
                }
            }, post.getId());

            request.send();
        }else{
            PostJSONObjectRequest request = PostJSONObjectRequest.addFav(new VolleyCallback<JSONObject>() {
                @Override
                public void onSuccess(JSONObject response) {
                        isFav = true;
                        setFav(isFav);
                }

                @Override
                public void onFailure() {
                    isFav = false;
                    setFav(false);
                }
            }, post.getId());

            request.send();
        }
    }

    public void updateFav(){
        GetJSONObjectRequest request = GetJSONObjectRequest.getFav(post.getId(), new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    isFav = response.getBoolean("is_fav");
                    Log.d("Fav Status",response.getString("is_fav") + " " + response.getString("success"));
                    setFav(isFav);
                }
                catch(org.json.JSONException e){
                    Log.d("ERROR !!!","Problem");
                }
            }

            @Override
            public void onFailure() {
                setFav(false);
            }
        });

        request.send();
    }

    public void setFav(Boolean isFav){
        FloatingActionButton favBut = (FloatingActionButton) findViewById(R.id.favButton);
        if(isFav){
            favBut.setBackgroundResource(R.drawable.cross);
        }
        else{
            favBut.setBackgroundResource(R.drawable.plus);
        }
    }

    public void updateUsersRating(View view) {
        Log.d("RATING UPDATED", "rating has been updated");
        updateRating(ratingBar.getRating(), Connection.get().getUserId(), post.getUserId(), context);
        Intent intent = new Intent(this, ShowPost.class);
        //Bundle b = new Bundle();
        //b.putString("id", post.getUserId());
        //intent.putExtras(b);
        startActivity(intent);
    }

    public void sendEmail(View view) {
        Log.d("Sending email", "Email popup");
        // Sending email code source: "https://stackoverflow.com/questions/8994488/android-button-onclick-submit-to-email"

        Intent emailIntent;
        String feedback;

        emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mail});
        String subject = "Bulletin Board Post: " + post.getTitle();
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        startActivity(Intent.createChooser(emailIntent, "Choose your email client:"));
    }
    public void phone(View view){
        // Sending phone call code source: "https://stackoverflow.com/questions/5403308/make-a-phone-call-click-on-a-button"
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+phone));
        startActivity(callIntent);
    }
}
