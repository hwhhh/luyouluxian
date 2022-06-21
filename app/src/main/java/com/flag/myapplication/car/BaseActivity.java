package com.flag.myapplication.car;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.flag.myapplication.car.utils.Xutils;
import com.flag.myapplication.car.bean.User;


import org.xutils.DbManager;
import org.xutils.ex.DbException;


/**
 * @Author: Paper
 * time :2019/9/4  10:26
 * desc:
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Context myContext;
    public  DbManager db;
    User user;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db= Xutils.initDbConfiginit();
        myContext = this;
        try {
             user= Xutils.initDbConfiginit().selector(User.class).where("zhuangt","=","1").findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

        ImageView imageView=findViewById(R.id.backimage);
        if(imageView!=null)
        {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        setContent();
        initData();
        initListener();
    }


    protected void setContent() {



    }

    protected abstract void initData();

    protected abstract void initListener();

    /**
     * 切换：Fragment
     * 方式:hide/show
     * 注：Fragment由hide到show,不走 onCreateView 方法，所有的 view 都会保存在内存
     */

}
