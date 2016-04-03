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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ListView list_food_name;
	private GridView list_material;
	private ListView list_step;
	private ListView list_material_edit;
	private ListView list_step_edit;
	
	private TextView tv_food_name;
	private ImageView img_food;
	private ImageView img_rank;
	private Button btn_arrow;
	private Button btn_query;
	private Button btn_edit;
	private ViewGroup layout_page_1;
	private ViewGroup layout_page_2;
	private ViewGroup layout_show;
	private ViewGroup layout_edit;
	
	private List<FoodListBean> bean_food;
	private FoodListAdapter adapter_food;
	
	private List<MaterialListBean> bean_material;
	private MaterialListAdapter adapter_material;
	
	private List<StepListBean> bean_step;
	private StepListAdapter adapter_step;
	
	private int page = 1;
	private boolean isEdit = false;
	
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
		img_food = (ImageView) findViewById(R.id.img_food);
		img_rank = (ImageView) findViewById(R.id.img_rank);
		btn_arrow = (Button) findViewById(R.id.btn_arrow);
		btn_query = (Button) findViewById(R.id.btn_query);
		btn_edit = (Button) findViewById(R.id.btn_edit);
		layout_page_1 = (ViewGroup) findViewById(R.id.layout_page_1);
		layout_page_2 = (ViewGroup) findViewById(R.id.layout_page_2);
		layout_show = (ViewGroup) findViewById(R.id.layout_show);
		layout_edit = (ViewGroup) findViewById(R.id.layout_edit);
		
		list_food_name.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				tv_food_name.setText("◆"+bean_food.get(position).getFoodName()+"◆");
				setHighLightItem(position);
				setFoodImg(bean_food.get(position).getFoodImgIndex());
				setFoodRank(bean_food.get(position).getFoodRank());
				showMaterial(position);
				showStep(position);
				
				page = 1;
				layout_page_1.setVisibility(View.VISIBLE);
				layout_page_2.setVisibility(View.INVISIBLE);
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
				}else{
					isEdit = false;
					btn_edit.setBackgroundResource(R.drawable.tab_default);
					layout_show.setVisibility(View.VISIBLE);
					layout_edit.setVisibility(View.INVISIBLE);
					btn_arrow.setVisibility(View.VISIBLE);
				}
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
	
	private void addFoodToList(String name, int img_index, int rank){
		FoodListBean listbean = new FoodListBean();
		listbean.setFoodName(name);
		listbean.setFoodImgIndex(img_index);
		listbean.setFoodRank(rank);
		bean_food.add(listbean);
		adapter_food.notifyDataSetChanged();
	}
	
	private void initFoodList(){
		bean_food = new ArrayList<FoodListBean>();
		setFoodAdapter(bean_food);

		addFoodToList("龙老汤面", 1, 2);
		addFoodToList("药膳麻婆豆腐", 2, 3);
		addFoodToList("猛虎炒饭", 3, 2);
		addFoodToList("多汁牛排", 5, 7);
		addFoodToList("特制炖牛肉", 4, 6);
		addFoodToList("杂锦肉锅", 2, 5);
		addFoodToList("浓汤炖鱼锅", 8, 5);
		addFoodToList("香酥炸鱼排", 6, 3);
		addFoodToList("田园蛋包饭", 7, 4);
		addFoodToList("会心奶酪培根面", 1, 1);
		
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
		// TODO test
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
		showMaterial(0);
	}
	
	private void showMaterial(int index){
		if(bean_material.size() > 0){
			bean_material.clear();
		}
		switch(index){
			case 0 :
				addMaterialToList("面条", 248, 300, "g");
				addMaterialToList("鲜虾", 241, 200, "g");
				addMaterialToList("鱼肉", 127, 200, "g");
				addMaterialToList("洋葱", 39, 10, "g");
				addMaterialToList("胡萝卜", 26, 10, "g");
				break;
			case 1 :
				addMaterialToList("豆腐", 130, 300, "g");
				addMaterialToList("辣椒", 35, 30, "g");
				addMaterialToList("番茄", 28, 50, "g");
				addMaterialToList("洋葱", 39, 10, "g");
				addMaterialToList("胡萝卜", 26, 10, "g");
				break;
			case 2 :
				addMaterialToList("米饭", 248, 300, "g");
				addMaterialToList("辣椒", 35, 10, "g");
				addMaterialToList("猪肉", 129, 100, "g");
				addMaterialToList("洋葱", 39, 20, "g");
				addMaterialToList("胡萝卜", 26, 20, "g");
				break;
			case 3 :
				addMaterialToList("牛排", 129, 300, "g");
				addMaterialToList("薄荷", 52, 5, "g");
				addMaterialToList("洋葱", 39, 10, "g");
				addMaterialToList("黑椒", 178, 5, "g");
				break;
			case 4 :
				addMaterialToList("牛肉", 129, 400, "g");
				addMaterialToList("辣椒", 35, 10, "g");
				addMaterialToList("黑椒", 178, 5, "g");
				addMaterialToList("洋葱", 39, 10, "g");
				addMaterialToList("胡萝卜", 26, 10, "g");
				break;
			case 5 :
				addMaterialToList("猪肉", 129, 100, "g");
				addMaterialToList("鲜虾", 241, 100, "g");
				addMaterialToList("鱼肉", 127, 100, "g");
				addMaterialToList("番茄", 28, 50, "g");
				addMaterialToList("洋葱", 39, 10, "g");
				break;
			case 6 :
				addMaterialToList("鱼肉", 127, 400, "g");
				addMaterialToList("高汤", 178, 200, "g");
				addMaterialToList("洋葱", 39, 10, "g");
				break;
			case 7 :
				addMaterialToList("鱼排", 125, 300, "g");
				addMaterialToList("香料", 51, 10, "g");
				addMaterialToList("柠檬", 201, 10, "g");
				break;
			case 8 :
				addMaterialToList("米饭", 250, 300, "g");
				addMaterialToList("鸡蛋", 59, 30, "g");
				addMaterialToList("番茄", 28, 50, "g");
				addMaterialToList("香菜", 53, 5, "g");
				break;
			case 9 :
				addMaterialToList("面条", 248, 300, "g");
				addMaterialToList("培根", 129, 80, "g");
				addMaterialToList("奶油", 178, 30, "g");
				break;
		}
	}
	
	private void setStepAdapter(List<StepListBean> list){
		adapter_step = new StepListAdapter(getBaseContext(), list);
		list_step.setAdapter(adapter_step);
		// TODO test
		list_step_edit.setAdapter(adapter_step);
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
}
