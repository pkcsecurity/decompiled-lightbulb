package com.facebook.litho.displaylist;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import com.facebook.litho.displaylist.DisplayListException;
import com.facebook.litho.displaylist.DisplayListJB;
import com.facebook.litho.displaylist.DisplayListJBMR2;
import com.facebook.litho.displaylist.DisplayListLollipop;
import com.facebook.litho.displaylist.DisplayListMarshmallow;
import com.facebook.litho.displaylist.DisplayListPostMarshmallow;
import com.facebook.litho.displaylist.PlatformDisplayList;

public class DisplayList {

   private int mBottom;
   private final PlatformDisplayList mDisplayListImpl;
   private int mLeft;
   private int mRight;
   private boolean mStarted;
   private int mTop;


   private DisplayList(PlatformDisplayList var1) {
      this.mDisplayListImpl = var1;
   }

   @Nullable
   public static DisplayList createDisplayList(@Nullable String var0) {
      PlatformDisplayList var1 = createPlatformDisplayList(var0);
      return var1 == null?null:new DisplayList(var1);
   }

   @Nullable
   private static PlatformDisplayList createPlatformDisplayList(@Nullable String var0) {
      int var1 = VERSION.SDK_INT;
      return var1 >= 24?DisplayListPostMarshmallow.createDisplayList(var0):(var1 >= 23?DisplayListMarshmallow.createDisplayList(var0):(var1 >= 21?DisplayListLollipop.createDisplayList(var0):(var1 >= 18?DisplayListJBMR2.createDisplayList(var0):(var1 >= 16?DisplayListJB.createDisplayList(var0):null))));
   }

   public void clear() throws DisplayListException {
      this.mDisplayListImpl.clear();
   }

   public void draw(Canvas var1) throws DisplayListException {
      this.mDisplayListImpl.draw(var1);
   }

   public void end(Canvas var1) throws DisplayListException {
      if(!this.mStarted) {
         throw new DisplayListException(new IllegalStateException("Can\'t end a DisplayList that is not started"));
      } else {
         this.mStarted = false;
         this.mDisplayListImpl.end(var1);
      }
   }

   public Rect getBounds() {
      return new Rect(this.mLeft, this.mTop, this.mRight, this.mBottom);
   }

   public boolean isValid() {
      return this.mDisplayListImpl.isValid();
   }

   void print(Canvas var1) throws DisplayListException {
      this.mDisplayListImpl.print(var1);
   }

   public void setBounds(int var1, int var2, int var3, int var4) throws DisplayListException {
      this.mLeft = var1;
      this.mTop = var2;
      this.mRight = var3;
      this.mBottom = var4;
      this.mDisplayListImpl.setBounds(var1, var2, var3, var4);
   }

   public void setTranslationX(float var1) throws DisplayListException {
      this.mDisplayListImpl.setTranslationX(var1);
   }

   public void setTranslationY(float var1) throws DisplayListException {
      this.mDisplayListImpl.setTranslationY(var1);
   }

   public Canvas start(int var1, int var2) throws DisplayListException {
      if(this.mStarted) {
         throw new DisplayListException(new IllegalStateException("Can\'t start a DisplayList that is already started"));
      } else {
         Canvas var3 = this.mDisplayListImpl.start(var1, var2);
         this.mStarted = true;
         return var3;
      }
   }
}
