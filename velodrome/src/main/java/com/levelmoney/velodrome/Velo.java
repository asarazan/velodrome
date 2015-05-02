package com.levelmoney.velodrome;

import android.content.Intent;

/**
 * Created by Aaron Sarazan on 4/26/15
 * Copyright(c) 2015 Level, Inc.
 */
public interface Velo {

    /**
     * What do we do with the result once we get it?
     * You should probably check for RESULT_OK before you continue.
     */
    boolean handleResult(int resultCode, Intent data);

}
