package csx.haha.com.optimization.s5;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.google.gson.Gson;

import java.nio.ByteBuffer;

import csx.haha.com.optimization.R;
import csx.haha.com.optimization.s5.flatbuffer.PeopleList;
import csx.haha.com.optimization.s5.json.PeopleListJson;
import csx.haha.com.optimization.utils.Utils;

/**
 * Created by sunray on 2018-3-24.
 */

public class S5Avtivity extends Activity {
    private static final String TAG = "S5Avtivity";
    private TextView textViewFlat, textViewJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s5);
        textViewFlat = (TextView) findViewById(R.id.textViewFlat);
        textViewJson = (TextView) findViewById(R.id.textViewJson);
    }

    public void loadFromFlatBuffer(View view) {
        byte[] buffer = Utils.readRawResource(getApplication(), R.raw.sample_flatbuffer);
        long startTime = System.currentTimeMillis();
        ByteBuffer bb = ByteBuffer.wrap(buffer);
        PeopleList peopleList = PeopleList.getRootAsPeopleList(bb);
        long timeTaken = System.currentTimeMillis() - startTime;
        String logText = "FlatBuffer : " + timeTaken + "ms";
        textViewFlat.setText(logText);
        Log.d(TAG, "loadFromFlatBuffer " + logText);
    }

    public void loadFromJson(View view) {
        String jsonText = new String(Utils.readRawResource(getApplication(), R.raw.sample_json));
        long startTime = System.currentTimeMillis();
        PeopleListJson peopleList = new Gson().fromJson(jsonText, PeopleListJson.class);
        long timeTaken = System.currentTimeMillis() - startTime;
        String logText = "Json : " + timeTaken + "ms";
        textViewJson.setText(logText);
        Log.d(TAG, "loadFromJson " + logText);
    }
}
