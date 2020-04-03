package com.khosla.assignment.Singleton;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mcontext;


    private MySingleton(Context context){
        mcontext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context){
        if (mInstance == null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(mcontext.getApplicationContext());
        }
        return requestQueue;
    }

    public <T>void addRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}