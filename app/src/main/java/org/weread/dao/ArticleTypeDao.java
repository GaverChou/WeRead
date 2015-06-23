package org.weread.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.weread.model.ArtcleType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrgaver on 15-6-13.
 */
public class ArticleTypeDao extends DataDao {

    private static final String TAG = "ArticleTypeDao";
    protected static final String TAB_NAME = "ArticleType";

    public ArticleTypeDao(Context context) {
        super(context);
        createTable();
    }

    public void createTable() {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TAB_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT,type varchar(16),type_url varchar(256)," +
                    "type_count INTEGER);");//INTEGER为整型
            Log.v(TAG, "Create Table " + TAB_NAME + " ok");
        } catch (Exception e) {
            Log.v(TAG, "Create Table " + TAB_NAME + " err,table exists.");
        }
    }

    public void addActionResult(List<ArtcleType> artcleTypeList) {
        db.beginTransaction();  //开始事务
        try {
            for (ArtcleType result : artcleTypeList) {
                db.execSQL("INSERT INTO " + TAB_NAME + " VALUES(null,?,?,?)",
                        new Object[]{result.getType(), result.getTypeUrl(), result.getTypeCount()});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    public List<ArtcleType> query() {
        ArrayList<ArtcleType> artcleTypeResults = new ArrayList<ArtcleType>();
        Cursor c = null;
        try {
            c = queryTheCursor();
            while (c.moveToNext()) {
                ArtcleType result = new ArtcleType();
                result.setType(c.getString(c.getColumnIndex("type")));
                result.setTypeUrl(c.getString(c.getColumnIndex("type_url")));
                result.setTypeCount(c.getInt(c.getColumnIndex("type_count")));
                artcleTypeResults.add(result);
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return artcleTypeResults;
    }

    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM " + TAB_NAME, null);
        return c;
    }

    public void deleteAll() {
        db.execSQL("delete from " + TAB_NAME);
        Log.v(TAG, "Delete all ActionResult!");
    }
}
