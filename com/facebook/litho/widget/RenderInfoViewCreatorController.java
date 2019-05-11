package com.facebook.litho.widget;

import android.support.annotation.UiThread;
import android.support.annotation.VisibleForTesting;
import android.util.SparseArray;
import com.facebook.litho.viewcompat.ViewCreator;
import com.facebook.litho.widget.RenderInfo;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

public class RenderInfoViewCreatorController {

   public static final int DEFAULT_COMPONENT_VIEW_TYPE = 0;
   private final int mComponentViewType;
   private final boolean mCustomViewTypeEnabled;
   @VisibleForTesting
   final Map<ViewCreator, Integer> mViewCreatorToViewType = new HashMap();
   private int mViewTypeCounter;
   @VisibleForTesting
   final SparseArray<ViewCreator> mViewTypeToViewCreator = new SparseArray();


   public RenderInfoViewCreatorController(boolean var1, int var2) {
      this.mCustomViewTypeEnabled = var1;
      this.mComponentViewType = var2;
      this.mViewTypeCounter = var2 + 1;
   }

   private void ensureCustomViewTypeValidity(RenderInfo var1) {
      if(this.mCustomViewTypeEnabled && !var1.hasCustomViewType()) {
         throw new IllegalStateException("If you enable custom viewTypes, you must provide a customViewType in ViewRenderInfo.");
      } else if(!this.mCustomViewTypeEnabled && var1.hasCustomViewType()) {
         throw new IllegalStateException("You must enable custom viewTypes to provide customViewType in ViewRenderInfo.");
      } else if(this.mCustomViewTypeEnabled && this.mComponentViewType == var1.getViewType()) {
         throw new IllegalStateException("CustomViewType cannot be the same as ComponentViewType.");
      }
   }

   int getComponentViewType() {
      return this.mComponentViewType;
   }

   @Nullable
   public ViewCreator getViewCreator(int var1) {
      return (ViewCreator)this.mViewTypeToViewCreator.get(var1);
   }

   @UiThread
   public void maybeTrackViewCreator(RenderInfo var1) {
      if(var1.rendersView()) {
         this.ensureCustomViewTypeValidity(var1);
         ViewCreator var3 = var1.getViewCreator();
         int var2;
         if(this.mViewCreatorToViewType.containsKey(var3)) {
            var2 = ((Integer)this.mViewCreatorToViewType.get(var3)).intValue();
         } else {
            if(var1.hasCustomViewType()) {
               var2 = var1.getViewType();
            } else {
               var2 = this.mViewTypeCounter;
               this.mViewTypeCounter = var2 + 1;
            }

            this.mViewTypeToViewCreator.put(var2, var3);
            this.mViewCreatorToViewType.put(var3, Integer.valueOf(var2));
         }

         if(!var1.hasCustomViewType()) {
            var1.setViewType(var2);
         }

      }
   }
}
