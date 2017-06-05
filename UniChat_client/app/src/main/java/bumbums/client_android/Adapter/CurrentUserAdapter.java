package bumbums.client_android.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import aboullaite.User;
import aboullaite.util.Constants;
import bumbums.client_android.R;

/**
 * Created by hansb on 2017-05-26.
 */

public class CurrentUserAdapter extends RecyclerView.Adapter<CurrentUserAdapter.UserViewHolder> {

    private ArrayList<User> currentUser;
    private Context mContext;
    private OnClickHandler mOnClickHandler;

    public CurrentUserAdapter(ArrayList<User> currentUser,OnClickHandler onClickHandler){
        this.currentUser = currentUser;
        mOnClickHandler = onClickHandler;
    }

    public interface OnClickHandler{
        void onClickUser(String email);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem  = R.layout.list_user;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(currentUser==null)return 0;
        else
            return currentUser.size();
    }

    public void swapData(ArrayList<User> currentUser){
        this.currentUser = currentUser;
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        TextView nickname;
        TextView email;
        ImageView photo;
        LinearLayout frame;
        public UserViewHolder(View itemView) {
            super(itemView);
            nickname = (TextView)itemView.findViewById(R.id.tv_row_grid_name);
            email = (TextView)itemView.findViewById(R.id.tv_row_grid_email);
            photo = (ImageView)itemView.findViewById(R.id.iv_profile);
            frame = (LinearLayout)itemView.findViewById(R.id.ll_user);
        }
        void bind(final int listIndex){
            User user = currentUser.get(listIndex);
            nickname.setText(user.getNickname());
            email.setText(user.getId());
            if(email.getText().equals(Constants.DonggukBotEmail)){
                photo.setImageResource(R.drawable.ic_donggukbot);
            }
            frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickHandler.onClickUser(currentUser.get(listIndex).getId());
                }
            });
        }


    }
}
