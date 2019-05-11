package com.facebook.share.widget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.internal.Utility;
import com.facebook.share.Sharer;
import com.facebook.share.internal.CameraEffectFeature;
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.OpenGraphActionDialogFeature;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareDialogFeature;
import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.ShareStoryFeature;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareStoryContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ShareDialog extends FacebookDialogBase<ShareContent, Sharer.Result> implements Sharer {

   private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Share.toRequestCode();
   private static final String FEED_DIALOG = "feed";
   private static final String TAG = "ShareDialog";
   private static final String WEB_OG_SHARE_DIALOG = "share_open_graph";
   public static final String WEB_SHARE_DIALOG = "share";
   private boolean isAutomaticMode;
   private boolean shouldFailOnDataError;


   public ShareDialog(Activity var1) {
      super(var1, DEFAULT_REQUEST_CODE);
      this.shouldFailOnDataError = false;
      this.isAutomaticMode = true;
      ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
   }

   ShareDialog(Activity var1, int var2) {
      super(var1, var2);
      this.shouldFailOnDataError = false;
      this.isAutomaticMode = true;
      ShareInternalUtility.registerStaticShareCallback(var2);
   }

   public ShareDialog(Fragment var1) {
      this(new FragmentWrapper(var1));
   }

   ShareDialog(Fragment var1, int var2) {
      this(new FragmentWrapper(var1), var2);
   }

   public ShareDialog(android.support.v4.app.Fragment var1) {
      this(new FragmentWrapper(var1));
   }

   ShareDialog(android.support.v4.app.Fragment var1, int var2) {
      this(new FragmentWrapper(var1), var2);
   }

   private ShareDialog(FragmentWrapper var1) {
      super(var1, DEFAULT_REQUEST_CODE);
      this.shouldFailOnDataError = false;
      this.isAutomaticMode = true;
      ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
   }

   private ShareDialog(FragmentWrapper var1, int var2) {
      super(var1, var2);
      this.shouldFailOnDataError = false;
      this.isAutomaticMode = true;
      ShareInternalUtility.registerStaticShareCallback(var2);
   }

   public static boolean canShow(Class<? extends ShareContent> var0) {
      return canShowWebTypeCheck(var0) || canShowNative(var0);
   }

   private static boolean canShowNative(Class<? extends ShareContent> var0) {
      DialogFeature var1 = getFeature(var0);
      return var1 != null && DialogPresenter.canPresentNativeDialogWithFeature(var1);
   }

   private static boolean canShowWebCheck(ShareContent var0) {
      if(!canShowWebTypeCheck(var0.getClass())) {
         return false;
      } else {
         if(var0 instanceof ShareOpenGraphContent) {
            ShareOpenGraphContent var2 = (ShareOpenGraphContent)var0;

            try {
               ShareInternalUtility.toJSONObjectForWeb(var2);
            } catch (Exception var1) {
               Utility.logd(TAG, "canShow returned false because the content of the Opem Graph object can\'t be shared via the web dialog", var1);
               return false;
            }
         }

         return true;
      }
   }

   private static boolean canShowWebTypeCheck(Class<? extends ShareContent> var0) {
      return ShareLinkContent.class.isAssignableFrom(var0) || ShareOpenGraphContent.class.isAssignableFrom(var0) || SharePhotoContent.class.isAssignableFrom(var0) && AccessToken.isCurrentAccessTokenActive();
   }

   private static DialogFeature getFeature(Class<? extends ShareContent> var0) {
      return (DialogFeature)(ShareLinkContent.class.isAssignableFrom(var0)?ShareDialogFeature.SHARE_DIALOG:(SharePhotoContent.class.isAssignableFrom(var0)?ShareDialogFeature.PHOTOS:(ShareVideoContent.class.isAssignableFrom(var0)?ShareDialogFeature.VIDEO:(ShareOpenGraphContent.class.isAssignableFrom(var0)?OpenGraphActionDialogFeature.OG_ACTION_DIALOG:(ShareMediaContent.class.isAssignableFrom(var0)?ShareDialogFeature.MULTIMEDIA:(ShareCameraEffectContent.class.isAssignableFrom(var0)?CameraEffectFeature.SHARE_CAMERA_EFFECT:(ShareStoryContent.class.isAssignableFrom(var0)?ShareStoryFeature.SHARE_STORY_ASSET:null)))))));
   }

   private void logDialogShare(Context var1, ShareContent var2, ShareDialog.Mode var3) {
      if(this.isAutomaticMode) {
         var3 = ShareDialog.Mode.AUTOMATIC;
      }

      String var8;
      switch(null.$SwitchMap$com$facebook$share$widget$ShareDialog$Mode[var3.ordinal()]) {
      case 1:
         var8 = "automatic";
         break;
      case 2:
         var8 = "web";
         break;
      case 3:
         var8 = "native";
         break;
      default:
         var8 = "unknown";
      }

      DialogFeature var6 = getFeature(var2.getClass());
      String var7;
      if(var6 == ShareDialogFeature.SHARE_DIALOG) {
         var7 = "status";
      } else if(var6 == ShareDialogFeature.PHOTOS) {
         var7 = "photo";
      } else if(var6 == ShareDialogFeature.VIDEO) {
         var7 = "video";
      } else if(var6 == OpenGraphActionDialogFeature.OG_ACTION_DIALOG) {
         var7 = "open_graph";
      } else {
         var7 = "unknown";
      }

      AppEventsLogger var5 = AppEventsLogger.newLogger(var1);
      Bundle var4 = new Bundle();
      var4.putString("fb_share_dialog_show", var8);
      var4.putString("fb_share_dialog_content_type", var7);
      var5.logSdkEvent("fb_share_dialog_show", (Double)null, var4);
   }

   public static void show(Activity var0, ShareContent var1) {
      (new ShareDialog(var0)).show(var1);
   }

   public static void show(Fragment var0, ShareContent var1) {
      show(new FragmentWrapper(var0), var1);
   }

   public static void show(android.support.v4.app.Fragment var0, ShareContent var1) {
      show(new FragmentWrapper(var0), var1);
   }

   private static void show(FragmentWrapper var0, ShareContent var1) {
      (new ShareDialog(var0)).show(var1);
   }

   public boolean canShow(ShareContent var1, ShareDialog.Mode var2) {
      Object var3 = var2;
      if(var2 == ShareDialog.Mode.AUTOMATIC) {
         var3 = BASE_AUTOMATIC_MODE;
      }

      return this.canShowImpl(var1, var3);
   }

   protected AppCall createBaseAppCall() {
      return new AppCall(this.getRequestCode());
   }

   protected List<FacebookDialogBase.ModeHandler> getOrderedModeHandlers() {
      ArrayList var1 = new ArrayList();
      var1.add(new ShareDialog.NativeHandler(null));
      var1.add(new ShareDialog.FeedHandler(null));
      var1.add(new ShareDialog.WebShareHandler(null));
      var1.add(new ShareDialog.CameraEffectHandler(null));
      var1.add(new ShareDialog.ShareStoryHandler(null));
      return var1;
   }

   public boolean getShouldFailOnDataError() {
      return this.shouldFailOnDataError;
   }

   protected void registerCallbackImpl(CallbackManagerImpl var1, FacebookCallback<Sharer.Result> var2) {
      ShareInternalUtility.registerSharerCallback(this.getRequestCode(), var1, var2);
   }

   public void setShouldFailOnDataError(boolean var1) {
      this.shouldFailOnDataError = var1;
   }

   public void show(ShareContent var1, ShareDialog.Mode var2) {
      boolean var3;
      if(var2 == ShareDialog.Mode.AUTOMATIC) {
         var3 = true;
      } else {
         var3 = false;
      }

      this.isAutomaticMode = var3;
      if(this.isAutomaticMode) {
         var2 = BASE_AUTOMATIC_MODE;
      }

      this.showImpl(var1, var2);
   }

   class NativeHandler extends FacebookDialogBase.ModeHandler {

      private NativeHandler() {
         super();
      }

      // $FF: synthetic method
      NativeHandler(Object var2) {
         this();
      }

      public boolean canShow(ShareContent var1, boolean var2) {
         boolean var4 = false;
         if(var1 != null && !(var1 instanceof ShareCameraEffectContent)) {
            if(var1 instanceof ShareStoryContent) {
               return false;
            } else {
               boolean var3;
               if(!var2) {
                  if(var1.getShareHashtag() != null) {
                     var3 = DialogPresenter.canPresentNativeDialogWithFeature(ShareDialogFeature.HASHTAG);
                  } else {
                     var3 = true;
                  }

                  var2 = var3;
                  if(var1 instanceof ShareLinkContent) {
                     var2 = var3;
                     if(!Utility.isNullOrEmpty(((ShareLinkContent)var1).getQuote())) {
                        var2 = var3 & DialogPresenter.canPresentNativeDialogWithFeature(ShareDialogFeature.LINK_SHARE_QUOTES);
                     }
                  }
               } else {
                  var2 = true;
               }

               var3 = var4;
               if(var2) {
                  var3 = var4;
                  if(ShareDialog.canShowNative(var1.getClass())) {
                     var3 = true;
                  }
               }

               return var3;
            }
         } else {
            return false;
         }
      }

      public AppCall createAppCall(final ShareContent var1) {
         ShareDialog.this.logDialogShare(ShareDialog.this.getActivityContext(), var1, ShareDialog.Mode.NATIVE);
         ShareContentValidation.validateForNativeShare(var1);
         final AppCall var2 = ShareDialog.this.createBaseAppCall();
         DialogPresenter.setupAppCallForNativeDialog(var2, new DialogPresenter.ParameterProvider() {

            // $FF: synthetic field
            final boolean val$shouldFailOnDataError;

            {
               this.val$shouldFailOnDataError = var4;
            }
            public Bundle getLegacyParameters() {
               return LegacyNativeDialogParameters.create(var2.getCallId(), var1, this.val$shouldFailOnDataError);
            }
            public Bundle getParameters() {
               return NativeDialogParameters.create(var2.getCallId(), var1, this.val$shouldFailOnDataError);
            }
         }, ShareDialog.getFeature(var1.getClass()));
         return var2;
      }

      public Object getMode() {
         return ShareDialog.Mode.NATIVE;
      }
   }

   class CameraEffectHandler extends FacebookDialogBase.ModeHandler {

      private CameraEffectHandler() {
         super();
      }

      // $FF: synthetic method
      CameraEffectHandler(Object var2) {
         this();
      }

      public boolean canShow(ShareContent var1, boolean var2) {
         return var1 instanceof ShareCameraEffectContent && ShareDialog.canShowNative(var1.getClass());
      }

      public AppCall createAppCall(final ShareContent var1) {
         ShareContentValidation.validateForNativeShare(var1);
         final AppCall var2 = ShareDialog.this.createBaseAppCall();
         DialogPresenter.setupAppCallForNativeDialog(var2, new DialogPresenter.ParameterProvider() {

            // $FF: synthetic field
            final boolean val$shouldFailOnDataError;

            {
               this.val$shouldFailOnDataError = var4;
            }
            public Bundle getLegacyParameters() {
               return LegacyNativeDialogParameters.create(var2.getCallId(), var1, this.val$shouldFailOnDataError);
            }
            public Bundle getParameters() {
               return NativeDialogParameters.create(var2.getCallId(), var1, this.val$shouldFailOnDataError);
            }
         }, ShareDialog.getFeature(var1.getClass()));
         return var2;
      }

      public Object getMode() {
         return ShareDialog.Mode.NATIVE;
      }
   }

   class ShareStoryHandler extends FacebookDialogBase.ModeHandler {

      private ShareStoryHandler() {
         super();
      }

      // $FF: synthetic method
      ShareStoryHandler(Object var2) {
         this();
      }

      public boolean canShow(ShareContent var1, boolean var2) {
         return var1 instanceof ShareStoryContent && ShareDialog.canShowNative(var1.getClass());
      }

      public AppCall createAppCall(final ShareContent var1) {
         ShareContentValidation.validateForStoryShare(var1);
         final AppCall var2 = ShareDialog.this.createBaseAppCall();
         DialogPresenter.setupAppCallForNativeDialog(var2, new DialogPresenter.ParameterProvider() {

            // $FF: synthetic field
            final boolean val$shouldFailOnDataError;

            {
               this.val$shouldFailOnDataError = var4;
            }
            public Bundle getLegacyParameters() {
               return LegacyNativeDialogParameters.create(var2.getCallId(), var1, this.val$shouldFailOnDataError);
            }
            public Bundle getParameters() {
               return NativeDialogParameters.create(var2.getCallId(), var1, this.val$shouldFailOnDataError);
            }
         }, ShareDialog.getFeature(var1.getClass()));
         return var2;
      }

      public Object getMode() {
         return ShareDialog.Mode.NATIVE;
      }
   }

   class FeedHandler extends FacebookDialogBase.ModeHandler {

      private FeedHandler() {
         super();
      }

      // $FF: synthetic method
      FeedHandler(Object var2) {
         this();
      }

      public boolean canShow(ShareContent var1, boolean var2) {
         return var1 instanceof ShareLinkContent || var1 instanceof ShareFeedContent;
      }

      public AppCall createAppCall(ShareContent var1) {
         ShareDialog.this.logDialogShare(ShareDialog.this.getActivityContext(), var1, ShareDialog.Mode.FEED);
         AppCall var2 = ShareDialog.this.createBaseAppCall();
         Bundle var4;
         if(var1 instanceof ShareLinkContent) {
            ShareLinkContent var3 = (ShareLinkContent)var1;
            ShareContentValidation.validateForWebShare(var3);
            var4 = WebDialogParameters.createForFeed(var3);
         } else {
            var4 = WebDialogParameters.createForFeed((ShareFeedContent)var1);
         }

         DialogPresenter.setupAppCallForWebDialog(var2, "feed", var4);
         return var2;
      }

      public Object getMode() {
         return ShareDialog.Mode.FEED;
      }
   }

   class WebShareHandler extends FacebookDialogBase.ModeHandler {

      private WebShareHandler() {
         super();
      }

      // $FF: synthetic method
      WebShareHandler(Object var2) {
         this();
      }

      private SharePhotoContent createAndMapAttachments(SharePhotoContent var1, UUID var2) {
         SharePhotoContent.Builder var6 = (new SharePhotoContent.Builder()).readFrom(var1);
         ArrayList var7 = new ArrayList();
         ArrayList var8 = new ArrayList();

         for(int var3 = 0; var3 < var1.getPhotos().size(); ++var3) {
            SharePhoto var5 = (SharePhoto)var1.getPhotos().get(var3);
            Bitmap var9 = var5.getBitmap();
            SharePhoto var4 = var5;
            if(var9 != null) {
               NativeAppCallAttachmentStore.Attachment var10 = NativeAppCallAttachmentStore.createAttachment(var2, var9);
               var4 = (new SharePhoto.Builder()).readFrom(var5).setImageUrl(Uri.parse(var10.getAttachmentUrl())).setBitmap((Bitmap)null).build();
               var8.add(var10);
            }

            var7.add(var4);
         }

         var6.setPhotos(var7);
         NativeAppCallAttachmentStore.addAttachments(var8);
         return var6.build();
      }

      private String getActionName(ShareContent var1) {
         return !(var1 instanceof ShareLinkContent) && !(var1 instanceof SharePhotoContent)?(var1 instanceof ShareOpenGraphContent?"share_open_graph":null):"share";
      }

      public boolean canShow(ShareContent var1, boolean var2) {
         return var1 != null && ShareDialog.canShowWebCheck(var1);
      }

      public AppCall createAppCall(ShareContent var1) {
         ShareDialog.this.logDialogShare(ShareDialog.this.getActivityContext(), var1, ShareDialog.Mode.WEB);
         AppCall var3 = ShareDialog.this.createBaseAppCall();
         ShareContentValidation.validateForWebShare(var1);
         Bundle var2;
         if(var1 instanceof ShareLinkContent) {
            var2 = WebDialogParameters.create((ShareLinkContent)var1);
         } else if(var1 instanceof SharePhotoContent) {
            var2 = WebDialogParameters.create(this.createAndMapAttachments((SharePhotoContent)var1, var3.getCallId()));
         } else {
            var2 = WebDialogParameters.create((ShareOpenGraphContent)var1);
         }

         DialogPresenter.setupAppCallForWebDialog(var3, this.getActionName(var1), var2);
         return var3;
      }

      public Object getMode() {
         return ShareDialog.Mode.WEB;
      }
   }

   public static enum Mode {

      // $FF: synthetic field
      private static final ShareDialog.Mode[] $VALUES = new ShareDialog.Mode[]{AUTOMATIC, NATIVE, WEB, FEED};
      AUTOMATIC("AUTOMATIC", 0),
      FEED("FEED", 3),
      NATIVE("NATIVE", 1),
      WEB("WEB", 2);


      private Mode(String var1, int var2) {}
   }
}
