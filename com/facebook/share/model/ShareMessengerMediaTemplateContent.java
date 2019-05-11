package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMessengerActionButton;

public final class ShareMessengerMediaTemplateContent extends ShareContent<ShareMessengerMediaTemplateContent, ShareMessengerMediaTemplateContent.Builder> {

   public static final Creator<ShareMessengerMediaTemplateContent> CREATOR = new Creator() {
      public ShareMessengerMediaTemplateContent createFromParcel(Parcel var1) {
         return new ShareMessengerMediaTemplateContent(var1);
      }
      public ShareMessengerMediaTemplateContent[] newArray(int var1) {
         return new ShareMessengerMediaTemplateContent[var1];
      }
   };
   private final String attachmentId;
   private final ShareMessengerActionButton button;
   private final ShareMessengerMediaTemplateContent.MediaType mediaType;
   private final Uri mediaUrl;


   ShareMessengerMediaTemplateContent(Parcel var1) {
      super(var1);
      this.mediaType = (ShareMessengerMediaTemplateContent.MediaType)var1.readSerializable();
      this.attachmentId = var1.readString();
      this.mediaUrl = (Uri)var1.readParcelable(Uri.class.getClassLoader());
      this.button = (ShareMessengerActionButton)var1.readParcelable(ShareMessengerActionButton.class.getClassLoader());
   }

   private ShareMessengerMediaTemplateContent(ShareMessengerMediaTemplateContent.Builder var1) {
      super((ShareContent.Builder)var1);
      this.mediaType = var1.mediaType;
      this.attachmentId = var1.attachmentId;
      this.mediaUrl = var1.mediaUrl;
      this.button = var1.button;
   }

   // $FF: synthetic method
   ShareMessengerMediaTemplateContent(ShareMessengerMediaTemplateContent.Builder var1, Object var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public String getAttachmentId() {
      return this.attachmentId;
   }

   public ShareMessengerActionButton getButton() {
      return this.button;
   }

   public ShareMessengerMediaTemplateContent.MediaType getMediaType() {
      return this.mediaType;
   }

   public Uri getMediaUrl() {
      return this.mediaUrl;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeSerializable(this.mediaType);
      var1.writeString(this.attachmentId);
      var1.writeParcelable(this.mediaUrl, var2);
      var1.writeParcelable(this.button, var2);
   }

   public static class Builder extends ShareContent.Builder<ShareMessengerMediaTemplateContent, ShareMessengerMediaTemplateContent.Builder> {

      private String attachmentId;
      private ShareMessengerActionButton button;
      private ShareMessengerMediaTemplateContent.MediaType mediaType;
      private Uri mediaUrl;


      public ShareMessengerMediaTemplateContent build() {
         return new ShareMessengerMediaTemplateContent(this, null);
      }

      public ShareMessengerMediaTemplateContent.Builder readFrom(ShareMessengerMediaTemplateContent var1) {
         return var1 == null?this:((ShareMessengerMediaTemplateContent.Builder)super.readFrom((ShareContent)var1)).setMediaType(var1.getMediaType()).setAttachmentId(var1.getAttachmentId()).setMediaUrl(var1.getMediaUrl()).setButton(var1.getButton());
      }

      public ShareMessengerMediaTemplateContent.Builder setAttachmentId(String var1) {
         this.attachmentId = var1;
         return this;
      }

      public ShareMessengerMediaTemplateContent.Builder setButton(ShareMessengerActionButton var1) {
         this.button = var1;
         return this;
      }

      public ShareMessengerMediaTemplateContent.Builder setMediaType(ShareMessengerMediaTemplateContent.MediaType var1) {
         this.mediaType = var1;
         return this;
      }

      public ShareMessengerMediaTemplateContent.Builder setMediaUrl(Uri var1) {
         this.mediaUrl = var1;
         return this;
      }
   }

   public static enum MediaType {

      // $FF: synthetic field
      private static final ShareMessengerMediaTemplateContent.MediaType[] $VALUES = new ShareMessengerMediaTemplateContent.MediaType[]{IMAGE, VIDEO};
      IMAGE("IMAGE", 0),
      VIDEO("VIDEO", 1);


      private MediaType(String var1, int var2) {}
   }
}
