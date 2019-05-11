package com.facebook.internal;

import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

class ProfileInformationCache {

   private static final ConcurrentHashMap<String, JSONObject> infoCache = new ConcurrentHashMap();


   public static JSONObject getProfileInformation(String var0) {
      return (JSONObject)infoCache.get(var0);
   }

   public static void putProfileInformation(String var0, JSONObject var1) {
      infoCache.put(var0, var1);
   }
}
