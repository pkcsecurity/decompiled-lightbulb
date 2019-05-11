package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.model.ShareMessengerActionButton;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public final class ShareMessengerGenericTemplateElement implements ShareModel {

   public static final Creator<ShareMessengerGenericTemplateElement> CREATOR = new Creator() {
      public ShareMessengerGenericTemplateElement createFromParcel(Parcel var1) {
         return new ShareMessengerGenericTemplateElement(var1);
      }
      public ShareMessengerGenericTemplateElement[] newArray(int var1) {
         return new ShareMessengerGenericTemplateElement[var1];
      }
   };
   private final ShareMessengerActionButton button;
   private final ShareMessengerActionButton defaultAction;
   private final Uri imageUrl;
   private final String subtitle;
   private final String title;


   ShareMessengerGenericTemplateElement(Parcel var1) {
      this.title = var1.readString();
      this.subtitle = var1.readString();
      this.imageUrl = (Uri)var1.readParcelable(Uri.class.getClassLoader());
      this.defaultAction = (ShareMessengerActionButton)var1.readParcelable(ShareMessengerActionButton.class.getClassLoader());
      this.button = (ShareMessengerActionButton)var1.readParcelable(ShareMessengerActionButton.class.getClassLoader());
   }

   private ShareMessengerGenericTemplateElement(ShareMessengerGenericTemplateElement.Builder var1) {
      this.title = var1.title;
      this.subtitle = var1.subtitle;
      this.imageUrl = var1.imageUrl;
      this.defaultAction = var1.defaultAction;
      this.button = var1.button;
   }

   // $FF: synthetic method
   ShareMessengerGenericTemplateElement(ShareMessengerGenericTemplateElement.Builder var1, Object var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public ShareMessengerActionButton getButton() {
      return this.button;
   }

   public ShareMessengerActionButton getDefaultAction() {
      return this.defaultAction;
   }

   public Uri getImageUrl() {
      return this.imageUrl;
   }

   public String getSubtitle() {
      return this.subtitle;
   }

   public String getTitle() {
      return this.title;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeString(this.title);
      var1.writeString(this.subtitle);
      var1.writeParcelable(this.imageUrl, var2);
      var1.writeParcelable(this.defaultAction, var2);
      var1.writeParcelable(this.button, var2);
   }

   public static class Builder implements ShareModelBuilder<ShareMessengerGenericTemplateElement, ShareMessengerGenericTemplateElement.Builder> {

      private ShareMessengerActionButton button;
      private ShareMessengerActionButton defaultAction;
      private Uri imageUrl;
      private String subtitle;
      private String title;


      public ShareMessengerGenericTemplateElement build() {
         return new ShareMessengerGenericTemplateElement(this, null);
      }

      ShareMessengerGenericTemplateElement.Builder readFrom(Parcel var1) {
         return this.readFrom((ShareMessengerGenericTemplateElement)var1.readParcelable(ShareMessengerGenericTemplateElement.class.getClassLoader()));
      }

      public ShareMessengerGenericTemplateElement.Builder readFrom(ShareMessengerGenericTemplateElement var1) {
         return var1 == null?this:this.setTitle(var1.title).setSubtitle(var1.subtitle).setImageUrl(var1.imageUrl).setDefaultAction(var1.defaultAction).setButton(var1.button);
      }

      public ShareMessengerGenericTemplateElement.Builder setButton(ShareMessengerActionButton var1) {
         this.button = var1;
         return this;
      }

      public ShareMessengerGenericTemplateElement.Builder setDefaultAction(ShareMessengerActionButton var1) {
         this.defaultAction = var1;
         return this;
      }

      public ShareMessengerGenericTemplateElement.Builder setImageUrl(Uri var1) {
         this.imageUrl = var1;
         return this;
      }

      public ShareMessengerGenericTemplateElement.Builder setSubtitle(String var1) {
         this.subtitle = var1;
         return this;
      }

      public ShareMessengerGenericTemplateElement.Builder setTitle(String var1) {
         this.title = var1;
         return this;
      }
   }
}
