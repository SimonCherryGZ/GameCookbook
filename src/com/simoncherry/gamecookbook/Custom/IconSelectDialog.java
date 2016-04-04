package com.simoncherry.gamecookbook.Custom;

import java.util.ArrayList;
import java.util.List;

import com.simoncherry.gamecookbook.R;
import com.simoncherry.gamecookbook.Adapter.IconSelectListAdapter;
import com.simoncherry.gamecookbook.Bean.IconSelectListBean;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class IconSelectDialog extends Dialog{
	
	public interface OnIconSelectListener {
        void setIcon(int type, int index);
    }

	public IconSelectDialog(Context context) {
		super(context);
	}
	
	public IconSelectDialog(Context context, int theme) {
		super(context, theme);
	}
	
	public static class Builder {
		private Context context;
		private GridView gridview_icon;
		private List<IconSelectListBean> bean_icon;
		private IconSelectListAdapter adapter_icon;
		private OnIconSelectListener mListener;
		
		private int type;
		
		public Builder(Context context, int type) {  
            this.context = context;
            this.type = type;
        }
		
		public void setClicklistener(OnIconSelectListener listener) {
			this.mListener = listener;
		}
		
		public IconSelectDialog create(){
			LayoutInflater inflater = (LayoutInflater) context  
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			final IconSelectDialog dialog = new IconSelectDialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			View layout = inflater.inflate(R.layout.layout_icon_dialog, null);
			dialog.addContentView(layout, new LayoutParams(  
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			gridview_icon = (GridView)layout.findViewById(R.id.girdview_icon);
			
			gridview_icon.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					//Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
					mListener.setIcon(type, position+1);
					dialog.dismiss();
				}
			});
			
			initIconList(type);
			
			dialog.setContentView(layout);
			return dialog;
		}
		
		private void setIconAdapter(List<IconSelectListBean> list){
			adapter_icon = new IconSelectListAdapter(context, list);
			gridview_icon.setAdapter(adapter_icon);
		}
		
		private void initIconList(int type){
			bean_icon = new ArrayList<IconSelectListBean>();
			setIconAdapter(bean_icon);
			
			Resources res = context.getResources();
			
			if(type == 0){
				for(int i=1; i<= 8; i++){
					String img_name = "food" + String.valueOf(i);
					int img_id = res.getIdentifier(img_name, "drawable", context.getPackageName());
					addIconToList(img_id);
				}
			}else{
				for(int i=1; i<= 7; i++){
					String img_name = "logo_rank_" + String.valueOf(i);
					int img_id = res.getIdentifier(img_name, "drawable", context.getPackageName());
					addIconToList(img_id);
				}
			}
		}
		
		private void addIconToList(int resId){
			IconSelectListBean listbean = new IconSelectListBean();
			listbean.setIconResId(resId);
			bean_icon.add(listbean);
			adapter_icon.notifyDataSetChanged();
		}
	}
	
}