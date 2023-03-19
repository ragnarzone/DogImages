package com.example.dogimages;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // declaration of layout widgets
    ImageView mImageView;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialization of layout widgets
        mImageView = findViewById(R.id.dogImageView);
        nextButton = findViewById(R.id.nextDogButton);

        nextButton.setOnClickListener(View -> loadImage());

        loadImage();
    }

    // function for HTTP request
    private void loadImage(){
        RequestQueue volleyQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://dog.ceo/api/breeds/image/random";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url, null,
                (Response.Listener<JSONObject>) response -> {
                    String dogImageUrl;
                    try {
                        dogImageUrl = response.getString("message");
                        Glide.with(MainActivity.this).load(dogImageUrl).into(mImageView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                (Response.ErrorListener) error -> {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
                );
        volleyQueue.add(jsonObjectRequest);
    }
}
