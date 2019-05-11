package com.facebook.litho.displaylist;

import android.support.annotation.Nullable;
import android.view.RenderNode;
import android.view.View;
import com.facebook.litho.displaylist.DisplayListMarshmallow;
import com.facebook.litho.displaylist.PlatformDisplayList;

public class DisplayListPostMarshmallow extends DisplayListMarshmallow {

   private DisplayListPostMarshmallow(RenderNode var1) {
      super(var1);
   }

   @Nullable
   static PlatformDisplayList createDisplayList(String var0) {
      try {
         ensureInitialized();
         if(sInitialized) {
            DisplayListPostMarshmallow var2 = new DisplayListPostMarshmallow(RenderNode.create(var0, (View)null));
            return var2;
         }
      } catch (Throwable var1) {
         sInitializationFailed = true;
      }

      return null;
   }

   public void clear() {
      this.mDisplayList.discardDisplayList();
   }
}
