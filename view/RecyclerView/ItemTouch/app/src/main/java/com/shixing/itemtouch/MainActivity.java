package com.shixing.itemtouch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 拖拽、滑动动画
 * MVP模式
 */
public class MainActivity extends AppCompatActivity implements StartDtagListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<QQMessage> list = DataUtils.init();
        QQAdapter adapter = new QQAdapter(list, this);
        mRecyclerView.setAdapter(adapter);



        //条目触摸帮助类
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
