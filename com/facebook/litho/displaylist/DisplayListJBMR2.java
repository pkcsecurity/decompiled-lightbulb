package com.facebook.litho.displaylist;

import android.graphics.Canvas;
import com.facebook.litho.displaylist.DisplayListJB;
import com.facebook.litho.displaylist.PlatformDisplayList;
import javax.annotation.Nullable;

class DisplayListJBMR2 extends DisplayListJB {

   DisplayListJBMR2(android.view.DisplayList var1) {
      super(var1);
   }

   @Nullable
   static PlatformDisplayList createDisplayList(String var0) {
      android.view.DisplayList var1 = instantiateDisplayList(var0);
      return var1 == null?null:new DisplayListJBMR2(var1);
   }

   public void clear() {
      this.mDisplayList.clear();
   }

   public void end(Canvas var1) {
      this.mDisplayList.end();
   }

   public void setBounds(int var1, int var2, int var3, int var4) {
      this.mDisplayList.setLeftTopRightBottom(var1, var2, var3, var4);
      this.mDisplayList.setClipToBounds(false);
   }

   public Canvas start(int var1, int var2) {
      return (Canvas)this.mDisplayList.start(var1, var2);
   }
}
