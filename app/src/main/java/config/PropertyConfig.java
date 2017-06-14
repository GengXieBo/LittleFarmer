package config;

/**
 * Created by lenovo on 2017/6/6.
 */

public class PropertyConfig {

    //注册
    public static final int SIGN_UP = 0 ;

    //注册成功
    public static final int SIGN_UP_SUCCESS = 1 ;

    //注册失败
    public static final int SIGN_UP_FAILED = 2 ;

    //账号不存在
    public static final int ADMIN_NOT_EXIT = 3 ;

    //密码错误
    public static final int PWD_ERROR = 4 ;

    //登录成功
    public static final int LOGIN_SUCCESS = 5 ;

    //登录失败
    public static final int LOGIN_FAILED = 6 ;

    //查询植物信息成功
    public static final int SEARCH_PLANT_SUCCESS = 7 ;

    //查询植物信息失败
    public static final int SEARCH_PLANT_FAILED = 8 ;

    //地区类型-棚区
    public static final int GREEN_HOUSE_AREA = 9 ;

    //地区类型—田区
    public static final int FIELD_AREA = 10 ;

    //地区类型-地区
    public static final int LAND_AREA = 11 ;

    //地区类型-坡区
    public static final int SLOPE_AREA = 12 ;

    //地区类型-果树区
    public static final int FRUIT_TREE_AREA = 13 ;

    //地区类型-水池区
    public static final int POOL_AREA = 14 ;

    //其它地区
    public static final int ELSE_AREA = 15 ;

    //作物种植成功
    public static final int GROW_SUCCESS = 16 ;

    //作物收获成功
    public static final int HARVEST_SUCCESS = 17 ;

    //作物铲除成功
    public static final int REMOVE_SUCESS = 18 ;

    //土地操作失败
    public static final int AREA_OPERATE_FAILED = 19 ;
}
