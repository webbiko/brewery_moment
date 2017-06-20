package com.brewery.app.data.model;

import java.util.List;

public class BreweryResult<T> {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}