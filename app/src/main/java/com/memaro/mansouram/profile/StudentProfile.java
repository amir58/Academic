package com.memaro.mansouram.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.memaro.mansouram.R;

import java.util.ArrayList;

public class StudentProfile extends AppCompatActivity {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private TextView tvUsername, tvEmail;
    private ListView listView;
    private ArrayList<String> listOfLectures = new ArrayList<>();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        initViews();

        tvUsername.setText(user.getDisplayName());
        tvEmail.setText(user.getEmail());

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listOfLectures);
        listView.setAdapter(adapter);

        ref.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOfLectures.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("lectures").getChildren()) {
                        listOfLectures.add(snapshot.getValue(String.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initViews() {
        tvUsername = findViewById(R.id.sp_tv_username);
        tvEmail = findViewById(R.id.sp_tv_email);
        listView = findViewById(R.id.sp_list_view);
    }
}
