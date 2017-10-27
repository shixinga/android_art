package one.com.myaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by shixing on 2017/4/27.
 */

public class BookService extends Service {


    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    // private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList =
    // new CopyOnWriteArrayList<IOnNewBookArrivedListener>();

    private IBinder mBinder = new IBookManager.Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {

        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            StringBuffer sb = new StringBuffer();
            for (Book book :mBookList) {
                sb.append(book.id + "," + book.name +"..");
            }
            Log.d(MainActivity.TAG,sb.toString() + "QQ");
            return mBookList;
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        mBookList.add(new Book(1,"cccc"));
        mBookList.add(new Book(2,"sss"));
        Log.d(MainActivity.TAG, "onBind: " );
        return mBinder;
    }
}
