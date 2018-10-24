package com.nomad.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class NetworkManager {
    private static NetworkManager sNetworkManager;

    private RequestQueue mQueue;

    private ImageLoader mImageLoader;

    private static final int DEFAULT_IMAGE_CACHE_SIZE = 4 * 1024 * 1024;//4M

    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }

    public static NetworkManager getInstance() {
        if (sNetworkManager == null) {
            synchronized (NetworkManager.class) {
                if (sNetworkManager == null) {
                    sNetworkManager = new NetworkManager();
                }
            }
        }
        return sNetworkManager;
    }

    public void init(Context context) {
        mQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mQueue, new ImageLruCache(DEFAULT_IMAGE_CACHE_SIZE));
    }

    private static class ImageLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

        public ImageLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }
    }


    public void sendRequest(Request request) {
        mQueue.add(request);
    }
}
