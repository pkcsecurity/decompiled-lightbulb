package com.google.android.gms.maps.model;

import com.google.android.gms.maps.model.PatternItem;

public final class Dash extends PatternItem {

   public final float a;


   public final String toString() {
      float var1 = this.a;
      StringBuilder var2 = new StringBuilder(30);
      var2.append("[Dash: length=");
      var2.append(var1);
      var2.append("]");
      return var2.toString();
   }
}
