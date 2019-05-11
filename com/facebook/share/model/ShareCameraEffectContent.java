package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.model.CameraEffectArguments;
import com.facebook.share.model.CameraEffectTextures;
import com.facebook.share.model.ShareContent;

public class ShareCameraEffectContent extends ShareContent<ShareCameraEffectContent, ShareCameraEffectContent.Builder> {

   public static final Creator<ShareCameraEffectContent> CREATOR = new Creator() {
      public ShareCameraEffectContent createFromParcel(Parcel var1) {
         return new ShareCameraEffectContent(var1);
      }
      public ShareCameraEffectContent[] newArray(int var1) {
         return new ShareCameraEffectContent[var1];
      }
   };
   private CameraEffectArguments arguments;
   private String effectId;
   private CameraEffectTextures textures;


   ShareCameraEffectContent(Parcel var1) {
      super(var1);
      this.effectId = var1.readString();
      this.arguments = (new CameraEffectArguments.Builder()).readFrom(var1).build();
      this.textures = (new CameraEffectTextures.Builder()).readFrom(var1).build();
   }

   private ShareCameraEffectContent(ShareCameraEffectContent.Builder var1) {
      super((ShareContent.Builder)var1);
      this.effectId = var1.effectId;
      this.arguments = var1.arguments;
      this.textures = var1.textures;
   }

   // $FF: synthetic method
   ShareCameraEffectContent(ShareCameraEffectContent.Builder var1, Object var2) {
      this(var1);
   }

   public CameraEffectArguments getArguments() {
      return this.arguments;
   }

   public String getEffectId() {
      return this.effectId;
   }

   public CameraEffectTextures getTextures() {
      return this.textures;
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
      var1.writeString(this.effectId);
      var1.writeParcelable(this.arguments, 0);
      var1.writeParcelable(this.textures, 0);
   }

   public static final class Builder extends ShareContent.Builder<ShareCameraEffectContent, ShareCameraEffectContent.Builder> {

      private CameraEffectArguments arguments;
      private String effectId;
      private CameraEffectTextures textures;


      public ShareCameraEffectContent build() {
         return new ShareCameraEffectContent(this, null);
      }

      public ShareCameraEffectContent.Builder readFrom(ShareCameraEffectContent var1) {
         return var1 == null?this:((ShareCameraEffectContent.Builder)super.readFrom((ShareContent)var1)).setEffectId(this.effectId).setArguments(this.arguments);
      }

      public ShareCameraEffectContent.Builder setArguments(CameraEffectArguments var1) {
         this.arguments = var1;
         return this;
      }

      public ShareCameraEffectContent.Builder setEffectId(String var1) {
         this.effectId = var1;
         return this;
      }

      public ShareCameraEffectContent.Builder setTextures(CameraEffectTextures var1) {
         this.textures = var1;
         return this;
      }
   }
}
