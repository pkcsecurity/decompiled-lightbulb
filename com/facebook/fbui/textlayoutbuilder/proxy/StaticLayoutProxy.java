package com.facebook.fbui.textlayoutbuilder.proxy;

import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import android.text.TextUtils.TruncateAt;

public class StaticLayoutProxy {

   public static StaticLayout create(CharSequence var0, int var1, int var2, TextPaint var3, int var4, Alignment var5, float var6, float var7, boolean var8, TruncateAt var9, int var10, int var11, TextDirectionHeuristicCompat var12) {
      try {
         StaticLayout var13 = new StaticLayout(var0, var1, var2, var3, var4, var5, fromTextDirectionHeuristicCompat(var12), var6, var7, var8, var9, var10, var11);
         return var13;
      } catch (IllegalArgumentException var14) {
         if(var14.getMessage().contains("utext_close")) {
            return new StaticLayout(var0, var1, var2, var3, var4, var5, fromTextDirectionHeuristicCompat(var12), var6, var7, var8, var9, var10, var11);
         } else {
            throw var14;
         }
      }
   }

   public static TextDirectionHeuristic fromTextDirectionHeuristicCompat(TextDirectionHeuristicCompat var0) {
      return var0 == TextDirectionHeuristicsCompat.LTR?TextDirectionHeuristics.LTR:(var0 == TextDirectionHeuristicsCompat.RTL?TextDirectionHeuristics.RTL:(var0 == TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR?TextDirectionHeuristics.FIRSTSTRONG_LTR:(var0 == TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL?TextDirectionHeuristics.FIRSTSTRONG_RTL:(var0 == TextDirectionHeuristicsCompat.ANYRTL_LTR?TextDirectionHeuristics.ANYRTL_LTR:(var0 == TextDirectionHeuristicsCompat.LOCALE?TextDirectionHeuristics.LOCALE:TextDirectionHeuristics.FIRSTSTRONG_LTR)))));
   }
}
