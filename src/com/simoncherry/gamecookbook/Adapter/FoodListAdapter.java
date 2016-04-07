package com.simoncherry.gamecookbook.Adapter;

import java.util.List;

import com.simoncherry.gamecookbook.R;
import com.simoncherry.gamecookbook.Bean.FoodListBean;
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

public class FoodListAdapter extends BaseAdapter{
	private Context ctx;
	private LayoutInflater inflater;
	private List<FoodListBean> list;
	
	public FoodListAdapter(Context context, List<FoodListBean> list){
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
			convertView = inflater.inflate(R.layout.item_food_list, null);
			holder = new ViewHolder();
			holder.tv_food_name = (TextView) convertView.findViewById(R.id.tv_item_food_name);
			holder.img_bg = (ImageView) convertView.findViewById(R.id.img_item_bg);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		FoodListBean listbean = list.get(position);
		String food_name = listbean.getFoodName();
		
		holder.tv_food_name.setText(food_name);
		
		if(listbean.getHighLight() == true){
			holder.img_bg.setImageBitmap(decodeSampledBitmapFromResource(
					ctx.getResources(), R.drawable.item_background_pressed,
					160, 40));
		}else{
			holder.img_bg.setImageBitmap(decodeSampledBitmapFromResource(
					ctx.getResources(), R.drawable.item_background_default,
					160, 40));
		}
		
		return convertView;
	}
	
	private static class ViewHolder {
		TextView tv_food_name;
		ImageView img_bg;
	}
	
	public int calculateInSampleSize(BitmapFactory.Options options, 
	        int reqWidth, int reqHeight) { 
	    // 源图片的高度和宽度 
	    final int height = options.outHeight; 
	    final int width = options.outWidth; 
	    int inSampleSize = 1; 
	    if (height > reqHeight || width > reqWidth) { 
	        // 计算出实际宽高和目标宽高的比率 
	        final int heightRatio = Math.round((float) height / (float) reqHeight); 
	        final int widthRatio = Math.round((float) width / (float) reqWidth); 
	        // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高 
	        // 一定都会大于等于目标的宽和高。 
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio; 
	    } 
	    return inSampleSize; 
	}
	
	public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, 
	        int reqWidth, int reqHeight) { 
	    // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小 
	    final BitmapFactory.Options options = new BitmapFactory.Options(); 
	    options.inJustDecodeBounds = true; 
	    BitmapFactory.decodeResource(res, resId, options); 
	    // 调用上面定义的方法计算inSampleSize值 
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight); 
	    
	    // 2016.03.08
	    options.inPreferredConfig = Bitmap.Config.RGB_565;
	    
	    // 使用获取到的inSampleSize值再次解析图片 
	    options.inJustDecodeBounds = false; 
	    return BitmapFactory.decodeResource(res, resId, options); 
	}
	
}