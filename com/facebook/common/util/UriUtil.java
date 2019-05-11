package com.facebook.common.util;

import android.content.ContentResolver;
import android.net.Uri;
import android.net.Uri.Builder;
import android.provider.ContactsContract;
import android.provider.MediaStore.Images.Media;
import java.io.File;
import javax.annotation.Nullable;

public class UriUtil {

   public static final String DATA_SCHEME = "data";
   public static final String HTTPS_SCHEME = "https";
   public static final String HTTP_SCHEME = "http";
   public static final String LOCAL_ASSET_SCHEME = "asset";
   private static final String LOCAL_CONTACT_IMAGE_PREFIX = Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "display_photo").getPath();
   public static final String LOCAL_CONTENT_SCHEME = "content";
   public static final String LOCAL_FILE_SCHEME = "file";
   public static final String LOCAL_RESOURCE_SCHEME = "res";
   public static final String QUALIFIED_RESOURCE_SCHEME = "android.resource";


   @Nullable
   public static String getRealPathFromUri(ContentResolver param0, Uri param1) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   public static String getSchemeOrNull(@Nullable Uri var0) {
      return var0 == null?null:var0.getScheme();
   }

   public static Uri getUriForFile(File var0) {
      return Uri.fromFile(var0);
   }

   public static Uri getUriForQualifiedResource(String var0, int var1) {
      return (new Builder()).scheme("android.resource").authority(var0).path(String.valueOf(var1)).build();
   }

   public static Uri getUriForResourceId(int var0) {
      return (new Builder()).scheme("res").path(String.valueOf(var0)).build();
   }

   public static boolean isDataUri(@Nullable Uri var0) {
      return "data".equals(getSchemeOrNull(var0));
   }

   public static boolean isLocalAssetUri(@Nullable Uri var0) {
      return "asset".equals(getSchemeOrNull(var0));
   }

   public static boolean isLocalCameraUri(Uri var0) {
      String var1 = var0.toString();
      return var1.startsWith(Media.EXTERNAL_CONTENT_URI.toString()) || var1.startsWith(Media.INTERNAL_CONTENT_URI.toString());
   }

   public static boolean isLocalContactUri(Uri var0) {
      return isLocalContentUri(var0) && "com.android.contacts".equals(var0.getAuthority()) && !var0.getPath().startsWith(LOCAL_CONTACT_IMAGE_PREFIX);
   }

   public static boolean isLocalContentUri(@Nullable Uri var0) {
      return "content".equals(getSchemeOrNull(var0));
   }

   public static boolean isLocalFileUri(@Nullable Uri var0) {
      return "file".equals(getSchemeOrNull(var0));
   }

   public static boolean isLocalResourceUri(@Nullable Uri var0) {
      return "res".equals(getSchemeOrNull(var0));
   }

   public static boolean isNetworkUri(@Nullable Uri var0) {
      String var1 = getSchemeOrNull(var0);
      return "https".equals(var1) || "http".equals(var1);
   }

   public static boolean isQualifiedResourceUri(@Nullable Uri var0) {
      return "android.resource".equals(getSchemeOrNull(var0));
   }

   public static Uri parseUriOrNull(@Nullable String var0) {
      return var0 != null?Uri.parse(var0):null;
   }
}
