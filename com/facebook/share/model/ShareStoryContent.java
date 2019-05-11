package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.SharePhoto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ShareStoryContent extends ShareContent<ShareStoryContent, ShareStoryContent.Builder> {

   public static final Creator<ShareStoryContent> CREATOR = new Creator() {
      public ShareStoryContent createFromParcel(Parcel var1) {
         return new ShareStoryContent(var1);
      }
      public ShareStoryContent[] newArray(int var1) {
         return new ShareStoryContent[var1];
      }
   };
   private final String mAttributionLink;
   private final ShareMedia mBackgroundAsset;
   @Nullable
   private final List<String> mBackgroundColorList;
   private final SharePhoto mStickerAsset;


   ShareStoryContent(Parcel var1) {
      super(var1);
      this.mBackgroundAsset = (ShareMedia)var1.readParcelable(ShareMedia.class.getClassLoader());
      this.mStickerAsset = (SharePhoto)var1.readParcelable(SharePhoto.class.getClassLoader());
      this.mBackgroundColorList = this.readUnmodifiableStringList(var1);
      this.mAttributionLink = var1.readString();
   }

   private ShareStoryContent(ShareStoryContent.Builder var1) {
      super((ShareContent.Builder)var1);
      this.mBackgroundAsset = var1.mBackgroundAsset;
      this.mStickerAsset = var1.mStickerAsset;
      this.mBackgroundColorList = var1.mBackgroundColorList;
      this.mAttributionLink = var1.mAttributionLink;
   }

   // $FF: synthetic method
   ShareStoryContent(ShareStoryContent.Builder var1, Object var2) {
      this(var1);
   }

   @Nullable
   private List<String> readUnmodifiableStringList(Parcel var1) {
      ArrayList var2 = new ArrayList();
      var1.readStringList(var2);
      return var2.isEmpty()?null:Collections.unmodifiableList(var2);
   }

   public int describeContents() {
      return 0;
   }

   public String getAttributionLink() {
      return this.mAttributionLink;
   }

   public ShareMedia getBackgroundAsset() {
      return this.mBackgroundAsset;
   }

   @Nullable
   public List<String> getBackgroundColorList() {
      return this.mBackgroundColorList == null?null:Collections.unmodifiableList(this.mBackgroundColorList);
   }

   public SharePhoto getStickerAsset() {
      return this.mStickerAsset;
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
      var1.writeParcelable(this.mBackgroundAsset, 0);
      var1.writeParcelable(this.mStickerAsset, 0);
      var1.writeStringList(this.mBackgroundColorList);
      var1.writeString(this.mAttributionLink);
   }

   public static final class Builder extends ShareContent.Builder<ShareStoryContent, ShareStoryContent.Builder> {

      static final String TAG = "ShareStoryContent$Builder";
      private String mAttributionLink;
      private ShareMedia mBackgroundAsset;
      private List<String> mBackgroundColorList;
      private SharePhoto mStickerAsset;


      public ShareStoryContent build() {
         return new ShareStoryContent(this, null);
      }

      public ShareStoryContent.Builder readFrom(ShareStoryContent var1) {
         return var1 == null?this:((ShareStoryContent.Builder)super.readFrom((ShareContent)var1)).setBackgroundAsset(var1.getBackgroundAsset()).setStickerAsset(var1.getStickerAsset()).setBackgroundColorList(var1.getBackgroundColorList()).setAttributionLink(var1.getAttributionLink());
      }

      public ShareStoryContent.Builder setAttributionLink(String var1) {
         this.mAttributionLink = var1;
         return this;
      }

      public ShareStoryContent.Builder setBackgroundAsset(ShareMedia var1) {
         this.mBackgroundAsset = var1;
         return this;
      }

      public ShareStoryContent.Builder setBackgroundColorList(List<String> var1) {
         this.mBackgroundColorList = var1;
         return this;
      }

      public ShareStoryContent.Builder setStickerAsset(SharePhoto var1) {
         this.mStickerAsset = var1;
         return this;
      }
   }
}
