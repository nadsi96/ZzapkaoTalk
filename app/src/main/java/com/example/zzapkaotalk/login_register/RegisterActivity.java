package com.example.zzapkaotalk.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zzapkaotalk.R;
import com.example.zzapkaotalk.profile.profile_item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth; // 파이어베이스 인증 처리
    private DatabaseReference databaseReference; // db
    private EditText et_registerId, et_registerPw, et_registerName, et_registerPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_registerId = findViewById(R.id.et_registerId);
        et_registerPw = findViewById(R.id.et_registerPw);
        et_registerName = findViewById(R.id.et_registerName);
        et_registerPhone = findViewById(R.id.et_registerPhone);

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = et_registerId.getText().toString();

                String userPw = et_registerPw.getText().toString();
                String userName = et_registerName.getText().toString();
                String userPhone = et_registerPhone.getText().toString();

                int checkPhoneNum = checkNum(userPhone);

                if (userId.equals("")){
                    Toast.makeText(RegisterActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }else if (userName.equals("")){
                    Toast.makeText(RegisterActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }else if (userPw.equals("")){
                    Toast.makeText(RegisterActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }else if (userPhone.equals("")){
                    Toast.makeText(RegisterActivity.this, "전화번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (checkPhoneNum == 0){
                    Toast.makeText(RegisterActivity.this, "번호에 숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                userId = userId + "@Zzap.com";

                firebaseAuth = FirebaseAuth.getInstance();
                databaseReference = FirebaseDatabase.getInstance().getReference("ZzapKaoTalk");

                // Firebase Auth 진행
                // 회원 생성 요청, 결과 동작 --> Firebase db에 정보 insert
                firebaseAuth.createUserWithEmailAndPassword(userId, userPw).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            profile_item userAccount = new profile_item();
                            userAccount.setIdToken(firebaseUser.getUid());
                            userAccount.setId(firebaseUser.getEmail());
                            userAccount.setName(userName);
                            userAccount.setPhone(userPhone);
                            userAccount.setList_chatRoom(0);
                            userAccount.setList_friends(0);
                            userAccount.setProfile_bg("default");
                            userAccount.setProfile_img("default");
                            userAccount.setProfile_msg("");

                            // setValue() == db에 insert
                            databaseReference.child("UserAccount").child(firebaseUser.getUid()).setValue(userAccount);

                            Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private int checkNum(String phone){
        try{
            int phoneNum = Integer.parseInt(phone);
            return phoneNum;
        }catch (Exception e){
            return 0;
        }
    }
}