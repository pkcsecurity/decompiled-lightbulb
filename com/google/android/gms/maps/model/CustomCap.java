package com.google.android.gms.maps.model;

import com.google.android.gms.maps.model.Cap;

public final class CustomCap extends Cap {

   public final jc a;
   public final float b;


   public final String toString() {
      String var2 = String.valueOf(this.a);
      float var1 = this.b;
      StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 55);
      var3.append("[CustomCap: bitmapDescriptor=");
      var3.append(var2);
      var3.append(" refWidth=");
      var3.append(var1);
      var3.append("]");
      return var3.toString();
   }
}
