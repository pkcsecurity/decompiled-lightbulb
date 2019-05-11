package com.facebook.react.flat;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import com.facebook.react.flat.AndroidView;
import com.facebook.react.flat.FlatShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.yoga.YogaUnit;
import com.facebook.yoga.YogaValue;

class FlatReactModalShadowNode extends FlatShadowNode implements AndroidView {

   private final Point mMaxPoint = new Point();
   private final Point mMinPoint = new Point();
   private boolean mPaddingChanged;


   FlatReactModalShadowNode() {
      this.forceMountToView();
      this.forceMountChildrenToView();
   }

   @TargetApi(16)
   public void addChildAt(ReactShadowNodeImpl var1, int var2) {
      super.addChildAt(var1, var2);
      Display var4 = ((WindowManager)this.getThemedContext().getSystemService("window")).getDefaultDisplay();
      var4.getCurrentSizeRange(this.mMinPoint, this.mMaxPoint);
      var2 = var4.getRotation();
      int var3;
      if(var2 != 0 && var2 != 2) {
         var3 = this.mMaxPoint.x;
         var2 = this.mMinPoint.y;
      } else {
         var3 = this.mMinPoint.x;
         var2 = this.mMaxPoint.y;
      }

      var1.setStyleWidth((float)var3);
      var1.setStyleHeight((float)var2);
   }

   public boolean isPaddingChanged() {
      return this.mPaddingChanged;
   }

   public boolean needsCustomLayoutForChildren() {
      return false;
   }

   public void resetPaddingChanged() {
      this.mPaddingChanged = false;
   }

   public void setPadding(int var1, float var2) {
      YogaValue var3 = this.getStylePadding(var1);
      if(var3.unit != YogaUnit.POINT || var3.value != var2) {
         super.setPadding(var1, var2);
         this.mPaddingChanged = true;
         this.markUpdated();
      }

   }

   public void setPaddingPercent(int var1, float var2) {
      YogaValue var3 = this.getStylePadding(var1);
      if(var3.unit != YogaUnit.PERCENT || var3.value != var2) {
         super.setPadding(var1, var2);
         this.mPaddingChanged = true;
         this.markUpdated();
      }

   }
}
