package com.memaro.mansouram.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.memaro.mansouram.R;

//import androidmads.library.qrgenearator.QRGEncoder;

public class QRCodeGenerator extends AppCompatActivity {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    String TAG = "GeneratorQRCode";
    String lectureName;
    EditText editText;

    Bitmap bitmap;
//    QRGEncoder generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_genearator);

        editText = findViewById(R.id.qr_et_lecture_name);

    }

    public void generate(View view) {
        lectureName = editText.getText().toString();
        if (lectureName.length() > 0) pushLecture();
    }

    private void pushLecture() {
        final String lectureId = ref.push().getKey();
        LectureModel lecture = new LectureModel(lectureName, lectureId);

        ref.child("lectures").child(lectureId).setValue(lecture)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new
                                Intent(QRCodeGenerator.this, QRCodeAttendants.class);
                        intent.putExtra("lectureId", lectureId);
                        startActivity(intent);
                    }
                });

    }

    public void openLecturesActivity(View view) {

    }

}
