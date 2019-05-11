package com.facebook.react.modules.storage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.facebook.react.bridge.ReadableArray;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

public class AsyncLocalStorageUtil {

   static String buildKeySelection(int var0) {
      String[] var1 = new String[var0];
      Arrays.fill(var1, "?");
      StringBuilder var2 = new StringBuilder();
      var2.append("key IN (");
      var2.append(TextUtils.join(", ", var1));
      var2.append(")");
      return var2.toString();
   }

   static String[] buildKeySelectionArgs(ReadableArray var0, int var1, int var2) {
      String[] var4 = new String[var2];

      for(int var3 = 0; var3 < var2; ++var3) {
         var4[var3] = var0.getString(var1 + var3);
      }

      return var4;
   }

   private static void deepMergeInto(JSONObject var0, JSONObject var1) throws JSONException {
      Iterator var2 = var1.keys();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         JSONObject var4 = var1.optJSONObject(var3);
         JSONObject var5 = var0.optJSONObject(var3);
         if(var4 != null && var5 != null) {
            deepMergeInto(var5, var4);
            var0.put(var3, var5);
         } else {
            var0.put(var3, var1.get(var3));
         }
      }

   }

   @Nullable
   public static String getItemImpl(SQLiteDatabase param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   static boolean mergeImpl(SQLiteDatabase var0, String var1, String var2) throws JSONException {
      String var3 = getItemImpl(var0, var1);
      if(var3 != null) {
         JSONObject var4 = new JSONObject(var3);
         deepMergeInto(var4, new JSONObject(var2));
         var2 = var4.toString();
      }

      return setItemImpl(var0, var1, var2);
   }

   static boolean setItemImpl(SQLiteDatabase var0, String var1, String var2) {
      ContentValues var3 = new ContentValues();
      var3.put("key", var1);
      var3.put("value", var2);
      return -1L != var0.insertWithOnConflict("catalystLocalStorage", (String)null, var3, 5);
   }
}
