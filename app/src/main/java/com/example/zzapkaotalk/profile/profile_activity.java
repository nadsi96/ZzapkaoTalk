package com.example.zzapkaotalk.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zzapkaotalk.R;
import com.google.firebase.database.FirebaseDatabase;

public class profile_activity extends AppCompatActivity {

    ImageView img_profile, img_profile_bg, img_profile_exit;
    TextView tv_profile_name, tv_profile_msg;

    LinearLayout btn_editProfile, btn_1on1Chat, btn_addToList, btn_block;

    profile_item profile;
    String idToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        img_profile_bg = findViewById(R.id.img_profile_bg);
        img_profile = findViewById(R.id.img_profile);
        img_profile_exit = findViewById(R.id.img_profile_exit);
        tv_profile_msg = findViewById(R.id.tv_profile_msg);
        tv_profile_name = findViewById(R.id.tv_profile_name);

        btn_editProfile = findViewById(R.id.btn_editProfile);
        btn_1on1Chat = findViewById(R.id.btn_1on1chat);
        btn_addToList = findViewById(R.id.btn_addToList);
        btn_block = findViewById(R.id.btn_block);

        Intent intent = getIntent();
        profile = (profile_item)intent.getSerializableExtra("profile");
        idToken = intent.getStringExtra("user_idToken");
        boolean isUser = intent.getBooleanExtra("isUser", false);
        boolean isNewFriend = intent.getBooleanExtra("isNew", false);

        Glide.with(this).load(profile.getProfile_bg()).into(img_profile_bg);
        Glide.with(this)
                .load(profile.getProfile_img())
                .into(img_profile);
        tv_profile_name.setText(profile.getName());
        tv_profile_msg.setText(profile.getProfile_msg());

        // 프로필 메뉴 설정
        set_profileMenu(isUser, isNewFriend);

        // 닫기 버튼
        img_profile_exit.bringToFront(); // 화면 맨 위에 출력
        img_profile_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    // 프로필 메뉴 설정
    // 본인 프로필인 경우, 프로필 편집
    // 새로운 친구인 경우, 친구 추가
    // 사용자의 친구인 경우, 1:1 채팅, 친구 차단
    private void set_profileMenu(boolean isUser, boolean isNewFriend){
        setInvisible();
        if(isUser){
            btn_editProfile.setVisibility(View.VISIBLE);
            btn_editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 프로필 편집
                    // 프로필 사진 or 배경 바꾸기
                }
            });
        }else if(isNewFriend){
            btn_addToList.setVisibility(View.VISIBLE);
            btn_addToList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //친구 추가
                    FirebaseDatabase.getInstance().
                            getReference("ZzapKaoTalk/UserAccount/").
                            child(idToken).
                            child("list_friends").
                            child(profile.getIdToken()).
                            setValue(true);

                    Toast.makeText(profile_activity.this, "친구 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    set_profileMenu(false, false);
                }
            });
        }else{
            btn_1on1Chat.setVisibility(View.VISIBLE);
            btn_block.setVisibility(View.VISIBLE);

            btn_1on1Chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 1:1 채팅방 만들기
                }
            });

            btn_block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 친구 목록에서 제거

                    Toast.makeText(profile_activity.this, "친구 목록에서 제거되었습니다.", Toast.LENGTH_SHORT).show();
                    set_profileMenu(false, true);
                }
            });
        }
    }

    private void setInvisible(){
        btn_editProfile.setVisibility(View.GONE);
        btn_1on1Chat.setVisibility(View.GONE);
        btn_addToList.setVisibility(View.GONE);
        btn_block.setVisibility(View.GONE);
    }
}