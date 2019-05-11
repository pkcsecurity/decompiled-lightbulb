package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMessengerActionButton;

public final class ShareMessengerOpenGraphMusicTemplateContent extends ShareContent<ShareMessengerOpenGraphMusicTemplateContent, ShareMessengerOpenGraphMusicTemplateContent.Builder> {

   public static final Creator<ShareMessengerOpenGraphMusicTemplateContent> CREATOR = new Creator() {
      public ShareMessengerOpenGraphMusicTemplateContent createFromParcel(Parcel var1) {
         return new ShareMessengerOpenGraphMusicTemplateContent(var1);
      }
      public ShareMessengerOpenGraphMusicTemplateContent[] newArray(int var1) {
         return new ShareMessengerOpenGraphMusicTemplateContent[var1];
      }
   };
   private final ShareMessengerActionButton button;
   private final Uri url;


   ShareMessengerOpenGraphMusicTemplateContent(Parcel var1) {
      super(var1);
      this.url = (Uri)var1.readParcelable(Uri.class.getClassLoader());
      this.button = (ShareMessengerActionButton)var1.readParcelable(ShareMessengerActionButton.class.getClassLoader());
   }

   private ShareMessengerOpenGraphMusicTemplateContent(ShareMessengerOpenGraphMusicTemplateContent.Builder var1) {
      super((ShareContent.Builder)var1);
      this.url = var1.url;
      this.button = var1.button;
   }

   // $FF: synthetic method
   ShareMessengerOpenGraphMusicTemplateContent(ShareMessengerOpenGraphMusicTemplateContent.Builder var1, Object var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public ShareMessengerActionButton getButton() {
      return this.button;
   }

   public Uri getUrl() {
      return this.url;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeParcelable(this.url, var2);
      var1.writeParcelable(this.button, var2);
   }

   public static class Builder extends ShareContent.Builder<ShareMessengerOpenGraphMusicTemplateContent, ShareMessengerOpenGraphMusicTemplateContent.Builder> {

      private ShareMessengerActionButton button;
      private Uri url;


      public ShareMessengerOpenGraphMusicTemplateContent build() {
         return new ShareMessengerOpenGraphMusicTemplateContent(this, null);
      }

      public ShareMessengerOpenGraphMusicTemplateContent.Builder readFrom(ShareMessengerOpenGraphMusicTemplateContent var1) {
         return var1 == null?this:((ShareMessengerOpenGraphMusicTemplateContent.Builder)super.readFrom((ShareContent)var1)).setUrl(var1.getUrl()).setButton(var1.getButton());
      }

      public ShareMessengerOpenGraphMusicTemplateContent.Builder setButton(ShareMessengerActionButton var1) {
         this.button = var1;
         return this;
      }

      public ShareMessengerOpenGraphMusicTemplateContent.Builder setUrl(Uri var1) {
         this.url = var1;
         return this;
      }
   }
}
