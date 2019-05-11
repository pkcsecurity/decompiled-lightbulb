package com.google.android.gms.common.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzq;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@KeepForSdk
public final class Objects {

   private Objects() {
      throw new AssertionError("Uninstantiable");
   }

   @KeepForSdk
   public static boolean equal(@Nullable Object var0, @Nullable Object var1) {
      return var0 == var1 || var0 != null && var0.equals(var1);
   }

   @KeepForSdk
   public static int hashCode(Object ... var0) {
      return Arrays.hashCode(var0);
   }

   @KeepForSdk
   public static Objects.ToStringHelper toStringHelper(Object var0) {
      return new Objects.ToStringHelper(var0, (zzq)null);
   }

   @KeepForSdk
   public static final class ToStringHelper {

      private final List<String> zzer;
      private final Object zzes;


      private ToStringHelper(Object var1) {
         this.zzes = Preconditions.checkNotNull(var1);
         this.zzer = new ArrayList();
      }

      // $FF: synthetic method
      ToStringHelper(Object var1, zzq var2) {
         this(var1);
      }

      @KeepForSdk
      public final Objects.ToStringHelper add(String var1, @Nullable Object var2) {
         List var3 = this.zzer;
         var1 = (String)Preconditions.checkNotNull(var1);
         String var5 = String.valueOf(var2);
         StringBuilder var4 = new StringBuilder(String.valueOf(var1).length() + 1 + String.valueOf(var5).length());
         var4.append(var1);
         var4.append("=");
         var4.append(var5);
         var3.add(var4.toString());
         return this;
      }

      @KeepForSdk
      public final String toString() {
         StringBuilder var3 = new StringBuilder(100);
         var3.append(this.zzes.getClass().getSimpleName());
         var3.append('{');
         int var2 = this.zzer.size();

         for(int var1 = 0; var1 < var2; ++var1) {
            var3.append((String)this.zzer.get(var1));
            if(var1 < var2 - 1) {
               var3.append(", ");
            }
         }

         var3.append('}');
         return var3.toString();
      }
   }
}
