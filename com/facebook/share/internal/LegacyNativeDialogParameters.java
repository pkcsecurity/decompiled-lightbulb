package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class LegacyNativeDialogParameters {

   private static Bundle create(ShareLinkContent var0, boolean var1) {
      Bundle var2 = createBaseParameters(var0, var1);
      Utility.putNonEmptyString(var2, "com.facebook.platform.extra.TITLE", var0.getContentTitle());
      Utility.putNonEmptyString(var2, "com.facebook.platform.extra.DESCRIPTION", var0.getContentDescription());
      Utility.putUri(var2, "com.facebook.platform.extra.IMAGE", var0.getImageUrl());
      return var2;
   }

   private static Bundle create(ShareOpenGraphContent var0, JSONObject var1, boolean var2) {
      Bundle var3 = createBaseParameters(var0, var2);
      Utility.putNonEmptyString(var3, "com.facebook.platform.extra.PREVIEW_PROPERTY_NAME", var0.getPreviewPropertyName());
      Utility.putNonEmptyString(var3, "com.facebook.platform.extra.ACTION_TYPE", var0.getAction().getActionType());
      Utility.putNonEmptyString(var3, "com.facebook.platform.extra.ACTION", var1.toString());
      return var3;
   }

   private static Bundle create(SharePhotoContent var0, List<String> var1, boolean var2) {
      Bundle var3 = createBaseParameters(var0, var2);
      var3.putStringArrayList("com.facebook.platform.extra.PHOTOS", new ArrayList(var1));
      return var3;
   }

   private static Bundle create(ShareVideoContent var0, boolean var1) {
      return null;
   }

   public static Bundle create(UUID var0, ShareContent var1, boolean var2) {
      Validate.notNull(var1, "shareContent");
      Validate.notNull(var0, "callId");
      if(var1 instanceof ShareLinkContent) {
         return create((ShareLinkContent)var1, var2);
      } else if(var1 instanceof SharePhotoContent) {
         SharePhotoContent var7 = (SharePhotoContent)var1;
         return create(var7, ShareInternalUtility.getPhotoUrls(var7, var0), var2);
      } else if(var1 instanceof ShareVideoContent) {
         return create((ShareVideoContent)var1, var2);
      } else if(var1 instanceof ShareOpenGraphContent) {
         ShareOpenGraphContent var5 = (ShareOpenGraphContent)var1;

         try {
            Bundle var4 = create(var5, ShareInternalUtility.toJSONObjectForCall(var0, var5), var2);
            return var4;
         } catch (JSONException var3) {
            StringBuilder var6 = new StringBuilder();
            var6.append("Unable to create a JSON Object from the provided ShareOpenGraphContent: ");
            var6.append(var3.getMessage());
            throw new FacebookException(var6.toString());
         }
      } else {
         return null;
      }
   }

   private static Bundle createBaseParameters(ShareContent var0, boolean var1) {
      Bundle var2 = new Bundle();
      Utility.putUri(var2, "com.facebook.platform.extra.LINK", var0.getContentUrl());
      Utility.putNonEmptyString(var2, "com.facebook.platform.extra.PLACE", var0.getPlaceId());
      Utility.putNonEmptyString(var2, "com.facebook.platform.extra.REF", var0.getRef());
      var2.putBoolean("com.facebook.platform.extra.DATA_FAILURES_FATAL", var1);
      List var3 = var0.getPeopleIds();
      if(!Utility.isNullOrEmpty((Collection)var3)) {
         var2.putStringArrayList("com.facebook.platform.extra.FRIENDS", new ArrayList(var3));
      }

      return var2;
   }
}
