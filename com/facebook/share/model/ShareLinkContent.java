package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.share.model.ShareContent;

public final class ShareLinkContent extends ShareContent<ShareLinkContent, ShareLinkContent.Builder> {

   public static final Creator<ShareLinkContent> CREATOR = new Creator() {
      public ShareLinkContent createFromParcel(Parcel var1) {
         return new ShareLinkContent(var1);
      }
      public ShareLinkContent[] newArray(int var1) {
         return new ShareLinkContent[var1];
      }
   };
   @Deprecated
   private final String contentDescription;
   @Deprecated
   private final String contentTitle;
   @Deprecated
   private final Uri imageUrl;
   private final String quote;


   ShareLinkContent(Parcel var1) {
      super(var1);
      this.contentDescription = var1.readString();
      this.contentTitle = var1.readString();
      this.imageUrl = (Uri)var1.readParcelable(Uri.class.getClassLoader());
      this.quote = var1.readString();
   }

   private ShareLinkContent(ShareLinkContent.Builder var1) {
      super((ShareContent.Builder)var1);
      this.contentDescription = var1.contentDescription;
      this.contentTitle = var1.contentTitle;
      this.imageUrl = var1.imageUrl;
      this.quote = var1.quote;
   }

   // $FF: synthetic method
   ShareLinkContent(ShareLinkContent.Builder var1, Object var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   @Deprecated
   public String getContentDescription() {
      return this.contentDescription;
   }

   @Deprecated
   @Nullable
   public String getContentTitle() {
      return this.contentTitle;
   }

   @Deprecated
   @Nullable
   public Uri getImageUrl() {
      return this.imageUrl;
   }

   @Nullable
   public String getQuote() {
      return this.quote;
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
      var1.writeString(this.contentDescription);
      var1.writeString(this.contentTitle);
      var1.writeParcelable(this.imageUrl, 0);
      var1.writeString(this.quote);
   }

   public static final class Builder extends ShareContent.Builder<ShareLinkContent, ShareLinkContent.Builder> {

      static final String TAG = "ShareLinkContent$Builder";
      @Deprecated
      private String contentDescription;
      @Deprecated
      private String contentTitle;
      @Deprecated
      private Uri imageUrl;
      private String quote;


      public ShareLinkContent build() {
         return new ShareLinkContent(this, null);
      }

      public ShareLinkContent.Builder readFrom(ShareLinkContent var1) {
         return var1 == null?this:((ShareLinkContent.Builder)super.readFrom((ShareContent)var1)).setContentDescription(var1.getContentDescription()).setImageUrl(var1.getImageUrl()).setContentTitle(var1.getContentTitle()).setQuote(var1.getQuote());
      }

      @Deprecated
      public ShareLinkContent.Builder setContentDescription(@Nullable String var1) {
         Log.w(TAG, "This method does nothing. ContentDescription is deprecated in Graph API 2.9.");
         return this;
      }

      @Deprecated
      public ShareLinkContent.Builder setContentTitle(@Nullable String var1) {
         Log.w(TAG, "This method does nothing. ContentTitle is deprecated in Graph API 2.9.");
         return this;
      }

      @Deprecated
      public ShareLinkContent.Builder setImageUrl(@Nullable Uri var1) {
         Log.w(TAG, "This method does nothing. ImageUrl is deprecated in Graph API 2.9.");
         return this;
      }

      public ShareLinkContent.Builder setQuote(@Nullable String var1) {
         this.quote = var1;
         return this;
      }
   }
}
