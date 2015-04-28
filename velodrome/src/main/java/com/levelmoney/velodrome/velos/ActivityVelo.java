package com.levelmoney.velodrome.velos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.levelmoney.velodrome.Velo;

/**
 * Created by Aaron Sarazan on 4/26/15
 * Copyright(c) 2015 Level, Inc.
 */
public abstract class ActivityVelo implements Velo {
    private static final String TAG = ActivityVelo.class.getSimpleName();

    private final int mRequestCode;
    private final Class<? extends Activity> mClazz;
    private final Fragment mTarget;

    public ActivityVelo(int requestCode, Class<? extends Activity> clazz, Fragment target) {
        mRequestCode = requestCode;
        mClazz = clazz;
        mTarget = target;
    }

    @Override
    public int requestCode() {
        return mRequestCode;
    }

    public void go(Bundle args) {
        Intent i = new Intent(mTarget.getActivity(), mClazz);
        i.putExtras(args);
        mTarget.startActivityForResult(i, mRequestCode);
    }
}
