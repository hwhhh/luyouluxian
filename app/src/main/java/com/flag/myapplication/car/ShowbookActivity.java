package com.flag.myapplication.car;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.flag.myapplication.car.utils.Xutils;
import com.flag.myapplication.car.bean.Jingdian;


import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;


@ContentView(R.layout.activity_showbook)
public class ShowbookActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.addimageg)
    ImageView addimageg;

    @ViewInject(R.id.backimage)
    ImageView backimage;

    @ViewInject(R.id.title)
    TextView title;

    @ViewInject(R.id.neirong)
    TextView neirong;

    @ViewInject(R.id.fenshu)
    TextView fenshu;

    @ViewInject(R.id.IBSMtv)
    TextView IBSMtv;

    @ViewInject(R.id.typesp)
    Spinner typesp;


    @ViewInject(R.id.jiagettv)
    TextView jiagettv;
    @ViewInject(R.id.zhuoztv)
    TextView zhuoztv;

    File imageviewfile;
    @ViewInject(R.id.text)
    TextView text;




    String imagepath;
    Jingdian danzi;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        x.view().inject(ShowbookActivity.this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {

        backimage.setVisibility(View.VISIBLE);
        addimageg.setOnClickListener(this);
        danzi= (Jingdian) getIntent().getSerializableExtra("data");
        text.setText("景点详情");




        if(danzi!=null)
        {
            title.setText(danzi.getBookname());
            neirong.setText(danzi.getNeirong());
            imagepath=danzi.getImgurl();
            x.image().bind(addimageg, new File(danzi.getImgurl()).toURI().toString());
            fenshu.setText(danzi.getFenshu()+"");
            IBSMtv.setText(danzi.getLuxian()+"");
            jiagettv.setText(danzi.getJiege()+"");
            zhuoztv.setText(danzi.getZhuoze()+"");




            String [ ] typearray = getResources().getStringArray(R.array.typearray);

            for (int i=0;i<typearray.length;i++) {
                if (danzi.getTypename().equals(typearray[i])) {
                    typesp.setSelection(i);
                }
            }






        }







    }

    @Override
    protected void initListener() {


        backimage.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {


      if (v.getId()==R.id.backimage)
        {
            finish();
        }



    }




}
