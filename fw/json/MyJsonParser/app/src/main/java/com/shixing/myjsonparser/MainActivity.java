package com.shixing.myjsonparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        objectToJson();
        jsonToObject();
    }

    private void jsonToObject() {
        String jsonToUser = "{\"id\":1,\"name\":\"csxx\",\"pwd\":\"123456t\"}";
        User user= (User) MyFastJson.pareseObject(jsonToUser,User.class);
        Log.d(TAG, "jsonToObject: user=" + user.toString());
        String jsonToList = "[{\"id\":1,\"name\":\"csxx\",\"pwd\":\"123456t\"},{\"id\":3,\"name\":\"cs2\",\"pwd\":\"12112s\"},{\"id\":3,\"name\":\"cs2\",\"pwd\":\"12112s\"}]";
        List<User> users = (List<User>) MyFastJson.pareseObject(jsonToList, User.class);
        for (User u : users) {
            Log.d(TAG, "jsonToObject: list's one user=" + u.toString());
        }
        String jsonToNews = "{\"author\":{\"id\":1,\"name\":\"csxx\",\"pwd\":\"123456t\"},\"content\":\"从今天开始放假啦。\",\"id\":1,\"reader\":[{\"id\":1,\"name\":\"csxx\",\"pwd\":\"123456t\"},{\"id\":3,\"name\":\"cs2\",\"pwd\":\"12112s\"},{\"id\":3,\"name\":\"cs2\",\"pwd\":\"12112s\"}],\"title\":\"新年放假通知\"}";
        News news = (News) MyFastJson.pareseObject(jsonToNews, News.class);
        Log.d(TAG, "jsonToObject: news=" + news.toString());

    }

    private void objectToJson() {
        User user = new User(1, "csxx", "123456t");
        String jsonStr = MyFastJson.toJson(user);
        Log.d(TAG, "onCreate: " + jsonStr);



        User user2 = new User(3,"cs2", "12112s");
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user2);
        list.add(user2);
        String jsonStr2 = MyFastJson.toJson(list);
        Log.d(TAG, "onCreate: " + jsonStr2);



        News news = new News();
        news.setId(1);
        news.setTitle("新年放假通知");
        news.setContent("从今天开始放假啦。");
        news.setAuthor(user);
        news.setReader(list);
        Log.d(TAG, MyFastJson.toJson(news));
    }
}
