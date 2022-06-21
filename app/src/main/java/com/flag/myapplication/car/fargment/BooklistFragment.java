package com.flag.myapplication.car.fargment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.flag.myapplication.car.R;
import com.flag.myapplication.car.utils.Xutils;
import com.flag.myapplication.car.ExpandableAdapter;
import com.flag.myapplication.car.bean.Jingdian;
import com.flag.myapplication.car.bean.DataEntity;


import java.util.ArrayList;
import java.util.List;


public class BooklistFragment extends Fragment {
    public Context myContext;
    private TextView text;
    String titlelist;
    ExpandableListView expandableListView;
    private List<DataEntity>  dataEntityList=new ArrayList<>();
    private ExpandableAdapter studentExpandableAdapter;
    String [ ] zuname;

    public BooklistFragment() {
    }


    public static BooklistFragment newInstance(String param1) {
        BooklistFragment fragment = new BooklistFragment();
        Bundle args = new Bundle();
        args.putString("title", param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext=getActivity();
        if (getArguments() != null) {
            titlelist = getArguments().getString("title");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //初始化控件
        View view = inflater.inflate(R.layout.fragment_booklist, container, false);
         zuname = getActivity().getResources().getStringArray(R.array.typearray);
        expandableListView =view.findViewById(R.id.exlistview);
        text=view.findViewById(R.id.text);
        text.setText("类型");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        initAdapter();
    }

    private void initData() {

        //获取各个分类的数据集合
        dataEntityList.clear();
        for(int i=0;i<zuname.length;i++){
            List<Jingdian> childrenData=new ArrayList<>() ;
            try {
                List<Jingdian> datalist = Xutils.initDbConfiginit().selector(Jingdian.class).where("typename","=",zuname[i]).findAll();

                if(datalist!=null)
                {
                    childrenData.addAll(datalist);
                }
                DataEntity dataEntity=new DataEntity(zuname[i],childrenData);
                dataEntityList.add(dataEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }



    private void initAdapter() {

        //添加适配器
        studentExpandableAdapter=new ExpandableAdapter(getActivity(),dataEntityList);
        expandableListView.setAdapter(studentExpandableAdapter);

        studentExpandableAdapter.setCheckBoxListener(new ExpandableAdapter.CheckBoxListener() {
            @Override
            public void checkStateListener(int groupPosition, int childPosition, boolean isChecked) {
                Log.e("MainActivity","isChecked:"+isChecked);
                dataEntityList.get(groupPosition).getChildrenDataList().get(childPosition);
            }
        });


        /**
         * 默认展开某个item
         * */
        //expandableListView.expandGroup(1);


    }







}
