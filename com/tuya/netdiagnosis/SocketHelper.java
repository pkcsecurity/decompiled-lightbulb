package com.tuya.netdiagnosis;

import android.support.annotation.Keep;
import com.tuya.netdiagnosis.DomainHelper;
import com.tuya.netdiagnosis.LDNetDiagnoService.LDNetSocket;
import com.tuya.netdiagnosis.LDNetDiagnoService.LDNetSocket.LDNetSocketListener;
import com.tuya.netdiagnosis.model.DomainPort;
import java.net.InetAddress;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata
@Keep
public final class SocketHelper {

   private LDNetSocket netSocket;


   @JvmOverloads
   public SocketHelper(@NotNull LDNetSocketListener var1) {
      this(var1, false, 2, (DefaultConstructorMarker)null);
   }

   @JvmOverloads
   public SocketHelper(@NotNull LDNetSocketListener var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var1, "listener");
      super();
      LDNetSocket var3 = LDNetSocket.getInstance();
      var3.initListener(var1);
      var3.isCConn = var2;
      Intrinsics.checkExpressionValueIsNotNull(var3, "LDNetSocket.getInstance(… 设置是否启用C进行connected\n    }");
      this.netSocket = var3;
   }

   @JvmOverloads
   // $FF: synthetic method
   public SocketHelper(LDNetSocketListener var1, boolean var2, int var3, DefaultConstructorMarker var4) {
      if((var3 & 2) != 0) {
         var2 = false;
      }

      this(var1, var2);
   }

   @JvmOverloads
   // $FF: synthetic method
   public static boolean connect$default(SocketHelper var0, DomainPort var1, InetAddress[] var2, int var3, Object var4) {
      if((var3 & 2) != 0) {
         var2 = (InetAddress[])null;
      }

      return var0.connect(var1, var2);
   }

   public final void cancel() {
      this.netSocket.resetInstance();
   }

   @JvmOverloads
   public final boolean connect(@NotNull DomainPort var1) {
      return connect$default(this, var1, (InetAddress[])null, 2, (Object)null);
   }

   @JvmOverloads
   public final boolean connect(@NotNull DomainPort var1, @Nullable InetAddress[] var2) {
      Intrinsics.checkParameterIsNotNull(var1, "domainPort");
      LDNetSocket var3 = this.netSocket;
      if(var2 == null) {
         var2 = (new DomainHelper()).parse(var1.getDomain());
      }

      var3.remoteInet = var2;
      return this.netSocket.exec(var1.getDomain(), var1.getPort());
   }
}
