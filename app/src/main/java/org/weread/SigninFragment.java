package org.weread;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.gc.materialdesign.widgets.SnackBar;

import org.weread.api.UserApi;
import org.weread.model.Users;
import org.weread.network.ResponseListener;
import org.weread.util.Util;

import java.util.Map;

import butterknife.InjectView;

/**
 * Created by GaverChou on 2015-06-20.
 */
public class SigninFragment extends BaseFragment<LoginAndSigninActivity> {
    @InjectView(R.id.signin_username_tv)
    TextView mUserNameTv;
    @InjectView(R.id.signin_passwd_tv)
    TextView mPasswdTv;
    @InjectView(R.id.signin_passwd_repeat_tv)
    TextView mPasswdRepeatTv;
    @InjectView(R.id.signin_email_tv)
    TextView mEmailTv;
    @InjectView(R.id.signin_register_btn)
    Button mConfirmBtn;
    private static SigninFragment mInstance = null;

    public synchronized static SigninFragment getInstance() {
        if (mInstance == null) {
            mInstance = new SigninFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, savedInstanceState, R.layout.fragment_signin);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mConfirmBtn.setOnClickListener(new RegisterBtnListener());
    }

    class RegisterBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Users users = new Users();
            users.setUsername(mUserNameTv.getText().toString().trim());
            users.setPasswd(mPasswdTv.getText().toString().trim());
            users.setEmail(mEmailTv.getText().toString().trim());
            String passwdRepeat = mPasswdRepeatTv.getText().toString().trim();
            if (!Util.showSnackBarByTag(mActivity, Util.checkUser(users, passwdRepeat))) {
                return;
            }
            progressDialog = new ProgressDialog(mActivity, "请稍后。。");
            progressDialog.show();
            UserApi.register(users, new ResponseListener<Map<String, Object>>() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TAG", error.toString());
                    setProgressDismiss();
                    new SnackBar(mActivity,
                            "注册失败！服务器连接异常！").show();
                }

                @Override
                public void onResponse(Map<String, Object> response) {
                    Log.d("TAG", response.toString());
                    setProgressDismiss();
                    new SnackBar(mActivity,
                            response.get("msg").toString()).show();
                }
            });
        }
    }
}
