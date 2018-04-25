package csx.haha.com.italker.frags.media;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import net.qiujuer.genius.ui.Ui;

import csx.haha.com.common.tools.UiTool;
import csx.haha.com.common.widget.GalleryView;
import csx.haha.com.italker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends BottomSheetDialogFragment
        implements GalleryView.SelectChangeListener{

    private GalleryView mGalleryView;
    private OnSelectedListener mListener;

    public GalleryFragment() {

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //先使用默认的GalleryView
        return new TransStatusButtomSheetDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGalleryView = root.findViewById(R.id.galleryView);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGalleryView.setUp(getLoaderManager(), this);
    }

    @Override
    public void onSelectedCountChanged(int count) {
        //如果选中了一张图片
        if (count > 0) {
            dismiss();
            if (mListener != null) {
                //得到所有的选中的图片的路径
                String paths [] = mGalleryView.getSelectedPath();
                //返回第一张
                mListener.onSelectedImage(paths[0]);
                //取消和唤起者之间的引用，加快内存回收
                mListener = null;
            }
        }
    }

    public GalleryFragment setListener(OnSelectedListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public interface OnSelectedListener {
        void onSelectedImage(String path);
    }

    public static class TransStatusButtomSheetDialog extends BottomSheetDialog {

        public TransStatusButtomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TransStatusButtomSheetDialog(@NonNull Context context, @StyleRes int theme) {
            super(context, theme);
        }

        protected TransStatusButtomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final Window window = getWindow();
            if (window == null) {
                return;
            }
            //得到屏幕的高度
            int screenHeight = UiTool.getScreenHeight(getOwnerActivity());
            //得到状态栏的高度
            int statusHeight = UiTool.getStatusBarHeight(getOwnerActivity());

            int dialogHeight = screenHeight - statusHeight;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    dialogHeight <= 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        }
    }
}
