package com.example.movieapp.model;

import java.io.Serializable;

public class Cast implements Serializable {
    private String Actor;
    private String PicUrl;

    public Cast() {
    }

    public String getActor() {
        return Actor;
    }

    public void setActor(String actor) {
        Actor = actor;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
