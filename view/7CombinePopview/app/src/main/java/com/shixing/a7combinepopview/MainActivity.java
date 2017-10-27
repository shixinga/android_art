package com.shixing.a7combinepopview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    EditText mEt;
    List<String> mList = new ArrayList<>();
    ListView mListView;
    PopupWindow mPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initView();

        initController();
    }

    private void initController() {
        mListView.setAdapter(new MyBaseAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mEt.setText(mList.get(position));
                mPopupWindow.dismiss();
            }
        });
    }

    private void initView() {
        mEt = (EditText) findViewById(R.id.et);
        mListView = new ListView(this);

    }

    private void initData() {
        for (int i = 0; i < 30; ++i) {
            mList.add("201111" + i);
        }


    }

    public void showOnClick(View view) {
        mPopupWindow = new PopupWindow(mListView,mEt.getWidth(),500);
        mPopupWindow.setOutsideTouchable(true); //设置外部可点击，一旦点击，就将该popupwindow隐藏
        mPopupWindow.showAsDropDown(mEt,0,0);

    }

    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this,R.layout.item_list,null);
            } else {
                view = convertView;
            }
            TextView tv = (TextView) view.findViewById(R.id.tv_item);
            tv.setText(mList.get(position));
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_delete);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(position);
                    notifyDataSetChanged();
                    mPopupWindow.dismiss();
                }
            });
            return view;
        }
    }
}
