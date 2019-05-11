package com.google.android.gms.common.internal;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public final class Preconditions {

   private Preconditions() {
      throw new AssertionError("Uninstantiable");
   }

   @KeepForSdk
   public static void checkArgument(boolean var0) {
      if(!var0) {
         throw new IllegalArgumentException();
      }
   }

   @KeepForSdk
   public static void checkArgument(boolean var0, Object var1) {
      if(!var0) {
         throw new IllegalArgumentException(String.valueOf(var1));
      }
   }

   @KeepForSdk
   public static void checkArgument(boolean var0, String var1, Object ... var2) {
      if(!var0) {
         throw new IllegalArgumentException(String.format(var1, var2));
      }
   }

   @KeepForSdk
   public static void checkHandlerThread(Handler var0) {
      if(Looper.myLooper() != var0.getLooper()) {
         throw new IllegalStateException("Must be called on the handler thread");
      }
   }

   @KeepForSdk
   public static void checkMainThread(String var0) {
      if(!com.google.android.gms.common.util.zzc.isMainThread()) {
         throw new IllegalStateException(var0);
      }
   }

   @KeepForSdk
   public static String checkNotEmpty(String var0) {
      if(TextUtils.isEmpty(var0)) {
         throw new IllegalArgumentException("Given String is empty or null");
      } else {
         return var0;
      }
   }

   @KeepForSdk
   public static String checkNotEmpty(String var0, Object var1) {
      if(TextUtils.isEmpty(var0)) {
         throw new IllegalArgumentException(String.valueOf(var1));
      } else {
         return var0;
      }
   }

   @KeepForSdk
   public static void checkNotMainThread() {
      checkNotMainThread("Must not be called on the main application thread");
   }

   @KeepForSdk
   public static void checkNotMainThread(String var0) {
      if(com.google.android.gms.common.util.zzc.isMainThread()) {
         throw new IllegalStateException(var0);
      }
   }

   @NonNull
   @KeepForSdk
   public static <T extends Object> T checkNotNull(@Nullable T var0) {
      if(var0 == null) {
         throw new NullPointerException("null reference");
      } else {
         return var0;
      }
   }

   @NonNull
   @KeepForSdk
   public static <T extends Object> T checkNotNull(T var0, Object var1) {
      if(var0 == null) {
         throw new NullPointerException(String.valueOf(var1));
      } else {
         return var0;
      }
   }

   @KeepForSdk
   public static int checkNotZero(int var0) {
      if(var0 == 0) {
         throw new IllegalArgumentException("Given Integer is zero");
      } else {
         return var0;
      }
   }

   @KeepForSdk
   public static int checkNotZero(int var0, Object var1) {
      if(var0 == 0) {
         throw new IllegalArgumentException(String.valueOf(var1));
      } else {
         return var0;
      }
   }

   @KeepForSdk
   public static long checkNotZero(long var0) {
      if(var0 == 0L) {
         throw new IllegalArgumentException("Given Long is zero");
      } else {
         return var0;
      }
   }

   @KeepForSdk
   public static long checkNotZero(long var0, Object var2) {
      if(var0 == 0L) {
         throw new IllegalArgumentException(String.valueOf(var2));
      } else {
         return var0;
      }
   }

   @KeepForSdk
   public static void checkState(boolean var0) {
      if(!var0) {
         throw new IllegalStateException();
      }
   }

   @KeepForSdk
   public static void checkState(boolean var0, Object var1) {
      if(!var0) {
         throw new IllegalStateException(String.valueOf(var1));
      }
   }

   @KeepForSdk
   public static void checkState(boolean var0, String var1, Object ... var2) {
      if(!var0) {
         throw new IllegalStateException(String.format(var1, var2));
      }
   }
}
