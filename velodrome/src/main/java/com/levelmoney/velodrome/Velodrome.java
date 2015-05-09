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

package com.levelmoney.velodrome;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.levelmoney.velodrome.annotations.HandleResult;

import java.lang.reflect.Field;

/**
 * It handles lots of lifecycle stuff. Get it?
 *
 * This is an attempt to streamline the awkwardness of dealing with request codes.
 * If it uses onActivityResult, it can probably benefit from this framework.
 */
public final class Velodrome {

    private Velodrome() {}

    /**
     * Call this method from onActivityResult
     */
    public static boolean handleResult(Object target, int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) return false;
        for (Field f : target.getClass().getDeclaredFields()) {
            HandleResult h = f.getAnnotation(HandleResult.class);
            if (h != null) {
                boolean accessible = f.isAccessible();
                try {
                    // We can get rid of this when we switch to compile-time processing.
                    f.setAccessible(true);
                    ResultHandler v = (ResultHandler) f.get(target);
                    if (v.requestCode() == requestCode) {
                        v.handleResult(data);
                        return true;
                    }
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
