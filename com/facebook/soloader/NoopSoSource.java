package com.facebook.soloader;

import com.facebook.soloader.SoSource;
import java.io.File;

public class NoopSoSource extends SoSource {

   public int loadLibrary(String var1, int var2) {
      return 1;
   }

   public File unpackLibrary(String var1) {
      throw new UnsupportedOperationException("unpacking not supported in test mode");
   }
}
