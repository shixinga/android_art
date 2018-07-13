package com.shixing.materialdesign_floattingactionbar_behavior;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FabRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<String> list;

	public FabRecyclerAdapter(List<String> list) {
        // TODO Auto-generated constructor stub
		this.list = list;
	}
	
	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		String str = list.get(position);
		MyViewHolder holder = (MyViewHolder) viewHolder;
		holder.tv.setText(str);
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(arg0.getContext()).inflate(R.layout.listitem, arg0, false);
		return new MyViewHolder(view);
	}
	
	class MyViewHolder extends RecyclerView.ViewHolder {

		private TextView tv;

		public MyViewHolder(View itemView) {
			super(itemView);
			tv = (TextView) itemView.findViewById(R.id.tv);
		}
		
	}

}
