/**
 * @author GaverChou E-mail:1123666456@qq.com
 * @version Create on 2015年5月19日 下午5:21:12
 * @description
 */
package org.weread.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.gc.materialdesign.widgets.SnackBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.weread.R;
import org.weread.model.Users;

public class Util {

    public final static int USER_OK = 0x00;
    public final static int USER_EMAIL_VALID = 0x01;
    public final static int USER_PASSWD_NO_SAME = 0x02;
    public final static int USER_USERNAME_EMPTY = 0x03;
    public final static int USER_PASSWD_NO_VALID = 0x04;

    public static int checkUser(Users user, String passwdrepeat) {
        if (!checkEmail(user.getEmail())) {
            return USER_EMAIL_VALID;
        } else if (user.getUsername() == null || "".equals(user.getUsername())) {
            return USER_USERNAME_EMPTY;
        } else if (!checkPasswd(user.getPasswd())) {
            return USER_PASSWD_NO_VALID;
        } else if (!user.getPasswd().equals(passwdrepeat)) {
            return USER_PASSWD_NO_SAME;
        }
        return USER_OK;
    }

    public static int checkUser(Users user) {
        if (user.getUsername() == null || "".equals(user.getUsername())) {
            return USER_USERNAME_EMPTY;
        } else if (!checkPasswd(user.getPasswd())) {
            return USER_PASSWD_NO_VALID;
        }
        return USER_OK;
    }

    public static boolean checkEmail(String email) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

    public static boolean showSnackBarByTag(Activity context, int type) {
        switch (type) {
            case USER_EMAIL_VALID:
                new SnackBar(context, context.getResources().getString(R.string.username_invalid)).show();
                break;
            case USER_PASSWD_NO_SAME:
                new SnackBar(context, context.getResources().getString(R.string.password_no_the_sam)).show();
                break;
            case USER_USERNAME_EMPTY:
                new SnackBar(context, context.getResources().getString(R.string.username_invalid)).show();
                break;
            case USER_PASSWD_NO_VALID:
                new SnackBar(context, context.getResources().getString(R.string.password_invalid)).show();
                break;
            case USER_OK:
                return true;
        }
        return false;
    }

    public static boolean checkPasswd(String passwd) {
        String check = "^[a-z0-9A-Z_]{8,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(passwd);
        return matcher.matches();
        // return true;
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getBitmap(String path) {
//先解析图片边框的大小
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        Bitmap bm = BitmapFactory.decodeFile(path, ops);
        ops.inSampleSize = 1;
        int oHeight = ops.outHeight;
        int oWidth = ops.outWidth;
//控制压缩比
        int contentHeight = 128;
        int contentWidth = 128;
        if (((float) oHeight / contentHeight) < ((float) oWidth / contentWidth)) {
            ops.inSampleSize = (int) Math.ceil((float) oWidth / contentWidth);
        } else {
            ops.inSampleSize = (int) Math.ceil((float) oHeight / contentHeight);
        }
        ops.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, ops);
        return bm;
    }
}
