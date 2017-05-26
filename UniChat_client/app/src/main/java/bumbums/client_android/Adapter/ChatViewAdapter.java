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
import aboullaite.util.Constants;
import bumbums.client_android.Client;
import bumbums.client_android.R;

/**
 * Created by hansb on 2017-05-26.
 */

public class ChatViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Data> chatDatas;
    private User user;

    public static final int ITEM_TYPE_INCOMING = 0;
    public static final int ITEM_TYPE_OUTGOING = 1;

    public ChatViewAdapter(ArrayList<Data> chatDatas,User user){
        this.chatDatas = chatDatas;
        this.user = user;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == ITEM_TYPE_INCOMING) {
            return new InComingViewHolder(inflater.inflate(R.layout.list_chat_incoming,parent,false));
        }
        else if(viewType == ITEM_TYPE_OUTGOING) {
            return new OutGoingViewHolder(inflater.inflate(R.layout.list_chat_outgoing,parent,false));
        }
        else{//다른거 추가해야함

        }
        return null;
    }

   public void swapData(ArrayList<Data> chatDatas) {
        this.chatDatas = chatDatas;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        Data data = chatDatas.get(position);
        if(itemType == ITEM_TYPE_INCOMING){
            ((InComingViewHolder)holder).msg.setText(data.getMsg());
            ((InComingViewHolder)holder).nickname.setText(data.getSendor());
        }
        else if(itemType == ITEM_TYPE_OUTGOING){
            ((OutGoingViewHolder)holder).msg.setText(data.getMsg());
            ((OutGoingViewHolder)holder).nickname.setText(data.getSendor());
        }
    }

    @Override
    public int getItemCount() {
        if (chatDatas == null)
            return 0;
        else
            return chatDatas.size();
    }

    public class InComingViewHolder extends RecyclerView.ViewHolder {
        TextView msg;
        TextView nickname;

        public InComingViewHolder(View itemVIew) {
            super(itemVIew);
            msg = (TextView) itemView.findViewById(R.id.tv_list_msg);
            nickname = (TextView) itemView.findViewById(R.id.tv_list_nickname);
        }
    }
    public class OutGoingViewHolder extends RecyclerView.ViewHolder {
        TextView msg;
        TextView nickname;

        public OutGoingViewHolder(View itemVIew) {
            super(itemVIew);
            msg = (TextView) itemView.findViewById(R.id.tv_list_msg);
            nickname = (TextView) itemView.findViewById(R.id.tv_list_nickname);
        }
    }
    @Override
    public int getItemViewType(int position) {
        int type = chatDatas.get(position).getType() ;
       String sendor =  chatDatas.get(position).getSendor() ;

        if(type == Constants.TYPE_MSG){
            if(sendor==null)return ITEM_TYPE_INCOMING;
            
            if(sendor.equals(Client.nickname))return ITEM_TYPE_OUTGOING;
            else return ITEM_TYPE_INCOMING;
        }
        else if( type == Constants.TYPE_WHISPER){
            //나중에 구현
        }
        return super.getItemViewType(position);
    }
}
