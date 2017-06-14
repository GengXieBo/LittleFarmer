package com.example.lenovo.littlefarmer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import beans.Plant;
import util.GradationScrollView;

public class PlantDetailActivity extends AppCompatActivity implements
        GradationScrollView.ScrollViewListener , View.OnClickListener{

    private TextView plantName = null;
    private TextView plantType = null ;
    private TextView plantSeason = null ;
    private TextView plantDescripe = null ;
    private TextView plantMatureTime = null ;

    private GradationScrollView plantScrollView = null ;
    private RelativeLayout barRetive = null ;

    private int height ;

    private ImageView plantImage = null ;

    private ImageView backImage = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);

        plantName = (TextView)findViewById(R.id.plant_name_text) ;
        plantType = (TextView)findViewById(R.id.plant_type_text) ;
        plantSeason = (TextView)findViewById(R.id.plant_season_text) ;
        plantDescripe = (TextView)findViewById(R.id.plant_descripe_text) ;
        backImage = (ImageView)findViewById(R.id.back_image) ;
        plantMatureTime = (TextView)findViewById(R.id.plant_mature_time) ;

        plantScrollView = (GradationScrollView)findViewById(R.id.plant_scroll_view) ;
        plantImage = (ImageView)findViewById(R.id.plant_pic_image) ;
        barRetive = (RelativeLayout)findViewById(R.id.activity_detail_bar) ;

        Intent intent = getIntent() ;

        plantName.setText(intent.getStringExtra("plantName"));
        plantType.setText(intent.getStringExtra("plantType"));
        plantSeason.setText(intent.getStringExtra("plantSeason"));
        plantDescripe.setText(intent.getStringExtra("plantDescripe"));
        plantMatureTime.setText(String.valueOf(intent.getIntExtra("plantMatureTime" , 1)));


        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE) ;

        Picasso.with(this).load(intent.getStringExtra("plantPic").substring(20))
                .resize(wm.getDefaultDisplay().getWidth() , 400).into(plantImage);

        plantImage.setFocusable(true);
        plantImage.setFocusableInTouchMode(true);
        plantImage.requestFocus();

        backImage.setOnClickListener(this);

        //获取顶部图片高度后，为ScrollView设置监听
        initListener() ;

    }

    private void initListener() {
        //为标题栏设置透明
        barRetive.setBackgroundColor(Color.argb((int) 0, 63,81,181));

        ViewTreeObserver vt = plantImage.getViewTreeObserver() ;
        vt.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    barRetive.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                height = plantImage.getHeight() ;
                plantScrollView.setScrollViewListener(PlantDetailActivity.this);
            }
        });

    }

    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (y <= 0) {   //设置标题的背景颜色
            barRetive.setBackgroundColor(Color.argb((int) 0, 63,81,181));
        } else if (y > 0 && y <= height) { //滑动距离小于plant图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            barRetive.setBackgroundColor(Color.argb((int) alpha, 63,81,181));
        } else {    //滑动到plant下面设置普通颜色
            barRetive.setBackgroundColor(Color.argb((int) 255, 63,81,181));
        }
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
