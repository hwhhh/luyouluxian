package com.flag.myapplication.car;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flag.myapplication.car.ShowbookActivity;
import com.flag.myapplication.car.bean.DataEntity;


import org.xutils.x;

import java.io.File;
import java.util.List;

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<DataEntity> dataEntity;
    private CheckBoxListener checkBoxListener;

    public ExpandableAdapter(Context context, List<DataEntity> dataEntity) {
        this.context = context;
        this.dataEntity = dataEntity;
    }

    /**
     * 获取组的数目
     *
     * @return 返回一级列表组的数量
     */
    @Override
    public int getGroupCount() {
        return dataEntity == null ? 0 : dataEntity.size();
    }

    /**
     * 获取指定组中的子节点数量
     *
     * @param groupPosition 子元素组所在的位置
     * @return 返回指定组中的子数量
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return dataEntity.get(groupPosition).getChildrenDataList().size();
    }

    /**
     * 获取与给定组相关联的对象
     *
     * @param groupPosition 子元素组所在的位置
     * @return 返回指定组的子数据
     */
    @Override
    public Object getGroup(int groupPosition) {
        return dataEntity.get(groupPosition).getTitle();
    }


    /**
     * 获取与给定组中的给定子元素关联的数据
     *
     * @param groupPosition 子元素组所在的位置
     * @param childPosition 子元素的位置
     * @return 返回子元素的对象
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataEntity.get(groupPosition).getChildrenDataList().get(childPosition);
    }

    /**
     * 获取组在给定位置的ID（唯一的）
     *
     * @param groupPosition 子元素组所在的位置
     * @return 返回关联组ID
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    /**
     * 获取给定组中给定子元素的ID(唯一的)
     *
     * @param groupPosition 子元素组所在的位置
     * @param childPosition 子元素的位置
     * @return 返回子元素关联的ID
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * @return 确定id 是否总是指向同一个对象
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * @return 返回指定组的对应的视图 （一级列表样式）
     */
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentHolder parentHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itemfu, null);
            parentHolder = new ParentHolder();
            parentHolder.name = convertView.findViewById(R.id.name);
            convertView.setTag(parentHolder);
        } else {
            parentHolder = (ParentHolder) convertView.getTag();
        }
        parentHolder.name.setText( "  "+dataEntity.get(groupPosition).getTitle());




        return convertView;
    }

    /**
     * @return 返回指定位置对应子视图的视图
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildrenHolder childrenHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itempar, null);
            childrenHolder = new ChildrenHolder();
            childrenHolder.zuozhetv = convertView.findViewById(R.id.zuozhetv);

            childrenHolder.title = convertView.findViewById(R.id.title);

            childrenHolder.fenshu = convertView.findViewById(R.id.fenshu);
            childrenHolder.jiagetiv = convertView.findViewById(R.id.jiagetiv);

            childrenHolder.img = convertView.findViewById(R.id.image);



            convertView.setTag(childrenHolder);
        } else {
            childrenHolder = (ChildrenHolder) convertView.getTag();
        }

        childrenHolder.zuozhetv.setText("人员："+dataEntity.get(groupPosition).getChildrenDataList().get(childPosition).getZhuoze());
        childrenHolder.fenshu.setText("评分："+dataEntity.get(groupPosition).getChildrenDataList().get(childPosition).getFenshu());
        childrenHolder.jiagetiv.setText("价格："+dataEntity.get(groupPosition).getChildrenDataList().get(childPosition).getJiege());
        childrenHolder.title.setText("标题："+dataEntity.get(groupPosition).getChildrenDataList().get(childPosition).getBookname());
        x.image().bind(childrenHolder.img, new File(dataEntity.get(groupPosition).getChildrenDataList().get(childPosition).getImgurl()).toURI().toString());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, ShowbookActivity.class);
                intent.putExtra("data",dataEntity.get(groupPosition).getChildrenDataList().get(childPosition));
                context.startActivity(intent);
            }
        });


        return convertView;
    }

    /**
     * 指定位置的子元素是否可选
     *
     * @param groupPosition 子元素组所在的位置
     * @param childPosition 子元素的位置
     * @return 返回是否可选
     */

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class ParentHolder {
        TextView name;
    }


    class ChildrenHolder {
        TextView jiagetiv,title ,fenshu,zuozhetv,chuanshe;
        ImageView img;









    }


    /**
     * 用于提供对外复选框修改通知接口
     */
    public interface CheckBoxListener {
        void checkStateListener(int groupPosition, int childPosition, boolean isChecked);
    }

    public void setCheckBoxListener(CheckBoxListener checkBoxListener) {
        this.checkBoxListener = checkBoxListener;
    }


    /**
     * 用于刷新更新后的数据
     */
    public void reFreshData(List<DataEntity> dataEntity) {
        this.dataEntity = dataEntity;
        notifyDataSetChanged();
    }


}