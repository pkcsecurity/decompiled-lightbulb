package com.facebook.react.flat;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import com.facebook.infer.annotation.Assertions;
import java.util.HashMap;
import javax.annotation.Nullable;

final class TypefaceCache {

   private static final String[] EXTENSIONS = new String[]{"", "_bold", "_italic", "_bold_italic"};
   private static final String[] FILE_EXTENSIONS = new String[]{".ttf", ".otf"};
   private static final HashMap<String, Typeface[]> FONTFAMILY_CACHE = new HashMap();
   private static final String FONTS_ASSET_PATH = "fonts/";
   private static final int MAX_STYLES = 4;
   private static final HashMap<Typeface, Typeface[]> TYPEFACE_CACHE = new HashMap();
   @Nullable
   private static AssetManager sAssetManager;


   private static Typeface createTypeface(String var0, int var1) {
      String var6 = EXTENSIONS[var1];
      StringBuilder var5 = new StringBuilder(32);
      var5.append("fonts/");
      var5.append(var0);
      var5.append(var6);
      int var3 = var5.length();
      String[] var9 = FILE_EXTENSIONS;
      int var4 = var9.length;
      int var2 = 0;

      while(var2 < var4) {
         var5.append(var9[var2]);
         String var7 = var5.toString();

         try {
            Typeface var10 = Typeface.createFromAsset(sAssetManager, var7);
            return var10;
         } catch (RuntimeException var8) {
            var5.setLength(var3);
            ++var2;
         }
      }

      return (Typeface)Assertions.assumeNotNull(Typeface.create(var0, var1));
   }

   public static Typeface getTypeface(Typeface var0, int var1) {
      if(var0 == null) {
         return Typeface.defaultFromStyle(var1);
      } else {
         Typeface[] var3 = (Typeface[])TYPEFACE_CACHE.get(var0);
         Typeface[] var2;
         if(var3 == null) {
            var2 = new Typeface[4];
            var2[var0.getStyle()] = var0;
         } else {
            var2 = var3;
            if(var3[var1] != null) {
               return var3[var1];
            }
         }

         var0 = Typeface.create(var0, var1);
         var2[var1] = var0;
         TYPEFACE_CACHE.put(var0, var2);
         return var0;
      }
   }

   public static Typeface getTypeface(String var0, int var1) {
      Typeface[] var3 = (Typeface[])FONTFAMILY_CACHE.get(var0);
      Typeface[] var2;
      if(var3 == null) {
         var2 = new Typeface[4];
         FONTFAMILY_CACHE.put(var0, var2);
      } else {
         var2 = var3;
         if(var3[var1] != null) {
            return var3[var1];
         }
      }

      Typeface var4 = createTypeface(var0, var1);
      var2[var1] = var4;
      TYPEFACE_CACHE.put(var4, var2);
      return var4;
   }

   public static void setAssetManager(AssetManager var0) {
      sAssetManager = var0;
   }
}
