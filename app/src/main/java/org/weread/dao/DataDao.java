package org.weread.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by mrgaver on 15-6-13.
 */
public abstract class DataDao {
    private static final String TAG = "DataDao";
    protected static final String DATABASE_NAME = "Tunnel.db";//建立数据库名
    protected SQLiteDatabase db;
    protected Context context;

    public DataDao(Context context) {
        this.context = context;
        db = context.openOrCreateDatabase(DATABASE_NAME, 0, null);
        Log.v(TAG, "db path=" + db.getPath());
    }

    public void Close() {
        db.close();
    }
}
