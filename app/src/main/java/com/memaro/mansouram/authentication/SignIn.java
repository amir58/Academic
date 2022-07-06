package com.memaro.mansouram.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.memaro.mansouram.MainActivity;
import com.memaro.mansouram.R;

public class SignIn extends AppCompatActivity implements View.OnClickListener {


    private EditText etEmail, etPassword;
    private Button btnLogin;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews(); // ميثود تعريف الويدجت
    }

    private void initViews() {
        etEmail = findViewById(R.id.sign_in_email);
        etPassword = findViewById(R.id.sign_in_password);
        btnLogin = findViewById(R.id.sign_in_button);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button) {
            signIn(); // اون كليك
        }
    }

    private void signIn() {
        // بنسحب الداتا فالسطور الجاية
        String email, password;
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        // بنعمل Validation على الداتا لو فى EditText فاضى بيقف
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all data", Toast.LENGTH_SHORT).show();
            return; // ده الامر الى بنوقف بيه الداتا
        }

        // الميثود الى بنبعتلها الايميل و الباسورد عشان نسجل
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignIn.this, "Welcome", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignIn.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(SignIn.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void openSignUp(View view) {
        startActivity(new Intent(SignIn.this, SignUp.class));
    }
}
