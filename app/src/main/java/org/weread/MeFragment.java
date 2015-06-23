package org.weread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Network;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.gc.materialdesign.widgets.SnackBar;

import org.weread.api.AccessTokenKeper;
import org.weread.api.UserApi;
import org.weread.dao.UserInfoKeeper;
import org.weread.model.AccessToken;
import org.weread.model.UserInfo;
import org.weread.model.Users;
import org.weread.network.NetworkImageLoader;
import org.weread.network.ResponseListener;
import org.weread.widget.RoundImageView;

import butterknife.InjectView;

/**
 * Created by GaverChou on 2015-06-19.
 */
public class MeFragment extends BaseFragment<MainActivity> {
    private static MeFragment mInstance = null;
    @InjectView(R.id.me_logined_view)
    View loginedView;
    @InjectView(R.id.me_nologin_view)
    View noLoginView;
    @InjectView(R.id.me_login_btn)
    Button loginBtn;
    @InjectView(R.id.me_gender_edt)
    EditText mGenderEdit;
    @InjectView(R.id.me_old_edt)
    EditText mOldEdit;
    @InjectView(R.id.me_email_edt)
    EditText mEmailEdit;
    @InjectView(R.id.me_addr_edt)
    EditText mAddrEdit;
    @InjectView(R.id.me_username_tv)
    TextView mUsernameTv;
    @InjectView(R.id.me_user_thumb)
    RoundImageView mUserThumb;
    @InjectView(R.id.me_profile_img)
    NetworkImageView mUserBgImg;

    public synchronized static MeFragment getInstance() {
        if (mInstance == null) {
            mInstance = new MeFragment();
        }
        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, savedInstanceState, R.layout.fragment_me);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        reSetEditText();
        loginBtn.setOnClickListener(new LoginListener());
        mUserThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 0);
            }
        });
        mUserBgImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AccessToken token = AccessTokenKeper.readAccessToken(mActivity);
        if (token.isLogin()) {
            noLoginView.setVisibility(View.GONE);
            loginedView.setVisibility(View.VISIBLE);
            getUserInfo(token);
        } else {
            noLoginView.setVisibility(View.VISIBLE);
            loginedView.setVisibility(View.GONE);
        }
    }

    private void getUserInfo(AccessToken token) {
        UserInfo userInfo = UserInfoKeeper.readUserInfo(mActivity);
        String username = userInfo.getUsers().getUsername();
//        if (username != null && !"".equals(username)) {
//            mAddrEdit.setText(userInfo.getAddr());
//            mGenderEdit.setText(userInfo.getGender());
//            mOldEdit.setText(userInfo.getOld() + "");
//            mEmailEdit.setText(userInfo.getUsers().getEmail());
//            mUsernameTv.setText(userInfo.getUsers().getUsername());
//        } else {
            UserApi.getUserInfoById(token.getUid() + "", new ResponseListener<UserInfo>() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }

                @Override
                public void onResponse(UserInfo response) {
                    if (response != null) {
                        mAddrEdit.setText(response.getAddr());
                        mGenderEdit.setText(response.getGender());
                        mOldEdit.setText(response.getOld() + "");
                        mEmailEdit.setText(response.getUsers().getEmail());
                        mUsernameTv.setText(response.getUsers().getUsername());
                        UserInfoKeeper.writeUserInfo(mActivity, response);
                    }
                }
            });
//        }
        String imgUrl = UserInfoKeeper.getUserThumb(mActivity);
        String imgUrlBg = UserInfoKeeper.getUserThumbBg(mActivity);
        NetworkImageLoader.loadIntoNetworkImageView(mUserBgImg, imgUrlBg, R.mipmap.profile_bg);
        NetworkImageLoader.loadNetworkImage(mUserThumb, imgUrl);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_me, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:// 点击返回图标事件
//                reSetEditText();
                updateUserInfo();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateUserInfo() {
        AccessToken token = AccessTokenKeper.readAccessToken(mActivity);
        UserInfo userInfo = new UserInfo();
        Users users = new Users();
        users.setUid(token.getUid());
        userInfo.setUsers(users);
        userInfo.setGender(mGenderEdit.getText().toString().trim());
        userInfo.setAddr(mAddrEdit.getText().toString().trim());
        try {
            userInfo.setOld(Integer.valueOf(mOldEdit.getText().toString().trim()));
        } catch (Exception e) {
            userInfo.setOld(0);
        }
        updateUserInfo(userInfo);
    }

    private void updateUserInfo(UserInfo userInfo) {
        UserApi.updateUserInfoById(userInfo, new ResponseListener<Boolean>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new SnackBar(mActivity, "更新失败，服务端连接错误！").show();
            }

            @Override
            public void onResponse(Boolean response) {
                new SnackBar(mActivity, "更新成功！").show();
                AccessToken token = AccessTokenKeper.readAccessToken(mActivity);
                getUserInfo(token);
            }
        });
    }

    public void reSetEditText() {
        mGenderEdit.setFocusable(!mGenderEdit.isFocusable());
        mOldEdit.setFocusable(!mOldEdit.isFocusable());
        mEmailEdit.setFocusable(!mEmailEdit.isFocusable());
        mAddrEdit.setFocusable(!mAddrEdit.isFocusable());
        mGenderEdit.setFocusableInTouchMode(!mGenderEdit.isFocusableInTouchMode());
        mOldEdit.setFocusableInTouchMode(!mOldEdit.isFocusableInTouchMode());
        mEmailEdit.setFocusableInTouchMode(!mEmailEdit.isFocusableInTouchMode());
        mAddrEdit.setFocusableInTouchMode(!mAddrEdit.isFocusableInTouchMode());
    }

    class LoginListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mActivity, LoginAndSigninActivity.class);
            startActivity(intent);
        }
    }
}
