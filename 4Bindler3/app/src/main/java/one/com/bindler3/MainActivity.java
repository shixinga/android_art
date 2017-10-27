package one.com.bindler3;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView tv;
    private IBookManager iBookManager;
    private ServiceConnection myServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                  iBookManager = IBookManager.Stub.asInterface(iBinder);
            Log.d(TAG,"mainactivity:" + iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        Intent intent = new Intent(this,BookService.class);
        bindService(intent,myServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void addOnClick(View view) {
        try {
            iBookManager.addBook(new Book(333,"瑞"));
            Log.d(TAG,"mainactivity add book");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void getOnClick(View view) {
        List<Book> bookList = null;
        try {
            bookList = iBookManager.getBookList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        StringBuffer name = new StringBuffer();
        for (Book book : bookList) {
            name.append(book.userName + ".." + book.bookId + "!!");
        }
        tv.setText(Thread.currentThread().getName() + ".." +name.toString());
    }

}
