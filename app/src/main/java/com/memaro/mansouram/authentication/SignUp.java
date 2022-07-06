package com.memaro.mansouram.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.memaro.mansouram.R;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText etUsername, etEmail, etPassword;
    private Button btnSignUp;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
    }

    private void initViews() {
        etUsername = findViewById(R.id.sign_up_username);
        etEmail = findViewById(R.id.sign_up_email);
        etPassword = findViewById(R.id.sign_up_password);
        btnSignUp = findViewById(R.id.sign_up_button);

        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_up_button) {
            signUp();
        }
    }

    private void signUp() {
        final String username, email, password;
        username = etUsername.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all data", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser user = task.getResult().getUser();
                    UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username).build();

                    user.updateProfile(profile).addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    createUserData(username, user.getUid());
                                }
                            });

                } else {
                    Log.e(TAG, "onComplete: " + task.getException().getLocalizedMessage());
                    Toast.makeText(SignUp.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUserData(String username, String uid) {
        UserModel userData = new UserModel(username, uid);
        reference.child("users").child(uid).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "User created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                    Log.e(TAG, "onComplete: " + task.getException().getLocalizedMessage());
                    Toast.makeText(SignUp.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
