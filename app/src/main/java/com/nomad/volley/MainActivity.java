package com.nomad.volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, Thread.currentThread().toString());
            Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        onStartStringRequest();
        onStartJsonObjectRequest();
    }

    public void onStartStringRequest() {
        String url = "https://www.baidu.com";
        Response.Listener<String> mStringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, Thread.currentThread().toString());
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        };

        StringRequest stringRequest = new StringRequest(url, mStringListener, mErrorListener);
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void onStartJsonObjectRequest() {
        String url = "http://api.github.com/users/huntingboy/repos";
        Response.Listener<JSONObject> mJsonObjectListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    String desc = response.getJSONArray("results")
//                            .getJSONObject(0)
//                            .getString("desc");
                    String desc = response.getJSONObject("owner").getString("login");
                    Toast.makeText(MainActivity.this, desc, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, mJsonObjectListener, mErrorListener);
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
