package com.facebook.react.views.text;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.SparseArray;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

public class ReactFontManager {

   private static final String[] EXTENSIONS = new String[]{"", "_bold", "_italic", "_bold_italic"};
   private static final String[] FILE_EXTENSIONS = new String[]{".ttf", ".otf"};
   private static final String FONTS_ASSET_PATH = "fonts/";
   private static ReactFontManager sReactFontManagerInstance;
   private Map<String, ReactFontManager.FontFamily> mFontCache = new HashMap();


   @Nullable
   private static Typeface createTypeface(String var0, int var1, AssetManager var2) {
      String var5 = EXTENSIONS[var1];
      String[] var6 = FILE_EXTENSIONS;
      int var4 = var6.length;
      int var3 = 0;

      while(var3 < var4) {
         String var7 = var6[var3];
         StringBuilder var8 = new StringBuilder();
         var8.append("fonts/");
         var8.append(var0);
         var8.append(var5);
         var8.append(var7);
         var7 = var8.toString();

         try {
            Typeface var10 = Typeface.createFromAsset(var2, var7);
            return var10;
         } catch (RuntimeException var9) {
            ++var3;
         }
      }

      return Typeface.create(var0, var1);
   }

   public static ReactFontManager getInstance() {
      if(sReactFontManagerInstance == null) {
         sReactFontManagerInstance = new ReactFontManager();
      }

      return sReactFontManagerInstance;
   }

   @Nullable
   public Typeface getTypeface(String var1, int var2, AssetManager var3) {
      ReactFontManager.FontFamily var5 = (ReactFontManager.FontFamily)this.mFontCache.get(var1);
      ReactFontManager.FontFamily var4 = var5;
      if(var5 == null) {
         var4 = new ReactFontManager.FontFamily(null);
         this.mFontCache.put(var1, var4);
      }

      Typeface var6 = var4.getTypeface(var2);
      Typeface var7 = var6;
      if(var6 == null) {
         Typeface var8 = createTypeface(var1, var2, var3);
         var7 = var8;
         if(var8 != null) {
            var4.setTypeface(var2, var8);
            var7 = var8;
         }
      }

      return var7;
   }

   public void setTypeface(String var1, int var2, Typeface var3) {
      if(var3 != null) {
         ReactFontManager.FontFamily var5 = (ReactFontManager.FontFamily)this.mFontCache.get(var1);
         ReactFontManager.FontFamily var4 = var5;
         if(var5 == null) {
            var4 = new ReactFontManager.FontFamily(null);
            this.mFontCache.put(var1, var4);
         }

         var4.setTypeface(var2, var3);
      }

   }

   static class FontFamily {

      private SparseArray<Typeface> mTypefaceSparseArray;


      private FontFamily() {
         this.mTypefaceSparseArray = new SparseArray(4);
      }

      // $FF: synthetic method
      FontFamily(Object var1) {
         this();
      }

      public Typeface getTypeface(int var1) {
         return (Typeface)this.mTypefaceSparseArray.get(var1);
      }

      public void setTypeface(int var1, Typeface var2) {
         this.mTypefaceSparseArray.put(var1, var2);
      }
   }
}
