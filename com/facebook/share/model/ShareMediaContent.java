package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.ShareVideo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class ShareMediaContent extends ShareContent<ShareMediaContent, ShareMediaContent.Builder> {

   public static final Creator<ShareMediaContent> CREATOR = new Creator() {
      public ShareMediaContent createFromParcel(Parcel var1) {
         return new ShareMediaContent(var1);
      }
      public ShareMediaContent[] newArray(int var1) {
         return new ShareMediaContent[var1];
      }
   };
   private final List<ShareMedia> media;


   ShareMediaContent(Parcel var1) {
      super(var1);
      this.media = Arrays.asList((ShareMedia[])var1.readParcelableArray(ShareMedia.class.getClassLoader()));
   }

   private ShareMediaContent(ShareMediaContent.Builder var1) {
      super((ShareContent.Builder)var1);
      this.media = Collections.unmodifiableList(var1.media);
   }

   // $FF: synthetic method
   ShareMediaContent(ShareMediaContent.Builder var1, Object var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   @Nullable
   public List<ShareMedia> getMedia() {
      return this.media;
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
      var1.writeParcelableArray((ShareMedia[])this.media.toArray(), var2);
   }

   public static class Builder extends ShareContent.Builder<ShareMediaContent, ShareMediaContent.Builder> {

      private final List<ShareMedia> media = new ArrayList();


      public ShareMediaContent.Builder addMedia(@Nullable List<ShareMedia> var1) {
         if(var1 != null) {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               this.addMedium((ShareMedia)var2.next());
            }
         }

         return this;
      }

      public ShareMediaContent.Builder addMedium(@Nullable ShareMedia var1) {
         if(var1 != null) {
            Object var2;
            if(var1 instanceof SharePhoto) {
               var2 = (new SharePhoto.Builder()).readFrom((SharePhoto)var1).build();
            } else {
               if(!(var1 instanceof ShareVideo)) {
                  throw new IllegalArgumentException("medium must be either a SharePhoto or ShareVideo");
               }

               var2 = (new ShareVideo.Builder()).readFrom((ShareVideo)var1).build();
            }

            this.media.add(var2);
            return this;
         } else {
            return this;
         }
      }

      public ShareMediaContent build() {
         return new ShareMediaContent(this, null);
      }

      public ShareMediaContent.Builder readFrom(ShareMediaContent var1) {
         return var1 == null?this:((ShareMediaContent.Builder)super.readFrom((ShareContent)var1)).addMedia(var1.getMedia());
      }

      public ShareMediaContent.Builder setMedia(@Nullable List<ShareMedia> var1) {
         this.media.clear();
         this.addMedia(var1);
         return this;
      }
   }
}
