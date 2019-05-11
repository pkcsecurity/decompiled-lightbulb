package com.google.api.client.http;

import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.util.BackOff;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Sleeper;
import java.io.IOException;

@Beta
public class HttpBackOffUnsuccessfulResponseHandler implements HttpUnsuccessfulResponseHandler {

   private final BackOff a;
   private HttpBackOffUnsuccessfulResponseHandler.BackOffRequired b;
   private Sleeper c;


   public final boolean a(mf var1, mh var2, boolean var3) throws IOException {
      if(!var3) {
         return false;
      } else if(this.b.a(var2)) {
         try {
            var3 = nn.a(this.c, this.a);
            return var3;
         } catch (InterruptedException var4) {
            return false;
         }
      } else {
         return false;
      }
   }

   @Beta
   public interface BackOffRequired {

      HttpBackOffUnsuccessfulResponseHandler.BackOffRequired a = new HttpBackOffUnsuccessfulResponseHandler.BackOffRequired() {
         public boolean a(mh var1) {
            return true;
         }
      };
      HttpBackOffUnsuccessfulResponseHandler.BackOffRequired b = new HttpBackOffUnsuccessfulResponseHandler.BackOffRequired() {
         public boolean a(mh var1) {
            return var1.c() / 100 == 5;
         }
      };


      boolean a(mh var1);
   }
}
