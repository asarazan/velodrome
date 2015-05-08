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

package com.levelmoney.velodrome.kotlin

import android.app.Activity
import android.content.Intent
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import com.levelmoney.velodrome.handlers.ActivityResultHandler
import com.levelmoney.velodrome.handlers.BasicResultHandler
import com.levelmoney.velodrome.handlers.DialogFragmentResultHandler

/**
 * [ResultHandler]
 * val handler = resultHandler(SOME_CODE) {
 *   Log.d(TAG, it.getStringExtra("result")
 * }
 */
public fun Fragment.resultHandler(requestCode: Int, handler: (Intent?) -> Unit): BasicResultHandler {
    return object: BasicResultHandler(requestCode) {
        override fun handleResult(data: Intent?) {
            handler(data)
        }
    }
}

public fun Activity.resultHandler(requestCode: Int, handler: (Intent?) -> Unit): BasicResultHandler {
    return object: BasicResultHandler(requestCode) {
        override fun handleResult(data: Intent?) {
            handler(data)
        }
    }
}

/**
 * [ResultHandler]
 * val aLauncher = activityLauncher(SOME_CODE, javaClass<SomeActivity>()) {
 *   Log.d(TAG, it.getStringExtra("result")
 * }
 */
public fun Fragment.activityLauncher(requestCode: Int, clazz: Class<out Activity>, handler: (Intent?) -> Unit): ActivityResultHandler {
    return object: ActivityResultHandler(this, requestCode, clazz) {
        override fun handleResult(data: Intent?) {
            handler(data)
        }
    }
}

public fun Activity.activityLauncher(requestCode: Int, clazz: Class<out Activity>, handler: (Intent?) -> Unit): ActivityResultHandler {
    return object: ActivityResultHandler(this, requestCode, clazz) {
        override fun handleResult(data: Intent?) {
            handler(data)
        }
    }
}

/**
 * [ResultHandler]
 * val dLauncher = dialogLauncher(SOME_CODE, javaClass<SomeDFragment>()) {
 *   Log.d(TAG, it.getStringExtra("result")
 * }
 */
public fun Fragment.dialogLauncher(requestCode: Int, clazz: Class<out DialogFragment>, handler: (Intent?) -> Unit): DialogFragmentResultHandler {
    return object: DialogFragmentResultHandler(this, requestCode, clazz) {
        override fun handleResult(data: Intent?) {
            handler(data)
        }
    }
}
