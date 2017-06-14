package myview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.littlefarmer.PlantDetailActivity;
import com.example.lenovo.littlefarmer.R;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;

import beans.Plant;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by lenovo on 2017/6/9.
 */

public class PlantRecylerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private LayoutInflater inflater ;
    private List<Plant> plantList ;
    private Context context = null ;
    private WindowManager wm = null ;

    public PlantRecylerViewAdapter(Context context , List<Plant> plantList){
        inflater = LayoutInflater.from(context) ;
        this.plantList = plantList ;
        this.context = context ;
        wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE) ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.fragment_plant_card , parent , false) ;

        MyViewHolder myViewHolder = new MyViewHolder(view , context ,plantList) ;

        return myViewHolder ;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.plantName.setText(plantList.get(position).getPlantName());


        Picasso.with(context).load(plantList.get(position).getPlantPic().getFileUrl().substring(20))
                .resize(wm.getDefaultDisplay().getWidth() , 400).into(holder.plantPic);

        holder.plantDescripe.setText(plantList.get(position).getPlantDescripe());
        holder.plantSeason.setText(plantList.get(position).getPlantSeason());
        holder.plantType.setText(plantList.get(position).getPlantType());
    }

    @Override
    public int getItemCount() {
        return plantList.size() ;
    }

    public void addItem(Plant plant){
        plantList.add(plant) ;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

     ImageView plantPic = null ;
     TextView plantName = null ;
     TextView plantDescripe = null ;
     TextView plantType = null ;
     TextView plantSeason = null ;

    private CardView cardView = null ;

    public MyViewHolder(View itemView , final Context context , final List<Plant> plantList) {
        super(itemView);
        plantName = (TextView)itemView.findViewById(R.id.plant_name_text) ;
        plantPic = (ImageView)itemView.findViewById(R.id.plant_pic_image) ;
        plantDescripe = (TextView)itemView.findViewById(R.id.plant_descripe_text) ;
        plantType = (TextView)itemView.findViewById(R.id.plant_type_text) ;
        plantSeason = (TextView)itemView.findViewById(R.id.plant_season_text) ;

        cardView = (CardView) itemView.findViewById(R.id.plant_card) ;

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , PlantDetailActivity.class) ;

                intent.putExtra("plantName" , plantList.get(getPosition()).getPlantName()) ;
                intent.putExtra("plantType" , plantList.get(getPosition()).getPlantType()) ;
                intent.putExtra("plantSeason" , plantList.get(getPosition()).getPlantSeason()) ;
                intent.putExtra("plantDescripe" , plantList.get(getPosition()).getPlantDescripe()) ;
                intent.putExtra("plantPic" , plantList.get(getPosition()).getPlantPic().getFileUrl()) ;
                intent.putExtra("plantMatureTime" , plantList.get(getPosition()).getPlantMatureTime()) ;

                context.startActivity(intent);
            }
        });

    }
}
