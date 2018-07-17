package com.projectex.projectex;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tab2 extends Fragment implements MovieAdapter.ItemClickListener {

    public MovieAdapter.ItemClickListener itemClickListener;
    private RecyclerView mList;
    private GridLayoutManager gridLayoutManager;
    private MovieAdapter adapter;
    private List<Result> movieList = new ArrayList<>();
    private Movie movie;
    private RequestQueue requestQueue;
    private String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=f1ca3a4317c47fd219821e560d10684c&language=en-US&page=1";
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);

        // set up the RecyclerView

        mList = view.findViewById(R.id.rviewmovies);
        adapter = new MovieAdapter(getContext(), movieList);
        adapter.setClickListener(this);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=f1ca3a4317c47fd219821e560d10684c&language=en-US&page=1";

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        gson = new Gson();
                        movie = gson.fromJson(response.toString(), Movie.class);
                        adapter.update(movie.results);
                        int numberOfColumns = 2;
                        gridLayoutManager = new GridLayoutManager(getContext(), numberOfColumns);
                        mList.setLayoutManager(gridLayoutManager);
                        mList.setAdapter(adapter);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);


        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(getActivity(), "callback called", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(),MovieDetails.class);
        intent.putExtra("id",String.valueOf(movie.results.get(position).id));
        startActivity(intent);

    }

}

