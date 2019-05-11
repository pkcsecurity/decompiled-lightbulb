package com.facebook.share.model;

import android.os.Parcel;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public abstract class ShareMessengerActionButton implements ShareModel {

   private final String title;


   ShareMessengerActionButton(Parcel var1) {
      this.title = var1.readString();
   }

   protected ShareMessengerActionButton(ShareMessengerActionButton.Builder var1) {
      this.title = var1.title;
   }

   public int describeContents() {
      return 0;
   }

   public String getTitle() {
      return this.title;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeString(this.title);
   }

   public abstract static class Builder<M extends ShareMessengerActionButton, B extends ShareMessengerActionButton.Builder> implements ShareModelBuilder<M, B> {

      private String title;


      public B readFrom(M var1) {
         return var1 == null?this:this.setTitle(var1.getTitle());
      }

      public B setTitle(@Nullable String var1) {
         this.title = var1;
         return this;
      }
   }
}
