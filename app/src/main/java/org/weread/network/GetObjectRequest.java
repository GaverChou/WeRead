package org.weread.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by mrgaver on 15-6-20.
 */
public class GetObjectRequest<T> extends Request<T> {
    /**
     * 正确数据的时候回掉用
     */
    private ResponseListener mListener;
    /*用来解析 json 用的*/
    private Gson mGson;
    /*在用 gson 解析 json 数据的时候，需要用到这个参数*/
    private Type mClazz;

    public GetObjectRequest(String url, Type type, ResponseListener listener) {
        super(Request.Method.GET, url, listener);
        this.mListener = listener;
        mGson = new Gson();
        mClazz = type;
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
