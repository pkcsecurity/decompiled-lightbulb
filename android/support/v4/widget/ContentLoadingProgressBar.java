package android.support.v4.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ContentLoadingProgressBar extends ProgressBar {

   private static final int MIN_DELAY = 500;
   private static final int MIN_SHOW_TIME = 500;
   private final Runnable mDelayedHide;
   private final Runnable mDelayedShow;
   boolean mDismissed;
   boolean mPostedHide;
   boolean mPostedShow;
   long mStartTime;


   public ContentLoadingProgressBar(@NonNull Context var1) {
      this(var1, (AttributeSet)null);
   }

   public ContentLoadingProgressBar(@NonNull Context var1, @Nullable AttributeSet var2) {
      super(var1, var2, 0);
      this.mStartTime = -1L;
      this.mPostedHide = false;
      this.mPostedShow = false;
      this.mDismissed = false;
      this.mDelayedHide = new Runnable() {
         public void run() {
            ContentLoadingProgressBar.this.mPostedHide = false;
            ContentLoadingProgressBar.this.mStartTime = -1L;
            ContentLoadingProgressBar.this.setVisibility(8);
         }
      };
      this.mDelayedShow = new Runnable() {
         public void run() {
            ContentLoadingProgressBar.this.mPostedShow = false;
            if(!ContentLoadingProgressBar.this.mDismissed) {
               ContentLoadingProgressBar.this.mStartTime = System.currentTimeMillis();
               ContentLoadingProgressBar.this.setVisibility(0);
            }

         }
      };
   }

   private void removeCallbacks() {
      this.removeCallbacks(this.mDelayedHide);
      this.removeCallbacks(this.mDelayedShow);
   }

   public void hide() {
      // $FF: Couldn't be decompiled
   }

   public void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.removeCallbacks();
   }

   public void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      this.removeCallbacks();
   }

   public void show() {
      synchronized(this){}

      try {
         this.mStartTime = -1L;
         this.mDismissed = false;
         this.removeCallbacks(this.mDelayedHide);
         this.mPostedHide = false;
         if(!this.mPostedShow) {
            this.postDelayed(this.mDelayedShow, 500L);
            this.mPostedShow = true;
         }
      } finally {
         ;
      }

   }
}
