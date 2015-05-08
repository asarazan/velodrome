package com.levelmoney.velodrome.handlers;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import java.security.SecureRandom;

/**
 * Created by Aaron Sarazan on 4/26/15
 * Copyright(c) 2015 Level, Inc.
 */
public abstract class DialogFragmentResultHandler extends BasicResultHandler {

    private static final SecureRandom RAND = new SecureRandom();

    private final Class<? extends DialogFragment> mClazz;
    private final Fragment mTarget;
    private final String mTag = "velo_" + RAND.nextInt();

    public DialogFragmentResultHandler(Fragment target, int requestCode, Class<? extends DialogFragment> clazz) {
        super(requestCode);
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
        df.setTargetFragment(mTarget, requestCode());
        df.show(mTarget.getFragmentManager(), mTag);
    }
}
