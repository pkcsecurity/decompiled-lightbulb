package com.facebook.litho.config;


public interface LayoutThreadPoolConfiguration {

   int getCorePoolSize();

   int getMaxPoolSize();

   int getThreadPriority();
}
