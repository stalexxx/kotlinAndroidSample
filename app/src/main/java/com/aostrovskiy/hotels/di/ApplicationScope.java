package com.aostrovskiy.hotels.di;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ApplicationScope
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationScope {
}
