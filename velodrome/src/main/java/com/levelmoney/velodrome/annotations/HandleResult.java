package com.levelmoney.velodrome.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

/**
 * Created by Aaron Sarazan on 5/2/15
 * Copyright(c) 2015 Level, Inc.
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface HandleResult {

}
