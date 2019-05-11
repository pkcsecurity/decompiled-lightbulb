package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.widget.Button;
import com.google.android.gms.base.R;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.SignInButtonConfig;
import com.google.android.gms.common.util.DeviceProperties;

public final class SignInButtonImpl extends Button {

   public SignInButtonImpl(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public SignInButtonImpl(Context var1, AttributeSet var2) {
      super(var1, var2, 16842824);
   }

   private static int zaa(int var0, int var1, int var2, int var3) {
      switch(var0) {
      case 0:
         return var1;
      case 1:
         return var2;
      case 2:
         return var3;
      default:
         StringBuilder var4 = new StringBuilder(33);
         var4.append("Unknown color scheme: ");
         var4.append(var0);
         throw new IllegalStateException(var4.toString());
      }
   }

   public final void configure(Resources var1, int var2, int var3) {
      this.setTypeface(Typeface.DEFAULT_BOLD);
      this.setTextSize(14.0F);
      int var4 = (int)(var1.getDisplayMetrics().density * 48.0F + 0.5F);
      this.setMinHeight(var4);
      this.setMinWidth(var4);
      var4 = zaa(var3, R.drawable.common_google_signin_btn_icon_dark, R.drawable.common_google_signin_btn_icon_light, R.drawable.common_google_signin_btn_icon_light);
      int var5 = zaa(var3, R.drawable.common_google_signin_btn_text_dark, R.drawable.common_google_signin_btn_text_light, R.drawable.common_google_signin_btn_text_light);
      StringBuilder var7;
      switch(var2) {
      case 0:
      case 1:
         var4 = var5;
      case 2:
         Drawable var6 = DrawableCompat.wrap(var1.getDrawable(var4));
         DrawableCompat.setTintList(var6, var1.getColorStateList(R.color.common_google_signin_btn_tint));
         DrawableCompat.setTintMode(var6, Mode.SRC_ATOP);
         this.setBackgroundDrawable(var6);
         this.setTextColor((ColorStateList)Preconditions.checkNotNull(var1.getColorStateList(zaa(var3, R.color.common_google_signin_btn_text_dark, R.color.common_google_signin_btn_text_light, R.color.common_google_signin_btn_text_light))));
         switch(var2) {
         case 0:
            this.setText(var1.getString(R.string.common_signin_button_text));
            break;
         case 1:
            this.setText(var1.getString(R.string.common_signin_button_text_long));
            break;
         case 2:
            this.setText((CharSequence)null);
            break;
         default:
            var7 = new StringBuilder(32);
            var7.append("Unknown button size: ");
            var7.append(var2);
            throw new IllegalStateException(var7.toString());
         }

         this.setTransformationMethod((TransformationMethod)null);
         if(DeviceProperties.isWearable(this.getContext())) {
            this.setGravity(19);
         }

         return;
      default:
         var7 = new StringBuilder(32);
         var7.append("Unknown button size: ");
         var7.append(var2);
         throw new IllegalStateException(var7.toString());
      }
   }

   public final void configure(Resources var1, SignInButtonConfig var2) {
      this.configure(var1, var2.getButtonSize(), var2.getColorScheme());
   }
}
