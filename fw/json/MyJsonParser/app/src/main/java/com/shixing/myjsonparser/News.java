package com.shixing.myjsonparser;

import java.util.List;

/**
 * Created by Administrator on 2017/2/19 0019.
 */
public class News {
    private int id;
    private String title;
    private String content;
    private User author;
    private List<User> reader;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    public List<User> getReader() {
        return reader;
    }
    public void setReader(List<User> reader) {
        this.reader = reader;
    }
    @Override
    public String toString() {
        return "News [author=" + author + ", content=" + content + ", id=" + id
                + ", reader=" + reader + ", title=" + title + "]";
    }
}