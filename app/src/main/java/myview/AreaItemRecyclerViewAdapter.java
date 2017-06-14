package myview;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.littlefarmer.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import beans.Area;
import beans.Plant;
import beans.PlantHelp;
import cn.bmob.v3.listener.UpdateListener;
import config.PropertyConfig;
import util.AreaUtil;

/**
 * Created by lenovo on 2017/6/13.
 */

public class AreaItemRecyclerViewAdapter extends RecyclerView.Adapter<AreaItemHolder> {

    private List<Area> areaList = null ;
    private static Context context = null ;
    private LayoutInflater inflater ;

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1){
                case PropertyConfig.GROW_SUCCESS:
                    notifyDataSetChanged();
                    break ;
                case PropertyConfig.HARVEST_SUCCESS:
                    notifyDataSetChanged();
                    Toast.makeText(context , "收获成功..." , Toast.LENGTH_SHORT).show();
                    break ;
                case PropertyConfig.REMOVE_SUCESS:
                    notifyDataSetChanged();
                    Toast.makeText(context , "铲除成功..." ,Toast.LENGTH_SHORT).show();
                    break ;
                case PropertyConfig.AREA_OPERATE_FAILED:
                    Toast.makeText(context , "操作失败..." , Toast.LENGTH_SHORT).show();
                    break ;
            }

        }
    } ;

    public AreaItemRecyclerViewAdapter(List<Area> areaList , Context context){
        this.areaList = areaList ;
        this.context = context ;
        inflater = LayoutInflater.from(context) ;
    }

    @Override
    public AreaItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.area_list_item , parent , false) ;
        AreaItemHolder areaItemHolder = new AreaItemHolder(view , context, areaList ,mHandler) ;

        return areaItemHolder ;
    }

    @Override
    public void onBindViewHolder(AreaItemHolder holder, int position) {

        holder.areaName.setText(areaList.get(position).getAreaName());

        if(areaList.get(position).getWeatherGrow()){
            Picasso.with(context).load(areaList.get(position).getAreaPlant().getPlantPic())
                    .into(holder.areaImage);
            holder.areaPlant.setText(areaList.get(position).getAreaPlant().getPlantName());

            Log.d("Two time ------ " , String.valueOf(AreaUtil.getLeftMatureTime(areaList.get(position).getUpdatedAt())) +
                    areaList.get(position).getAreaPlant().getPlantMatureTime()) ;
            int leftTime = 0 ;
            leftTime = areaList.get(position).getAreaPlant().getPlantMatureTime() -
                    AreaUtil.getLeftMatureTime(areaList.get(position).getUpdatedAt()) ;

            if(leftTime <= 0){
                areaList.get(position).setAreaState(3);
                holder.mature.setText("剩余:" + String.valueOf(0));
            }else{
                holder.mature.setText("剩余:" + String.valueOf(leftTime));
            }

        }else{
            holder.mature.setText("");
            holder.areaImage.setImageResource(AreaUtil.imgs[areaList.get(position).getAreaType() - 9]);
            holder.areaPlant.setText("暂无作物");
        }

        holder.areaState.setText(AreaUtil.areaState[areaList.get(position).getAreaState()]);


    }

    @Override
    public int getItemCount() {
        return areaList.size() ;
    }
}
class AreaItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView areaImage ;
    TextView areaName ;
    TextView areaState ;
    TextView areaPlant ;

    ImageView moreImage ;
    RelativeLayout menuRelative = null ;

    private ImageView growImage ;
    private ImageView harvestImage ;
    private ImageView removeImage ;

    private List<Area> areaList ;
    private Context context ;

    TextView mature ;

    private Handler mHandler ;


    public AreaItemHolder(View itemView , final Context context , List<Area> areaList , Handler mHandler) {
        super(itemView);

        this.areaList = areaList ;
        this.context = context ;
        this.mHandler = mHandler ;

        areaImage = (ImageView)itemView.findViewById(R.id.area_image) ;
        areaName = (TextView)itemView.findViewById(R.id.area_name) ;
        areaState = (TextView)itemView.findViewById(R.id.area_state) ;
        areaPlant = (TextView)itemView.findViewById(R.id.area_plant) ;
        moreImage = (ImageView)itemView.findViewById(R.id.more_image) ;
        menuRelative = (RelativeLayout)itemView.findViewById(R.id.menu_relative) ;
        mature = (TextView)itemView.findViewById(R.id.mature_text) ;

        growImage = (ImageView)itemView.findViewById(R.id.grow_image) ;
        harvestImage = (ImageView)itemView.findViewById(R.id.harvest_image) ;
        removeImage = (ImageView)itemView.findViewById(R.id.remove_image) ;

        moreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuRelative.getVisibility() == View.VISIBLE){
                    menuRelative.setVisibility(View.GONE);
                }else{
                    menuRelative.setVisibility(View.VISIBLE);
                }
            }
        });

        growImage.setOnClickListener(this);
        harvestImage.setOnClickListener(this);
        removeImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.grow_image:
                if(areaList.get(getPosition()).getWeatherGrow()){
                    Toast.makeText(context , "该地块已经种植作物..." , Toast.LENGTH_SHORT).show();
                }else{
                    choosePlant();
                }
                break ;
            case R.id.harvest_image:

                if(areaList.get(getPosition()).getAreaState() == 3){
                    //进行收获操作
                    areaList.get(getPosition()).setWeatherGrow(false);
                    areaList.get(getPosition()).setAreaPlant(null);
                    areaList.get(getPosition()).setAreaState(0);
                    areaList.get(getPosition()).update(context, areaList.get(getPosition()).getObjectId(),
                            new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Message message = new Message() ;
                                    message.arg1 = PropertyConfig.HARVEST_SUCCESS ;
                                    mHandler.sendMessage(message) ;
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Message message = new Message() ;
                                    message.arg1 = PropertyConfig.AREA_OPERATE_FAILED ;
                                    mHandler.sendMessage(message) ;
                                }
                            });
                }else{
                    Toast.makeText(context , "暂时无法收获..." , Toast.LENGTH_SHORT).show();
                }

                break ;
            case R.id.remove_image:

                if(areaList.get(getPosition()).getWeatherGrow()){
                    //进行铲除操作
                    areaList.get(getPosition()).setWeatherGrow(false);
                    areaList.get(getPosition()).setAreaPlant(null);
                    areaList.get(getPosition()).setAreaState(0);
                    areaList.get(getPosition()).update(context, areaList.get(getPosition()).getObjectId(),
                            new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Message message = new Message() ;
                                    message.arg1 = PropertyConfig.REMOVE_SUCESS ;
                                    mHandler.sendMessage(message) ;
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Message message = new Message() ;
                                    message.arg1 = PropertyConfig.AREA_OPERATE_FAILED ;
                                    mHandler.sendMessage(message) ;
                                }
                            });
                }else{
                    Toast.makeText(context , "暂无植物可以进行铲除..." , Toast.LENGTH_SHORT).show();
                }
                break ;
            default:
                break ;

        }

    }

    public void choosePlant(){

        final String[] plantList = new String[AreaUtil.plantList.size()] ;

        for(int i = 0 ; i < AreaUtil.plantList.size() ; i++){
            plantList[i] = AreaUtil.plantList.get(i).getPlantName() ;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context) ;

        builder.setTitle("作物选择") ;
        //加载数据信息
        builder.setSingleChoiceItems(plantList , 1 , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                areaList.get(getPosition()).setWeatherGrow(true);
                PlantHelp plantHelp = new PlantHelp() ;
                copyPlant(AreaUtil.plantList.get(which) , plantHelp) ;
                areaList.get(getPosition()).setAreaPlant(plantHelp);

                if(plantHelp.getPlantName().equals("苗种")){
                    areaList.get(getPosition()).setAreaState(1);
                }else{
                    areaList.get(getPosition()).setAreaState(2);
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss") ;
                try {
                    Date date = sdf.parse(areaList.get(getPosition()).getUpdatedAt()) ;
                    Log.d("update time -- -:1 " , date.toString()) ;
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Log.d("update time -- -: " , areaList.get(getPosition()).getUpdatedAt()) ;
                Log.d("on the click button : " , areaList.get(getPosition()).getObjectId() + AreaUtil.plantList.get(which).getPlantName()) ;
                areaList.get(getPosition()).update(context, areaList.get(getPosition()).
                        getObjectId() , new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Message message = new Message() ;
                        message.arg1 = PropertyConfig.GROW_SUCCESS ;
                        mHandler.sendMessage(message) ;
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        areaList.get(getPosition()).setWeatherGrow(false);
                        areaList.get(getPosition()).setAreaPlant(null);
                        areaList.get(getPosition()).setAreaState(0);
                        Message message = new Message() ;
                        message.arg1 = PropertyConfig.AREA_OPERATE_FAILED ;
                        mHandler.sendMessage(message) ;
                    }
                });

                dialog.cancel();

            }
        }).setPositiveButton("取消" , null).create().show() ;

    }

    private void copyPlant(Plant plant, PlantHelp plantHelp) {

        plantHelp.setPlantName(plant.getPlantName());
        plantHelp.setPlantDescripe(plant.getPlantDescripe());
        plantHelp.setPlantSeason(plant.getPlantSeason());
        plantHelp.setPlantType(plant.getPlantType());
        plantHelp.setPlantPic(plant.getPlantPic().getFileUrl().substring(20));
        plantHelp.setPlantMatureTime(plant.getPlantMatureTime());

    }
}
