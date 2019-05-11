package com.facebook.soloader;

import android.content.Context;
import com.facebook.soloader.UnpackingSoSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class ExoSoSource extends UnpackingSoSource {

   public ExoSoSource(Context var1, String var2) {
      super(var1, var2);
   }

   protected UnpackingSoSource.Unpacker makeUnpacker() throws IOException {
      return new ExoSoSource.ExoUnpacker(this);
   }

   final class ExoUnpacker extends UnpackingSoSource.Unpacker {

      private final ExoSoSource.FileDso[] mDsos;
      // $FF: synthetic field
      final ExoSoSource this$0;


      ExoUnpacker(ExoSoSource param1) throws IOException {
         // $FF: Couldn't be decompiled
      }

      protected UnpackingSoSource.DsoManifest getDsoManifest() throws IOException {
         return new UnpackingSoSource.DsoManifest(this.mDsos);
      }

      protected UnpackingSoSource.InputDsoIterator openDsoIterator() throws IOException {
         return new ExoSoSource.FileBackedInputDsoIterator(null);
      }
   }

   final class FileBackedInputDsoIterator extends UnpackingSoSource.InputDsoIterator {

      private int mCurrentDso;


      private FileBackedInputDsoIterator() {}

      // $FF: synthetic method
      FileBackedInputDsoIterator(Object var2) {
         this();
      }

      public boolean hasNext() {
         return this.mCurrentDso < ExoSoSource.super.mDsos.length;
      }

      public UnpackingSoSource.InputDso next() throws IOException {
         ExoSoSource.FileDso[] var2 = ExoSoSource.super.mDsos;
         int var1 = this.mCurrentDso;
         this.mCurrentDso = var1 + 1;
         ExoSoSource.FileDso var3 = var2[var1];
         FileInputStream var6 = new FileInputStream(var3.backingFile);

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

   static final class FileDso extends UnpackingSoSource.Dso {

      final File backingFile;


      FileDso(String var1, String var2, File var3) {
         super(var1, var2);
         this.backingFile = var3;
      }
   }
}
