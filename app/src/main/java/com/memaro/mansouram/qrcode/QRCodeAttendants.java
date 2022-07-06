package com.memaro.mansouram.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.zxing.WriterException;
import com.memaro.mansouram.R;

import java.util.ArrayList;

//import androidmads.library.qrgenearator.QRGContents;
//import androidmads.library.qrgenearator.QRGEncoder;

public class QRCodeAttendants extends AppCompatActivity {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ValueEventListener listener;

    ImageView ivQRCode;
    Bitmap bitmapQRCode;
    ListView listView;
    ArrayList<AttendLectureModel> arrayList = new ArrayList();
    AttendLectureAdapter adapter;

//    QRGEncoder generator;
    private String lectureId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_attendants);

        ivQRCode = findViewById(R.id.qr_code_attendants_iv);
        listView = findViewById(R.id.attendants_list_view);

        showQRCode();

        adapter = new AttendLectureAdapter
                (this, R.layout.row_student_attend, arrayList);
        listView.setAdapter(adapter);

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("students")
                        .getChildren()) {

                    arrayList.add(snapshot.getValue(AttendLectureModel.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        ref.child("lectures").child(lectureId).addValueEventListener(listener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        ref.removeEventListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ref.removeEventListener(listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ref.removeEventListener(listener);
    }

    private void showQRCode() {
        Intent intent = getIntent();
        lectureId = intent.getStringExtra("lectureId");
        Toast.makeText(this, "" + lectureId, Toast.LENGTH_SHORT).show();

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int s = width < height ? width : height;
        s = s * 3 / 4;
//        generator = new QRGEncoder(lectureId, null, QRGContents.Type.TEXT, s);
//        try {
//            bitmapQRCode = generator.encodeAsBitmap();
//            ivQRCode.setImageBitmap(bitmapQRCode);
//
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
    }

    public static class AttendLectureModel {
        String userId, username;

        public AttendLectureModel() {
        }

        public AttendLectureModel(String userId, String username) {
            this.userId = userId;
            this.username = username;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    private class AttendLectureAdapter extends ArrayAdapter {

        public AttendLectureAdapter(Context context, int resource, ArrayList<AttendLectureModel> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.row_student_attend, parent, false);

            TextView textView = view.findViewById(R.id.row_student_attend_tv);

            AttendLectureModel model = (AttendLectureModel) getItem(position);

            textView.setText(model.getUsername());

            return view;

        }
    }
}