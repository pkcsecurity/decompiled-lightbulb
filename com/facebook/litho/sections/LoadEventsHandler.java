package com.facebook.litho.sections;


public interface LoadEventsHandler {

   void onInitialLoad();

   void onLoadFailed(boolean var1);

   void onLoadStarted(boolean var1);

   void onLoadSucceeded(boolean var1);
}
