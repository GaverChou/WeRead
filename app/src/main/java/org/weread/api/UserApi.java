package org.weread.api;

import android.graphics.Bitmap;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;

import org.weread.model.AccessToken;
import org.weread.model.Article;
import org.weread.model.FormImage;
import org.weread.model.UserInfo;
import org.weread.model.Users;
import org.weread.network.GetObjectRequest;
import org.weread.network.PostObjectRequest;
import org.weread.network.PostUploadRequest;
import org.weread.network.ResponseListener;
import org.weread.network.VolleyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GaverChou on 2015-06-20.
 */
public class UserApi extends BaseApi {
    public final static String REGISTER_URL = BASE_URL + "register";
    public final static String LOGIN_URL = BASE_URL + "login";
    public final static String LOGOUT_URL = BASE_URL + "logout";
    public final static String GET_INFO_URL = BASE_URL + "getUserMessage";
    public final static String UPDATE_INFO_URL = BASE_URL + "editUserMessage";
    public final static String UPLOAD_IMG_URL = BASE_URL + "upload";
    public final static String UPLOAD_IMG_BG_URL = BASE_URL + "uploadUserImgBg";

    public static void register(Users user, ResponseListener listener) {
        Map<String, String> param = new HashMap<>();
        param.put("username", user.getUsername());
        param.put("password", user.getPasswd());
        param.put("email", user.getEmail());
        Request request = new PostObjectRequest(REGISTER_URL, param, new TypeToken<Map<String, Object>>() {
        }.getType(), listener);
        VolleyUtil.getRequestQueue().add(request);
    }

    public static void getUserInfoById(String uid, ResponseListener listener) {
        Map<String, String> param = new HashMap<>();
        param.put("uid", uid);
        Request request = new PostObjectRequest(GET_INFO_URL, param, new TypeToken<UserInfo>() {
        }.getType(), listener);
        VolleyUtil.getRequestQueue().add(request);
    }

    public static void updateUserInfoById(UserInfo userInfo, ResponseListener listener) {
        Map<String, String> param = new HashMap<>();
        param.put("uid", userInfo.getUsers().getUid() + "");
        param.put("gender", userInfo.getGender());
        param.put("addr", userInfo.getAddr());
        param.put("old", userInfo.getOld() + "");
        Request request = new PostObjectRequest(UPDATE_INFO_URL, param, new TypeToken<Boolean>() {
        }.getType(), listener);
        VolleyUtil.getRequestQueue().add(request);
    }

    public static void login(Users user, ResponseListener listener) {
        Map<String, String> param = new HashMap<>();
        param.put("username", user.getUsername());
        param.put("password", user.getPasswd());
        Request request = new PostObjectRequest(LOGIN_URL, param, new TypeToken<AccessToken>() {
        }.getType(), listener);
        VolleyUtil.getRequestQueue().add(request);
    }

    public static void logout(AccessToken token, ResponseListener listener) {
        Map<String, String> param = new HashMap<>();
        param.put("token", token.getToken());
        Request request = new PostObjectRequest(LOGOUT_URL, param, new TypeToken<Boolean>() {
        }.getType(), listener);
        VolleyUtil.getRequestQueue().add(request);
    }

    public static void uploadImg( List<FormImage> imageList, ResponseListener listener) {
        Request request = new PostUploadRequest(UPLOAD_IMG_URL, imageList, listener);
        VolleyUtil.getRequestQueue().add(request);
    }
    public static void uploadImgBg( List<FormImage> imageList, ResponseListener listener) {
        Request request = new PostUploadRequest(UPLOAD_IMG_BG_URL, imageList, listener);
        VolleyUtil.getRequestQueue().add(request);
    }
}
