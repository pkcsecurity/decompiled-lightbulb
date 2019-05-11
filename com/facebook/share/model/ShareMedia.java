package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.util.ArrayList;
import java.util.List;

public abstract class ShareMedia implements ShareModel {

   private final Bundle params;


   ShareMedia(Parcel var1) {
      this.params = var1.readBundle();
   }

   protected ShareMedia(ShareMedia.Builder var1) {
      this.params = new Bundle(var1.params);
   }

   public int describeContents() {
      return 0;
   }

   public abstract ShareMedia.Type getMediaType();

   @Deprecated
   public Bundle getParameters() {
      return new Bundle(this.params);
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeBundle(this.params);
   }

   public abstract static class Builder<M extends ShareMedia, B extends ShareMedia.Builder> implements ShareModelBuilder<M, B> {

      private Bundle params = new Bundle();


      static List<ShareMedia> readListFrom(Parcel var0) {
         Parcelable[] var4 = var0.readParcelableArray(ShareMedia.class.getClassLoader());
         ArrayList var3 = new ArrayList(var4.length);
         int var2 = var4.length;

         for(int var1 = 0; var1 < var2; ++var1) {
            var3.add((ShareMedia)var4[var1]);
         }

         return var3;
      }

      static void writeListTo(Parcel var0, int var1, List<ShareMedia> var2) {
         var0.writeParcelableArray((ShareMedia[])var2.toArray(), var1);
      }

      public B readFrom(M var1) {
         return var1 == null?this:this.setParameters(var1.getParameters());
      }

      @Deprecated
      public B setParameter(String var1, String var2) {
         this.params.putString(var1, var2);
         return this;
      }

      @Deprecated
      public B setParameters(Bundle var1) {
         this.params.putAll(var1);
         return this;
      }
   }

   public static enum Type {

      // $FF: synthetic field
      private static final ShareMedia.Type[] $VALUES = new ShareMedia.Type[]{PHOTO, VIDEO};
      PHOTO("PHOTO", 0),
      VIDEO("VIDEO", 1);


      private Type(String var1, int var2) {}
   }
}
