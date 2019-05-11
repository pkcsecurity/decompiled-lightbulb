package com.facebook.appevents.codeless.internal;

import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import com.facebook.appevents.codeless.internal.ViewHierarchy;

public class SensitiveUserDataUtils {

   private static boolean isCreditCard(TextView var0) {
      String var7 = ViewHierarchy.getTextOfView(var0).replaceAll("\\s", "");
      int var1 = var7.length();
      boolean var6 = false;
      if(var1 >= 12) {
         if(var1 > 19) {
            return false;
         } else {
            int var3 = var1 - 1;
            int var4 = 0;

            for(boolean var2 = false; var3 >= 0; --var3) {
               char var8 = var7.charAt(var3);
               if(var8 < 48) {
                  return false;
               }

               if(var8 > 57) {
                  return false;
               }

               int var5 = var8 - 48;
               var1 = var5;
               if(var2) {
                  var5 *= 2;
                  var1 = var5;
                  if(var5 > 9) {
                     var1 = var5 % 10 + 1;
                  }
               }

               var4 += var1;
               var2 ^= true;
            }

            if(var4 % 10 == 0) {
               var6 = true;
            }

            return var6;
         }
      } else {
         return false;
      }
   }

   private static boolean isEmail(TextView var0) {
      if(var0.getInputType() == 32) {
         return true;
      } else {
         String var1 = ViewHierarchy.getTextOfView(var0);
         return var1 != null && var1.length() != 0?Patterns.EMAIL_ADDRESS.matcher(var1).matches():false;
      }
   }

   private static boolean isPassword(TextView var0) {
      return var0.getInputType() == 128?true:var0.getTransformationMethod() instanceof PasswordTransformationMethod;
   }

   private static boolean isPersonName(TextView var0) {
      return var0.getInputType() == 96;
   }

   private static boolean isPhoneNumber(TextView var0) {
      return var0.getInputType() == 3;
   }

   private static boolean isPostalAddress(TextView var0) {
      return var0.getInputType() == 112;
   }

   public static boolean isSensitiveUserData(View var0) {
      boolean var2 = var0 instanceof TextView;
      boolean var1 = false;
      if(!var2) {
         return false;
      } else {
         TextView var3 = (TextView)var0;
         if(isPassword(var3) || isCreditCard(var3) || isPersonName(var3) || isPostalAddress(var3) || isPhoneNumber(var3) || isEmail(var3)) {
            var1 = true;
         }

         return var1;
      }
   }
}
