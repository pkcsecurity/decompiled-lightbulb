package android.support.v13.view.inputmethod;

import android.os.Bundle;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.inputmethod.EditorInfo;

public final class EditorInfoCompat {

   private static final String CONTENT_MIME_TYPES_KEY = "android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
   private static final String[] EMPTY_STRING_ARRAY = new String[0];
   public static final int IME_FLAG_FORCE_ASCII = Integer.MIN_VALUE;
   public static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;


   @NonNull
   public static String[] getContentMimeTypes(EditorInfo var0) {
      String[] var1;
      if(VERSION.SDK_INT >= 25) {
         var1 = var0.contentMimeTypes;
         return var1 != null?var1:EMPTY_STRING_ARRAY;
      } else if(var0.extras == null) {
         return EMPTY_STRING_ARRAY;
      } else {
         var1 = var0.extras.getStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
         return var1 != null?var1:EMPTY_STRING_ARRAY;
      }
   }

   public static void setContentMimeTypes(@NonNull EditorInfo var0, @Nullable String[] var1) {
      if(VERSION.SDK_INT >= 25) {
         var0.contentMimeTypes = var1;
      } else {
         if(var0.extras == null) {
            var0.extras = new Bundle();
         }

         var0.extras.putStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES", var1);
      }
   }
}
