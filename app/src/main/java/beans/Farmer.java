package beans;

import cn.bmob.v3.BmobObject;

/**
 * Created by lenovo on 2017/6/5.
 */

public class Farmer extends BmobObject{

    private String admin = null ;
    private String pwd = null ;

    public Farmer(){

    }

    public void setAdmin(String admin){
        this.admin = admin ;
    }

    public  String getAdmin(){
        return admin ;
    }

    public void setPwd(String pwd){
        this.pwd = pwd ;
    }

    public String getPwd(){
        return pwd ;
    }
}
