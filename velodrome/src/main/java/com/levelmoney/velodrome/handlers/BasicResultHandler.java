package com.levelmoney.velodrome.handlers;

import com.levelmoney.velodrome.ResultHandler;

/**
 * Created by Aaron Sarazan on 5/8/15
 * Copyright(c) 2015 Level, Inc.
 */
public abstract class BasicResultHandler implements ResultHandler {

    private final int mRequestCode;

    public BasicResultHandler(int requestCode) {
        mRequestCode = requestCode;
    }

    @Override
    public int requestCode() {
        return mRequestCode;
    }
}
