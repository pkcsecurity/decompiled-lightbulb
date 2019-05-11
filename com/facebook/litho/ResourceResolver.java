package com.facebook.litho;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.FastMath;
import com.facebook.litho.ResourceCache;

public class ResourceResolver {

   private final int[] mAttrs = new int[1];
   private ResourceCache mResourceCache;
   private Resources mResources;
   private Theme mTheme;


   public ResourceResolver(ComponentContext var1) {
      this.init(var1);
   }

   @Nullable
   private final int[] resolveIntArrayRes(@ArrayRes int var1) {
      if(var1 != 0) {
         int[] var2 = (int[])this.mResourceCache.get(var1);
         if(var2 != null) {
            return var2;
         } else {
            var2 = this.mResources.getIntArray(var1);
            this.mResourceCache.put(var1, var2);
            return var2;
         }
      } else {
         return null;
      }
   }

   public int dipsToPixels(float var1) {
      return FastMath.round(var1 * this.mResources.getDisplayMetrics().density);
   }

   public void init(ComponentContext var1) {
      this.mResources = var1.getAndroidContext().getResources();
      this.mTheme = var1.getAndroidContext().getTheme();
      this.mResourceCache = var1.getResourceCache();
   }

   public final void release() {
      this.mResources = null;
      this.mTheme = null;
      this.mResourceCache = null;
   }

   public boolean resolveBoolAttr(@AttrRes int var1, @BoolRes int var2) {
      this.mAttrs[0] = var1;
      TypedArray var4 = this.mTheme.obtainStyledAttributes(this.mAttrs);

      boolean var3;
      try {
         var3 = var4.getBoolean(0, this.resolveBoolRes(var2));
      } finally {
         var4.recycle();
      }

      return var3;
   }

   public boolean resolveBoolRes(@BoolRes int var1) {
      if(var1 != 0) {
         Boolean var3 = (Boolean)this.mResourceCache.get(var1);
         if(var3 != null) {
            return var3.booleanValue();
         } else {
            boolean var2 = this.mResources.getBoolean(var1);
            this.mResourceCache.put(var1, Boolean.valueOf(var2));
            return var2;
         }
      } else {
         return false;
      }
   }

   public int resolveColorAttr(@AttrRes int var1, @ColorRes int var2) {
      this.mAttrs[0] = var1;
      TypedArray var3 = this.mTheme.obtainStyledAttributes(this.mAttrs);

      try {
         var1 = var3.getColor(0, this.resolveColorRes(var2));
      } finally {
         var3.recycle();
      }

      return var1;
   }

   public int resolveColorRes(@ColorRes int var1) {
      if(var1 != 0) {
         Integer var3 = (Integer)this.mResourceCache.get(var1);
         if(var3 != null) {
            return var3.intValue();
         } else {
            int var2 = this.mResources.getColor(var1);
            this.mResourceCache.put(var1, Integer.valueOf(var2));
            return var2;
         }
      } else {
         return 0;
      }
   }

   public int resolveDimenOffsetAttr(@AttrRes int var1, @DimenRes int var2) {
      this.mAttrs[0] = var1;
      TypedArray var3 = this.mTheme.obtainStyledAttributes(this.mAttrs);

      try {
         var1 = var3.getDimensionPixelOffset(0, this.resolveDimenOffsetRes(var2));
      } finally {
         var3.recycle();
      }

      return var1;
   }

   public int resolveDimenOffsetRes(@DimenRes int var1) {
      if(var1 != 0) {
         Integer var3 = (Integer)this.mResourceCache.get(var1);
         if(var3 != null) {
            return var3.intValue();
         } else {
            int var2 = this.mResources.getDimensionPixelOffset(var1);
            this.mResourceCache.put(var1, Integer.valueOf(var2));
            return var2;
         }
      } else {
         return 0;
      }
   }

   public int resolveDimenSizeAttr(@AttrRes int var1, @DimenRes int var2) {
      this.mAttrs[0] = var1;
      TypedArray var3 = this.mTheme.obtainStyledAttributes(this.mAttrs);

      try {
         var1 = var3.getDimensionPixelSize(0, this.resolveDimenSizeRes(var2));
      } finally {
         var3.recycle();
      }

      return var1;
   }

   public int resolveDimenSizeRes(@DimenRes int var1) {
      if(var1 != 0) {
         Integer var3 = (Integer)this.mResourceCache.get(var1);
         if(var3 != null) {
            return var3.intValue();
         } else {
            int var2 = this.mResources.getDimensionPixelSize(var1);
            this.mResourceCache.put(var1, Integer.valueOf(var2));
            return var2;
         }
      } else {
         return 0;
      }
   }

   @Nullable
   public Drawable resolveDrawableAttr(@AttrRes int var1, @DrawableRes int var2) {
      if(var1 == 0) {
         return null;
      } else {
         this.mAttrs[0] = var1;
         TypedArray var3 = this.mTheme.obtainStyledAttributes(this.mAttrs);

         Drawable var4;
         try {
            var4 = this.resolveDrawableRes(var3.getResourceId(0, var2));
         } finally {
            var3.recycle();
         }

         return var4;
      }
   }

