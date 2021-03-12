package com.branch.www;

import java.util.Date;

public class MessagesRes {
    private int id;
    private int thread_id;
    private String user_id;
    private String body;
    private Date timestamp;
    private String agent_id;

    public MessagesRes(int id, int thread_id, String user_id, String body, Date timestamp, String agent_id) {
        this.id = id;
        this.thread_id = thread_id;
        this.user_id = user_id;
        this.body = body;
        this.timestamp = timestamp;
        this.agent_id = agent_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }
}
