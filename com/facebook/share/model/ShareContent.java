package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ShareContent<P extends ShareContent, E extends ShareContent.Builder> implements ShareModel {

   private final Uri contentUrl;
   private final ShareHashtag hashtag;
   private final String pageId;
   private final List<String> peopleIds;
   private final String placeId;
   private final String ref;


   protected ShareContent(Parcel var1) {
      this.contentUrl = (Uri)var1.readParcelable(Uri.class.getClassLoader());
      this.peopleIds = this.readUnmodifiableStringList(var1);
      this.placeId = var1.readString();
      this.pageId = var1.readString();
      this.ref = var1.readString();
      this.hashtag = (new ShareHashtag.Builder()).readFrom(var1).build();
   }

   protected ShareContent(ShareContent.Builder var1) {
      this.contentUrl = var1.contentUrl;
      this.peopleIds = var1.peopleIds;
      this.placeId = var1.placeId;
      this.pageId = var1.pageId;
      this.ref = var1.ref;
      this.hashtag = var1.hashtag;
   }

   private List<String> readUnmodifiableStringList(Parcel var1) {
      ArrayList var2 = new ArrayList();
      var1.readStringList(var2);
      return var2.size() == 0?null:Collections.unmodifiableList(var2);
   }

   public int describeContents() {
      return 0;
   }

   @Nullable
   public Uri getContentUrl() {
      return this.contentUrl;
   }

   @Nullable
   public String getPageId() {
      return this.pageId;
   }

   @Nullable
   public List<String> getPeopleIds() {
      return this.peopleIds;
   }

   @Nullable
   public String getPlaceId() {
      return this.placeId;
   }

   @Nullable
   public String getRef() {
      return this.ref;
   }

   @Nullable
   public ShareHashtag getShareHashtag() {
      return this.hashtag;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeParcelable(this.contentUrl, 0);
      var1.writeStringList(this.peopleIds);
      var1.writeString(this.placeId);
      var1.writeString(this.pageId);
      var1.writeString(this.ref);
      var1.writeParcelable(this.hashtag, 0);
   }

   public abstract static class Builder<P extends ShareContent, E extends ShareContent.Builder> implements ShareModelBuilder<P, E> {

      private Uri contentUrl;
      private ShareHashtag hashtag;
      private String pageId;
      private List<String> peopleIds;
      private String placeId;
      private String ref;


      public E readFrom(P var1) {
         return var1 == null?this:this.setContentUrl(var1.getContentUrl()).setPeopleIds(var1.getPeopleIds()).setPlaceId(var1.getPlaceId()).setPageId(var1.getPageId()).setRef(var1.getRef());
      }

      public E setContentUrl(@Nullable Uri var1) {
         this.contentUrl = var1;
         return this;
      }

      public E setPageId(@Nullable String var1) {
         this.pageId = var1;
         return this;
      }

      public E setPeopleIds(@Nullable List<String> var1) {
         if(var1 == null) {
            var1 = null;
         } else {
            var1 = Collections.unmodifiableList(var1);
         }

         this.peopleIds = var1;
         return this;
      }

      public E setPlaceId(@Nullable String var1) {
         this.placeId = var1;
         return this;
      }

      public E setRef(@Nullable String var1) {
         this.ref = var1;
         return this;
      }

      public E setShareHashtag(@Nullable ShareHashtag var1) {
         this.hashtag = var1;
         return this;
      }
   }
}
