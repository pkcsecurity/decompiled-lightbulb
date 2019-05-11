package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.util.Set;

public class CameraEffectArguments implements ShareModel {

   public static final Creator<CameraEffectArguments> CREATOR = new Creator() {
      public CameraEffectArguments createFromParcel(Parcel var1) {
         return new CameraEffectArguments(var1);
      }
      public CameraEffectArguments[] newArray(int var1) {
         return new CameraEffectArguments[var1];
      }
   };
   private final Bundle params;


   CameraEffectArguments(Parcel var1) {
      this.params = var1.readBundle(this.getClass().getClassLoader());
   }

   private CameraEffectArguments(CameraEffectArguments.Builder var1) {
      this.params = var1.params;
   }

   // $FF: synthetic method
   CameraEffectArguments(CameraEffectArguments.Builder var1, Object var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   @Nullable
   public Object get(String var1) {
      return this.params.get(var1);
   }

   @Nullable
   public String getString(String var1) {
      return this.params.getString(var1);
   }

   @Nullable
   public String[] getStringArray(String var1) {
      return this.params.getStringArray(var1);
   }

   public Set<String> keySet() {
      return this.params.keySet();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeBundle(this.params);
   }

   public static class Builder implements ShareModelBuilder<CameraEffectArguments, CameraEffectArguments.Builder> {

      private Bundle params = new Bundle();


      public CameraEffectArguments build() {
         return new CameraEffectArguments(this, null);
      }

      public CameraEffectArguments.Builder putArgument(String var1, String var2) {
         this.params.putString(var1, var2);
         return this;
      }

      public CameraEffectArguments.Builder putArgument(String var1, String[] var2) {
         this.params.putStringArray(var1, var2);
         return this;
      }

      public CameraEffectArguments.Builder readFrom(Parcel var1) {
         return this.readFrom((CameraEffectArguments)var1.readParcelable(CameraEffectArguments.class.getClassLoader()));
      }

      public CameraEffectArguments.Builder readFrom(CameraEffectArguments var1) {
         if(var1 != null) {
            this.params.putAll(var1.params);
         }

         return this;
      }
   }
}
