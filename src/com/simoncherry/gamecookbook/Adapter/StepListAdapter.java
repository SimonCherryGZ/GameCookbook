package com.simoncherry.gamecookbook.Adapter;

import java.util.List;

import com.simoncherry.gamecookbook.R;
import com.simoncherry.gamecookbook.Bean.FoodListBean;
import com.simoncherry.gamecookbook.Bean.StepListBean;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StepListAdapter extends BaseAdapter{
	private Context ctx;
	private LayoutInflater inflater;
	private List<StepListBean> list;
	
	public StepListAdapter(Context context, List<StepListBean> list){
		this.ctx = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_step_list, null);
			holder = new ViewHolder();
			holder.tv_index = (TextView) convertView.findViewById(R.id.tv_index);
			holder.tv_step = (TextView) convertView.findViewById(R.id.tv_step);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		StepListBean listbean = list.get(position);
		String step_text = listbean.getStepText();
		int step_index = listbean.getStepIndex();
		holder.tv_index.setText(String.valueOf(step_index) + ".");
		holder.tv_step.setText(step_text);
		
		return convertView;
	}
	
	private static class ViewHolder {
		TextView tv_index;
		TextView tv_step;
	}
	
}