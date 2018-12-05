package com.zj.ocr;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.zj.orc.R;
import com.zj.orc.databinding.ActivityMainBinding;
import com.zj.ocr.network.AuthService;
import com.zj.ocr.utils.ShareUtil;

public class LoginActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil. setContentView(this, R.layout.activity_main);
        binding.setPresenter(this);

    }

    public void onLogin(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                token=AuthService.getAuth();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (token==null|| TextUtils.isEmpty(token)){
                            Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }else{
                            ShareUtil.getInstance().putString("token",token);
                            MenuActivity.launchActivity(LoginActivity.this);
                        }
                    }
                });
            }
        }).start();

    }
}
