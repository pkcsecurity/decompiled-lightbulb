package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ObjectsCompat;

public class Pair<F extends Object, S extends Object> {

   @Nullable
   public final F first;
   @Nullable
   public final S second;


   public Pair(@Nullable F var1, @Nullable S var2) {
      this.first = var1;
      this.second = var2;
   }

   @NonNull
   public static <A extends Object, B extends Object> Pair<A, B> create(@Nullable A var0, @Nullable B var1) {
      return new Pair(var0, var1);
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof Pair;
      boolean var3 = false;
      if(!var2) {
         return false;
      } else {
         Pair var4 = (Pair)var1;
         var2 = var3;
         if(ObjectsCompat.equals(var4.first, this.first)) {
            var2 = var3;
            if(ObjectsCompat.equals(var4.second, this.second)) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   public int hashCode() {
      Object var3 = this.first;
      int var2 = 0;
      int var1;
      if(var3 == null) {
         var1 = 0;
      } else {
         var1 = this.first.hashCode();
      }

      if(this.second != null) {
         var2 = this.second.hashCode();
      }

      return var1 ^ var2;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Pair{");
      var1.append(String.valueOf(this.first));
      var1.append(" ");
      var1.append(String.valueOf(this.second));
      var1.append("}");
      return var1.toString();
   }
}
