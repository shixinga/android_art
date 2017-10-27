package one.com.v2listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void listViewOnClick(View view) {
        mIntent = new Intent(this,SecondActivity.class);
        startActivity(mIntent);
    }

    public void flexaleOnClick(View view) {
        mIntent = new Intent(this,FlexableActivity.class);
        startActivity(mIntent);
    }
}
