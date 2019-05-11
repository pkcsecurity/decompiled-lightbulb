package com.facebook.common.references;


public interface ResourceReleaser<T extends Object> {

   void release(T var1);
}
