package com.levelmoney.velodrome.velos;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.levelmoney.velodrome.Velo;

import java.security.SecureRandom;

/**
 * Created by Aaron Sarazan on 4/26/15
 * Copyright(c) 2015 Level, Inc.
 */
public abstract class DialogFragmentVelo implements Velo {
    private static final String TAG = DialogFragmentVelo.class.getSimpleName();

    private static final SecureRandom RAND = new SecureRandom();

    private final int mRequestCode;
    private final Class<? extends DialogFragment> mClazz;
    private final Fragment mTarget;
    private final String mTag = "velo_" + RAND.nextInt();

    public DialogFragmentVelo(Fragment target, int requestCode, Class<? extends DialogFragment> clazz) {
        mRequestCode = requestCode;
        mClazz = clazz;
        mTarget = target;
    }

    public void go(Bundle args) {
        DialogFragment df;
        try {
            df = mClazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (args != null) df.setArguments(args);
        df.setTargetFragment(mTarget, mRequestCode);
        df.show(mTarget.getFragmentManager(), mTag);
    }
}
