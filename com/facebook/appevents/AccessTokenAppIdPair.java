package com.facebook.appevents;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import java.io.Serializable;

class AccessTokenAppIdPair implements Serializable {

   private static final long serialVersionUID = 1L;
   private final String accessTokenString;
   private final String applicationId;


   public AccessTokenAppIdPair(AccessToken var1) {
      this(var1.getToken(), FacebookSdk.getApplicationId());
   }

   public AccessTokenAppIdPair(String var1, String var2) {
      String var3 = var1;
      if(Utility.isNullOrEmpty(var1)) {
         var3 = null;
      }

      this.accessTokenString = var3;
      this.applicationId = var2;
   }

   private Object writeReplace() {
      return new AccessTokenAppIdPair.SerializationProxyV1(this.accessTokenString, this.applicationId, null);
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof AccessTokenAppIdPair;
      boolean var3 = false;
      if(!var2) {
         return false;
      } else {
         AccessTokenAppIdPair var4 = (AccessTokenAppIdPair)var1;
         var2 = var3;
         if(Utility.areObjectsEqual(var4.accessTokenString, this.accessTokenString)) {
            var2 = var3;
            if(Utility.areObjectsEqual(var4.applicationId, this.applicationId)) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   public String getAccessTokenString() {
      return this.accessTokenString;
   }

   public String getApplicationId() {
      return this.applicationId;
   }

   public int hashCode() {
      String var3 = this.accessTokenString;
      int var2 = 0;
      int var1;
      if(var3 == null) {
         var1 = 0;
      } else {
         var1 = this.accessTokenString.hashCode();
      }

      if(this.applicationId != null) {
         var2 = this.applicationId.hashCode();
      }

      return var1 ^ var2;
   }

   static class SerializationProxyV1 implements Serializable {

      private static final long serialVersionUID = -2488473066578201069L;
      private final String accessTokenString;
      private final String appId;


      private SerializationProxyV1(String var1, String var2) {
         this.accessTokenString = var1;
         this.appId = var2;
      }

      // $FF: synthetic method
      SerializationProxyV1(String var1, String var2, Object var3) {
         this(var1, var2);
      }

      private Object readResolve() {
         return new AccessTokenAppIdPair(this.accessTokenString, this.appId);
      }
   }
}
