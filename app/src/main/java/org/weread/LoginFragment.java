package org.weread;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.gc.materialdesign.widgets.SnackBar;

import org.weread.api.AccessTokenKeper;
import org.weread.api.UserApi;
import org.weread.model.AccessToken;
import org.weread.model.Users;
import org.weread.network.ResponseListener;
import org.weread.util.Util;

import java.util.Map;

import butterknife.InjectView;

/**
 * Created by GaverChou on 2015-06-20.
 */
public class LoginFragment extends BaseFragment<LoginAndSigninActivity> {
    @InjectView(R.id.login_username_edittv)
    TextView mUserNameTv;
    @InjectView(R.id.login_passwd_edittv)
    TextView mPassWdTv;
    @InjectView(R.id.login_confirm_btn)
    Button mLoginBtn;
    private static LoginFragment mInstance = null;

    public synchronized static LoginFragment getInstance() {
        if (mInstance == null) {
            mInstance = new LoginFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, savedInstanceState, R.layout.fragment_login);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login() {
        Users users = new Users();
        users.setUsername(mUserNameTv.getText().toString().trim());
        users.setPasswd(mPassWdTv.getText().toString().trim());
        if (!Util.showSnackBarByTag(mActivity, Util.checkUser(users))) {
            return;
        }
        progressDialog = new ProgressDialog(mActivity, "请稍后。。");
        progressDialog.show();
        UserApi.login(users, new ResponseListener<AccessToken>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", error.toString());
                setProgressDismiss();
                new SnackBar(mActivity,
                        "登录失败！服务器连接异常！").show();
            }

            @Override
            public void onResponse(AccessToken response) {
                Log.d("TAG", response.toString());
                setProgressDismiss();
                if (response.getUid() == 606 || response.getUid() == 2) {
                    new SnackBar(mActivity,
                            response.getToken()).show();
                } else {
                    new SnackBar(mActivity, "登录成功！").show();
                    AccessTokenKeper.writeAccessToken(mActivity,response);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mActivity.finish();
                        }
                    }, 1000);
                }
            }
        });
    }
}
