package com.example.zzapkaotalk.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zzapkaotalk.R;

public class Frag_Message extends Fragment {

    private View view;

    private String idToken, userId;

    public Frag_Message(String idToken, String userId){
        this.idToken = idToken;
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_frag_message, container, false);

        return view;
    }
}
