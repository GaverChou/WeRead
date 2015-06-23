package org.weread.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.weread.model.Article;
import org.weread.model.ArticleItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by GaverChou on 2015-06-21.
 */
public class CollectionDao extends DataDao {
    private static final String TAG = "CollectionDao";
    protected static final String TAB_NAME = "Collection";

    public CollectionDao(Context context) {
        super(context);
        createTable();
    }

    public void createTable() {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TAB_NAME + " (_ID INTEGER PRIMARY KEY,title varchar(128),author varchar(256)," +
                    "create_time text," +
                    "collect_count int," +
                    "content text," +
                    "sumary text," +
                    "img_url varchar(256)," +
                    "type varchar(16));");//INTEGER为整型
            Log.v(TAG, "Create Table " + TAB_NAME + " ok");
        } catch (Exception e) {
            Log.v(TAG, "Create Table " + TAB_NAME + " err,table exists.");
        }
    }

    public void addActionResult(Article result) {
        db.beginTransaction();  //开始事务
        try {
            db.execSQL("INSERT INTO " + TAB_NAME + " VALUES(?,?,?,?,?,?,?,?,?)",
                    new Object[]{result.getAid(), result.getTitle(), result.getAuthor(), result.getCreateTime().toString(),
                            result.getCollectCount(), result.getContent(), result.getSumary(), result.getImgurl(),
                            result.getArticleType()});
            db.setTransactionSuccessful();  //设置事务成功完成
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    public Article queryArticleByTitle(String title) {
        Cursor c = null;
        Article result = new Article();
        try {
            c = queryTheCursor(" where title='" + title + "'");
            if (c.moveToNext()) {
                result.setTitle(c.getString(c.getColumnIndex("title")));
                result.setImgurl(c.getString(c.getColumnIndex("img_url")));
                result.setContent(c.getString(c.getColumnIndex("content")));
                String dateStr = c.getString(c.getColumnIndex("create_time"));
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                    Date date = sdf.parse(dateStr);
                    result.setCreateTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return result;
            }
        } catch (Exception e) {
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return null;
    }

    public List<Article> query() {
        ArrayList<Article> actionResults = new ArrayList<Article>();
        Cursor c = null;
        try {
            c = queryTheCursor();
            while (c.moveToNext()) {
                Article result = new Article();
                result.setAid(c.getInt(c.getColumnIndex("_ID")));
                result.setTitle(c.getString(c.getColumnIndex("title")));
                result.setAuthor(c.getString(c.getColumnIndex("author")));
                String dateStr = c.getString(c.getColumnIndex("create_time"));
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                    Date date = sdf.parse(dateStr);
                    result.setCreateTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                result.setCreateTime(c.getString(c.getColumnIndex("create_time")));
                result.setCollectCount(c.getInt(c.getColumnIndex("collect_count")));
                result.setContent(c.getString(c.getColumnIndex("content")));
                result.setSumary(c.getString(c.getColumnIndex("sumary")));
                result.setImgurl(c.getString(c.getColumnIndex("img_url")));
                result.setArticleType(c.getString(c.getColumnIndex("type")));
                actionResults.add(result);
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return actionResults;
    }

    public List<ArticleItem> queryArticleItem() {
        ArrayList<ArticleItem> articleItemResults = new ArrayList<ArticleItem>();
        Cursor c = null;
        try {
            c = queryTheCursor();
            while (c.moveToNext()) {
                ArticleItem result = new ArticleItem();
                result.mArtTit = c.getString(c.getColumnIndex("title"));
                result.mImgUrl = c.getString(c.getColumnIndex("img_url"));
                result.mSumary = c.getString(c.getColumnIndex("sumary"));
                String dateStr = c.getString(c.getColumnIndex("create_time"));
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                    Date date = sdf.parse(dateStr);
                    SimpleDateFormat sdf2 = new SimpleDateFormat(
                            "MM/dd/yyyy");
                    result.mCreateTime = sdf2.format(date).toString();
                } catch (ParseException e) {
                    e.printStackTrace();
                    result.mCreateTime = "00/00/0000";
                }
                articleItemResults.add(result);
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return articleItemResults;
    }

    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM " + TAB_NAME, null);
        return c;
    }

    public Cursor queryTheCursor(String tag) {
        Cursor c = db.rawQuery("SELECT * FROM " + TAB_NAME + tag, null);
        return c;
    }
}
