package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.littlefarmer.LoginActivity;
import com.example.lenovo.littlefarmer.R;

import util.FarmerUtils;
public class PersonalFragment extends Fragment implements View.OnClickListener{

    private ConstraintLayout personalTop = null ;
    private LinearLayout personalBottom = null ;

    private TextView adminText = null ;
    private TextView loginStateText = null ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        personalTop = (ConstraintLayout)view.findViewById(R.id.personal_top_constrain) ;
        personalBottom = (LinearLayout)view.findViewById(R.id.personal_bottom) ;
        adminText = (TextView)view.findViewById(R.id.admin_text) ;
        loginStateText = (TextView)view.findViewById(R.id.person_state_text) ;

        personalTop.setOnClickListener(this);
        personalBottom.setOnClickListener(this);


        return view ;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginInit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_top_constrain:

                break ;
            case R.id.personal_bottom:
                if(FarmerUtils.isLogin){

                    FarmerUtils.isLogin = false ;
                    FarmerUtils.farmer = null ;
                    loginInit();
                    Toast.makeText(getActivity() , "已退出登录！" , Toast.LENGTH_SHORT).show();

                }else{
                    Intent intent = new Intent(getActivity() , LoginActivity.class) ;
                    startActivity(intent);
                }

                break ;
        }
    }

    /*
     进行登录的初始化
     */
    public void loginInit(){
        if(FarmerUtils.isLogin){
            adminText.setText(FarmerUtils.farmer.getAdmin());
            loginStateText.setText(R.string.qiutLogin);
        }else{
            adminText.setText("not login yet...");
            loginStateText.setText(R.string.login);
        }
    }
}
