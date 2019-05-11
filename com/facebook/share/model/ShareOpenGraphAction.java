package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareOpenGraphValueContainer;

public final class ShareOpenGraphAction extends ShareOpenGraphValueContainer<ShareOpenGraphAction, ShareOpenGraphAction.Builder> {

   public static final Creator<ShareOpenGraphAction> CREATOR = new Creator() {
      public ShareOpenGraphAction createFromParcel(Parcel var1) {
         return new ShareOpenGraphAction(var1);
      }
      public ShareOpenGraphAction[] newArray(int var1) {
         return new ShareOpenGraphAction[var1];
      }
   };


   ShareOpenGraphAction(Parcel var1) {
      super(var1);
   }

   private ShareOpenGraphAction(ShareOpenGraphAction.Builder var1) {
      super((ShareOpenGraphValueContainer.Builder)var1);
   }

   // $FF: synthetic method
   ShareOpenGraphAction(ShareOpenGraphAction.Builder var1, Object var2) {
      this(var1);
   }

   @Nullable
   public String getActionType() {
      return this.getString("og:type");
   }

   public static final class Builder extends ShareOpenGraphValueContainer.Builder<ShareOpenGraphAction, ShareOpenGraphAction.Builder> {

      private static final String ACTION_TYPE_KEY = "og:type";


      public ShareOpenGraphAction build() {
         return new ShareOpenGraphAction(this, null);
      }

      ShareOpenGraphAction.Builder readFrom(Parcel var1) {
         return this.readFrom((ShareOpenGraphAction)var1.readParcelable(ShareOpenGraphAction.class.getClassLoader()));
      }

      public ShareOpenGraphAction.Builder readFrom(ShareOpenGraphAction var1) {
         return var1 == null?this:((ShareOpenGraphAction.Builder)super.readFrom((ShareOpenGraphValueContainer)var1)).setActionType(var1.getActionType());
      }

      public ShareOpenGraphAction.Builder setActionType(String var1) {
         this.putString("og:type", var1);
         return this;
      }
   }
}
