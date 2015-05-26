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

import android.content.Intent;

import com.levelmoney.velodrome.annotations.HandleResult;
import com.levelmoney.velodrome.annotations.HandleResults;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This is an attempt to streamline the awkwardness of dealing with request codes.
 * If it uses onActivityResult, it can probably benefit from this framework.
 *
 * EXAMPLE:
 * public void onActivityResult(int requestCode, int resultCode, Intent data) {
 *      super.onActivityResult(requestCode, resultCode, data);
 *      Velodrome.handleResult(this, requestCode, resultCode, data);
 * }
 */
public final class Velodrome {

    private Velodrome() {}

    /**
     * Call this method from onActivityResult
     * and it will search for a {@link HandleResult} of the same requestCode.
     *
     * @param target the object which declares {@link HandleResult} methods.
     * @param requestCode requestCode that was passed to the Dialog or Activity.
     *                    Corresponds to 'value' on {@link HandleResult}
     * @param resultCode By default, only Activity.RESULT_OK will be forwarded to result handlers.
     *                   This can be overriden with the resultCode field on {@link HandleResult}
     * @param data the result data intent.
     *
     * @return whether an appropriate handler was found.
     */
    public static synchronized boolean handleResult(Object target, int requestCode, int resultCode, Intent data) {
        for (Method m : target.getClass().getMethods()) {
            HandleResult ann = m.getAnnotation(HandleResult.class);
            if (ann != null) {
                if (ann.value() == requestCode && ann.resultCode() == resultCode) {
                    invoke(m, target, data, resultCode);
                    return true;
                }
            }
            HandleResults anns = m.getAnnotation(HandleResults.class);
            if (anns != null) {
                for (int value : anns.value()) {
                    if (value == requestCode && anns.resultCode() == resultCode) {
                        invoke(m, target, data, resultCode);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void invoke(Method m, Object target, Intent data, int resultCode) {
        Class[] params = m.getParameterTypes();
        Object[] args = new Object[params.length];
        for (int i = 0; i < params.length; ++i) {
            Class c = params[i];
            if (c == int.class) {
                args[i] = resultCode;
            } else if (c == Intent.class) {
                args[i] = data;
            }
        }
        try {
            m.invoke(target, args);
        } catch (IllegalAccessException e) {
            throw new VelodromeException(e);
        } catch (InvocationTargetException e) {
            throw new VelodromeException(e);
        }
    }

    public static class VelodromeException extends RuntimeException {
        public VelodromeException(Throwable throwable) {
            super(throwable);
        }
    }
}
