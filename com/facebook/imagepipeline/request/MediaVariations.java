package com.facebook.imagepipeline.request;

import android.net.Uri;
import com.facebook.common.internal.Objects;
import com.facebook.imagepipeline.request.ImageRequest;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class MediaVariations {

   public static final String SOURCE_ID_EXTRACTOR = "id_extractor";
   public static final String SOURCE_IMAGE_REQUEST = "request";
   public static final String SOURCE_INDEX_DB = "index_db";
   private final boolean mForceRequestForSpecifiedUri;
   private final String mMediaId;
   private final String mSource;
   @Nullable
   private final List<MediaVariations.Variant> mVariants;


   private MediaVariations(MediaVariations.Builder var1) {
      this.mMediaId = var1.mMediaId;
      this.mVariants = var1.mVariants;
      this.mForceRequestForSpecifiedUri = var1.mForceRequestForSpecifiedUri;
      this.mSource = var1.mSource;
   }

   // $FF: synthetic method
   MediaVariations(MediaVariations.Builder var1, Object var2) {
      this(var1);
   }

   @Nullable
   public static MediaVariations forMediaId(@Nullable String var0) {
      return var0 != null && !var0.isEmpty()?newBuilderForMediaId(var0).build():null;
   }

   public static MediaVariations.Builder newBuilderForMediaId(String var0) {
      return new MediaVariations.Builder(var0, null);
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof MediaVariations;
      boolean var3 = false;
      if(!var2) {
         return false;
      } else {
         MediaVariations var4 = (MediaVariations)var1;
         var2 = var3;
         if(Objects.equal(this.mMediaId, var4.mMediaId)) {
            var2 = var3;
            if(this.mForceRequestForSpecifiedUri == var4.mForceRequestForSpecifiedUri) {
               var2 = var3;
               if(Objects.equal(this.mVariants, var4.mVariants)) {
                  var2 = true;
               }
            }
         }

         return var2;
      }
   }

   public String getMediaId() {
      return this.mMediaId;
   }

   public List<MediaVariations.Variant> getSortedVariants(Comparator<MediaVariations.Variant> var1) {
      int var3 = this.getVariantsCount();
      if(var3 == 0) {
         return Collections.emptyList();
      } else {
         ArrayList var4 = new ArrayList(var3);

         for(int var2 = 0; var2 < var3; ++var2) {
            var4.add(this.mVariants.get(var2));
         }

         Collections.sort(var4, var1);
         return var4;
      }
   }

   public String getSource() {
      return this.mSource;
   }

   public MediaVariations.Variant getVariant(int var1) {
      return (MediaVariations.Variant)this.mVariants.get(var1);
   }

   public int getVariantsCount() {
      return this.mVariants == null?0:this.mVariants.size();
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.mMediaId, Boolean.valueOf(this.mForceRequestForSpecifiedUri), this.mVariants, this.mSource});
   }

   public boolean shouldForceRequestForSpecifiedUri() {
      return this.mForceRequestForSpecifiedUri;
   }

   public String toString() {
      return String.format((Locale)null, "%s-%b-%s-%s", new Object[]{this.mMediaId, Boolean.valueOf(this.mForceRequestForSpecifiedUri), this.mVariants, this.mSource});
   }

   public static final class Variant {

      @Nullable
      private final ImageRequest.CacheChoice mCacheChoice;
      private final int mHeight;
      private final Uri mUri;
      private final int mWidth;


      public Variant(Uri var1, int var2, int var3) {
         this(var1, var2, var3, (ImageRequest.CacheChoice)null);
      }

      public Variant(Uri var1, int var2, int var3, @Nullable ImageRequest.CacheChoice var4) {
         this.mUri = var1;
         this.mWidth = var2;
         this.mHeight = var3;
         this.mCacheChoice = var4;
      }

      public boolean equals(Object var1) {
         boolean var2 = var1 instanceof MediaVariations.Variant;
         boolean var3 = false;
         if(!var2) {
            return false;
         } else {
            MediaVariations.Variant var4 = (MediaVariations.Variant)var1;
            var2 = var3;
            if(Objects.equal(this.mUri, var4.mUri)) {
               var2 = var3;
               if(this.mWidth == var4.mWidth) {
                  var2 = var3;
                  if(this.mHeight == var4.mHeight) {
                     var2 = var3;
                     if(this.mCacheChoice == var4.mCacheChoice) {
                        var2 = true;
                     }
                  }
               }
            }

            return var2;
         }
      }

      @Nullable
      public ImageRequest.CacheChoice getCacheChoice() {
         return this.mCacheChoice;
      }

      public int getHeight() {
         return this.mHeight;
      }

      public Uri getUri() {
         return this.mUri;
      }

      public int getWidth() {
         return this.mWidth;
      }

      public int hashCode() {
         return (this.mUri.hashCode() * 31 + this.mWidth) * 31 + this.mHeight;
      }

      public String toString() {
         return String.format((Locale)null, "%dx%d %s %s", new Object[]{Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), this.mUri, this.mCacheChoice});
      }
   }

   public static class Builder {

      private boolean mForceRequestForSpecifiedUri;
      private final String mMediaId;
      private String mSource;
      private List<MediaVariations.Variant> mVariants;


      private Builder(String var1) {
         this.mForceRequestForSpecifiedUri = false;
         this.mSource = "request";
         this.mMediaId = var1;
      }

      // $FF: synthetic method
      Builder(String var1, Object var2) {
         this(var1);
      }

      public MediaVariations.Builder addVariant(Uri var1, int var2, int var3) {
         return this.addVariant(var1, var2, var3, (ImageRequest.CacheChoice)null);
      }

      public MediaVariations.Builder addVariant(Uri var1, int var2, int var3, ImageRequest.CacheChoice var4) {
         if(this.mVariants == null) {
            this.mVariants = new ArrayList();
         }

         this.mVariants.add(new MediaVariations.Variant(var1, var2, var3, var4));
         return this;
      }

      public MediaVariations build() {
         return new MediaVariations(this, null);
      }

      public MediaVariations.Builder setForceRequestForSpecifiedUri(boolean var1) {
         this.mForceRequestForSpecifiedUri = var1;
         return this;
      }

      public MediaVariations.Builder setSource(String var1) {
         this.mSource = var1;
         return this;
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface Source {
   }
}
