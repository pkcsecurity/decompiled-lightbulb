package android.support.v4.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.compat.R;
import android.support.v4.content.res.GrowingArrayUtils;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.Xml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class ColorStateListInflaterCompat {

   private static final int DEFAULT_COLOR = -65536;


   @NonNull
   public static ColorStateList createFromXml(@NonNull Resources var0, @NonNull XmlPullParser var1, @Nullable Theme var2) throws XmlPullParserException, IOException {
      AttributeSet var4 = Xml.asAttributeSet(var1);

      int var3;
      do {
         var3 = var1.next();
      } while(var3 != 2 && var3 != 1);

      if(var3 != 2) {
         throw new XmlPullParserException("No start tag found");
      } else {
         return createFromXmlInner(var0, var1, var4, var2);
      }
   }

   @NonNull
   public static ColorStateList createFromXmlInner(@NonNull Resources var0, @NonNull XmlPullParser var1, @NonNull AttributeSet var2, @Nullable Theme var3) throws XmlPullParserException, IOException {
      String var4 = var1.getName();
      if(!var4.equals("selector")) {
         StringBuilder var5 = new StringBuilder();
         var5.append(var1.getPositionDescription());
         var5.append(": invalid color state list tag ");
         var5.append(var4);
         throw new XmlPullParserException(var5.toString());
      } else {
         return inflate(var0, var1, var2, var3);
      }
   }

   private static ColorStateList inflate(@NonNull Resources var0, @NonNull XmlPullParser var1, @NonNull AttributeSet var2, @Nullable Theme var3) throws XmlPullParserException, IOException {
      int var10 = var1.getDepth() + 1;
      int[][] var13 = new int[20][];
      int[] var14 = new int[var13.length];
      int var5 = 0;

      while(true) {
         int var6 = var1.next();
         if(var6 == 1) {
            break;
         }

         int var7 = var1.getDepth();
         if(var7 < var10 && var6 == 3) {
            break;
         }

         if(var6 == 2 && var7 <= var10 && var1.getName().equals("item")) {
            TypedArray var15 = obtainAttributes(var0, var3, var2, R.styleable.ColorStateListItem);
            int var11 = var15.getColor(R.styleable.ColorStateListItem_android_color, -65281);
            float var4 = 1.0F;
            if(var15.hasValue(R.styleable.ColorStateListItem_android_alpha)) {
               var4 = var15.getFloat(R.styleable.ColorStateListItem_android_alpha, 1.0F);
            } else if(var15.hasValue(R.styleable.ColorStateListItem_alpha)) {
               var4 = var15.getFloat(R.styleable.ColorStateListItem_alpha, 1.0F);
            }

            var15.recycle();
            int var12 = var2.getAttributeCount();
            int[] var18 = new int[var12];
            var6 = 0;

            int var8;
            for(var7 = 0; var6 < var12; var7 = var8) {
               int var9 = var2.getAttributeNameResource(var6);
               var8 = var7;
               if(var9 != 16843173) {
                  var8 = var7;
                  if(var9 != 16843551) {
                     var8 = var7;
                     if(var9 != R.attr.alpha) {
                        if(var2.getAttributeBooleanValue(var6, false)) {
                           var8 = var9;
                        } else {
                           var8 = -var9;
                        }

                        var18[var7] = var8;
                        var8 = var7 + 1;
                     }
                  }
               }

               ++var6;
            }

            var18 = StateSet.trimStateSet(var18, var7);
            var6 = modulateColorAlpha(var11, var4);
            if(var5 != 0) {
               var7 = var18.length;
            }

            var14 = GrowingArrayUtils.append(var14, var5, var6);
            var13 = (int[][])GrowingArrayUtils.append(var13, var5, var18);
            ++var5;
         }
      }

      int[] var16 = new int[var5];
      int[][] var17 = new int[var5][];
      System.arraycopy(var14, 0, var16, 0, var5);
      System.arraycopy(var13, 0, var17, 0, var5);
      return new ColorStateList(var17, var16);
   }

   @ColorInt
   private static int modulateColorAlpha(@ColorInt int var0, 
      @FloatRange(
         from = 0.0D,
         to = 1.0D
      ) float var1) {
      return var0 & 16777215 | Math.round((float)Color.alpha(var0) * var1) << 24;
   }

   private static TypedArray obtainAttributes(Resources var0, Theme var1, AttributeSet var2, int[] var3) {
      return var1 == null?var0.obtainAttributes(var2, var3):var1.obtainStyledAttributes(var2, var3, 0, 0);
   }
}
