package com.example.zzapkaotalk.fragment.addItem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.zzapkaotalk.R;
import com.example.zzapkaotalk.profile.profile_activity;
import com.example.zzapkaotalk.profile.profile_item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class AddFriend extends AddItem{

    private ArrayList<String> userFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.tv_title.setText("친구 추가");

        super.btn_searchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputText_for_search = et_searchId.getText().toString();
                // 입력된 내용이 없으면 메시지 띄움
                if (inputText_for_search.length() <= 0){
                    Toast.makeText(getApplicationContext(), "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // userFriendList에 현재 친구 목록 저장
                get_userFriendList();

                // 이름으로 검색
                databaseReference.orderByChild("name").equalTo(inputText_for_search).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.i("data Check", snapshot.toString());
                        Log.i("data Check getValue", snapshot.getValue().toString());

                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Log.i("dataSnapshot", dataSnapshot.getValue().toString());

                            // 현재 친구목록에 존재하는 친구면 표시하지 않음음
                           if(userFriendList.contains(dataSnapshot.getKey()))
                                continue;

                            profile_item friend = dataSnapshot.getValue(profile_item.class);
                            RelativeLayout new_friend = (RelativeLayout) View.inflate(AddFriend.this, R.layout.list_add_friends, null);

                            ImageView friend_img = new_friend.findViewById(R.id.img_profile);
                            TextView friend_name = new_friend.findViewById(R.id.tv_profile_name);
                            TextView friend_email = new_friend.findViewById(R.id.tv_profile_id);

                            Glide.with(new_friend)
                                    .load(friend.getProfile_img())
                                    .into(friend_img);
                            friend_name.setText(friend.getName());
                            friend_email.setText(friend.getId());

                            // 해당 프로필 클릭시 동작(프로필 상세보기 이동)
                            set_profileClicked(new_friend, friend);

                            // 친구추가 버튼 클릭시 바로 친구 목록에 추가
                            // 검색 목록에서는 해당 프로필 삭제
                            set_btnAddFriendClicked(new_friend, new_friend.findViewById(R.id.btn_addFriend), friend.getIdToken());

                            linearLayout.addView(new_friend);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    // 현재 내게 저장된 친구 목록
    private  void get_userFriendList(){
        databaseReference.child(super.idToken).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //userFriendList = (ArrayList<String>) snapshot.child("list_friends").getValue();

                userFriendList = new ArrayList<>();
                for(DataSnapshot ds : snapshot.child("list_friends").getChildren()){
                    userFriendList.add(ds.getKey());
                    Log.i("Adding Friends Token", ds.getKey());
                }

                for(String friendsToken : userFriendList){
                    Log.i("Friends Token", friendsToken);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void set_profileClicked(RelativeLayout view, profile_item profile){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFriend.this, profile_activity.class);

                intent.putExtra("user_idToken", idToken); // 본인 idToken
                intent.putExtra("profile", profile); // 상세보기하는 선택한 프로필
                intent.putExtra("isNew", true);

                startActivity(intent);
            }
        });
    }

    // 친구 목록에 해당 친구 추가
    // 현재 검색목록에서 해당 친구 제거
    private void set_btnAddFriendClicked(View view, Button btn, String friends_idToken){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                databaseReference.child(idToken).child("list_friends").child(friends_idToken).setValue(true);

                view.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "친구 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 친구 추가 버튼에 동작 설정
//   View.OnClickListener btn_addFriendClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            databaseReference.child(idToken).
//        }
//    }

}
