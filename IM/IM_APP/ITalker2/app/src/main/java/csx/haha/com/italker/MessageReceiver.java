package csx.haha.com.italker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;

import csx.haha.com.factory.Factory;
import csx.haha.com.factory.data.helper.AccountHelper;
import csx.haha.com.factory.persistence.Account;


/**
 * Created by sunray on 2018-4-27.
 */

public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = "MainActivity";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
Log.d(TAG, "onReceive: ");
        Bundle bundle = intent.getExtras();

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID: {
                Log.i(TAG, "GET_CLIENTID:" + bundle.toString());
                // 当Id初始化的时候
                // 获取设备Id
                onClientInit(bundle.getString("clientid"));
                break;
            }
            case PushConsts.GET_MSG_DATA: {
                // 常规消息送达
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String message = new String(payload);
                    Log.i(TAG, "GET_MSG_DATA:" + message);
                    onMessageArrived(message);
                }
                break;
            }
            default:
                Log.i(TAG, "OTHER:" + bundle.toString());
                break;
        }
    }

    /**
     * 当Id初始化的时候
     * @param cid 设备的pushId
     */
    private void onClientInit(String cid) {
        //设置设备id
        Account.setPushId(cid);
        if (Account.isLogin()) {
            AccountHelper.bindPushId(null);
        }
    }

    /**
     * 消息从个推送到手机时
     * @param message
     */
    private void onMessageArrived(String message) {
        //交给Factory处理
        Factory.dispatchPush(message);
    }
}
