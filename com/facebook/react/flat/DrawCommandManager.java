package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewParent;
import com.facebook.react.flat.DrawCommand;
import com.facebook.react.flat.FlatViewGroup;
import com.facebook.react.flat.NodeRegion;
import com.facebook.react.flat.VerticalDrawCommandManager;
import com.facebook.react.flat.ViewResolver;
import javax.annotation.Nullable;

abstract class DrawCommandManager {

   protected static void ensureViewHasNoParent(View var0) {
      ViewParent var1 = var0.getParent();
      if(var1 != null) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Cannot add view ");
         var2.append(var0);
         var2.append(" to DrawCommandManager while it has a parent ");
         var2.append(var1);
         throw new RuntimeException(var2.toString());
      }
   }

   static DrawCommandManager getVerticalClippingInstance(FlatViewGroup var0, DrawCommand[] var1) {
      return new VerticalDrawCommandManager(var0, var1);
   }

   @Nullable
   abstract NodeRegion anyNodeRegionWithinBounds(float var1, float var2);

   abstract void debugDraw(Canvas var1);

   abstract void draw(Canvas var1);

   abstract void getClippingRect(Rect var1);

   abstract SparseArray<View> getDetachedViews();

   abstract void mountDrawCommands(DrawCommand[] var1, SparseIntArray var2, float[] var3, float[] var4, boolean var5);

   abstract void mountNodeRegions(NodeRegion[] var1, float[] var2, float[] var3);

   abstract void mountViews(ViewResolver var1, int[] var2, int[] var3);

   abstract void onClippedViewDropped(View var1);

   abstract boolean updateClippingRect();

   @Nullable
   abstract NodeRegion virtualNodeRegionWithinBounds(float var1, float var2);
}
