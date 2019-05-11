package com.facebook.react.flat;

import android.text.Layout;
import android.text.Spanned;
import com.facebook.react.flat.NodeRegion;
import com.facebook.react.flat.RCTRawText;
import javax.annotation.Nullable;

final class TextNodeRegion extends NodeRegion {

   @Nullable
   private Layout mLayout;


   TextNodeRegion(float var1, float var2, float var3, float var4, int var5, boolean var6, @Nullable Layout var7) {
      super(var1, var2, var3, var4, var5, var6);
      this.mLayout = var7;
   }

   @Nullable
   Layout getLayout() {
      return this.mLayout;
   }

   int getReactTag(float var1, float var2) {
      if(this.mLayout != null) {
         CharSequence var5 = this.mLayout.getText();
         if(var5 instanceof Spanned) {
            int var4 = Math.round(var2 - this.getTop());
            if(var4 >= this.mLayout.getLineTop(0) && var4 < this.mLayout.getLineBottom(this.mLayout.getLineCount() - 1)) {
               float var3 = (float)Math.round(var1 - this.getLeft());
               var4 = this.mLayout.getLineForVertical(var4);
               if(this.mLayout.getLineLeft(var4) <= var3 && var3 <= this.mLayout.getLineRight(var4)) {
                  var4 = this.mLayout.getOffsetForHorizontal(var4, var3);
                  RCTRawText[] var6 = (RCTRawText[])((Spanned)var5).getSpans(var4, var4, RCTRawText.class);
                  if(var6.length != 0) {
                     return var6[0].getReactTag();
                  }
               }
            }
         }
      }

      return super.getReactTag(var1, var2);
   }

   boolean matchesTag(int var1) {
      if(super.matchesTag(var1)) {
         return true;
      } else {
         if(this.mLayout != null) {
            Spanned var4 = (Spanned)this.mLayout.getText();
            RCTRawText[] var5 = (RCTRawText[])var4.getSpans(0, var4.length(), RCTRawText.class);
            int var3 = var5.length;

            for(int var2 = 0; var2 < var3; ++var2) {
               if(var5[var2].getReactTag() == var1) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public void setLayout(Layout var1) {
      this.mLayout = var1;
   }
}
