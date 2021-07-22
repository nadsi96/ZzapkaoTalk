package com.example.zzapkaotalk.fragment.addItem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.zzapkaotalk.R;
import com.example.zzapkaotalk.profile.profile_item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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

                get_userFriendList();

                // 이름으로 검색
                databaseReference.orderByChild("name").equalTo(inputText_for_search).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.i("data Check", snapshot.toString());
                        Log.i("data Check getValue", snapshot.getValue().toString());
                        // 등록된 친구는 표시 안하도록 수정 필요

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
        ArrayList<String> list;
        databaseReference.child(super.idToken).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userFriendList = (ArrayList<String>) snapshot.child("list_friends").getValue();
                for(String friendsToken : userFriendList){
                    Log.i("Friends Token", friendsToken);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // 해당 user의 칸 클릭하면 프로필 화면 띄움
    View.OnClickListener show_Profile = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    // 친구 추가 버튼에 동작 설정
//   View.OnClickListener btn_addFriendClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            databaseReference.child(idToken).
//        }
//    }

}
