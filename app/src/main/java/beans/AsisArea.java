package beans;

/**
 * Created by lenovo on 2017/6/13.
 */

public class AsisArea {

    private int areaPic ;
    private String areaName ;
    private int areaType ;

    public AsisArea(int areaPic , String areaName , int areaType){
        this.areaPic = areaPic ;
        this.areaName = areaName ;
        this.areaType = areaType ;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaPic() {
        return areaPic;
    }

    public void setAreaPic(int areaPic) {
        this.areaPic = areaPic;
    }

    public int getAreaType() {
        return areaType;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }
}
