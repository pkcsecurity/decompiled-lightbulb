package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region.Op;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.art.ARTVirtualNode;
import com.facebook.react.views.art.PropHelper;
import javax.annotation.Nullable;

public class ARTGroupShadowNode extends ARTVirtualNode {

   @Nullable
   protected RectF mClipping;


   private static RectF createClipping(float[] var0) {
      if(var0.length != 4) {
         throw new JSApplicationIllegalArgumentException("Clipping should be array of length 4 (e.g. [x, y, width, height])");
      } else {
         return new RectF(var0[0], var0[1], var0[0] + var0[2], var0[1] + var0[3]);
      }
   }

   public void draw(Canvas var1, Paint var2, float var3) {
      var3 *= this.mOpacity;
      if(var3 > 0.01F) {
         this.saveAndSetupCanvas(var1);
         if(this.mClipping != null) {
            var1.clipRect(this.mClipping.left * this.mScale, this.mClipping.top * this.mScale, this.mClipping.right * this.mScale, this.mClipping.bottom * this.mScale, Op.REPLACE);
         }

         for(int var4 = 0; var4 < this.getChildCount(); ++var4) {
            ARTVirtualNode var5 = (ARTVirtualNode)this.getChildAt(var4);
            var5.draw(var1, var2, var3);
            var5.markUpdateSeen();
         }

         this.restoreCanvas(var1);
      }

   }

   public boolean isVirtual() {
      return true;
   }

   @ReactProp(
      name = "clipping"
   )
   public void setClipping(@Nullable ReadableArray var1) {
      float[] var2 = PropHelper.toFloatArray(var1);
      if(var2 != null) {
         this.mClipping = createClipping(var2);
         this.markUpdated();
      }

   }
}
