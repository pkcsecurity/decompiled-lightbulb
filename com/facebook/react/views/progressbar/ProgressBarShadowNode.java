package com.facebook.react.views.progressbar;

import android.util.SparseIntArray;
import android.view.View.MeasureSpec;
import android.widget.ProgressBar;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.progressbar.ReactProgressBarViewManager;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNode;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;

public class ProgressBarShadowNode extends LayoutShadowNode implements YogaMeasureFunction {

   private final SparseIntArray mHeight = new SparseIntArray();
   private final Set<Integer> mMeasured = new HashSet();
   private String mStyle = "Normal";
   private final SparseIntArray mWidth = new SparseIntArray();


   public ProgressBarShadowNode() {
      this.setMeasureFunction(this);
   }

   @Nullable
   public String getStyle() {
      return this.mStyle;
   }

   public long measure(YogaNode var1, float var2, YogaMeasureMode var3, float var4, YogaMeasureMode var5) {
      int var6 = ReactProgressBarViewManager.getStyleFromString(this.getStyle());
      if(!this.mMeasured.contains(Integer.valueOf(var6))) {
         ProgressBar var8 = ReactProgressBarViewManager.createProgressBar(this.getThemedContext(), var6);
         int var7 = MeasureSpec.makeMeasureSpec(-2, 0);
         var8.measure(var7, var7);
         this.mHeight.put(var6, var8.getMeasuredHeight());
         this.mWidth.put(var6, var8.getMeasuredWidth());
         this.mMeasured.add(Integer.valueOf(var6));
      }

      return YogaMeasureOutput.make(this.mWidth.get(var6), this.mHeight.get(var6));
   }

   @ReactProp(
      name = "styleAttr"
   )
   public void setStyle(@Nullable String var1) {
      String var2 = var1;
      if(var1 == null) {
         var2 = "Normal";
      }

      this.mStyle = var2;
   }
}
