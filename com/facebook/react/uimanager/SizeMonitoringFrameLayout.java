package com.facebook.react.uimanager;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import javax.annotation.Nullable;

public class SizeMonitoringFrameLayout extends FrameLayout {

   @Nullable
   private SizeMonitoringFrameLayout.OnSizeChangedListener mOnSizeChangedListener;


   public SizeMonitoringFrameLayout(Context var1) {
      super(var1);
   }

   public SizeMonitoringFrameLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public SizeMonitoringFrameLayout(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      super.onSizeChanged(var1, var2, var3, var4);
      if(this.mOnSizeChangedListener != null) {
         this.mOnSizeChangedListener.onSizeChanged(var1, var2, var3, var4);
      }

   }

   public void setOnSizeChangedListener(SizeMonitoringFrameLayout.OnSizeChangedListener var1) {
      this.mOnSizeChangedListener = var1;
   }

   public interface OnSizeChangedListener {

      void onSizeChanged(int var1, int var2, int var3, int var4);
   }
}
