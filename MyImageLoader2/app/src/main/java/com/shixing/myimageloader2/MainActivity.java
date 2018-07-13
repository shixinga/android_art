package com.shixing.myimageloader2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.shixing.myimageloader2.cache.DoubleCache;
import com.shixing.myimageloader2.config.ImageLoaderConfig;
import com.shixing.myimageloader2.core.SimpleImageLoader;
import com.shixing.myimageloader2.policy.ReversePolicy;

public class MainActivity extends AppCompatActivity {

    private SimpleImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(new MyAdapter(this));

        //配置
        ImageLoaderConfig.Builder build = new ImageLoaderConfig.Builder();
        build.setThreadCount(3) //线程数量
                .setLoadPolicy(new ReversePolicy()) //加载策略
                .setCachePolicy(new DoubleCache(this)) //缓存策略
                .setLoadingPlaceHolder(R.drawable.loading)
                .setFailedPlaceHolder(R.drawable.not_found);

        ImageLoaderConfig config = build.build();
        //初始化
        imageLoader = SimpleImageLoader.getInstance(config);

    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return imageThumbUrls.length;
        }

        @Override
        public Object getItem(int position) {
            return imageThumbUrls[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View item = inflater.inflate(R.layout.item, parent, false);
            ImageView imageView = (ImageView) item.findViewById(R.id.iv);
            //请求图片
            imageLoader.displayImage(imageView, imageThumbUrls[position]);
            return item;
        }


    }
    public final static String[] imageThumbUrls = new String[] {
            "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg",
            "http://pic47.nipic.com/20140830/7487939_180041822000_2.jpg",
            "http://pic41.nipic.com/20140518/4135003_102912523000_2.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1133260524,1171054226&fm=21&gp=0.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/3b87e950352ac65c0f1f6e9efff2b21192138ac0.jpg",
            "http://pic42.nipic.com/20140618/9448607_210533564001_2.jpg",
            "http://pic10.nipic.com/20101027/3578782_201643041706_2.jpg",
            "http://p0.so.qhimgs1.com/t0153e0c6a6606b79bc.jpg",
            "http://img2.3lian.com/2014/c7/51/d/26.jpg",
            "http://img3.3lian.com/2013/c1/34/d/93.jpg",
            "http://b.zol-img.com.cn/desk/bizhi/image/3/960x600/1375841395686.jpg",
            "http://pic1.win4000.com/wallpaper/1/55daab78c98c6.jpg",
            "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1304/15/c1/19880703_1366014804265.jpg",
            "http://imgrt.pconline.com.cn/images/upload/upc/tx/wallpaper/1210/17/c1/spcgroup/14468225_1350443478079_1680x1050.jpg",
            "http://pic41.nipic.com/20140518/4135003_102025858000_2.jpg",
            "http://img01.taopic.com/170823/240508-1FR30Q63374.jpg",
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
            "http://www.renyugang.cn/emlog/content/plugins/kl_album/upload/201004/852706aad6df6cd839f1211c358f2812201004120651068641.jpg",
            // 以下三张为本地图片,本地图片支持uri格式，形如file:// + 图片绝对路径
            "file:///storage/emulated/0/Camera/P41115-140216.jpg",
            "file:///storage/emulated/0/Camera/P41115-142950.jpg",
            "file:///storage/emulated/0/Camera/P50102-133614.jpg",
            "http://alipiba.com/image/not_found_haha.jpg", // 不存在的图片


    };
}
