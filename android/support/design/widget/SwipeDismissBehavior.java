package android.support.design.widget;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class SwipeDismissBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

   private static final float DEFAULT_ALPHA_END_DISTANCE = 0.5F;
   private static final float DEFAULT_ALPHA_START_DISTANCE = 0.0F;
   private static final float DEFAULT_DRAG_DISMISS_THRESHOLD = 0.5F;
   public static final int STATE_DRAGGING = 1;
   public static final int STATE_IDLE = 0;
   public static final int STATE_SETTLING = 2;
   public static final int SWIPE_DIRECTION_ANY = 2;
   public static final int SWIPE_DIRECTION_END_TO_START = 1;
   public static final int SWIPE_DIRECTION_START_TO_END = 0;
   float alphaEndSwipeDistance = 0.5F;
   float alphaStartSwipeDistance = 0.0F;
   private final ViewDragHelper.Callback dragCallback = new ViewDragHelper.Callback() {

      private static final int INVALID_POINTER_ID = -1;
      private int activePointerId = -1;
      private int originalCapturedViewLeft;

      private boolean shouldDismiss(View var1, float var2) {
         boolean var6 = false;
         if(var2 != 0.0F) {
            boolean var7;
            if(ViewCompat.getLayoutDirection(var1) == 1) {
               var7 = true;
            } else {
               var7 = false;
            }

            if(SwipeDismissBehavior.this.swipeDirection == 2) {
               return true;
            } else if(SwipeDismissBehavior.this.swipeDirection == 0) {
               if(var7) {
                  if(var2 < 0.0F) {
                     return true;
                  }
               } else if(var2 > 0.0F) {
                  return true;
               }

               return false;
            } else if(SwipeDismissBehavior.this.swipeDirection != 1) {
               return false;
            } else {
               if(var7) {
                  if(var2 > 0.0F) {
                     return true;
                  }
               } else if(var2 < 0.0F) {
                  return true;
               }

               return false;
            }
         } else {
            int var3 = var1.getLeft();
            int var4 = this.originalCapturedViewLeft;
            int var5 = Math.round((float)var1.getWidth() * SwipeDismissBehavior.this.dragDismissThreshold);
            if(Math.abs(var3 - var4) >= var5) {
               var6 = true;
            }

            return var6;
         }
      }
      public int clampViewPositionHorizontal(View var1, int var2, int var3) {
         boolean var5;
         if(ViewCompat.getLayoutDirection(var1) == 1) {
            var5 = true;
         } else {
            var5 = false;
         }

         int var4;
         if(SwipeDismissBehavior.this.swipeDirection == 0) {
            if(var5) {
               var4 = this.originalCapturedViewLeft - var1.getWidth();
               var3 = this.originalCapturedViewLeft;
            } else {
               var4 = this.originalCapturedViewLeft;
               var3 = this.originalCapturedViewLeft;
               var3 += var1.getWidth();
            }
         } else if(SwipeDismissBehavior.this.swipeDirection == 1) {
            if(var5) {
               var4 = this.originalCapturedViewLeft;
               var3 = this.originalCapturedViewLeft;
               var3 += var1.getWidth();
            } else {
               var4 = this.originalCapturedViewLeft - var1.getWidth();
               var3 = this.originalCapturedViewLeft;
            }
         } else {
            var4 = this.originalCapturedViewLeft - var1.getWidth();
            var3 = this.originalCapturedViewLeft;
            var3 += var1.getWidth();
         }

         return SwipeDismissBehavior.clamp(var4, var2, var3);
      }
      public int clampViewPositionVertical(View var1, int var2, int var3) {
         return var1.getTop();
      }
      public int getViewHorizontalDragRange(View var1) {
         return var1.getWidth();
      }
      public void onViewCaptured(View var1, int var2) {
         this.activePointerId = var2;
         this.originalCapturedViewLeft = var1.getLeft();
         ViewParent var3 = var1.getParent();
         if(var3 != null) {
            var3.requestDisallowInterceptTouchEvent(true);
         }

      }
      public void onViewDragStateChanged(int var1) {
         if(SwipeDismissBehavior.this.listener != null) {
            SwipeDismissBehavior.this.listener.onDragStateChanged(var1);
         }

      }
      public void onViewPositionChanged(View var1, int var2, int var3, int var4, int var5) {
         float var6 = (float)this.originalCapturedViewLeft + (float)var1.getWidth() * SwipeDismissBehavior.this.alphaStartSwipeDistance;
         float var7 = (float)this.originalCapturedViewLeft + (float)var1.getWidth() * SwipeDismissBehavior.this.alphaEndSwipeDistance;
         float var8 = (float)var2;
         if(var8 <= var6) {
            var1.setAlpha(1.0F);
         } else if(var8 >= var7) {
            var1.setAlpha(0.0F);
         } else {
            var1.setAlpha(SwipeDismissBehavior.clamp(0.0F, 1.0F - SwipeDismissBehavior.fraction(var6, var7, var8), 1.0F));
         }
      }
      public void onViewReleased(View var1, float var2, float var3) {
         this.activePointerId = -1;
         int var4 = var1.getWidth();
         boolean var5;
         if(this.shouldDismiss(var1, var2)) {
            if(var1.getLeft() < this.originalCapturedViewLeft) {
               var4 = this.originalCapturedViewLeft - var4;
            } else {
               var4 += this.originalCapturedViewLeft;
            }

            var5 = true;
         } else {
            var4 = this.originalCapturedViewLeft;
            var5 = false;
         }

         if(SwipeDismissBehavior.this.viewDragHelper.settleCapturedViewAt(var4, var1.getTop())) {
            ViewCompat.postOnAnimation(var1, SwipeDismissBehavior.this.new SettleRunnable(var1, var5));
         } else {
            if(var5 && SwipeDismissBehavior.this.listener != null) {
               SwipeDismissBehavior.this.listener.onDismiss(var1);
            }

         }
      }
      public boolean tryCaptureView(View var1, int var2) {
         return this.activePointerId == -1 && SwipeDismissBehavior.this.canSwipeDismissView(var1);
      }
   };
   float dragDismissThreshold = 0.5F;
   private boolean interceptingEvents;
   SwipeDismissBehavior.OnDismissListener listener;
   private float sensitivity = 0.0F;
   private boolean sensitivitySet;
   int swipeDirection = 2;
   ViewDragHelper viewDragHelper;


   static float clamp(float var0, float var1, float var2) {
      return Math.min(Math.max(var0, var1), var2);
   }

   static int clamp(int var0, int var1, int var2) {
      return Math.min(Math.max(var0, var1), var2);
   }

   private void ensureViewDragHelper(ViewGroup var1) {
      if(this.viewDragHelper == null) {
         ViewDragHelper var2;
         if(this.sensitivitySet) {
            var2 = ViewDragHelper.create(var1, this.sensitivity, this.dragCallback);
         } else {
            var2 = ViewDragHelper.create(var1, this.dragCallback);
         }

         this.viewDragHelper = var2;
      }

   }

   static float fraction(float var0, float var1, float var2) {
      return (var2 - var0) / (var1 - var0);
   }

   public boolean canSwipeDismissView(@NonNull View var1) {
      return true;
   }

   public int getDragState() {
      return this.viewDragHelper != null?this.viewDragHelper.getViewDragState():0;
   }

   public boolean onInterceptTouchEvent(CoordinatorLayout var1, V var2, MotionEvent var3) {
      boolean var5;
      label21: {
         var5 = this.interceptingEvents;
         int var4 = var3.getActionMasked();
         if(var4 != 3) {
            switch(var4) {
            case 0:
               this.interceptingEvents = var1.isPointInChildBounds(var2, (int)var3.getX(), (int)var3.getY());
               var5 = this.interceptingEvents;
               break label21;
            case 1:
               break;
            default:
               break label21;
            }
         }

         this.interceptingEvents = false;
      }

      if(var5) {
         this.ensureViewDragHelper(var1);
         return this.viewDragHelper.shouldInterceptTouchEvent(var3);
      } else {
         return false;
      }
   }

   public boolean onTouchEvent(CoordinatorLayout var1, V var2, MotionEvent var3) {
      if(this.viewDragHelper != null) {
         this.viewDragHelper.processTouchEvent(var3);
         return true;
      } else {
         return false;
      }
   }

   public void setDragDismissDistance(float var1) {
      this.dragDismissThreshold = clamp(0.0F, var1, 1.0F);
   }

   public void setEndAlphaSwipeDistance(float var1) {
      this.alphaEndSwipeDistance = clamp(0.0F, var1, 1.0F);
   }

   public void setListener(SwipeDismissBehavior.OnDismissListener var1) {
      this.listener = var1;
   }

   public void setSensitivity(float var1) {
      this.sensitivity = var1;
      this.sensitivitySet = true;
   }

   public void setStartAlphaSwipeDistance(float var1) {
      this.alphaStartSwipeDistance = clamp(0.0F, var1, 1.0F);
   }

   public void setSwipeDirection(int var1) {
      this.swipeDirection = var1;
   }

   class SettleRunnable implements Runnable {

      private final boolean dismiss;
      private final View view;


      SettleRunnable(View var2, boolean var3) {
         this.view = var2;
         this.dismiss = var3;
      }

      public void run() {
         if(SwipeDismissBehavior.this.viewDragHelper != null && SwipeDismissBehavior.this.viewDragHelper.continueSettling(true)) {
            ViewCompat.postOnAnimation(this.view, this);
         } else {
            if(this.dismiss && SwipeDismissBehavior.this.listener != null) {
               SwipeDismissBehavior.this.listener.onDismiss(this.view);
            }

         }
      }
   }

   public interface OnDismissListener {

      void onDismiss(View var1);

      void onDragStateChanged(int var1);
   }
}
