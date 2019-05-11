package com.google.android.gms.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.SignInButtonCreator;
import com.google.android.gms.common.internal.SignInButtonImpl;
import fl.a;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SignInButton extends FrameLayout implements OnClickListener {

   public static final int COLOR_AUTO = 2;
   public static final int COLOR_DARK = 0;
   public static final int COLOR_LIGHT = 1;
   public static final int SIZE_ICON_ONLY = 2;
   public static final int SIZE_STANDARD = 0;
   public static final int SIZE_WIDE = 1;
   private int mColor;
   private int mSize;
   private View zaas;
   private OnClickListener zaat;


   public SignInButton(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public SignInButton(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public SignInButton(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.zaat = null;
      TypedArray var6 = var1.getTheme().obtainStyledAttributes(var2, com.google.android.gms.base.R.SignInButton, 0, 0);

      try {
         this.mSize = var6.getInt(com.google.android.gms.base.R.SignInButton_buttonSize, 0);
         this.mColor = var6.getInt(com.google.android.gms.base.R.SignInButton_colorScheme, 2);
      } finally {
         var6.recycle();
      }

      this.setStyle(this.mSize, this.mColor);
   }

   public final void onClick(View var1) {
      if(this.zaat != null && var1 == this.zaas) {
         this.zaat.onClick(this);
      }

   }

   public final void setColorScheme(int var1) {
      this.setStyle(this.mSize, var1);
   }

   public final void setEnabled(boolean var1) {
      super.setEnabled(var1);
      this.zaas.setEnabled(var1);
   }

   public final void setOnClickListener(OnClickListener var1) {
      this.zaat = var1;
      if(this.zaas != null) {
         this.zaas.setOnClickListener(this);
      }

   }

   @Deprecated
   public final void setScopes(Scope[] var1) {
      this.setStyle(this.mSize, this.mColor);
   }

   public final void setSize(int var1) {
      this.setStyle(var1, this.mColor);
   }

   public final void setStyle(int var1, int var2) {
      this.mSize = var1;
      this.mColor = var2;
      Context var3 = this.getContext();
      if(this.zaas != null) {
         this.removeView(this.zaas);
      }

      try {
         this.zaas = SignInButtonCreator.createView(var3, this.mSize, this.mColor);
      } catch (a var5) {
         Log.w("SignInButton", "Sign in button not found, using placeholder instead");
         var1 = this.mSize;
         var2 = this.mColor;
         SignInButtonImpl var4 = new SignInButtonImpl(var3);
         var4.configure(var3.getResources(), var1, var2);
         this.zaas = var4;
      }

      this.addView(this.zaas);
      this.zaas.setEnabled(this.isEnabled());
      this.zaas.setOnClickListener(this);
   }

   @Deprecated
   public final void setStyle(int var1, int var2, Scope[] var3) {
      this.setStyle(var1, var2);
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface ColorScheme {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface ButtonSize {
   }
}
