package myview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.littlefarmer.AreaSubsidiaryActivity;
import com.example.lenovo.littlefarmer.R;

import java.util.List;

import beans.Area;
import beans.AsisArea;

/**
 * Created by lenovo on 2017/6/11.
 */

public class AreaRecyclerViewAdapter extends RecyclerView.Adapter<MyAreaViewHolder> {

    private LayoutInflater inflater ;
    private List<AsisArea> areaList ;
    private Context context = null ;

    public AreaRecyclerViewAdapter(Context context , List<AsisArea> areaList){
        inflater = LayoutInflater.from(context) ;
        this.areaList = areaList ;
        this.context = context ;
    }

    @Override
    public MyAreaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.fragment_area_recycler_item , parent , false) ;

        MyAreaViewHolder myViewHolder = new MyAreaViewHolder(view , context , areaList) ;

        return myViewHolder ;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(MyAreaViewHolder holder, int position) {
        holder.areaImage.setImageResource(areaList.get(position).getAreaPic());
        holder.areaName.setText(areaList.get(position).getAreaName());
    }

    @Override
    public int getItemCount() {
        return areaList.size() ;
    }

}

class MyAreaViewHolder extends RecyclerView.ViewHolder{

     CardView areaCard ;
     ImageView areaImage ;
     TextView areaName ;

    public MyAreaViewHolder(View itemView , final Context context , final List<AsisArea> areaList) {
        super(itemView);

        areaImage = (ImageView)itemView.findViewById(R.id.area_image) ;
        areaName = (TextView)itemView.findViewById(R.id.area_text) ;
        areaCard = (CardView)itemView.findViewById(R.id.area_card) ;

        areaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , AreaSubsidiaryActivity.class) ;
                intent.putExtra("areaType" , areaList.get(getPosition()).getAreaType()) ;
                context.startActivity(intent);
            }
        });
    }
}
