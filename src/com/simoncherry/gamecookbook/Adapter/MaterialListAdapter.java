package com.simoncherry.gamecookbook.Adapter;

import java.util.List;

import com.simoncherry.gamecookbook.R;
import com.simoncherry.gamecookbook.Bean.FoodListBean;
import com.simoncherry.gamecookbook.Bean.MaterialListBean;

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

public class MaterialListAdapter extends BaseAdapter{
	private Context ctx;
	private LayoutInflater inflater;
	private List<MaterialListBean> list;
	
	public MaterialListAdapter(Context context, List<MaterialListBean> list){
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
			convertView = inflater.inflate(R.layout.item_material_list, null);
			holder = new ViewHolder();
			holder.tv_material_name = (TextView) convertView.findViewById(R.id.tv_material_name);
			holder.tv_material_weight = (TextView) convertView.findViewById(R.id.tv_material_weight);
			holder.img_material_icon = (ImageView) convertView.findViewById(R.id.img_material_icon);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		MaterialListBean listbean = list.get(position);
		int material_imgID = listbean.getMaterialImgIndex();
		String material_name = listbean.getMaterialName();
		int material_weight = listbean.getMaterialWeight();
		String material_unit = listbean.getMaterialUnit();
		
		//holder.img_material_icon.setImageResource(material_imgID);
		int imgID = ctx.getResources().getIdentifier("icon_"+String.valueOf(material_imgID), "drawable", ctx.getPackageName());
		holder.img_material_icon.setImageBitmap(decodeSampledBitmapFromResource(
				ctx.getResources(), imgID, 64, 64));
		
		holder.tv_material_name.setText(material_name);
		holder.tv_material_weight.setText(
				String.valueOf(material_weight) + material_unit);
		
		return convertView;
	}
	
	private static class ViewHolder {
		TextView tv_material_name;
		TextView tv_material_weight;
		ImageView img_material_icon;
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