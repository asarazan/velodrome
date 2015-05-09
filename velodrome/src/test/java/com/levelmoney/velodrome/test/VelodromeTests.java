package com.levelmoney.velodrome.test;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.levelmoney.velodrome.ResultHandler;
import com.levelmoney.velodrome.Velodrome;
import com.levelmoney.velodrome.annotations.HandleResult;
import com.levelmoney.velodrome.handlers.BasicResultHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;

/**
 * Created by Aaron Sarazan on 5/8/15
 * Copyright(c) 2015 Level, Inc.
 */

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk=18, manifest="./src/main/AndroidManifest.xml")
public class VelodromeTests {

    private static final int OK = Activity.RESULT_OK;
    private static final int CANCEL = Activity.RESULT_CANCELED;
    private static final int CODE = 1;

    private int mValue;
    private final InjectableTarget mTarget = new InjectableTarget(new BasicResultHandler(CODE) {
        @Override
        public void handleResult(@Nullable Intent data) {
            if (data != null) {
                mValue = data.getIntExtra("value", -1);
            } else {
                mValue = -1;
            }
        }
    });

    @Before
    public void setUp() {
        mValue = 0;
    }

    @Test
    public void testOk() {
        Velodrome.handleResult(mTarget, CODE, OK, intent(1));
        assertEquals(mValue, 1);
    }

    @Test
    public void testCancel() {
        Velodrome.handleResult(mTarget, CODE, CANCEL, intent(1));
        assertEquals(mValue, 0);
    }

    @Test
    public void testNullValue() {
        Velodrome.handleResult(mTarget, CODE, OK, intent(null));
        assertEquals(mValue, -1);
    }

    @Test
    public void testNullIntent() {
        Velodrome.handleResult(mTarget, CODE, OK, null);
        assertEquals(mValue, -1);
    }

    private static Intent intent(@Nullable Integer value) {
        Intent retval = new Intent(ShadowApplication.getInstance().getApplicationContext(), Activity.class);
        if (value != null) retval.putExtra("value", value);
        return retval;
    }

    private static class InjectableTarget {
        @HandleResult
        private final ResultHandler mHandler;
        private InjectableTarget(ResultHandler handler) {
            mHandler = handler;
        }
    }
}