package org.weread.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by mrgaver on 15-6-12.
 */
public class VolleyUtil {

    private static RequestQueue mRequestQueue;

    //此种单例更安全
    public static synchronized void initialize(Context context) {
        if (mRequestQueue == null) {
            synchronized (VolleyUtil.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(context);
                }
            }
        }
        mRequestQueue.start();
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            throw new RuntimeException("请先初始化mRequestQueue");
        return mRequestQueue;
    }
}
