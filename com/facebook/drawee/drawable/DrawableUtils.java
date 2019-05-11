package com.facebook.drawee.drawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import com.facebook.drawee.drawable.CloneableDrawable;
import com.facebook.drawee.drawable.DrawableProperties;
import com.facebook.drawee.drawable.TransformAwareDrawable;
import com.facebook.drawee.drawable.TransformCallback;
import javax.annotation.Nullable;

public class DrawableUtils {

   @Nullable
   public static Drawable cloneDrawable(Drawable var0) {
      if(var0 instanceof CloneableDrawable) {
         return ((CloneableDrawable)var0).cloneDrawable();
      } else {
         ConstantState var1 = var0.getConstantState();
         return var1 != null?var1.newDrawable():null;
      }
   }

   public static void copyProperties(Drawable var0, Drawable var1) {
      if(var1 != null && var0 != null) {
         if(var0 != var1) {
            var0.setBounds(var1.getBounds());
            var0.setChangingConfigurations(var1.getChangingConfigurations());
            var0.setLevel(var1.getLevel());
            var0.setVisible(var1.isVisible(), false);
            var0.setState(var1.getState());
         }
      }
   }

   public static int getOpacityFromColor(int var0) {
      var0 >>>= 24;
      return var0 == 255?-1:(var0 == 0?-2:-3);
   }

   public static int multiplyColorAlpha(int var0, int var1) {
      return var1 == 255?var0:(var1 == 0?var0 & 16777215:var0 & 16777215 | (var0 >>> 24) * (var1 + (var1 >> 7)) >> 8 << 24);
   }

   public static void setCallbacks(Drawable var0, @Nullable Callback var1, @Nullable TransformCallback var2) {
      if(var0 != null) {
         var0.setCallback(var1);
         if(var0 instanceof TransformAwareDrawable) {
            ((TransformAwareDrawable)var0).setTransformCallback(var2);
         }
      }

   }

   public static void setDrawableProperties(Drawable var0, DrawableProperties var1) {
      if(var0 != null) {
         if(var1 != null) {
            var1.applyTo(var0);
         }
      }
   }
}
