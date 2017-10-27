package one.com.v2listview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shixing on 2017/6/20.
 */

/**
 * 普通的viewHolder
 */
public class SecondActivity extends Activity {
    private static final String TAG = "SecondActivity";
    private List<String> mDatas = new ArrayList<>();
    private ListView mListview;
    private MyBaseAdaptor mBaseAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        for (int i = 1; i < 20; ++i) {
            mDatas.add("" + i);
        }
        mListview = (ListView) findViewById(R.id.lv);
        mBaseAdaptor = new MyBaseAdaptor(this);
        mListview.setAdapter(mBaseAdaptor);

    }

    public void addOnClick(View view) {
        mDatas.add("haha");
        mBaseAdaptor.notifyDataSetChanged();
        mListview.setSelection(mDatas.size() - 1);
    }


    class MyBaseAdaptor extends BaseAdapter {

        LayoutInflater mLayoutInflater;

        public MyBaseAdaptor(Context context) {
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView != null) {
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                viewHolder = new ViewHolder();
                convertView = mLayoutInflater.inflate(R.layout.item_list,null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(viewHolder);
            }
            viewHolder.imageView.setBackgroundResource(R.mipmap.ic_launcher);
            viewHolder.textView.setText(mDatas.get(position));
            return convertView;
        }
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
