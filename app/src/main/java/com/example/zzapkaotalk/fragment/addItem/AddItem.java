package com.example.zzapkaotalk.fragment.addItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zzapkaotalk.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.zip.Inflater;

public class AddItem extends AppCompatActivity {

    protected String idToken, userId;

    protected FirebaseDatabase database;
    protected DatabaseReference databaseReference;

    protected Button btn_searchId;
    protected EditText et_searchId;
    protected TextView tv_title;

    protected LayoutInflater inflater;
    protected LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Intent intent = getIntent();
        idToken = intent.getStringExtra("idToken");
        userId = intent.getStringExtra("userId");

        btn_searchId = findViewById(R.id.btn_searchId);
        et_searchId = findViewById(R.id.et_searchId);
        tv_title = findViewById(R.id.tv_addTitle);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("ZzapKaoTalk/UserAccount/");

        inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        linearLayout = findViewById(R.id.view_friendList);
    }

//    @Nullable
//    @Override
//    public View onCreateView(@Nullable View parent,
//                             @NonNull String name,
//                             @NonNull Context context,
//                             @NonNull AttributeSet attrs) {
//
//
//        return super.onCreateView(parent, name, context, attrs);
//    }
}