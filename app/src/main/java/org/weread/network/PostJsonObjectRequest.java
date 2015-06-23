package org.weread.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by mrgaver on 15-6-12.
 */
public class PostJsonObjectRequest<T> extends JsonRequest<T> {
    /**
     * 正确数据的时候回掉用
     */
    protected ResponseListener mListener;
    /*用来解析 json 用的*/
    protected Gson mGson;
    /*在用 gson 解析 json 数据的时候，需要用到这个参数*/
    protected Type mClazz;

    public PostJsonObjectRequest(String url, String requestBody, Type type, ResponseListener listener) {
        super(Method.POST, url, requestBody, listener, listener);
        this.mListener = listener;
//        mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        mGson = new Gson();
        mClazz = type;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            T result;
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            result = mGson.fromJson(jsonString, mClazz);
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
