package android.support.v4.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompatApi21Impl;
import android.support.v4.provider.FontsContractCompat;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

@RequiresApi(26)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {

   private static final String ABORT_CREATION_METHOD = "abortCreation";
   private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
   private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
   private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
   private static final String DEFAULT_FAMILY = "sans-serif";
   private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
   private static final String FREEZE_METHOD = "freeze";
   private static final int RESOLVE_BY_FONT_TABLE = -1;
   private static final String TAG = "TypefaceCompatApi26Impl";
   protected final Method mAbortCreation;
   protected final Method mAddFontFromAssetManager;
   protected final Method mAddFontFromBuffer;
   protected final Method mCreateFromFamiliesWithDefault;
   protected final Class mFontFamily;
   protected final Constructor mFontFamilyCtor;
   protected final Method mFreeze;


   public TypefaceCompatApi26Impl() {
      Object var9 = null;

      Class var1;
      Method var3;
      Method var4;
      Method var5;
      Method var6;
      Method var7;
      Constructor var11;
      try {
         var1 = this.obtainFontFamily();
         var11 = this.obtainFontFamilyCtor(var1);
         var3 = this.obtainAddFontFromAssetManagerMethod(var1);
         var4 = this.obtainAddFontFromBufferMethod(var1);
         var5 = this.obtainFreezeMethod(var1);
         var6 = this.obtainAbortCreationMethod(var1);
         var7 = this.obtainCreateFromFamiliesWithDefaultMethod(var1);
      } catch (NoSuchMethodException var10) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Unable to collect necessary methods for class ");
         var2.append(var10.getClass().getName());
         Log.e("TypefaceCompatApi26Impl", var2.toString(), var10);
         Object var8 = null;
         var7 = (Method)var8;
         var6 = (Method)var8;
         var5 = (Method)var8;
         var4 = (Method)var8;
         var3 = (Method)var8;
         var11 = (Constructor)var8;
         var1 = (Class)var9;
      }

      this.mFontFamily = var1;
      this.mFontFamilyCtor = var11;
      this.mAddFontFromAssetManager = var3;
      this.mAddFontFromBuffer = var4;
      this.mFreeze = var5;
      this.mAbortCreation = var6;
      this.mCreateFromFamiliesWithDefault = var7;
   }

   private void abortCreation(Object var1) {
      try {
         this.mAbortCreation.invoke(var1, new Object[0]);
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2);
      }
   }

   private boolean addFontFromAssetManager(Context var1, Object var2, String var3, int var4, int var5, int var6, @Nullable FontVariationAxis[] var7) {
      try {
         boolean var8 = ((Boolean)this.mAddFontFromAssetManager.invoke(var2, new Object[]{var1.getAssets(), var3, Integer.valueOf(0), Boolean.valueOf(false), Integer.valueOf(var4), Integer.valueOf(var5), Integer.valueOf(var6), var7})).booleanValue();
         return var8;
      } catch (InvocationTargetException var9) {
         throw new RuntimeException(var9);
      }
   }

   private boolean addFontFromBuffer(Object var1, ByteBuffer var2, int var3, int var4, int var5) {
      try {
         boolean var6 = ((Boolean)this.mAddFontFromBuffer.invoke(var1, new Object[]{var2, Integer.valueOf(var3), null, Integer.valueOf(var4), Integer.valueOf(var5)})).booleanValue();
         return var6;
      } catch (InvocationTargetException var7) {
         throw new RuntimeException(var7);
      }
   }

   private boolean freeze(Object var1) {
      try {
         boolean var2 = ((Boolean)this.mFreeze.invoke(var1, new Object[0])).booleanValue();
         return var2;
      } catch (InvocationTargetException var3) {
         throw new RuntimeException(var3);
      }
   }

   private boolean isFontFamilyPrivateAPIAvailable() {
      if(this.mAddFontFromAssetManager == null) {
         Log.w("TypefaceCompatApi26Impl", "Unable to collect necessary private methods. Fallback to legacy implementation.");
      }

      return this.mAddFontFromAssetManager != null;
   }

   private Object newFamily() {
      try {
         Object var1 = this.mFontFamilyCtor.newInstance(new Object[0]);
         return var1;
      } catch (InvocationTargetException var2) {
         throw new RuntimeException(var2);
      }
   }

   protected Typeface createFromFamiliesWithDefault(Object var1) {
      try {
         Object var2 = Array.newInstance(this.mFontFamily, 1);
         Array.set(var2, 0, var1);
         Typeface var4 = (Typeface)this.mCreateFromFamiliesWithDefault.invoke((Object)null, new Object[]{var2, Integer.valueOf(-1), Integer.valueOf(-1)});
         return var4;
      } catch (InvocationTargetException var3) {
         throw new RuntimeException(var3);
      }
   }

   public Typeface createFromFontFamilyFilesResourceEntry(Context var1, FontResourcesParserCompat.FontFamilyFilesResourceEntry var2, Resources var3, int var4) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:629)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
   }

   public Typeface createFromFontInfo(Context var1, @Nullable CancellationSignal var2, @NonNull FontsContractCompat.FontInfo[] var3, int var4) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:629)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
   }

   @Nullable
   public Typeface createFromResourcesFontFile(Context var1, Resources var2, int var3, String var4, int var5) {
      if(!this.isFontFamilyPrivateAPIAvailable()) {
         return super.createFromResourcesFontFile(var1, var2, var3, var4, var5);
      } else {
         Object var6 = this.newFamily();
         if(!this.addFontFromAssetManager(var1, var6, var4, 0, -1, -1, (FontVariationAxis[])null)) {
            this.abortCreation(var6);
            return null;
         } else {
            return !this.freeze(var6)?null:this.createFromFamiliesWithDefault(var6);
         }
      }
   }

   protected Method obtainAbortCreationMethod(Class var1) throws NoSuchMethodException {
      return var1.getMethod("abortCreation", new Class[0]);
   }

   protected Method obtainAddFontFromAssetManagerMethod(Class var1) throws NoSuchMethodException {
      return var1.getMethod("addFontFromAssetManager", new Class[]{AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class});
   }

   protected Method obtainAddFontFromBufferMethod(Class var1) throws NoSuchMethodException {
      return var1.getMethod("addFontFromBuffer", new Class[]{ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE});
   }

   protected Method obtainCreateFromFamiliesWithDefaultMethod(Class var1) throws NoSuchMethodException {
      Method var2 = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", new Class[]{Array.newInstance(var1, 1).getClass(), Integer.TYPE, Integer.TYPE});
      var2.setAccessible(true);
      return var2;
   }

   protected Class obtainFontFamily() throws ClassNotFoundException {
      return Class.forName("android.graphics.FontFamily");
   }

   protected Constructor obtainFontFamilyCtor(Class var1) throws NoSuchMethodException {
      return var1.getConstructor(new Class[0]);
   }

   protected Method obtainFreezeMethod(Class var1) throws NoSuchMethodException {
      return var1.getMethod("freeze", new Class[0]);
   }
}
