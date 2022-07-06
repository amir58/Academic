package com.memaro.mansouram.chatbot;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.memaro.mansouram.R;

import java.util.List;


public class ChatBotAdapter extends RecyclerView.Adapter<ChatBotAdapter.Holder> {

    List<ChatBotModel> list;
    Context context;

    public ChatBotAdapter(List<ChatBotModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.chat_bot_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        ChatBotModel model = list.get(position);

        if (model.isSpeakerState()) {
            holder.linearLayout.setGravity(Gravity.END);
        } else {
            holder.linearLayout.setGravity(Gravity.START);
        }

        holder.tvMessage.setText(model.getMessage());

        if (model.isYesState()) {
            holder.btnYes.setVisibility(View.VISIBLE);
            holder.btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChatBotActivity.adapterList.add(new ChatBotModel().setMessage("Yes").setSpeakerState(true));
                    ChatBotActivity.adapterList.add(new ChatBotModel()
                            .setMessage("Are you ready to learn more about Academic Guidance ?")
                            .setSpeakerState(false));
                    ChatBotActivity.adapter.notifyDataSetChanged();
                    ChatBotActivity.recyclerView.smoothScrollToPosition(list.size());
                }
            });

        } else {
            holder.btnYes.setVisibility(View.GONE);
        }

        if (model.isNoState()) {
            holder.btnNo.setVisibility(View.VISIBLE);
            holder.btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChatBotActivity.adapterList.add(new ChatBotModel().setMessage("No").setSpeakerState(true));
                    ChatBotActivity.adapterList.add(new ChatBotModel()
                            .setMessage("Tell me more about your needs").setSpeakerState(false));
                    ChatBotActivity.adapter.notifyDataSetChanged();
                    ChatBotActivity.recyclerView.smoothScrollToPosition(list.size());
                }
            });

        } else {
            holder.btnNo.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView tvMessage;
        Button btnYes, btnNo, btnLectures, btnSpecialties;

        public Holder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.chat_bot_row_linear);
            tvMessage = itemView.findViewById(R.id.chat_bot_row_tv);
            btnLectures = itemView.findViewById(R.id.chat_bot_row_show_lectures);
            btnSpecialties = itemView.findViewById(R.id.chat_bot_row_show_specialties);
            btnYes = itemView.findViewById(R.id.chat_bot_row_yes);
            btnNo = itemView.findViewById(R.id.chat_bot_row_no);
        }
    }


}
