package com.branch.www;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.username_layout)
    TextInputLayout UserNameLayout;
    @BindView(R.id.username_edit)
    TextInputEditText UserNameEdit;
    @BindView(R.id.password_layout)
    TextInputLayout PasswordLayout;
    @BindView(R.id.password_edit)
    TextInputEditText PasswordEdit;
    @BindView(R.id.login_btn)
    Button LoginBtn;
    BranchViewModel branchViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   UserNameLayout.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        PasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 PasswordLayout.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

       // branchViewModel = new ViewModelProvider(this).get(BranchViewModel.class);
        branchViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(BranchViewModel.class);

        //Clicking Login button
        LoginBtn.setOnClickListener(v->{
             String password = UserNameEdit.getText().toString().trim();
             String username = PasswordEdit.getText().toString().trim();
             LoginRequest loginRequest = new LoginRequest(username,password);
             logUserIn(loginRequest);
        });

    }
    private void logUserIn(LoginRequest loginRequest){
        branchViewModel.signIn(loginRequest);
        branchViewModel.loginResStateLiveData.observe(this,response->{
            switch (response.getStatus()) {
                case LOADING:
                    //Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show();
                    showProgress(getString(R.string.loading));
                    break;
                case ERROR:
                    hideProgress();
                    Throwable e = response.getError();
                    if (e != null) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        HashMap<String, String> errors = response.getErrors();
                        assert errors != null;
                        for (HashMap.Entry<String, String> error : errors.entrySet()) {
                            switch (error.getKey()) {
                                case Configs.PASSWORD:
                                    PasswordLayout.setError(error.getValue());
                                    break;
                                case Configs.EMAIL:
                                    UserNameLayout.setError(error.getValue());
                                    break;
                                default:
                                    System.out.println("default");
                            }
                        }

                    }

                    break;
                case SUCCESS:
                    hideProgress();
                    LoginRes loginRes = response.getData();
                    saveToken(loginRes);
                    Intent intent  = new Intent(this,MessagesActivity.class);
                    startActivity(intent);

                    break;
            }
        });
    }

    public void saveToken(LoginRes loginRes){
        SharedPreferences sharedPreferences = getSharedPreferences("Keys", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token",loginRes.getAuth_token());
        editor.apply();
    }
    public String getToken(){
        SharedPreferences sharedPreferences = getSharedPreferences("Keys", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token","");
    }

}