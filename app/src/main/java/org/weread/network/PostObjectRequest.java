package org.weread.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by mrgaver on 15-6-20.
 */
public class PostObjectRequest<T> extends Request<T> {

    /**
     * 正确数据的时候回掉用
     */
    private ResponseListener mListener;
    /*用来解析 json 用的*/
    private Gson mGson;
    /*在用 gson 解析 json 数据的时候，需要用到这个参数*/
    private Type mClazz;
    /*请求 数据通过参数的形式传入*/
    private Map<String, String> mParams;

    public PostObjectRequest(String url, Map<String, String> params, Type type, ResponseListener listener) {
        super(Method.POST, url, listener);
        this.mListener = listener;
        mGson = new Gson();
        mClazz = type;
        setShouldCache(false);
        mParams = params;
    }

    //mMap是已经按照前面的方式,设置了参数的实例
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    /**
     * 这里开始解析数据
     *
     * @param response Response from the network
     * @return
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            T result;
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, DEFAULT_PARAMS_ENCODING));
            Log.d("jsonString",jsonString);
            result = mGson.fromJson(jsonString, mClazz);
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    /**
     * 回调正确的数据
     *
     * @param response The parsed response returned by
     */
    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}
