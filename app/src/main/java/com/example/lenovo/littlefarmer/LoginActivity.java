package com.example.lenovo.littlefarmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import config.PropertyConfig;
import beans.Farmer;
import util.FarmerUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginButton = null ;
    private TextView signUpText = null ;
    private TextView forgetPwdText = null ;

    private EditText adminEdit = null ;
    private EditText pwdEdit = null ;

    private ImageView backImage = null ;

    private ProgressDialog loginProgressDialog = null ;

    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1){
                case PropertyConfig.LOGIN_SUCCESS:
                    loginProgressDialog.dismiss();
                    FarmerUtils.isLogin = true ;
                    FarmerUtils.farmer = (Farmer)msg.obj ;
                    LoginActivity.this.finish();
                    break ;
                case PropertyConfig.LOGIN_FAILED:
                    loginProgressDialog.dismiss();
                    break ;
            }
        }
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        loginButton = (Button)findViewById(R.id.login_button) ;
        signUpText = (TextView)findViewById(R.id.sign_up_text) ;
        forgetPwdText = (TextView)findViewById(R.id.forget_pwd_text) ;
        adminEdit = (EditText)findViewById(R.id.admin_edit) ;
        pwdEdit = (EditText)findViewById(R.id.pwd_edit) ;
        backImage = (ImageView)findViewById(R.id.back_image) ;

        loginButton.setOnClickListener(this);
        signUpText.setOnClickListener(this);
        forgetPwdText.setOnClickListener(this);
        backImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                Farmer farmer = new Farmer() ;
                farmer.setAdmin(adminEdit.getText().toString());
                farmer.setPwd(pwdEdit.getText().toString());

                if(farmer.getAdmin().equals("") || farmer.getPwd().equals("")){
                    Toast.makeText(LoginActivity.this , "账号或密码不能为空！" , Toast.LENGTH_SHORT).show();
                }else{
                    loginProgressDialog = ProgressDialog.show(LoginActivity.this , "请稍后..." ,
                                                    "正在登录..." , true) ;

                    //进行登录检查
                    checkLogin(farmer);
                }
                break ;
            case R.id.sign_up_text:
                //进行注册页面的跳转
                Intent intent = new Intent(LoginActivity.this , SignUpActivity.class) ;
                startActivity(intent);
                break ;
            case R.id.forget_pwd_text:

                break ;

            case R.id.back_image:
                finish();
                break ;
            default:
                break ;
        }
    }

    public void checkLogin(final Farmer farmer){

        BmobQuery<Farmer> farmerBmobQuery = new BmobQuery<Farmer>() ;

        farmerBmobQuery.addWhereEqualTo("admin" , farmer.getAdmin()) ;

        farmerBmobQuery.findObjects(LoginActivity.this, new FindListener<Farmer>() {
            @Override
            public void onSuccess(List<Farmer> list) {

                boolean result = true ;
                if(list.size() <= 0){
                    result = false ;
                    Toast.makeText(LoginActivity.this , "账号不存在！" , Toast.LENGTH_SHORT).show();
                }else{
                    if(!list.get(0).getPwd().equals(farmer.getPwd())){
                        result = false ;
                        Toast.makeText(LoginActivity.this , "密码错误！" , Toast.LENGTH_SHORT).show();
                    }
                }

                Message message = new Message() ;
                if(result){
                    message.arg1 = PropertyConfig.LOGIN_SUCCESS ;
                    message.obj = farmer ;
                }else{
                    message.arg1 = PropertyConfig.LOGIN_FAILED ;
                }
                mHandler.sendMessage(message) ;

            }

            @Override
            public void onError(int i, String s) {
                Message message = new Message() ;
                message.arg1 = PropertyConfig.LOGIN_FAILED ;
                mHandler.sendMessage(message) ;
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(loginProgressDialog != null) {
                Toast.makeText(LoginActivity.this, "登录取消！", Toast.LENGTH_SHORT).show();
            }

        }

        return super.onKeyDown(keyCode, event);
    }
}
