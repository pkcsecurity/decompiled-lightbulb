package com.tuya.netdiagnosis;

import android.content.Context;
import android.support.annotation.Keep;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata
@Keep
public final class NetHelper {

   public final boolean isNetConnected(@NotNull Context var1) {
      Intrinsics.checkParameterIsNotNull(var1, "context");
      Boolean var2 = aip.b(var1);
      return var2 != null?var2.booleanValue():false;
   }
}
