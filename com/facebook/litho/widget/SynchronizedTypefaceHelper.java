package com.facebook.litho.widget;

import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.util.LongSparseArray;
import android.util.SparseArray;
import java.util.concurrent.atomic.AtomicBoolean;

public class SynchronizedTypefaceHelper {

   private static final AtomicBoolean sIsInitialized = new AtomicBoolean(false);


   public static void setupSynchronizedTypeface() {
      // $FF: Couldn't be decompiled
   }

   static class SynchronizedTypefaceSparseArray extends SparseArray<Typeface> {

      private final SparseArray<Typeface> mDelegateSparseArray;
      private final Object mLock = new Object();


      SynchronizedTypefaceSparseArray(SparseArray<Typeface> var1) {
         this.mDelegateSparseArray = var1;
      }

      public Typeface get(int param1) {
         // $FF: Couldn't be decompiled
      }

      public void put(int param1, Typeface param2) {
         // $FF: Couldn't be decompiled
      }
   }

   @TargetApi(16)
   static class SynchronizedSparseArray extends SparseArray<SparseArray<Typeface>> {

      private final Object mLock;


      SynchronizedSparseArray(Object var1, int var2) {
         super(var2);
         this.mLock = var1;
      }

      public SparseArray<Typeface> get(int param1) {
         // $FF: Couldn't be decompiled
      }

      public void put(int param1, SparseArray<Typeface> param2) {
         // $FF: Couldn't be decompiled
      }
   }

   @TargetApi(21)
   static class SynchronizedLongSparseArray extends LongSparseArray<SparseArray<Typeface>> {

      private final Object mLock;


      SynchronizedLongSparseArray(Object var1, int var2) {
         super(var2);
         this.mLock = var1;
      }

      public SparseArray<Typeface> get(long param1) {
         // $FF: Couldn't be decompiled
      }

      public void put(long param1, SparseArray<Typeface> param3) {
         // $FF: Couldn't be decompiled
      }
   }
}
