package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public final class AppGroupCreationContent implements ShareModel {

   public static final Creator<AppGroupCreationContent> CREATOR = new Creator() {
      public AppGroupCreationContent createFromParcel(Parcel var1) {
         return new AppGroupCreationContent(var1);
      }
      public AppGroupCreationContent[] newArray(int var1) {
         return new AppGroupCreationContent[var1];
      }
   };
   private final String description;
   private final String name;
   private AppGroupCreationContent.AppGroupPrivacy privacy;


   AppGroupCreationContent(Parcel var1) {
      this.name = var1.readString();
      this.description = var1.readString();
      this.privacy = (AppGroupCreationContent.AppGroupPrivacy)var1.readSerializable();
   }

   private AppGroupCreationContent(AppGroupCreationContent.Builder var1) {
      this.name = var1.name;
      this.description = var1.description;
      this.privacy = var1.privacy;
   }

   // $FF: synthetic method
   AppGroupCreationContent(AppGroupCreationContent.Builder var1, Object var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public AppGroupCreationContent.AppGroupPrivacy getAppGroupPrivacy() {
      return this.privacy;
   }

   public String getDescription() {
      return this.description;
   }

   public String getName() {
      return this.name;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeString(this.name);
      var1.writeString(this.description);
      var1.writeSerializable(this.privacy);
   }

   public static enum AppGroupPrivacy {

      // $FF: synthetic field
      private static final AppGroupCreationContent.AppGroupPrivacy[] $VALUES = new AppGroupCreationContent.AppGroupPrivacy[]{Open, Closed};
      Closed("Closed", 1),
      Open("Open", 0);


      private AppGroupPrivacy(String var1, int var2) {}
   }

   public static class Builder implements ShareModelBuilder<AppGroupCreationContent, AppGroupCreationContent.Builder> {

      private String description;
      private String name;
      private AppGroupCreationContent.AppGroupPrivacy privacy;


      public AppGroupCreationContent build() {
         return new AppGroupCreationContent(this, null);
      }

      public AppGroupCreationContent.Builder readFrom(AppGroupCreationContent var1) {
         return var1 == null?this:this.setName(var1.getName()).setDescription(var1.getDescription()).setAppGroupPrivacy(var1.getAppGroupPrivacy());
      }

      public AppGroupCreationContent.Builder setAppGroupPrivacy(AppGroupCreationContent.AppGroupPrivacy var1) {
         this.privacy = var1;
         return this;
      }

      public AppGroupCreationContent.Builder setDescription(String var1) {
         this.description = var1;
         return this;
      }

      public AppGroupCreationContent.Builder setName(String var1) {
         this.name = var1;
         return this;
      }
   }
}
