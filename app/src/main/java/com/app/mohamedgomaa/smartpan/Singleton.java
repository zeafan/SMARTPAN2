package com.app.mohamedgomaa.smartpan;

/**
 * Created by Mohamed Gooma on 7/7/2017.
 */
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
public class Singleton {
        private static Singleton mySingleTon;
        private Context _context;
        RequestQueue requestQueue;

        public Singleton(Context _context) {
            this._context = _context;
            requestQueue = myRequest();
        }

        public RequestQueue myRequest() {
            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(_context.getApplicationContext());
            }
            return requestQueue;
        }

        public static synchronized Singleton mySingleton(Context context)
        {
            if(mySingleTon==null)
            {
                mySingleTon=new Singleton(context);
            }
            return mySingleTon;
        }
        public<T> void addToRequestQueue(Request<T> request)
        {
            myRequest().add(request);
        }
    }