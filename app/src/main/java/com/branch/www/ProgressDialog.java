package com.branch.www;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

public class ProgressDialog {
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    private FragmentActivity mActivity;
    public ProgressDialog(FragmentActivity activity){
     mActivity = activity;
     builder = new AlertDialog.Builder(activity);
    }
    public void show(String message){
        try{
            View view = LayoutInflater.from(mActivity).inflate(R.layout.loading_layout,null);
            builder.setView(view);
            ProgressBar loader = view.findViewById(R.id.loader_img);
            TextView messageText = view.findViewById(R.id.message_text);
            messageText.setText(message);
            builder.setCancelable(false);
            alertDialog = builder.create();
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.TRANSPARENT);
            alertDialog.getWindow().setBackgroundDrawable(gradientDrawable);
            alertDialog.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void cancel(){
        try{
            if(alertDialog != null){
                if(alertDialog.isShowing())
                    alertDialog.dismiss();
                alertDialog.cancel();
                alertDialog = null;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
