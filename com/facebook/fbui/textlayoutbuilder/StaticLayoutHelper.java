package com.facebook.fbui.textlayoutbuilder;

import android.os.Build.VERSION;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import android.text.StaticLayout.Builder;
import android.text.TextUtils.TruncateAt;
import com.facebook.fbui.textlayoutbuilder.proxy.StaticLayoutProxy;

class StaticLayoutHelper {

   private static final String SPACE_AND_ELLIPSIS = " …";


   public static boolean fixLayout(StaticLayout param0) {
      // $FF: Couldn't be decompiled
   }

   private static StaticLayout getStaticLayoutMaybeMaxLines(CharSequence var0, int var1, int var2, TextPaint var3, int var4, Alignment var5, float var6, float var7, boolean var8, TruncateAt var9, int var10, int var11, TextDirectionHeuristicCompat var12) {
      try {
         StaticLayout var14 = StaticLayoutProxy.create(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
         return var14;
      } catch (LinkageError var13) {
         return getStaticLayoutNoMaxLines(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
      }
   }

   private static StaticLayout getStaticLayoutNoMaxLines(CharSequence var0, int var1, int var2, TextPaint var3, int var4, Alignment var5, float var6, float var7, boolean var8, TruncateAt var9, int var10) {
      return new StaticLayout(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   public static StaticLayout make(CharSequence var0, int var1, int var2, TextPaint var3, int var4, Alignment var5, float var6, float var7, boolean var8, TruncateAt var9, int var10, int var11, TextDirectionHeuristicCompat var12, int var13, int var14, int var15, int[] var16, int[] var17) {
      if(VERSION.SDK_INT >= 23) {
         Builder var19 = Builder.obtain(var0, var1, var2, var3, var4).setAlignment(var5).setLineSpacing(var7, var6).setIncludePad(var8).setEllipsize(var9).setEllipsizedWidth(var10).setMaxLines(var11).setTextDirection(StaticLayoutProxy.fromTextDirectionHeuristicCompat(var12)).setBreakStrategy(var13).setHyphenationFrequency(var14).setIndents(var16, var17);
         if(VERSION.SDK_INT >= 26) {
            var19.setJustificationMode(var15);
         }

         return var19.build();
      } else {
         StaticLayout var20 = getStaticLayoutMaybeMaxLines(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
         StaticLayout var22 = var20;
         if(var11 > 0) {
            while(true) {
               CharSequence var18 = var0;
               var22 = var20;
               if(var20.getLineCount() <= var11) {
                  break;
               }

               var13 = var20.getLineStart(var11);
               if(var13 >= var2) {
                  var22 = var20;
                  break;
               }

               for(var2 = var13; var2 > var1 && Character.isSpace(var18.charAt(var2 - 1)); --var2) {
                  ;
               }

               var20 = getStaticLayoutMaybeMaxLines(var18, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
               if(var20.getLineCount() >= var11 && var20.getEllipsisCount(var11 - 1) == 0) {
                  StringBuilder var21 = new StringBuilder();
                  var21.append(var0.subSequence(var1, var2));
                  var21.append(" …");
                  String var23 = var21.toString();
                  var20 = getStaticLayoutMaybeMaxLines(var23, 0, var23.length(), var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
               }
            }
         }

         while(!fixLayout(var22)) {
            ;
         }

         return var22;
      }
   }

   private static void swap(int[] var0, int var1, int var2) {
      int var3 = var0[var1];
      var0[var1] = var0[var2];
      var0[var2] = var3;
   }
}
