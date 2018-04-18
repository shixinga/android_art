package csx.haha.com.our.selfview;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import csx.haha.com.our.R;


public class IconCheckboxView extends LinearLayout {

	public CheckBox cb_status;
	public ImageView iv_item;

	public IconCheckboxView(Context context) {
		super(context);
		iniView(context);
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	private void iniView(Context context) {
		View.inflate(context, R.layout.list_menu_item, this);
		cb_status = (CheckBox) this.findViewById(R.id.cb_status);
		iv_item = (ImageView) this.findViewById(R.id.iv_item);
	}

	/**
	 * 提供给在代码中通过findViewById()获取的该类对象访问该cb_status的check状态
	 */

	public boolean isChecked() {
		return cb_status.isChecked();
	}


	public void setChecked(boolean checked) {
		cb_status.setChecked(checked);
	}

}
