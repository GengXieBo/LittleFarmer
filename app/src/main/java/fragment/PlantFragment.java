package fragment;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lenovo.littlefarmer.R;
import com.example.lenovo.littlefarmer.SearchPlantActivity;

import java.util.ArrayList;
import java.util.List;

import beans.Plant;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import config.PropertyConfig;
import myview.PlantRecylerViewAdapter;


public class PlantFragment extends Fragment implements View.OnClickListener{

    private ImageButton addImageButton = null ;

    private RecyclerView plantRecyclerView = null ;

    private SwipeRefreshLayout swipeRefreshLayout = null ;

    private RelativeLayout plantSearchRe = null ;

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
                    swipeRefreshLayout.setRefreshing(false);

                    Toast.makeText(getActivity() , "刷新成功！" , Toast.LENGTH_SHORT).show();

                    break ;
                case PropertyConfig.SEARCH_PLANT_FAILED:
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity() , "刷新失败！" , Toast.LENGTH_SHORT).show();
                    break ;
            }

        }
    } ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant , container , false) ;

        addImageButton = (ImageButton)view.findViewById(R.id.add_imageButton) ;
        plantRecyclerView = (RecyclerView)view.findViewById(R.id.plant_recyler_view) ;
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh) ;
        plantSearchRe = (RelativeLayout)view.findViewById(R.id.fragment_plant_search_re) ;

        plantSearchRe.setOnClickListener(this);


        plantRecylerViewAdapter = new PlantRecylerViewAdapter(getActivity() , plantList) ;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) ;
        plantRecyclerView.setLayoutManager(linearLayoutManager);
        plantRecyclerView.setAdapter(plantRecylerViewAdapter);

        recyclerDataInit() ; //进行作物数据的初始化

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                    plantList.clear();
                    plantRecylerViewAdapter.notifyDataSetChanged();
                    recyclerDataInit();
            }
        });

        addImageButton.setOnClickListener(this);

        return view ;
    }

    private void recyclerDataInit() {

        BmobQuery<Plant> queryPlant = new BmobQuery<>() ;

        queryPlant.findObjects(getActivity(), new FindListener<Plant>() {
            @Override
            public void onSuccess(List<Plant> list) {
                Message msg = new Message() ;
                msg.arg1 = PropertyConfig.SEARCH_PLANT_SUCCESS ;
                msg.obj = list ;
                mHandler.sendMessage(msg) ;
            }

            @Override
            public void onError(int i, String s) {
                Message msg = new Message() ;
                msg.arg1 = PropertyConfig.SEARCH_PLANT_FAILED ;
                mHandler.sendMessage(msg) ;
            }
        });

    }

    @Override
    public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(getActivity() , R.anim.item_scale_anim_one) ;
        switch (v.getId()){
            case R.id.add_imageButton:
              //  addImageButton.startAnimation(animation);
                break ;

            case R.id.fragment_plant_search_re:
                plantSearchRe.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(getActivity() , SearchPlantActivity.class) ;
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break ;
            default:
                break ;
        }

    }

}
