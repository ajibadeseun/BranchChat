package com.branch.www;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    @LayoutRes
    protected abstract int layoutRes();

    ProgressDialog progressDialog ;
    //hiding keyboard
    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected  void showProgress(String message){
        try{
            if(progressDialog == null){
                progressDialog = new ProgressDialog(this);
            }
            progressDialog.show(message);
        }
        catch (Exception e){

        }
    }
    protected void hideProgress(){
        try{
            if(progressDialog != null){
                progressDialog.cancel();
            }
        }catch (Exception e){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
        ButterKnife.bind(this);
    }
}