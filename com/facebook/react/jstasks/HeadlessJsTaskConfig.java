package com.facebook.react.jstasks;

import com.facebook.react.bridge.WritableMap;

public class HeadlessJsTaskConfig {

   private final boolean mAllowedInForeground;
   private final WritableMap mData;
   private final String mTaskKey;
   private final long mTimeout;


   public HeadlessJsTaskConfig(String var1, WritableMap var2) {
      this(var1, var2, 0L, false);
   }

   public HeadlessJsTaskConfig(String var1, WritableMap var2, long var3) {
      this(var1, var2, var3, false);
   }

   public HeadlessJsTaskConfig(String var1, WritableMap var2, long var3, boolean var5) {
      this.mTaskKey = var1;
      this.mData = var2;
      this.mTimeout = var3;
      this.mAllowedInForeground = var5;
   }

   WritableMap getData() {
      return this.mData;
   }

   String getTaskKey() {
      return this.mTaskKey;
   }

   long getTimeout() {
      return this.mTimeout;
   }

   boolean isAllowedInForeground() {
      return this.mAllowedInForeground;
   }
}
