package com.example.wamitest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class LogDto implements Serializable {
    private String id;
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private Date time;

    public LogDto(String id, Date time) {
        this.id = id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
