package com.facebook.litho.displaylist;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.HardwareCanvas;
import com.facebook.litho.displaylist.DisplayListException;
import com.facebook.litho.displaylist.PlatformDisplayList;
import java.lang.reflect.Constructor;
import javax.annotation.Nullable;

class DisplayListJB implements PlatformDisplayList {

   private static Class sDisplayListClass;
   private static Constructor sDisplayListConstructor;
   private static boolean sInitializationFailed;
   private static boolean sInitialized;
   protected final android.view.DisplayList mDisplayList;


   DisplayListJB(android.view.DisplayList var1) {
      this.mDisplayList = var1;
   }

   @Nullable
   static PlatformDisplayList createDisplayList(String var0) {
      android.view.DisplayList var1 = instantiateDisplayList(var0);
      return var1 == null?null:new DisplayListJB(var1);
   }

   private static void ensureInitialized() throws Exception {
      if(!sInitialized) {
         if(!sInitializationFailed) {
            sDisplayListClass = Class.forName("android.view.GLES20DisplayList");
            sDisplayListConstructor = sDisplayListClass.getDeclaredConstructor(new Class[]{String.class});
            sDisplayListConstructor.setAccessible(true);
            sInitialized = true;
         }
      }
   }

   @Nullable
   static android.view.DisplayList instantiateDisplayList(String var0) {
      label19: {
         boolean var1;
         try {
            ensureInitialized();
            var1 = sInitialized;
         } catch (Throwable var4) {
            sInitializationFailed = true;
            break label19;
         }

         if(!var1) {
            return null;
         }
      }

      try {
         android.view.DisplayList var5 = (android.view.DisplayList)sDisplayListConstructor.newInstance(new Object[]{var0});
         return var5;
      } catch (Throwable var3) {
         return null;
      }
   }

   public void clear() {
      this.mDisplayList.invalidate();
      this.mDisplayList.clear();
   }

   public void draw(Canvas var1) throws DisplayListException {
      if(!(var1 instanceof HardwareCanvas)) {
         throw new DisplayListException(new ClassCastException());
      } else {
         ((HardwareCanvas)var1).drawDisplayList(this.mDisplayList, (Rect)null, 0);
      }
   }

   public void end(Canvas var1) {
      ((HardwareCanvas)var1).onPostDraw();
      this.mDisplayList.end();
   }

   public boolean isValid() {
      return this.mDisplayList.isValid();
   }

   public void print(Canvas var1) throws DisplayListException {}

   public void setBounds(int var1, int var2, int var3, int var4) {
      this.mDisplayList.setLeftTopRightBottom(var1, var2, var3, var4);
      this.mDisplayList.setClipChildren(false);
   }

   public void setTranslationX(float var1) {
      this.mDisplayList.setTranslationX(var1);
   }

   public void setTranslationY(float var1) {
      this.mDisplayList.setTranslationY(var1);
   }

   public Canvas start(int var1, int var2) {
      HardwareCanvas var3 = this.mDisplayList.start();
      HardwareCanvas var4 = (HardwareCanvas)var3;
      var4.setViewport(var1, var2);
      var4.onPreDraw((Rect)null);
      return (Canvas)var3;
   }
}
