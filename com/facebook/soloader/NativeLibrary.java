package com.facebook.soloader;

import java.util.List;

public abstract class NativeLibrary {

   private static final String TAG = "com.facebook.soloader.NativeLibrary";
   private boolean mLibrariesLoaded = false;
   private List<String> mLibraryNames;
   private volatile UnsatisfiedLinkError mLinkError = null;
   private Boolean mLoadLibraries = Boolean.valueOf(true);
   private final Object mLock = new Object();


   protected NativeLibrary(List<String> var1) {
      this.mLibraryNames = var1;
   }

   public void ensureLoaded() throws UnsatisfiedLinkError {
      if(!this.loadLibraries()) {
         throw this.mLinkError;
      }
   }

   public UnsatisfiedLinkError getError() {
      return this.mLinkError;
   }

   protected void initialNativeCheck() throws UnsatisfiedLinkError {}

   public boolean loadLibraries() {
      // $FF: Couldn't be decompiled
   }
}
