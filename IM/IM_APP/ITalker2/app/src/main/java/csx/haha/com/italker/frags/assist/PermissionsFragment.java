package csx.haha.com.italker.frags.assist;


import android.Manifest;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import csx.haha.com.italker.App;
import csx.haha.com.italker.R;
import csx.haha.com.italker.frags.media.GalleryFragment;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class PermissionsFragment extends BottomSheetDialogFragment
        implements EasyPermissions.PermissionCallbacks{
    private static final String TAG = "MainActivity";
    // 权限回调的标示
    private static final int RC = 0x0100;

    public PermissionsFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //复用即可
        return new GalleryFragment.TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_permissions, container, false);
        refreshState(root);
        root.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击时进行权限申请
                requestPerm();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
//Log.d(TAG, "onResume: ");
        super.onResume();
        //界面显示时刷新
        refreshState(getView());
    }

    private void refreshState(View root) {
        Context context = getContext();
        root.findViewById(R.id.im_state_permission_network)
                .setVisibility(haveNetworkPerm(context) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_read)
                .setVisibility(haveReadPerm(context) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_write)
                .setVisibility(haveWritePerm(context) ? View.VISIBLE : View.GONE);
        root.findViewById(R.id.im_state_permission_record_audio)
                .setVisibility(haveAudioRecordPerm(context) ? View.VISIBLE : View.GONE);
    }

    /**
     * 获取是否有网络权限
     * @param context
     * @return true则有
     */
    private static boolean haveNetworkPerm(Context context) {
        String[] perms = new String[] {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }
    /**
     * 获取是否有读取外存文件权限
     * @param context
     * @return true则有
     */
    private static boolean haveReadPerm(Context context) {
        String[] perms = new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }
    /**
     * 获取是否有写入外存权限
     * @param context
     * @return true则有
     */
    private static boolean haveWritePerm(Context context) {
        String[] perms = new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        return EasyPermissions.hasPermissions(context, perms);
    }
    /**
     * 获取是否有录音权限
     * @param context
     * @return true则有
     */
    private static boolean haveAudioRecordPerm(Context context) {
        String[] perms = new String[] {
                Manifest.permission.RECORD_AUDIO
        };
        return EasyPermissions.hasPermissions(context, perms);
    }

    private static void show(FragmentManager manager) {
        //调用BottomSheetDialogFragment以及准备好的显示方法
        new PermissionsFragment()
                .show(manager, PermissionsFragment.class.getName());
    }

    /**
     * 检查是否具有所有的权限
     * @param context
     * @param manager
     * @return
     */
    public static boolean haveAll(Context context, FragmentManager manager) {
        boolean haveAll = haveNetworkPerm(context)
                && haveReadPerm(context)
                && haveWritePerm(context)
                && haveAudioRecordPerm(context);
        //如果不是全部权限都拥有，则显示该PermissionsFragment
        if (!haveAll) {
            show(manager);
        }
        return haveAll;
    }

    /**
     * 申请权限的方法
     */
    @AfterPermissionGranted(RC)
    private void requestPerm() {
//        Log.d(TAG, "requestPerm: ");
        String [] perms = new String[] {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };

        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            App.showToast(R.string.label_permission_ok);
            //Fragment 中调用getView()得到跟布局，前提是在onCreateView()后
            refreshState(getView());
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.title_assist_permissions),
                    RC, perms);
        }
    }

/*    @Override
    public void onPause() {
Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void onStop() {
Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }*/

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
//Log.d(TAG, "onPermissionsGranted: ");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
//Log.d(TAG, "onPermissionsDenied: ");
        // 如果所有权限都拒绝，则弹出弹出框，用户点击后去到设置界面自己打开权限
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .build()
                    .show();
        }
    }
    /**
     * 权限申请的时候回调的方法，在这个方法中把对应的权限申请状态交给EasyPermissions框架
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//Log.d(TAG, "onRequestPermissionsResult: ");
        //传递对应的参数，并且告知接收权限接收的处理者是我自己
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
