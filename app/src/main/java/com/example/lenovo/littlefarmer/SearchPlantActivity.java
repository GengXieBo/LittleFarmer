package com.example.lenovo.littlefarmer;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import beans.Plant;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import config.PropertyConfig;
import myview.PlantRecylerViewAdapter;

public class SearchPlantActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView backImage = null ;

    private ImageView searchImage = null ;

    private EditText searchEdit = null ;

    private RecyclerView searchRecyclerView = null ;

    private ProgressDialog progressDialog = null ;

    private PlantRecylerViewAdapter plantRecylerViewAdapter = null ;

    private List<Plant> plantList = new ArrayList<Plant>() ;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.arg1){
                case PropertyConfig.SEARCH_PLANT_SUCCESS:
                    List<Plant> xplant = (List<Plant>) msg.obj ;
                    for(Plant plant : xplant){
                        plantRecylerViewAdapter.addItem(plant);
                    }
                    progressDialog.dismiss();

                    Toast.makeText(SearchPlantActivity.this , "搜索成功！" , Toast.LENGTH_SHORT).show();
                    break ;
                case PropertyConfig.SEARCH_PLANT_FAILED:
                    progressDialog.dismiss();

                    Toast.makeText(SearchPlantActivity.this , "抱歉，没有您想要的信息！" , Toast.LENGTH_SHORT).show();
                    break ;
            }

        }
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_plant);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        backImage = (ImageView)findViewById(R.id.back_image) ;
        searchImage = (ImageView)findViewById(R.id.search_image) ;
        searchEdit = (EditText)findViewById(R.id.search_edit) ;
        searchRecyclerView = (RecyclerView)findViewById(R.id.search_recycler) ;

        plantRecylerViewAdapter = new PlantRecylerViewAdapter(SearchPlantActivity.this , plantList) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchPlantActivity.this) ;

        searchRecyclerView.setLayoutManager(linearLayoutManager);
        searchRecyclerView.setAdapter(plantRecylerViewAdapter);

        backImage.setOnClickListener(this);
        searchImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_image:
                finish();
                break ;
            case R.id.search_image:
                String plantName = searchEdit.getText().toString() ;
                if(plantName.equals("")){
                    Toast.makeText(SearchPlantActivity.this , "搜索内容为空！" , Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog = ProgressDialog.show(SearchPlantActivity.this , "请稍等" , "正在搜索中..." , true) ;
                    plantList.clear();
                    plantRecylerViewAdapter.notifyDataSetChanged();
                    searchPlant(plantName);

                }
                break ;
            default:
                break;
        }
    }

    public void searchPlant(final String plantName){

        BmobQuery<Plant> plantBmobQuery = new BmobQuery<>() ;
        plantBmobQuery.findObjects(SearchPlantActivity.this, new FindListener<Plant>() {
            @Override
            public void onSuccess(List<Plant> list) {

                List<Plant> plants = new ArrayList<Plant>() ;
                for(Plant p : list){
                    if(p.getPlantName().contains(plantName)){
                        plants.add(p) ;
                    }
                }
                Message message = new Message() ;
                if(plants.size() > 0) {
                    message.arg1 = PropertyConfig.SEARCH_PLANT_SUCCESS ;
                    message.obj = plants ;
                }else{
                    message.arg1 = PropertyConfig.SEARCH_PLANT_FAILED ;
                }

                mHandler.sendMessage(message) ;
            }

            @Override
            public void onError(int i, String s) {
                Message message = new Message() ;
                message.arg1 = PropertyConfig.SEARCH_PLANT_SUCCESS ;
                mHandler.sendMessage(message) ;
            }
        });
    }
}
