package com.example.zzapkaotalk.profile;

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
    private int list_chatRoom;
    private int list_friends;

    public profile_item(){

    }

    public profile_item(String id, String name, String phone, String profile_img, String profile_bg, String profile_msg) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.profile_img = profile_img;
        this.profile_bg = profile_bg;
        this.profile_msg = profile_msg;
        this.list_chatRoom = 0;
        this.list_friends = 0;
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

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public String getProfile_bg() {
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

    public int getList_chatRoom() {
        return list_chatRoom;
    }

    public void setList_chatRoom(int list_chatRoom) {
        this.list_chatRoom = list_chatRoom;
    }

    public int getList_friends() {
        return list_friends;
    }

    public void setList_friends(int list_friends) {
        this.list_friends = list_friends;
    }
}
