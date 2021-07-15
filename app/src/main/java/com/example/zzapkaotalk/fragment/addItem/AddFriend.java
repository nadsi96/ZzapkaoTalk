package com.example.zzapkaotalk.fragment.addItem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.zzapkaotalk.R;
import com.example.zzapkaotalk.profile.profile_item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AddFriend extends AddItem{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_item);

        Log.i("test Log", "say something");

        super.tv_title.setText("친구 추가");

        super.btn_searchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputText_for_search = et_searchId.getText().toString();

                // 이름으로 검색
                databaseReference.orderByChild("name").equalTo(inputText_for_search).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.i("data Check", snapshot.toString());
                        Log.i("data Check getValue", snapshot.getValue().toString());
                        // 등록된 친구는 표시 안하도록 수정 필요
                        // id도 표시하도록 수정 필요
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Log.i("dataSnapshot", dataSnapshot.getValue().toString());
                            profile_item friend = dataSnapshot.getValue(profile_item.class);
                            RelativeLayout new_friend = (RelativeLayout) View.inflate(AddFriend.this, R.layout.list_add_friends, null);

                            ImageView friend_img = new_friend.findViewById(R.id.img_profile);
                            TextView friend_name = new_friend.findViewById(R.id.tv_profile_name);

                            Glide.with(new_friend)
                                    .load(friend.getProfile_img())
                                    .into(friend_img);
                            friend_name.setText(friend.getName());

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

    // 친구 추가 버튼에 동작 설정
//   View.OnClickListener btn_addFriendClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            databaseReference.child(idToken).
//        }
//    }

}
