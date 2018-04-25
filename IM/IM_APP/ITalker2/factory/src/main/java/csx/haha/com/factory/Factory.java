package csx.haha.com.factory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by sunray on 2018-4-25.
 */

public class Factory {

    private static final Factory sInstance;
    private Executor executor;
    static {
        sInstance = new Factory();
    }

    private Factory() {
        executor = Executors.newFixedThreadPool(4);
    }

    public static Factory getInstance() {
        return sInstance;
    }

    /**
     * 异步运行的方法
     *
     * @param runnable Runnable
     */
    public static void runOnAsync(Runnable runnable) {
        // 拿到单例，拿到线程池，然后异步执行
        sInstance.executor.execute(runnable);
    }
}
