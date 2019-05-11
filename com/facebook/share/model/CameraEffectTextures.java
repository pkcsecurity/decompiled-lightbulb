package com.facebook.share.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.internal.Utility;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.util.Set;

public class CameraEffectTextures implements ShareModel {

   public static final Creator<CameraEffectTextures> CREATOR = new Creator() {
      public CameraEffectTextures createFromParcel(Parcel var1) {
         return new CameraEffectTextures(var1);
      }
      public CameraEffectTextures[] newArray(int var1) {
         return new CameraEffectTextures[var1];
      }
   };
   private final Bundle textures;


   CameraEffectTextures(Parcel var1) {
      this.textures = var1.readBundle(this.getClass().getClassLoader());
   }

   private CameraEffectTextures(CameraEffectTextures.Builder var1) {
      this.textures = var1.textures;
   }

   // $FF: synthetic method
   CameraEffectTextures(CameraEffectTextures.Builder var1, Object var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   @Nullable
   public Object get(String var1) {
      return this.textures.get(var1);
   }

   @Nullable
   public Bitmap getTextureBitmap(String var1) {
      Object var2 = this.textures.get(var1);
      return var2 instanceof Bitmap?(Bitmap)var2:null;
   }

   @Nullable
   public Uri getTextureUri(String var1) {
      Object var2 = this.textures.get(var1);
      return var2 instanceof Uri?(Uri)var2:null;
   }

   public Set<String> keySet() {
      return this.textures.keySet();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeBundle(this.textures);
   }

   public static class Builder implements ShareModelBuilder<CameraEffectTextures, CameraEffectTextures.Builder> {

      private Bundle textures = new Bundle();


      private CameraEffectTextures.Builder putParcelableTexture(String var1, Parcelable var2) {
         if(!Utility.isNullOrEmpty(var1) && var2 != null) {
            this.textures.putParcelable(var1, var2);
         }

         return this;
      }

      public CameraEffectTextures build() {
         return new CameraEffectTextures(this, null);
      }

      public CameraEffectTextures.Builder putTexture(String var1, Bitmap var2) {
         return this.putParcelableTexture(var1, var2);
      }

      public CameraEffectTextures.Builder putTexture(String var1, Uri var2) {
         return this.putParcelableTexture(var1, var2);
      }

      public CameraEffectTextures.Builder readFrom(Parcel var1) {
         return this.readFrom((CameraEffectTextures)var1.readParcelable(CameraEffectTextures.class.getClassLoader()));
      }

      public CameraEffectTextures.Builder readFrom(CameraEffectTextures var1) {
         if(var1 != null) {
            this.textures.putAll(var1.textures);
         }

         return this;
      }
   }
}
