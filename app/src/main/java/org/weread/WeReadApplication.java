package org.weread;

import android.app.Application;
import android.content.Context;

import org.weread.network.NetworkImageLoader;
import org.weread.network.VolleyUtil;

/**
 * Created by GaverChou on 2015-06-20.
 */
public class WeReadApplication extends Application {
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initialize();
    }

    private void initialize() {
        initRequestQueue();
        initImageLoader();
    }

    private void initImageLoader() {
        NetworkImageLoader.initLoader(mContext);
    }

    private void initRequestQueue() {
        VolleyUtil.initialize(mContext);
    }
}
