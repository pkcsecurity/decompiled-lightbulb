package android.support.v7.widget;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

public class LinearSnapHelper extends SnapHelper {

   private static final float INVALID_DISTANCE = 1.0F;
   @Nullable
   private OrientationHelper mHorizontalHelper;
   @Nullable
   private OrientationHelper mVerticalHelper;


   private float computeDistancePerChild(RecyclerView.LayoutManager var1, OrientationHelper var2) {
      int var9 = var1.getChildCount();
      if(var9 == 0) {
         return 1.0F;
      } else {
         int var6 = 0;
         View var10 = null;
         int var3 = Integer.MAX_VALUE;
         View var11 = null;

         int var4;
         int var5;
         int var8;
         for(var5 = Integer.MIN_VALUE; var6 < var9; var5 = var8) {
            View var12 = var1.getChildAt(var6);
            int var7 = var1.getPosition(var12);
            View var13;
            if(var7 == -1) {
               var13 = var10;
               var8 = var5;
            } else {
               var4 = var3;
               if(var7 < var3) {
                  var10 = var12;
                  var4 = var7;
               }

               var13 = var10;
               var3 = var4;
               var8 = var5;
               if(var7 > var5) {
                  var11 = var12;
                  var8 = var7;
                  var3 = var4;
                  var13 = var10;
               }
            }

            ++var6;
            var10 = var13;
         }

         if(var10 != null) {
            if(var11 == null) {
               return 1.0F;
            } else {
               var4 = Math.min(var2.getDecoratedStart(var10), var2.getDecoratedStart(var11));
               var4 = Math.max(var2.getDecoratedEnd(var10), var2.getDecoratedEnd(var11)) - var4;
               if(var4 == 0) {
                  return 1.0F;
               } else {
                  return (float)var4 * 1.0F / (float)(var5 - var3 + 1);
               }
            }
         } else {
            return 1.0F;
         }
      }
   }

   private int distanceToCenter(@NonNull RecyclerView.LayoutManager var1, @NonNull View var2, OrientationHelper var3) {
      int var5 = var3.getDecoratedStart(var2);
      int var6 = var3.getDecoratedMeasurement(var2) / 2;
      int var4;
      if(var1.getClipToPadding()) {
         var4 = var3.getStartAfterPadding() + var3.getTotalSpace() / 2;
      } else {
         var4 = var3.getEnd() / 2;
      }

      return var5 + var6 - var4;
   }

   private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager var1, OrientationHelper var2, int var3, int var4) {
      int[] var6 = this.calculateScrollDistance(var3, var4);
      float var5 = this.computeDistancePerChild(var1, var2);
      if(var5 <= 0.0F) {
         return 0;
      } else {
         if(Math.abs(var6[0]) > Math.abs(var6[1])) {
            var3 = var6[0];
         } else {
            var3 = var6[1];
         }

         return Math.round((float)var3 / var5);
      }
   }

   @Nullable
   private View findCenterView(RecyclerView.LayoutManager var1, OrientationHelper var2) {
      int var8 = var1.getChildCount();
      View var9 = null;
      if(var8 == 0) {
         return null;
      } else {
         int var3;
         if(var1.getClipToPadding()) {
            var3 = var2.getStartAfterPadding() + var2.getTotalSpace() / 2;
         } else {
            var3 = var2.getEnd() / 2;
         }

         int var5 = Integer.MAX_VALUE;

         int var6;
         for(int var4 = 0; var4 < var8; var5 = var6) {
            View var10 = var1.getChildAt(var4);
            int var7 = Math.abs(var2.getDecoratedStart(var10) + var2.getDecoratedMeasurement(var10) / 2 - var3);
            var6 = var5;
            if(var7 < var5) {
               var9 = var10;
               var6 = var7;
            }

            ++var4;
         }

         return var9;
      }
   }

   @NonNull
   private OrientationHelper getHorizontalHelper(@NonNull RecyclerView.LayoutManager var1) {
      if(this.mHorizontalHelper == null || this.mHorizontalHelper.mLayoutManager != var1) {
         this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(var1);
      }

      return this.mHorizontalHelper;
   }

   @NonNull
   private OrientationHelper getVerticalHelper(@NonNull RecyclerView.LayoutManager var1) {
      if(this.mVerticalHelper == null || this.mVerticalHelper.mLayoutManager != var1) {
         this.mVerticalHelper = OrientationHelper.createVerticalHelper(var1);
      }

      return this.mVerticalHelper;
   }

   public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager var1, @NonNull View var2) {
      int[] var3 = new int[2];
      if(var1.canScrollHorizontally()) {
         var3[0] = this.distanceToCenter(var1, var2, this.getHorizontalHelper(var1));
      } else {
         var3[0] = 0;
      }

      if(var1.canScrollVertically()) {
         var3[1] = this.distanceToCenter(var1, var2, this.getVerticalHelper(var1));
         return var3;
      } else {
         var3[1] = 0;
         return var3;
      }
   }

   public View findSnapView(RecyclerView.LayoutManager var1) {
      return var1.canScrollVertically()?this.findCenterView(var1, this.getVerticalHelper(var1)):(var1.canScrollHorizontally()?this.findCenterView(var1, this.getHorizontalHelper(var1)):null);
   }

   public int findTargetSnapPosition(RecyclerView.LayoutManager var1, int var2, int var3) {
      if(!(var1 instanceof RecyclerView.ScrollVectorProvider)) {
         return -1;
      } else {
         int var6 = var1.getItemCount();
         if(var6 == 0) {
            return -1;
         } else {
            View var8 = this.findSnapView(var1);
            if(var8 == null) {
               return -1;
            } else {
               int var7 = var1.getPosition(var8);
               if(var7 == -1) {
                  return -1;
               } else {
                  RecyclerView.ScrollVectorProvider var9 = (RecyclerView.ScrollVectorProvider)var1;
                  int var4 = var6 - 1;
                  PointF var10 = var9.computeScrollVectorForPosition(var4);
                  if(var10 == null) {
                     return -1;
                  } else {
                     int var5;
                     if(var1.canScrollHorizontally()) {
                        var5 = this.estimateNextPositionDiffForFling(var1, this.getHorizontalHelper(var1), var2, 0);
                        var2 = var5;
                        if(var10.x < 0.0F) {
                           var2 = -var5;
                        }
                     } else {
                        var2 = 0;
                     }

                     if(var1.canScrollVertically()) {
                        var5 = this.estimateNextPositionDiffForFling(var1, this.getVerticalHelper(var1), 0, var3);
                        var3 = var5;
                        if(var10.y < 0.0F) {
                           var3 = -var5;
                        }
                     } else {
                        var3 = 0;
                     }

                     if(var1.canScrollVertically()) {
                        var2 = var3;
                     }

                     if(var2 == 0) {
                        return -1;
                     } else {
                        var3 = var7 + var2;
                        var2 = var3;
                        if(var3 < 0) {
                           var2 = 0;
                        }

                        var3 = var2;
                        if(var2 >= var6) {
                           var3 = var4;
                        }

                        return var3;
                     }
                  }
               }
            }
         }
      }
   }
}
