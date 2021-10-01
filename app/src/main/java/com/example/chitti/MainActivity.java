package com.example.chitti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView chatRV;
    private EditText userMsgEdt;
    private FloatingActionButton sendmsgBtn;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModal>chatsModalArrayList;
    private chatRVAdapter chatadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatRV = findViewById(R.id.idRVchats);
        sendmsgBtn = findViewById(R.id.idfloatingactionbutton);
        userMsgEdt = findViewById(R.id.usertext);
        chatsModalArrayList = new ArrayList<>();
        chatadapter = new chatRVAdapter(chatsModalArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chatRV.setLayoutManager(manager);
        chatRV.setAdapter(chatadapter);

        sendmsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userMsgEdt.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter a valid message",Toast.LENGTH_SHORT).show();
                     return;
                }
                getresponse(userMsgEdt.getText().toString());
                userMsgEdt.setText("");
            }
        });
    }
    private void getresponse(String message){
        chatsModalArrayList.add(new ChatsModal(message,USER_KEY));
        chatadapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=159982&key=jVGkBE7I50UFYVH5&uid=[uid]&msg="+message;
        String  BASE_URL = "http://api.brainshop.ai/";
                Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<msgModal> call = retrofitAPI.getMessage(url);
        call.enqueue(new Callback<msgModal>() {
            @Override
            public void onResponse(Call<msgModal> call, Response<msgModal> response) {
                msgModal modal = response.body();
                chatsModalArrayList.add(new ChatsModal(modal.getCnt(),BOT_KEY));
                chatadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<msgModal> call, Throwable t) {
            chatsModalArrayList.add(new ChatsModal("please revert your question",BOT_KEY));
            chatadapter.notifyDataSetChanged();
            }
        });
    }
}