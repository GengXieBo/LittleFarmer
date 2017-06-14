package util;

import android.util.Log;

import com.example.lenovo.littlefarmer.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.Plant;

/**
 * Created by lenovo on 2017/6/13.
 */

public class AreaUtil {

    static public int imgs[] = {R.mipmap.green_house_area , R.mipmap.field_area , R.mipmap.land_area ,
            R.mipmap.slope_area , R.mipmap.fruit_tree_area , R.mipmap.pool_area , R.mipmap.else_area} ;
    static public String[] txts = {"大棚区", "田地区" , "土地区" , "山坡区" ,"果树区" ,"水池区" ,"其它地区"} ;

    static public int[] type = {9 , 10 , 11 , 12 , 13 , 14 , 15} ;

    static public String[] areaState = {"空闲" , "育苗" , "已种植作物" , "作物成熟"} ;

    static public List<Plant> plantList = new ArrayList<>() ;

    //获取作物剩余成熟时间
    public static int getLeftMatureTime(String updateTime){

        int left = 0 ;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
        try {
            Date date1 = simpleDateFormat.parse(updateTime) ;
            Date date2 = new Date(System.currentTimeMillis()) ;
            long longTime = date2.getTime() - date1.getTime() ;

            Log.d("two time is " , updateTime + ",,," + date1.toString() + ",,," + date2.toString() + ", " + longTime) ;
            left = new Long(longTime / (1000 * 60) ).intValue() ;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return left ;

    }
}
