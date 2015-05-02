package com.levelmoney.velodrome;

import android.content.Intent;

import java.lang.reflect.Field;

/**
 * Created by Aaron Sarazan on 4/26/15
 * Copyright(c) 2015 Level, Inc.
 *
 * It handles lots of lifecycle stuff. Get it?
 * Eh maybe Tre can come up with a better name.
 *
 * Anyway this is an attempt to streamline the awkwardness of dealing with request codes.
 * If it uses handleResult, it can probably benefit from this framework.
 */
public final class Velodrome {
    private static final String TAG = Velodrome.class.getSimpleName();

    /**
     * If you don't have an ObserveSupportFragment, you'll have to do it the old fashioned way.
     * Remember to call through to handleResult from your Fragment/Activity.
     */
    private Velodrome() {}

    public static boolean handleResult(Object target, int requestCode, int resultCode, Intent data) {
        for (Field f : target.getClass().getDeclaredFields()) {
            ResultHandler h = f.getAnnotation(ResultHandler.class);
            if (h != null && h.value() == requestCode) {
                boolean accessible = f.isAccessible();
                try {
                    // We can get rid of this when we switch to compile-time processing.
                    f.setAccessible(true);
                    Velo v = (Velo) f.get(target);
                    if (v.handleResult(resultCode, data)) return true;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } finally {
                    f.setAccessible(accessible);
                }
            }
        }

        return false;
    }
}
