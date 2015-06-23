package org.weread;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.gc.materialdesign.widgets.SnackBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.weread.adapter.DrawerListAdapter;
import org.weread.api.AccessTokenKeper;
import org.weread.api.UserApi;
import org.weread.callable.OnItemClickListener;
import org.weread.dao.UserInfoKeeper;
import org.weread.model.AccessToken;
import org.weread.model.Article;
import org.weread.model.DrawerSelectItem;
import org.weread.model.FormImage;
import org.weread.model.UserInfo;
import org.weread.network.ResponseListener;
import org.weread.util.Util;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.InjectView;

/**
 * Created by GaverChou on 2015-06-19.
 */
public class MainActivity extends BaseActivity {
    @InjectView(R.id.drawer)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.listView)
    RecyclerView mDrawerSeletItemListv;
    @InjectView(R.id.drawer_layout_touxiang_img)
    ImageView mUserThumbImg;
    HomeFragment homeFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerListAdapter mDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.main_content_panel);
    }

    @Override
    protected void initView() {
        super.initView();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerSeletItemListv.setLayoutManager(new LinearLayoutManager(this));
        mDrawerSeletItemListv.setHasFixedSize(true);
        mDrawerAdapter = new DrawerListAdapter(this, getDrawerSeletorData());
        mDrawerSeletItemListv.setAdapter(mDrawerAdapter);
    }

    protected ProgressDialog progressDialog;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult",requestCode+"|"+resultCode);
        if (requestCode==262144&&resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (uri!=null){
                Bitmap cameraBitmap = Util.getBitmapFromUri(this, uri);
                progressDialog = new ProgressDialog(this, "上传中。。");
                progressDialog.show();
                AccessToken token = AccessTokenKeper.readAccessToken(this);
                List<FormImage> imageList = new ArrayList<FormImage>();
                FormImage formImage = new FormImage(cameraBitmap);
                formImage.setmName(token.getUid() + "");
                formImage.setmFileName(token.getUid() + UUID.randomUUID().toString() +"touxiang.png");
                imageList.add(formImage);
                uploadImg(imageList);
            }
        } else if (requestCode==262145&&resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (uri!=null){
                Bitmap cameraBitmap = Util.getBitmapFromUri(this, uri);
                progressDialog = new ProgressDialog(this, "上传中。。");
                progressDialog.show();
                AccessToken token = AccessTokenKeper.readAccessToken(this);
                List<FormImage> imageList = new ArrayList<FormImage>();
                FormImage formImage = new FormImage(cameraBitmap);
                formImage.setmName(token.getUid() + "");
                formImage.setmFileName(token.getUid() + UUID.randomUUID().toString() +"touxiang.png");
                imageList.add(formImage);
                uploadImgBg(imageList);
            }
        }else {
            new SnackBar(this, "选择图片失败,请重新选择")
                    .show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void uploadImgBg(List<FormImage> imageList) {
        UserApi.uploadImgBg(imageList, new ResponseListener<String>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("onErrorResponse", "===========VolleyError=========" + error);
                new SnackBar(MainActivity.this, "上传图片失败,网络问题")
                        .show();
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Map<String, String> date = gson.fromJson(response, new TypeToken<Map<String, String>>() {
                }.getType());
                Log.v("onResponse", "===========onResponse=========" + response);
                String imgUrl = date.get("imgURl");
                if (imgUrl != null && !"".equals(imgUrl)) {
                    UserInfoKeeper.setUserThumbBg(MainActivity.this, imgUrl);
                    new SnackBar(MainActivity.this, "上传图片成功")
                            .show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void uploadImg(List<FormImage> imageList) {
        UserApi.uploadImg(imageList, new ResponseListener<String>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("onErrorResponse", "===========VolleyError=========" + error);
                new SnackBar(MainActivity.this, "上传图片失败,网络问题")
                        .show();
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Map<String, String> date = gson.fromJson(response, new TypeToken<Map<String, String>>() {
                }.getType());
                Log.v("onResponse", "===========onResponse=========" + response);
                String imgUrl = date.get("imgURl");
                if (imgUrl != null && !"".equals(imgUrl)) {
                    UserInfoKeeper.setUserThumb(MainActivity.this, imgUrl);
                    new SnackBar(MainActivity.this, "上传图片成功")
                            .show();
                }
                progressDialog.dismiss();
            }
        });
    }

    public static ArrayList<DrawerSelectItem> getDrawerSeletorData() {
        ArrayList<DrawerSelectItem> mDrawerSelectItems = new ArrayList<>();
        mDrawerSelectItems.add(new DrawerSelectItem(R.string.home));
        mDrawerSelectItems.add(new DrawerSelectItem(R.string.find_label));
        mDrawerSelectItems.add(new DrawerSelectItem(R.string.love_article));

        mDrawerSelectItems.add(new DrawerSelectItem(R.string.me));
        mDrawerSelectItems.add(new DrawerSelectItem(R.string.action_settings));
        mDrawerSelectItems.add(new DrawerSelectItem(R.string.zanzhu));
        return mDrawerSelectItems;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mDrawerAdapter.setItemOnclickListener(new DrawerItemClickListener());
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    class DrawerItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(View view, int pos, Object obj) {
            switch (pos) {
                case 0:
                case 1:
                case 2:
                case 3:
                    homeFragment.getmViewPager().setCurrentItem(pos);
                    break;
                case 4:
                    break;
                case 5:
                    logout();
                    break;
            }
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void logout() {
        AccessToken token = AccessTokenKeper.readAccessToken(this);
        UserApi.logout(token, new ResponseListener<Boolean>() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

            @Override
            public void onResponse(Boolean response) {
                AccessTokenKeper.clear(MainActivity.this);
                new SnackBar(MainActivity.this, "注销成功！").show();
            }
        });
    }
}
