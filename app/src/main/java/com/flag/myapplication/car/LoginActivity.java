package com.flag.myapplication.car;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flag.myapplication.car.utils.StrUtil;
import com.flag.myapplication.car.utils.Xutils;
import com.flag.myapplication.car.bean.User;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.tv_regi)
    TextView tv_regi;
    @ViewInject(R.id.tv_login)
    TextView tv_login;
    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.et_pwd)
    EditText et_pwd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        x.view().inject(LoginActivity.this);
        super.onCreate(savedInstanceState);


        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        String usernamest = pref.getString("username","");
        String passwordst = pref.getString("password","");
        et_phone.setText(usernamest);
        et_pwd.setText(passwordst);

    }

    @Override
    protected void initData() {
        try {
            User user=  db.selector(User.class).where("zhuangt","=",1).findFirst();
            if(user!=null)
            {
                Intent intent=new Intent(myContext, MainActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
                finish();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }



    }

    @Override
    protected void initListener() {




        tv_regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//

                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPhone = et_phone.getText().toString().trim();
                String tempPwd = et_pwd.getText().toString().trim();
                if (StrUtil.isEmpty(tempPhone) || StrUtil.isEmpty(tempPwd)) {
                    Toast.makeText(myContext,"账号号或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }


                loginhttpbendi( tempPhone , tempPwd);


            }
        });
    }



    public void loginhttpbendi(String username ,String password)
    {


            try {
                User li33st= Xutils.initDbConfiginit().selector(User.class).where("username","=" ,username)
                        .and("password","=",password).findFirst();
                if(li33st!=null)
                {


                    SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putString("username",username);
                    editor.putString("password",password);
                    editor.commit();


                    li33st.setZhuangt(1);
                    Xutils.initDbConfiginit().update(li33st);
                    Intent intent=new Intent(myContext, MainActivity.class);
                    intent.putExtra("user",li33st);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(myContext,"登陆失败", Toast.LENGTH_SHORT).show();
                }
            } catch (DbException e) {
                e.printStackTrace();
            }


    }






    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
//    Map<String,Object> map = new HashMap<>();
//map.put("pageNumber",page);
//map.put("typeid",typeid);
////如果请求不需要参数,传null
//// GetDataTask.post("app/types", null, new GetDataTask.GetDataCallback(){}
//GetDataTask.post("app/types", map, new GetDataTask.GetDataCallback() {
//        @Override
//        public void success(String response) {
//            Gson gson = new Gson(); //后台返回来的json格式，其他格式自己处理
//            Result result = gson.fromJson(response, Result.class);
//        }
//        @Override
//        public void failed(String... args) {
//        }
//    });

}
