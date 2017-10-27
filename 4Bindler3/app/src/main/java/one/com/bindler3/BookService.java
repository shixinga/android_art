package one.com.bindler3;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by shixing on 2017/4/27.
 */

public class BookService extends Service {
    private static final String TAG = "MainActivity";
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    // private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList =
    // new CopyOnWriteArrayList<IOnNewBookArrivedListener>();


    private Binder myBinder = new IBookManager.Stub(){

        @Override
        public List<Book> getBookList() throws RemoteException {
            SystemClock.sleep(100);
            Log.d(TAG, "service getBookList: " + Thread.currentThread().getName());
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
            Log.d(TAG,"name=" + book.userName + ",,id=" + book.bookId);
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"service:" + myBinder);
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mBookList.add(new Book(1,"ccc"));
        this.mBookList.add(new Book(88,"cscscsxx"));
    }
}
