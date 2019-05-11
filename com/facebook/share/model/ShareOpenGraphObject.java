package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.model.ShareOpenGraphValueContainer;

public final class ShareOpenGraphObject extends ShareOpenGraphValueContainer<ShareOpenGraphObject, ShareOpenGraphObject.Builder> {

   public static final Creator<ShareOpenGraphObject> CREATOR = new Creator() {
      public ShareOpenGraphObject createFromParcel(Parcel var1) {
         return new ShareOpenGraphObject(var1);
      }
      public ShareOpenGraphObject[] newArray(int var1) {
         return new ShareOpenGraphObject[var1];
      }
   };


   ShareOpenGraphObject(Parcel var1) {
      super(var1);
   }

   private ShareOpenGraphObject(ShareOpenGraphObject.Builder var1) {
      super((ShareOpenGraphValueContainer.Builder)var1);
   }

   // $FF: synthetic method
   ShareOpenGraphObject(ShareOpenGraphObject.Builder var1, Object var2) {
      this(var1);
   }

   public static final class Builder extends ShareOpenGraphValueContainer.Builder<ShareOpenGraphObject, ShareOpenGraphObject.Builder> {

      public Builder() {
         this.putBoolean("fbsdk:create_object", true);
      }

      public ShareOpenGraphObject build() {
         return new ShareOpenGraphObject(this, null);
      }

      ShareOpenGraphObject.Builder readFrom(Parcel var1) {
         return (ShareOpenGraphObject.Builder)this.readFrom((ShareOpenGraphObject)var1.readParcelable(ShareOpenGraphObject.class.getClassLoader()));
      }
   }
}
