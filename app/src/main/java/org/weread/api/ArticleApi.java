package org.weread.api;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;

import org.weread.model.ArtcleType;
import org.weread.model.Article;
import org.weread.model.Users;
import org.weread.network.GetObjectRequest;
import org.weread.network.PostJsonObjectRequest;
import org.weread.network.PostObjectRequest;
import org.weread.network.ResponseListener;
import org.weread.network.VolleyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GaverChou on 2015-06-20.
 */
public class ArticleApi extends BaseApi {

    public final static String GET_ALL_ARTICLE_URL = BASE_URL + "getAllArtListByPage";
    public final static String GET_ARTICLE_TYPE_URL = BASE_URL + "getTypeList";
    public final static String GET_ARTICLE_URL = BASE_URL + "getArtListByPage";
    public final static String GET_ARTICLE_BY_TIT_URL = BASE_URL + "getArtByTit";
    public final static String ADD_COLLECTION_URL = BASE_URL + "addCollection";

    public static void getArticleAllByPageApi(int from, int size, ResponseListener listener) {
        String url = GET_ALL_ARTICLE_URL + "?pageNow=" + from + "&pageSize=" + size;
        Request request = new GetObjectRequest(url, new TypeToken<List<Article>>() {
        }.getType(), listener);
        VolleyUtil.getRequestQueue().add(request);
    }

    public static void getArticleByPageApi(String type, int from, int size, ResponseListener listener) {
//        String url = GET_ARTICLE_URL + "?type=" + type + "&pageNow=" + from + "&pageSize=" + size;
        Map<String,String> param = new HashMap<>();
        param.put("type",type);
        param.put("pageNow",from+"");
        param.put("pageSize",size+"");
        Request request = new PostObjectRequest(GET_ARTICLE_URL,param, new TypeToken<List<Article>>() {
        }.getType(), listener);
        VolleyUtil.getRequestQueue().add(request);
    }

    public static void getArticleTypeApi(ResponseListener listener) {
        Request request = new GetObjectRequest(GET_ARTICLE_TYPE_URL, new TypeToken<List<ArtcleType>>() {
        }.getType(), listener);
        VolleyUtil.getRequestQueue().add(request);
    }

    public static void getArticleByTitle(String title, ResponseListener listener) {
        Map<String, String> param = new HashMap<>();
        param.put("title", title);
        Request request = new PostObjectRequest(GET_ARTICLE_BY_TIT_URL, param, new TypeToken<Article>() {
        }.getType(), listener);
        VolleyUtil.getRequestQueue().add(request);
    }
    public static void updateCollection(String title, ResponseListener listener) {
        Map<String, String> param = new HashMap<>();
        param.put("title", title);
        Request request = new PostObjectRequest(ADD_COLLECTION_URL, param, new TypeToken<Boolean>() {
        }.getType(), listener);
        VolleyUtil.getRequestQueue().add(request);
    }
}
