package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.yoga.YogaUnit;

@DoNotStrip
public class YogaValue {

   static final YogaValue AUTO = new YogaValue(Float.NaN, YogaUnit.AUTO);
   static final YogaValue UNDEFINED = new YogaValue(Float.NaN, YogaUnit.UNDEFINED);
   static final YogaValue ZERO = new YogaValue(0.0F, YogaUnit.POINT);
   public final YogaUnit unit;
   public final float value;


   @DoNotStrip
   YogaValue(float var1, int var2) {
      this(var1, YogaUnit.fromInt(var2));
   }

   public YogaValue(float var1, YogaUnit var2) {
      this.value = var1;
      this.unit = var2;
   }

   public static YogaValue parse(String var0) {
      return var0 == null?null:("undefined".equals(var0)?UNDEFINED:("auto".equals(var0)?AUTO:(var0.endsWith("%")?new YogaValue(Float.parseFloat(var0.substring(0, var0.length() - 1)), YogaUnit.PERCENT):new YogaValue(Float.parseFloat(var0), YogaUnit.POINT))));
   }

   public boolean equals(Object var1) {
      boolean var3 = var1 instanceof YogaValue;
      boolean var2 = false;
      if(var3) {
         YogaValue var4 = (YogaValue)var1;
         if(this.unit == var4.unit) {
            if(this.unit == YogaUnit.UNDEFINED || Float.compare(this.value, var4.value) == 0) {
               var2 = true;
            }

            return var2;
         }
      }

      return false;
   }

   public int hashCode() {
      return Float.floatToIntBits(this.value) + this.unit.intValue();
   }

   public String toString() {
      switch(null.$SwitchMap$com$facebook$yoga$YogaUnit[this.unit.ordinal()]) {
      case 1:
         return "undefined";
      case 2:
         return Float.toString(this.value);
      case 3:
         StringBuilder var1 = new StringBuilder();
         var1.append(this.value);
         var1.append("%");
         return var1.toString();
      case 4:
         return "auto";
      default:
         throw new IllegalStateException();
      }
   }
}
