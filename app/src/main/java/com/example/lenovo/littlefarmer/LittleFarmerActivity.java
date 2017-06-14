package com.example.lenovo.littlefarmer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.bmob.v3.Bmob;
import myview.FarmerViewPagerAdapter;


public class LittleFarmerActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager viewPager = null ;
    private FarmerViewPagerAdapter farmerViewPagerAdapter = null ;

    private RelativeLayout mainRelativeLayout = null ;
    private RelativeLayout areaRelativeLayout = null ;
    private RelativeLayout plantRelativeLayout = null ;
    private RelativeLayout personalRelativeLayout = null ;

    private ImageView mainImageView = null ;
    private ImageView areaImageView = null ;
    private ImageView plantImageView = null ;
    private ImageView personalImageView = null ;

    Animation animation = null ;

    private int prePosition = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_little_farmer);

        if(getSupportActionBar() !=  null){
            getSupportActionBar().hide();
        }

        Bmob.initialize(this , "f6f446756856568d0a130cb1b3da5e07");

        //初始化动画
        animation = AnimationUtils.loadAnimation(this , R.anim.item_scale_anim) ;

        viewPager = (ViewPager)findViewById(R.id.view_pager) ;

        mainRelativeLayout = (RelativeLayout)findViewById(R.id.main_relative) ;
        areaRelativeLayout = (RelativeLayout)findViewById(R.id.area_relative) ;
        plantRelativeLayout = (RelativeLayout)findViewById(R.id.plant_relative) ;
        personalRelativeLayout = (RelativeLayout)findViewById(R.id.personal_relative) ;

        mainImageView = (ImageView)findViewById(R.id.main_image) ;
        areaImageView = (ImageView)findViewById(R.id.area_image) ;
        plantImageView = (ImageView)findViewById(R.id.plant_image) ;
        personalImageView = (ImageView)findViewById(R.id.personal_image) ;

        mainRelativeLayout.setOnClickListener(this);
        areaRelativeLayout.setOnClickListener(this);
        plantRelativeLayout.setOnClickListener(this);
        personalRelativeLayout.setOnClickListener(this);

        farmerViewPagerAdapter = new FarmerViewPagerAdapter(getSupportFragmentManager()) ;

        viewPager.setAdapter(farmerViewPagerAdapter);
        viewPager.setCurrentItem(0);
        prePosition = 0 ;
        mainImageView.setImageResource(R.mipmap.main_focus);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeImageState(position);
                prePosition = position ;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_relative:
                viewPager.setCurrentItem(0);
                changeImageState(0);
                prePosition = 0 ;
                break ;
            case R.id.area_relative:
                viewPager.setCurrentItem(1);
                changeImageState(1);
                prePosition = 1 ;
                break ;
            case R.id.plant_relative:
                viewPager.setCurrentItem(2);
                changeImageState(2);
                prePosition = 2 ;
                break ;
            case R.id.personal_relative:
                viewPager.setCurrentItem(3);
                changeImageState(3);
                prePosition = 3 ;
                break ;
            default:
                break ;
        }
    }

    public void changeImageState(int currentPosition){
        switch (prePosition){
            case 0:
                mainImageView.setImageResource(R.mipmap.main_unfocus);
                break ;
            case 1:
                areaImageView.setImageResource(R.mipmap.area_unfocus);
                break ;
            case 2:
                plantImageView.setImageResource(R.mipmap.plant_unfocus);
                break ;
            case 3:
                personalImageView.setImageResource(R.mipmap.personal_unfocus);
                break ;
            default:
                break ;
        }
        switch (currentPosition){
            case 0:
                mainImageView.setImageResource(R.mipmap.main_focus);
                mainImageView.startAnimation(animation);
                break ;
            case 1:
                areaImageView.setImageResource(R.mipmap.area_focus);
                areaImageView.startAnimation(animation);
                break ;
            case 2:
                plantImageView.setImageResource(R.mipmap.plant_focus);
                plantImageView.startAnimation(animation);
                break ;
            case 3:
                personalImageView.setImageResource(R.mipmap.personal_focus);
                personalImageView.startAnimation(animation);
                break ;
            default:
                break ;
        }
    }
}
