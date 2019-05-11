package com.facebook.share.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareStoryContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class NativeDialogParameters {

   private static Bundle create(ShareCameraEffectContent param0, Bundle param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   private static Bundle create(ShareLinkContent var0, boolean var1) {
      Bundle var2 = createBaseParameters(var0, var1);
      Utility.putNonEmptyString(var2, "TITLE", var0.getContentTitle());
      Utility.putNonEmptyString(var2, "DESCRIPTION", var0.getContentDescription());
      Utility.putUri(var2, "IMAGE", var0.getImageUrl());
      Utility.putNonEmptyString(var2, "QUOTE", var0.getQuote());
      Utility.putUri(var2, "MESSENGER_LINK", var0.getContentUrl());
      Utility.putUri(var2, "TARGET_DISPLAY", var0.getContentUrl());
      return var2;
   }

   private static Bundle create(ShareMediaContent var0, List<Bundle> var1, boolean var2) {
      Bundle var3 = createBaseParameters(var0, var2);
      var3.putParcelableArrayList("MEDIA", new ArrayList(var1));
      return var3;
   }

   private static Bundle create(ShareMessengerGenericTemplateContent var0, boolean var1) {
      Bundle var2 = createBaseParameters(var0, var1);

      try {
         MessengerShareContentUtility.addGenericTemplateContent(var2, var0);
         return var2;
      } catch (JSONException var3) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Unable to create a JSON Object from the provided ShareMessengerGenericTemplateContent: ");
         var4.append(var3.getMessage());
         throw new FacebookException(var4.toString());
      }
   }

   private static Bundle create(ShareMessengerMediaTemplateContent var0, boolean var1) {
      Bundle var2 = createBaseParameters(var0, var1);

      try {
         MessengerShareContentUtility.addMediaTemplateContent(var2, var0);
         return var2;
      } catch (JSONException var3) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Unable to create a JSON Object from the provided ShareMessengerMediaTemplateContent: ");
         var4.append(var3.getMessage());
         throw new FacebookException(var4.toString());
      }
   }

   private static Bundle create(ShareMessengerOpenGraphMusicTemplateContent var0, boolean var1) {
      Bundle var2 = createBaseParameters(var0, var1);

      try {
         MessengerShareContentUtility.addOpenGraphMusicTemplateContent(var2, var0);
         return var2;
      } catch (JSONException var3) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Unable to create a JSON Object from the provided ShareMessengerOpenGraphMusicTemplateContent: ");
         var4.append(var3.getMessage());
         throw new FacebookException(var4.toString());
      }
   }

   private static Bundle create(ShareOpenGraphContent var0, JSONObject var1, boolean var2) {
      Bundle var3 = createBaseParameters(var0, var2);
      Utility.putNonEmptyString(var3, "PREVIEW_PROPERTY_NAME", (String)ShareInternalUtility.getFieldNameAndNamespaceFromFullName(var0.getPreviewPropertyName()).second);
      Utility.putNonEmptyString(var3, "ACTION_TYPE", var0.getAction().getActionType());
      Utility.putNonEmptyString(var3, "ACTION", var1.toString());
      return var3;
   }

   private static Bundle create(SharePhotoContent var0, List<String> var1, boolean var2) {
      Bundle var3 = createBaseParameters(var0, var2);
      var3.putStringArrayList("PHOTOS", new ArrayList(var1));
      return var3;
   }

   private static Bundle create(ShareStoryContent var0, @Nullable Bundle var1, @Nullable Bundle var2, boolean var3) {
      Bundle var4 = createBaseParameters(var0, var3);
      if(var1 != null) {
         var4.putParcelable("bg_asset", var1);
      }

      if(var2 != null) {
         var4.putParcelable("interactive_asset_uri", var2);
      }

      List var5 = var0.getBackgroundColorList();
      if(!Utility.isNullOrEmpty((Collection)var5)) {
         var4.putStringArrayList("top_background_color_list", new ArrayList(var5));
      }

      Utility.putNonEmptyString(var4, "content_url", var0.getAttributionLink());
      return var4;
   }

   private static Bundle create(ShareVideoContent var0, String var1, boolean var2) {
      Bundle var3 = createBaseParameters(var0, var2);
      Utility.putNonEmptyString(var3, "TITLE", var0.getContentTitle());
      Utility.putNonEmptyString(var3, "DESCRIPTION", var0.getContentDescription());
      Utility.putNonEmptyString(var3, "VIDEO", var1);
      return var3;
   }

   public static Bundle create(UUID var0, ShareContent var1, boolean var2) {
      Validate.notNull(var1, "shareContent");
      Validate.notNull(var0, "callId");
      if(var1 instanceof ShareLinkContent) {
         return create((ShareLinkContent)var1, var2);
      } else if(var1 instanceof SharePhotoContent) {
         SharePhotoContent var11 = (SharePhotoContent)var1;
         return create(var11, ShareInternalUtility.getPhotoUrls(var11, var0), var2);
      } else if(var1 instanceof ShareVideoContent) {
         ShareVideoContent var10 = (ShareVideoContent)var1;
         return create(var10, ShareInternalUtility.getVideoUrl(var10, var0), var2);
      } else if(var1 instanceof ShareOpenGraphContent) {
         ShareOpenGraphContent var8 = (ShareOpenGraphContent)var1;

         try {
            Bundle var4 = create(var8, ShareInternalUtility.removeNamespacesFromOGJsonObject(ShareInternalUtility.toJSONObjectForCall(var0, var8), false), var2);
            return var4;
         } catch (JSONException var3) {
            StringBuilder var9 = new StringBuilder();
            var9.append("Unable to create a JSON Object from the provided ShareOpenGraphContent: ");
            var9.append(var3.getMessage());
            throw new FacebookException(var9.toString());
         }
      } else if(var1 instanceof ShareMediaContent) {
         ShareMediaContent var7 = (ShareMediaContent)var1;
         return create(var7, ShareInternalUtility.getMediaInfos(var7, var0), var2);
      } else if(var1 instanceof ShareCameraEffectContent) {
         ShareCameraEffectContent var6 = (ShareCameraEffectContent)var1;
         return create(var6, ShareInternalUtility.getTextureUrlBundle(var6, var0), var2);
      } else if(var1 instanceof ShareMessengerGenericTemplateContent) {
         return create((ShareMessengerGenericTemplateContent)var1, var2);
      } else if(var1 instanceof ShareMessengerOpenGraphMusicTemplateContent) {
         return create((ShareMessengerOpenGraphMusicTemplateContent)var1, var2);
      } else if(var1 instanceof ShareMessengerMediaTemplateContent) {
         return create((ShareMessengerMediaTemplateContent)var1, var2);
      } else if(var1 instanceof ShareStoryContent) {
         ShareStoryContent var5 = (ShareStoryContent)var1;
         return create(var5, ShareInternalUtility.getBackgroundAssetMediaInfo(var5, var0), ShareInternalUtility.getStickerUrl(var5, var0), var2);
      } else {
         return null;
      }
   }

   private static Bundle createBaseParameters(ShareContent var0, boolean var1) {
      Bundle var2 = new Bundle();
      Utility.putUri(var2, "LINK", var0.getContentUrl());
      Utility.putNonEmptyString(var2, "PLACE", var0.getPlaceId());
      Utility.putNonEmptyString(var2, "PAGE", var0.getPageId());
      Utility.putNonEmptyString(var2, "REF", var0.getRef());
      var2.putBoolean("DATA_FAILURES_FATAL", var1);
      List var3 = var0.getPeopleIds();
      if(!Utility.isNullOrEmpty((Collection)var3)) {
         var2.putStringArrayList("FRIENDS", new ArrayList(var3));
      }

      ShareHashtag var4 = var0.getShareHashtag();
      if(var4 != null) {
         Utility.putNonEmptyString(var2, "HASHTAG", var4.getHashtag());
      }

      return var2;
   }
}
