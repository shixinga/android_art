package one.com.myaidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private TextView tv;
    private IBookManager iBookManager;
    private  IBinder mmm;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iBookManager = IBookManager.Stub.asInterface(iBinder);
            mmm = iBinder;
            Log.d(TAG,"manager= " + iBookManager);
            Log.d(TAG,"iBinder= " + iBinder);
            Log.d(TAG,"==== " + (iBinder == iBookManager));
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
        bindService(intent,sc,BIND_AUTO_CREATE);
    }

    public void getOnClick(View view) {
        IBinder mRemote = iBookManager.asBinder();
        try {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            java.util.List<one.com.myaidl.Book> _result;
            try {
                _data.writeInterfaceToken("one.com.myaidl.IBookManager");
                mmm.transact(IBookManager.Stub.TRANSACTION_getBookList, _data, _reply, 0);
                _reply.readException();
                _result = _reply.createTypedArrayList(one.com.myaidl.Book.CREATOR);
            } finally {
                _reply.recycle();
                _data.recycle();
            }

            StringBuffer sb = new StringBuffer();
            for (Book book :_result) {
                sb.append(book.id + "," + book.name +"..");
            }
            tv.setText(sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
