package com.levelmoney.velodrome.test;

import android.app.Activity;
import android.content.Intent;

import com.levelmoney.velodrome.Velodrome;
import com.levelmoney.velodrome.annotations.HandleResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    private static final int CODE_M1 = 2;
    private static final int CODE_M2 = 3;

    public static class Tester {
        private int mValue = 0;
        private boolean mCanceled = false;

        @HandleResult(CODE)
        public void onResult(Intent data) {
            update(data);
        }

        @HandleResult(value = CODE, resultCode = CANCEL)
        public void onCancel(Intent data) {
            mCanceled = true;
        }

        @HandleResult({CODE_M1, CODE_M2})
        public void onMultiple(Intent data) {
            update(data);
        }

        private void update(Intent data) {
            if (data != null) {
                mValue = data.getIntExtra("value", -1);
            } else {
                mValue = -1;
            }
        }

        public boolean isCanceled() {
            return mCanceled;
        }

        public int getValue() {
            return mValue;
        }
    }

    private final Tester mTarget = new Tester();

    @Test
    public void testOk() {
        Velodrome.handleResult(mTarget, CODE, OK, intent(1));
        assertFalse(mTarget.isCanceled());
        assertEquals(mTarget.getValue(), 1);
    }

    @Test
    public void testCancel() {
        Velodrome.handleResult(mTarget, CODE, CANCEL, intent(1));
        assertTrue(mTarget.isCanceled());
        assertEquals(mTarget.getValue(), 0);
    }

    @Test
    public void testNullValue() {
        Velodrome.handleResult(mTarget, CODE, OK, intent(null));
        assertFalse(mTarget.isCanceled());
        assertEquals(mTarget.getValue(), -1);
    }

    @Test
    public void testNullIntent() {
        Velodrome.handleResult(mTarget, CODE, OK, null);
        assertFalse(mTarget.isCanceled());
        assertEquals(mTarget.getValue(), -1);
    }

    @Test
    public void testMultipleRequestCodes() {
        Velodrome.handleResult(mTarget, CODE_M1, OK, intent(1));
        assertFalse(mTarget.isCanceled());
        assertEquals(mTarget.getValue(), 1);

        Velodrome.handleResult(mTarget, CODE_M2, OK, intent(2));
        assertFalse(mTarget.isCanceled());
        assertEquals(mTarget.getValue(), 2);
    }

    private static Intent intent(Integer value) {
        Intent retval = new Intent(ShadowApplication.getInstance().getApplicationContext(), Activity.class);
        if (value != null) retval.putExtra("value", value);
        return retval;
    }
}