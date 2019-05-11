package com.facebook.cache.common;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.MultiCacheKey;
import com.facebook.common.util.SecureHashUtil;
import java.io.UnsupportedEncodingException;
import java.util.List;

public final class CacheKeyUtil {

   public static String getFirstResourceId(CacheKey var0) {
      try {
         if(var0 instanceof MultiCacheKey) {
            return secureHashKey((CacheKey)((MultiCacheKey)var0).getCacheKeys().get(0));
         } else {
            String var2 = secureHashKey(var0);
            return var2;
         }
      } catch (UnsupportedEncodingException var1) {
         throw new RuntimeException(var1);
      }
   }

   public static List<String> getResourceIds(CacheKey param0) {
      // $FF: Couldn't be decompiled
   }

   private static String secureHashKey(CacheKey var0) throws UnsupportedEncodingException {
      return SecureHashUtil.makeSHA1HashBase64(var0.getUriString().getBytes("UTF-8"));
   }
}
