package com.example.zzapkaotalk.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.zzapkaotalk.R;
import com.example.zzapkaotalk.profile.profile_item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Frag_Friends extends Fragment {

    private View view;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private int friends_count;
    private String idToken, userId;

    public Frag_Friends(String idToken, String userId){
        this.idToken = idToken;
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_frag_friends, container, false);

        View userProfile = view.findViewById(R.id.user_profile);

        database = FirebaseDatabase.getInstance(); // Firebase Database 연동

        databaseReference = database.getReference("ZzapKaoTalk/UserAccount/"+idToken); // 해당 document 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Firebase database의 data 받아오는 곳
                profile_item item = snapshot.getValue(profile_item.class);

                ImageView img = userProfile.findViewById(R.id.img_profile);
                TextView name = userProfile.findViewById(R.id.tv_profile_name);
                TextView msg = userProfile.findViewById(R.id.tv_profile_msg);

                Glide.with(userProfile)
                        .load(item.getProfile_img())
                        .into(img);
                name.setText(item.getName());
                msg.setText(item.getProfile_msg());

                TextView countFriends = view.findViewById(R.id.tv_countFriends);
                String countFriendsText = "친구 ";
                if(item.getList_friends() == null)
                    countFriendsText += "0";
                else
                    countFriendsText += item.getList_friends().size();
                countFriends.setText(countFriendsText);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB 가져오는 중 Error 발생 시
                Log.e("Call Data", "Fail...");
            }
        });

        return view;
    }
}
