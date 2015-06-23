package org.weread.dao;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.weread.model.AccessToken;
import org.weread.model.UserInfo;
import org.weread.model.Users;

public class UserInfoKeeper {
    private static final String PREFERENCES_NAME = "org_weread_sdk_android";
    private static final String  KEY_IS_FIRST_ART_TYPE="isFirstArtType";
    private static final String KEY_IS_FIRST_GOOD = "isFirstGood";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_OLD = "old";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDR = "addr";
    private static final String KEY_THUMB = "thumb";

    public static void writeUserInfo(Context context, UserInfo userInfo) {
        if (null == context || null == userInfo || userInfo.getUsers() == null) {
            return;
        }

        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(KEY_USERNAME, userInfo.getUsers().getUsername());
        editor.putInt(KEY_OLD, userInfo.getOld());
        editor.putString(KEY_GENDER, userInfo.getGender());
        editor.putString(KEY_EMAIL, userInfo.getUsers().getEmail());
        editor.putString(KEY_ADDR, userInfo.getAddr());
        editor.commit();
    }

    public static UserInfo readUserInfo(Context context) {
        if (null == context) {
            return null;
        }

        UserInfo userInfo = new UserInfo();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        userInfo.setOld(pref.getInt(KEY_OLD, 0));
        userInfo.setAddr(pref.getString(KEY_ADDR, "...."));
        userInfo.setGender(pref.getString(KEY_GENDER, "....."));
        Users users = new Users();
        users.setUsername(pref.getString(KEY_USERNAME, ""));
        users.setEmail(pref.getString(KEY_EMAIL, "....@...@.."));
        userInfo.setUsers(users);
        return userInfo;
    }

    public static void setIsFirstGood(Context context) {
        if (null == context) {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putBoolean(KEY_IS_FIRST_GOOD, false);
        editor.commit();
    }

    public static boolean isFirstGood(Context context) {
        if (null == context) {
            return true;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        return pref.getBoolean(KEY_IS_FIRST_GOOD, true);
    }
    public static void setUserThumb(Context context,String url) {
        if (null == context) {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        url = url.trim();
        editor.putString(KEY_THUMB, url);
        editor.commit();
    }
    public static String getUserThumb(Context context) {
        if (null == context) {
            return "";
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        return pref.getString(KEY_THUMB, "").trim();
    }

    public static void setUserThumbBg(Context context,String url) {
        if (null == context) {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        url = url.trim();
        editor.putString(KEY_THUMB+"Bg", url);
        editor.commit();
    }
    public static String getUserThumbBg(Context context) {
        if (null == context) {
            return "";
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        return pref.getString(KEY_THUMB+"Bg", "").trim();
    }

    public static void setIsFirstArtType(Context context) {
        if (null == context) {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putBoolean(KEY_IS_FIRST_ART_TYPE, false);
        editor.commit();
    }

    public static boolean isFirstArtType(Context context) {
        if (null == context) {
            return true;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        return pref.getBoolean(KEY_IS_FIRST_ART_TYPE, true);
    }
    public static void clear(Context context) {
        if (null == context) {
            return;
        }

        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
