package one.com.contentproviderhi1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.R.attr.logoDescription;
import static android.R.attr.version;
import static android.content.ContentValues.TAG;

/**
 * Created by shixing on 2017/5/15.
 */

public class MySqliteHelper extends SQLiteOpenHelper {
    public static final String TABLE_USER = "user";
    public static final String TABLE_BOOK = "book";
    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " ("
            + "id INTEGER PRIMARY KEY ,name TEXT)";
    public static final String CREATE_TABLE_BOOK = "CREATE TABLE  " + TABLE_BOOK + " ("
            + "id INTEGER PRIMARY KEY ,name TEXT)";
    private Context mContext;
    public MySqliteHelper(Context context) {
        super(context, "haha.db", null, 2);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: " + Thread.currentThread().getName());
    }
}
