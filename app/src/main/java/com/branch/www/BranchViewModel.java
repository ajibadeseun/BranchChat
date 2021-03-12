package com.branch.www;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.HashMap;
import java.util.List;

public class BranchViewModel extends AndroidViewModel {
    public BranchRepository branchRepository;

    StateLiveData<LoginRes> loginResStateLiveData = new StateLiveData<>();
    StateLiveData<List<MessagesRes>> messagesResStateLiveData = new StateLiveData<>();
    StateLiveData<MessagesRes> pushMessageStateLiveData = new StateLiveData<>();


    HashMap<String, String> errors = new HashMap<>();

    public BranchViewModel(@NonNull Application application) {
        super(application);
        this.branchRepository = new BranchRepository();
    }

    public void signIn(LoginRequest loginRequest){
        if(ValidateUser(loginRequest)){
            loginResStateLiveData = branchRepository.login(loginRequest);
        }
        else{
            loginResStateLiveData.postError(errors);
        }

    }
    private boolean ValidateUser(LoginRequest loginRequest){
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
            errors.put(Configs.EMAIL, "Username field cannot be empty");

        }
        if(loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()){
            errors.put(Configs.PASSWORD, "Username field cannot be empty");
        }
        return errors.isEmpty();
    }
    public  void retrieveMessages(String token){
        messagesResStateLiveData = branchRepository.retrieveMessages(token);
    }
    public  void sendMessage(CreateMessageRequest createMessageRequest,String token){
        if(ValidateMessage(createMessageRequest)){
            pushMessageStateLiveData = branchRepository.pushMessage(createMessageRequest,token);
        }
        else{
            pushMessageStateLiveData.postError(errors);
        }
    }
    private boolean ValidateMessage(CreateMessageRequest createMessageRequest){
        errors.clear();
        if(createMessageRequest.getThread_id() == 0){
            errors.put(Configs.MESSAGE, "Thread id field cannot be zero");
        }
        if(createMessageRequest.getBody() == null|| createMessageRequest.getBody().isEmpty()){
            errors.put(Configs.MESSAGE_BODY, "Message field cannot be empty");
        }
        return errors.isEmpty();
    }
}
