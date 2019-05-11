package com.tuya.netdiagnosis;

import android.support.annotation.Keep;
import com.tuya.netdiagnosis.LDNetDiagnoService.LDNetPing;
import com.tuya.netdiagnosis.LDNetDiagnoService.LDNetPing.LDNetPingListener;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata
@Keep
public final class PingHelper {

   private LDNetPing netPing;


   @JvmOverloads
   public PingHelper() {
      this(0, (LDNetPingListener)null, 3, (DefaultConstructorMarker)null);
   }

   @JvmOverloads
   public PingHelper(int var1) {
      this(var1, (LDNetPingListener)null, 2, (DefaultConstructorMarker)null);
   }

   @JvmOverloads
   public PingHelper(int var1, @Nullable LDNetPingListener var2) {
      this.netPing = new LDNetPing(var1, var2);
   }

   @JvmOverloads
   // $FF: synthetic method
   public PingHelper(int var1, LDNetPingListener var2, int var3, DefaultConstructorMarker var4) {
      if((var3 & 1) != 0) {
         var1 = 4;
      }

      if((var3 & 2) != 0) {
         var2 = (LDNetPingListener)null;
      }

      this(var1, var2);
   }

   public final void cancel() {}

   public final boolean ping(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "host");
      return this.netPing.exec(var1, false);
   }
}
