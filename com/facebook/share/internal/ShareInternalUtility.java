package com.facebook.share.internal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.util.Pair;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.Sharer;
import com.facebook.share.internal.OpenGraphJSONUtility;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.model.CameraEffectTextures;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareStoryContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.LikeView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareInternalUtility {

   public static final String MY_PHOTOS = "me/photos";
   private static final String MY_STAGING_RESOURCES = "me/staging_resources";
   private static final String STAGING_PARAM = "file";


   private static AppCall getAppCallFromActivityResult(int var0, int var1, Intent var2) {
      UUID var3 = NativeProtocol.getCallIdFromIntent(var2);
      return var3 == null?null:AppCall.finishPendingCall(var3, var0);
   }

   private static NativeAppCallAttachmentStore.Attachment getAttachment(UUID var0, Uri var1, Bitmap var2) {
      return var2 != null?NativeAppCallAttachmentStore.createAttachment(var0, var2):(var1 != null?NativeAppCallAttachmentStore.createAttachment(var0, var1):null);
   }

   private static NativeAppCallAttachmentStore.Attachment getAttachment(UUID var0, ShareMedia var1) {
      boolean var2 = var1 instanceof SharePhoto;
      Bitmap var3 = null;
      Uri var5;
      if(var2) {
         SharePhoto var4 = (SharePhoto)var1;
         var3 = var4.getBitmap();
         var5 = var4.getImageUrl();
      } else if(var1 instanceof ShareVideo) {
         var5 = ((ShareVideo)var1).getLocalUrl();
      } else {
         var5 = null;
      }

      return getAttachment(var0, var5, var3);
   }

   @Nullable
   public static Bundle getBackgroundAssetMediaInfo(ShareStoryContent var0, final UUID var1) {
      if(var0 != null && var0.getBackgroundAsset() != null) {
         ArrayList var2 = new ArrayList();
         var2.add(var0.getBackgroundAsset());
         final ArrayList var3 = new ArrayList();
         List var4 = Utility.map(var2, new Utility.Mapper() {
            public Bundle apply(ShareMedia var1x) {
               NativeAppCallAttachmentStore.Attachment var3x = ShareInternalUtility.getAttachment(var1, var1x);
               var3.add(var3x);
               Bundle var2 = new Bundle();
               var2.putString("type", var1x.getMediaType().name());
               var2.putString("uri", var3x.getAttachmentUrl());
               String var4 = ShareInternalUtility.getUriExtension(var3x.getOriginalUri());
               if(var4 != null) {
                  Utility.putNonEmptyString(var2, "extension", var4);
               }

               return var2;
            }
         });
         NativeAppCallAttachmentStore.addAttachments(var3);
         return (Bundle)var4.get(0);
      } else {
         return null;
      }
   }

   public static Pair<String, String> getFieldNameAndNamespaceFromFullName(String var0) {
      int var1 = var0.indexOf(58);
      String var4;
      String var5;
      if(var1 != -1) {
         int var2 = var0.length();
         int var3 = var1 + 1;
         if(var2 > var3) {
            var4 = var0.substring(0, var1);
            var5 = var0.substring(var3);
            var0 = var4;
            var4 = var5;
            return new Pair(var0, var4);
         }
      }

      var5 = null;
      var4 = var0;
      var0 = var5;
      return new Pair(var0, var4);
   }

   public static List<Bundle> getMediaInfos(ShareMediaContent var0, final UUID var1) {
      if(var0 != null) {
         List var2 = var0.getMedia();
         if(var2 != null) {
            final ArrayList var3 = new ArrayList();
            List var4 = Utility.map(var2, new Utility.Mapper() {
               public Bundle apply(ShareMedia var1x) {
                  NativeAppCallAttachmentStore.Attachment var2 = ShareInternalUtility.getAttachment(var1, var1x);
                  var3.add(var2);
                  Bundle var3x = new Bundle();
                  var3x.putString("type", var1x.getMediaType().name());
                  var3x.putString("uri", var2.getAttachmentUrl());
                  return var3x;
               }
            });
            NativeAppCallAttachmentStore.addAttachments(var3);
            return var4;
         }
      }

      return null;
   }

   @Nullable
   public static LikeView.ObjectType getMostSpecificObjectType(LikeView.ObjectType var0, LikeView.ObjectType var1) {
      return var0 == var1?var0:(var0 == LikeView.ObjectType.UNKNOWN?var1:(var1 == LikeView.ObjectType.UNKNOWN?var0:null));
   }

   public static String getNativeDialogCompletionGesture(Bundle var0) {
      return var0.containsKey("completionGesture")?var0.getString("completionGesture"):var0.getString("com.facebook.platform.extra.COMPLETION_GESTURE");
   }

   public static List<String> getPhotoUrls(SharePhotoContent var0, final UUID var1) {
      if(var0 != null) {
         List var2 = var0.getPhotos();
         if(var2 != null) {
            var2 = Utility.map(var2, new Utility.Mapper() {
               public NativeAppCallAttachmentStore.Attachment apply(SharePhoto var1x) {
                  return ShareInternalUtility.getAttachment(var1, var1x);
               }
            });
            List var3 = Utility.map(var2, new Utility.Mapper() {
               public String apply(NativeAppCallAttachmentStore.Attachment var1) {
                  return var1.getAttachmentUrl();
               }
            });
            NativeAppCallAttachmentStore.addAttachments(var2);
            return var3;
         }
      }

      return null;
   }

   public static String getShareDialogPostId(Bundle var0) {
      return var0.containsKey("postId")?var0.getString("postId"):(var0.containsKey("com.facebook.platform.extra.POST_ID")?var0.getString("com.facebook.platform.extra.POST_ID"):var0.getString("post_id"));
   }

   public static ResultProcessor getShareResultProcessor(final FacebookCallback<Sharer.Result> var0) {
      return new ResultProcessor(var0) {
         public void onCancel(AppCall var1) {
            ShareInternalUtility.invokeOnCancelCallback(var0);
         }
         public void onError(AppCall var1, FacebookException var2) {
            ShareInternalUtility.invokeOnErrorCallback(var0, var2);
         }
         public void onSuccess(AppCall var1, Bundle var2) {
            if(var2 != null) {
               String var3 = ShareInternalUtility.getNativeDialogCompletionGesture(var2);
               if(var3 != null && !"post".equalsIgnoreCase(var3)) {
                  if("cancel".equalsIgnoreCase(var3)) {
                     ShareInternalUtility.invokeOnCancelCallback(var0);
                     return;
                  }

                  ShareInternalUtility.invokeOnErrorCallback(var0, new FacebookException("UnknownError"));
                  return;
               }

               var3 = ShareInternalUtility.getShareDialogPostId(var2);
               ShareInternalUtility.invokeOnSuccessCallback(var0, var3);
            }

         }
      };
   }

   @Nullable
   public static Bundle getStickerUrl(ShareStoryContent var0, final UUID var1) {
      if(var0 != null && var0.getStickerAsset() != null) {
         ArrayList var2 = new ArrayList();
         var2.add(var0.getStickerAsset());
         List var3 = Utility.map(var2, new Utility.Mapper() {
            public NativeAppCallAttachmentStore.Attachment apply(SharePhoto var1x) {
               return ShareInternalUtility.getAttachment(var1, var1x);
            }
         });
         List var4 = Utility.map(var3, new Utility.Mapper() {
            public Bundle apply(NativeAppCallAttachmentStore.Attachment var1) {
               Bundle var2 = new Bundle();
               var2.putString("uri", var1.getAttachmentUrl());
               String var3 = ShareInternalUtility.getUriExtension(var1.getOriginalUri());
               if(var3 != null) {
                  Utility.putNonEmptyString(var2, "extension", var3);
               }

               return var2;
            }
         });
         NativeAppCallAttachmentStore.addAttachments(var3);
         return (Bundle)var4.get(0);
      } else {
         return null;
      }
   }

   public static Bundle getTextureUrlBundle(ShareCameraEffectContent var0, UUID var1) {
      if(var0 != null) {
         CameraEffectTextures var7 = var0.getTextures();
         if(var7 != null) {
            Bundle var2 = new Bundle();
            ArrayList var3 = new ArrayList();
            Iterator var4 = var7.keySet().iterator();

            while(var4.hasNext()) {
               String var5 = (String)var4.next();
               NativeAppCallAttachmentStore.Attachment var6 = getAttachment(var1, var7.getTextureUri(var5), var7.getTextureBitmap(var5));
               var3.add(var6);
               var2.putString(var5, var6.getAttachmentUrl());
            }

            NativeAppCallAttachmentStore.addAttachments(var3);
            return var2;
         }
      }

      return null;
   }

   @Nullable
   public static String getUriExtension(Uri var0) {
      if(var0 == null) {
         return null;
      } else {
         String var2 = var0.toString();
         int var1 = var2.lastIndexOf(46);
         return var1 == -1?null:var2.substring(var1);
      }
   }

   public static String getVideoUrl(ShareVideoContent var0, UUID var1) {
      if(var0 != null && var0.getVideo() != null) {
         NativeAppCallAttachmentStore.Attachment var2 = NativeAppCallAttachmentStore.createAttachment(var1, var0.getVideo().getLocalUrl());
         ArrayList var3 = new ArrayList(1);
         var3.add(var2);
         NativeAppCallAttachmentStore.addAttachments(var3);
         return var2.getAttachmentUrl();
      } else {
         return null;
      }
   }

   public static boolean handleActivityResult(int var0, int var1, Intent var2, ResultProcessor var3) {
      AppCall var4 = getAppCallFromActivityResult(var0, var1, var2);
      if(var4 == null) {
         return false;
      } else {
         NativeAppCallAttachmentStore.cleanupAttachmentsForCall(var4.getCallId());
         if(var3 == null) {
            return true;
         } else {
            FacebookException var5 = NativeProtocol.getExceptionFromErrorData(NativeProtocol.getErrorDataFromResultIntent(var2));
            if(var5 != null) {
               if(var5 instanceof FacebookOperationCanceledException) {
                  var3.onCancel(var4);
                  return true;
               } else {
                  var3.onError(var4, var5);
                  return true;
               }
            } else {
               var3.onSuccess(var4, NativeProtocol.getSuccessResultsFromIntent(var2));
               return true;
            }
         }
      }
   }

   public static void invokeCallbackWithError(FacebookCallback<Sharer.Result> var0, String var1) {
      invokeOnErrorCallback(var0, var1);
   }

   public static void invokeCallbackWithException(FacebookCallback<Sharer.Result> var0, Exception var1) {
      if(var1 instanceof FacebookException) {
         invokeOnErrorCallback(var0, (FacebookException)var1);
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Error preparing share content: ");
         var2.append(var1.getLocalizedMessage());
         invokeCallbackWithError(var0, var2.toString());
      }
   }

   public static void invokeCallbackWithResults(FacebookCallback<Sharer.Result> var0, String var1, GraphResponse var2) {
      FacebookRequestError var3 = var2.getError();
      if(var3 != null) {
         String var4 = var3.getErrorMessage();
         var1 = var4;
         if(Utility.isNullOrEmpty(var4)) {
            var1 = "Unexpected error sharing.";
         }

         invokeOnErrorCallback(var0, var2, var1);
      } else {
         invokeOnSuccessCallback(var0, var1);
      }
   }

   static void invokeOnCancelCallback(FacebookCallback<Sharer.Result> var0) {
      logShareResult("cancelled", (String)null);
      if(var0 != null) {
         var0.onCancel();
      }

   }

   static void invokeOnErrorCallback(FacebookCallback<Sharer.Result> var0, FacebookException var1) {
      logShareResult("error", var1.getMessage());
      if(var0 != null) {
         var0.onError(var1);
      }

   }

   static void invokeOnErrorCallback(FacebookCallback<Sharer.Result> var0, GraphResponse var1, String var2) {
      logShareResult("error", var2);
      if(var0 != null) {
         var0.onError(new FacebookGraphResponseException(var1, var2));
      }

   }

   static void invokeOnErrorCallback(FacebookCallback<Sharer.Result> var0, String var1) {
      logShareResult("error", var1);
      if(var0 != null) {
         var0.onError(new FacebookException(var1));
      }

   }

   static void invokeOnSuccessCallback(FacebookCallback<Sharer.Result> var0, String var1) {
      logShareResult("succeeded", (String)null);
      if(var0 != null) {
         var0.onSuccess(new Sharer.Result(var1));
      }

   }

   private static void logShareResult(String var0, String var1) {
      AppEventsLogger var2 = AppEventsLogger.newLogger(FacebookSdk.getApplicationContext());
      Bundle var3 = new Bundle();
      var3.putString("fb_share_dialog_outcome", var0);
      if(var1 != null) {
         var3.putString("error_message", var1);
      }

      var2.logSdkEvent("fb_share_dialog_result", (Double)null, var3);
   }

   public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken var0, Bitmap var1, GraphRequest.Callback var2) {
      Bundle var3 = new Bundle(1);
      var3.putParcelable("file", var1);
      return new GraphRequest(var0, "me/staging_resources", var3, HttpMethod.POST, var2);
   }

   public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken var0, Uri var1, GraphRequest.Callback var2) throws FileNotFoundException {
      if(Utility.isFileUri(var1)) {
         return newUploadStagingResourceWithImageRequest(var0, new File(var1.getPath()), var2);
      } else if(!Utility.isContentUri(var1)) {
         throw new FacebookException("The image Uri must be either a file:// or content:// Uri");
      } else {
         GraphRequest.ParcelableResourceWithMimeType var4 = new GraphRequest.ParcelableResourceWithMimeType(var1, "image/png");
         Bundle var3 = new Bundle(1);
         var3.putParcelable("file", var4);
         return new GraphRequest(var0, "me/staging_resources", var3, HttpMethod.POST, var2);
      }
   }

   public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken var0, File var1, GraphRequest.Callback var2) throws FileNotFoundException {
      GraphRequest.ParcelableResourceWithMimeType var4 = new GraphRequest.ParcelableResourceWithMimeType(ParcelFileDescriptor.open(var1, 268435456), "image/png");
      Bundle var3 = new Bundle(1);
      var3.putParcelable("file", var4);
      return new GraphRequest(var0, "me/staging_resources", var3, HttpMethod.POST, var2);
   }

   public static void registerSharerCallback(final int var0, CallbackManager var1, final FacebookCallback<Sharer.Result> var2) {
      if(!(var1 instanceof CallbackManagerImpl)) {
         throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
      } else {
         ((CallbackManagerImpl)var1).registerCallback(var0, new CallbackManagerImpl.Callback() {
            public boolean onActivityResult(int var1, Intent var2x) {
               return ShareInternalUtility.handleActivityResult(var0, var1, var2x, ShareInternalUtility.getShareResultProcessor(var2));
            }
         });
      }
   }

   public static void registerStaticShareCallback(final int var0) {
      CallbackManagerImpl.registerStaticCallback(var0, new CallbackManagerImpl.Callback() {
         public boolean onActivityResult(int var1, Intent var2) {
            return ShareInternalUtility.handleActivityResult(var0, var1, var2, ShareInternalUtility.getShareResultProcessor((FacebookCallback)null));
         }
      });
   }

   public static JSONArray removeNamespacesFromOGJsonArray(JSONArray var0, boolean var1) throws JSONException {
      JSONArray var5 = new JSONArray();

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         Object var4 = var0.get(var2);
         Object var3;
         if(var4 instanceof JSONArray) {
            var3 = removeNamespacesFromOGJsonArray((JSONArray)var4, var1);
         } else {
            var3 = var4;
            if(var4 instanceof JSONObject) {
               var3 = removeNamespacesFromOGJsonObject((JSONObject)var4, var1);
            }
         }

         var5.put(var3);
      }

      return var5;
   }

   public static JSONObject removeNamespacesFromOGJsonObject(JSONObject param0, boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public static JSONObject toJSONObjectForCall(final UUID var0, ShareOpenGraphContent var1) throws JSONException {
      ShareOpenGraphAction var2 = var1.getAction();
      final ArrayList var3 = new ArrayList();
      JSONObject var7 = OpenGraphJSONUtility.toJSONObject(var2, new OpenGraphJSONUtility.PhotoJSONProcessor() {
         public JSONObject toJSONObject(SharePhoto var1) {
            NativeAppCallAttachmentStore.Attachment var2 = ShareInternalUtility.getAttachment(var0, var1);
            if(var2 == null) {
               return null;
            } else {
               var3.add(var2);
               JSONObject var3x = new JSONObject();

               try {
                  var3x.put("url", var2.getAttachmentUrl());
                  if(var1.getUserGenerated()) {
                     var3x.put("user_generated", true);
                  }

                  return var3x;
               } catch (JSONException var4) {
                  throw new FacebookException("Unable to attach images", var4);
               }
            }
         }
      });
      NativeAppCallAttachmentStore.addAttachments(var3);
      if(var1.getPlaceId() != null && Utility.isNullOrEmpty(var7.optString("place"))) {
         var7.put("place", var1.getPlaceId());
      }

      if(var1.getPeopleIds() != null) {
         JSONArray var4 = var7.optJSONArray("tags");
         Object var5;
         if(var4 == null) {
            var5 = new HashSet();
         } else {
            var5 = Utility.jsonArrayToSet(var4);
         }

         Iterator var6 = var1.getPeopleIds().iterator();

         while(var6.hasNext()) {
            ((Set)var5).add((String)var6.next());
         }

         var7.put("tags", new JSONArray((Collection)var5));
      }

      return var7;
   }

   public static JSONObject toJSONObjectForWeb(ShareOpenGraphContent var0) throws JSONException {
      return OpenGraphJSONUtility.toJSONObject(var0.getAction(), new OpenGraphJSONUtility.PhotoJSONProcessor() {
         public JSONObject toJSONObject(SharePhoto var1) {
            Uri var4 = var1.getImageUrl();
            if(!Utility.isWebUri(var4)) {
               throw new FacebookException("Only web images may be used in OG objects shared via the web dialog");
            } else {
               JSONObject var2 = new JSONObject();

               try {
                  var2.put("url", var4.toString());
                  return var2;
               } catch (JSONException var3) {
                  throw new FacebookException("Unable to attach images", var3);
               }
            }
         }
      });
   }
}
