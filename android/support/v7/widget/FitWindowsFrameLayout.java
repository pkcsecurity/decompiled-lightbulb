package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.FitWindowsViewGroup;
import android.util.AttributeSet;
import android.widget.FrameLayout;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class FitWindowsFrameLayout extends FrameLayout implements FitWindowsViewGroup {

   private FitWindowsViewGroup.OnFitSystemWindowsListener mListener;


   public FitWindowsFrameLayout(Context var1) {
      super(var1);
   }

   public FitWindowsFrameLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   protected boolean fitSystemWindows(Rect var1) {
      if(this.mListener != null) {
         this.mListener.onFitSystemWindows(var1);
      }

      return super.fitSystemWindows(var1);
   }

   public void setOnFitSystemWindowsListener(FitWindowsViewGroup.OnFitSystemWindowsListener var1) {
      this.mListener = var1;
   }
}
