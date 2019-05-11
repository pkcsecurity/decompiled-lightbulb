package com.facebook.soloader;

import android.content.Context;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import com.facebook.soloader.NoopSoSource;
import com.facebook.soloader.SoSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;

public class SoLoader {

   static final boolean DEBUG = false;
   public static final int SOLOADER_ALLOW_ASYNC_INIT = 2;
   public static final int SOLOADER_ENABLE_EXOPACKAGE = 1;
   private static String SO_STORE_NAME_MAIN;
   static final boolean SYSTRACE_LIBRARY_LOADING = false;
   static final String TAG = "SoLoader";
   private static int sFlags;
   private static final Set<String> sLoadedLibraries = new HashSet();
   @Nullable
   private static ThreadPolicy sOldPolicy;
   @Nullable
   private static SoSource[] sSoSources;


   private static void assertInitialized() {
      if(sSoSources == null) {
         throw new RuntimeException("SoLoader.init() not yet called");
      }
   }

   public static void init(Context var0, int var1) throws IOException {
      ThreadPolicy var2 = StrictMode.allowThreadDiskWrites();

      try {
         initImpl(var0, var1);
      } finally {
         StrictMode.setThreadPolicy(var2);
      }

   }

   public static void init(Context var0, boolean var1) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
   }

   private static void initImpl(Context param0, int param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static void loadLibrary(String param0) throws UnsatisfiedLinkError {
      // $FF: Couldn't be decompiled
   }

   public static void loadLibraryBySoName(String var0, int var1) throws IOException {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:296)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
   }

   public static String makeLdLibraryPath() {
      // $FF: Couldn't be decompiled
   }

   private static int makePrepareFlags() {
      return (sFlags & 2) != 0?1:0;
   }

   public static void prependSoSource(SoSource param0) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static void setInTestMode() {
      sSoSources = new SoSource[]{new NoopSoSource()};
   }

   public static File unpackLibraryAndDependencies(String var0) throws UnsatisfiedLinkError {
      assertInitialized();

      try {
         File var2 = unpackLibraryBySoName(System.mapLibraryName(var0));
         return var2;
      } catch (IOException var1) {
         throw new RuntimeException(var1);
      }
   }

   static File unpackLibraryBySoName(String var0) throws IOException {
      for(int var1 = 0; var1 < sSoSources.length; ++var1) {
         File var2 = sSoSources[var1].unpackLibrary(var0);
         if(var2 != null) {
            return var2;
         }
      }

      throw new FileNotFoundException(var0);
   }

   public static final class WrongAbiError extends UnsatisfiedLinkError {

      WrongAbiError(Throwable var1) {
         super("APK was built for a different platform");
         this.initCause(var1);
      }
   }
}
