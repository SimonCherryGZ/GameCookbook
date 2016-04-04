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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity{
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
	private Button btn_add_food;
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
		btn_add_food = (Button) findViewById(R.id.btn_add_material);
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
				tv_food_name.setText("◆"+bean_food.get(position).getFoodName()+"◆");
				tv_food_effect.setText(bean_food.get(position).getFoodEffect());
				setHighLightItem(position);
				setFoodImg(bean_food.get(position).getFoodImgIndex());
				setFoodRank(bean_food.get(position).getFoodRank());
				showMaterial(position);
				showStep(position);
				
				page = 1;
				layout_page_1.setVisibility(View.VISIBLE);
				layout_page_2.setVisibility(View.INVISIBLE);
				
				last_position = position;
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
				if(isEdit == false){
					isEdit = true;
					btn_edit.setBackgroundResource(R.drawable.tab_pressed);
					layout_show.setVisibility(View.INVISIBLE);
					layout_edit.setVisibility(View.VISIBLE);
					btn_arrow.setVisibility(View.INVISIBLE);
					
					bean_step = new ArrayList<StepListBean>();
					adapter_step = new StepListAdapter(getBaseContext(), bean_step);
					list_step_edit.setAdapter(adapter_step);
					
					bean_material = new ArrayList<MaterialListBean>();
					adapter_material = new MaterialListAdapter(getBaseContext(), bean_material);
					list_material_edit.setAdapter(adapter_material);
				}else{
//					isEdit = false;
//					btn_edit.setBackgroundResource(R.drawable.tab_default);
//					layout_show.setVisibility(View.VISIBLE);
//					layout_edit.setVisibility(View.INVISIBLE);
//					btn_arrow.setVisibility(View.VISIBLE);
//					
//					showMaterial(last_position);
//					showStep(last_position);
					
					showFoodConfirmDialog(MainActivity.this);
				}
			}
		});
		
		btn_add_food.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				final MaterialEditDialog.Builder builder = new MaterialEditDialog.Builder(MainActivity.this);
				builder.setPositiveButton(new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO
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
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		initFoodList();
		initMaterialList();
		initStepList();
		
		layout_show.setVisibility(View.VISIBLE);
		layout_edit.setVisibility(View.INVISIBLE);
	}
	
	private void setFoodAdapter(List<FoodListBean> list){
		adapter_food = new FoodListAdapter(getBaseContext(), list);
		list_food_name.setAdapter(adapter_food);
	}
	
	private void addFoodToList(String name, String effect, int img_index, int rank){
		FoodListBean listbean = new FoodListBean();
		listbean.setFoodName(name);
		listbean.setFoodEffect(effect);
		listbean.setFoodImgIndex(img_index);
		listbean.setFoodRank(rank);
		bean_food.add(listbean);
		adapter_food.notifyDataSetChanged();
	}
	
	private void initFoodList(){
		bean_food = new ArrayList<FoodListBean>();
		setFoodAdapter(bean_food);

		addFoodToList("龙老汤面", "HP恢复40%", 1, 2);
		addFoodToList("药膳麻婆豆腐", "异常状态抵抗提示30%", 2, 3);
		addFoodToList("猛虎炒饭", "ATK提升50%", 3, 2);
		addFoodToList("多汁牛排", "HP恢复100%", 5, 7);
		addFoodToList("特制炖牛肉", "HP恢复80%", 4, 6);
		addFoodToList("杂锦肉锅", "HP恢复30%、SP恢复30%", 2, 5);
		addFoodToList("浓汤炖鱼锅", "异常状态恢复", 8, 5);
		addFoodToList("香酥炸鱼排", "会心一击率提升25%", 6, 3);
		addFoodToList("田园蛋包饭", "DEF提升20%", 7, 4);
		addFoodToList("会心奶酪培根面", "HP恢复30%", 1, 1);
		
		setHighLightItem(0);
		setFoodImg(bean_food.get(0).getFoodImgIndex());
		setFoodRank(bean_food.get(0).getFoodRank());
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
		showMaterial(0);
	}
	
	private void showMaterial(int index){
		if(bean_material.size() > 0){
			bean_material.clear();
		}
		switch(index){
			case 0 :
				addMaterialToList("面条", 33, 300, "g");
				addMaterialToList("鲜虾", 32, 200, "g");
				addMaterialToList("鱼肉", 26, 200, "g");
				addMaterialToList("洋葱", 9, 10, "g");
				addMaterialToList("胡萝卜", 2, 10, "g");
				break;
			case 1 :
				addMaterialToList("豆腐", 28, 300, "g");
				addMaterialToList("辣椒", 7, 30, "g");
				addMaterialToList("番茄", 3, 50, "g");
				addMaterialToList("洋葱", 9, 10, "g");
				addMaterialToList("胡萝卜", 2, 10, "g");
				break;
			case 2 :
				addMaterialToList("米饭", 34, 300, "g");
				addMaterialToList("辣椒", 7, 10, "g");
				addMaterialToList("猪肉", 27, 100, "g");
				addMaterialToList("洋葱", 9, 20, "g");
				addMaterialToList("胡萝卜", 2, 20, "g");
				break;
			case 3 :
				addMaterialToList("牛排", 27, 300, "g");
				addMaterialToList("薄荷", 16, 5, "g");
				addMaterialToList("洋葱", 9, 10, "g");
				addMaterialToList("黑椒", 30, 5, "g");
				break;
			case 4 :
				addMaterialToList("牛肉", 27, 400, "g");
				addMaterialToList("辣椒", 7, 10, "g");
				addMaterialToList("黑椒", 30, 5, "g");
				addMaterialToList("洋葱", 9, 10, "g");
				addMaterialToList("胡萝卜", 2, 10, "g");
				break;
			case 5 :
				addMaterialToList("猪肉", 27, 100, "g");
				addMaterialToList("鲜虾", 32, 100, "g");
				addMaterialToList("鱼肉", 26, 100, "g");
				addMaterialToList("番茄", 4, 50, "g");
				addMaterialToList("洋葱", 9, 10, "g");
				break;
			case 6 :
				addMaterialToList("鱼肉", 26, 400, "g");
				addMaterialToList("高汤", 30, 200, "g");
				addMaterialToList("洋葱", 9, 10, "g");
				break;
			case 7 :
				addMaterialToList("鱼排", 25, 300, "g");
				addMaterialToList("香料", 14, 10, "g");
				addMaterialToList("柠檬", 31, 10, "g");
				break;
			case 8 :
				addMaterialToList("米饭", 34, 300, "g");
				addMaterialToList("鸡蛋", 19, 30, "g");
				addMaterialToList("番茄", 3, 50, "g");
				addMaterialToList("香菜", 15, 5, "g");
				break;
			case 9 :
				addMaterialToList("面条", 33, 300, "g");
				addMaterialToList("培根", 27, 80, "g");
				addMaterialToList("奶油", 18, 30, "g");
				break;
		}
	}
	
	private void setStepAdapter(List<StepListBean> list){
		adapter_step = new StepListAdapter(getBaseContext(), list);
		list_step.setAdapter(adapter_step);
	}
	
	private void initStepList(){
		bean_step = new ArrayList<StepListBean>();
		setStepAdapter(bean_step);
		showStep(0);
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
		
		switch(index){
			case 0 :
				addStepToList("大虾去掉虾足、肠线，洗净");
				addStepToList("炒锅倒油，放入姜丝，虾略微煎一下");
				addStepToList("煎出红色的虾油后倒入水，加盐、生抽");
				addStepToList("水沸后煮面");
				addStepToList("面条煮熟后放入生菜略煮");
				break;
			case 1 :
				addStepToList("豆腐切小块，开水放少许盐煮5分钟，捞出备用");
				addStepToList("准备蒜末，姜末");
				addStepToList("油热后转小火，炒豆瓣酱");
				addStepToList("出红油后放入蒜末，姜末");
				addStepToList("放适量水，盐，糖，鸡精，花椒粉调味");
				addStepToList("倒入豆腐大火烧开转小火，继续炖5分钟，使其入味");
				addStepToList("调好淀粉，倒入锅中");
				addStepToList("大火勾芡，出锅");
				break;
			case 2 :
				addStepToList("青椒洗净切沫葱切沫备用要");
				addStepToList("肉洗净切沫腌上加黄酒生抽白胡椒");
				addStepToList("锅内放油煸葱煸肉丝");
				addStepToList("鸡蛋打散炒岀来备用");
				addStepToList("肉煸岀香味放米饭炒");
				addStepToList("放炒好鸡蛋放盐炒３分钟左右即可盛岀");
				break;
			case 3 :
				addStepToList("腌渍牛排4个小时");
				addStepToList("将腌制好的牛排放进烤箱加热5分钟");
				addStepToList("平底锅放入黄油，将牛排的表面2分钟");
				addStepToList("将洋葱和蘑菇切好，锅里放油，先炒洋葱，再加入蘑菇，最后倒入黑椒汁翻炒");
				break;
			case 4 :
				addStepToList("牛肉清洗干净");
				addStepToList("切成3厘米见方的肉块");
				addStepToList("将牛肉及调料品一同入锅中，水可以多加一些");
				addStepToList("放入炖锅炖1.5小时");
				addStepToList("待水开后，撇去浮沫，继续慢炖，盐可以根据口味来添加");
				break;
			case 5 :
				addStepToList("将冬菇、金针、云耳水发好，咸肉切断几段");
				addStepToList("放小许油，放几片姜在小煲里爆一爆");
				addStepToList("再加入咸肉段，爆一小会");
				addStepToList("再放入冬菇、金针、云耳");
				addStepToList("加生抽、盐、糖焖，焖到入味放豆腐块滚几滚，上煲");
				break;
			case 6 :
				addStepToList("锅里放入适量的鸡油");
				addStepToList("鲫鱼用厨房纸擦干后，放入锅里煎");
				addStepToList("两面金黄后，把姜丝、葱段放旁边煸炒一下");
				addStepToList("兑足量的热水大火煮沸，撇去浮沫");
				addStepToList("小火炖到汤汁奶白，调入盐黑胡椒煮开即可，起锅放香油");
				break;
			case 7 :
				addStepToList("鱼排切块");
				addStepToList("用盐，酱油，鸡精，十三香，辣椒粉，料酒");
				addStepToList("拌匀腌制一到半天入味");
				addStepToList("然后放进浇至八成热的油锅里");
				addStepToList("炸金黄炸熟透");
				break;
			case 8 :
				addStepToList("熟的冷冻豌豆用开水烫一下进行解冻，再沥干水份待用");
				addStepToList("洋葱洗净切丁、午餐肉切丁");
				addStepToList("锅内倒少量油，倒入洋葱和午餐肉翻炒");
				addStepToList("然后加入米饭、番茄酱、酱油和沥干水的豌豆翻炒均匀");
				addStepToList("三个鸡蛋加一点盐打散");
				addStepToList("平底锅里到一点油小火烧热，倒入一半的蛋液后晃动锅子摊一张蛋饼");
				addStepToList("放上适量的炒好的米饭，左右两边的蛋饼向上折，然后盛到盘子里用蕃茄酱装饰一下");
				break;
			case 9 :
				addStepToList("准备材料：芦笋洗净切小段；三文鱼切小条");
				addStepToList("热锅，锅内放黄油");
				addStepToList("倒入牛奶");
				addStepToList("倒入奶油");
				addStepToList("开锅后放入芦笋和三文鱼");
				addStepToList("放入莳萝，黑胡椒，盐调味。 放入煮好的意大利面，收汤后即可");
				break;
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
	
	private void showFoodConfirmDialog(Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);  
		builder.setTitle("确认添加此菜谱？");
		//builder.setMessage("确认添加此菜谱？");
		builder.setPositiveButton("确认添加",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	isEdit = false;
                    	
                    	String food_name = et_food_name.getText().toString();
                    	String food_effect = et_food_effect.getText().toString();
                    	addFoodToList(food_name, food_effect, temp_icon_index, temp_rank_index);
                    	
    					btn_edit.setBackgroundResource(R.drawable.tab_default);
    					layout_show.setVisibility(View.VISIBLE);
    					layout_edit.setVisibility(View.INVISIBLE);
    					btn_arrow.setVisibility(View.VISIBLE);
    					
    					showMaterial(last_position);
    					showStep(last_position);

    					list_food_name.smoothScrollToPosition(bean_food.size()-1);
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
    					btn_edit.setBackgroundResource(R.drawable.tab_default);
    					layout_show.setVisibility(View.VISIBLE);
    					layout_edit.setVisibility(View.INVISIBLE);
    					btn_arrow.setVisibility(View.VISIBLE);
    					
    					showMaterial(last_position);
    					showStep(last_position);                    	
                    	
                        dialog.dismiss();
                    }  
                });  
        builder.show(); 
	}
	
}
