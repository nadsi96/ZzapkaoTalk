package com.example.zzapkaotalk.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zzapkaotalk.R;

public class profile_activity extends AppCompatActivity {

    ImageView img_profile, img_profile_bg;
    TextView tv_profile_name, tv_profile_msg;

    profile_item profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        img_profile_bg = findViewById(R.id.img_profile_bg);
        img_profile = findViewById(R.id.img_profile);
        tv_profile_msg = findViewById(R.id.tv_profile_msg);
        tv_profile_name = findViewById(R.id.tv_profile_name);

        Intent intent = getIntent();
        profile = (profile_item)intent.getSerializableExtra("profile");
        boolean isUser = intent.getBooleanExtra("isUser", false);

        Glide.with(this).load(profile.getProfile_bg()).into(img_profile_bg);
        Glide.with(this)
                .load(profile.getProfile_img())
                .into(img_profile);
        tv_profile_name.setText(profile.getName());
        tv_profile_msg.setText(profile.getProfile_msg());

        if(isUser){
            findViewById(R.id.btn_1on1chat).setVisibility(View.GONE);
        }

        findViewById(R.id.img_profile_exit).bringToFront(); // 화면 맨 위에 출력
    }
}