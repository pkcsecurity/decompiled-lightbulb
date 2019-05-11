package com.facebook.react.bridge;

import javax.annotation.concurrent.GuardedBy;

public class JavaScriptContextHolder {

   @GuardedBy
   private long mContext;


   public JavaScriptContextHolder(long var1) {
      this.mContext = var1;
   }

   public void clear() {
      synchronized(this){}

      try {
         this.mContext = 0L;
      } finally {
         ;
      }

   }

   @GuardedBy
   public long get() {
      return this.mContext;
   }
}
