package com.projectex.projectex;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.projectex.projectex.Model.Example;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class MovieDetails extends AppCompatActivity {
    Example example;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle bundle = getIntent().getExtras();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.themoviedb.org/3/movie/" + bundle.getString("id") + "?api_key=f1ca3a4317c47fd219821e560d10684c&language=en-US";
        final TextView title = (TextView) findViewById(R.id.name);
        final TextView budget = findViewById(R.id.budget);
        final TextView genres = findViewById(R.id.genres);
        final TextView overView = findViewById(R.id.overview);
        final TextView vote_average = findViewById(R.id.vote_average);
        final ImageView imageView2 = findViewById(R.id.imageView2);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                example = gson.fromJson(response.toString(), Example.class);
                title.setText(example.title);
                budget.setText("Budget Cost:" + String.valueOf(example.budget));
                String genre = "";
                for(int i = 0;i<example.genres.size(); i++){
                   genre = genre + example.genres.get(i).name + "   ";
                }
                genres.setText("Genres:" + genre);
                overView.setText(example.overview);
                vote_average.setText("Vote Average:" + String.valueOf(example.vote_average));
                Picasso.with(getBaseContext()).load("https://image.tmdb.org/t/p/w500/" + example.poster_path).into(imageView2);
            }
        },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MovieDetails.this, "errorw aahya hai", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(jsonObjectRequest);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
