package com.facebook.share.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

@Deprecated
public class LikeContent implements ShareModel {

   @Deprecated
   public static final Creator<LikeContent> CREATOR = new Creator() {
      public LikeContent createFromParcel(Parcel var1) {
         return new LikeContent(var1);
      }
      public LikeContent[] newArray(int var1) {
         return new LikeContent[var1];
      }
   };
   private final String objectId;
   private final String objectType;


   @Deprecated
   LikeContent(Parcel var1) {
      this.objectId = var1.readString();
      this.objectType = var1.readString();
   }

   private LikeContent(LikeContent.Builder var1) {
      this.objectId = var1.objectId;
      this.objectType = var1.objectType;
   }

   // $FF: synthetic method
   LikeContent(LikeContent.Builder var1, Object var2) {
      this(var1);
   }

   @Deprecated
   public int describeContents() {
      return 0;
   }

   @Deprecated
   public String getObjectId() {
      return this.objectId;
   }

   @Deprecated
   public String getObjectType() {
      return this.objectType;
   }

   @Deprecated
   public void writeToParcel(Parcel var1, int var2) {
      var1.writeString(this.objectId);
      var1.writeString(this.objectType);
   }

   @Deprecated
   public static class Builder implements ShareModelBuilder<LikeContent, LikeContent.Builder> {

      private String objectId;
      private String objectType;


      @Deprecated
      public LikeContent build() {
         return new LikeContent(this, null);
      }

      @Deprecated
      public LikeContent.Builder readFrom(LikeContent var1) {
         return var1 == null?this:this.setObjectId(var1.getObjectId()).setObjectType(var1.getObjectType());
      }

      @Deprecated
      public LikeContent.Builder setObjectId(String var1) {
         this.objectId = var1;
         return this;
      }

      @Deprecated
      public LikeContent.Builder setObjectType(String var1) {
         this.objectType = var1;
         return this;
      }
   }
}
