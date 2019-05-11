package com.facebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Pair;
import java.io.FileNotFoundException;
import java.util.UUID;

public class FacebookContentProvider extends ContentProvider {

   private static final String ATTACHMENT_URL_BASE = "content://com.facebook.app.FacebookContentProvider";
   private static final String INVALID_FILE_NAME = "..";
   private static final String TAG = "com.facebook.FacebookContentProvider";


   public static String getAttachmentUrl(String var0, UUID var1, String var2) {
      return String.format("%s%s/%s/%s", new Object[]{"content://com.facebook.app.FacebookContentProvider", var0, var1.toString(), var2});
   }

   public int delete(Uri var1, String var2, String[] var3) {
      return 0;
   }

   public String getType(Uri var1) {
      return null;
   }

   public Uri insert(Uri var1, ContentValues var2) {
      return null;
   }

   public boolean onCreate() {
      return true;
   }

   public ParcelFileDescriptor openFile(Uri param1, String param2) throws FileNotFoundException {
      // $FF: Couldn't be decompiled
   }

   Pair<UUID, String> parseCallIdAndAttachmentName(Uri param1) {
      // $FF: Couldn't be decompiled
   }

   public Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5) {
      return null;
   }

   public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
      return 0;
   }
}
