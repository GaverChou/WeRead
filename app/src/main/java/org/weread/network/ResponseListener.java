package org.weread.network;

import com.android.volley.Response;

/**
 * Created by mrgaver on 15-6-12.
 */
public interface ResponseListener<T> extends Response.ErrorListener,Response.Listener<T> {

}