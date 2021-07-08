package com.example.zzapkaotalk.profile;

import java.lang.reflect.Array;
import java.util.ArrayList;

/*
아이디
비밀번호
이름
전화번호
프로필 사진
프로필 배경
상태 메시지
친구목록 [ ]
채팅방 목록 [ ]
 */
public class profile_item {

    private String idToken;
    private String id;
    private String name;
    private String phone;
    private String profile_img;
    private String profile_bg;
    private String profile_msg;
    private ArrayList<String> list_chatRoom;
    private ArrayList<String> list_friends;

    public profile_item(){

    }

    public profile_item(String id, String name, String phone, String profile_img, String profile_bg, String profile_msg) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.profile_img = profile_img;
        this.profile_bg = profile_bg;
        this.profile_msg = profile_msg;
        this.list_chatRoom = new ArrayList<>();
        this.list_friends = new ArrayList<>();
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfile_img()
    {
        if (this.profile_img.equals("default"))
            return "https://firebasestorage.googleapis.com/v0/b/zzapkaotalk.appspot.com/o/send.png?alt=media&token=b18e6bcb-a7de-4b1f-8df0-9d9e5da9c22c";
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public String getProfile_bg()
    {
        if (this.profile_bg.equals("default"))
            return "https://firebasestorage.googleapis.com/v0/b/zzapkaotalk.appspot.com/o/currency.jpg?alt=media&token=067a70a5-543d-44b7-9611-6f02dab07482";
        return profile_bg;
    }

    public void setProfile_bg(String profile_bg) {
        this.profile_bg = profile_bg;
    }

    public String getProfile_msg() {
        return profile_msg;
    }

    public void setProfile_msg(String profile_msg) {
        this.profile_msg = profile_msg;
    }

    public ArrayList<String> getList_chatRoom() {
        return list_chatRoom;
    }

    public void setList_chatRoom(ArrayList<String> list_chatRoom) {
        this.list_chatRoom = list_chatRoom;
    }

    public ArrayList<String> getList_friends() {
        return list_friends;
    }

    public void setList_friends(ArrayList<String> list_friends) {
        this.list_friends = list_friends;
    }
}
