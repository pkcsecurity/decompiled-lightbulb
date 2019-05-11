package com.facebook.litho.displaylist;

import android.graphics.Canvas;
import android.view.HardwareCanvas;
import android.view.RenderNode;
import android.view.View;
import com.facebook.litho.displaylist.DisplayListException;
import com.facebook.litho.displaylist.PlatformDisplayList;

public class DisplayListLollipop implements PlatformDisplayList {

   private final RenderNode mDisplayList;


   private DisplayListLollipop(RenderNode var1) {
      this.mDisplayList = var1;
   }

   static PlatformDisplayList createDisplayList(String var0) {
      return new DisplayListLollipop(RenderNode.create(var0, (View)null));
   }

   public void clear() {
      this.mDisplayList.destroyDisplayListData();
   }

   public void draw(Canvas var1) throws DisplayListException {
      if(!(var1 instanceof HardwareCanvas)) {
         throw new DisplayListException(new ClassCastException());
      } else {
         ((HardwareCanvas)var1).drawRenderNode(this.mDisplayList);
      }
   }

   public void end(Canvas var1) {
      this.mDisplayList.end((HardwareCanvas)var1);
   }

   public boolean isValid() {
      return this.mDisplayList.isValid();
   }

   public void print(Canvas var1) {
      this.mDisplayList.output();
   }

   public void setBounds(int var1, int var2, int var3, int var4) {
      this.mDisplayList.setLeftTopRightBottom(var1, var2, var3, var4);
      this.mDisplayList.setClipToBounds(false);
   }

   public void setTranslationX(float var1) {
      this.mDisplayList.setTranslationX(var1);
   }

   public void setTranslationY(float var1) {
      this.mDisplayList.setTranslationY(var1);
   }

   public Canvas start(int var1, int var2) {
      return (Canvas)this.mDisplayList.start(var1, var2);
   }
}
