package com.facebook.litho.boost;

import com.facebook.litho.boost.LithoAffinityBooster;
import java.util.HashMap;
import java.util.Map;

public abstract class LithoAffinityBoosterFactory {

   Map<String, LithoAffinityBooster> mBoosters = new HashMap();


   public LithoAffinityBooster acquireInstance(String var1) {
      LithoAffinityBooster var2 = (LithoAffinityBooster)this.mBoosters.get(var1);
      if(var2 != null) {
         return var2;
      } else {
         var2 = this.create(var1);
         this.mBoosters.put(var1, var2);
         return var2;
      }
   }

   public LithoAffinityBooster acquireInstance(String var1, int var2) {
      LithoAffinityBooster var3 = (LithoAffinityBooster)this.mBoosters.get(var1);
      if(var3 != null) {
         return var3;
      } else {
         var3 = this.create(var1, var2);
         this.mBoosters.put(var1, var3);
         return var3;
      }
   }

   protected abstract LithoAffinityBooster create(String var1);

   protected abstract LithoAffinityBooster create(String var1, int var2);
}
