package com.shixing.expandablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ExpandableListView melv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        melv = (ExpandableListView) findViewById(R.id.elv);
        initData();
    }

    public void initData() {
        String[] parent = new String[3];
        parent[0] = "1st";
        parent[1] = "2nd";
        parent[2] = "3rd";
        ArrayList<String> childList1 = new ArrayList<>();
        ArrayList<String> childList2 = new ArrayList<>();
        ArrayList<String> childList3 = new ArrayList<>();
        childList1.add("1-1");
        childList1.add("1-2");
        childList1.add("1-3");
        childList2.add("2-1");
        childList2.add("2-2");
        childList2.add("2-3");
        childList3.add("3-1");
        childList3.add("3-2");
        childList3.add("3-3");

        Map<String,ArrayList<String>> dataSet = new HashMap<>();
        dataSet.put(parent[0],childList1);
        dataSet.put(parent[1],childList2);
        dataSet.put(parent[2],childList3);

        MyExpandableAdapter myExpandableAdapter = new MyExpandableAdapter(this,dataSet,parent);
        melv.setAdapter(myExpandableAdapter);
    }
}
