package com.facebook.react.flat;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import com.facebook.react.flat.AttachDetachListener;
import com.facebook.react.flat.DrawCommand;
import com.facebook.react.flat.FlatRootViewManager;
import com.facebook.react.flat.FlatViewGroup;
import com.facebook.react.flat.NodeRegion;
import com.facebook.react.flat.ViewResolver;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.SizeMonitoringFrameLayout;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewManagerRegistry;
import java.util.ArrayList;
import javax.annotation.Nullable;

final class FlatNativeViewHierarchyManager extends NativeViewHierarchyManager implements ViewResolver {

   FlatNativeViewHierarchyManager(ViewManagerRegistry var1) {
      super(var1, new FlatRootViewManager());
   }

   public void addRootView(int var1, SizeMonitoringFrameLayout var2, ThemedReactContext var3) {
      FlatViewGroup var4 = new FlatViewGroup(var3);
      var2.addView(var4);
      var2.setId(var1);
      this.addRootViewGroup(var1, var4, var3);
   }

   void detachAllChildrenFromViews(int[] var1) {
      int var3 = var1.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         int var4 = var1[var2];
         View var5 = this.resolveView(var4);
         if(var5 instanceof FlatViewGroup) {
            ((FlatViewGroup)var5).detachAllViewsFromParent();
         } else {
            ViewGroup var6 = (ViewGroup)var5;
            ((ViewGroupManager)this.resolveViewManager(var4)).removeAllViews(var6);
         }
      }

   }

   protected void dropView(View var1) {
      super.dropView(var1);
      if(var1 instanceof FlatViewGroup) {
         FlatViewGroup var8 = (FlatViewGroup)var1;
         if(var8.getRemoveClippedSubviews()) {
            SparseArray var4 = var8.getDetachedViews();
            int var2 = 0;

            for(int var3 = var4.size(); var2 < var3; ++var2) {
               View var5 = (View)var4.valueAt(var2);

               try {
                  this.dropView(var5);
               } catch (Exception var7) {
                  ;
               }

               var8.removeDetachedView(var5);
            }
         }
      }

   }

   void dropViews(SparseIntArray var1) {
      int var3 = var1.size();

      for(int var2 = 0; var2 < var3; ++var2) {
         int var4 = var1.keyAt(var2);
         View var5 = null;
         View var6;
         if(var4 > 0) {
            label40: {
               try {
                  var6 = this.resolveView(var4);
               } catch (Exception var8) {
                  break label40;
               }

               try {
                  this.dropView(var6);
               } catch (Exception var7) {
                  ;
               }

               var5 = var6;
            }
         } else {
            this.removeRootView(-var4);
         }

         var4 = var1.valueAt(var2);
         if(var4 > 0 && var5 != null && var5.getParent() == null) {
            var6 = this.resolveView(var4);
            if(var6 instanceof FlatViewGroup) {
               ((FlatViewGroup)var6).onViewDropped(var5);
            }
         }
      }

   }

   public View getView(int var1) {
      return super.resolveView(var1);
   }

   void setPadding(int var1, int var2, int var3, int var4, int var5) {
      this.resolveView(var1).setPadding(var2, var3, var4, var5);
   }

   void updateClippingMountState(int var1, @Nullable DrawCommand[] var2, SparseIntArray var3, float[] var4, float[] var5, @Nullable AttachDetachListener[] var6, @Nullable NodeRegion[] var7, float[] var8, float[] var9, boolean var10) {
      FlatViewGroup var11 = (FlatViewGroup)this.resolveView(var1);
      if(var2 != null) {
         var11.mountClippingDrawCommands(var2, var3, var4, var5, var10);
      }

      if(var6 != null) {
         var11.mountAttachDetachListeners(var6);
      }

      if(var7 != null) {
         var11.mountClippingNodeRegions(var7, var8, var9);
      }

   }

   void updateMountState(int var1, @Nullable DrawCommand[] var2, @Nullable AttachDetachListener[] var3, @Nullable NodeRegion[] var4) {
      FlatViewGroup var5 = (FlatViewGroup)this.resolveView(var1);
      if(var2 != null) {
         var5.mountDrawCommands(var2);
      }

      if(var3 != null) {
         var5.mountAttachDetachListeners(var3);
      }

      if(var4 != null) {
         var5.mountNodeRegions(var4);
      }

   }

   void updateViewBounds(int var1, int var2, int var3, int var4, int var5) {
      View var7 = this.resolveView(var1);
      var1 = var4 - var2;
      int var6 = var5 - var3;
      if(var7.getWidth() == var1 && var7.getHeight() == var6) {
         var7.offsetLeftAndRight(var2 - var7.getLeft());
         var7.offsetTopAndBottom(var3 - var7.getTop());
      } else {
         var7.measure(MeasureSpec.makeMeasureSpec(var1, 1073741824), MeasureSpec.makeMeasureSpec(var6, 1073741824));
         var7.layout(var2, var3, var4, var5);
      }
   }

   void updateViewGroup(int var1, int[] var2, int[] var3) {
      View var5 = this.resolveView(var1);
      if(var5 instanceof FlatViewGroup) {
         ((FlatViewGroup)var5).mountViews(this, var2, var3);
      } else {
         ViewGroup var7 = (ViewGroup)var5;
         ViewGroupManager var8 = (ViewGroupManager)this.resolveViewManager(var1);
         ArrayList var6 = new ArrayList(var2.length);
         int var4 = var2.length;

         for(var1 = 0; var1 < var4; ++var1) {
            var6.add(this.resolveView(Math.abs(var2[var1])));
         }

         var8.addViews(var7, var6);
      }
   }
}
