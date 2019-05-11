package com.facebook.litho.widget;

import android.content.Context;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import com.facebook.litho.widget.CenterSnappingSmoothScroller;
import com.facebook.litho.widget.EdgeSnappingSmoothScroller;
import com.facebook.litho.widget.SmoothScrollAlignmentType;
import com.facebook.litho.widget.StartSnapHelper;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.Nullable;

public class SnapUtil {

   public static final int SNAP_NONE = Integer.MIN_VALUE;
   public static final int SNAP_TO_CENTER = Integer.MAX_VALUE;
   public static final int SNAP_TO_CENTER_CHILD = 2147483646;
   public static final int SNAP_TO_END = 1;
   public static final int SNAP_TO_START = -1;


   public static RecyclerView.SmoothScroller getSmoothScrollerWithOffset(Context var0, int var1, SmoothScrollAlignmentType var2) {
      return (RecyclerView.SmoothScroller)(var2 != SmoothScrollAlignmentType.SNAP_TO_ANY && var2 != SmoothScrollAlignmentType.SNAP_TO_START && var2 != SmoothScrollAlignmentType.SNAP_TO_END?(var2 == SmoothScrollAlignmentType.SNAP_TO_CENTER?new CenterSnappingSmoothScroller(var0, var1):new LinearSmoothScroller(var0)):new EdgeSnappingSmoothScroller(var0, var2.getValue(), var1));
   }

   @Nullable
   public static SnapHelper getSnapHelper(int var0) {
      if(var0 != -1) {
         switch(var0) {
         case 2147483646:
            return new LinearSnapHelper();
         case Integer.MAX_VALUE:
            return new PagerSnapHelper();
         default:
            return null;
         }
      } else {
         return new StartSnapHelper();
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface SnapMode {
   }
}
