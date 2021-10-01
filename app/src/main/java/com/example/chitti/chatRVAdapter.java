package com.example.chitti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class chatRVAdapter extends RecyclerView.Adapter {
    private ArrayList<ChatsModal> chatsModalArrayList;
    private Context context;

    public chatRVAdapter(ArrayList<ChatsModal> chatsModalArrayList, Context context) {
        this.chatsModalArrayList = chatsModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch(viewType){
            case 0:
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg_rv_item,parent,false);
            return new UserViewholder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg_rv_item,parent,false);
                return new UserViewholder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ChatsModal chatsModal = chatsModalArrayList.get(position);
    switch ((chatsModal.getSender())){
        case "user":
            ((UserViewholder)holder).userTV.setText(chatsModal.getMessage());
            break;
        case "bot" :
            ((BotViewholder)holder).botTV.setText(chatsModal.getMessage());
            break;
    }
    }


    @Override
    public int getItemViewType(int position){
        switch(chatsModalArrayList.get(position).getSender()){
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }
    @Override
    public int getItemCount() {
        return chatsModalArrayList.size();
    }

    public static class UserViewholder extends RecyclerView.ViewHolder{
        TextView userTV;
        public UserViewholder(@NonNull View itemView) {
            super(itemView);
            userTV = itemView.findViewById(R.id.idTVuser);
        }
    }

    public static class BotViewholder extends RecyclerView.ViewHolder{
        TextView botTV;
        public BotViewholder(@NonNull View itemView) {
            super(itemView);
            botTV = itemView.findViewById(R.id.idTVbot);
        }
    }
}
