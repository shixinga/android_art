package com.shixing.myhttp.service;

import com.shixing.myhttp.api.HttpRequestProvider;
import com.shixing.myhttp.http.HttpRequest;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shixing on 2018/7/11.
 */

public class WorkStation {

    private static final int MAX_REQUEST_SIZE = 60;

    private static final ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {

        private AtomicInteger index = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("http thread name is " + index.getAndIncrement());
            return thread;
        }
    });


    private Deque<MyRequest> mRunning = new ArrayDeque<>();

    private Deque<MyRequest> mCache = new ArrayDeque<>();

    private HttpRequestProvider mRequestProvider;

    public WorkStation() {
        mRequestProvider = new HttpRequestProvider();
    }

    public void add(MyRequest request) {

        if (mRunning.size() > MAX_REQUEST_SIZE) {
            mCache.add(request);
        } else {
            doHttpRequest(request);
            mRunning.add(request);
        }

    }


    public void doHttpRequest(MyRequest request) {
        HttpRequest httpRequest = null;
        try {
            httpRequest = mRequestProvider.getHttpRequest(URI.create(request.getUrl()), request.getMethod());
        } catch (IOException e) {
            e.printStackTrace();
        }

        sThreadPool.execute(new HttpRunnable(httpRequest, request, this));
    }


    public void finish(MyRequest request) {
        mRunning.remove(request);
        if (mRunning.size() > MAX_REQUEST_SIZE) {
            return;
        }

        if (mCache.size() == 0) {
            return;
        }

        Iterator<MyRequest> iterator = mCache.iterator();

        while (iterator.hasNext()) {
            MyRequest next = iterator.next();
            mRunning.add(next);
            iterator.remove();
            doHttpRequest(next);
        }

    }
}
