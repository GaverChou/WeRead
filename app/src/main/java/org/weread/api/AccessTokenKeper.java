package org.weread.api;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.weread.model.AccessToken;

public class AccessTokenKeper {
    private static final String PREFERENCES_NAME = "org_weread_sdk_android";

    private static final String KEY_UID = "uid";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "expires_in";
    public static void writeAccessToken(Context context, AccessToken token) {
        if (null == context || null == token) {
            return;
        }

        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putInt(KEY_UID, token.getUid());
        editor.putString(KEY_ACCESS_TOKEN, token.getToken());
        editor.putLong(KEY_EXPIRES_IN, token.getExpireIn());
        editor.commit();
    }

    public static AccessToken readAccessToken(Context context) {
        if (null == context) {
            return null;
        }

        AccessToken token = new AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_APPEND);
        token.setUid(pref.getInt(KEY_UID, 0));
        token.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
        token.setExpireIn(pref.getLong(KEY_EXPIRES_IN, 0));
        return token;
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
