package android.support.v4.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewParentCompat;
import android.view.View;
import android.view.ViewParent;

public class NestedScrollingChildHelper {

   private boolean mIsNestedScrollingEnabled;
   private ViewParent mNestedScrollingParentNonTouch;
   private ViewParent mNestedScrollingParentTouch;
   private int[] mTempNestedScrollConsumed;
   private final View mView;


   public NestedScrollingChildHelper(@NonNull View var1) {
      this.mView = var1;
   }

   private ViewParent getNestedScrollingParentForType(int var1) {
      switch(var1) {
      case 0:
         return this.mNestedScrollingParentTouch;
      case 1:
         return this.mNestedScrollingParentNonTouch;
      default:
         return null;
      }
   }

   private void setNestedScrollingParentForType(int var1, ViewParent var2) {
      switch(var1) {
      case 0:
         this.mNestedScrollingParentTouch = var2;
         return;
      case 1:
         this.mNestedScrollingParentNonTouch = var2;
         return;
      default:
      }
   }

   public boolean dispatchNestedFling(float var1, float var2, boolean var3) {
      if(this.isNestedScrollingEnabled()) {
         ViewParent var4 = this.getNestedScrollingParentForType(0);
         if(var4 != null) {
            return ViewParentCompat.onNestedFling(var4, this.mView, var1, var2, var3);
         }
      }

      return false;
   }

   public boolean dispatchNestedPreFling(float var1, float var2) {
      if(this.isNestedScrollingEnabled()) {
         ViewParent var3 = this.getNestedScrollingParentForType(0);
         if(var3 != null) {
            return ViewParentCompat.onNestedPreFling(var3, this.mView, var1, var2);
         }
      }

      return false;
   }

   public boolean dispatchNestedPreScroll(int var1, int var2, @Nullable int[] var3, @Nullable int[] var4) {
      return this.dispatchNestedPreScroll(var1, var2, var3, var4, 0);
   }

   public boolean dispatchNestedPreScroll(int var1, int var2, @Nullable int[] var3, @Nullable int[] var4, int var5) {
      if(this.isNestedScrollingEnabled()) {
         ViewParent var10 = this.getNestedScrollingParentForType(var5);
         if(var10 == null) {
            return false;
         }

         boolean var8 = true;
         if(var1 != 0 || var2 != 0) {
            int var6;
            int var7;
            if(var4 != null) {
               this.mView.getLocationInWindow(var4);
               var6 = var4[0];
               var7 = var4[1];
            } else {
               var6 = 0;
               var7 = 0;
            }

            int[] var9 = var3;
            if(var3 == null) {
               if(this.mTempNestedScrollConsumed == null) {
                  this.mTempNestedScrollConsumed = new int[2];
               }

               var9 = this.mTempNestedScrollConsumed;
            }

            var9[0] = 0;
            var9[1] = 0;
            ViewParentCompat.onNestedPreScroll(var10, this.mView, var1, var2, var9, var5);
            if(var4 != null) {
               this.mView.getLocationInWindow(var4);
               var4[0] -= var6;
               var4[1] -= var7;
            }

            if(var9[0] == 0) {
               if(var9[1] != 0) {
                  return true;
               }

               var8 = false;
            }

            return var8;
         }

         if(var4 != null) {
            var4[0] = 0;
            var4[1] = 0;
            return false;
         }
      }

      return false;
   }

   public boolean dispatchNestedScroll(int var1, int var2, int var3, int var4, @Nullable int[] var5) {
      return this.dispatchNestedScroll(var1, var2, var3, var4, var5, 0);
   }

   public boolean dispatchNestedScroll(int var1, int var2, int var3, int var4, @Nullable int[] var5, int var6) {
      if(this.isNestedScrollingEnabled()) {
         ViewParent var9 = this.getNestedScrollingParentForType(var6);
         if(var9 == null) {
            return false;
         }

         if(var1 != 0 || var2 != 0 || var3 != 0 || var4 != 0) {
            int var7;
            int var8;
            if(var5 != null) {
               this.mView.getLocationInWindow(var5);
               var7 = var5[0];
               var8 = var5[1];
            } else {
               var7 = 0;
               var8 = 0;
            }

            ViewParentCompat.onNestedScroll(var9, this.mView, var1, var2, var3, var4, var6);
            if(var5 != null) {
               this.mView.getLocationInWindow(var5);
               var5[0] -= var7;
               var5[1] -= var8;
            }

            return true;
         }

         if(var5 != null) {
            var5[0] = 0;
            var5[1] = 0;
            return false;
         }
      }

      return false;
   }

   public boolean hasNestedScrollingParent() {
      return this.hasNestedScrollingParent(0);
   }

   public boolean hasNestedScrollingParent(int var1) {
      return this.getNestedScrollingParentForType(var1) != null;
   }

   public boolean isNestedScrollingEnabled() {
      return this.mIsNestedScrollingEnabled;
   }

   public void onDetachedFromWindow() {
      ViewCompat.stopNestedScroll(this.mView);
   }

   public void onStopNestedScroll(@NonNull View var1) {
      ViewCompat.stopNestedScroll(this.mView);
   }

   public void setNestedScrollingEnabled(boolean var1) {
      if(this.mIsNestedScrollingEnabled) {
         ViewCompat.stopNestedScroll(this.mView);
      }

      this.mIsNestedScrollingEnabled = var1;
   }

   public boolean startNestedScroll(int var1) {
      return this.startNestedScroll(var1, 0);
   }

   public boolean startNestedScroll(int var1, int var2) {
      if(this.hasNestedScrollingParent(var2)) {
         return true;
      } else {
         if(this.isNestedScrollingEnabled()) {
            ViewParent var3 = this.mView.getParent();

            for(View var4 = this.mView; var3 != null; var3 = var3.getParent()) {
               if(ViewParentCompat.onStartNestedScroll(var3, var4, this.mView, var1, var2)) {
                  this.setNestedScrollingParentForType(var2, var3);
                  ViewParentCompat.onNestedScrollAccepted(var3, var4, this.mView, var1, var2);
                  return true;
               }

               if(var3 instanceof View) {
                  var4 = (View)var3;
               }
            }
         }

         return false;
      }
   }

   public void stopNestedScroll() {
      this.stopNestedScroll(0);
   }

   public void stopNestedScroll(int var1) {
      ViewParent var2 = this.getNestedScrollingParentForType(var1);
      if(var2 != null) {
         ViewParentCompat.onStopNestedScroll(var2, this.mView, var1);
         this.setNestedScrollingParentForType(var1, (ViewParent)null);
      }

   }
}
