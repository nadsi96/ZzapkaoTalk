package com.example.zzapkaotalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zzapkaotalk.fragment.Frag_Friends;
import com.example.zzapkaotalk.fragment.Frag_Message;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;

    private Frag_Friends frag_friends;
    private Frag_Message frag_message;

    private TextView tv_mainTitle;
    private ImageView img_addItem;

    private String idToken, userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_mainTitle = findViewById(R.id.tv_mainTitle);
        img_addItem = findViewById(R.id.img_addItem);

        Intent intent = getIntent();
        idToken = intent.getStringExtra("idToken");
        userId = intent.getStringExtra("userId");


        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_friends:
                        setFrag(1);
                        break;
                    case R.id.action_message:
                        setFrag(2);
                        break;
                    case R.id.action_search:
                        setFrag(3);
                        break;
                    case R.id.action_setting:
                        setFrag(4);
                        break;
                }
                return true;
            }
        });
        frag_friends = new Frag_Friends(idToken, userId);
        frag_message = new Frag_Message(idToken, userId);

        setFrag(1); // Frag_Friends를 첫 화면으로
    }

    // Fragment 교체 실행
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n){
            case 1:
                ft.replace(R.id.frame_main, frag_friends);
                ft.commit();
                tv_mainTitle.setText("친구");
                img_addItem.setImageResource(R.drawable.ic_baseline_person_add_24);
                break;
            case 2:
                ft.replace(R.id.frame_main, frag_message);
                ft.commit();
                tv_mainTitle.setText("채팅");
                img_addItem.setImageResource(R.drawable.ic_add_text);
                break;
            default:
                break;
        }
    }
}