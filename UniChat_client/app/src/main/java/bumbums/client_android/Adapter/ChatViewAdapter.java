package bumbums.client_android.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import aboullaite.Data;
import aboullaite.User;
import bumbums.client_android.R;

/**
 * Created by hansb on 2017-05-26.
 */

public class ChatViewAdapter extends RecyclerView.Adapter<ChatViewAdapter.ChatViewHolder> {


    private ArrayList<Data> chatDatas;
    private User user;

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem  = R.layout.list_chat_incoming;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        ChatViewHolder viewHolder = new ChatViewHolder(view);
        return viewHolder;
    }
    void swapData(ArrayList<Data> chatDatas){
        this.chatDatas = chatDatas;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return chatDatas.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{
        TextView msg;
        TextView nickname;

        public ChatViewHolder(View itemVIew){
            super(itemVIew);
            msg = (TextView)itemView.findViewById(R.id.tv_list_msg);
            nickname = (TextView)itemView.findViewById(R.id.tv_list_nickname);
        }
        void bind(int listindex){
            Data data = chatDatas.get(listindex);
            msg.setText(data.getMsg());
            nickname.setText(data.getSendor());
        }
    }
}
