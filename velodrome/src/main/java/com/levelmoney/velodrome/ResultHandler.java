package com.levelmoney.velodrome;

import android.content.Intent;

/**
 * Created by Aaron Sarazan on 4/26/15
 * Copyright(c) 2015 Level, Inc.
 */
public interface ResultHandler {

    /**
     * What do we do with the result once we get it?
     * Velodrome automatically filters out !RESULT_OK
     */
    void handleResult(Intent data);

}
