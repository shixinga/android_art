package one.com.a6aidl_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import one.com.a6aidl_service.aidl.Book;
import one.com.a6aidl_service.aidl.IBookManager;
import one.com.a6aidl_service.aidl.IOnNewBookReceiveListener;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    private int count = 1;
    private IBookManager iBookManager;
    private Book receiveBook;
    private static final String TAG = "MainActivity";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv.setText(Thread.currentThread().getName() + ".." + receiveBook.name);
        }
    };
    private ServiceConnection sc = new ServiceConnection() {
        //ui线程调用。在aidl的服务端进程的onBind()调用之后才会调用onServiceConnected()
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: " + Thread.currentThread().getName());
            iBookManager = IBookManager.Stub.asInterface(service);
        }

        //ui线程调用。AIDL服务端进程的service挂了之后才会调用onServiceDisconnected()
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: " + Thread.currentThread().getName());
            iBookManager = null;
        }
    };

    private IOnNewBookReceiveListener iOnNewBookReceiveListener = new IOnNewBookReceiveListener.Stub() {

        //在binder线程池调用，而不是ui线程调用，因为是被AIDL服务端调用的方法。
        @Override
        public void onNewBookReceive(Book book) throws RemoteException {
            receiveBook = book;
            handler.sendEmptyMessage(1);
            Log.d(TAG, "onNewBookReceive: " + Thread.currentThread().getName() +"..listener:" + this);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        Intent intent = new Intent();
        intent.setAction("com.haha.csx.service");
        intent.setPackage("one.com.a6aidl_service");
        bindService(intent,sc,BIND_AUTO_CREATE);
    }

    public void getOnClick(View view) throws RemoteException {
        if(iBookManager != null && iBookManager.asBinder().isBinderAlive()) {

            List<Book> list = iBookManager.getBookList();
            StringBuffer sb = new StringBuffer();
            sb.append(list.getClass().getCanonicalName() + "..");
            for (Book book : list) {
                sb.append(book.name + ",");
            }
            tv.setText(sb.toString());
        } else {
            tv.setText("服务端的binder已经死了");
        }
    }

    public void addOnClick(View view) throws RemoteException {
        iBookManager.addBook(new Book("csx" + count++));
    }

    public void registerOnClick(View view) throws RemoteException {
        iBookManager.registerListener(iOnNewBookReceiveListener);
    }

    public void unRegisterOnClick(View view) throws RemoteException {
        iBookManager.unRegisterListener(iOnNewBookReceiveListener);
    }


    //解绑service之后，如果服务端进程挂了，并不会调用ServiceConnection sc中的onServiceDisconnected()
    //所以这时再调用iBookManager对象的方法程序会崩
    public void unbindOnClick(View view) {
        unbindService(sc);
    }
}
