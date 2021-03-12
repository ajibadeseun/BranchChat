package com.branch.www;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class InnerMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<MessagesRes> itemList;
    private Context mContext;

    private static final int VIEW_TYPE_ONE = 1;
    private static final int VIEW_TYPE_TWO = 2;
    public InnerMessagesAdapter(List<MessagesRes> itemList, Context mContext) {
        this.itemList = itemList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case VIEW_TYPE_ONE:
                View items = inflater.inflate(R.layout.chat_user_one, parent, false);
                viewHolder = new ViewOneHolder(items);
                break;
            case VIEW_TYPE_TWO:
                View item = inflater.inflate(R.layout.chat_user_two, parent, false);
                viewHolder = new ViewTwoHolder(item);
                break;

        }
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessagesRes messagesRes = itemList.get(position);
        if(messagesRes.getAgent_id() != null){
            if(holder instanceof ViewOneHolder){
                configureViewOneViewHolder((ViewOneHolder)holder,position);
            }

        }
        if(messagesRes.getUser_id() != null){
            if(holder instanceof  ViewTwoHolder){
                configureViewTwoViewHolder((ViewTwoHolder)holder,position);
            }

        }
    }

    private void configureViewOneViewHolder(ViewOneHolder viewOneHolder,int position){
        MessagesRes messagesRes = itemList.get(position);

        viewOneHolder.AgentIdText.setText(messagesRes.getAgent_id());
        viewOneHolder.MessageBodyText.setText(messagesRes.getBody());
        Date date = messagesRes.getTimestamp();
        long timeInMillis = date.getTime();
        viewOneHolder.TimestampText.setText(UtilFunctions.getTimeAgo(timeInMillis));
    }

    private void configureViewTwoViewHolder(ViewTwoHolder viewTwoHolder,int position){
        MessagesRes messagesRes = itemList.get(position);

        viewTwoHolder.UserIdText.setText(messagesRes.getUser_id());
        viewTwoHolder.MessageBodyText.setText(messagesRes.getBody());
        Date date = messagesRes.getTimestamp();
        long timeInMillis = date.getTime();
        viewTwoHolder.TimestampText.setText(UtilFunctions.getTimeAgo(timeInMillis));
    }

    public class ViewOneHolder extends RecyclerView.ViewHolder {
         public TextView TimestampText;
         public TextView MessageBodyText;
         public TextView AgentIdText;
        public ViewOneHolder(View item) {
            super(item);
            TimestampText = item.findViewById(R.id.txtInfo);
            MessageBodyText = item.findViewById(R.id.txtMessage);
            AgentIdText = item.findViewById(R.id.tag);
        }
    }

    @Override
    public int getItemCount() {
        return itemList == null? 0 : itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        MessagesRes messagesRes = itemList.get(position);
        if(messagesRes.getAgent_id() != null){
            return VIEW_TYPE_ONE;
        }
        else  if(messagesRes.getUser_id() != null){
            return VIEW_TYPE_TWO;
        }
        else {
            return 0;
        }
    }


    public class ViewTwoHolder extends RecyclerView.ViewHolder {
        public TextView TimestampText;
        public TextView MessageBodyText;
        public TextView UserIdText;
        public ViewTwoHolder(View item) {
            super(item);
            TimestampText = item.findViewById(R.id.txtInfo);
            MessageBodyText = item.findViewById(R.id.txtMessage);
            UserIdText = item.findViewById(R.id.tag);
        }
    }
}
