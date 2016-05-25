package com.aiwan.ace.aiwan.Imservice.entity;

/**
 * Created by ACE on 2016/3/16.
 */
public class SearchElement {
    public int startIndex = -1;
    public int endIndex = -1;

    @Override
    public String toString() {
        return "SearchElement [startIndex=" + startIndex + ", endIndex="
                + endIndex + "]";
    }

    public void reset() {
        startIndex = -1;
        endIndex = -1;
    }
}
