package com.example.zzapkaotalk.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.zzapkaotalk.MainActivity;
import com.example.zzapkaotalk.R;
import com.example.zzapkaotalk.profile.profile_activity;
import com.example.zzapkaotalk.profile.profile_item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Frag_Friends extends Fragment {

    private View view;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private int friends_count;
    private String idToken, userId;

    private LinearLayout main_friendList_View;
    private View userProfile;
    TextView countFriends;

    public Frag_Friends(String idToken, String userId){
        this.idToken = idToken;
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_frag_friends, container, false);

        main_friendList_View = view.findViewById(R.id.main_friendList_View);
        userProfile = view.findViewById(R.id.user_profile);

        database = FirebaseDatabase.getInstance(); // Firebase Database 연동

        databaseReference = database.getReference("ZzapKaoTalk/UserAccount/"); // 해당 document 연결

        countFriends = view.findViewById(R.id.tv_countFriends);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setView();
    }

    private void setView(){
        main_friendList_View.removeAllViews();

        databaseReference.child(idToken).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Firebase database의 data 받아오는 곳

                Log.i("userID", snapshot.toString());
                Log.i("databaseReference", databaseReference.child(idToken).toString());

                // 사용자 프로필 세팅
                profile_item item = snapshot.getValue(profile_item.class);

                ImageView img = userProfile.findViewById(R.id.img_profile);
                TextView name = userProfile.findViewById(R.id.tv_profile_name);
                TextView msg = userProfile.findViewById(R.id.tv_profile_msg);

                Glide.with(userProfile)
                        .load(item.getProfile_img())
                        .into(img);
                name.setText(item.getName());
                msg.setText(item.getProfile_msg());

                // 클릭 시 프로필 상세 페이지 이동
                userProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        set_profileClicked(item, true);
                    }
                });

                // 친구 목록에 있는 친구 수, 친구들 프로필 추가
                String countFriendsText = "친구 ";
                if(item.getList_friends() == null)
                    countFriendsText += "0";
                else{
                    countFriendsText += item.getList_friends().size();
                    set_FriendList(item); // 친구들 프로필 추가
                }

                countFriends.setText(countFriendsText); // 친구 수
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB 가져오는 중 Error 발생 시
                Log.e("Call Data", "Fail...");
            }
        });
    }

    // 친구 목록에 칭구들 프로필 추가
    private void set_FriendList(profile_item item){
        for(String friends_idToken : item.getList_friends().keySet()){
            Log.i("friends_idToken", friends_idToken);
            Log.i("databaseReference", databaseReference.toString());
            Log.i("databaseReference", databaseReference.child(friends_idToken).toString());

            databaseReference.child(friends_idToken).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.i("dataSnapShot", snapshot.toString());

                    profile_item friend = snapshot.getValue(profile_item.class);

                    LinearLayout new_friend = (LinearLayout) View.inflate(getContext(), R.layout.list_main_friends, null);

                    ImageView friend_img = new_friend.findViewById(R.id.img_profile);
                    TextView friend_name = new_friend.findViewById(R.id.tv_profile_name);
                    TextView friend_msg = new_friend.findViewById(R.id.tv_profile_msg);

                    Glide.with(new_friend)
                            .load(friend.getProfile_img())
                            .into(friend_img);
                    friend_name.setText(friend.getName());
                    friend_msg.setText(friend.getProfile_msg());

                    main_friendList_View.addView(new_friend);
                    new_friend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            set_profileClicked(friend, false);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    // 클릭시 프로필 상세 페이지 이동
    private void set_profileClicked(profile_item profile, boolean isUser){
        Intent intent = new Intent(getContext(), profile_activity.class);

        intent.putExtra("user_idToken", idToken); // 본인 idToken
        intent.putExtra("profile", profile); // 상세보기하는 선택한 프로필
        intent.putExtra("isUser", isUser); // 사용자면 true, 아니면 false 전달

        startActivity(intent);
    }
}
