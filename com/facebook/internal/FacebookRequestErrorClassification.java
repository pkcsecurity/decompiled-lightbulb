package com.facebook.internal;

import com.facebook.FacebookRequestError;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FacebookRequestErrorClassification {

   public static final int EC_APP_NOT_INSTALLED = 412;
   public static final int EC_APP_TOO_MANY_CALLS = 4;
   public static final int EC_INVALID_SESSION = 102;
   public static final int EC_INVALID_TOKEN = 190;
   public static final int EC_RATE = 9;
   public static final int EC_SERVICE_UNAVAILABLE = 2;
   public static final int EC_TOO_MANY_USER_ACTION_CALLS = 341;
   public static final int EC_USER_TOO_MANY_CALLS = 17;
   public static final int ESC_APP_INACTIVE = 493;
   public static final int ESC_APP_NOT_INSTALLED = 458;
   public static final String KEY_LOGIN_RECOVERABLE = "login_recoverable";
   public static final String KEY_NAME = "name";
   public static final String KEY_OTHER = "other";
   public static final String KEY_RECOVERY_MESSAGE = "recovery_message";
   public static final String KEY_TRANSIENT = "transient";
   private static FacebookRequestErrorClassification defaultInstance;
   private final Map<Integer, Set<Integer>> loginRecoverableErrors;
   private final String loginRecoverableRecoveryMessage;
   private final Map<Integer, Set<Integer>> otherErrors;
   private final String otherRecoveryMessage;
   private final Map<Integer, Set<Integer>> transientErrors;
   private final String transientRecoveryMessage;


   FacebookRequestErrorClassification(Map<Integer, Set<Integer>> var1, Map<Integer, Set<Integer>> var2, Map<Integer, Set<Integer>> var3, String var4, String var5, String var6) {
      this.otherErrors = var1;
      this.transientErrors = var2;
      this.loginRecoverableErrors = var3;
      this.otherRecoveryMessage = var4;
      this.transientRecoveryMessage = var5;
      this.loginRecoverableRecoveryMessage = var6;
   }

   public static FacebookRequestErrorClassification createFromJSON(JSONArray var0) {
      if(var0 == null) {
         return null;
      } else {
         int var1 = 0;
         Map var7 = null;
         Map var2 = var7;
         Map var3 = var7;
         Object var4 = var7;
         Object var5 = var7;

         Object var6;
         Object var11;
         for(var6 = var7; var1 < var0.length(); var5 = var11) {
            JSONObject var12 = var0.optJSONObject(var1);
            Map var8;
            Map var9;
            Object var10;
            if(var12 == null) {
               var8 = var7;
               var9 = var2;
               var10 = var4;
               var11 = var5;
            } else {
               String var13 = var12.optString("name");
               if(var13 == null) {
                  var8 = var7;
                  var9 = var2;
                  var10 = var4;
                  var11 = var5;
               } else if(var13.equalsIgnoreCase("other")) {
                  var10 = var12.optString("recovery_message", (String)null);
                  var8 = parseJSONDefinition(var12);
                  var9 = var2;
                  var11 = var5;
               } else if(var13.equalsIgnoreCase("transient")) {
                  var11 = var12.optString("recovery_message", (String)null);
                  var9 = parseJSONDefinition(var12);
                  var8 = var7;
                  var10 = var4;
               } else {
                  var8 = var7;
                  var9 = var2;
                  var10 = var4;
                  var11 = var5;
                  if(var13.equalsIgnoreCase("login_recoverable")) {
                     var6 = var12.optString("recovery_message", (String)null);
                     var3 = parseJSONDefinition(var12);
                     var11 = var5;
                     var10 = var4;
                     var9 = var2;
                     var8 = var7;
                  }
               }
            }

            ++var1;
            var7 = var8;
            var2 = var9;
            var4 = var10;
         }

         return new FacebookRequestErrorClassification(var7, var2, var3, (String)var4, (String)var5, (String)var6);
      }
   }

   public static FacebookRequestErrorClassification getDefaultErrorClassification() {
      synchronized(FacebookRequestErrorClassification.class){}

      FacebookRequestErrorClassification var0;
      try {
         if(defaultInstance == null) {
            defaultInstance = getDefaultErrorClassificationImpl();
         }

         var0 = defaultInstance;
      } finally {
         ;
      }

      return var0;
   }

   private static FacebookRequestErrorClassification getDefaultErrorClassificationImpl() {
      return new FacebookRequestErrorClassification((Map)null, new HashMap() {
         {
            this.put(Integer.valueOf(2), (Object)null);
            this.put(Integer.valueOf(4), (Object)null);
            this.put(Integer.valueOf(9), (Object)null);
            this.put(Integer.valueOf(17), (Object)null);
            this.put(Integer.valueOf(341), (Object)null);
         }
      }, new HashMap() {
         {
            this.put(Integer.valueOf(102), (Object)null);
            this.put(Integer.valueOf(190), (Object)null);
            this.put(Integer.valueOf(412), (Object)null);
         }
      }, (String)null, (String)null, (String)null);
   }

   private static Map<Integer, Set<Integer>> parseJSONDefinition(JSONObject var0) {
      JSONArray var6 = var0.optJSONArray("items");
      if(var6.length() == 0) {
         return null;
      } else {
         HashMap var7 = new HashMap();

         for(int var1 = 0; var1 < var6.length(); ++var1) {
            var0 = var6.optJSONObject(var1);
            if(var0 != null) {
               int var3 = var0.optInt("code");
               if(var3 != 0) {
                  JSONArray var8 = var0.optJSONArray("subcodes");
                  HashSet var9;
                  if(var8 != null && var8.length() > 0) {
                     HashSet var5 = new HashSet();
                     int var2 = 0;

                     while(true) {
                        var9 = var5;
                        if(var2 >= var8.length()) {
                           break;
                        }

                        int var4 = var8.optInt(var2);
                        if(var4 != 0) {
                           var5.add(Integer.valueOf(var4));
                        }

                        ++var2;
                     }
                  } else {
                     var9 = null;
                  }

                  var7.put(Integer.valueOf(var3), var9);
               }
            }
         }

         return var7;
      }
   }

   public FacebookRequestError.Category classify(int var1, int var2, boolean var3) {
      if(var3) {
         return FacebookRequestError.Category.TRANSIENT;
      } else {
         Set var4;
         if(this.otherErrors != null && this.otherErrors.containsKey(Integer.valueOf(var1))) {
            var4 = (Set)this.otherErrors.get(Integer.valueOf(var1));
            if(var4 == null || var4.contains(Integer.valueOf(var2))) {
               return FacebookRequestError.Category.OTHER;
            }
         }

         if(this.loginRecoverableErrors != null && this.loginRecoverableErrors.containsKey(Integer.valueOf(var1))) {
            var4 = (Set)this.loginRecoverableErrors.get(Integer.valueOf(var1));
            if(var4 == null || var4.contains(Integer.valueOf(var2))) {
               return FacebookRequestError.Category.LOGIN_RECOVERABLE;
            }
         }

         if(this.transientErrors != null && this.transientErrors.containsKey(Integer.valueOf(var1))) {
            var4 = (Set)this.transientErrors.get(Integer.valueOf(var1));
            if(var4 == null || var4.contains(Integer.valueOf(var2))) {
               return FacebookRequestError.Category.TRANSIENT;
            }
         }

         return FacebookRequestError.Category.OTHER;
      }
   }

   public Map<Integer, Set<Integer>> getLoginRecoverableErrors() {
      return this.loginRecoverableErrors;
   }

   public Map<Integer, Set<Integer>> getOtherErrors() {
      return this.otherErrors;
   }

   public String getRecoveryMessage(FacebookRequestError.Category var1) {
      switch(null.$SwitchMap$com$facebook$FacebookRequestError$Category[var1.ordinal()]) {
      case 1:
         return this.otherRecoveryMessage;
      case 2:
         return this.loginRecoverableRecoveryMessage;
      case 3:
         return this.transientRecoveryMessage;
      default:
         return null;
      }
   }

   public Map<Integer, Set<Integer>> getTransientErrors() {
      return this.transientErrors;
   }
}
