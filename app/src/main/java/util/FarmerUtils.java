package util;

import android.content.Context;
import android.widget.Toast;

import beans.Farmer;

/**
 * Created by lenovo on 2017/6/6.
 */

/*
用于检查用户输入的账号与密码是否合法
 */
public class FarmerUtils {

    //设置登录状态
    public static boolean isLogin = false ;

    //登录信息保存
    public static Farmer farmer = null ;

    public static boolean isLegal(String admin , String pwd , String confirmPwd , Context context){

        if(admin.equals("") || pwd.equals("") || confirmPwd.equals("")){
            Toast.makeText(context , "账号或密码不能为空！" , Toast.LENGTH_SHORT).show();
            return false ;
        }

        if(!pwd.equals(confirmPwd)){
            Toast.makeText(context , "两次输入密码不相同！" , Toast.LENGTH_SHORT).show();
            return false ;
        }

        return true ;
    }

}
