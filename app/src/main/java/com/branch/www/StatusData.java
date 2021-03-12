package com.branch.www;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class StatusData<T> {
    @NonNull
    private DataStatus status;

    @Nullable
    private T data;

    @Nullable
    private Throwable error;
    @Nullable
    private HashMap<String,String> errors;


    public StatusData() {
        this.status = DataStatus.CREATED;
        this.data = null;
        this.error = null;
    }

    public StatusData<T> loading() {
        this.status = DataStatus.LOADING;
        this.data = null;
        this.error = null;
        return this;
    }

    public StatusData<T> success(@NonNull T data) {
        this.status = DataStatus.SUCCESS;
        this.data = data;
        this.error = null;
        return this;
    }

    public StatusData<T> error(@NonNull Throwable error) {
        this.status = DataStatus.ERROR;
        this.data = null;
        this.error = error;
        return this;
    }

    public StatusData<T> error(@NonNull HashMap<String,String> error) {
        this.status = DataStatus.ERROR;
        this.data = null;
        this.errors = error;
        return this;
    }

    public StatusData<T> complete() {
        this.status = DataStatus.COMPLETE;
        return this;
    }

    @NonNull
    public DataStatus getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }
    @Nullable
    public HashMap<String,String> getErrors(){
        return errors;
    }

    public enum DataStatus {
        CREATED,
        SUCCESS,
        ERROR,
        LOADING,
        COMPLETE
    }
}
