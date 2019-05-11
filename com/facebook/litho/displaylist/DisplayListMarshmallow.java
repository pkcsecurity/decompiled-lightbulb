package com.facebook.litho.displaylist;

import android.graphics.Canvas;
import android.view.DisplayListCanvas;
import android.view.RenderNode;
import android.view.View;
import com.facebook.litho.displaylist.DisplayListException;
import com.facebook.litho.displaylist.PlatformDisplayList;
import com.facebook.litho.displaylist.Utils;
import java.lang.reflect.Method;
import javax.annotation.Nullable;

public class DisplayListMarshmallow implements PlatformDisplayList {

   protected static boolean sInitializationFailed;
   protected static boolean sInitialized;
   private static Class sRenderNodeClass;
   private static Method sStartMethod;
   protected final RenderNode mDisplayList;


   DisplayListMarshmallow(RenderNode var1) {
      this.mDisplayList = var1;
   }

   @Nullable
   static PlatformDisplayList createDisplayList(String var0) {
      try {
         ensureInitialized();
         if(sInitialized) {
            DisplayListMarshmallow var2 = new DisplayListMarshmallow(RenderNode.create(var0, (View)null));
            return var2;
         }
      } catch (Throwable var1) {
         sInitializationFailed = true;
      }

      return null;
   }

   protected static void ensureInitialized() throws Exception {
      if(!sInitialized) {
         if(!sInitializationFailed) {
            sRenderNodeClass = Class.forName("android.view.RenderNode");
            sStartMethod = sRenderNodeClass.getDeclaredMethod("start", new Class[]{Integer.TYPE, Integer.TYPE});
            sInitialized = true;
         }
      }
   }

   public void clear() {
      this.mDisplayList.destroyDisplayListData();
   }

   public void draw(Canvas var1) throws DisplayListException {
      if(!(var1 instanceof DisplayListCanvas)) {
         throw new DisplayListException(new ClassCastException());
      } else {
         ((DisplayListCanvas)var1).drawRenderNode(this.mDisplayList);
      }
   }

   public void end(Canvas var1) {
      this.mDisplayList.end((DisplayListCanvas)var1);
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

   public Canvas start(int var1, int var2) throws DisplayListException {
      return (Canvas)Utils.safeInvoke(sStartMethod, this.mDisplayList, new Object[]{Integer.valueOf(var1), Integer.valueOf(var2)});
   }
}
