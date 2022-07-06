package com.memaro.mansouram;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.memaro.mansouram.admin.chatbot.ChatBotResults;
import com.memaro.mansouram.authentication.SignIn;
import com.memaro.mansouram.chatbot.ChatBotActivity;

import com.memaro.mansouram.profile.StudentProfile;
import com.memaro.mansouram.qrcode.QRCodeGenerator;
import com.memaro.mansouram.qrcode.QRCodeScanner;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private DrawerLayout drawer;
    private NavigationView navigationView;

    private TextView tvNavHeaderEmail;
    private TextView tvNavHeaderUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initViews() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tvNavHeaderEmail = headerView.findViewById(R.id.nav_header_email);
        tvNavHeaderUsername = headerView.findViewById(R.id.nav_header_username);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAuthentication();
    }

    private void checkAuthentication() {
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, SignIn.class));
            finish();
        } else {
            tvNavHeaderEmail.setText(auth.getCurrentUser().getEmail());
            tvNavHeaderUsername.setText(auth.getCurrentUser().getDisplayName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            auth.signOut();
            checkAuthentication();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_qr:
                startActivity(new Intent(MainActivity.this, QRCodeScanner.class));
                break;

            case R.id.nav_chat_bot_results:
                startActivity(new Intent(MainActivity.this, ChatBotResults.class));
                break;

            case R.id.nav_create_attend:
                startActivity(new Intent(MainActivity.this, QRCodeGenerator.class));
                break;

            case R.id.nav_sp:
                startActivity(new Intent(MainActivity.this, StudentProfile.class));
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startQuiz(View view) {
        startActivity(new Intent(MainActivity.this, ChatBotActivity.class));
    }
}
