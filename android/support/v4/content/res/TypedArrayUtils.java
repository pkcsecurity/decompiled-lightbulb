package android.support.v4.content.res;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.support.annotation.AnyRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleableRes;
import android.support.v4.content.res.ComplexColorCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import org.xmlpull.v1.XmlPullParser;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TypedArrayUtils {

   private static final String NAMESPACE = "http://schemas.android.com/apk/res/android";


   public static int getAttr(@NonNull Context var0, int var1, int var2) {
      TypedValue var3 = new TypedValue();
      var0.getTheme().resolveAttribute(var1, var3, true);
      return var3.resourceId != 0?var1:var2;
   }

   public static boolean getBoolean(@NonNull TypedArray var0, @StyleableRes int var1, @StyleableRes int var2, boolean var3) {
      return var0.getBoolean(var1, var0.getBoolean(var2, var3));
   }

   @Nullable
   public static Drawable getDrawable(@NonNull TypedArray var0, @StyleableRes int var1, @StyleableRes int var2) {
      Drawable var4 = var0.getDrawable(var1);
      Drawable var3 = var4;
      if(var4 == null) {
         var3 = var0.getDrawable(var2);
      }

      return var3;
   }

   public static int getInt(@NonNull TypedArray var0, @StyleableRes int var1, @StyleableRes int var2, int var3) {
      return var0.getInt(var1, var0.getInt(var2, var3));
   }

   public static boolean getNamedBoolean(@NonNull TypedArray var0, @NonNull XmlPullParser var1, @NonNull String var2, @StyleableRes int var3, boolean var4) {
      return !hasAttribute(var1, var2)?var4:var0.getBoolean(var3, var4);
   }

   @ColorInt
   public static int getNamedColor(@NonNull TypedArray var0, @NonNull XmlPullParser var1, @NonNull String var2, @StyleableRes int var3, @ColorInt int var4) {
      return !hasAttribute(var1, var2)?var4:var0.getColor(var3, var4);
   }

   public static ComplexColorCompat getNamedComplexColor(@NonNull TypedArray var0, @NonNull XmlPullParser var1, @Nullable Theme var2, @NonNull String var3, @StyleableRes int var4, @ColorInt int var5) {
      if(hasAttribute(var1, var3)) {
         TypedValue var7 = new TypedValue();
         var0.getValue(var4, var7);
         if(var7.type >= 28 && var7.type <= 31) {
            return ComplexColorCompat.from(var7.data);
         }

         ComplexColorCompat var6 = ComplexColorCompat.inflate(var0.getResources(), var0.getResourceId(var4, 0), var2);
         if(var6 != null) {
            return var6;
         }
      }

      return ComplexColorCompat.from(var5);
   }

   public static float getNamedFloat(@NonNull TypedArray var0, @NonNull XmlPullParser var1, @NonNull String var2, @StyleableRes int var3, float var4) {
      return !hasAttribute(var1, var2)?var4:var0.getFloat(var3, var4);
   }

   public static int getNamedInt(@NonNull TypedArray var0, @NonNull XmlPullParser var1, @NonNull String var2, @StyleableRes int var3, int var4) {
      return !hasAttribute(var1, var2)?var4:var0.getInt(var3, var4);
   }

   @AnyRes
   public static int getNamedResourceId(@NonNull TypedArray var0, @NonNull XmlPullParser var1, @NonNull String var2, @StyleableRes int var3, @AnyRes int var4) {
      return !hasAttribute(var1, var2)?var4:var0.getResourceId(var3, var4);
   }

   @Nullable
   public static String getNamedString(@NonNull TypedArray var0, @NonNull XmlPullParser var1, @NonNull String var2, @StyleableRes int var3) {
      return !hasAttribute(var1, var2)?null:var0.getString(var3);
   }

   @AnyRes
   public static int getResourceId(@NonNull TypedArray var0, @StyleableRes int var1, @StyleableRes int var2, @AnyRes int var3) {
      return var0.getResourceId(var1, var0.getResourceId(var2, var3));
   }

   @Nullable
   public static String getString(@NonNull TypedArray var0, @StyleableRes int var1, @StyleableRes int var2) {
      String var4 = var0.getString(var1);
      String var3 = var4;
      if(var4 == null) {
         var3 = var0.getString(var2);
      }

      return var3;
   }

   @Nullable
   public static CharSequence getText(@NonNull TypedArray var0, @StyleableRes int var1, @StyleableRes int var2) {
      CharSequence var4 = var0.getText(var1);
      CharSequence var3 = var4;
      if(var4 == null) {
         var3 = var0.getText(var2);
      }

      return var3;
   }

   @Nullable
   public static CharSequence[] getTextArray(@NonNull TypedArray var0, @StyleableRes int var1, @StyleableRes int var2) {
      CharSequence[] var4 = var0.getTextArray(var1);
      CharSequence[] var3 = var4;
      if(var4 == null) {
         var3 = var0.getTextArray(var2);
      }

      return var3;
   }

   public static boolean hasAttribute(@NonNull XmlPullParser var0, @NonNull String var1) {
      return var0.getAttributeValue("http://schemas.android.com/apk/res/android", var1) != null;
   }

   @NonNull
   public static TypedArray obtainAttributes(@NonNull Resources var0, @Nullable Theme var1, @NonNull AttributeSet var2, @NonNull int[] var3) {
      return var1 == null?var0.obtainAttributes(var2, var3):var1.obtainStyledAttributes(var2, var3, 0, 0);
   }

   @Nullable
   public static TypedValue peekNamedValue(@NonNull TypedArray var0, @NonNull XmlPullParser var1, @NonNull String var2, int var3) {
      return !hasAttribute(var1, var2)?null:var0.peekValue(var3);
   }
}
