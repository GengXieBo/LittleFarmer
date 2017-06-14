package com.example.lenovo.littlefarmer;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import config.PropertyConfig;
import beans.Farmer;
import util.FarmerUtils;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button signUpButton = null ;
    private EditText adminEdit = null ;
    private EditText pwdEdit = null ;
    private EditText confirmPwdEdit = null ;
    private ImageView backImage = null ;

    private ProgressDialog signUpProgressDialog = null ;

    private final Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg){
            switch (msg.arg1){
                case PropertyConfig.SIGN_UP:
                    Farmer farmer = (Farmer) msg.obj ;
                    signUpFarmer(farmer) ;
                    break ;

                case PropertyConfig.SIGN_UP_SUCCESS:
                    signUpProgressDialog.dismiss();
                    SignUpActivity.this.finish();
                    break ;
                case PropertyConfig.SIGN_UP_FAILED:
                    signUpProgressDialog.dismiss();
                    break ;
            }
        }
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        Bmob.initialize(this , "f6f446756856568d0a130cb1b3da5e07");

        signUpButton = (Button)findViewById(R.id.sign_up_button) ;
        adminEdit = (EditText)findViewById(R.id.admin_edit) ;
        pwdEdit = (EditText)findViewById(R.id.pwd_edit) ;
        confirmPwdEdit = (EditText)findViewById(R.id.confirm_pwd_edit) ;
        backImage = (ImageView)findViewById(R.id.back_image) ;

        signUpButton.setOnClickListener(this);
        backImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.sign_up_button:
                Farmer farmer = new Farmer() ;
                if(FarmerUtils.isLegal(adminEdit.getText().toString() , pwdEdit.getText().toString()
                        , confirmPwdEdit.getText().toString()  , SignUpActivity.this)){
                        //设置账号与密码
                        farmer.setAdmin(adminEdit.getText().toString());
                        farmer.setPwd(pwdEdit.getText().toString());

                        signUpProgressDialog = ProgressDialog.show(SignUpActivity.this , "请稍等..."
                                                        , "正在进行注册..." , true) ;
                        //检查账号是否已存在
                        adminIsDuplicate(farmer);

                }
                break ;

            case R.id.back_image:
                finish();
                break ;
            default:
                break ;
        }

    }

    /*
     检查账号是否重复
     */
    public void adminIsDuplicate(final Farmer farmer){

        BmobQuery<Farmer> farmerBmobQuery = new BmobQuery<Farmer>() ;

        farmerBmobQuery.findObjects(SignUpActivity.this, new FindListener<Farmer>() {

            @Override
            public void onSuccess(List<Farmer> list) {
                boolean isDuplicated = false ;
                for(Farmer f : list){
                    if(farmer.getAdmin().equals(f.getAdmin())){
                        isDuplicated = true ;
                        Message message = new Message() ;
                        message.arg1 = PropertyConfig.SIGN_UP_FAILED ;
                        mHandler.sendMessage(message) ;
                        Toast.makeText(SignUpActivity.this , "账号已存在！" , Toast.LENGTH_SHORT).show() ;
                        break ;
                    }
                }

                if(!isDuplicated){
                    Message message = new Message() ;
                    message.arg1 = PropertyConfig.SIGN_UP ;
                    message.obj = farmer ;
                    mHandler.sendMessage(message) ;
                }

            }

            @Override
            public void onError(int i, String s) {
                Message message = new Message() ;
                message.arg1 = PropertyConfig.SIGN_UP_FAILED ;
                mHandler.sendMessage(message) ;
                Toast.makeText(SignUpActivity.this , "注册失败！" , Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void signUpFarmer(Farmer farmer){
        //进行数据上传
        farmer.save(SignUpActivity.this , new SaveListener() {
            @Override
            public void onSuccess() {
                Message message = new Message() ;
                message.arg1 = PropertyConfig.SIGN_UP_SUCCESS ;
                mHandler.sendMessage(message) ;
                Toast.makeText(SignUpActivity.this , "注册成功！" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Message message = new Message() ;
                message.arg1 = PropertyConfig.SIGN_UP_FAILED ;
                mHandler.sendMessage(message) ;
                Toast.makeText(SignUpActivity.this , "注册失败！" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(signUpProgressDialog != null){
                signUpProgressDialog.dismiss();
                Toast.makeText(SignUpActivity.this , "注册已被取消！" , Toast.LENGTH_SHORT).show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
