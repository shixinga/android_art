package csx.haha.com.our.adapter;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import csx.haha.com.our.R;

/**
 * 主界面的listView的adapter
 * @author Administrator
 *
 */
public class MyListViewBaseAdapter extends BaseAdapter{
	
		private Context context;
		//主界面的list
		private List<Map<String, Object>> needList;
		private SharedPreferences sharedPreferences;
		
		
		
		public MyListViewBaseAdapter(Context context,
				List<Map<String, Object>> needList,
				SharedPreferences sharedPreferences) {
			this.context = context;
			this.needList = needList;
			this.sharedPreferences = sharedPreferences;
		}
		@Override
		public int getCount() {
			return needList.size();
		}
		//有多少个条目被显示，这个方法就会被调用多少次
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			//1.减少内存中view对象创建的个数
			if(convertView==null){
			//把一个布局文件转化成  view对象。
				view  = View.inflate(context, R.layout.list_main_item, null);
				//2.减少子孩子查询的次数  内存中对象的地址。
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
				//当孩子生出来的时候找到他们的引用，存放在记事本，放在父亲的口袋
				view.setTag(holder);
			}else{
				view = convertView;
				holder = (ViewHolder) view.getTag();//5%
			}
			Map<String, Object> map = needList.get(position);
			//设置软件图标
			holder.iv_icon.setImageDrawable((Drawable) map.get("ico"));
			//获取软件的包名
			final String packageName = (String) map.get("packageName");
			holder.iv_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new Builder(context);
					builder.setTitle("警告");
					builder.setMessage("确定要删除这条记录么？");
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Editor editor = sharedPreferences.edit();
							//更新界面。
							needList.remove(position);
							editor.putBoolean(packageName, false);
							//通知listview数据适配器更新
							MyListViewBaseAdapter.this.notifyDataSetChanged();
							editor.commit();
						}
					});
					builder.setNegativeButton("取消", null);
					builder.show();
				}
			});
			return view;
		}
		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}