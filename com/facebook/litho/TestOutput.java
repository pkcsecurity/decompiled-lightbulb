package com.facebook.litho;

import android.graphics.Rect;

class TestOutput {

   private final Rect mBounds = new Rect();
   private long mHostMarker = -1L;
   private long mLayoutOutputId = -1L;
   private String mTestKey;


   Rect getBounds() {
      return this.mBounds;
   }

   long getHostMarker() {
      return this.mHostMarker;
   }

   long getLayoutOutputId() {
      return this.mLayoutOutputId;
   }

   String getTestKey() {
      return this.mTestKey;
   }

   void release() {
      this.mTestKey = null;
      this.mLayoutOutputId = -1L;
      this.mHostMarker = -1L;
      this.mBounds.setEmpty();
   }

   void setBounds(int var1, int var2, int var3, int var4) {
      this.mBounds.set(var1, var2, var3, var4);
   }

   void setBounds(Rect var1) {
      this.mBounds.set(var1);
   }

   void setHostMarker(long var1) {
      this.mHostMarker = var1;
   }

   void setLayoutOutputId(long var1) {
      this.mLayoutOutputId = var1;
   }

   void setTestKey(String var1) {
      this.mTestKey = var1;
   }
}
