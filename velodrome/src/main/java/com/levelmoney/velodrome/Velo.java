package com.levelmoney.velodrome;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Aaron Sarazan on 4/26/15
 * Copyright(c) 2015 Level, Inc.
 */
public interface Velo {

    /**
     * The request code to be received by onActivityResult.
     */
    int requestCode();

    /**
     * e.g. launch a DialogFragment or start an Activity for a result.
     */
    void go(Bundle args);

    /**
     * What do we do with the result once we get it?
     * If the resultCode was not RESULT_OK, this will not be called.
     */
    boolean onActivityResult(Intent data);

}
