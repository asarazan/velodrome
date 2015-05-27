package com.levelmoney.velodrome.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Aaron Sarazan on 5/27/15
 * Copyright(c) 2015 Level, Inc.
 */
@Retention(RUNTIME) @Target(PARAMETER)
public @interface Arg {

    String value();

}
