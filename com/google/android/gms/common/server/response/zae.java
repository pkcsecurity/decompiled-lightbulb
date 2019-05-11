package com.google.android.gms.common.server.response;

import com.google.android.gms.common.server.response.FastParser;
import java.io.BufferedReader;
import java.io.IOException;

final class zae implements FastParser.zaa<Boolean> {

   // $FF: synthetic method
   public final Object zah(FastParser var1, BufferedReader var2) throws FastParser.ParseException, IOException {
      return Boolean.valueOf(FastParser.zaa(var1, var2, false));
   }
}
