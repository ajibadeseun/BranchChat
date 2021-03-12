package com.branch.www;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;

public class StateLiveData<T> extends MutableLiveData<StatusData<T>> {
    /**
     * Use this to put the Data on a LOADING Status
     */
    public void postLoading() {
        postValue(new StatusData<T>().loading());
    }

    /**
     * Use this to put the Data on a ERROR DataStatus
     * @param throwable the error to be handled
     */
    public void postError(Throwable throwable) {
        postValue(new StatusData<T>().error(throwable));
    }
    public void postError(HashMap<String,String> errors) {

        postValue(new StatusData<T>().error(errors));
    }


    /**
     * Use this to put the Data on a SUCCESS DataStatus
     * @param data
     */
    public void postSuccess(T data) {
        postValue(new StatusData<T>().success(data));
    }

    /**
     * Use this to put the Data on a COMPLETE DataStatus
     */
    public void postComplete() {
        postValue(new StatusData<T>().complete());
    }


}
