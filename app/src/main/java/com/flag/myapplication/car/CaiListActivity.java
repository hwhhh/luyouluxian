package com.flag.myapplication.car;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.flag.myapplication.car.bean.Jingdian;
import com.flag.myapplication.car.bean.User;
import com.flag.myapplication.car.utils.Xutils;

import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class CaiListActivity extends Activity {
    private static User userbean;
    public Context myContext;
     TextView text,nodate;
    ImageView add;
    ListView listView;
    List<Jingdian> danzis=new ArrayList<>();

    EditText edsele;
    Button selebt;
    LinearLayout layoutSearch;
    String titlelist;
    private String status;
    public CaiListActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        text=findViewById(R.id.text);
        add=findViewById(R.id.add);
        nodate=findViewById(R.id.nodate);
        edsele=findViewById(R.id.edsele);
        selebt=findViewById(R.id.selebt);
        listView=findViewById(R.id.list);
        layoutSearch=findViewById(R.id.layout_search);
        status=getIntent().getStringExtra("status");
        String title=getIntent().getStringExtra("title");
        layoutSearch.setVisibility(View.GONE);
        text.setText(title);
        //item点击事件
        //搜索按钮
        selebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleall();
            }
        });


    }



    @Override
    public void onResume() {
        super.onResume();
        initdata ();


    }

    public void   initdata ()
    {





        seleall();
    }

    public void seleall()
    {
        InputMethodManager imm = (InputMethodManager)this.getSystemService(INPUT_METHOD_SERVICE);//这两行是收起软键盘
        imm.hideSoftInputFromWindow(edsele.getWindowToken(), 0);
        danzis.clear();
        String string=edsele.getText()+"";
        string=string+"";


        try {
            if(status.equals("0")){
                danzis.addAll(Xutils.initDbConfiginit().selector(Jingdian.class).where("like","=","1").findAll());

            }else{
                danzis.addAll(Xutils.initDbConfiginit().selector(Jingdian.class).where("collect","=","1").findAll());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(danzis.size()==0)
            {
                nodate.setVisibility(View.VISIBLE);
            }
        else
        {
            nodate.setVisibility(View.GONE);

        }
            listView.setAdapter(mydapter);
    }

    public void danzidele(int id, final int index)
    {

        try {
            Xutils.initDbConfiginit().delete(danzis.get(index).getId());
        } catch (DbException e) {
            e.printStackTrace();
        }
        danzis.remove(index);
        mydapter.notifyDataSetChanged();
    }

    BaseAdapter mydapter=new BaseAdapter() {
        @Override
        public int getCount() {
            return danzis.size();
        }

        @Override
        public Object getItem(int position) {
            return danzis.get(position);
        }

        @Override
        public long getItemId(int position) {
            return danzis.size();
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {

            view= LayoutInflater.from(MyApplication.getInstance().getApplicationContext()).inflate(R.layout.item2,parent,false);
            TextView zuozhetv= (TextView) view.findViewById(R.id.zuozhetv);

            TextView title= (TextView) view.findViewById(R.id.title);
            TextView update= (TextView) view.findViewById(R.id.update);
            ImageView imageView= (ImageView) view.findViewById(R.id.image);

            TextView fenshu= (TextView) view.findViewById(R.id.fenshu);

            TextView jiagetiv= (TextView) view.findViewById(R.id.jiagetiv);


            TextView delete= (TextView) view.findViewById(R.id.delete);
            TextView tvLike= (TextView) view.findViewById(R.id.tv_like);
            TextView tvCollect= (TextView) view.findViewById(R.id.tv_collect);
            TextView tvIsLike= (TextView) view.findViewById(R.id.tv_isLike);
            TextView tvIsCollect= (TextView) view.findViewById(R.id.tv_isCollect);
            LinearLayout mLayout= (LinearLayout) view.findViewById(R.id.layout_content);
            tvLike.setText("取消赞");
            tvCollect.setText("取消收藏");
            if(status.equals("1")){
                tvCollect.setVisibility(View.VISIBLE);
                tvIsCollect.setVisibility(View.VISIBLE);

                tvLike.setVisibility(View.GONE);
                tvIsLike.setVisibility(View.GONE);

            }else{
                tvLike.setVisibility(View.VISIBLE);
                tvIsLike.setVisibility(View.VISIBLE);
                tvCollect.setVisibility(View.GONE);
                tvIsCollect.setVisibility(View.GONE);

            }
            if(danzis.get(position).getLike().equals("1")||
                    danzis.get(position).getCollect().equals("1")){
                mLayout.setVisibility(View.VISIBLE);
            }
            if(danzis.get(position).getLike().equals("1")){
                tvIsLike.setText("已点赞");
            }else{
                tvIsLike.setVisibility(View.GONE);
            }
            if(danzis.get(position).getCollect().equals("1")){
                tvIsCollect.setText("已收藏");
            }else{
                tvIsCollect.setVisibility(View.GONE);

            }



                update.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);



            zuozhetv.setText("内容："+danzis.get(position).getNeirong());
            jiagetiv.setText("价格："+danzis.get(position).getJiege());


                if(danzis.get(position).getImgurl()!=null)
            {
                x.image().bind(imageView, new File(danzis.get(position).getImgurl()).toURI().toString());

            }
            fenshu.setText("评分："+danzis.get(position).getFenshu());


            title.setText("标题："+danzis.get(position).getBookname());


            //修改按钮，
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(myContext, AddbookActivity.class).putExtra("data",danzis.get(position)));
                }
            });
            //删除按钮
            view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Xutils.initDbConfiginit().delete(danzis.get(position));
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    danzis.remove(position);
                    notifyDataSetChanged();

                }
            });
            tvLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        Jingdian jingdian = danzis.get(position);
                        WhereBuilder builder = WhereBuilder.b();

                        builder.and("id", "=", jingdian.getId());

                        KeyValue key1 = new KeyValue("like", "0");

                        Xutils.initDbConfiginit().update(Jingdian.class,builder,key1);
                        Toast.makeText(CaiListActivity.this, "取消点赞成功", Toast.LENGTH_SHORT).show();
                        danzis.get(position).setLike("0");
                        seleall();
                        notifyDataSetChanged();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }
            });
            tvCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Jingdian jingdian = danzis.get(position);
                        WhereBuilder builder = WhereBuilder.b();

                        builder.and("id", "=", jingdian.getId());

                        KeyValue key1 = new KeyValue("collect", "0");

                        Xutils.initDbConfiginit().update(Jingdian.class,builder,key1);
                        Toast.makeText(CaiListActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                        danzis.get(position).setCollect("0");
                        seleall();
                        notifyDataSetChanged();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }
            });
            return view;
        }
    };



}
