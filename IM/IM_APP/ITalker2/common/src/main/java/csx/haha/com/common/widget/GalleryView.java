package csx.haha.com.common.widget;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import csx.haha.com.common.R;
import csx.haha.com.common.widget.recycler.RecyclerAdapter;

public class GalleryView extends RecyclerView {
    private static final String TAG = "MainActivity";
    Adapter mAdapter = new Adapter();
    private LoaderCallback mLoaderCallback = new LoaderCallback();
    private static final int LOADER_ID = 0x0100;
    private List<Image> mSelectImages = new LinkedList<>();
    private static final int MAX_IMAGE_COUNT = 3; //最大的图片选中个数
    private SelectedChangeListener mListener;
    private static final int MIN_IMAGE_FILE_SIZE = 10 * 1024; //最小的图片大小为10kB


    public GalleryView(Context context) {
        super(context);
        init();
    }

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new GridLayoutManager(getContext(), 4));
        setAdapter(mAdapter);
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Image>() {
            @Override
            public void onItemClick(RecyclerAdapter.MyViewHolder holder, Image data) {
                //Cell点击操作，如果说我们的点击是允许的，那么更新对应的Cell的状态
                //然后更新界面。同理，如果不允许点击（已经达到最大的选择量），那么就不刷新界面
                if (onItemSelectClick(data)) {
                    holder.updateData(data);
                }
            }

        });
    }


    /**
     * 初始化方法
     * @param loaderManager
     * @return 任何一个LOADER_ID,可用于销毁Loader
     */
    public int setUp(LoaderManager loaderManager, SelectedChangeListener listener) {
        this.mListener = listener;
        loaderManager.initLoader(LOADER_ID, null, mLoaderCallback);
        return LOADER_ID;
    }

    /**
     * Cell点击的具体逻辑
     * @param image
     * @return true代表数据被更改了，需要刷新
     */
    private boolean onItemSelectClick(Image image) {
        boolean notifyRefresh;
        if (mSelectImages.contains(image)) {
            //如果之前在那么现在就移除
            mSelectImages.remove(image);
            image.isSelect = false;
            //状态已经改变则需要更新
            notifyRefresh = true;
        } else {
            if (mSelectImages.size() >= MAX_IMAGE_COUNT) {
                String str = getResources().getString(R.string.label_gallery_select_max_size);
                str = String.format(str, MAX_IMAGE_COUNT);
                Toast.makeText(getContext(),
                        str, Toast.LENGTH_SHORT).show();
                notifyRefresh = false;
            } else {
                mSelectImages.add(image);
                image.isSelect = true;
                notifyRefresh = true;
            }
        }

        if (notifyRefresh) {
            notifySelectChanged();
        }
        return true;
    }

    /**
     * 通知选中状态改变
     */
    private void notifySelectChanged() {
        if (mListener != null) {
            mListener.onSelectedCountChanged(mSelectImages.size());
        }
    }

    /**
     * 得到选中的图片的全部地址
     * @return
     */
    public String[] getSelectedPath() {
        String paths[] = new String[mSelectImages.size()];
        int index = 0;
        for (Image image : mSelectImages) {
            paths[index++] = image.path;
        }
        return paths;

    }

    /**
     * 可以进行清空选中的图片
     */
    public void clear() {
        for (Image image : mSelectImages) {
            image.isSelect = false;
        }
        mSelectImages.clear();
        mAdapter.notifyDataSetChanged();

        //发送后将选中图片个数设为0
        notifySelectChanged();
    }

    /**
     * 用于实际的数据加载的Loader Callback
     */
    private class LoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {
        private final String[] IMAGE_PROJECTION = new String[] {
                MediaStore.Images.Media._ID, //id
                MediaStore.Images.Media.DATA, //图片路径
                MediaStore.Images.Media.DATE_ADDED //图片的创建时间
        };
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//            Log.d(TAG, "onCreateLoader: " + Thread.currentThread().getName());
            if (id == LOADER_ID) {
                return new CursorLoader(getContext(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        null,
                        null,
                        null,
                        IMAGE_PROJECTION[2] + " DESC"); //倒序查询
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//            Log.d(TAG, "onLoadFinished: " + Thread.currentThread().getName());
            //当Loader加载完成时
            List<Image> images = new ArrayList<>();
            //判断是否有数据
            if (data != null) {
                int count = data.getCount();
                if (count > 0) {
                    //移动游标到开始
                    data.moveToFirst();

                    //得到对应的列的index坐标
                    int indexId = data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]);
                    int indexPath = data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]);
                    int indexDate = data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]);

                    do {
                        int id = data.getInt(indexId);
                        String path = data.getString(indexPath);
//Log.d(TAG, "onLoadFinished: image path: " + path);
                        long dateTime = data.getLong(indexDate);

                        File file = new File(path);
                        if (!file.exists() || file.length() < MAX_IMAGE_COUNT) {
                            //如果图片不存在或者图片太小，则继续遍历下一张图片
                            continue;
                        }
                        Image image = new Image();
                        image.id = id;
                        image.path = path;
                        image.date = dateTime;
                        images.add(image);
                    } while (data.moveToNext());
                }
            }
            updateSource(images);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
//            Log.d(TAG, "onLoaderReset: " + Thread.currentThread().getName());
            updateSource(null);
        }
    }

    /**
     * 通知Adapter数据更改的方法
     * @param images
     */
    private void updateSource(List<Image> images) {
        mAdapter.replace(images);
    }

    /**
     * 内部的数据结构
     */
    private static class Image {
        int id;
        String path; //图片的路径
        long date; //图片的创建日期
        boolean isSelect;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Image image = (Image) o;

            return path != null ? path.equals(image.path) : image.path == null;

        }

        @Override
        public int hashCode() {
            return path != null ? path.hashCode() : 0;
        }
    }
    private class Adapter extends RecyclerAdapter<Image> {


        @Override
        protected int getItemViewType(int position, Image data) {
            return R.layout.cell_galley;
        }

        @Override
        protected MyViewHolder myOnCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }
    }

    private class ViewHolder extends RecyclerAdapter.MyViewHolder<Image> {
        private ImageView mPic;
        private View mShade;
        private CheckBox mSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            mPic = itemView.findViewById(R.id.im_image);
            mShade = itemView.findViewById(R.id.view_shade);
            mSelected = itemView.findViewById(R.id.cb_select);
        }

        @Override
        protected void onBind(Image data) {
            Glide.with(getContext())
                    .load(data.path) //加载路径
                    .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用缓存
                    .centerCrop() //居中剪切
                    .placeholder(R.color.grey_200) //默认颜色
                    .into(mPic);

            mShade.setVisibility(data.isSelect ? VISIBLE :INVISIBLE);
            mSelected.setChecked(data.isSelect);
            mSelected.setVisibility(VISIBLE);
        }
    }

    public interface SelectedChangeListener {
        void onSelectedCountChanged(int count);
    }

}
