package com.ioa.msw.rss;

import com.google.gson.annotations.SerializedName;

public class Enclosure {

    @SerializedName("@url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
