package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.zal;
import com.google.android.gms.common.server.response.zam;
import com.google.android.gms.common.server.response.zan;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@ShowFirstParty
@SafeParcelable.Class(
   creator = "FieldMappingDictionaryCreator"
)
public final class zak extends AbstractSafeParcelable {

   public static final Creator<zak> CREATOR = new zan();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int zale;
   private final HashMap<String, Map<String, FastJsonResponse.Field<?, ?>>> zaqu;
   @SafeParcelable.Field(
      getter = "getSerializedDictionary",
      id = 2
   )
   private final ArrayList<zal> zaqv;
   @SafeParcelable.Field(
      getter = "getRootClassName",
      id = 3
   )
   private final String zaqw;


   @SafeParcelable.Constructor
   zak(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) ArrayList<zal> var2, 
      @SafeParcelable.Param(
         id = 3
      ) String var3) {
      this.zale = var1;
      this.zaqv = null;
      HashMap var7 = new HashMap();
      int var5 = var2.size();

      for(var1 = 0; var1 < var5; ++var1) {
         zal var8 = (zal)var2.get(var1);
         String var9 = var8.className;
         HashMap var10 = new HashMap();
         int var6 = var8.zaqx.size();

         for(int var4 = 0; var4 < var6; ++var4) {
            zam var11 = (zam)var8.zaqx.get(var4);
            var10.put(var11.zaqy, var11.zaqz);
         }

         var7.put(var9, var10);
      }

      this.zaqu = var7;
      this.zaqw = (String)Preconditions.checkNotNull(var3);
      this.zacr();
   }

   public zak(Class<? extends FastJsonResponse> var1) {
      this.zale = 1;
      this.zaqv = null;
      this.zaqu = new HashMap();
      this.zaqw = var1.getCanonicalName();
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder();
      Iterator var2 = this.zaqu.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         var1.append(var3);
         var1.append(":\n");
         Map var6 = (Map)this.zaqu.get(var3);
         Iterator var4 = var6.keySet().iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            var1.append("  ");
            var1.append(var5);
            var1.append(": ");
            var1.append(var6.get(var5));
         }
      }

      return var1.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zale);
      ArrayList var3 = new ArrayList();
      Iterator var4 = this.zaqu.keySet().iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         var3.add(new zal(var5, (Map)this.zaqu.get(var5)));
      }

      SafeParcelWriter.writeTypedList(var1, 2, var3, false);
      SafeParcelWriter.writeString(var1, 3, this.zaqw, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   public final void zaa(Class<? extends FastJsonResponse> var1, Map<String, FastJsonResponse.Field<?, ?>> var2) {
      this.zaqu.put(var1.getCanonicalName(), var2);
   }

   public final boolean zaa(Class<? extends FastJsonResponse> var1) {
      return this.zaqu.containsKey(var1.getCanonicalName());
   }

   public final void zacr() {
      Iterator var1 = this.zaqu.keySet().iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         Map var4 = (Map)this.zaqu.get(var2);
         Iterator var3 = var4.keySet().iterator();

         while(var3.hasNext()) {
            ((FastJsonResponse.Field)var4.get((String)var3.next())).zaa(this);
         }
      }

   }

   public final void zacs() {
      Iterator var1 = this.zaqu.keySet().iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         Map var3 = (Map)this.zaqu.get(var2);
         HashMap var4 = new HashMap();
         Iterator var5 = var3.keySet().iterator();

         while(var5.hasNext()) {
            String var6 = (String)var5.next();
            var4.put(var6, ((FastJsonResponse.Field)var3.get(var6)).zacl());
         }

         this.zaqu.put(var2, var4);
      }

   }

   public final String zact() {
      return this.zaqw;
   }

   public final Map<String, FastJsonResponse.Field<?, ?>> zai(String var1) {
      return (Map)this.zaqu.get(var1);
   }
}
