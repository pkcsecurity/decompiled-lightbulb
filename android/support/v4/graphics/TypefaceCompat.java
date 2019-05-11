package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.TypefaceCompatApi21Impl;
import android.support.v4.graphics.TypefaceCompatApi24Impl;
import android.support.v4.graphics.TypefaceCompatApi26Impl;
import android.support.v4.graphics.TypefaceCompatApi28Impl;
import android.support.v4.graphics.TypefaceCompatBaseImpl;
import android.support.v4.provider.FontsContractCompat;
import android.support.v4.util.LruCache;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TypefaceCompat {

   private static final String TAG = "TypefaceCompat";
   private static final LruCache<String, Typeface> sTypefaceCache;
   private static final TypefaceCompatBaseImpl sTypefaceCompatImpl;


   static {
      if(VERSION.SDK_INT >= 28) {
         sTypefaceCompatImpl = new TypefaceCompatApi28Impl();
      } else if(VERSION.SDK_INT >= 26) {
         sTypefaceCompatImpl = new TypefaceCompatApi26Impl();
      } else if(VERSION.SDK_INT >= 24 && TypefaceCompatApi24Impl.isUsable()) {
         sTypefaceCompatImpl = new TypefaceCompatApi24Impl();
      } else if(VERSION.SDK_INT >= 21) {
         sTypefaceCompatImpl = new TypefaceCompatApi21Impl();
      } else {
         sTypefaceCompatImpl = new TypefaceCompatBaseImpl();
      }

      sTypefaceCache = new LruCache(16);
   }

   @Nullable
   public static Typeface createFromFontInfo(@NonNull Context var0, @Nullable CancellationSignal var1, @NonNull FontsContractCompat.FontInfo[] var2, int var3) {
      return sTypefaceCompatImpl.createFromFontInfo(var0, var1, var2, var3);
   }

   @Nullable
   public static Typeface createFromResourcesFamilyXml(@NonNull Context var0, @NonNull FontResourcesParserCompat.FamilyResourceEntry var1, @NonNull Resources var2, int var3, int var4, @Nullable ResourcesCompat.FontCallback var5, @Nullable Handler var6, boolean var7) {
      Typeface var10;
      if(var1 instanceof FontResourcesParserCompat.ProviderResourceEntry) {
         boolean var9;
         FontResourcesParserCompat.ProviderResourceEntry var11;
         label31: {
            var11 = (FontResourcesParserCompat.ProviderResourceEntry)var1;
            var9 = false;
            if(var7) {
               if(var11.getFetchStrategy() != 0) {
                  break label31;
               }
            } else if(var5 != null) {
               break label31;
            }

            var9 = true;
         }

         int var8;
         if(var7) {
            var8 = var11.getTimeout();
         } else {
            var8 = -1;
         }

         var10 = FontsContractCompat.getFontSync(var0, var11.getRequest(), var5, var6, var9, var8, var4);
      } else {
         Typeface var12 = sTypefaceCompatImpl.createFromFontFamilyFilesResourceEntry(var0, (FontResourcesParserCompat.FontFamilyFilesResourceEntry)var1, var2, var4);
         var10 = var12;
         if(var5 != null) {
            if(var12 != null) {
               var5.callbackSuccessAsync(var12, var6);
               var10 = var12;
            } else {
               var5.callbackFailAsync(-3, var6);
               var10 = var12;
            }
         }
      }

      if(var10 != null) {
         sTypefaceCache.put(createResourceUid(var2, var3, var4), var10);
      }

      return var10;
   }

   @Nullable
   public static Typeface createFromResourcesFontFile(@NonNull Context var0, @NonNull Resources var1, int var2, String var3, int var4) {
      Typeface var5 = sTypefaceCompatImpl.createFromResourcesFontFile(var0, var1, var2, var3, var4);
      if(var5 != null) {
         String var6 = createResourceUid(var1, var2, var4);
         sTypefaceCache.put(var6, var5);
      }

      return var5;
   }

   private static String createResourceUid(Resources var0, int var1, int var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append(var0.getResourcePackageName(var1));
      var3.append("-");
      var3.append(var1);
      var3.append("-");
      var3.append(var2);
      return var3.toString();
   }

   @Nullable
   public static Typeface findFromCache(@NonNull Resources var0, int var1, int var2) {
      return (Typeface)sTypefaceCache.get(createResourceUid(var0, var1, var2));
   }
}
