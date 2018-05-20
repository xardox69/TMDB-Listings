package com.dubizzle.app.filter;

public interface ValidationCallback {

    void onValid(int maxYear, int minYear);

    void onInvalid(int id);
}
