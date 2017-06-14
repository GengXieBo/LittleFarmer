package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lenovo.littlefarmer.R;

import java.util.ArrayList;
import java.util.List;

import beans.AsisArea;
import myview.AreaRecyclerViewAdapter;
import util.AreaUtil;
import util.SpacesItemDecoration;

public class AreaFragment extends Fragment {

    private RecyclerView areaRecycler = null ;
    private AreaRecyclerViewAdapter areaRecyclerViewAdapter = null ;

    private List<AsisArea> areaList = new ArrayList<>() ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_area , container , false) ;

        areaRecycler = (RecyclerView)view.findViewById(R.id.area_recycler) ;


            areaRecyclerViewAdapter = new AreaRecyclerViewAdapter(getActivity() , areaList) ;

            //设置layoutManager
            areaRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

            areaRecycler.setAdapter(areaRecyclerViewAdapter);
            //设置item之间的间隔

            SpacesItemDecoration decoration=new SpacesItemDecoration(2);

            areaRecycler.addItemDecoration(decoration);

            //初始化数据
            initData();

        return view ;
    }

    private void initData() {

        areaList.clear();
        for (int i = 0; i < AreaUtil.imgs.length; i++) {
            areaList.add(new AsisArea(AreaUtil.imgs[i], AreaUtil.txts[i] , AreaUtil.type[i]));
        }

        areaRecyclerViewAdapter.notifyDataSetChanged();
    }
}
