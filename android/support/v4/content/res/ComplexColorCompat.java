package android.support.v4.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.content.res.Resources.Theme;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.ColorStateListInflaterCompat;
import android.support.v4.content.res.GradientColorInflaterCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class ComplexColorCompat {

   private static final String LOG_TAG = "ComplexColorCompat";
   private int mColor;
   private final ColorStateList mColorStateList;
   private final Shader mShader;


   private ComplexColorCompat(Shader var1, ColorStateList var2, @ColorInt int var3) {
      this.mShader = var1;
      this.mColorStateList = var2;
      this.mColor = var3;
   }

   @NonNull
   private static ComplexColorCompat createFromXml(@NonNull Resources var0, @ColorRes int var1, @Nullable Theme var2) throws IOException, XmlPullParserException {
      XmlResourceParser var4 = var0.getXml(var1);
      AttributeSet var6 = Xml.asAttributeSet(var4);

      int var3;
      byte var8;
      do {
         var3 = var4.next();
         var8 = 1;
      } while(var3 != 2 && var3 != 1);

      if(var3 != 2) {
         throw new XmlPullParserException("No start tag found");
      } else {
         String var5;
         label29: {
            var5 = var4.getName();
            var3 = var5.hashCode();
            if(var3 != 89650992) {
               if(var3 == 1191572447 && var5.equals("selector")) {
                  var8 = 0;
                  break label29;
               }
            } else if(var5.equals("gradient")) {
               break label29;
            }

            var8 = -1;
         }

         switch(var8) {
         case 0:
            return from(ColorStateListInflaterCompat.createFromXmlInner(var0, var4, var6, var2));
         case 1:
            return from(GradientColorInflaterCompat.createFromXmlInner(var0, var4, var6, var2));
         default:
            StringBuilder var7 = new StringBuilder();
            var7.append(var4.getPositionDescription());
            var7.append(": unsupported complex color tag ");
            var7.append(var5);
            throw new XmlPullParserException(var7.toString());
         }
      }
   }

   static ComplexColorCompat from(@ColorInt int var0) {
      return new ComplexColorCompat((Shader)null, (ColorStateList)null, var0);
   }

   static ComplexColorCompat from(@NonNull ColorStateList var0) {
      return new ComplexColorCompat((Shader)null, var0, var0.getDefaultColor());
   }

   static ComplexColorCompat from(@NonNull Shader var0) {
      return new ComplexColorCompat(var0, (ColorStateList)null, 0);
   }

   @Nullable
   public static ComplexColorCompat inflate(@NonNull Resources var0, @ColorRes int var1, @Nullable Theme var2) {
      try {
         ComplexColorCompat var4 = createFromXml(var0, var1, var2);
         return var4;
      } catch (Exception var3) {
         Log.e("ComplexColorCompat", "Failed to inflate ComplexColor.", var3);
         return null;
      }
   }

   @ColorInt
   public int getColor() {
      return this.mColor;
   }

   @Nullable
   public Shader getShader() {
      return this.mShader;
   }

   public boolean isGradient() {
      return this.mShader != null;
   }

   public boolean isStateful() {
      return this.mShader == null && this.mColorStateList != null && this.mColorStateList.isStateful();
   }

   public boolean onStateChanged(int[] var1) {
      if(this.isStateful()) {
         int var2 = this.mColorStateList.getColorForState(var1, this.mColorStateList.getDefaultColor());
         if(var2 != this.mColor) {
            this.mColor = var2;
            return true;
         }
      }

      return false;
   }

   public void setColor(@ColorInt int var1) {
      this.mColor = var1;
   }

   public boolean willDraw() {
      return this.isGradient() || this.mColor != 0;
   }
}
