package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompatBaseImpl;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v4.provider.FontsContractCompat;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;

@RequiresApi(24)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
class TypefaceCompatApi24Impl extends TypefaceCompatBaseImpl {

   private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
   private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
   private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
   private static final String TAG = "TypefaceCompatApi24Impl";
   private static final Method sAddFontWeightStyle;
   private static final Method sCreateFromFamiliesWithDefault;
   private static final Class sFontFamily;
   private static final Constructor sFontFamilyCtor;


   static {
      Object var4 = null;

      Constructor var0;
      Class var1;
      Object var2;
      Object var3;
      try {
         var1 = Class.forName("android.graphics.FontFamily");
         var0 = var1.getConstructor(new Class[0]);
         var3 = var1.getMethod("addFontWeightStyle", new Class[]{ByteBuffer.class, Integer.TYPE, List.class, Integer.TYPE, Boolean.TYPE});
         var2 = Typeface.class.getMethod("createFromFamiliesWithDefault", new Class[]{Array.newInstance(var1, 1).getClass()});
      } catch (NoSuchMethodException var5) {
         Log.e("TypefaceCompatApi24Impl", var5.getClass().getName(), var5);
         var1 = null;
         var3 = var1;
         var2 = var1;
         var0 = (Constructor)var4;
      }

      sFontFamilyCtor = var0;
      sFontFamily = var1;
      sAddFontWeightStyle = (Method)var3;
      sCreateFromFamiliesWithDefault = (Method)var2;
   }

   private static boolean addFontWeightStyle(Object var0, ByteBuffer var1, int var2, int var3, boolean var4) {
      try {
         var4 = ((Boolean)sAddFontWeightStyle.invoke(var0, new Object[]{var1, Integer.valueOf(var2), null, Integer.valueOf(var3), Boolean.valueOf(var4)})).booleanValue();
         return var4;
      } catch (InvocationTargetException var5) {
         throw new RuntimeException(var5);
      }
   }

   private static Typeface createFromFamiliesWithDefault(Object var0) {
      try {
         Object var1 = Array.newInstance(sFontFamily, 1);
         Array.set(var1, 0, var0);
         Typeface var3 = (Typeface)sCreateFromFamiliesWithDefault.invoke((Object)null, new Object[]{var1});
         return var3;
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2);
      }
   }

   public static boolean isUsable() {
      if(sAddFontWeightStyle == null) {
         Log.w("TypefaceCompatApi24Impl", "Unable to collect necessary private methods.Fallback to legacy implementation.");
      }

      return sAddFontWeightStyle != null;
   }

   private static Object newFamily() {
      try {
         Object var0 = sFontFamilyCtor.newInstance(new Object[0]);
         return var0;
      } catch (InvocationTargetException var1) {
         throw new RuntimeException(var1);
      }
   }

   public Typeface createFromFontFamilyFilesResourceEntry(Context var1, FontResourcesParserCompat.FontFamilyFilesResourceEntry var2, Resources var3, int var4) {
      Object var6 = newFamily();
      FontResourcesParserCompat.FontFileResourceEntry[] var9 = var2.getEntries();
      int var5 = var9.length;

      for(var4 = 0; var4 < var5; ++var4) {
         FontResourcesParserCompat.FontFileResourceEntry var7 = var9[var4];
         ByteBuffer var8 = TypefaceCompatUtil.copyToDirectBuffer(var1, var3, var7.getResourceId());
         if(var8 == null) {
            return null;
         }

         if(!addFontWeightStyle(var6, var8, var7.getTtcIndex(), var7.getWeight(), var7.isItalic())) {
            return null;
         }
      }

      return createFromFamiliesWithDefault(var6);
   }

   public Typeface createFromFontInfo(Context var1, @Nullable CancellationSignal var2, @NonNull FontsContractCompat.FontInfo[] var3, int var4) {
      Object var9 = newFamily();
      SimpleArrayMap var10 = new SimpleArrayMap();
      int var6 = var3.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         FontsContractCompat.FontInfo var11 = var3[var5];
         Uri var12 = var11.getUri();
         ByteBuffer var8 = (ByteBuffer)var10.get(var12);
         ByteBuffer var7 = var8;
         if(var8 == null) {
            var7 = TypefaceCompatUtil.mmap(var1, var2, var12);
            var10.put(var12, var7);
         }

         if(!addFontWeightStyle(var9, var7, var11.getTtcIndex(), var11.getWeight(), var11.isItalic())) {
            return null;
         }
      }

      return Typeface.create(createFromFamiliesWithDefault(var9), var4);
   }
}
