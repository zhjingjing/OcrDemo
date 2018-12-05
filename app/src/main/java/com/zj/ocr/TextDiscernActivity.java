package com.zj.ocr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.loopj.android.http.RequestHandle;
import com.zj.ocr.network.ResponseHandler;
import com.zj.ocr.utils.Base64BitmapUtil;
import com.zj.orc.R;
import com.zj.ocr.bean.TextDuscernBean;
import com.zj.orc.databinding.ActivityTextDiscernBinding;
import com.zj.ocr.network.AiApi;

import java.io.File;

public class TextDiscernActivity extends AppCompatActivity {

    private ActivityTextDiscernBinding binding;
    private String normalUrl="http://d.hiphotos.baidu.com/exp/w=500/sign=feeac86ced1190ef01fb92dffe1a9df7/32fa828ba61ea8d386c06c859d0a304e241f5854.jpg";
    private final static int REQUEST_TAKE_PHOTO=1101;
    private final static int REQUEST_PERMISSION=1102;


    public static void launchActivity(Context context){
        context.startActivity(new Intent(context,TextDiscernActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil. setContentView(this, R.layout.activity_text_discern);
        binding.setPresenter(this);
    }


    public void onUrlClick(){
        RequestHandle requestHandle= AiApi.getGeneralBasicurl(normalUrl, new ResponseHandler<TextDuscernBean>() {
            @Override
            public void onFailure(int statusCode, int errorCode, String msg) {
                Toast.makeText(TextDiscernActivity.this,msg,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(TextDuscernBean result) {
                if (result!=null){
                    String words="";
                    for (int i=0;i<result.words_result_num;i++){
                        words+=result.words_result.get(i).words+";";
                    }
//                    Toast.makeText(TextDiscernActivity.this,words,Toast.LENGTH_SHORT).show();
                    binding.tvResult.setText(words);
                }
            }

        });
    }

    public void onTakePhotoClick(){
        if (Build.VERSION.SDK_INT>23){
            getPermissionPhoto();
        }else{
            takePhoto();
        }
    }

    public void getPermissionPhoto(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TextDiscernActivity.this,new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION);
        }else{
            takePhoto();
        }
    }

    File tempFile;
    public void takePhoto(){
        tempFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/photo/"+System.currentTimeMillis() + ".jpg");
        tempFile.getParentFile().mkdirs();
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){

            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            if (Build.VERSION.SDK_INT>=24){
                intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(this, "com.zj.orc.FileProvider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            } else {
                //否则使用Uri.fromFile(file)方法获取Uri
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            }
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_PERMISSION){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                takePhoto();
            }else{
                Toast.makeText(this,"需要拍照等权限",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_TAKE_PHOTO:
                    if (tempFile!=null){
                        Toast.makeText(this,"成功啦",Toast.LENGTH_SHORT).show();
//                        Uri contentUri;
//                        if (Build.VERSION.SDK_INT>=24){
//                             contentUri = FileProvider.getUriForFile(this, "com.zj.orc.FileProvider", tempFile);
//                        } else {    //否则使用Uri.fromFile(file)方法获取Uri
//                            contentUri=Uri.fromFile(tempFile);
//                        }
                        Bitmap bitmap=BitmapFactory.decodeFile(tempFile.getAbsolutePath());
                        binding.ivPhoto.setImageBitmap(bitmap);
                    }else {
                        Toast.makeText(this,"图片缓存文件为空",Toast.LENGTH_SHORT).show();
                    }
                    break;



            }


        }

    }

    public void onImageInfoClick(){
        if (tempFile!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(tempFile.getAbsolutePath());

            String base= Base64BitmapUtil.bitmapToBase64(bitmap);
            RequestHandle requestHandle= AiApi.getGeneralBasic(base, new ResponseHandler<TextDuscernBean>() {
                @Override
                public void onFailure(int statusCode, int errorCode, String msg) {
                    Toast.makeText(TextDiscernActivity.this,msg,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(TextDuscernBean result) {
                    if (result!=null){
                        String words="";
                        for (int i=0;i<result.words_result_num;i++){
                            words+=result.words_result.get(i).words+";";
                        }
//                        Toast.makeText(TextDiscernActivity.this,words,Toast.LENGTH_SHORT).show();
                        binding.tvResult.setText(words);
                    }

                }

            });
        }




    }


    private BitmapFactory.Options getBitmapOption(int inSampleSize)

    {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }
}
