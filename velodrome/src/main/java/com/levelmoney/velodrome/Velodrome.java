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
import android.os.Bundle;

import com.levelmoney.velodrome.annotations.Arg;
import com.levelmoney.velodrome.annotations.OnActivityResult;

import java.lang.annotation.Annotation;
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
     * and it will search for a {@link OnActivityResult} of the same requestCode.
     *
     * @param target the object which declares {@link OnActivityResult} methods.
     * @param requestCode requestCode that was passed to the Dialog or Activity.
     *                    Corresponds to 'value' on {@link OnActivityResult}
     * @param resultCode By default, only Activity.RESULT_OK will be forwarded to result handlers.
     *                   This can be overriden with the resultCode field on {@link OnActivityResult}
     * @param data the result data intent.
     *
     * @return whether an appropriate handler was found.
     */
    public static synchronized boolean handleResult(Object target, int requestCode, int resultCode, Intent data) {
        for (Method m : target.getClass().getMethods()) {
            OnActivityResult anns = m.getAnnotation(OnActivityResult.class);
            if (anns != null) {
                for (int value : anns.value()) {
                    if (value == requestCode && anns.resultCode() == resultCode) {
                        invoke(m, target, data);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void invoke(Method m, Object target, Intent data) {
        Class[] params = m.getParameterTypes();
        Annotation[][] anns = m.getParameterAnnotations();
        Object[] args = getArgs(params, anns, data);
        try {
            m.invoke(target, args);
        } catch (IllegalAccessException e) {
            throw new VelodromeException(e);
        } catch (InvocationTargetException e) {
            throw new VelodromeException(e);
        }
    }

    public static Object[] getArgs(Class[] params, Annotation[][] anns, Intent data) {
        Object[] retval = new Object[params.length];
        for (int i = 0; i < params.length; ++i) {
            Class c = params[i];
            Annotation[] a = anns[i];
            retval[i] = getArg(c, a, data);
        }
        return retval;
    }

    private static Object getArg(Class c, Annotation[] anns, Intent data) {
        if (anns.length == 0) {
            if (c == Intent.class) {
                return data;
            }
        } else {
            for (Annotation a : anns) {
                if (Arg.class == a.annotationType()) {
                    for (ArgGetter getter : ArgGetter.values()) {
                        for (Class clazz : getter.classes) {
                            if (clazz == c) {
                                String name = ((Arg) a).value();
                                return getter.get(name, data);
                            }
                        }
                    }
                }
            }
        }
        throw new VelodromeException("Cannot pull value of type " + c.getSimpleName() + "from Intent");
    }

    public static class VelodromeException extends RuntimeException {
        public VelodromeException(String detailMessage) {
            super(detailMessage);
        }
        public VelodromeException(Throwable throwable) {
            super(throwable);
        }
    }

    private enum ArgGetter {
        STRING(String.class) {
            @Override
            Object get(String name, Intent data) {
                return data.getStringExtra(name);
            }
        },
        INTEGER(Integer.class, int.class) {
            @Override
            Object get(String name, Intent data) {
                return data.getIntExtra(name, 0);
            }
        },
        LONG(Long.class, long.class) {
            @Override
            Object get(String name, Intent data) {
                return data.getLongExtra(name, 0L);
            }
        },
        FLOAT(Float.class, float.class) {
            @Override
            Object get(String name, Intent data) {
                return data.getFloatExtra(name, 0f);
            }
        },
        DOUBLE(Double.class, double.class) {
            @Override
            Object get(String name, Intent data) {
                return data.getDoubleExtra(name, 0.0);
            }
        },
        BUNDLE(Bundle.class) {
            @Override
            Object get(String name, Intent data) {
                return data.getBundleExtra(name);
            }
        }

        // These would require isAssignableFrom checks which would probably be pretty rough
        // on performance. We can have these once we build the annotation processor.

//        PARCELABLE(Parcelable.class) {
//            @Override
//            Object get(String name, Intent data) {
//                return data.getParcelableExtra(name);
//            }
//        },
//        SERIALIZABLE(Serializable.class) {
//            @Override
//            Object get(String name, Intent data) {
//                return data.getSerializableExtra(name);
//            }
//        }
        ;

        public final Class[] classes;
        ArgGetter(Class... classes) {
            this.classes = classes;
        }

        abstract Object get(String name, Intent data);
    }
}
