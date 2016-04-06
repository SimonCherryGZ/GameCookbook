package com.simoncherry.gamecookbook.Activity;

import java.util.ArrayList;
import java.util.List;

import com.simoncherry.gamecookbook.R;
import com.simoncherry.gamecookbook.Adapter.FoodListAdapter;
import com.simoncherry.gamecookbook.Adapter.MaterialListAdapter;
import com.simoncherry.gamecookbook.Adapter.StepListAdapter;
import com.simoncherry.gamecookbook.Bean.FoodListBean;
import com.simoncherry.gamecookbook.Bean.MaterialListBean;
import com.simoncherry.gamecookbook.Bean.StepListBean;
import com.simoncherry.gamecookbook.Custom.IconSelectDialog;
import com.simoncherry.gamecookbook.Custom.IconSelectDialog.OnIconSelectListener;
import com.simoncherry.gamecookbook.Custom.MaterialEditDialog;
import com.simoncherry.gamecookbook.Custom.QueryDialog;
import com.simoncherry.gamecookbook.Helper.SQLiteHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	private static String DB_NAME = "gamecookbook.db";
	private static int DB_VERSION = 1;

	private Cursor cursor;
	private SQLiteDatabase db;
	private SQLiteOpenHelper dbHelper;
	
	private ListView list_food_name;
	private GridView list_material;
	private ListView list_step;
	private ListView list_material_edit;
	private ListView list_step_edit;
	
	private TextView tv_food_name;
	private TextView tv_food_effect;
	private ImageView img_food;
	private ImageView img_rank;
	private ImageView img_icon_edit;
	private ImageView img_rank_edit;
	private Button btn_arrow;
	private Button btn_query;
	private Button btn_edit;
	private Button btn_add_material;
	private Button btn_add_step;
	private ViewGroup layout_page_1;
	private ViewGroup layout_page_2;
	private ViewGroup layout_show;
	private ViewGroup layout_edit;
	
	private EditText et_food_name;
	private EditText et_food_effect;
	
	private List<FoodListBean> bean_food;
	private FoodListAdapter adapter_food;
	
	private List<MaterialListBean> bean_material;
	private MaterialListAdapter adapter_material;
	
	private List<StepListBean> bean_step;
	private StepListAdapter adapter_step;
	
	private int page = 1;
	private int last_position = 0;
	private boolean isEdit = false;
	private boolean isModify = false;
	private int temp_icon_index = 0;
	private int temp_rank_index = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		list_food_name = (ListView) findViewById(R.id.list_food_name);
		list_material = (GridView) findViewById(R.id.list_material);
		list_step = (ListView) findViewById(R.id.list_step);
		list_material_edit = (ListView) findViewById(R.id.list_material_edit);
		list_step_edit = (ListView) findViewById(R.id.list_step_edit);
		
		tv_food_name = (TextView) findViewById(R.id.tv_food_name);
		tv_food_effect = (TextView) findViewById(R.id.tv_food_effect);
		img_food = (ImageView) findViewById(R.id.img_food);
		img_rank = (ImageView) findViewById(R.id.img_rank);
		img_icon_edit = (ImageView) findViewById(R.id.img_icon_edit);
		img_rank_edit = (ImageView) findViewById(R.id.img_rank_edit);
		btn_arrow = (Button) findViewById(R.id.btn_arrow);
		btn_query = (Button) findViewById(R.id.btn_query);
		btn_edit = (Button) findViewById(R.id.btn_edit);
		btn_add_material = (Button) findViewById(R.id.btn_add_material);
		btn_add_step = (Button) findViewById(R.id.btn_add_step);
		layout_page_1 = (ViewGroup) findViewById(R.id.layout_page_1);
		layout_page_2 = (ViewGroup) findViewById(R.id.layout_page_2);
		layout_show = (ViewGroup) findViewById(R.id.layout_show);
		layout_edit = (ViewGroup) findViewById(R.id.layout_edit);
		
		et_food_name = (EditText) findViewById(R.id.et_food_name);
		et_food_effect = (EditText) findViewById(R.id.et_food_effect);
		
		list_food_name.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showTargetFood(position);
				
				page = 1;
				layout_page_1.setVisibility(View.VISIBLE);
				layout_page_2.setVisibility(View.INVISIBLE);
				
				last_position = position;
			}
		});
		
		list_food_name.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO
				last_position = position;
				showEditFoodConfirmDialog(MainActivity.this, position);
				return true;
			}			
		});
		
		list_material_edit.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("要删除该条目吗？");
				builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {				
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						bean_material.remove(position);
						adapter_material.notifyDataSetChanged();
						dialog.dismiss();
					}
				});
				
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				
				builder.show();
			}
		});
		
		list_step_edit.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("要删除该条目吗？");
				builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {				
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						bean_step.remove(position);
						adapter_step.notifyDataSetChanged();
						dialog.dismiss();
					}
				});
				
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				
				builder.show();
			}			
		});
		
		btn_arrow.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				page++;
				if(page>2){
					page = 1;
				}
				
				if(page == 1){
					layout_page_1.setVisibility(View.VISIBLE);
					layout_page_2.setVisibility(View.INVISIBLE);
				}else{
					layout_page_1.setVisibility(View.INVISIBLE);
					layout_page_2.setVisibility(View.VISIBLE);
				}
			}
		});
		
		btn_edit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(isEdit == false && isModify == false){
					isEdit = true;
					btn_edit.setBackgroundResource(R.drawable.tab_pressed);
					layout_show.setVisibility(View.INVISIBLE);
					layout_edit.setVisibility(View.VISIBLE);
					btn_arrow.setVisibility(View.INVISIBLE);
					
					//bean_step = new ArrayList<StepListBean>();
					//adapter_step = new StepListAdapter(getBaseContext(), bean_step);
					//list_step_edit.setAdapter(adapter_step);
					bean_step.clear();
					adapter_step.notifyDataSetChanged();
					
					//bean_material = new ArrayList<MaterialListBean>();
					//adapter_material = new MaterialListAdapter(getBaseContext(), bean_material);
					//list_material_edit.setAdapter(adapter_material);
					bean_material.clear();
					adapter_material.notifyDataSetChanged();
					
				}else{
					int type = 0;
					if(isEdit == true){
						type = 0;
					}else if(isModify == true){
						type = 1;
					}
					showAddFoodConfirmDialog(MainActivity.this, type);
				}
			}
		});
		
		btn_add_material.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				final MaterialEditDialog.Builder builder = new MaterialEditDialog.Builder(MainActivity.this);
				builder.setPositiveButton(new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						int icon_index = builder.getIconIndex();
						String material_name = builder.getMaterialName();
						int material_weight = builder.getMaterialWeight();
						String material_unit = builder.getMaterialUnit();
						
						addMaterialToList(material_name, icon_index, material_weight, material_unit);
						list_material_edit.smoothScrollToPosition(bean_material.size()-1);
						
						dialog.dismiss();
					}
				});
				
				builder.setNegativeButton(new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				
				builder.create().show();
			}
		});
		
		btn_add_step.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				showAddStepDialog(MainActivity.this);
			}
		});
		
		img_icon_edit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				IconSelectDialog.Builder builder = new IconSelectDialog.Builder(
						MainActivity.this, 0);

				builder.setClicklistener(new OnIconSelectListener(){
					@Override
					public void setIcon(int type, int index) {
						if(type == 0){
							temp_icon_index = index;
							String img_name = "food" + String.valueOf(index);
							int img_id = getResources().getIdentifier(img_name, "drawable", getPackageName());
							img_icon_edit.setImageResource(img_id);
						}
					}
				});
				builder.create().show();
			}
		});
		
		img_rank_edit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				IconSelectDialog.Builder builder = new IconSelectDialog.Builder(
						MainActivity.this, 1);

				builder.setClicklistener(new OnIconSelectListener(){
					@Override
					public void setIcon(int type, int index) {
						if(type == 1){
							temp_rank_index = index;
							String img_name = "logo_rank_" + String.valueOf(index);
							int img_id = getResources().getIdentifier(img_name, "drawable", getPackageName());
							img_rank_edit.setImageResource(img_id);
						}
					}
				});
				builder.create().show();
			}
		});
		
		btn_query.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {

				final QueryDialog.Builder builder = new QueryDialog.Builder(MainActivity.this);
				builder.setPositiveButton(new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(bean_food.size() > 0){
							bean_food.clear();
							adapter_food.notifyDataSetChanged();
						}
						
						String query_name = builder.getQueryName();
						String query_material = builder.getQueryMaterial();
						String query_effect = builder.getQueryEffect();
						
//						query_name = "name" + " like " + "'%" + query_name + "%'";
//						query_material = "material" + " like " + "'%" + query_material + "%'";
//						query_effect = "effect" + " like " + "'%" + query_effect + "%'";
//						
//						String query_string = query_name + " and " + query_material + " and " + query_effect;
						
						String query_string = "";
						int start = 0;
						int end = query_material.indexOf(" ", start);
						
						if(end == -1){
							query_material = "material like '%" + query_material + "%'";
							query_string = query_material;
						}else{
							query_string = "material like '%" + query_material.substring(start, end) + "%'";
							start = end+1;
							end = query_material.indexOf(" ", start);
							while(end != -1){
								query_string += " and material like '%" + query_material.substring(start, end) + "%'";
								start = end+1;
								end = query_material.indexOf(" ", start);
							}
							
							query_string += " and material like '%" + query_material.substring(start, query_material.length()) + "%'";
						}

						Toast.makeText(getBaseContext(), query_string, Toast.LENGTH_LONG).show();
						
						cursor = db.query(true, SQLiteHelper.TB_NAME, null,
								query_string, null, null, null, null, null);
						cursor.moveToFirst();
						
			        	while(!cursor.isAfterLast() && (cursor.getString(1) != null)){
			        		addFoodToList(cursor.getString(0),
			        				cursor.getString(1), cursor.getString(2),
			        				cursor.getInt(3), cursor.getInt(4),
			        				cursor.getString(5), cursor.getString(6));
			        		
			        		cursor.moveToNext();
			        	}
			        	
			        	if(bean_food.size() > 0){
			        		showTargetFood(0);
			        	}
						
						dialog.dismiss();
					}
				});
				
				builder.setNegativeButton(new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				
				builder.create().show();
			}
		});
		
		initFoodList();
		initMaterialList();
		initStepList();
		
		try{
			dbHelper = new SQLiteHelper(this, DB_NAME, null, DB_VERSION);
    		db = dbHelper.getWritableDatabase();       	
    		cursor = db.query(SQLiteHelper.TB_NAME, null, null, null, null, null, "name");
        	cursor.moveToFirst();
        	
        	while(!cursor.isAfterLast() && (cursor.getString(1) != null)){
        		addFoodToList(cursor.getString(0),
        				cursor.getString(1), cursor.getString(2),
        				cursor.getInt(3), cursor.getInt(4),
        				cursor.getString(5), cursor.getString(6));
        		
        		cursor.moveToNext();
        	}
        	
        	if(bean_food.size() > 0){
        		showTargetFood(0);
        	}
        	
		}catch(IllegalArgumentException e){
    		e.printStackTrace();
    		++ DB_VERSION;
    		dbHelper.onUpgrade(db, --DB_VERSION, DB_VERSION);
    	}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		//initFoodList();
		//initMaterialList();
		//initStepList();
		
		layout_show.setVisibility(View.VISIBLE);
		layout_edit.setVisibility(View.INVISIBLE);
	}
	
	private void setFoodAdapter(List<FoodListBean> list){
		adapter_food = new FoodListAdapter(getBaseContext(), list);
		list_food_name.setAdapter(adapter_food);
	}
	
	private void addFoodToList(String id, String name, String effect, int img_index, int rank, String material, String step){
		FoodListBean listbean = new FoodListBean();
		listbean.setId(id);
		listbean.setFoodName(name);
		listbean.setFoodEffect(effect);
		listbean.setFoodImgIndex(img_index);
		listbean.setFoodRank(rank);
		listbean.setFoodMaterial(material);
		listbean.setFoodStep(step);
		bean_food.add(listbean);
		adapter_food.notifyDataSetChanged();
	}
	
	private void initFoodList(){
		bean_food = new ArrayList<FoodListBean>();
		setFoodAdapter(bean_food);
	}
	
	private void setFoodImg(int index){
		String img_name = "food" + String.valueOf(index);
		int img_id = getResources().getIdentifier(img_name, "drawable", getPackageName());
		img_food.setImageResource(img_id);
	}
	
	private void setFoodRank(int rank){
		String img_name = "logo_rank_" + String.valueOf(rank);
		int img_id = getResources().getIdentifier(img_name, "drawable", getPackageName());
		img_rank.setImageResource(img_id);
	}
	
	private void setHighLightItem(int index){
		for(int i=0; i<=bean_food.size()-1; i++){
			if(i == index){
				bean_food.get(i).setHighLight(true);
			}else{
				bean_food.get(i).setHighLight(false);
			}
		}
		adapter_food.notifyDataSetChanged();
	}
	
	private void setMaterialAdapter(List<MaterialListBean> list){
		adapter_material = new MaterialListAdapter(getBaseContext(), list);
		list_material.setAdapter(adapter_material);
		list_material_edit.setAdapter(adapter_material);
	}
	
	private void addMaterialToList(String name, int img_index, int weight, String unit){
		MaterialListBean listbean = new MaterialListBean();
		listbean.setMaterialName(name);
		listbean.setMaterialImgIndex(img_index);
		listbean.setMaterialWeight(weight);
		listbean.setMaterialUnit(unit);
		bean_material.add(listbean);
		adapter_material.notifyDataSetChanged();
	}
	
	private void initMaterialList(){
		bean_material = new ArrayList<MaterialListBean>();
		setMaterialAdapter(bean_material);
	}
	
	private void showMaterial(int index){
		if(bean_material.size() > 0){
			bean_material.clear();
		}
		
		int material_icon_index = 0;
		String material_name = "";
		int material_weight = 0;
		String material_unit = "";
		
		String material_code = bean_food.get(index).getFoodMaterial();
		
		for(int i=0; i<= material_code.length()-2;){
			if(material_code.substring(i, i+1).equals("/")){
				int start = i;
				int end = material_code.indexOf("|", start);
				material_icon_index = Integer.parseInt(
						material_code.substring(start+1, end));
				
				start = end+1;
				end = material_code.indexOf("|", start);
				material_name = material_code.substring(start, end);
				
				start = end+1;
				end = material_code.indexOf("|", start);
				material_weight = Integer.parseInt(
						material_code.substring(start, end));
				
				start = end+1;
				end = material_code.indexOf("|", start);
				material_unit = material_code.substring(start, end);
				
				addMaterialToList(
						material_name, material_icon_index, 
						material_weight, material_unit);
				i = end + 1;
			}
		}
	}
	
	private void setStepAdapter(List<StepListBean> list){
		adapter_step = new StepListAdapter(getBaseContext(), list);
		list_step.setAdapter(adapter_step);
		list_step_edit.setAdapter(adapter_step);
	}
	
	private void initStepList(){
		bean_step = new ArrayList<StepListBean>();
		setStepAdapter(bean_step);
	}
	
	private void addStepToList(String text){
		StepListBean listbean = new StepListBean();
		listbean.setStepText(text);
		
		int count = bean_step.size();
		listbean.setStepIndex(count);
		bean_step.add(listbean);
		adapter_step.notifyDataSetChanged();
	}
	
	private void showStep(int index){
		if(bean_step.size() > 0){
			bean_step.clear();
		}
		
		String step_decode = "";
		String step_code = bean_food.get(index).getFoodStep();
		
		for(int i=0; i<= step_code.length()-2;){
			if(step_code.substring(i, i+1).equals("/")){
				int start = i;
				int end = step_code.indexOf("|", start);
				step_decode = step_code.substring(start+1, end);
				
				addStepToList(step_decode);
				i = end + 1;
			}
		}
	}
	
	private void showAddStepDialog(Context context) {  
		final EditText editText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);  
        builder.setTitle("输入步骤");  
        //builder.setMessage("Message");  
        builder.setView(editText);
        builder.setPositiveButton("确定",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	addStepToList(editText.getText().toString());
                    	list_step_edit.smoothScrollToPosition(bean_step.size()-1);
                        dialog.dismiss();
                    }  
                });   
        builder.setNegativeButton("取消",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                        dialog.dismiss();
                    }  
                });  
        builder.show();  
    }
	
	private void showAddFoodConfirmDialog(Context context, final int type){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);  
		String title;
		if(type == 0){
			title = "确认添加此菜谱？";
		}else{
			title = "确认修改此菜谱？";
		}
		
		builder.setTitle(title);
		builder.setPositiveButton("确定操作",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {

                    	if(
                			et_food_name.getText().length()>1 &&
                			et_food_effect.getText().length()>1 &&
                			bean_material.size()>0 && 
                			bean_step.size()>0){
                    		
                    			String name = et_food_name.getText().toString();
                    			String effect = et_food_effect.getText().toString();
                    			int imgid = temp_icon_index;
                    			int rank = temp_rank_index;

	                    		String material_code = "";
	                    		for(int i=0; i<bean_material.size(); i++){
	                    			int icon_index = bean_material.get(i).getMaterialImgIndex();
	                    			String material_name = bean_material.get(i).getMaterialName();
	                    			int material_weight = bean_material.get(i).getMaterialWeight();
	                    			String material_unit = bean_material.get(i).getMaterialUnit();
	                    			
	                    			String code = "/" + String.valueOf(icon_index) + "|" 
	                    					+ material_name + "|"
	                    					+ String.valueOf(material_weight) + "|"
	                    					+ material_unit + "|";
	                    			
	                    			material_code += code;
	                    		}
	                    		
	                    		String step_code = "";
	                    		for(int i=0; i<bean_step.size(); i++){
	                    			String step_text = bean_step.get(i).getStepText();
	                    			
	                    			String code = "/" + step_text + "|";
	                    			
	                    			step_code += code;
	                    		}
	                    		
	                    		ContentValues value = new ContentValues();
	                    		value.put("name", name);
	                    		value.put("effect", effect);
	                    		value.put("imgid", imgid);
	                    		value.put("rank", rank);
	                    		value.put("material", material_code);
	                    		value.put("step", step_code);
	                    		
	                    		if(type == 0){
		                    		Long cookbookID = db.insert(SQLiteHelper.TB_NAME, "id", value);
		                    		
		                    		addFoodToList(cookbookID.toString(), name, effect, imgid, rank,
		                    				material_code, step_code);
		                    		
		    			        	showTargetFood(bean_food.size()-1);
		    			        	
	                    		}else{
	                    			String targetID = bean_food.get(last_position).getId();
	                    			
	                    			db.update(SQLiteHelper.TB_NAME, value, 
	                    					"id=" + targetID, null);
	                    			
	                    			bean_food.remove(last_position);
	                    			FoodListBean listbean = new FoodListBean();
	                    			listbean.setId(targetID);
	                    			listbean.setFoodName(name);
	                    			listbean.setFoodEffect(effect);
	                    			listbean.setFoodImgIndex(imgid);
	                    			listbean.setFoodRank(rank);
	                    			listbean.setFoodMaterial(material_code);
	                    			listbean.setFoodStep(step_code);
	                    			bean_food.add(last_position, listbean);
	                    			adapter_food.notifyDataSetChanged();
	                    			
	                    			showTargetFood(last_position);
	                    		}
                    		
                    	}else{
                    		Toast.makeText(getApplicationContext(), "添加内容为空！", Toast.LENGTH_LONG).show();
                    	}
                    	
                    	isEdit = false;
                    	isModify = false;
    					btn_edit.setBackgroundResource(R.drawable.tab_default);
    					layout_show.setVisibility(View.VISIBLE);
    					layout_edit.setVisibility(View.INVISIBLE);
    					btn_arrow.setVisibility(View.VISIBLE);                   
                    	
    					resetEditUI();
                        dialog.dismiss();
                    }  
                });   
		builder.setNeutralButton("继续编辑",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
        builder.setNegativeButton("放弃编辑",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {
    					isEdit = false;
    					isModify = false;
    					btn_edit.setBackgroundResource(R.drawable.tab_default);
    					layout_show.setVisibility(View.VISIBLE);
    					layout_edit.setVisibility(View.INVISIBLE);
    					btn_arrow.setVisibility(View.VISIBLE);
    					
    					showMaterial(last_position);
    					showStep(last_position);                    	
                    	
    					resetEditUI();
                        dialog.dismiss();
                    }  
                });  
        builder.show(); 
	}
	
	private void showEditFoodConfirmDialog(Context context, final int position){
		AlertDialog.Builder builder = new AlertDialog.Builder(context); 
		builder.setTitle(bean_food.get(position).getFoodName());
		builder.setMessage("要进行什么操作？");
		builder.setPositiveButton("修改", 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						isModify = true;
						showEditTargetFood(position);
						btn_edit.setBackgroundResource(R.drawable.tab_pressed);
						layout_show.setVisibility(View.INVISIBLE);
						layout_edit.setVisibility(View.VISIBLE);
						btn_arrow.setVisibility(View.INVISIBLE);
						
						dialog.dismiss();
					}
				});
		
		builder.setNeutralButton("删除",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						try{
							db.delete(SQLiteHelper.TB_NAME,
									"id" + "=" +
							bean_food.get(position).getId(), null);
							
							bean_food.remove(position);
							adapter_food.notifyDataSetChanged();
							
							if(bean_food.size() > 0){
				        		showTargetFood(0);
				        	}
							
						}catch(Exception e){
							e.printStackTrace();
						}
						
						dialog.dismiss();
					}
				});
		
		builder.setNegativeButton("取消", 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		
		builder.show();
	}
	
	private void showTargetFood(int position){
		tv_food_name.setText("◆"+bean_food.get(position).getFoodName()+"◆");
		tv_food_effect.setText(bean_food.get(position).getFoodEffect());
		setHighLightItem(position);
		setFoodImg(bean_food.get(position).getFoodImgIndex());
		setFoodRank(bean_food.get(position).getFoodRank());
		showMaterial(position);
		showStep(position);
	}
	
	private void resetEditUI(){
		et_food_name.setText("");
		et_food_effect.setText("");
		img_rank_edit.setImageResource(R.drawable.logo_rank_1);
		img_icon_edit.setImageResource(R.drawable.food1);
	}
	
	private int getIconImgId(String type, int index){
		String img_name = "type" + String.valueOf(index);
		int img_id = getResources().getIdentifier(img_name, "drawable", getPackageName());
		return img_id;
	}
	
	private void showEditTargetFood(int position){
		FoodListBean bean_target = bean_food.get(position);
		
		et_food_name.setText(bean_target.getFoodName());
		et_food_effect.setText(bean_target.getFoodEffect());
		temp_rank_index = bean_target.getFoodRank();
		temp_icon_index = bean_target.getFoodImgIndex();
		img_rank_edit.setImageResource(getIconImgId("logo_rank_", temp_rank_index));
		img_icon_edit.setImageResource(getIconImgId("food", temp_icon_index));
		showMaterial(position);
		showStep(position);
	}
}
