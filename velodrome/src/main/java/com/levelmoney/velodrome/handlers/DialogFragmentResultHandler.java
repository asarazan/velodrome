/*
 * Copyright 2015 Level Money, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.levelmoney.velodrome.handlers;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import java.security.SecureRandom;

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
