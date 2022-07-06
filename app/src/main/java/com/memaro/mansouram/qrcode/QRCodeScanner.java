package com.memaro.mansouram.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.zxing.Result;
import com.memaro.mansouram.profile.StudentProfile;

//import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeScanner extends AppCompatActivity
//        implements ZXingScannerView.ResultHandler
{

//    ZXingScannerView scannerView;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String lectureId;
    private int request = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions
                    (this, new String[]{Manifest.permission.CAMERA}, request);
        }

//        scannerView = new ZXingScannerView(QRCodeScanner.this);

        if (isNetworkAvailable()) {
//            setContentView(scannerView);

        } else {
            showNoInternetDialog();
        }
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Warning")
                .setMessage("No internet connection")
                .setPositiveButton("Retry Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        recreate();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

//    @Override
//    public void handleResult(Result rawResult) {
//        //MainActivity.tvQRResult.setText(rawResult.getText());
//        QRCodeAttendants.AttendLectureModel model =
//                new QRCodeAttendants.AttendLectureModel(user.getUid(), user.getDisplayName());
//        lectureId = rawResult.getText();
//
//        ref.child("lectures").child(lectureId)
//                .child("students").child(user.getUid()).setValue(model)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        getLectureName();
//                    }
//                });
//    }

    private void getLectureName() {
        ref.child("lectures").child(lectureId).child("lectureName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lectureName = dataSnapshot.getValue(String.class);
                pushInStudentProfile(lectureName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void pushInStudentProfile(String lectureName) {
        ref.child("users").child(user.getUid()).child("lectures")
                .push().setValue(lectureName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(QRCodeScanner.this, "Attend Success", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(QRCodeScanner.this, StudentProfile.class));
                    }
                });

    }

    @Override
    protected void onPause() {
        super.onPause();
//        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNetworkAvailable()) {
//            scannerView.setResultHandler(QRCodeScanner.this);
//            scannerView.startCamera();
        } else {
            showNoInternetDialog();
        }
    }

}