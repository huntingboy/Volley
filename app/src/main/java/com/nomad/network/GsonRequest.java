package com.nomad.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class GsonRequest<T> extends JsonRequest<T> {

    private Gson mGson = new Gson();

    private Class<T> mClass;

    public GsonRequest(int method, String url, String requestBody, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public GsonRequest(Class<T> beanClass, String url, NetworkListener<T> listener) {
        this(Method.GET, url, null, listener, listener);
        mClass = beanClass;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsedString;
        try {
            parsedString = new String(response.data, PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
            parsedString = new String(response.data);
        }
        T result = mGson.fromJson(parsedString, mClass);
        return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
    }
}
