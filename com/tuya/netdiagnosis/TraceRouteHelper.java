package com.tuya.netdiagnosis;

import android.support.annotation.Keep;
import com.tuya.netdiagnosis.LDNetDiagnoService.LDNetTraceRoute;
import com.tuya.netdiagnosis.LDNetDiagnoService.LDNetTraceRoute.LDNetTraceRouteListener;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata
@Keep
public final class TraceRouteHelper {

   private LDNetTraceRoute traceRoute;


   @JvmOverloads
   public TraceRouteHelper(@NotNull LDNetTraceRouteListener var1) {
      this(var1, false, 2, (DefaultConstructorMarker)null);
   }

   @JvmOverloads
   public TraceRouteHelper(@NotNull LDNetTraceRouteListener var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var1, "listener");
      super();
      LDNetTraceRoute var3 = LDNetTraceRoute.getInstance();
      var3.initListener(var1);
      var3.isCTrace = var2;
      Intrinsics.checkExpressionValueIsNotNull(var3, "LDNetTraceRoute.getInsta…ce = isUseJNICTrace\n    }");
      this.traceRoute = var3;
   }

   @JvmOverloads
   // $FF: synthetic method
   public TraceRouteHelper(LDNetTraceRouteListener var1, boolean var2, int var3, DefaultConstructorMarker var4) {
      if((var3 & 2) != 0) {
         var2 = true;
      }

      this(var1, var2);
   }

   public final void cancel() {
      this.traceRoute.resetInstance();
   }

   public final boolean traceRoute(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "host");
      return this.traceRoute.startTraceRoute(var1);
   }
}
