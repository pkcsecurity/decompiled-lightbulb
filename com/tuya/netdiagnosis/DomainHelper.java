package com.tuya.netdiagnosis;

import android.support.annotation.Keep;
import java.net.InetAddress;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata
@Keep
public final class DomainHelper {

   @Nullable
   public final InetAddress[] parse(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "domain");
      Map var2 = aip.b(var1);
      Object var3 = var2.get("remoteInet");
      if(var3 == null) {
         throw new bth("null cannot be cast to non-null type kotlin.Array<java.net.InetAddress>");
      } else {
         InetAddress[] var8 = (InetAddress[])var3;
         Object var6 = var2.get("useTime");
         if(var6 == null) {
            throw new bth("null cannot be cast to non-null type kotlin.String");
         } else {
            String var4 = (String)var6;
            InetAddress[] var7 = var8;
            if(var8 == null) {
               var7 = var8;
               if(Integer.parseInt(var4) > 10000) {
                  Object var5 = aip.b(var1).get("remoteInet");
                  if(var5 == null) {
                     throw new bth("null cannot be cast to non-null type kotlin.Array<java.net.InetAddress>");
                  }

                  var7 = (InetAddress[])var5;
               }
            }

            return var7;
         }
      }
   }
}
