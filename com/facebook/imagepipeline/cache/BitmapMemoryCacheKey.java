package com.facebook.imagepipeline.cache;

import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.time.RealtimeSinceBootClock;
import com.facebook.common.util.HashCodeUtil;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import java.util.Locale;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class BitmapMemoryCacheKey implements CacheKey {

   private final long mCacheTime;
   private final Object mCallerContext;
   private final int mHash;
   private final ImageDecodeOptions mImageDecodeOptions;
   @Nullable
   private final CacheKey mPostprocessorCacheKey;
   @Nullable
   private final String mPostprocessorName;
   @Nullable
   private final ResizeOptions mResizeOptions;
   private final RotationOptions mRotationOptions;
   private final String mSourceString;


   public BitmapMemoryCacheKey(String var1, @Nullable ResizeOptions var2, RotationOptions var3, ImageDecodeOptions var4, @Nullable CacheKey var5, @Nullable String var6, Object var7) {
      this.mSourceString = (String)Preconditions.checkNotNull(var1);
      this.mResizeOptions = var2;
      this.mRotationOptions = var3;
      this.mImageDecodeOptions = var4;
      this.mPostprocessorCacheKey = var5;
      this.mPostprocessorName = var6;
      int var9 = var1.hashCode();
      int var8;
      if(var2 != null) {
         var8 = var2.hashCode();
      } else {
         var8 = 0;
      }

      this.mHash = HashCodeUtil.hashCode(Integer.valueOf(var9), Integer.valueOf(var8), Integer.valueOf(var3.hashCode()), this.mImageDecodeOptions, this.mPostprocessorCacheKey, var6);
      this.mCallerContext = var7;
      this.mCacheTime = RealtimeSinceBootClock.get().now();
   }

   public boolean containsUri(Uri var1) {
      return this.getUriString().contains(var1.toString());
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof BitmapMemoryCacheKey;
      boolean var3 = false;
      if(!var2) {
         return false;
      } else {
         BitmapMemoryCacheKey var4 = (BitmapMemoryCacheKey)var1;
         var2 = var3;
         if(this.mHash == var4.mHash) {
            var2 = var3;
            if(this.mSourceString.equals(var4.mSourceString)) {
               var2 = var3;
               if(Objects.equal(this.mResizeOptions, var4.mResizeOptions)) {
                  var2 = var3;
                  if(Objects.equal(this.mRotationOptions, var4.mRotationOptions)) {
                     var2 = var3;
                     if(Objects.equal(this.mImageDecodeOptions, var4.mImageDecodeOptions)) {
                        var2 = var3;
                        if(Objects.equal(this.mPostprocessorCacheKey, var4.mPostprocessorCacheKey)) {
                           var2 = var3;
                           if(Objects.equal(this.mPostprocessorName, var4.mPostprocessorName)) {
                              var2 = true;
                           }
                        }
                     }
                  }
               }
            }
         }

         return var2;
      }
   }

   public Object getCallerContext() {
      return this.mCallerContext;
   }

   public long getInBitmapCacheSince() {
      return this.mCacheTime;
   }

   @Nullable
   public String getPostprocessorName() {
      return this.mPostprocessorName;
   }

   public String getUriString() {
      return this.mSourceString;
   }

   public int hashCode() {
      return this.mHash;
   }

   public String toString() {
      return String.format((Locale)null, "%s_%s_%s_%s_%s_%s_%d", new Object[]{this.mSourceString, this.mResizeOptions, this.mRotationOptions, this.mImageDecodeOptions, this.mPostprocessorCacheKey, this.mPostprocessorName, Integer.valueOf(this.mHash)});
   }
}