   @Nullable
   public Drawable resolveDrawableRes(@DrawableRes int var1) {
      return var1 == 0?null:this.mResources.getDrawable(var1);
   }

   public float resolveFloatAttr(@AttrRes int var1, @DimenRes int var2) {
      this.mAttrs[0] = var1;
      TypedArray var4 = this.mTheme.obtainStyledAttributes(this.mAttrs);

      float var3;
      try {
         var3 = var4.getDimension(0, this.resolveFloatRes(var2));
      } finally {
         var4.recycle();
      }

      return var3;
   }

   public float resolveFloatRes(@DimenRes int var1) {
      if(var1 != 0) {
         Float var3 = (Float)this.mResourceCache.get(var1);
         if(var3 != null) {
            return var3.floatValue();
         } else {
            float var2 = this.mResources.getDimension(var1);
            this.mResourceCache.put(var1, Float.valueOf(var2));
            return var2;
         }
      } else {
         return 0.0F;
      }
   }

   @Nullable
   public int[] resolveIntArrayAttr(@AttrRes int var1, @ArrayRes int var2) {
      this.mAttrs[0] = var1;
      TypedArray var3 = this.mTheme.obtainStyledAttributes(this.mAttrs);

      int[] var4;
      try {
         var4 = this.resolveIntArrayRes(var3.getResourceId(0, var2));
      } finally {
         var3.recycle();
      }

      return var4;
   }

   public int resolveIntAttr(@AttrRes int var1, @IntegerRes int var2) {
      this.mAttrs[0] = var1;
      TypedArray var3 = this.mTheme.obtainStyledAttributes(this.mAttrs);

      try {
         var1 = var3.getInt(0, this.resolveIntRes(var2));
      } finally {
         var3.recycle();
      }

      return var1;
   }

   public int resolveIntRes(@IntegerRes int var1) {
      if(var1 != 0) {
         Integer var3 = (Integer)this.mResourceCache.get(var1);
         if(var3 != null) {
            return var3.intValue();
         } else {
            int var2 = this.mResources.getInteger(var1);
            this.mResourceCache.put(var1, Integer.valueOf(var2));
            return var2;
         }
      } else {
         return 0;
      }
   }

   @Nullable
   public Integer[] resolveIntegerArrayAttr(@AttrRes int var1, @ArrayRes int var2) {
      int[] var3 = this.resolveIntArrayAttr(var1, var2);
      if(var3 == null) {
         return null;
      } else {
         Integer[] var4 = new Integer[var3.length];

         for(var1 = 0; var1 < var3.length; ++var1) {
            var4[var1] = Integer.valueOf(var3[var1]);
         }

         return var4;
      }
   }

   @Nullable
   public Integer[] resolveIntegerArrayRes(@ArrayRes int var1) {
      int[] var2 = this.resolveIntArrayRes(var1);
      if(var2 == null) {
         return null;
      } else {
         Integer[] var3 = new Integer[var2.length];

         for(var1 = 0; var1 < var2.length; ++var1) {
            var3[var1] = Integer.valueOf(var2[var1]);
         }

         return var3;
      }
   }

   final int resolveResIdAttr(@AttrRes int var1, int var2) {
      this.mAttrs[0] = var1;
      TypedArray var3 = this.mTheme.obtainStyledAttributes(this.mAttrs);

      try {
         var1 = var3.getResourceId(0, var2);
      } finally {
         var3.recycle();
      }

      return var1;
   }

   @Nullable
   public String[] resolveStringArrayAttr(@AttrRes int var1, @ArrayRes int var2) {
      this.mAttrs[0] = var1;
      TypedArray var3 = this.mTheme.obtainStyledAttributes(this.mAttrs);

      String[] var4;
      try {
         var4 = this.resolveStringArrayRes(var3.getResourceId(0, var2));
      } finally {
         var3.recycle();
      }

      return var4;
   }

   @Nullable
   public String[] resolveStringArrayRes(@ArrayRes int var1) {
      if(var1 != 0) {
         String[] var2 = (String[])this.mResourceCache.get(var1);
         if(var2 != null) {
            return var2;
         } else {
            var2 = this.mResources.getStringArray(var1);
            this.mResourceCache.put(var1, var2);
            return var2;
         }
      } else {
         return null;
      }
   }

   public String resolveStringAttr(@AttrRes int param1, @StringRes int param2) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   public String resolveStringRes(@StringRes int var1) {
      if(var1 != 0) {
         String var2 = (String)this.mResourceCache.get(var1);
         if(var2 != null) {
            return var2;
         } else {
            var2 = this.mResources.getString(var1);
            this.mResourceCache.put(var1, var2);
            return var2;
         }
      } else {
         return null;
      }
   }

   @Nullable
   public String resolveStringRes(@StringRes int var1, Object ... var2) {
      return var1 != 0?this.mResources.getString(var1, var2):null;
   }

   public int sipsToPixels(float var1) {
      return FastMath.round(var1 * this.mResources.getDisplayMetrics().scaledDensity);
   }
}
