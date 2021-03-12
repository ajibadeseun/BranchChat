package com.branch.www;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

public class MessagesActivity extends BaseActivity {

    BranchViewModel branchViewModel;
    @BindView(R.id.list)
    RecyclerView recyclerView;

    @Override
    protected int layoutRes() {
        return R.layout.activity_messages;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        branchViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(BranchViewModel.class);
        populateMessages();

    }
    private void populateMessages(){
        String token = new UtilFunctions().getToken(this);
        branchViewModel.retrieveMessages(token);
        branchViewModel.messagesResStateLiveData.observe(this,response->{
            switch (response.getStatus()) {
                case LOADING:
                    //Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show();
                    showProgress(getString(R.string.loading));
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
                    MessagesAdapter userAdapter = new MessagesAdapter(messagesResList, this);
                    recyclerView.setAdapter(userAdapter);
                    break;
            }
        });
    }
}