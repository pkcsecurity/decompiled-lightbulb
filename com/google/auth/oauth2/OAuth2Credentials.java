package com.google.auth.oauth2;

import com.google.api.client.util.Clock;
import com.google.common.annotations.VisibleForTesting;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import ps.a;

public class OAuth2Credentials extends or {

   private static final long serialVersionUID = 4556936364828217687L;
   @VisibleForTesting
   protected transient Clock a;
   private final Object b;
   private Map<String, List<String>> c;
   private os d;
   private transient List<OAuth2Credentials.CredentialsChangedListener> e;


   protected OAuth2Credentials() {
      this((os)null);
   }

   public OAuth2Credentials(os var1) {
      this.b = new byte[0];
      this.a = Clock.a;
      if(var1 != null) {
         this.a(var1);
      }

   }

   protected static <T extends Object> T a(Class<? extends T> var0, T var1) {
      return rj.a(ServiceLoader.load(var0), var1);
   }

   protected static <T extends Object> T a(String var0) throws IOException, ClassNotFoundException {
      try {
         Object var2 = Class.forName(var0).newInstance();
         return var2;
      } catch (IllegalAccessException var1) {
         throw new IOException(var1);
      }
   }

   private void a(os var1) {
      this.d = var1;
      StringBuilder var2 = new StringBuilder();
      var2.append("Bearer ");
      var2.append(var1.a());
      this.c = Collections.singletonMap("Authorization", Collections.singletonList(var2.toString()));
   }

   private boolean a() {
      Long var1 = this.e();
      return this.c == null || var1 != null && var1.longValue() <= 60000L;
   }

   private Long e() {
      if(this.d == null) {
         return null;
      } else {
         Date var1 = this.d.b();
         return var1 == null?null:Long.valueOf(var1.getTime() - this.a.a());
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      this.a = Clock.a;
   }

   public Map<String, List<String>> a(URI param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void b() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public os c() throws IOException {
      throw new IllegalStateException("OAuth2Credentials instance does not support refreshing the access token. An instance with a new access token should be used, or a derived type that supports refreshing should be used.");
   }

   protected a d() {
      return ps.a(this).a("requestMetadata", this.c).a("temporaryAccess", this.d);
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof OAuth2Credentials;
      boolean var3 = false;
      if(!var2) {
         return false;
      } else {
         OAuth2Credentials var4 = (OAuth2Credentials)var1;
         var2 = var3;
         if(Objects.equals(this.c, var4.c)) {
            var2 = var3;
            if(Objects.equals(this.d, var4.d)) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.c, this.d});
   }

   public String toString() {
      return this.d().toString();
   }

   public interface CredentialsChangedListener {

      void a(OAuth2Credentials var1) throws IOException;
   }
}
