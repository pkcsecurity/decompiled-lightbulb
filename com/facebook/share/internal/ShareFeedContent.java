package com.facebook.share.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.model.ShareContent;

public class ShareFeedContent extends ShareContent<ShareFeedContent, ShareFeedContent.Builder> {

   public static final Creator<ShareFeedContent> CREATOR = new Creator() {
      public ShareFeedContent createFromParcel(Parcel var1) {
         return new ShareFeedContent(var1);
      }
      public ShareFeedContent[] newArray(int var1) {
         return new ShareFeedContent[var1];
      }
   };
   private final String link;
   private final String linkCaption;
   private final String linkDescription;
   private final String linkName;
   private final String mediaSource;
   private final String picture;
   private final String toId;


   ShareFeedContent(Parcel var1) {
      super(var1);
      this.toId = var1.readString();
      this.link = var1.readString();
      this.linkName = var1.readString();
      this.linkCaption = var1.readString();
      this.linkDescription = var1.readString();
      this.picture = var1.readString();
      this.mediaSource = var1.readString();
   }

   private ShareFeedContent(ShareFeedContent.Builder var1) {
      super((ShareContent.Builder)var1);
      this.toId = var1.toId;
      this.link = var1.link;
      this.linkName = var1.linkName;
      this.linkCaption = var1.linkCaption;
      this.linkDescription = var1.linkDescription;
      this.picture = var1.picture;
      this.mediaSource = var1.mediaSource;
   }

   // $FF: synthetic method
   ShareFeedContent(ShareFeedContent.Builder var1, Object var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public String getLink() {
      return this.link;
   }

   public String getLinkCaption() {
      return this.linkCaption;
   }

   public String getLinkDescription() {
      return this.linkDescription;
   }

   public String getLinkName() {
      return this.linkName;
   }

   public String getMediaSource() {
      return this.mediaSource;
   }

   public String getPicture() {
      return this.picture;
   }

   public String getToId() {
      return this.toId;
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
      var1.writeString(this.toId);
      var1.writeString(this.link);
      var1.writeString(this.linkName);
      var1.writeString(this.linkCaption);
      var1.writeString(this.linkDescription);
      var1.writeString(this.picture);
      var1.writeString(this.mediaSource);
   }

   public static final class Builder extends ShareContent.Builder<ShareFeedContent, ShareFeedContent.Builder> {

      private String link;
      private String linkCaption;
      private String linkDescription;
      private String linkName;
      private String mediaSource;
      private String picture;
      private String toId;


      public ShareFeedContent build() {
         return new ShareFeedContent(this, null);
      }

      public ShareFeedContent.Builder readFrom(ShareFeedContent var1) {
         return var1 == null?this:((ShareFeedContent.Builder)super.readFrom((ShareContent)var1)).setToId(var1.getToId()).setLink(var1.getLink()).setLinkName(var1.getLinkName()).setLinkCaption(var1.getLinkCaption()).setLinkDescription(var1.getLinkDescription()).setPicture(var1.getPicture()).setMediaSource(var1.getMediaSource());
      }

      public ShareFeedContent.Builder setLink(String var1) {
         this.link = var1;
         return this;
      }

      public ShareFeedContent.Builder setLinkCaption(String var1) {
         this.linkCaption = var1;
         return this;
      }

      public ShareFeedContent.Builder setLinkDescription(String var1) {
         this.linkDescription = var1;
         return this;
      }

      public ShareFeedContent.Builder setLinkName(String var1) {
         this.linkName = var1;
         return this;
      }

      public ShareFeedContent.Builder setMediaSource(String var1) {
         this.mediaSource = var1;
         return this;
      }

      public ShareFeedContent.Builder setPicture(String var1) {
         this.picture = var1;
         return this;
      }

      public ShareFeedContent.Builder setToId(String var1) {
         this.toId = var1;
         return this;
      }
   }
}
