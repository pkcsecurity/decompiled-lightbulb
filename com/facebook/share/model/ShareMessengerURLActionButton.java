package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareMessengerActionButton;

public final class ShareMessengerURLActionButton extends ShareMessengerActionButton {

   public static final Creator<ShareMessengerURLActionButton> CREATOR = new Creator() {
      public ShareMessengerURLActionButton createFromParcel(Parcel var1) {
         return new ShareMessengerURLActionButton(var1);
      }
      public ShareMessengerURLActionButton[] newArray(int var1) {
         return new ShareMessengerURLActionButton[var1];
      }
   };
   private final Uri fallbackUrl;
   private final boolean isMessengerExtensionURL;
   private final boolean shouldHideWebviewShareButton;
   private final Uri url;
   private final ShareMessengerURLActionButton.WebviewHeightRatio webviewHeightRatio;


   ShareMessengerURLActionButton(Parcel var1) {
      super(var1);
      this.url = (Uri)var1.readParcelable(Uri.class.getClassLoader());
      byte var2 = var1.readByte();
      boolean var4 = false;
      boolean var3;
      if(var2 != 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      this.isMessengerExtensionURL = var3;
      this.fallbackUrl = (Uri)var1.readParcelable(Uri.class.getClassLoader());
      this.webviewHeightRatio = (ShareMessengerURLActionButton.WebviewHeightRatio)var1.readSerializable();
      var3 = var4;
      if(var1.readByte() != 0) {
         var3 = true;
      }

      this.shouldHideWebviewShareButton = var3;
   }

   private ShareMessengerURLActionButton(ShareMessengerURLActionButton.Builder var1) {
      super((ShareMessengerActionButton.Builder)var1);
      this.url = var1.url;
      this.isMessengerExtensionURL = var1.isMessengerExtensionURL;
      this.fallbackUrl = var1.fallbackUrl;
      this.webviewHeightRatio = var1.webviewHeightRatio;
      this.shouldHideWebviewShareButton = var1.shouldHideWebviewShareButton;
   }

   // $FF: synthetic method
   ShareMessengerURLActionButton(ShareMessengerURLActionButton.Builder var1, Object var2) {
      this(var1);
   }

   @Nullable
   public Uri getFallbackUrl() {
      return this.fallbackUrl;
   }

   public boolean getIsMessengerExtensionURL() {
      return this.isMessengerExtensionURL;
   }

   public boolean getShouldHideWebviewShareButton() {
      return this.shouldHideWebviewShareButton;
   }

   public Uri getUrl() {
      return this.url;
   }

   @Nullable
   public ShareMessengerURLActionButton.WebviewHeightRatio getWebviewHeightRatio() {
      return this.webviewHeightRatio;
   }

   public static enum WebviewHeightRatio {

      // $FF: synthetic field
      private static final ShareMessengerURLActionButton.WebviewHeightRatio[] $VALUES = new ShareMessengerURLActionButton.WebviewHeightRatio[]{WebviewHeightRatioFull, WebviewHeightRatioTall, WebviewHeightRatioCompact};
      WebviewHeightRatioCompact("WebviewHeightRatioCompact", 2),
      WebviewHeightRatioFull("WebviewHeightRatioFull", 0),
      WebviewHeightRatioTall("WebviewHeightRatioTall", 1);


      private WebviewHeightRatio(String var1, int var2) {}
   }

   public static final class Builder extends ShareMessengerActionButton.Builder<ShareMessengerURLActionButton, ShareMessengerURLActionButton.Builder> {

      private Uri fallbackUrl;
      private boolean isMessengerExtensionURL;
      private boolean shouldHideWebviewShareButton;
      private Uri url;
      private ShareMessengerURLActionButton.WebviewHeightRatio webviewHeightRatio;


      public ShareMessengerURLActionButton build() {
         return new ShareMessengerURLActionButton(this, null);
      }

      public ShareMessengerURLActionButton.Builder readFrom(ShareMessengerURLActionButton var1) {
         return var1 == null?this:this.setUrl(var1.getUrl()).setIsMessengerExtensionURL(var1.getIsMessengerExtensionURL()).setFallbackUrl(var1.getFallbackUrl()).setWebviewHeightRatio(var1.getWebviewHeightRatio()).setShouldHideWebviewShareButton(var1.getShouldHideWebviewShareButton());
      }

      public ShareMessengerURLActionButton.Builder setFallbackUrl(@Nullable Uri var1) {
         this.fallbackUrl = var1;
         return this;
      }

      public ShareMessengerURLActionButton.Builder setIsMessengerExtensionURL(boolean var1) {
         this.isMessengerExtensionURL = var1;
         return this;
      }

      public ShareMessengerURLActionButton.Builder setShouldHideWebviewShareButton(boolean var1) {
         this.shouldHideWebviewShareButton = var1;
         return this;
      }

      public ShareMessengerURLActionButton.Builder setUrl(@Nullable Uri var1) {
         this.url = var1;
         return this;
      }

      public ShareMessengerURLActionButton.Builder setWebviewHeightRatio(ShareMessengerURLActionButton.WebviewHeightRatio var1) {
         this.webviewHeightRatio = var1;
         return this;
      }
   }
}
