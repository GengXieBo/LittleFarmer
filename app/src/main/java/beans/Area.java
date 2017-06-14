package beans;

import android.graphics.Point;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by lenovo on 2017/6/11.
 */

public class Area extends BmobObject {

    private String areaName ;

    private Integer areaType ;

    //是否种植作物
    private Boolean weatherGrow ;

    //土地状态
    private Integer areaState ;

    //种植的作物
    private PlantHelp areaPlant ;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }



    public Integer getAreaType() {
        return areaType;
    }

    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
    }

    public Boolean getWeatherGrow() {
        return weatherGrow;
    }

    public void setWeatherGrow(Boolean weatherGrow) {
        this.weatherGrow = weatherGrow;
    }

    public Integer getAreaState() {
        return areaState;
    }

    public void setAreaState(Integer areaState) {
        this.areaState = areaState;
    }

    public PlantHelp getAreaPlant() {
        return areaPlant;
    }

    public void setAreaPlant(PlantHelp areaPlant) {
        this.areaPlant = areaPlant;
    }

}
