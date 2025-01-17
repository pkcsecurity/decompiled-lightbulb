package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

public class LinearSmoothScroller extends RecyclerView.SmoothScroller {

   private static final boolean DEBUG = false;
   private static final float MILLISECONDS_PER_INCH = 25.0F;
   public static final int SNAP_TO_ANY = 0;
   public static final int SNAP_TO_END = 1;
   public static final int SNAP_TO_START = -1;
   private static final String TAG = "LinearSmoothScroller";
   private static final float TARGET_SEEK_EXTRA_SCROLL_RATIO = 1.2F;
   private static final int TARGET_SEEK_SCROLL_DISTANCE_PX = 10000;
   private final float MILLISECONDS_PER_PX;
   protected final DecelerateInterpolator mDecelerateInterpolator = new DecelerateInterpolator();
   protected int mInterimTargetDx = 0;
   protected int mInterimTargetDy = 0;
   protected final LinearInterpolator mLinearInterpolator = new LinearInterpolator();
   protected PointF mTargetVector;


   public LinearSmoothScroller(Context var1) {
      this.MILLISECONDS_PER_PX = this.calculateSpeedPerPixel(var1.getResources().getDisplayMetrics());
   }

   private int clampApplyScroll(int var1, int var2) {
      var2 = var1 - var2;
      return var1 * var2 <= 0?0:var2;
   }

   public int calculateDtToFit(int var1, int var2, int var3, int var4, int var5) {
      switch(var5) {
      case -1:
         return var3 - var1;
      case 0:
         var1 = var3 - var1;
         if(var1 > 0) {
            return var1;
         } else {
            var1 = var4 - var2;
            if(var1 < 0) {
               return var1;
            }

            return 0;
         }
      case 1:
         return var4 - var2;
      default:
         throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
      }
   }

   public int calculateDxToMakeVisible(View var1, int var2) {
      RecyclerView.LayoutManager var3 = this.getLayoutManager();
      if(var3 != null && var3.canScrollHorizontally()) {
         RecyclerView.LayoutParams var4 = (RecyclerView.LayoutParams)var1.getLayoutParams();
         return this.calculateDtToFit(var3.getDecoratedLeft(var1) - var4.leftMargin, var3.getDecoratedRight(var1) + var4.rightMargin, var3.getPaddingLeft(), var3.getWidth() - var3.getPaddingRight(), var2);
      } else {
         return 0;
      }
   }

   public int calculateDyToMakeVisible(View var1, int var2) {
      RecyclerView.LayoutManager var3 = this.getLayoutManager();
      if(var3 != null && var3.canScrollVertically()) {
         RecyclerView.LayoutParams var4 = (RecyclerView.LayoutParams)var1.getLayoutParams();
         return this.calculateDtToFit(var3.getDecoratedTop(var1) - var4.topMargin, var3.getDecoratedBottom(var1) + var4.bottomMargin, var3.getPaddingTop(), var3.getHeight() - var3.getPaddingBottom(), var2);
      } else {
         return 0;
      }
   }

   public float calculateSpeedPerPixel(DisplayMetrics var1) {
      return 25.0F / (float)var1.densityDpi;
   }

   public int calculateTimeForDeceleration(int var1) {
      return (int)Math.ceil((double)this.calculateTimeForScrolling(var1) / 0.3356D);
   }

   public int calculateTimeForScrolling(int var1) {
      return (int)Math.ceil((double)((float)Math.abs(var1) * this.MILLISECONDS_PER_PX));
   }

   protected int getHorizontalSnapPreference() {
      return this.mTargetVector != null && this.mTargetVector.x != 0.0F?(this.mTargetVector.x > 0.0F?1:-1):0;
   }

   protected int getVerticalSnapPreference() {
      return this.mTargetVector != null && this.mTargetVector.y != 0.0F?(this.mTargetVector.y > 0.0F?1:-1):0;
   }

   protected void onSeekTargetStep(int var1, int var2, RecyclerView.State var3, RecyclerView.Action var4) {
      if(this.getChildCount() == 0) {
         this.stop();
      } else {
         this.mInterimTargetDx = this.clampApplyScroll(this.mInterimTargetDx, var1);
         this.mInterimTargetDy = this.clampApplyScroll(this.mInterimTargetDy, var2);
         if(this.mInterimTargetDx == 0 && this.mInterimTargetDy == 0) {
            this.updateActionForInterimTarget(var4);
         }

      }
   }

   protected void onStart() {}

   public void onStop() {
      this.mInterimTargetDy = 0;
      this.mInterimTargetDx = 0;
      this.mTargetVector = null;
   }

   public void onTargetFound(View var1, RecyclerView.State var2, RecyclerView.Action var3) {
      int var4 = this.calculateDxToMakeVisible(var1, this.getHorizontalSnapPreference());
      int var5 = this.calculateDyToMakeVisible(var1, this.getVerticalSnapPreference());
      int var6 = this.calculateTimeForDeceleration((int)Math.sqrt((double)(var4 * var4 + var5 * var5)));
      if(var6 > 0) {
         var3.update(-var4, -var5, var6, this.mDecelerateInterpolator);
      }

   }

   protected void updateActionForInterimTarget(RecyclerView.Action var1) {
      PointF var3 = this.computeScrollVectorForPosition(this.getTargetPosition());
      if(var3 != null && (var3.x != 0.0F || var3.y != 0.0F)) {
         this.normalize(var3);
         this.mTargetVector = var3;
         this.mInterimTargetDx = (int)(var3.x * 10000.0F);
         this.mInterimTargetDy = (int)(var3.y * 10000.0F);
         int var2 = this.calculateTimeForScrolling(10000);
         var1.update((int)((float)this.mInterimTargetDx * 1.2F), (int)((float)this.mInterimTargetDy * 1.2F), (int)((float)var2 * 1.2F), this.mLinearInterpolator);
      } else {
         var1.jumpTo(this.getTargetPosition());
         this.stop();
      }
   }
}
