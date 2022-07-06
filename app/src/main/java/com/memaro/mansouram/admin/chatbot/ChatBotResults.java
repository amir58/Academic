package com.memaro.mansouram.admin.chatbot;


import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.memaro.mansouram.R;

public class ChatBotResults extends AppCompatActivity {

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot_results);

        listView = findViewById(R.id.chat_bot_results_list_view);

    }

}
