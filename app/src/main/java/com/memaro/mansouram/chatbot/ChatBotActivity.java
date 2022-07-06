package com.memaro.mansouram.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.memaro.mansouram.R;

import java.util.ArrayList;
import java.util.List;

public class ChatBotActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    public static ChatBotAdapter adapter;
    public static List<ChatBotModel> adapterList = new ArrayList<>();
    EditText etMessage;
    ImageView ivSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        initViews();

        adapter = new ChatBotAdapter(adapterList, ChatBotActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatBotActivity.this, RecyclerView.VERTICAL, false));

        recyclerView.setAdapter(adapter);

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMessage();
            }
        });
    }
    private void initViews() {
        recyclerView = findViewById(R.id.chat_bot_rv);
        etMessage = findViewById(R.id.chat_bot_et);
        ivSend = findViewById(R.id.chat_bot_send);
    }

    private void getMessage() {
        String message = etMessage.getText().toString().toLowerCase();
        if (message.isEmpty()) return;
        etMessage.setText("");
        adapterList.add(new ChatBotModel().setSpeakerState(true).setMessage(message).setYesState(false));
        adapter.notifyDataSetChanged();
        analysisData(message);
        recyclerView.smoothScrollToPosition(adapterList.size());
    }

    private void analysisData(String message) {
        for (int i = 0; i < ChatBotDB.welcome.length; i++) {
            if (ChatBotDB.welcome[i].contains(message)) {
                showWelcomeMessage();
                return;
            }
        }
        if (message.equals("yes")) {
            ChatBotActivity.adapterList.add(new ChatBotModel()
                    .setMessage("Are you ready to learn more about Academic Guidance ?")
                    .setSpeakerState(false));
            return;
        } else if (message.equals("no")) {
            ChatBotActivity.adapterList.add(new ChatBotModel()
                    .setMessage("Tell me more about your needs").setSpeakerState(false));

            return;
        }
        adapterList.add(new ChatBotModel().setMessage("How can i help you ?")
                .setSpeakerState(false)
                .setYesState(false));
        adapter.notifyDataSetChanged();
    }

    private void showWelcomeMessage() {
        adapterList.add(new ChatBotModel().setMessage("Hi , Welcome to AG ChatBot ")
                .setSpeakerState(false));
        adapterList.add(new ChatBotModel().setMessage("Is this your first time ?")
                .setSpeakerState(false).setYesState(true).setNoState(true));
        adapter.notifyDataSetChanged();
    }

}
