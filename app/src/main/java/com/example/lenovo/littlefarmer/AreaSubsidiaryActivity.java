package com.example.lenovo.littlefarmer;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import beans.Area;
import beans.Plant;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import myview.AreaItemRecyclerViewAdapter;
import util.AreaUtil;

public class AreaSubsidiaryActivity extends AppCompatActivity implements View.OnClickListener{

    //初始值为其它类型
    private int areaType = 15 ;

    private RecyclerView subsidiaryRecycler = null ;

    private AreaItemRecyclerViewAdapter areaItemRecyclerViewAdapter = null ;

    private List<Area> areaList = new ArrayList<>() ;

    private TextView barText = null ;

    private ImageView backImage = null ;

    private SwipeRefreshLayout swipeRefreshLayout = null ;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch(msg.arg1){
                case 1:
                    List<Area> areas = (List<Area>)msg.obj ;
                    for(int i = 0 ; i < areas.size() ; i++){
                        areaList.add(areas.get(i)) ;
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    areaItemRecyclerViewAdapter.notifyDataSetChanged();
                    break ;
                case 2:
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(AreaSubsidiaryActivity.this , "土地初始化失败 ! " , Toast.LENGTH_SHORT).show();
                    break ;
                case 3:
                    AreaUtil.plantList = (List<Plant>)msg.obj ;
                    break ;
                case 4:
                    Toast.makeText(AreaSubsidiaryActivity.this , "作物初始化失败! " , Toast.LENGTH_SHORT).show();
                    break ;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_subsidiary);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        subsidiaryRecycler = (RecyclerView)findViewById(R.id.subsidiary_recycler_view) ;
        barText = (TextView)findViewById(R.id.bar_text) ;
        backImage = (ImageView)findViewById(R.id.back_image) ;
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh) ;

        backImage.setOnClickListener(this);

        areaItemRecyclerViewAdapter = new AreaItemRecyclerViewAdapter(areaList , this) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) ;

        subsidiaryRecycler.setLayoutManager(linearLayoutManager);
        subsidiaryRecycler.setAdapter(areaItemRecyclerViewAdapter);

        Intent intent = getIntent() ;
        areaType = intent.getIntExtra("areaType" , 15) ;
        barText.setText(AreaUtil.txts[areaType - 9]);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                areaList.clear();
                areaItemRecyclerViewAdapter.notifyDataSetChanged();
                initData();
            }
        });

        initData() ;

    }

    private void initData() {

        BmobQuery<Area> queryArea = new BmobQuery<>() ;

        queryArea.addWhereEqualTo("areaType" , new Integer(areaType)) ;

        queryArea.findObjects(this, new FindListener<Area>() {
            @Override
            public void onSuccess(List<Area> list) {
                Message msg = new Message() ;
                msg.arg1 = 1 ;
                msg.obj = list ;
                mHandler.sendMessage(msg) ;
            }

            @Override
            public void onError(int i, String s) {
                Log.d("initDate----------------- : " , i + " , " + s) ;
                Message msg = new Message() ;
                msg.arg1 = 2 ;
                mHandler.sendMessage(msg) ;
            }
        });


        BmobQuery<Plant> plantBmobQuery = new BmobQuery<>() ;
        plantBmobQuery.findObjects(this, new FindListener<Plant>() {
            @Override
            public void onSuccess(List<Plant> list) {
                Message message = new Message() ;
                message.arg1 = 3 ;
                message.obj = list ;
                mHandler.sendMessage(message) ;
            }

            @Override
            public void onError(int i, String s) {
                Message message = new Message() ;
                message.arg1 = 4 ;
                mHandler.sendMessage(message) ;
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back_image:
                finish();
                break ;
            default:
                break ;
        }

    }
}
