package one.com.contentproviderhi1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.util.Log;

/**
 * Created by shixing on 2017/5/12.
 */

public class BookProvider extends ContentProvider {
    private static final String TAG = "BookProvider";
    private static final String AUTHORITY = "com.ahah";
    private static final int TABLE_USER_CODE = 1;
    private static final int TABLE_BOOK_CODE = 2;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY,"user",TABLE_USER_CODE);
        uriMatcher.addURI(AUTHORITY,"book",TABLE_BOOK_CODE);
    }
    private SQLiteDatabase mDb;
    private Context mContext;
    //启动该应用时就调用的方法，在ui线程被调用
    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: " + Thread.currentThread().getName());
        initData();
        mContext = getContext();
        return false;
    }

    private void initData() {
        mDb = new MySqliteHelper(getContext()).getWritableDatabase();
        mDb.execSQL("insert into " + MySqliteHelper.TABLE_USER + " values(1,'u1');");
        mDb.execSQL("insert into " + MySqliteHelper.TABLE_USER + " values(2,'u2');");
        mDb.execSQL("insert into " + MySqliteHelper.TABLE_USER + " values(3,'u3');");
        mDb.execSQL("insert into " + MySqliteHelper.TABLE_BOOK + " values(2,'b1');");
        mDb.execSQL("insert into " + MySqliteHelper.TABLE_BOOK + " values(4,'b2');");
    }

    //binder线程池里面被调用
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query: " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException();
        }
        return mDb.query(tableName,projection,selection,selectionArgs,null,null,sortOrder);
    }

    //binder线程池里面被调用
    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType: " + Thread.currentThread().getName());
        return null;
    }

    //binder线程池里面被调用
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert: " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException();
        }
        mDb.insert(tableName,null,values);
        mContext.getContentResolver().notifyChange(uri,null);
        return null;
    }

    //binder线程池里面被调用
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete: " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException();
        }
        int row = mDb.delete(tableName,selection,selectionArgs);
        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri,null);
            Log.d(TAG, "delete: success");
        }
        return row;
    }

    //binder线程池里面被调用
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update: " + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException();
        }
        int row = mDb.update(tableName,values,selection,selectionArgs);
        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri,null);
            Log.d(TAG, "update: success" );
        }
        return row;
    }

    public String getTableName(Uri uri) {
        String tableName = null;
        switch (uriMatcher.match(uri)) {
            case TABLE_USER_CODE:
                tableName = MySqliteHelper.TABLE_USER;
            case TABLE_BOOK_CODE:
                tableName = MySqliteHelper.TABLE_BOOK;
        }
        return tableName;
    }
}
