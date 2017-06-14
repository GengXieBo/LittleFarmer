package util;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import fragment.AreaFragment;
import fragment.MainFragment;
import fragment.PersonalFragment;
import fragment.PlantFragment;

/**
 * Created by lenovo on 2017/6/4.
 */

public class FragmentManagement {

    private static List<Fragment> fragmentList = null ;

    private FragmentManagement(){
        fragmentList = new ArrayList<Fragment>() ;
        fragmentList.add(new MainFragment()) ;
        fragmentList.add(new AreaFragment()) ;
        fragmentList.add(new PlantFragment()) ;
        fragmentList.add(new PersonalFragment()) ;
    }

    public static List<Fragment> getFragmentList(){
        if(fragmentList == null){
            new FragmentManagement();
        }
        return fragmentList ;
    }
}
