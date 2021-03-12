package com.branch.www;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BranchRepository {
    StateLiveData<LoginRes> loginResStateLiveData = new StateLiveData<>();
    StateLiveData<List<MessagesRes>> messagesResStateLiveData = new StateLiveData<>();
    StateLiveData<MessagesRes> pushMessageStateLiveData = new StateLiveData<>();

    public StateLiveData<LoginRes> login(LoginRequest loginRequest) {
        loginResStateLiveData.postLoading();
        BranchApiInterface branchApiInterface = RetrofitInstance.getApiService();
        Call<LoginRes> loginResCall = branchApiInterface.login(loginRequest);
        loginResCall.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                if(response.isSuccessful()){
                    loginResStateLiveData.postSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {
                loginResStateLiveData.postError(t);
            }
        });
        return loginResStateLiveData;
    }

    public StateLiveData<List<MessagesRes>> retrieveMessages(String token){
        messagesResStateLiveData.postLoading();
        BranchApiInterface branchApiInterface = RetrofitInstance.getApiService();
        Call<List<MessagesRes>> messagesResCall = branchApiInterface.getMessages(token);
        messagesResCall.enqueue(new Callback<List<MessagesRes>>() {
            @Override
            public void onResponse(Call<List<MessagesRes>> call, Response<List<MessagesRes>> response) {
                if(response.isSuccessful()){
                    messagesResStateLiveData.postSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<MessagesRes>> call, Throwable t) {
                loginResStateLiveData.postError(t);
            }
        });
        return messagesResStateLiveData;
    }
    public  StateLiveData<MessagesRes> pushMessage(CreateMessageRequest createMessageRequest,String token){
        pushMessageStateLiveData.postLoading();
        BranchApiInterface branchApiInterface = RetrofitInstance.getApiService();
        Call<MessagesRes> pushMessageCall = branchApiInterface.sendMessage(createMessageRequest,token);
        pushMessageCall.enqueue(new Callback<MessagesRes>() {
            @Override
            public void onResponse(Call<MessagesRes> call, Response<MessagesRes> response) {
                if(response.isSuccessful()){
                    pushMessageStateLiveData.postSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<MessagesRes> call, Throwable t) {
              pushMessageStateLiveData.postError(t);
            }
        });
        return  pushMessageStateLiveData;
    }
}
