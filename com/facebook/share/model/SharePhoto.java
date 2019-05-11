package com.facebook.share.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareMedia;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class SharePhoto extends ShareMedia {

   public static final Creator<SharePhoto> CREATOR = new Creator() {
      public SharePhoto createFromParcel(Parcel var1) {
         return new SharePhoto(var1);
      }
      public SharePhoto[] newArray(int var1) {
         return new SharePhoto[var1];
      }
   };
   private final Bitmap bitmap;
   private final String caption;
   private final Uri imageUrl;
   private final boolean userGenerated;


   SharePhoto(Parcel var1) {
      super(var1);
      this.bitmap = (Bitmap)var1.readParcelable(Bitmap.class.getClassLoader());
      this.imageUrl = (Uri)var1.readParcelable(Uri.class.getClassLoader());
      boolean var2;
      if(var1.readByte() != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.userGenerated = var2;
      this.caption = var1.readString();
   }

   private SharePhoto(SharePhoto.Builder var1) {
      super((ShareMedia.Builder)var1);
      this.bitmap = var1.bitmap;
      this.imageUrl = var1.imageUrl;
      this.userGenerated = var1.userGenerated;
      this.caption = var1.caption;
   }

   // $FF: synthetic method
   SharePhoto(SharePhoto.Builder var1, Object var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   @Nullable
   public Bitmap getBitmap() {
      return this.bitmap;
   }

   public String getCaption() {
      return this.caption;
   }

   @Nullable
   public Uri getImageUrl() {
      return this.imageUrl;
   }

   public ShareMedia.Type getMediaType() {
      return ShareMedia.Type.PHOTO;
   }

   public boolean getUserGenerated() {
      return this.userGenerated;
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
      var1.writeParcelable(this.bitmap, 0);
      var1.writeParcelable(this.imageUrl, 0);
      var1.writeByte((byte)this.userGenerated);
      var1.writeString(this.caption);
   }

   public static final class Builder extends ShareMedia.Builder<SharePhoto, SharePhoto.Builder> {

      private Bitmap bitmap;
      private String caption;
      private Uri imageUrl;
      private boolean userGenerated;


      static List<SharePhoto> readPhotoListFrom(Parcel var0) {
         List var1 = readListFrom(var0);
         ArrayList var3 = new ArrayList();
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            ShareMedia var2 = (ShareMedia)var4.next();
            if(var2 instanceof SharePhoto) {
               var3.add((SharePhoto)var2);
            }
         }

         return var3;
      }

      static void writePhotoListTo(Parcel var0, int var1, List<SharePhoto> var2) {
         ShareMedia[] var4 = new ShareMedia[var2.size()];

         for(int var3 = 0; var3 < var2.size(); ++var3) {
            var4[var3] = (ShareMedia)var2.get(var3);
         }

         var0.writeParcelableArray(var4, var1);
      }

      public SharePhoto build() {
         return new SharePhoto(this, null);
      }

      Bitmap getBitmap() {
         return this.bitmap;
      }

      Uri getImageUrl() {
         return this.imageUrl;
      }

      SharePhoto.Builder readFrom(Parcel var1) {
         return this.readFrom((SharePhoto)var1.readParcelable(SharePhoto.class.getClassLoader()));
      }

      public SharePhoto.Builder readFrom(SharePhoto var1) {
         return var1 == null?this:((SharePhoto.Builder)super.readFrom((ShareMedia)var1)).setBitmap(var1.getBitmap()).setImageUrl(var1.getImageUrl()).setUserGenerated(var1.getUserGenerated()).setCaption(var1.getCaption());
      }

      public SharePhoto.Builder setBitmap(@Nullable Bitmap var1) {
         this.bitmap = var1;
         return this;
      }

      public SharePhoto.Builder setCaption(@Nullable String var1) {
         this.caption = var1;
         return this;
      }

      public SharePhoto.Builder setImageUrl(@Nullable Uri var1) {
         this.imageUrl = var1;
         return this;
      }

      public SharePhoto.Builder setUserGenerated(boolean var1) {
         this.userGenerated = var1;
         return this;
      }
   }
}
