package org.weread.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.weread.R;

/**
 * Created by mrgaver on 15-6-11.
 */
public class NetworkImageLoader {
    protected static com.android.volley.toolbox.ImageLoader imageLoader;

    public synchronized static void initLoader(Context context) {
        if (imageLoader == null) {
            imageLoader = new com.android.volley.toolbox.ImageLoader(VolleyUtil.getRequestQueue(), new BitmapCache());
        }
    }

    public static void loadIntoNetworkImageView(NetworkImageView networkImageView, String imgUrl, int errorImgResId) {
        networkImageView.setImageUrl(imgUrl, imageLoader);
        networkImageView.setDefaultImageResId(errorImgResId);
        networkImageView.setErrorImageResId(errorImgResId);
    }

    public static void loadNetworkImage(ImageView imageView, String imgUrl) {
//        ImageRequest irequest = new ImageRequest(
//                imgUrl, listener, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError arg0) {
//            }
//        });
//        VolleyUtil.getRequestQueue().add(irequest);

        ImageLoader imageLoader = new ImageLoader(VolleyUtil.getRequestQueue(), new BitmapCache());

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.mipmap.img1, R.mipmap.img1);
        imageLoader.get(imgUrl, listener);
    }
}
