package one.com.a6aidl_service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import one.com.a6aidl_service.aidl.Book;
import one.com.a6aidl_service.aidl.IBookManager;
import one.com.a6aidl_service.aidl.IOnNewBookReceiveListener;

/**
 * Created by shixing on 2017/5/5.
 */

public class MyService extends Service {
    private static final String TAG = "MyService";
    private CopyOnWriteArrayList<Book> mList =  new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookReceiveListener> mListenerList =
            new RemoteCallbackList<>();
    private IBinder myBinder = new IBookManager.Stub() {

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int isPermited = checkCallingOrSelfPermission("com.haha.xxx");
            Log.d(TAG, "onTransact: isPermited = " + isPermited);
            if (isPermited == PackageManager.PERMISSION_DENIED) {
                return false;
            }
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(
                    getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            Log.d(TAG, "onTransact: " + packageName);
            if (!packageName.startsWith("one.com")) {
                return false;
            }
            return super.onTransact(code, data, reply, flags);
//            return false; //不起作用
        }

        //binder线程池中的线程调用，而不是ui线程，因为这是被客户端进程调用的方法
        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d(TAG, "getBookList:listtype= " + mList.getClass().getCanonicalName()
                + "..currentThread:" + Thread.currentThread().getName());
            /*try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            return mList;
        }
        //binder线程池中的线程调用，而不是ui线程，因为这是被客户端进程调用的方法
        @Override
        public void addBook(Book book) throws RemoteException {
            mList.add(book);
            Log.d(TAG, "addBook: "+ "..currentThread:" + Thread.currentThread().getName());
        }
        //binder线程池中的线程调用，而不是ui线程调用，因为这是被客户端进程调用的方法
        @Override
        public void registerListener(IOnNewBookReceiveListener listener) throws RemoteException {
            mListenerList.register(listener);
            Log.d(TAG, "registerListener:"+ "..currentThread:" + Thread.currentThread().getName() + " " + listener.asBinder());
        }

        //binder线程池中的线程调用，而不是ui线程调用，因为这是被客户端进程调用的方法
        @Override
        public void unRegisterListener(IOnNewBookReceiveListener listener) throws RemoteException {
            mListenerList.unregister(listener);
            Log.d(TAG, "unregisterListener:  "+ "..currentThread:" + Thread.currentThread().getName() + " " + listener.asBinder());

        }
    };

    //ui线程调用
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind:  "+ "..currentThread:" + Thread.currentThread().getName());
        return this.myBinder;
    }

    //ui线程调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: " + Thread.currentThread().getName());
        mList.add(new Book("1111"));
        mList.add(new Book("222"));
        new Thread(new ReceiveBook()).start();
    }

    private class ReceiveBook implements Runnable {

        @Override
        public void run() {
            int count = 1;
            while (true) {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Book book = new Book("cc" + count++);
                Log.d(TAG, "ReceiveBook run:  "+ "..currentThread:" + Thread.currentThread().getName());
                final int N = mListenerList.beginBroadcast();
                for (int i = 0; i < N; ++i) {
                    IOnNewBookReceiveListener listener = mListenerList.getBroadcastItem(i);
                    if (listener != null) {
                        try {
                            listener.onNewBookReceive(book);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                    }
                }
                mListenerList.finishBroadcast();//记得finish
            }
        }
    }
}
