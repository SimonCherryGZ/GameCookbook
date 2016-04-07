package com.simoncherry.gamecookbook.Custom;

import java.util.ArrayList;
import java.util.List;

import com.simoncherry.gamecookbook.R;
import com.simoncherry.gamecookbook.Adapter.IconSelectListAdapter;
import com.simoncherry.gamecookbook.Bean.IconSelectListBean;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MaterialEditDialog extends Dialog{

	public MaterialEditDialog(Context context) {
		super(context);
	}
	
	public MaterialEditDialog(Context context, int theme) {
		super(context, theme);
	}
	
	public static class Builder {
		private Context context;
		private GridView gridview_icon;
		private ImageView img_material;
		private EditText et_material_name;
		private EditText et_material_weight;
		private EditText et_material_unit;
		private Button btn_ok;
		private Button btn_cancel;
		private List<IconSelectListBean> bean_icon;
		private IconSelectListAdapter adapter_icon;
		
		private int integer_icon_index;
		private String text_material_name;
		private int integer_material_weight;
		private String text_material_unit;
		
		private DialogInterface.OnClickListener positiveButtonClickListener;  
        private DialogInterface.OnClickListener negativeButtonClickListener;
		
		public Builder(Context context) {  
            this.context = context;
        }
		
		public Builder setPositiveButton(DialogInterface.OnClickListener listener) {  
            this.positiveButtonClickListener = listener;  
            return this;  
        }  
  
        public Builder setNegativeButton(DialogInterface.OnClickListener listener) {  
            this.negativeButtonClickListener = listener;  
            return this;  
        }  
        
        public void setIconIndex(int index){
        	this.integer_icon_index = index;
        }
        
        public int getIconIndex(){
			return this.integer_icon_index;
        }
        
        public void setMaterialName(String name){
        	this.text_material_name = name;
        }
        
        public String getMaterialName(){
        	text_material_name = et_material_name.getText().toString();
        	return this.text_material_name;
        }
		
        public void setMaterialWeight(int weight){
        	this.integer_material_weight = weight;
        }
        
        public int getMaterialWeight(){
        	integer_material_weight = 
        			Integer.parseInt(et_material_weight.getText().toString());
        	return this.integer_material_weight;
        }
        
        public void setMaterialUnit(String unit){
        	this.text_material_unit = unit;
        }
        
        public String getMaterialUnit(){
        	text_material_unit = et_material_unit.getText().toString();
        	return this.text_material_unit;
        }
        
        
		public MaterialEditDialog create(){
			LayoutInflater inflater = (LayoutInflater) context  
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			final MaterialEditDialog dialog = new MaterialEditDialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			View layout = inflater.inflate(R.layout.layout_material_dialog, null);
			dialog.addContentView(layout, new LayoutParams(  
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			img_material = (ImageView) layout.findViewById(R.id.img_material);
			gridview_icon = (GridView) layout.findViewById(R.id.girdview_material);
			et_material_name = (EditText) layout.findViewById(R.id.et_material_name);
			et_material_weight = (EditText) layout.findViewById(R.id.et_material_weight);
			et_material_unit = (EditText) layout.findViewById(R.id.et_material_unit);
			
			if (positiveButtonClickListener != null) {
				btn_ok = (Button) layout.findViewById(R.id.btn_ok);
				btn_ok.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						positiveButtonClickListener.onClick(dialog,  
                                DialogInterface.BUTTON_POSITIVE);
					}
				});
			} else {   
                layout.findViewById(R.id.btn_ok).setVisibility(View.GONE);  
            } 
			
			if (negativeButtonClickListener != null) {
				btn_cancel = (Button) layout.findViewById(R.id.btn_cancel);
				btn_cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						negativeButtonClickListener.onClick(dialog,  
                                DialogInterface.BUTTON_NEGATIVE);
					}
				});
			} else {   
                layout.findViewById(R.id.btn_cancel).setVisibility(View.GONE);  
            }
			
			gridview_icon.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
					String img_name = "icon" + String.valueOf(position+1);
					int resId = context.getResources().getIdentifier(img_name, "drawable", context.getPackageName());
					img_material.setImageResource(resId);
					
					setIconIndex(position+1);
				}
			});
			
			
			initIconList();
			
			Window win = dialog.getWindow();
			win.getDecorView().setPadding(0, 0, 0, 0);
			WindowManager.LayoutParams lp = win.getAttributes();
	        //lp.width = WindowManager.LayoutParams.MATCH_PARENT;
	        //lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
			lp.width = (int) 920;
			lp.height = (int) 650;
	        win.setAttributes(lp);
	        
			dialog.setContentView(layout);
			return dialog;
		}
		
		private void setIconAdapter(List<IconSelectListBean> list){
			adapter_icon = new IconSelectListAdapter(context, list);
			gridview_icon.setAdapter(adapter_icon);
		}
		
		private void initIconList(){
			bean_icon = new ArrayList<IconSelectListBean>();
			setIconAdapter(bean_icon);
			
			Resources res = context.getResources();
			
			for(int i=1; i<=53; i++){
				String img_name = "icon" + String.valueOf(i);
				int img_id = res.getIdentifier(img_name, "drawable", context.getPackageName());
				addIconToList(img_id);
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