package beans;

import java.io.File;
import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by lenovo on 2017/6/9.
 */

public class Plant extends BmobObject implements Serializable{

    private String plantName = null ;
    private BmobFile plantPic = null ;
    private String plantDescripe = null ;
    private String plantSeason = null ;
    private String plantType = null ;
    private Integer plantMatureTime ;

    public Plant(){

    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public BmobFile getPlantPic() {
        return plantPic;
    }

    public void setPlantPic(BmobFile plantPic) {
        this.plantPic = plantPic;
    }

    public String getPlantDescripe() {
        return plantDescripe;
    }

    public void setPlantDescripe(String plantDescripe) {
        this.plantDescripe = plantDescripe;
    }

    public String getPlantSeason() {
        return plantSeason;
    }

    public void setPlantSeason(String plantSeason) {
        this.plantSeason = plantSeason;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public Integer getPlantMatureTime() {
        return plantMatureTime;
    }

    public void setPlantMatureTime(Integer plantMatureTime) {
        this.plantMatureTime = plantMatureTime;
    }
}
