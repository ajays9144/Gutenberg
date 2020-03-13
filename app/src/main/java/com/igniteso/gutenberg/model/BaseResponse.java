package com.igniteso.gutenberg.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse<T>
{
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("next")
    @Expose
    private String nextURL;
    @SerializedName("previous")
    @Expose
    private String previousURL;
    @SerializedName("results")
    @Expose
    private T result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNextURL() {
        return nextURL;
    }

    public void setNextURL(String nextURL) {
        this.nextURL = nextURL;
    }

    public String getPreviousURL() {
        return previousURL;
    }

    public void setPreviousURL(String previousURL) {
        this.previousURL = previousURL;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
