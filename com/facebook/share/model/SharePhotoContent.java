package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.SharePhoto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class SharePhotoContent extends ShareContent<SharePhotoContent, SharePhotoContent.Builder> {

   public static final Creator<SharePhotoContent> CREATOR = new Creator() {
      public SharePhotoContent createFromParcel(Parcel var1) {
         return new SharePhotoContent(var1);
      }
      public SharePhotoContent[] newArray(int var1) {
         return new SharePhotoContent[var1];
      }
   };
   private final List<SharePhoto> photos;


   SharePhotoContent(Parcel var1) {
      super(var1);
      this.photos = Collections.unmodifiableList(SharePhoto.Builder.readPhotoListFrom(var1));
   }

   private SharePhotoContent(SharePhotoContent.Builder var1) {
      super((ShareContent.Builder)var1);
      this.photos = Collections.unmodifiableList(var1.photos);
   }

   // $FF: synthetic method
   SharePhotoContent(SharePhotoContent.Builder var1, Object var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   @Nullable
   public List<SharePhoto> getPhotos() {
      return this.photos;
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
      SharePhoto.Builder.writePhotoListTo(var1, var2, this.photos);
   }

   public static class Builder extends ShareContent.Builder<SharePhotoContent, SharePhotoContent.Builder> {

      private final List<SharePhoto> photos = new ArrayList();


      public SharePhotoContent.Builder addPhoto(@Nullable SharePhoto var1) {
         if(var1 != null) {
            this.photos.add((new SharePhoto.Builder()).readFrom(var1).build());
         }

         return this;
      }

      public SharePhotoContent.Builder addPhotos(@Nullable List<SharePhoto> var1) {
         if(var1 != null) {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               this.addPhoto((SharePhoto)var2.next());
            }
         }

         return this;
      }

      public SharePhotoContent build() {
         return new SharePhotoContent(this, null);
      }

      public SharePhotoContent.Builder readFrom(SharePhotoContent var1) {
         return var1 == null?this:((SharePhotoContent.Builder)super.readFrom((ShareContent)var1)).addPhotos(var1.getPhotos());
      }

      public SharePhotoContent.Builder setPhotos(@Nullable List<SharePhoto> var1) {
         this.photos.clear();
         this.addPhotos(var1);
         return this;
      }
   }
}
