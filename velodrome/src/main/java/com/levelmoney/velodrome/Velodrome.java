package com.levelmoney.velodrome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.levelmoney.observefragment.FragmentObserver;
import com.levelmoney.observefragment.IObserveFragment;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Aaron Sarazan on 4/26/15
 * Copyright(c) 2015 Level, Inc.
 *
 * It handles lots of lifecycle stuff. Get it?
 * Eh maybe Tre can come up with a better name.
 *
 * Anyway this is an attempt to streamline the awkwardness of dealing with request codes.
 * If it uses onActivityResult, it can probably benefit from this framework.
 */
public class Velodrome {
    private static final String TAG = Velodrome.class.getSimpleName();

    private final Map<Integer, Velo> mVelos = new LinkedHashMap<>();

    /**
     * If you don't have an ObserveSupportFragment, you'll have to do it the old fashioned way.
     * Remember to call through to onActivityResult from your Fragment/Activity.
     */
    public Velodrome() {}

    /**
     * Avoid manually invoking onActivityResult by using ObserveSupportFragment.
     */
    public Velodrome(IObserveFragment f, Velo... vs) {
        f.addObserver(new FragmentObserver() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                Velodrome.this.onActivityResult(requestCode, resultCode, data);
            }
        });
        if (vs != null) {
            for (Velo v : vs) {
                add(v);
            }
        }
    }

    /**
     * Add a new strategy object.
     * @param v a Velo which will handle dialog or activity launching.
     */
    public void add(Velo v) {
        int code = v.requestCode();
        if (mVelos.containsKey(code)) throw new IllegalArgumentException("Behavior already specified for that request code!");
        mVelos.put(code, v);
    }

    /**
     * Find a Velo corresponding requestCode and have it do something.
     */
    public void go(int requestCode, Bundle args) {
        Velo v = mVelos.get(requestCode);
        if (v == null) throw new RuntimeException("Could not find velo for that requestCode!");
        v.go(args);
    }

    /**
     * See if we have a Velo that can handle this requestCode.
     */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return false;
        Velo v = mVelos.get(requestCode);
        return v != null && v.onActivityResult(data);
    }
}
