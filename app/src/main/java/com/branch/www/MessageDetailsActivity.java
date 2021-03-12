package com.branch.www;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

public class MessageDetailsActivity extends BaseActivity {
    @BindView(R.id.list)
    RecyclerView recyclerView;
    BranchViewModel branchViewModel;
    @BindView(R.id.edit)
    EditText EditMessage;
    @BindView(R.id.fab)
    FloatingActionButton SendBtn;
    int thread_id;
    @Override
    protected int layoutRes() {
        return R.layout.activity_message_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thread_id = getIntent().getIntExtra("thread_id",0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        branchViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(BranchViewModel.class);

        retrieveChats();

        EditMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             if(charSequence.toString().isEmpty()){
                 SendBtn.setEnabled(false);
             }
             else{
                 SendBtn.setEnabled(true);
             }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        SendBtn.setOnClickListener(v->{
            String messageBody = EditMessage.getText().toString().trim();
            CreateMessageRequest createMessageRequest = new CreateMessageRequest(thread_id,messageBody);
            sendMessage(createMessageRequest);
        });

    }
    private void sendMessage(CreateMessageRequest createMessageRequest){
        branchViewModel.sendMessage(createMessageRequest,new UtilFunctions().getToken(this));
        branchViewModel.pushMessageStateLiveData.observe(this,response->{
            switch (response.getStatus()) {
                case LOADING:
                    //Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show();
                    //showProgress(getString(R.string.loading));
                    break;
                case ERROR:
                    hideProgress();
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                    break;
                case SUCCESS:
                    EditMessage.setText("");
                    retrieveChats();
                    break;
            }
        });

    }
    private void retrieveChats(){
        branchViewModel.retrieveMessages(new UtilFunctions().getToken(this));
        branchViewModel.messagesResStateLiveData.observe(this,response->{
            switch (response.getStatus()) {
                case LOADING:
                    //Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show();
                    //showProgress(getString(R.string.loading));
                    break;
                case ERROR:
                    hideProgress();
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                    break;
                case SUCCESS:
                    hideProgress();
                    List<MessagesRes> messagesResList = response.getData();
                    Collections.sort(messagesResList, new Comparator<MessagesRes>() {
                        public int compare(MessagesRes m1, MessagesRes m2) {
                            return m1.getTimestamp().compareTo(m2.getTimestamp());
                        }
                    });

                    InnerMessagesAdapter userAdapter = new InnerMessagesAdapter(messagesResList, this);
                    recyclerView.setAdapter(userAdapter);
                    recyclerView.scrollToPosition(userAdapter.getItemCount() - 1);
                    break;
            }
        });
    }
}