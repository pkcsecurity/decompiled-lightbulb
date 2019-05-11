package com.facebook.litho.utils;

import android.graphics.Rect;
import android.support.v4.util.Pools;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.litho.LithoView;
import com.facebook.litho.ThreadUtils;

public class IncrementalMountUtils {

   private static final Pools.SynchronizedPool<Rect> sRectPool = new Pools.SynchronizedPool(10);


   private static Rect acquireRect() {
      Rect var1 = (Rect)sRectPool.acquire();
      Rect var0 = var1;
      if(var1 == null) {
         var0 = new Rect();
      }

      return var0;
   }

   public static void incrementallyMountLithoViews(View var0) {
      if(var0 instanceof LithoView) {
         LithoView var3 = (LithoView)var0;
         if(var3.isIncrementalMountEnabled()) {
            var3.performIncrementalMount();
            return;
         }
      }

      if(var0 instanceof ViewGroup) {
         int var1 = 0;
         ViewGroup var4 = (ViewGroup)var0;

         for(int var2 = var4.getChildCount(); var1 < var2; ++var1) {
            incrementallyMountLithoViews(var4.getChildAt(var1));
         }
      }

   }

   private static void maybePerformIncrementalMountOnView(int var0, int var1, View var2) {
      View var7;
      if(var2 instanceof IncrementalMountUtils.WrapperView) {
         var7 = ((IncrementalMountUtils.WrapperView)var2).getWrappedView();
      } else {
         var7 = var2;
      }

      if(var7 instanceof LithoView) {
         LithoView var8 = (LithoView)var7;
         if(var8.isIncrementalMountEnabled()) {
            if(var2 != var7 && var2.getHeight() != var7.getHeight()) {
               throw new IllegalStateException("ViewDiagnosticsWrapper must be the same height as the underlying view");
            } else {
               int var4 = (int)var2.getTranslationX();
               int var5 = (int)var2.getTranslationY();
               int var3 = var2.getTop() + var5;
               var5 += var2.getBottom();
               int var6 = var2.getLeft() + var4;
               var4 += var2.getRight();
               if(var6 < 0 || var3 < 0 || var4 > var0 || var5 > var1 || var8.getPreviousMountBounds().width() != var8.getWidth() || var8.getPreviousMountBounds().height() != var8.getHeight()) {
                  Rect var9 = acquireRect();
                  var9.set(Math.max(0, -var6), Math.max(0, -var3), Math.min(var4, var0) - var6, Math.min(var5, var1) - var3);
                  if(var9.isEmpty()) {
                     release(var9);
                  } else {
                     var8.performIncrementalMount(var9, true);
                     release(var9);
                  }
               }
            }
         }
      }
   }

   public static void performIncrementalMount(ViewGroup var0) {
      ThreadUtils.assertMainThread();
      int var2 = var0.getWidth();
      int var3 = var0.getHeight();

      for(int var1 = 0; var1 < var0.getChildCount(); ++var1) {
         maybePerformIncrementalMountOnView(var2, var3, var0.getChildAt(var1));
      }

   }

   private static void release(Rect var0) {
      var0.setEmpty();
      sRectPool.release(var0);
   }

   public interface WrapperView {

      View getWrappedView();
   }
}
