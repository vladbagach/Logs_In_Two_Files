package com.example.wamitest.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Logs {
    private final String id;
    @JsonFormat(pattern = "yy-MM-dd HH:mm::ss")
    private final Date time;

    public Logs(String id, Date time) {
        this.id = id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Logs{" +
                "id='" + id + '\'' +
                ", time=" + time +
                '}';
    }
}
