package com.flag.myapplication.car;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.flag.myapplication.car.utils.StrUtil;
import com.flag.myapplication.car.utils.Xutils;
import com.flag.myapplication.car.bean.Jingdian;


import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;


@ContentView(R.layout.activity_addbook)
public class AddbookActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.commotbutton)
    Button commotbutton;
    @ViewInject(R.id.addimageg)
    ImageView addimageg;

    @ViewInject(R.id.backimage)
    ImageView backimage;

    @ViewInject(R.id.title)
    EditText title;

    @ViewInject(R.id.neirong)
    EditText neirong;

    @ViewInject(R.id.fenshu)
    EditText fenshu;

    @ViewInject(R.id.IBSMtv)
    EditText IBSMtv;

    @ViewInject(R.id.typesp)
    Spinner typesp;



    @ViewInject(R.id.jiagettv)
    EditText jiagettv;
    @ViewInject(R.id.zhuoztv)
    EditText zhuoztv;

    File imageviewfile;
    @ViewInject(R.id.text)
    TextView text;




    private static final int CAMERA_CODE = 200;
    private static final int ALBUM_CODE = 100;



    String imagepath;
    Jingdian danzi;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        x.view().inject(AddbookActivity.this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {

        backimage.setVisibility(View.VISIBLE);
        commotbutton.setOnClickListener(this);
        addimageg.setOnClickListener(this);
        danzi= (Jingdian) getIntent().getSerializableExtra("data");
        text.setText("编辑景点");




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
        if (v.getId()==R.id.commotbutton)
        {
            save();
        }
        else if (v.getId()==R.id.abroad_choosephoto)
        {
            takesD();
            dialog.dismiss();

        }
        else if (v.getId()==R.id.abroad_takephoto)
        {
            takePhoto();
            dialog.dismiss();

        }
        else if (v.getId()==R.id.abroad_choose_cancel)
        {
            dialog.dismiss();
        }

        else if (v.getId()==R.id.addimageg)
        {
            chosePhotoDialog();
        }

        else if (v.getId()==R.id.backimage)
        {
            finish();
        }



    }



    private void takesD() {
        //权限判断
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            openAlbum();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    //打开相册
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent,ALBUM_CODE);
    }

    //处理权限
    private void takePhoto() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 200);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (permissions[0] == Manifest.permission.CAMERA){
                openCamera();
            }else if (permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE){
                openAlbum();
            }
        }
    }


    private File mFile;
    private Uri mImageUri;
    String path;

    private void openCamera() {
        //创建文件用于保存图片
        path=System.currentTimeMillis() + ".jpg";
        mFile = new File(getExternalCacheDir(), path);
        if (!mFile.exists()) {
            try {
                mFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //适配7.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mImageUri = Uri.fromFile(mFile);
        } else {
            //第二个参数要和清单文件中的配置保持一致
            mImageUri = FileProvider.getUriForFile(this, "guozhaohui.com.picturecropeasy", mFile);
        }

        //启动相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);//将拍照图片存入mImageUri
        startActivityForResult(intent, CAMERA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CODE) {
            if (resultCode == RESULT_OK) {
                try {

                    Uri imageUri = data.getData();
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
                    addimageg.setImageBitmap(bitmap);
                    imagepath = getExternalCacheDir().getAbsolutePath()+"/"+path;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == ALBUM_CODE) {
            //相册
            if (resultCode == RESULT_OK) {
                Uri imageUri = data.getData();



                imagepath = getFileFromUri(imageUri, this);
                addimageg.setImageURI(imageUri);









            }
        }
    }



    public String  getFileFromUri(Uri uri, Context context) {
        if (uri == null) {
            return null;
        }
        switch (uri.getScheme()) {
            case "content":
                return getFileFromContentUri(uri, context);
            case "file":
                return uri.getPath();
            default:
                return null;
        }
    }

    /**
     通过内容解析中查询uri中的文件路径
     */
    private String getFileFromContentUri(Uri contentUri, Context context) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = AddbookActivity.this.managedQuery(contentUri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = contentUri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        return img_path;
    }






    public void save()
    {

        if(danzi==null)
        {
            danzi=new Jingdian();

        }


        if(StrUtil.isEmpty(imagepath))
        {
            Toast.makeText(AddbookActivity.this,"请添加图案",Toast.LENGTH_LONG).show();
            return;
        }
        if(StrUtil.isEmpty(neirong.getText()+""))
        {
            Toast.makeText(AddbookActivity.this,"请输入简介",Toast.LENGTH_LONG).show();
            return;
        }
        if(StrUtil.isEmpty(title.getText()+""))
        {
            Toast.makeText(AddbookActivity.this,"请输入标题",Toast.LENGTH_LONG).show();
            return;
        }
        if(StrUtil.isEmpty(fenshu.getText()+""))
        {
            Toast.makeText(AddbookActivity.this,"请输入评分",Toast.LENGTH_LONG).show();
            return;
        }
        if(StrUtil.isEmpty(IBSMtv.getText()+""))
        {
            Toast.makeText(AddbookActivity.this,"请输入IBSM",Toast.LENGTH_LONG).show();
            return;
        }
        if(StrUtil.isEmpty(zhuoztv.getText()+""))
        {
            Toast.makeText(AddbookActivity.this,"请输入人员",Toast.LENGTH_LONG).show();
            return;
        }
        if(StrUtil.isEmpty(jiagettv.getText()+""))
        {
            Toast.makeText(AddbookActivity.this,"请输入价格",Toast.LENGTH_LONG).show();
            return;
        }
        danzi.setImgurl(imagepath);
        danzi.setNeirong(neirong.getText()+"");
        danzi.setBookname(title.getText()+"");
        danzi.setFenshu(Integer.parseInt(fenshu.getText()+""));
        danzi.setTypename(typesp.getSelectedItem()+"");
        danzi.setLuxian(IBSMtv.getText()+"");
        danzi.setZhuoze(zhuoztv.getText()+"");
        danzi.setJiege(jiagettv.getText()+"");
        try {
            Xutils.initDbConfiginit().saveOrUpdate(danzi);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Toast.makeText(AddbookActivity.this,"保存成功",Toast.LENGTH_LONG).show();
        finish();
    }





    private TextView cancel;
    private TextView takePhoto;
    private TextView choosePhoto;
    private Dialog dialog;

    public void chosePhotoDialog() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        View    inflate = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        choosePhoto = (TextView) inflate.findViewById(R.id.abroad_choosephoto);
        takePhoto = (TextView) inflate.findViewById(R.id.abroad_takephoto);
        cancel = (TextView) inflate.findViewById(R.id.abroad_choose_cancel);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        cancel.setOnClickListener(this);
        dialog.setContentView(inflate);
        Window window = dialog.getWindow();
        if (dialog != null && window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attr = window.getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.width = ViewGroup.LayoutParams.MATCH_PARENT;
                attr.gravity = Gravity.BOTTOM;//设置dialog 在布局中的位置
                window.setAttributes(attr);
            }
        }
        dialog.show();
    }



}
