package com.example.zzapkaotalk.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zzapkaotalk.R;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_frag_friends, container, false);

        database = FirebaseDatabase.getInstance(); // Firebase Database 연동

        databaseReference = database.getReference("userAccount"); // 해당 document 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Firebase database의 data 받아오는 곳


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
