package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.SparseArray;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse;
import com.google.android.gms.common.server.response.zak;
import com.google.android.gms.common.server.response.zap;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

@KeepForSdk
@SafeParcelable.Class(
   creator = "SafeParcelResponseCreator"
)
@VisibleForTesting
public class SafeParcelResponse extends FastSafeParcelableJsonResponse {

   @KeepForSdk
   public static final Creator<SafeParcelResponse> CREATOR = new zap();
   private final String mClassName;
   @SafeParcelable.VersionField(
      getter = "getVersionCode",
      id = 1
   )
   private final int zale;
   @SafeParcelable.Field(
      getter = "getFieldMappingDictionary",
      id = 3
   )
   private final zak zapy;
   @SafeParcelable.Field(
      getter = "getParcel",
      id = 2
   )
   private final Parcel zara;
   private final int zarb;
   private int zarc;
   private int zard;


   @SafeParcelable.Constructor
   SafeParcelResponse(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) Parcel var2, 
      @SafeParcelable.Param(
         id = 3
      ) zak var3) {
      this.zale = var1;
      this.zara = (Parcel)Preconditions.checkNotNull(var2);
      this.zarb = 2;
      this.zapy = var3;
      if(this.zapy == null) {
         this.mClassName = null;
      } else {
         this.mClassName = this.zapy.zact();
      }

      this.zarc = 2;
   }

   private SafeParcelResponse(SafeParcelable var1, zak var2, String var3) {
      this.zale = 1;
      this.zara = Parcel.obtain();
      var1.writeToParcel(this.zara, 0);
      this.zarb = 1;
      this.zapy = (zak)Preconditions.checkNotNull(var2);
      this.mClassName = (String)Preconditions.checkNotNull(var3);
      this.zarc = 2;
   }

   public SafeParcelResponse(zak var1, String var2) {
      this.zale = 1;
      this.zara = Parcel.obtain();
      this.zarb = 0;
      this.zapy = (zak)Preconditions.checkNotNull(var1);
      this.mClassName = (String)Preconditions.checkNotNull(var2);
      this.zarc = 0;
   }

   @KeepForSdk
   public static <T extends FastJsonResponse & SafeParcelable> SafeParcelResponse from(T var0) {
      String var1 = var0.getClass().getCanonicalName();
      zak var2 = new zak(var0.getClass());
      zaa(var2, var0);
      var2.zacs();
      var2.zacr();
      return new SafeParcelResponse((SafeParcelable)var0, var2, var1);
   }

   private static void zaa(zak var0, FastJsonResponse var1) {
      Class var3 = var1.getClass();
      if(!var0.zaa(var3)) {
         Map var2 = var1.getFieldMappings();
         var0.zaa(var3, var2);
         Iterator var9 = var2.keySet().iterator();

         while(var9.hasNext()) {
            FastJsonResponse.Field var8 = (FastJsonResponse.Field)var2.get((String)var9.next());
            Class var4 = var8.zapw;
            if(var4 != null) {
               String var7;
               try {
                  zaa(var0, (FastJsonResponse)var4.newInstance());
               } catch (InstantiationException var5) {
                  var7 = String.valueOf(var8.zapw.getCanonicalName());
                  if(var7.length() != 0) {
                     var7 = "Could not instantiate an object of type ".concat(var7);
                  } else {
                     var7 = new String("Could not instantiate an object of type ");
                  }

                  throw new IllegalStateException(var7, var5);
               } catch (IllegalAccessException var6) {
                  var7 = String.valueOf(var8.zapw.getCanonicalName());
                  if(var7.length() != 0) {
                     var7 = "Could not access object of type ".concat(var7);
                  } else {
                     var7 = new String("Could not access object of type ");
                  }

                  throw new IllegalStateException(var7, var6);
               }
            }
         }
      }

   }

   private static void zaa(StringBuilder var0, int var1, Object var2) {
      switch(var1) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
         var0.append(var2);
         return;
      case 7:
         var0.append("\"");
         var0.append(JsonUtils.escapeString(var2.toString()));
         var0.append("\"");
         return;
      case 8:
         var0.append("\"");
         var0.append(Base64Utils.encode((byte[])var2));
         var0.append("\"");
         return;
      case 9:
         var0.append("\"");
         var0.append(Base64Utils.encodeUrlSafe((byte[])var2));
         var0.append("\"");
         return;
      case 10:
         MapUtils.writeStringMapToJson(var0, (HashMap)var2);
         return;
      case 11:
         throw new IllegalArgumentException("Method does not accept concrete type.");
      default:
         var0 = new StringBuilder(26);
         var0.append("Unknown type = ");
         var0.append(var1);
         throw new IllegalArgumentException(var0.toString());
      }
   }

   private final void zaa(StringBuilder var1, Map<String, FastJsonResponse.Field<?, ?>> var2, Parcel var3) {
      SparseArray var7 = new SparseArray();
      Iterator var12 = var2.entrySet().iterator();

      while(var12.hasNext()) {
         Entry var8 = (Entry)var12.next();
         var7.put(((FastJsonResponse.Field)var8.getValue()).getSafeParcelableFieldId(), var8);
      }

      var1.append('{');
      int var5 = SafeParcelReader.validateObjectHeader(var3);
      boolean var4 = false;

      while(true) {
         int var6;
         Entry var13;
         do {
            if(var3.dataPosition() >= var5) {
               if(var3.dataPosition() != var5) {
                  var1 = new StringBuilder(37);
                  var1.append("Overread allowed size end=");
                  var1.append(var5);
                  throw new SafeParcelReader.ParseException(var1.toString(), var3);
               }

               var1.append('}');
               return;
            }

            var6 = SafeParcelReader.readHeader(var3);
            var13 = (Entry)var7.get(SafeParcelReader.getFieldId(var6));
         } while(var13 == null);

         if(var4) {
            var1.append(",");
         }

         String var19 = (String)var13.getKey();
         FastJsonResponse.Field var14 = (FastJsonResponse.Field)var13.getValue();
         var1.append("\"");
         var1.append(var19);
         var1.append("\":");
         int var17;
         if(var14.zacn()) {
            switch(var14.zaps) {
            case 0:
               this.zab(var1, var14, zab(var14, Integer.valueOf(SafeParcelReader.readInt(var3, var6))));
               break;
            case 1:
               this.zab(var1, var14, zab(var14, SafeParcelReader.createBigInteger(var3, var6)));
               break;
            case 2:
               this.zab(var1, var14, zab(var14, Long.valueOf(SafeParcelReader.readLong(var3, var6))));
               break;
            case 3:
               this.zab(var1, var14, zab(var14, Float.valueOf(SafeParcelReader.readFloat(var3, var6))));
               break;
            case 4:
               this.zab(var1, var14, zab(var14, Double.valueOf(SafeParcelReader.readDouble(var3, var6))));
               break;
            case 5:
               this.zab(var1, var14, zab(var14, SafeParcelReader.createBigDecimal(var3, var6)));
               break;
            case 6:
               this.zab(var1, var14, zab(var14, Boolean.valueOf(SafeParcelReader.readBoolean(var3, var6))));
               break;
            case 7:
               this.zab(var1, var14, zab(var14, SafeParcelReader.createString(var3, var6)));
               break;
            case 8:
            case 9:
               this.zab(var1, var14, zab(var14, SafeParcelReader.createByteArray(var3, var6)));
               break;
            case 10:
               Bundle var20 = SafeParcelReader.createBundle(var3, var6);
               HashMap var9 = new HashMap();
               Iterator var10 = var20.keySet().iterator();

               while(var10.hasNext()) {
                  String var11 = (String)var10.next();
                  var9.put(var11, var20.getString(var11));
               }

               this.zab(var1, var14, zab(var14, var9));
               break;
            case 11:
               throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
               var17 = var14.zaps;
               var1 = new StringBuilder(36);
               var1.append("Unknown field out type = ");
               var1.append(var17);
               throw new IllegalArgumentException(var1.toString());
            }
         } else if(var14.zapt) {
            var1.append("[");
            label96:
            switch(var14.zaps) {
            case 0:
               ArrayUtils.writeArray(var1, SafeParcelReader.createIntArray(var3, var6));
               break;
            case 1:
               ArrayUtils.writeArray(var1, (Object[])SafeParcelReader.createBigIntegerArray(var3, var6));
               break;
            case 2:
               ArrayUtils.writeArray(var1, SafeParcelReader.createLongArray(var3, var6));
               break;
            case 3:
               ArrayUtils.writeArray(var1, SafeParcelReader.createFloatArray(var3, var6));
               break;
            case 4:
               ArrayUtils.writeArray(var1, SafeParcelReader.createDoubleArray(var3, var6));
               break;
            case 5:
               ArrayUtils.writeArray(var1, (Object[])SafeParcelReader.createBigDecimalArray(var3, var6));
               break;
            case 6:
               ArrayUtils.writeArray(var1, SafeParcelReader.createBooleanArray(var3, var6));
               break;
            case 7:
               ArrayUtils.writeStringArray(var1, SafeParcelReader.createStringArray(var3, var6));
               break;
            case 8:
            case 9:
            case 10:
               throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
            case 11:
               Parcel[] var21 = SafeParcelReader.createParcelArray(var3, var6);
               var6 = var21.length;
               var17 = 0;

               while(true) {
                  if(var17 >= var6) {
                     break label96;
                  }

                  if(var17 > 0) {
                     var1.append(",");
                  }

                  var21[var17].setDataPosition(0);
                  this.zaa(var1, var14.zacq(), var21[var17]);
                  ++var17;
               }
            default:
               throw new IllegalStateException("Unknown field type out.");
            }

            var1.append("]");
         } else {
            byte[] var16;
            switch(var14.zaps) {
            case 0:
               var1.append(SafeParcelReader.readInt(var3, var6));
               break;
            case 1:
               var1.append(SafeParcelReader.createBigInteger(var3, var6));
               break;
            case 2:
               var1.append(SafeParcelReader.readLong(var3, var6));
               break;
            case 3:
               var1.append(SafeParcelReader.readFloat(var3, var6));
               break;
            case 4:
               var1.append(SafeParcelReader.readDouble(var3, var6));
               break;
            case 5:
               var1.append(SafeParcelReader.createBigDecimal(var3, var6));
               break;
            case 6:
               var1.append(SafeParcelReader.readBoolean(var3, var6));
               break;
            case 7:
               String var18 = SafeParcelReader.createString(var3, var6);
               var1.append("\"");
               var1.append(JsonUtils.escapeString(var18));
               var1.append("\"");
               break;
            case 8:
               var16 = SafeParcelReader.createByteArray(var3, var6);
               var1.append("\"");
               var1.append(Base64Utils.encode(var16));
               var1.append("\"");
               break;
            case 9:
               var16 = SafeParcelReader.createByteArray(var3, var6);
               var1.append("\"");
               var1.append(Base64Utils.encodeUrlSafe(var16));
               var1.append("\"");
               break;
            case 10:
               Bundle var15 = SafeParcelReader.createBundle(var3, var6);
               Set var24 = var15.keySet();
               var24.size();
               var1.append("{");
               Iterator var25 = var24.iterator();

               for(var4 = true; var25.hasNext(); var4 = false) {
                  String var23 = (String)var25.next();
                  if(!var4) {
                     var1.append(",");
                  }

                  var1.append("\"");
                  var1.append(var23);
                  var1.append("\"");
                  var1.append(":");
                  var1.append("\"");
                  var1.append(JsonUtils.escapeString(var15.getString(var23)));
                  var1.append("\"");
               }

               var1.append("}");
               break;
            case 11:
               Parcel var22 = SafeParcelReader.createParcel(var3, var6);
               var22.setDataPosition(0);
               this.zaa(var1, var14.zacq(), var22);
               break;
            default:
               throw new IllegalStateException("Unknown field type out");
            }
         }

         var4 = true;
      }
   }

   private final void zab(FastJsonResponse.Field<?, ?> var1) {
      boolean var2;
      if(var1.zapv != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      if(!var2) {
         throw new IllegalStateException("Field does not have a valid safe parcelable field id.");
      } else if(this.zara == null) {
         throw new IllegalStateException("Internal Parcel object is null.");
      } else {
         switch(this.zarc) {
         case 0:
            this.zard = SafeParcelWriter.beginObjectHeader(this.zara);
            this.zarc = 1;
            return;
         case 1:
            return;
         case 2:
            throw new IllegalStateException("Attempted to parse JSON with a SafeParcelResponse object that is already filled with data.");
         default:
            throw new IllegalStateException("Unknown parse state in SafeParcelResponse.");
         }
      }
   }

   private final void zab(StringBuilder var1, FastJsonResponse.Field<?, ?> var2, Object var3) {
      if(var2.zapr) {
         ArrayList var6 = (ArrayList)var3;
         var1.append("[");
         int var5 = var6.size();

         for(int var4 = 0; var4 < var5; ++var4) {
            if(var4 != 0) {
               var1.append(",");
            }

            zaa(var1, var2.zapq, var6.get(var4));
         }

         var1.append("]");
      } else {
         zaa(var1, var2.zapq, var3);
      }
   }

   private final Parcel zacu() {
      switch(this.zarc) {
      case 0:
         this.zard = SafeParcelWriter.beginObjectHeader(this.zara);
      case 1:
         SafeParcelWriter.finishObjectHeader(this.zara, this.zard);
         this.zarc = 2;
      default:
         return this.zara;
      }
   }

   public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<T> var3) {
      this.zab(var1);
      ArrayList var7 = new ArrayList();
      var3.size();
      var3 = (ArrayList)var3;
      int var5 = var3.size();
      int var4 = 0;

      while(var4 < var5) {
         Object var6 = var3.get(var4);
         ++var4;
         var7.add(((SafeParcelResponse)((FastJsonResponse)var6)).zacu());
      }

      SafeParcelWriter.writeParcelList(this.zara, var1.getSafeParcelableFieldId(), var7, true);
   }

   public <T extends FastJsonResponse> void addConcreteTypeInternal(FastJsonResponse.Field<?, ?> var1, String var2, T var3) {
      this.zab(var1);
      Parcel var4 = ((SafeParcelResponse)var3).zacu();
      SafeParcelWriter.writeParcel(this.zara, var1.getSafeParcelableFieldId(), var4, true);
   }

   public Map<String, FastJsonResponse.Field<?, ?>> getFieldMappings() {
      return this.zapy == null?null:this.zapy.zai(this.mClassName);
   }

   public Object getValueObject(String var1) {
      throw new UnsupportedOperationException("Converting to JSON does not require this method.");
   }

   public boolean isPrimitiveFieldSet(String var1) {
      throw new UnsupportedOperationException("Converting to JSON does not require this method.");
   }

   protected void setBooleanInternal(FastJsonResponse.Field<?, ?> var1, String var2, boolean var3) {
      this.zab(var1);
      SafeParcelWriter.writeBoolean(this.zara, var1.getSafeParcelableFieldId(), var3);
   }

   protected void setDecodedBytesInternal(FastJsonResponse.Field<?, ?> var1, String var2, byte[] var3) {
      this.zab(var1);
      SafeParcelWriter.writeByteArray(this.zara, var1.getSafeParcelableFieldId(), var3, true);
   }

   protected void setIntegerInternal(FastJsonResponse.Field<?, ?> var1, String var2, int var3) {
      this.zab(var1);
      SafeParcelWriter.writeInt(this.zara, var1.getSafeParcelableFieldId(), var3);
   }

   protected void setLongInternal(FastJsonResponse.Field<?, ?> var1, String var2, long var3) {
      this.zab(var1);
      SafeParcelWriter.writeLong(this.zara, var1.getSafeParcelableFieldId(), var3);
   }

   protected void setStringInternal(FastJsonResponse.Field<?, ?> var1, String var2, String var3) {
      this.zab(var1);
      SafeParcelWriter.writeString(this.zara, var1.getSafeParcelableFieldId(), var3, true);
   }

   protected void setStringsInternal(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<String> var3) {
      this.zab(var1);
      int var5 = var3.size();
      String[] var6 = new String[var5];

      for(int var4 = 0; var4 < var5; ++var4) {
         var6[var4] = (String)var3.get(var4);
      }

      SafeParcelWriter.writeStringArray(this.zara, var1.getSafeParcelableFieldId(), var6, true);
   }

   public String toString() {
      Preconditions.checkNotNull(this.zapy, "Cannot convert to JSON on client side.");
      Parcel var1 = this.zacu();
      var1.setDataPosition(0);
      StringBuilder var2 = new StringBuilder(100);
      this.zaa(var2, this.zapy.zai(this.mClassName), var1);
      return var2.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zale);
      SafeParcelWriter.writeParcel(var1, 2, this.zacu(), false);
      zak var4;
      switch(this.zarb) {
      case 0:
         var4 = null;
         break;
      case 1:
         var4 = this.zapy;
         break;
      case 2:
         var4 = this.zapy;
         break;
      default:
         var2 = this.zarb;
         StringBuilder var5 = new StringBuilder(34);
         var5.append("Invalid creation type: ");
         var5.append(var2);
         throw new IllegalStateException(var5.toString());
      }

      SafeParcelWriter.writeParcelable(var1, 3, var4, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   protected final void zaa(FastJsonResponse.Field<?, ?> var1, String var2, double var3) {
      this.zab(var1);
      SafeParcelWriter.writeDouble(this.zara, var1.getSafeParcelableFieldId(), var3);
   }

   protected final void zaa(FastJsonResponse.Field<?, ?> var1, String var2, float var3) {
      this.zab(var1);
      SafeParcelWriter.writeFloat(this.zara, var1.getSafeParcelableFieldId(), var3);
   }

   protected final void zaa(FastJsonResponse.Field<?, ?> var1, String var2, BigDecimal var3) {
      this.zab(var1);
      SafeParcelWriter.writeBigDecimal(this.zara, var1.getSafeParcelableFieldId(), var3, true);
   }

   protected final void zaa(FastJsonResponse.Field<?, ?> var1, String var2, BigInteger var3) {
      this.zab(var1);
      SafeParcelWriter.writeBigInteger(this.zara, var1.getSafeParcelableFieldId(), var3, true);
   }

   protected final void zaa(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Integer> var3) {
      this.zab(var1);
      int var5 = var3.size();
      int[] var6 = new int[var5];

      for(int var4 = 0; var4 < var5; ++var4) {
         var6[var4] = ((Integer)var3.get(var4)).intValue();
      }

      SafeParcelWriter.writeIntArray(this.zara, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected final void zaa(FastJsonResponse.Field<?, ?> var1, String var2, Map<String, String> var3) {
      this.zab(var1);
      Bundle var6 = new Bundle();
      Iterator var4 = var3.keySet().iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         var6.putString(var5, (String)var3.get(var5));
      }

      SafeParcelWriter.writeBundle(this.zara, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected final void zab(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<BigInteger> var3) {
      this.zab(var1);
      int var5 = var3.size();
      BigInteger[] var6 = new BigInteger[var5];

      for(int var4 = 0; var4 < var5; ++var4) {
         var6[var4] = (BigInteger)var3.get(var4);
      }

      SafeParcelWriter.writeBigIntegerArray(this.zara, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected final void zac(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Long> var3) {
      this.zab(var1);
      int var5 = var3.size();
      long[] var6 = new long[var5];

      for(int var4 = 0; var4 < var5; ++var4) {
         var6[var4] = ((Long)var3.get(var4)).longValue();
      }

      SafeParcelWriter.writeLongArray(this.zara, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected final void zad(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Float> var3) {
      this.zab(var1);
      int var5 = var3.size();
      float[] var6 = new float[var5];

      for(int var4 = 0; var4 < var5; ++var4) {
         var6[var4] = ((Float)var3.get(var4)).floatValue();
      }

      SafeParcelWriter.writeFloatArray(this.zara, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected final void zae(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Double> var3) {
      this.zab(var1);
      int var5 = var3.size();
      double[] var6 = new double[var5];

      for(int var4 = 0; var4 < var5; ++var4) {
         var6[var4] = ((Double)var3.get(var4)).doubleValue();
      }

      SafeParcelWriter.writeDoubleArray(this.zara, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected final void zaf(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<BigDecimal> var3) {
      this.zab(var1);
      int var5 = var3.size();
      BigDecimal[] var6 = new BigDecimal[var5];

      for(int var4 = 0; var4 < var5; ++var4) {
         var6[var4] = (BigDecimal)var3.get(var4);
      }

      SafeParcelWriter.writeBigDecimalArray(this.zara, var1.getSafeParcelableFieldId(), var6, true);
   }

   protected final void zag(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Boolean> var3) {
      this.zab(var1);
      int var5 = var3.size();
      boolean[] var6 = new boolean[var5];

      for(int var4 = 0; var4 < var5; ++var4) {
         var6[var4] = ((Boolean)var3.get(var4)).booleanValue();
      }

      SafeParcelWriter.writeBooleanArray(this.zara, var1.getSafeParcelableFieldId(), var6, true);
   }
}
