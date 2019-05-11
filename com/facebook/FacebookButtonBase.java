package com.facebook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.common.R;
import com.facebook.internal.FragmentWrapper;

public abstract class FacebookButtonBase extends Button {

   private String analyticsButtonCreatedEventName;
   private String analyticsButtonTappedEventName;
   private OnClickListener externalOnClickListener;
   private OnClickListener internalOnClickListener;
   private boolean overrideCompoundPadding;
   private int overrideCompoundPaddingLeft;
   private int overrideCompoundPaddingRight;
   private FragmentWrapper parentFragment;


   protected FacebookButtonBase(Context var1, AttributeSet var2, int var3, int var4, String var5, String var6) {
      super(var1, var2, 0);
      int var7 = var4;
      if(var4 == 0) {
         var7 = this.getDefaultStyleResource();
      }

      var4 = var7;
      if(var7 == 0) {
         var4 = R.style.com_facebook_button;
      }

      this.configureButton(var1, var2, var3, var4);
      this.analyticsButtonCreatedEventName = var5;
      this.analyticsButtonTappedEventName = var6;
      this.setClickable(true);
      this.setFocusable(true);
   }

   private void logButtonCreated(Context var1) {
      AppEventsLogger.newLogger(var1).logSdkEvent(this.analyticsButtonCreatedEventName, (Double)null, (Bundle)null);
   }

   private void logButtonTapped(Context var1) {
      AppEventsLogger.newLogger(var1).logSdkEvent(this.analyticsButtonTappedEventName, (Double)null, (Bundle)null);
   }

   private void parseBackgroundAttributes(Context param1, AttributeSet param2, int param3, int param4) {
      // $FF: Couldn't be decompiled
   }

   @SuppressLint({"ResourceType"})
   private void parseCompoundDrawableAttributes(Context var1, AttributeSet var2, int var3, int var4) {
      TypedArray var7 = var1.getTheme().obtainStyledAttributes(var2, new int[]{16843119, 16843117, 16843120, 16843118, 16843121}, var3, var4);

      try {
         this.setCompoundDrawablesWithIntrinsicBounds(var7.getResourceId(0, 0), var7.getResourceId(1, 0), var7.getResourceId(2, 0), var7.getResourceId(3, 0));
         this.setCompoundDrawablePadding(var7.getDimensionPixelSize(4, 0));
      } finally {
         var7.recycle();
      }

   }

   private void parseContentAttributes(Context var1, AttributeSet var2, int var3, int var4) {
      TypedArray var7 = var1.getTheme().obtainStyledAttributes(var2, new int[]{16842966, 16842967, 16842968, 16842969}, var3, var4);

      try {
         this.setPadding(var7.getDimensionPixelSize(0, 0), var7.getDimensionPixelSize(1, 0), var7.getDimensionPixelSize(2, 0), var7.getDimensionPixelSize(3, 0));
      } finally {
         var7.recycle();
      }

   }

   private void parseTextAttributes(Context var1, AttributeSet var2, int var3, int var4) {
      TypedArray var5 = var1.getTheme().obtainStyledAttributes(var2, new int[]{16842904}, var3, var4);

      try {
         this.setTextColor(var5.getColorStateList(0));
      } finally {
         var5.recycle();
      }

      var5 = var1.getTheme().obtainStyledAttributes(var2, new int[]{16842927}, var3, var4);

      try {
         this.setGravity(var5.getInt(0, 17));
      } finally {
         var5.recycle();
      }

      TypedArray var18 = var1.getTheme().obtainStyledAttributes(var2, new int[]{16842901, 16842903, 16843087}, var3, var4);

      try {
         this.setTextSize(0, (float)var18.getDimensionPixelSize(0, 0));
         this.setTypeface(Typeface.defaultFromStyle(var18.getInt(1, 1)));
         this.setText(var18.getString(2));
      } finally {
         var18.recycle();
      }

   }

   private void setupOnClickListener() {
      super.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            FacebookButtonBase.this.logButtonTapped(FacebookButtonBase.this.getContext());
            if(FacebookButtonBase.this.internalOnClickListener != null) {
               FacebookButtonBase.this.internalOnClickListener.onClick(var1);
            } else {
               if(FacebookButtonBase.this.externalOnClickListener != null) {
                  FacebookButtonBase.this.externalOnClickListener.onClick(var1);
               }

            }
         }
      });
   }

   protected void callExternalOnClickListener(View var1) {
      if(this.externalOnClickListener != null) {
         this.externalOnClickListener.onClick(var1);
      }

   }

   public void configureButton(Context var1, AttributeSet var2, int var3, int var4) {
      this.parseBackgroundAttributes(var1, var2, var3, var4);
      this.parseCompoundDrawableAttributes(var1, var2, var3, var4);
      this.parseContentAttributes(var1, var2, var3, var4);
      this.parseTextAttributes(var1, var2, var3, var4);
      this.setupOnClickListener();
   }

   protected Activity getActivity() {
      Context var2 = this.getContext();

      while(true) {
         boolean var1 = var2 instanceof Activity;
         if(var1 || !(var2 instanceof ContextWrapper)) {
            if(var1) {
               return (Activity)var2;
            } else {
               throw new FacebookException("Unable to get Activity.");
            }
         }

         var2 = ((ContextWrapper)var2).getBaseContext();
      }
   }

   public int getCompoundPaddingLeft() {
      return this.overrideCompoundPadding?this.overrideCompoundPaddingLeft:super.getCompoundPaddingLeft();
   }

   public int getCompoundPaddingRight() {
      return this.overrideCompoundPadding?this.overrideCompoundPaddingRight:super.getCompoundPaddingRight();
   }

   public abstract int getDefaultRequestCode();

   public int getDefaultStyleResource() {
      return 0;
   }

   public Fragment getFragment() {
      return this.parentFragment != null?this.parentFragment.getSupportFragment():null;
   }

   public android.app.Fragment getNativeFragment() {
      return this.parentFragment != null?this.parentFragment.getNativeFragment():null;
   }

   public int getRequestCode() {
      return this.getDefaultRequestCode();
   }

   protected int measureTextWidth(String var1) {
      return (int)Math.ceil((double)this.getPaint().measureText(var1));
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      if(!this.isInEditMode()) {
         this.logButtonCreated(this.getContext());
      }

   }

   protected void onDraw(Canvas var1) {
      boolean var2;
      if((this.getGravity() & 1) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if(var2) {
         int var5 = this.getCompoundPaddingLeft();
         int var3 = this.getCompoundPaddingRight();
         int var4 = this.getCompoundDrawablePadding();
         var4 = Math.min((this.getWidth() - (var4 + var5) - var3 - this.measureTextWidth(this.getText().toString())) / 2, (var5 - this.getPaddingLeft()) / 2);
         this.overrideCompoundPaddingLeft = var5 - var4;
         this.overrideCompoundPaddingRight = var3 + var4;
         this.overrideCompoundPadding = true;
      }

      super.onDraw(var1);
      this.overrideCompoundPadding = false;
   }

   public void setFragment(android.app.Fragment var1) {
      this.parentFragment = new FragmentWrapper(var1);
   }

   public void setFragment(Fragment var1) {
      this.parentFragment = new FragmentWrapper(var1);
   }

   protected void setInternalOnClickListener(OnClickListener var1) {
      this.internalOnClickListener = var1;
   }

   public void setOnClickListener(OnClickListener var1) {
      this.externalOnClickListener = var1;
   }
}
