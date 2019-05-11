package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareMedia;

public final class ShareVideo extends ShareMedia {

   public static final Creator<ShareVideo> CREATOR = new Creator() {
      public ShareVideo createFromParcel(Parcel var1) {
         return new ShareVideo(var1);
      }
      public ShareVideo[] newArray(int var1) {
         return new ShareVideo[var1];
      }
   };
   private final Uri localUrl;


   ShareVideo(Parcel var1) {
      super(var1);
      this.localUrl = (Uri)var1.readParcelable(Uri.class.getClassLoader());
   }

   private ShareVideo(ShareVideo.Builder var1) {
      super((ShareMedia.Builder)var1);
      this.localUrl = var1.localUrl;
   }

   // $FF: synthetic method
   ShareVideo(ShareVideo.Builder var1, Object var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   @Nullable
   public Uri getLocalUrl() {
      return this.localUrl;
   }

   public ShareMedia.Type getMediaType() {
      return ShareMedia.Type.VIDEO;
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
      var1.writeParcelable(this.localUrl, 0);
   }

   public static final class Builder extends ShareMedia.Builder<ShareVideo, ShareVideo.Builder> {

      private Uri localUrl;


      public ShareVideo build() {
         return new ShareVideo(this, null);
      }

      ShareVideo.Builder readFrom(Parcel var1) {
         return this.readFrom((ShareVideo)var1.readParcelable(ShareVideo.class.getClassLoader()));
      }

      public ShareVideo.Builder readFrom(ShareVideo var1) {
         return var1 == null?this:((ShareVideo.Builder)super.readFrom((ShareMedia)var1)).setLocalUrl(var1.getLocalUrl());
      }

      public ShareVideo.Builder setLocalUrl(@Nullable Uri var1) {
         this.localUrl = var1;
         return this;
      }
   }
}
