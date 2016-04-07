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

public class QueryDialog extends Dialog{

	public QueryDialog(Context context) {
		super(context);
	}
	
	public QueryDialog(Context context, int theme) {
		super(context, theme);
	}
	
	public static class Builder {
		private Context context;
		private EditText et_query_name;
		private EditText et_query_material;
		private EditText et_query_effect;
		private Button btn_ok;
		private Button btn_cancel;

		
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
        
        public String getQueryName(){
        	return et_query_name.getText().toString();
        }
        
        public String getQueryMaterial(){
        	return et_query_material.getText().toString();
        }
        
        public String getQueryEffect(){
        	return et_query_effect.getText().toString();
        }
        
        
        
		public QueryDialog create(){
			LayoutInflater inflater = (LayoutInflater) context  
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			final QueryDialog dialog = new QueryDialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			View layout = inflater.inflate(R.layout.layout_query_dialog, null);
			dialog.addContentView(layout, new LayoutParams(  
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			et_query_name = (EditText) layout.findViewById(R.id.et_query_name);
			et_query_material = (EditText) layout.findViewById(R.id.et_query_material);
			et_query_effect = (EditText) layout.findViewById(R.id.et_query_effect);
			
			//dialog.setTitle("输入查询条件");
			
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
			
			Window win = dialog.getWindow();
			win.getDecorView().setPadding(0, 0, 0, 0);
			WindowManager.LayoutParams lp = win.getAttributes();
			lp.width = (int) 800;
			lp.height = (int) 530;
	        win.setAttributes(lp);
	        
			dialog.setContentView(layout);
			return dialog;
		}
		
	}
	
}