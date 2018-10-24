package com.nomad.volley;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nomad.domain.GankBean;
import com.nomad.network.GsonRequest;
import com.nomad.network.NetworkListener;
import com.nomad.network.NetworkManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mTvMain;
    private ImageView mImageMain;
    private NetworkImageView mNetworkImageView;
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

        mTvMain = findViewById(R.id.tv_main);
        mImageMain = findViewById(R.id.image_main);
        mNetworkImageView = findViewById(R.id.image_network);

//        onStartStringRequest();
//        onStartJsonObjectRequest();
//        onStartJsonArrayRequest();
//        onStartImageRequest();
//        onStartGsonRequest();

        mNetworkImageView.setImageUrl(
                "https://pgw.udn.com.tw/gw/photo.php?u=https://uc.udn.com.tw/photo/2017/12/18/4/4351478.jpg&x=0&y=0&sw=0&sh=0&sl=W&fw=500&exp=3600",
                NetworkManager.getInstance().getmImageLoader());
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

    public void onStartJsonArrayRequest() {
        String url = "https://api.github.com/users/huntingboy/repos";
        Response.Listener<JSONArray> mJsonArrayListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    String name = response.getJSONObject(0).getString("name");
                    Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                    String text = "";
                    for (int i = 0; i < response.length(); i++) {
                        text = text + response.getJSONObject(i).getString("name") + "\n";
                    }
                    mTvMain.setText(text);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } ;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, mJsonArrayListener, mErrorListener);
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    public void onStartImageRequest() {
        String url = "http://f6.13322cc.com/f/20180116/sport/article/i/9e55cec46d674590a0b999cae534c33c.jpg";
        Response.Listener<Bitmap> mBitmapListener = new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                mImageMain.setImageBitmap(response);
            }
        };
        ImageRequest imageRequest = new ImageRequest(url, mBitmapListener, 0, 0, Bitmap.Config.RGB_565, mErrorListener);
        Volley.newRequestQueue(this).add(imageRequest);
    }


    public void onStartGsonRequest() {
        String url = "http://gank.io/api/data/Android/10/1";
        NetworkListener<GankBean> networkListener = new NetworkListener<GankBean>(){
            @Override
            public void onResponse(GankBean response) {
                String desc = response.getResults().get(0).getDesc();
                Toast.makeText(MainActivity.this, desc, Toast.LENGTH_SHORT).show();
                mTvMain.setText(desc);
            }
        };

        GsonRequest<GankBean> request = new GsonRequest<>(GankBean.class, url, networkListener);
        NetworkManager.getInstance().sendRequest(request);
    }
}
