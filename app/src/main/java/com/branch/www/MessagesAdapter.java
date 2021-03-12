package com.branch.www;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    private List<MessagesRes> mList;
    private Context mContext;
    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds
    public MessagesAdapter(List<MessagesRes> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View menus = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new MessagesAdapter.ViewHolder(menus);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessagesRes messagesRes = mList.get(position);
        holder.bodyText.setText(messagesRes.getBody());
        String name = messagesRes.getUser_id();
        holder.nameText.setText(name);
        long millis = messagesRes.getTimestamp().getTime();

        holder.timeStampText.setText(new UtilFunctions().getTimeAgo(millis));
        holder.layout.setOnClickListener(view -> {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,holder.pics,"profileImg");
            Bundle bundle = new Bundle();
            bundle.putInt("thread_id",messagesRes.getThread_id());
            Intent intent = new Intent(mContext,MessageDetailsActivity.class);
            intent.putExtras(bundle);
            mContext.startActivity(intent,options.toBundle());
        });
       // setFadeAnimation(holder.layout);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layout;
        public CircleImageView pics;
        public TextView nameText;
        public TextView bodyText;
        public TextView timeStampText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            pics = itemView.findViewById(R.id.pics);
            nameText = itemView.findViewById(R.id.name);
            bodyText = itemView.findViewById(R.id.body);
            timeStampText = itemView.findViewById(R.id.timestamp);
        }
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
}
