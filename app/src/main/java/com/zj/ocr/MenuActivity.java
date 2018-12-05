package com.zj.ocr;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zj.orc.R;
import com.zj.orc.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;
    private String token;
    public static void launchActivity(Context context){
        context.startActivity(new Intent(context, MenuActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil. setContentView(this, R.layout.activity_menu);
        binding.setPresenter(this);


    }

    public void onTextDiscernClicked(){
        TextDiscernActivity.launchActivity(this);
    }


}
