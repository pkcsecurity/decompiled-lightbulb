package com.facebook.react.views.text;

import android.content.Context;
import android.content.res.ColorStateList;

public final class DefaultStyleValuesUtil {

   private DefaultStyleValuesUtil() {
      throw new AssertionError("Never invoke this for an Utility class!");
   }

   private static ColorStateList getDefaultTextAttribute(Context param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   public static ColorStateList getDefaultTextColor(Context var0) {
      return getDefaultTextAttribute(var0, 16842904);
   }

   public static int getDefaultTextColorHighlight(Context var0) {
      return getDefaultTextAttribute(var0, 16842905).getDefaultColor();
   }

   public static ColorStateList getDefaultTextColorHint(Context var0) {
      return getDefaultTextAttribute(var0, 16842906);
   }
}
