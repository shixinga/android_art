package one.com.contentresolverhi1;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String TABLE_USER = "user";
    public static final String TABLE_BOOK = "book";
    private TextView tv;
    private Uri userUri = Uri.parse("content://com.ahah/" + TABLE_USER);
    private Uri bookUri = Uri.parse("content://com.ahah/" + TABLE_BOOK);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (EditText) findViewById(R.id.tv);
    }

    public void addOnClick(View view) {
        ContentValues values = new ContentValues();
        values.put("name","csx");
        getContentResolver().insert(userUri,values);
        Log.d(TAG, "addOnClick:");
    }
    public void deleteOnClick(View view) {

        String whereArgs = "id = ?";
        String selections[] = {tv.getText().toString()};
        getContentResolver().delete(userUri,whereArgs,selections);
        Log.d(TAG, "deleteOnClick: ");
    }
    public void updateOnClick(View view) {

        ContentValues values = new ContentValues();
        values.put("name","shuaige" + tv.getText().toString());
        String whereArgs = "id = ?";
        String [] selections = {tv.getText().toString()};
        getContentResolver().update(userUri,values,whereArgs,selections);
        Log.d(TAG, "updateOnClick: ");
    }
    public void queryOnClick(View view) {

        Cursor cursor = getContentResolver().query(userUri,null,null,null,null);
        if (cursor != null) {
            while(cursor.moveToNext()) {
                Log.d(TAG, "queryOnClick: user:id = " + cursor.getInt(0) + "..name=" + cursor.getString(1));
            }
        }
    }
}
