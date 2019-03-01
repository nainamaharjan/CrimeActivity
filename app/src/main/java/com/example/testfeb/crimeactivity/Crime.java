package com.example.testfeb.crimeactivity;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID id;
    private String title;
    private Date date;
    private Boolean solved=false;
    private String crimeSuspect;

    public String getCrimeSuspect() {
        return crimeSuspect;
    }

    public void setCrimeSuspect(String crimeSuspect) {
        this.crimeSuspect = crimeSuspect;
    }

    public Crime(UUID id) {
        this.id = id;
        this.date = new Date();
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    private Boolean result;

    public Crime() {
        id = UUID.randomUUID();
        date = new Date();

    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getSolved() {
        return solved;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }
}
