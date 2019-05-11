package com.facebook.react.views.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.util.TypedValue;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.SoftAssertions;

public class ReactDrawableHelper {

   private static final TypedValue sResolveOutValue = new TypedValue();


   public static Drawable createDrawableFromJSDescription(Context var0, ReadableMap var1) {
      String var3 = var1.getString("type");
      int var2;
      StringBuilder var4;
      if("ThemeAttrAndroid".equals(var3)) {
         String var6 = var1.getString("attribute");
         SoftAssertions.assertNotNull(var6);
         var2 = var0.getResources().getIdentifier(var6, "attr", "android");
         if(var2 == 0) {
            var4 = new StringBuilder();
            var4.append("Attribute ");
            var4.append(var6);
            var4.append(" couldn\'t be found in the resource list");
            throw new JSApplicationIllegalArgumentException(var4.toString());
         } else if(var0.getTheme().resolveAttribute(var2, sResolveOutValue, true)) {
            return VERSION.SDK_INT >= 21?var0.getResources().getDrawable(sResolveOutValue.resourceId, var0.getTheme()):var0.getResources().getDrawable(sResolveOutValue.resourceId);
         } else {
            var4 = new StringBuilder();
            var4.append("Attribute ");
            var4.append(var6);
            var4.append(" couldn\'t be resolved into a drawable");
            throw new JSApplicationIllegalArgumentException(var4.toString());
         }
      } else if("RippleAndroid".equals(var3)) {
         if(VERSION.SDK_INT < 21) {
            throw new JSApplicationIllegalArgumentException("Ripple drawable is not available on android API <21");
         } else {
            if(var1.hasKey("color") && !var1.isNull("color")) {
               var2 = var1.getInt("color");
            } else {
               if(!var0.getTheme().resolveAttribute(16843820, sResolveOutValue, true)) {
                  throw new JSApplicationIllegalArgumentException("Attribute colorControlHighlight couldn\'t be resolved into a drawable");
               }

               var2 = var0.getResources().getColor(sResolveOutValue.resourceId);
            }

            ColorDrawable var5;
            if(var1.hasKey("borderless") && !var1.isNull("borderless") && var1.getBoolean("borderless")) {
               var5 = null;
            } else {
               var5 = new ColorDrawable(-1);
            }

            return new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{var2}), (Drawable)null, var5);
         }
      } else {
         var4 = new StringBuilder();
         var4.append("Invalid type for android drawable: ");
         var4.append(var3);
         throw new JSApplicationIllegalArgumentException(var4.toString());
      }
   }
}
