package com.shixing.mysearchview;

import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        //SearchView在Menu里面
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //设置一出来就直接显示搜索框
//        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false); //如果没有输入，就没有X（删除图标）

        //有时候我们需要实现自定义扩展效果
        //通过猜想，searchView用到了一个布局，去appcompat里面找到abc_search_view.xml,该里面的控件的属性
       /* ImageView icon = searchView.findViewById(R.id.search_go_btn);
        searchView.setSubmitButtonEnabled(true);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "提交", Toast.LENGTH_SHORT).show();
            }
        });*/

        //监听焦点改变
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });
        //searchView的关闭监听
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {

            @Override
            public boolean onClose() {
                // TODO Auto-generated method stub
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "提交", Toast.LENGTH_SHORT).show();
            }
        });

        //监听文本变化，调用查询
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //提交文本
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "自动提交" + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 文本改变的时候回调
                Toast.makeText(MainActivity.this, "文本改变时回调:" + newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        EditText et = searchView.findViewById(R.id.search_src_text);
        et.setHint("请输入你的大名");
        et.setHintTextColor(Color.WHITE);
        return true; //true表示标题栏要显示选项按钮，false表示不显示菜单的显示按钮
    }
}


