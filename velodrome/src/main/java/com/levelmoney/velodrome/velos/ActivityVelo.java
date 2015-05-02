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
    private final Activity mTargetA;
    private final Fragment mTargetF;

    public ActivityVelo(Activity target, int requestCode, Class<? extends Activity> clazz) {
        mRequestCode = requestCode;
        mClazz = clazz;
        mTargetA = target;
        mTargetF = null;
    }

    public ActivityVelo(Fragment target, int requestCode, Class<? extends Activity> clazz) {
        mRequestCode = requestCode;
        mClazz = clazz;
        mTargetA = null;
        mTargetF = target;
    }

    public void go(Bundle args) {
        Activity a = mTargetA != null ? mTargetA : mTargetF.getActivity();
        Intent i = new Intent(a, mClazz);
        if (args != null) i.putExtras(args);
        if (mTargetA != null) {
            mTargetA.startActivityForResult(i, mRequestCode);
        } else {
            mTargetF.startActivityForResult(i, mRequestCode);
        }
    }
}
