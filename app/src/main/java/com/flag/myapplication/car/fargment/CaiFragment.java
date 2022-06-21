package com.flag.myapplication.car.fargment;

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

import androidx.fragment.app.Fragment;

import com.flag.myapplication.car.R;
import com.flag.myapplication.car.utils.Xutils;
import com.flag.myapplication.car.AddbookActivity;

import com.flag.myapplication.car.ShowbookActivity;
import com.flag.myapplication.car.bean.Jingdian;
import com.flag.myapplication.car.bean.User;


import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class CaiFragment extends Fragment {
    private static User userbean;
    public Context myContext;
     TextView text,nodate;
    ImageView add;
    ListView listView;
    List<Jingdian> danzis=new ArrayList<>();

    EditText edsele;
    Button selebt;

    String titlelist;
    public CaiFragment() {
        // Required empty public constructor
    }

    public static CaiFragment newInstance(String param1, User user) {
        CaiFragment fragment = new CaiFragment();
        Bundle args = new Bundle();
        userbean=user;
        args.putString("title", param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            titlelist = getArguments().getString("title");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //控件初始化
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        text=view.findViewById(R.id.text);
         add=view.findViewById(R.id.add);
        nodate=view.findViewById(R.id.nodate);
        edsele=view.findViewById(R.id.edsele);
        selebt=view.findViewById(R.id.selebt);





        listView=view.findViewById(R.id.list);

        //item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(userbean.getType()==1)
                {
                    Intent intent=new Intent(myContext, AddbookActivity.class);
                    intent.putExtra("data",danzis.get(position));
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(myContext, ShowbookActivity.class);
                    intent.putExtra("data",danzis.get(position));
                    startActivity(intent);
                }


            }
        });

        //搜索按钮
        selebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleall();
            }
        });


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        initdata ();


    }

    public void   initdata ()
    {

        //根据身份排队是否显示添加按钮
        if(userbean.getType()==1)
        {
            add.setVisibility(View.VISIBLE);
        }
        else
        {
            add.setVisibility(View.GONE);
        }
        //添加按钮点击事件
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(myContext, AddbookActivity.class),1);

            }
        });

        myContext=getActivity();
        text.setText(titlelist);




        seleall();
    }

    public void seleall()
    {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);//这两行是收起软键盘
        imm.hideSoftInputFromWindow(edsele.getWindowToken(), 0);
        danzis.clear();
        String string=edsele.getText()+"";
        string=string+"";


        try {
            danzis.addAll(Xutils.initDbConfiginit().selector(Jingdian.class).where("jingdianname","like","%"+string+"%").findAll());
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

            view= LayoutInflater.from(getActivity()).inflate(R.layout.item2,parent,false);
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


            if(userbean.getType()==1)
            {
                update.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);

            }
            else
            {
                update.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);

            }

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

                        KeyValue key1 = new KeyValue("like", "1");

                        Xutils.initDbConfiginit().update(Jingdian.class,builder,key1);
                        Toast.makeText(getActivity(), "点赞成功", Toast.LENGTH_SHORT).show();
                        danzis.get(position).setLike("1");
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

                        KeyValue key1 = new KeyValue("collect", "1");

                        Xutils.initDbConfiginit().update(Jingdian.class,builder,key1);
                        Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                        danzis.get(position).setCollect("1");
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
