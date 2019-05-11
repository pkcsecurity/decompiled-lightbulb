package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMessengerGenericTemplateElement;

public final class ShareMessengerGenericTemplateContent extends ShareContent<ShareMessengerGenericTemplateContent, ShareMessengerGenericTemplateContent.Builder> {

   public static final Creator<ShareMessengerGenericTemplateContent> CREATOR = new Creator() {
      public ShareMessengerGenericTemplateContent createFromParcel(Parcel var1) {
         return new ShareMessengerGenericTemplateContent(var1);
      }
      public ShareMessengerGenericTemplateContent[] newArray(int var1) {
         return new ShareMessengerGenericTemplateContent[var1];
      }
   };
   private final ShareMessengerGenericTemplateElement genericTemplateElement;
   private final ShareMessengerGenericTemplateContent.ImageAspectRatio imageAspectRatio;
   private final boolean isSharable;


   ShareMessengerGenericTemplateContent(Parcel var1) {
      super(var1);
      boolean var2;
      if(var1.readByte() != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.isSharable = var2;
      this.imageAspectRatio = (ShareMessengerGenericTemplateContent.ImageAspectRatio)var1.readSerializable();
      this.genericTemplateElement = (ShareMessengerGenericTemplateElement)var1.readParcelable(ShareMessengerGenericTemplateElement.class.getClassLoader());
   }

   protected ShareMessengerGenericTemplateContent(ShareMessengerGenericTemplateContent.Builder var1) {
      super((ShareContent.Builder)var1);
      this.isSharable = var1.isSharable;
      this.imageAspectRatio = var1.imageAspectRatio;
      this.genericTemplateElement = var1.genericTemplateElement;
   }

   public int describeContents() {
      return 0;
   }

   public ShareMessengerGenericTemplateElement getGenericTemplateElement() {
      return this.genericTemplateElement;
   }

   public ShareMessengerGenericTemplateContent.ImageAspectRatio getImageAspectRatio() {
      return this.imageAspectRatio;
   }

   public boolean getIsSharable() {
      return this.isSharable;
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
      var1.writeByte((byte)this.isSharable);
      var1.writeSerializable(this.imageAspectRatio);
      var1.writeParcelable(this.genericTemplateElement, var2);
   }

   public static enum ImageAspectRatio {

      // $FF: synthetic field
      private static final ShareMessengerGenericTemplateContent.ImageAspectRatio[] $VALUES = new ShareMessengerGenericTemplateContent.ImageAspectRatio[]{HORIZONTAL, SQUARE};
      HORIZONTAL("HORIZONTAL", 0),
      SQUARE("SQUARE", 1);


      private ImageAspectRatio(String var1, int var2) {}
   }

   public static class Builder extends ShareContent.Builder<ShareMessengerGenericTemplateContent, ShareMessengerGenericTemplateContent.Builder> {

      private ShareMessengerGenericTemplateElement genericTemplateElement;
      private ShareMessengerGenericTemplateContent.ImageAspectRatio imageAspectRatio;
      private boolean isSharable;


      public ShareMessengerGenericTemplateContent build() {
         return new ShareMessengerGenericTemplateContent(this);
      }

      public ShareMessengerGenericTemplateContent.Builder readFrom(ShareMessengerGenericTemplateContent var1) {
         return var1 == null?this:((ShareMessengerGenericTemplateContent.Builder)super.readFrom((ShareContent)var1)).setIsSharable(var1.getIsSharable()).setImageAspectRatio(var1.getImageAspectRatio()).setGenericTemplateElement(var1.getGenericTemplateElement());
      }

      public ShareMessengerGenericTemplateContent.Builder setGenericTemplateElement(ShareMessengerGenericTemplateElement var1) {
         this.genericTemplateElement = var1;
         return this;
      }

      public ShareMessengerGenericTemplateContent.Builder setImageAspectRatio(ShareMessengerGenericTemplateContent.ImageAspectRatio var1) {
         this.imageAspectRatio = var1;
         return this;
      }

      public ShareMessengerGenericTemplateContent.Builder setIsSharable(boolean var1) {
         this.isSharable = var1;
         return this;
      }
   }
}
