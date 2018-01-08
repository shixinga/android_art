package com.example.csx.myimagewall;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.csx.myimagewall.adapter.MyBaseAdapter;
import com.example.csx.myimagewall.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 即使快速滑到最下面，中间的imageview全都加载！所以很慢，所以需要懒加载（到哪停了就加载哪里的）
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity1";
    List<String> mDatas = new ArrayList<>();
    GridView mGv;
    MyBaseAdapter mMyBaseAdapter;

    boolean mCanGetBitmapFromNetwork;
    boolean mIsWifi;
    int mImageWidth = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initView();

        initAdapter();

        //Log.d(TAG, "onCreate: " + MyUtils.getScreenDisplay(this).widthPixels + " " + getResources().getDisplayMetrics().widthPixels + "/// " + MyUtils.getScreenDisplay(this) + "===" + getResources().getDisplayMetrics());
    }

    private void initData() {
        String[] imageUrls = {
                "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg",
                "http://pic47.nipic.com/20140830/7487939_180041822000_2.jpg",
                "http://pic41.nipic.com/20140518/4135003_102912523000_2.jpg",
                "http://img2.imgtn.bdimg.com/it/u=1133260524,1171054226&fm=21&gp=0.jpg",
                "http://h.hiphotos.baidu.com/image/pic/item/3b87e950352ac65c0f1f6e9efff2b21192138ac0.jpg",
                "http://pic42.nipic.com/20140618/9448607_210533564001_2.jpg",
                "http://pic10.nipic.com/20101027/3578782_201643041706_2.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512013419&di=46b917bd1abbaa1af5f84879990f7510&imgtype=jpg&er=1&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2015%2F215%2F45%2F04L5VRR21C5W.jpg",
                "http://img2.3lian.com/2014/c7/51/d/26.jpg",
                "http://img3.3lian.com/2013/c1/34/d/93.jpg",
                "http://b.zol-img.com.cn/desk/bizhi/image/3/960x600/1375841395686.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511418706020&di=921796ed450085b666f45fe07746f76f&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2013%2F255%2FHP4C7KBZQ0YP.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511418706020&di=6e2b77065c0327812eb1bc970882300b&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fd000baa1cd11728be0f1acf0c2fcc3cec2fd2c07.jpg",
                "http://imgrt.pconline.com.cn/images/upload/upc/tx/wallpaper/1210/17/c1/spcgroup/14468225_1350443478079_1680x1050.jpg",
                "http://pic41.nipic.com/20140518/4135003_102025858000_2.jpg",
                "http://www.1tong.com/uploads/wallpaper/landscapes/200-4-730x456.jpg",
                "http://pic.58pic.com/58pic/13/00/22/32M58PICV6U.jpg",
                "http://picview01.baomihua.com/photos/20120629/m_14_634765948339062500_11778706.jpg",
                "http://h.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=429e7b1b92ef76c6d087f32fa826d1cc/7acb0a46f21fbe09cc206a2e69600c338744ad8a.jpg",
                "http://pica.nipic.com/2007-12-21/2007122115114908_2.jpg",
                "http://cdn.duitang.com/uploads/item/201405/13/20140513212305_XcKLG.jpeg",
                "http://photo.loveyd.com/uploads/allimg/080618/1110324.jpg",
                "http://img4.duitang.com/uploads/item/201404/17/20140417105820_GuEHe.thumb.700_0.jpeg",
                "http://cdn.duitang.com/uploads/item/201204/21/20120421155228_i52eX.thumb.600_0.jpeg",
                "http://img4.duitang.com/uploads/item/201404/17/20140417105856_LTayu.thumb.700_0.jpeg",
                "http://img04.tooopen.com/images/20130723/tooopen_20530699.jpg",
                "http://www.qjis.com/uploads/allimg/120612/1131352Y2-16.jpg",
                "http://pic.dbw.cn/0/01/33/59/1335968_847719.jpg",
                "http://a.hiphotos.baidu.com/image/pic/item/a8773912b31bb051a862339c337adab44bede0c4.jpg",
                "http://h.hiphotos.baidu.com/image/pic/item/f11f3a292df5e0feeea8a30f5e6034a85edf720f.jpg",
                "http://img0.pconline.com.cn/pconline/bizi/desktop/1412/ER2.jpg",
                "http://pic.58pic.com/58pic/11/25/04/91v58PIC6Xy.jpg",
                "http://img3.3lian.com/2013/c2/32/d/101.jpg",
                "http://pic25.nipic.com/20121210/7447430_172514301000_2.jpg",
                "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
                "http://www.renyugang.cn/emlog/content/plugins/kl_album/upload/201004/852706aad6df6cd839f1211c358f2812201004120651068641.jpg"
        };
        for (String url : imageUrls) {
            mDatas.add(url);
        }
        int screenWidth = MyUtils.getScreenDisplay(this).widthPixels;
        int space = (int)MyUtils.dp2px(this, 20f);
        mImageWidth = (screenWidth - space) / 3;
        mIsWifi = MyUtils.isWifi(this);
        if (mIsWifi) {
            mCanGetBitmapFromNetwork = true;
        }
    }

    private void initView() {
        mGv = (GridView) findViewById(R.id.gl);
        if (!mIsWifi) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("要下载美女，必须开wifi要么用你流量");
            builder.setPositiveButton("确定吗", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mCanGetBitmapFromNetwork = true;
                    mMyBaseAdapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("cancle", null);
            builder.show();
        }
    }

    private void initAdapter() {
        Log.d(TAG, "initAdapter: wifi is up=" + mCanGetBitmapFromNetwork + " imageWidth=" + mImageWidth);
        mMyBaseAdapter = new MyBaseAdapter(mDatas, this, mCanGetBitmapFromNetwork,mImageWidth);
        mGv.setAdapter(mMyBaseAdapter);
    }
}
