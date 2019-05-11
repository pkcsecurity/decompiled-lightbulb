package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.zam;
import com.google.android.gms.common.server.response.zao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@ShowFirstParty
@SafeParcelable.Class(
   creator = "FieldMappingDictionaryEntryCreator"
)
public final class zal extends AbstractSafeParcelable {

   public static final Creator<zal> CREATOR = new zao();
   @SafeParcelable.Field(
      id = 2
   )
   final String className;
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int versionCode;
   @SafeParcelable.Field(
      id = 3
   )
   final ArrayList<zam> zaqx;


   @SafeParcelable.Constructor
   zal(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) String var2, 
      @SafeParcelable.Param(
         id = 3
      ) ArrayList<zam> var3) {
      this.versionCode = var1;
      this.className = var2;
      this.zaqx = var3;
   }

   zal(String var1, Map<String, FastJsonResponse.Field<?, ?>> var2) {
      this.versionCode = 1;
      this.className = var1;
      ArrayList var5;
      if(var2 == null) {
         var5 = null;
      } else {
         ArrayList var3 = new ArrayList();
         Iterator var4 = var2.keySet().iterator();

         while(true) {
            var5 = var3;
            if(!var4.hasNext()) {
               break;
            }

            var1 = (String)var4.next();
            var3.add(new zam(var1, (FastJsonResponse.Field)var2.get(var1)));
         }
      }

      this.zaqx = var5;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.versionCode);
      SafeParcelWriter.writeString(var1, 2, this.className, false);
      SafeParcelWriter.writeTypedList(var1, 3, this.zaqx, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
