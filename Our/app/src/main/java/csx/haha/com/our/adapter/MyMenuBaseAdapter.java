package csx.haha.com.our.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import csx.haha.com.our.selfview.IconCheckboxView;

/**
 * 左边菜单栏的adapter
 * @author Administrator
 *
 */
public class MyMenuBaseAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> list;
	private SharedPreferences sharedPreferences;

	public MyMenuBaseAdapter(Context context, List<Map<String, Object>> list,
			SharedPreferences sharedPreferences) {
		this.context = context;
		this.list = list;
		this.sharedPreferences = sharedPreferences;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// IconCheckboxView是自定义控件
		final IconCheckboxView checkboxView = new IconCheckboxView(context);
		// 获取position对应软件的map
		Map<String, Object> map = list.get(position);
		// 设置软件图标
		Drawable drawable = (Drawable) map.get("ico");
		checkboxView.iv_item.setImageDrawable(drawable);
		// 获取软件的包名
		final String packageName = (String) map.get("packageName");
		// 查看以前是否应经选择
		Boolean hasChecked = sharedPreferences.getBoolean(packageName, false);
		if (hasChecked) {
			checkboxView.cb_status.setChecked(true);
		}
		checkboxView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editor editor = sharedPreferences.edit();
				if (checkboxView.cb_status.isChecked()) {
					checkboxView.cb_status.setChecked(false);
					editor.putBoolean(packageName, false);
				} else {
					checkboxView.cb_status.setChecked(true);
					editor.putBoolean(packageName, true);
				}
				// 记得一定要提交事务
				editor.commit();
			}
		});
		return checkboxView;
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