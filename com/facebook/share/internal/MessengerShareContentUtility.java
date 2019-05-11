package com.facebook.share.internal;

import android.net.Uri;
import android.os.Bundle;
import com.facebook.internal.Utility;
import com.facebook.share.model.ShareMessengerActionButton;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerGenericTemplateElement;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import com.facebook.share.model.ShareMessengerURLActionButton;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessengerShareContentUtility {

   public static final String ATTACHMENT = "attachment";
   public static final String ATTACHMENT_ID = "attachment_id";
   public static final String ATTACHMENT_PAYLOAD = "payload";
   public static final String ATTACHMENT_TEMPLATE_TYPE = "template";
   public static final String ATTACHMENT_TYPE = "type";
   public static final String BUTTONS = "buttons";
   public static final String BUTTON_TYPE = "type";
   public static final String BUTTON_URL_TYPE = "web_url";
   public static final String DEFAULT_ACTION = "default_action";
   public static final String ELEMENTS = "elements";
   public static final Pattern FACEBOOK_DOMAIN = Pattern.compile("^(.+)\\.(facebook\\.com)$");
   public static final String FALLBACK_URL = "fallback_url";
   public static final String IMAGE_ASPECT_RATIO = "image_aspect_ratio";
   public static final String IMAGE_RATIO_HORIZONTAL = "horizontal";
   public static final String IMAGE_RATIO_SQUARE = "square";
   public static final String IMAGE_URL = "image_url";
   public static final String MEDIA_IMAGE = "image";
   public static final String MEDIA_TYPE = "media_type";
   public static final String MEDIA_VIDEO = "video";
   public static final String MESSENGER_EXTENSIONS = "messenger_extensions";
   public static final String PREVIEW_DEFAULT = "DEFAULT";
   public static final String PREVIEW_OPEN_GRAPH = "OPEN_GRAPH";
   public static final String SHARABLE = "sharable";
   public static final String SHARE_BUTTON_HIDE = "hide";
   public static final String SUBTITLE = "subtitle";
   public static final String TEMPLATE_GENERIC_TYPE = "generic";
   public static final String TEMPLATE_MEDIA_TYPE = "media";
   public static final String TEMPLATE_OPEN_GRAPH_TYPE = "open_graph";
   public static final String TEMPLATE_TYPE = "template_type";
   public static final String TITLE = "title";
   public static final String URL = "url";
   public static final String WEBVIEW_RATIO = "webview_height_ratio";
   public static final String WEBVIEW_RATIO_COMPACT = "compact";
   public static final String WEBVIEW_RATIO_FULL = "full";
   public static final String WEBVIEW_RATIO_TALL = "tall";
   public static final String WEBVIEW_SHARE_BUTTON = "webview_share_button";


   private static void addActionButton(Bundle var0, ShareMessengerActionButton var1, boolean var2) throws JSONException {
      if(var1 != null) {
         if(var1 instanceof ShareMessengerURLActionButton) {
            addURLActionButton(var0, (ShareMessengerURLActionButton)var1, var2);
         }

      }
   }

   public static void addGenericTemplateContent(Bundle var0, ShareMessengerGenericTemplateContent var1) throws JSONException {
      addGenericTemplateElementForPreview(var0, var1.getGenericTemplateElement());
      Utility.putJSONValueInBundle(var0, "MESSENGER_PLATFORM_CONTENT", serializeGenericTemplateContent(var1));
   }

   private static void addGenericTemplateElementForPreview(Bundle var0, ShareMessengerGenericTemplateElement var1) throws JSONException {
      if(var1.getButton() != null) {
         addActionButton(var0, var1.getButton(), false);
      } else if(var1.getDefaultAction() != null) {
         addActionButton(var0, var1.getDefaultAction(), true);
      }

      Utility.putUri(var0, "IMAGE", var1.getImageUrl());
      Utility.putNonEmptyString(var0, "PREVIEW_TYPE", "DEFAULT");
      Utility.putNonEmptyString(var0, "TITLE", var1.getTitle());
      Utility.putNonEmptyString(var0, "SUBTITLE", var1.getSubtitle());
   }

   public static void addMediaTemplateContent(Bundle var0, ShareMessengerMediaTemplateContent var1) throws JSONException {
      addMediaTemplateContentForPreview(var0, var1);
      Utility.putJSONValueInBundle(var0, "MESSENGER_PLATFORM_CONTENT", serializeMediaTemplateContent(var1));
   }

   private static void addMediaTemplateContentForPreview(Bundle var0, ShareMessengerMediaTemplateContent var1) throws JSONException {
      addActionButton(var0, var1.getButton(), false);
      Utility.putNonEmptyString(var0, "PREVIEW_TYPE", "DEFAULT");
      Utility.putNonEmptyString(var0, "ATTACHMENT_ID", var1.getAttachmentId());
      if(var1.getMediaUrl() != null) {
         Utility.putUri(var0, getMediaUrlKey(var1.getMediaUrl()), var1.getMediaUrl());
      }

      Utility.putNonEmptyString(var0, "type", getMediaType(var1.getMediaType()));
   }

   public static void addOpenGraphMusicTemplateContent(Bundle var0, ShareMessengerOpenGraphMusicTemplateContent var1) throws JSONException {
      addOpenGraphMusicTemplateContentForPreview(var0, var1);
      Utility.putJSONValueInBundle(var0, "MESSENGER_PLATFORM_CONTENT", serializeOpenGraphMusicTemplateContent(var1));
   }

   private static void addOpenGraphMusicTemplateContentForPreview(Bundle var0, ShareMessengerOpenGraphMusicTemplateContent var1) throws JSONException {
      addActionButton(var0, var1.getButton(), false);
      Utility.putNonEmptyString(var0, "PREVIEW_TYPE", "OPEN_GRAPH");
      Utility.putUri(var0, "OPEN_GRAPH_URL", var1.getUrl());
   }

   private static void addURLActionButton(Bundle var0, ShareMessengerURLActionButton var1, boolean var2) throws JSONException {
      String var3;
      if(var2) {
         var3 = Utility.getUriString(var1.getUrl());
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append(var1.getTitle());
         var4.append(" - ");
         var4.append(Utility.getUriString(var1.getUrl()));
         var3 = var4.toString();
      }

      Utility.putNonEmptyString(var0, "TARGET_DISPLAY", var3);
      Utility.putUri(var0, "ITEM_URL", var1.getUrl());
   }

   private static String getImageRatioString(ShareMessengerGenericTemplateContent.ImageAspectRatio var0) {
      return var0 == null?"horizontal":(null.$SwitchMap$com$facebook$share$model$ShareMessengerGenericTemplateContent$ImageAspectRatio[var0.ordinal()] != 1?"horizontal":"square");
   }

   private static String getMediaType(ShareMessengerMediaTemplateContent.MediaType var0) {
      return var0 == null?"image":(null.$SwitchMap$com$facebook$share$model$ShareMessengerMediaTemplateContent$MediaType[var0.ordinal()] != 1?"image":"video");
   }

   private static String getMediaUrlKey(Uri var0) {
      String var1 = var0.getHost();
      return !Utility.isNullOrEmpty(var1) && FACEBOOK_DOMAIN.matcher(var1).matches()?"uri":"IMAGE";
   }

   private static String getShouldHideShareButton(ShareMessengerURLActionButton var0) {
      return var0.getShouldHideWebviewShareButton()?"hide":null;
   }

   private static String getWebviewHeightRatioString(ShareMessengerURLActionButton.WebviewHeightRatio var0) {
      if(var0 == null) {
         return "full";
      } else {
         switch(null.$SwitchMap$com$facebook$share$model$ShareMessengerURLActionButton$WebviewHeightRatio[var0.ordinal()]) {
         case 1:
            return "compact";
         case 2:
            return "tall";
         default:
            return "full";
         }
      }
   }

   private static JSONObject serializeActionButton(ShareMessengerActionButton var0) throws JSONException {
      return serializeActionButton(var0, false);
   }

   private static JSONObject serializeActionButton(ShareMessengerActionButton var0, boolean var1) throws JSONException {
      return var0 instanceof ShareMessengerURLActionButton?serializeURLActionButton((ShareMessengerURLActionButton)var0, var1):null;
   }

   private static JSONObject serializeGenericTemplateContent(ShareMessengerGenericTemplateContent var0) throws JSONException {
      JSONArray var1 = (new JSONArray()).put(serializeGenericTemplateElement(var0.getGenericTemplateElement()));
      JSONObject var2 = (new JSONObject()).put("template_type", "generic").put("sharable", var0.getIsSharable()).put("image_aspect_ratio", getImageRatioString(var0.getImageAspectRatio())).put("elements", var1);
      var2 = (new JSONObject()).put("type", "template").put("payload", var2);
      return (new JSONObject()).put("attachment", var2);
   }

   private static JSONObject serializeGenericTemplateElement(ShareMessengerGenericTemplateElement var0) throws JSONException {
      JSONObject var1 = (new JSONObject()).put("title", var0.getTitle()).put("subtitle", var0.getSubtitle()).put("image_url", Utility.getUriString(var0.getImageUrl()));
      if(var0.getButton() != null) {
         JSONArray var2 = new JSONArray();
         var2.put(serializeActionButton(var0.getButton()));
         var1.put("buttons", var2);
      }

      if(var0.getDefaultAction() != null) {
         var1.put("default_action", serializeActionButton(var0.getDefaultAction(), true));
      }

      return var1;
   }

   private static JSONObject serializeMediaTemplateContent(ShareMessengerMediaTemplateContent var0) throws JSONException {
      JSONArray var1 = (new JSONArray()).put(serializeMediaTemplateElement(var0));
      JSONObject var2 = (new JSONObject()).put("template_type", "media").put("elements", var1);
      var2 = (new JSONObject()).put("type", "template").put("payload", var2);
      return (new JSONObject()).put("attachment", var2);
   }

   private static JSONObject serializeMediaTemplateElement(ShareMessengerMediaTemplateContent var0) throws JSONException {
      JSONObject var1 = (new JSONObject()).put("attachment_id", var0.getAttachmentId()).put("url", Utility.getUriString(var0.getMediaUrl())).put("media_type", getMediaType(var0.getMediaType()));
      if(var0.getButton() != null) {
         JSONArray var2 = new JSONArray();
         var2.put(serializeActionButton(var0.getButton()));
         var1.put("buttons", var2);
      }

      return var1;
   }

   private static JSONObject serializeOpenGraphMusicTemplateContent(ShareMessengerOpenGraphMusicTemplateContent var0) throws JSONException {
      JSONArray var1 = (new JSONArray()).put(serializeOpenGraphMusicTemplateElement(var0));
      JSONObject var2 = (new JSONObject()).put("template_type", "open_graph").put("elements", var1);
      var2 = (new JSONObject()).put("type", "template").put("payload", var2);
      return (new JSONObject()).put("attachment", var2);
   }

   private static JSONObject serializeOpenGraphMusicTemplateElement(ShareMessengerOpenGraphMusicTemplateContent var0) throws JSONException {
      JSONObject var1 = (new JSONObject()).put("url", Utility.getUriString(var0.getUrl()));
      if(var0.getButton() != null) {
         JSONArray var2 = new JSONArray();
         var2.put(serializeActionButton(var0.getButton()));
         var1.put("buttons", var2);
      }

      return var1;
   }

   private static JSONObject serializeURLActionButton(ShareMessengerURLActionButton var0, boolean var1) throws JSONException {
      JSONObject var3 = (new JSONObject()).put("type", "web_url");
      String var2;
      if(var1) {
         var2 = null;
      } else {
         var2 = var0.getTitle();
      }

      return var3.put("title", var2).put("url", Utility.getUriString(var0.getUrl())).put("webview_height_ratio", getWebviewHeightRatioString(var0.getWebviewHeightRatio())).put("messenger_extensions", var0.getIsMessengerExtensionURL()).put("fallback_url", Utility.getUriString(var0.getFallbackUrl())).put("webview_share_button", getShouldHideShareButton(var0));
   }
}
