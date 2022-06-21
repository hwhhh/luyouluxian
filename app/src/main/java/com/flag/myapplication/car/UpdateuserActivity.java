package com.flag.myapplication.car;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flag.myapplication.car.bean.User;
import com.flag.myapplication.car.utils.StrUtil;


import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_update)
public class UpdateuserActivity extends BaseActivity {

    @ViewInject(R.id.tv_login)
    TextView tv_login;
    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.et_pwd)
    EditText et_pwd;
    @ViewInject(R.id.et_pwd2)
    EditText et_pwd2;



    @ViewInject(R.id.text)
    TextView text;

    @ViewInject(R.id.et_sr)
    EditText et_sr;


    @ViewInject(R.id.et_dh)
    EditText et_dh;

    @ViewInject(R.id.nan)
    RadioButton nan;

    @ViewInject(R.id.nv)
    RadioButton nv;
    User data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        x.view().inject(UpdateuserActivity.this);
        super.onCreate(savedInstanceState);
        data= (User) getIntent().getSerializableExtra("data");
        text.setText("修改");
        if(data!=null)
        {
            et_phone.setText(data.getUsername());
            et_pwd.setText(data.getPassword());
            et_pwd2.setText(data.getPassword());

            et_sr.setText(data.getShengri());
            et_dh.setText(data.getPhone());
            if(data.getSex().equals("男"))
            {
                nan.setChecked(true);
            }
            else
            {
                nv.setChecked(true);
            }

        }

    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPhone = et_phone.getText().toString().trim();
                String tempPwd = et_pwd.getText().toString().trim();

                if (StrUtil.isEmpty(et_phone.getText().toString())) {
                    Toast.makeText(myContext,"账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (StrUtil.isEmpty(et_pwd.getText().toString())) {
                    Toast.makeText(myContext,"请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (StrUtil.isEmpty(et_pwd2.getText().toString())) {
                    Toast.makeText(myContext,"请再次输入密码", Toast.LENGTH_SHORT).show();

                    return;
                }


                if (!et_pwd.getText().toString().equals(et_pwd2.getText().toString())) {
                    Toast.makeText(myContext,"两次密码不一致", Toast.LENGTH_SHORT).show();

                    return;
                }

                data.setUsername(et_phone.getText().toString());
                data.setPassword(et_pwd .getText().toString());

                data.setShengri(et_sr .getText().toString());

                data.setPhone(et_dh .getText().toString());

                if(nv.isChecked())
                {
                    data.setSex("女");
                }
                else
                {
                    data.setSex("男");

                }

                loginhttpbend(data);


            }
        });
    }

    public void loginhttpbend(User user)
    {
        try {
            db.update(user);
        } catch (DbException e) {
            e.printStackTrace();
        }


        finish();

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
