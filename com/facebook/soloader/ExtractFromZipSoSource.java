package com.facebook.soloader;

import android.content.Context;
import com.facebook.soloader.SysUtil;
import com.facebook.soloader.UnpackingSoSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ExtractFromZipSoSource extends UnpackingSoSource {

   protected final File mZipFileName;
   protected final String mZipSearchPattern;


   public ExtractFromZipSoSource(Context var1, String var2, File var3, String var4) {
      super(var1, var2);
      this.mZipFileName = var3;
      this.mZipSearchPattern = var4;
   }

   protected UnpackingSoSource.Unpacker makeUnpacker() throws IOException {
      return new ExtractFromZipSoSource.ZipUnpacker();
   }

   final class ZipBackedInputDsoIterator extends UnpackingSoSource.InputDsoIterator {

      private int mCurrentDso;


      private ZipBackedInputDsoIterator() {}

      // $FF: synthetic method
      ZipBackedInputDsoIterator(Object var2) {
         this();
      }

      public boolean hasNext() {
         ExtractFromZipSoSource.this.ensureDsos();
         return this.mCurrentDso < ExtractFromZipSoSource.super.mDsos.length;
      }

      public UnpackingSoSource.InputDso next() throws IOException {
         ExtractFromZipSoSource.this.ensureDsos();
         ExtractFromZipSoSource.ZipDso[] var2 = ExtractFromZipSoSource.super.mDsos;
         int var1 = this.mCurrentDso;
         this.mCurrentDso = var1 + 1;
         ExtractFromZipSoSource.ZipDso var3 = var2[var1];
         InputStream var6 = ExtractFromZipSoSource.super.mZipFile.getInputStream(var3.backingEntry);

         try {
            UnpackingSoSource.InputDso var7 = new UnpackingSoSource.InputDso(var3, var6);
            return var7;
         } finally {
            if(var6 != null) {
               var6.close();
            }

         }
      }
   }

   public class ZipUnpacker extends UnpackingSoSource.Unpacker {

      private ExtractFromZipSoSource.ZipDso[] mDsos;
      private final ZipFile mZipFile;


      ZipUnpacker() throws IOException {
         this.mZipFile = new ZipFile(ExtractFromZipSoSource.this.mZipFileName);
      }

      public void close() throws IOException {
         this.mZipFile.close();
      }

      final ExtractFromZipSoSource.ZipDso[] ensureDsos() {
         if(this.mDsos == null) {
            HashMap var4 = new HashMap();
            Pattern var5 = Pattern.compile(ExtractFromZipSoSource.this.mZipSearchPattern);
            String[] var6 = SysUtil.getSupportedAbis();
            Enumeration var7 = this.mZipFile.entries();

            int var1;
            while(var7.hasMoreElements()) {
               ZipEntry var8 = (ZipEntry)var7.nextElement();
               Matcher var10 = var5.matcher(var8.getName());
               if(var10.matches()) {
                  String var9 = var10.group(1);
                  String var17 = var10.group(2);
                  var1 = SysUtil.findAbiScore(var6, var9);
                  if(var1 >= 0) {
                     ExtractFromZipSoSource.ZipDso var16 = (ExtractFromZipSoSource.ZipDso)var4.get(var17);
                     if(var16 == null || var1 < var16.abiScore) {
                        var4.put(var17, new ExtractFromZipSoSource.ZipDso(var17, var8, var1));
                     }
                  }
               }
            }

            ExtractFromZipSoSource.ZipDso[] var12 = (ExtractFromZipSoSource.ZipDso[])var4.values().toArray(new ExtractFromZipSoSource.ZipDso[var4.size()]);
            Arrays.sort(var12);
            byte var3 = 0;
            var1 = 0;

            int var2;
            for(var2 = 0; var1 < var12.length; ++var1) {
               ExtractFromZipSoSource.ZipDso var13 = var12[var1];
               if(this.shouldExtract(var13.backingEntry, var13.name)) {
                  ++var2;
               } else {
                  var12[var1] = null;
               }
            }

            ExtractFromZipSoSource.ZipDso[] var14 = new ExtractFromZipSoSource.ZipDso[var2];
            var2 = 0;

            int var11;
            for(var1 = var3; var1 < var12.length; var2 = var11) {
               ExtractFromZipSoSource.ZipDso var15 = var12[var1];
               var11 = var2;
               if(var15 != null) {
                  var14[var2] = var15;
                  var11 = var2 + 1;
               }

               ++var1;
            }

            this.mDsos = var14;
         }

         return this.mDsos;
      }

      protected final UnpackingSoSource.DsoManifest getDsoManifest() throws IOException {
         return new UnpackingSoSource.DsoManifest(this.ensureDsos());
      }

      protected final UnpackingSoSource.InputDsoIterator openDsoIterator() throws IOException {
         return new ExtractFromZipSoSource.ZipBackedInputDsoIterator(null);
      }

      protected boolean shouldExtract(ZipEntry var1, String var2) {
         return true;
      }
   }

   static final class ZipDso extends UnpackingSoSource.Dso implements Comparable {

      final int abiScore;
      final ZipEntry backingEntry;


      ZipDso(String var1, ZipEntry var2, int var3) {
         super(var1, makePseudoHash(var2));
         this.backingEntry = var2;
         this.abiScore = var3;
      }

      private static String makePseudoHash(ZipEntry var0) {
         return String.format("pseudo-zip-hash-1-%s-%s-%s-%s", new Object[]{var0.getName(), Long.valueOf(var0.getSize()), Long.valueOf(var0.getCompressedSize()), Long.valueOf(var0.getCrc())});
      }

      public int compareTo(Object var1) {
         return this.name.compareTo(((ExtractFromZipSoSource.ZipDso)var1).name);
      }
   }
}
