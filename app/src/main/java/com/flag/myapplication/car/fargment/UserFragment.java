package com.flag.myapplication.car.fargment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flag.myapplication.car.CaiListActivity;
import com.flag.myapplication.car.MyApplication;
import com.flag.myapplication.car.R;
import com.flag.myapplication.car.utils.DemoUtils;
import com.flag.myapplication.car.utils.Xutils;
import com.flag.myapplication.car.LoginActivity;
import com.flag.myapplication.car.UpdateuserActivity;

import com.flag.myapplication.car.bean.User;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import org.xutils.ex.DbException;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Looper.getMainLooper;


public class UserFragment extends Fragment implements View.OnClickListener, TencentLocationListener {

    TextView name, updatemy, tvAddress, tvLike, tvCollect;
    Button tuichu;
    static User useraa;
    private TencentLocationManager mLocationManager;
    private TencentLocationRequest request;

    private static final String NOTIFICATION_CHANNEL_NAME = "locationdemoBackgroundLocation";
    private static final int LOC_NOTIFICATIONID = 100;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
          tvAddress.setText("当前位置:"+(String)msg.obj);
        }
    };
    private SharedPreferences pref;

    public UserFragment() {
    }


    public static UserFragment newInstance(String param1, User user
    ) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        useraa = user;
        fragment.setArguments(args);
        return fragment;
    }

    public static UserFragment newInstance(int id) {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getActivity(). getSharedPreferences("data",MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        name = view.findViewById(R.id.name);
        tuichu = view.findViewById(R.id.tuichu);
        updatemy = view.findViewById(R.id.updatemy);
        tvAddress = view.findViewById(R.id.tv_address);
        tvLike = view.findViewById(R.id.tv_like);
        tvCollect = view.findViewById(R.id.tv_collect);


        initdata();
        String address = pref.getString("address","");
        tvAddress.setText("当前位置:"+address);
        return view;
    }

    public void initdata() {

        tuichu.setOnClickListener(this);
        updatemy.setOnClickListener(this);
        tvLike.setOnClickListener(this);
        tvCollect.setOnClickListener(this);


        try {
            User user = Xutils.initDbConfiginit().selector(User.class).where("zhuangt", "=", "1").findFirst();
            if (user != null) {
                name.setText(user.getUsername() + "");
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        mLocationManager = TencentLocationManager.getInstance(MyApplication.getInstance().getApplicationContext());
//        mLocationManager.setDeviceID(this, "7E35C989E01E1E48A8D9059C355F7C4A");
        // 设置坐标系为 gcj-02, 缺省坐标为 gcj-02, 所以通常不必进行如下调用
        mLocationManager
                .setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
        mLocationManager.triggerCodeGuarder(true);
        TencentLocationRequest request = TencentLocationRequest
                .create()
                .setRequestLevel(
                        TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA)
                .setInterval(10); // 设置定位周期, 建议值为 1s-20s

        // 开始定位
//        mLocationManager.enableForegroundLocation(LOC_NOTIFICATIONID, buildNotification());
        mLocationManager.requestLocationUpdates(request, this, getMainLooper());

        updateLocationStatus("开始定位: " + request + ", 坐标系="
                + DemoUtils.toString(mLocationManager.getCoordinateType()));
    }

    private void updateLocationStatus(String message) {
//        mLocationStatus.append(message);
//        mLocationStatus.append("\n---\n");
        System.out.println("开始定位->>" + message);
//        Toast.makeText(getActivity(), "地址:" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tuichu) {

            try {
                useraa.setZhuangt(0);
                Xutils.initDbConfiginit().update(useraa);
            } catch (DbException e) {
                e.printStackTrace();
            }
            getActivity().finish();

            startActivity(new Intent(getActivity(), LoginActivity.class));
        } else if (v.getId() == R.id.updatemy) {
            Intent intent = new Intent(getActivity(), UpdateuserActivity.class);
            intent.putExtra("data", useraa);
            startActivity(intent);
        }else if(v.getId()==R.id.tv_like){
            Intent intent = new Intent(getActivity(), CaiListActivity.class);
            intent.putExtra("title","我的点赞");
            intent.putExtra("status", "0");
            startActivity(intent);
        }else if(v.getId()==R.id.tv_collect){
            Intent intent = new Intent(getActivity(), CaiListActivity.class);
            intent.putExtra("status", "1");
            intent.putExtra("title","我的收藏");
            startActivity(intent);

        }


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        String address = pref.getString("address","");
        tvAddress.setText("当前位置:"+address);
    }

    @Override
    public void onLocationChanged(final TencentLocation location, int error, String s) {
        String msg="";

//        Toast.makeText(getActivity(),"code"+i,1000).show();
        if (error == TencentLocation.ERROR_OK) {
            Log.d("location", location.getAddress());
            pref.edit().putString("address",location.getAddress()).commit();
            Message message=new Message();
            message.obj=location.getAddress();
            message.what=1;
            if(handler!=null)
                handler.sendMessage(message);
            // 定位成功
            StringBuilder sb = new StringBuilder();
            sb.append("(纬度=").append(location.getLatitude()).append(",经度=")
                    .append(location.getLongitude()).append(",精度=")
                    .append(location.getAccuracy()).append("), 来源=")
                    .append(location.getProvider()).append(", 城市=")
                    .append(location.getCity()).append(",citycode=")
                    .append(location.getCityCode());
            msg = sb.toString();
        } else {
            // 定位失败
            msg = "定位失败: ";
        }
        updateLocationStatus(msg);

    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mLocationManager.removeUpdates(this);
        if(handler!=null)
        handler.removeMessages(1);
        handler=null;
    }
}
