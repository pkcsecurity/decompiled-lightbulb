package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public class ViewUtils {

   @KeepForSdk
   public static String getXmlAttributeString(String var0, String var1, Context var2, AttributeSet var3, boolean var4, boolean var5, String var6) {
      if(var3 == null) {
         var0 = null;
      } else {
         var0 = var3.getAttributeValue(var0, var1);
      }

      String var15 = var0;
      if(var0 != null) {
         var15 = var0;
         if(var0.startsWith("@string/")) {
            var15 = var0;
            if(var4) {
               String var7 = var0.substring(8);
               String var8 = var2.getPackageName();
               TypedValue var16 = new TypedValue();

               try {
                  Resources var13 = var2.getResources();
                  StringBuilder var9 = new StringBuilder(String.valueOf(var8).length() + 8 + String.valueOf(var7).length());
                  var9.append(var8);
                  var9.append(":string/");
                  var9.append(var7);
                  var13.getValue(var9.toString(), var16, true);
               } catch (NotFoundException var10) {
                  StringBuilder var12 = new StringBuilder(String.valueOf(var1).length() + 30 + String.valueOf(var0).length());
                  var12.append("Could not find resource for ");
                  var12.append(var1);
                  var12.append(": ");
                  var12.append(var0);
                  Log.w(var6, var12.toString());
               }

               if(var16.string != null) {
                  var15 = var16.string.toString();
               } else {
                  String var14 = String.valueOf(var16);
                  StringBuilder var17 = new StringBuilder(String.valueOf(var1).length() + 28 + String.valueOf(var14).length());
                  var17.append("Resource ");
                  var17.append(var1);
                  var17.append(" was not a string: ");
                  var17.append(var14);
                  Log.w(var6, var17.toString());
                  var15 = var0;
               }
            }
         }
      }

      if(var5 && var15 == null) {
         StringBuilder var11 = new StringBuilder(String.valueOf(var1).length() + 33);
         var11.append("Required XML attribute \"");
         var11.append(var1);
         var11.append("\" missing");
         Log.w(var6, var11.toString());
      }

      return var15;
   }
}
