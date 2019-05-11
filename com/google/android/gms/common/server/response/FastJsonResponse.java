package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.SafeParcelResponse;
import com.google.android.gms.common.server.response.zai;
import com.google.android.gms.common.server.response.zak;
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

@KeepForSdk
@ShowFirstParty
public abstract class FastJsonResponse {

   private final <I extends Object, O extends Object> void zaa(FastJsonResponse.Field<I, O> var1, I var2) {
      String var4 = var1.zapu;
      var2 = var1.convert(var2);
      switch(var1.zaps) {
      case 0:
         if(zaa(var4, var2)) {
            this.setIntegerInternal(var1, var4, ((Integer)var2).intValue());
            return;
         }
         break;
      case 1:
         this.zaa(var1, var4, (BigInteger)var2);
         return;
      case 2:
         if(zaa(var4, var2)) {
            this.setLongInternal(var1, var4, ((Long)var2).longValue());
            return;
         }
         break;
      case 3:
      default:
         int var3 = var1.zaps;
         StringBuilder var5 = new StringBuilder(44);
         var5.append("Unsupported type for conversion: ");
         var5.append(var3);
         throw new IllegalStateException(var5.toString());
      case 4:
         if(zaa(var4, var2)) {
            this.zaa(var1, var4, ((Double)var2).doubleValue());
            return;
         }
         break;
      case 5:
         this.zaa(var1, var4, (BigDecimal)var2);
         return;
      case 6:
         if(zaa(var4, var2)) {
            this.setBooleanInternal(var1, var4, ((Boolean)var2).booleanValue());
            return;
         }
         break;
      case 7:
         this.setStringInternal(var1, var4, (String)var2);
         return;
      case 8:
      case 9:
         if(zaa(var4, var2)) {
            this.setDecodedBytesInternal(var1, var4, (byte[])var2);
            return;
         }
      }

   }

   private static void zaa(StringBuilder var0, FastJsonResponse.Field var1, Object var2) {
      if(var1.zapq == 11) {
         var0.append(((FastJsonResponse)var1.zapw.cast(var2)).toString());
      } else if(var1.zapq == 7) {
         var0.append("\"");
         var0.append(JsonUtils.escapeString((String)var2));
         var0.append("\"");
      } else {
         var0.append(var2);
      }
   }

   private static <O extends Object> boolean zaa(String var0, O var1) {
      if(var1 == null) {
         if(Log.isLoggable("FastJsonResponse", 6)) {
            StringBuilder var2 = new StringBuilder(String.valueOf(var0).length() + 58);
            var2.append("Output field (");
            var2.append(var0);
            var2.append(") has a null value, but expected a primitive");
            Log.e("FastJsonResponse", var2.toString());
         }

         return false;
      } else {
         return true;
      }
   }

   protected static <O extends Object, I extends Object> I zab(FastJsonResponse.Field<I, O> var0, Object var1) {
      return var0.zapz != null?var0.convertBack(var1):var1;
   }

   @KeepForSdk
   public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<T> var3) {
      throw new UnsupportedOperationException("Concrete type array not supported");
   }

   @KeepForSdk
   public <T extends FastJsonResponse> void addConcreteTypeInternal(FastJsonResponse.Field<?, ?> var1, String var2, T var3) {
      throw new UnsupportedOperationException("Concrete type not supported");
   }

   @KeepForSdk
   public abstract Map<String, FastJsonResponse.Field<?, ?>> getFieldMappings();

   @KeepForSdk
   protected Object getFieldValue(FastJsonResponse.Field var1) {
      String var4 = var1.zapu;
      if(var1.zapw != null) {
         boolean var3;
         if(this.getValueObject(var1.zapu) == null) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkState(var3, "Concrete field shouldn\'t be value object: %s", new Object[]{var1.zapu});
         var3 = var1.zapt;

         try {
            char var2 = Character.toUpperCase(var4.charAt(0));
            String var6 = var4.substring(1);
            StringBuilder var8 = new StringBuilder(String.valueOf(var6).length() + 4);
            var8.append("get");
            var8.append(var2);
            var8.append(var6);
            var6 = var8.toString();
            Object var7 = this.getClass().getMethod(var6, new Class[0]).invoke(this, new Object[0]);
            return var7;
         } catch (Exception var5) {
            throw new RuntimeException(var5);
         }
      } else {
         return this.getValueObject(var1.zapu);
      }
   }

   @KeepForSdk
   protected abstract Object getValueObject(String var1);

   @KeepForSdk
   protected boolean isFieldSet(FastJsonResponse.Field var1) {
      if(var1.zaps == 11) {
         String var2;
         if(var1.zapt) {
            var2 = var1.zapu;
            throw new UnsupportedOperationException("Concrete type arrays not supported");
         } else {
            var2 = var1.zapu;
            throw new UnsupportedOperationException("Concrete types not supported");
         }
      } else {
         return this.isPrimitiveFieldSet(var1.zapu);
      }
   }

   @KeepForSdk
   protected abstract boolean isPrimitiveFieldSet(String var1);

   @KeepForSdk
   protected void setBooleanInternal(FastJsonResponse.Field<?, ?> var1, String var2, boolean var3) {
      throw new UnsupportedOperationException("Boolean not supported");
   }

   @KeepForSdk
   protected void setDecodedBytesInternal(FastJsonResponse.Field<?, ?> var1, String var2, byte[] var3) {
      throw new UnsupportedOperationException("byte[] not supported");
   }

   @KeepForSdk
   protected void setIntegerInternal(FastJsonResponse.Field<?, ?> var1, String var2, int var3) {
      throw new UnsupportedOperationException("Integer not supported");
   }

   @KeepForSdk
   protected void setLongInternal(FastJsonResponse.Field<?, ?> var1, String var2, long var3) {
      throw new UnsupportedOperationException("Long not supported");
   }

   @KeepForSdk
   protected void setStringInternal(FastJsonResponse.Field<?, ?> var1, String var2, String var3) {
      throw new UnsupportedOperationException("String not supported");
   }

   @KeepForSdk
   protected void setStringsInternal(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<String> var3) {
      throw new UnsupportedOperationException("String list not supported");
   }

   @KeepForSdk
   public String toString() {
      Map var3 = this.getFieldMappings();
      StringBuilder var4 = new StringBuilder(100);
      Iterator var5 = var3.keySet().iterator();

      while(var5.hasNext()) {
         String var7 = (String)var5.next();
         FastJsonResponse.Field var6 = (FastJsonResponse.Field)var3.get(var7);
         if(this.isFieldSet(var6)) {
            Object var8 = zab(var6, this.getFieldValue(var6));
            if(var4.length() == 0) {
               var4.append("{");
            } else {
               var4.append(",");
            }

            var4.append("\"");
            var4.append(var7);
            var4.append("\":");
            if(var8 == null) {
               var4.append("null");
            } else {
               int var1;
               int var2;
               ArrayList var9;
               switch(var6.zaps) {
               case 8:
                  var4.append("\"");
                  var4.append(Base64Utils.encode((byte[])var8));
                  var4.append("\"");
                  continue;
               case 9:
                  var4.append("\"");
                  var4.append(Base64Utils.encodeUrlSafe((byte[])var8));
                  var4.append("\"");
                  continue;
               case 10:
                  MapUtils.writeStringMapToJson(var4, (HashMap)var8);
                  continue;
               default:
                  if(!var6.zapr) {
                     zaa(var4, var6, var8);
                     continue;
                  }

                  var9 = (ArrayList)var8;
                  var4.append("[");
                  var1 = 0;
                  var2 = var9.size();
               }

               for(; var1 < var2; ++var1) {
                  if(var1 > 0) {
                     var4.append(",");
                  }

                  var8 = var9.get(var1);
                  if(var8 != null) {
                     zaa(var4, var6, var8);
                  }
               }

               var4.append("]");
            }
         }
      }

      if(var4.length() > 0) {
         var4.append("}");
      } else {
         var4.append("{}");
      }

      return var4.toString();
   }

   public final <O extends Object> void zaa(FastJsonResponse.Field<Double, O> var1, double var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)Double.valueOf(var2));
      } else {
         this.zaa(var1, var1.zapu, var2);
      }
   }

   public final <O extends Object> void zaa(FastJsonResponse.Field<Float, O> var1, float var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)Float.valueOf(var2));
      } else {
         this.zaa(var1, var1.zapu, var2);
      }
   }

   public final <O extends Object> void zaa(FastJsonResponse.Field<Integer, O> var1, int var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)Integer.valueOf(var2));
      } else {
         this.setIntegerInternal(var1, var1.zapu, var2);
      }
   }

   public final <O extends Object> void zaa(FastJsonResponse.Field<Long, O> var1, long var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)Long.valueOf(var2));
      } else {
         this.setLongInternal(var1, var1.zapu, var2);
      }
   }

   public final <O extends Object> void zaa(FastJsonResponse.Field<String, O> var1, String var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)var2);
      } else {
         this.setStringInternal(var1, var1.zapu, var2);
      }
   }

   protected void zaa(FastJsonResponse.Field<?, ?> var1, String var2, double var3) {
      throw new UnsupportedOperationException("Double not supported");
   }

   protected void zaa(FastJsonResponse.Field<?, ?> var1, String var2, float var3) {
      throw new UnsupportedOperationException("Float not supported");
   }

   protected void zaa(FastJsonResponse.Field<?, ?> var1, String var2, BigDecimal var3) {
      throw new UnsupportedOperationException("BigDecimal not supported");
   }

   protected void zaa(FastJsonResponse.Field<?, ?> var1, String var2, BigInteger var3) {
      throw new UnsupportedOperationException("BigInteger not supported");
   }

   protected void zaa(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Integer> var3) {
      throw new UnsupportedOperationException("Integer list not supported");
   }

   protected void zaa(FastJsonResponse.Field<?, ?> var1, String var2, Map<String, String> var3) {
      throw new UnsupportedOperationException("String map not supported");
   }

   public final <O extends Object> void zaa(FastJsonResponse.Field<BigDecimal, O> var1, BigDecimal var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)var2);
      } else {
         this.zaa(var1, var1.zapu, var2);
      }
   }

   public final <O extends Object> void zaa(FastJsonResponse.Field<BigInteger, O> var1, BigInteger var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)var2);
      } else {
         this.zaa(var1, var1.zapu, var2);
      }
   }

   public final <O extends Object> void zaa(FastJsonResponse.Field<ArrayList<Integer>, O> var1, ArrayList<Integer> var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)var2);
      } else {
         this.zaa(var1, var1.zapu, var2);
      }
   }

   public final <O extends Object> void zaa(FastJsonResponse.Field<Map<String, String>, O> var1, Map<String, String> var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)var2);
      } else {
         this.zaa(var1, var1.zapu, var2);
      }
   }

   public final <O extends Object> void zaa(FastJsonResponse.Field<Boolean, O> var1, boolean var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)Boolean.valueOf(var2));
      } else {
         this.setBooleanInternal(var1, var1.zapu, var2);
      }
   }

   public final <O extends Object> void zaa(FastJsonResponse.Field<byte[], O> var1, byte[] var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)var2);
      } else {
         this.setDecodedBytesInternal(var1, var1.zapu, var2);
      }
   }

   protected void zab(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<BigInteger> var3) {
      throw new UnsupportedOperationException("BigInteger list not supported");
   }

   public final <O extends Object> void zab(FastJsonResponse.Field<ArrayList<BigInteger>, O> var1, ArrayList<BigInteger> var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)var2);
      } else {
         this.zab(var1, var1.zapu, var2);
      }
   }

   protected void zac(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Long> var3) {
      throw new UnsupportedOperationException("Long list not supported");
   }

   public final <O extends Object> void zac(FastJsonResponse.Field<ArrayList<Long>, O> var1, ArrayList<Long> var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)var2);
      } else {
         this.zac(var1, var1.zapu, var2);
      }
   }

   protected void zad(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Float> var3) {
      throw new UnsupportedOperationException("Float list not supported");
   }

   public final <O extends Object> void zad(FastJsonResponse.Field<ArrayList<Float>, O> var1, ArrayList<Float> var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)var2);
      } else {
         this.zad(var1, var1.zapu, var2);
      }
   }

   protected void zae(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Double> var3) {
      throw new UnsupportedOperationException("Double list not supported");
   }

   public final <O extends Object> void zae(FastJsonResponse.Field<ArrayList<Double>, O> var1, ArrayList<Double> var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)var2);
      } else {
         this.zae(var1, var1.zapu, var2);
      }
   }

   protected void zaf(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<BigDecimal> var3) {
      throw new UnsupportedOperationException("BigDecimal list not supported");
   }

   public final <O extends Object> void zaf(FastJsonResponse.Field<ArrayList<BigDecimal>, O> var1, ArrayList<BigDecimal> var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)var2);
      } else {
         this.zaf(var1, var1.zapu, var2);
      }
   }

   protected void zag(FastJsonResponse.Field<?, ?> var1, String var2, ArrayList<Boolean> var3) {
      throw new UnsupportedOperationException("Boolean list not supported");
   }

   public final <O extends Object> void zag(FastJsonResponse.Field<ArrayList<Boolean>, O> var1, ArrayList<Boolean> var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)var2);
      } else {
         this.zag(var1, var1.zapu, var2);
      }
   }

   public final <O extends Object> void zah(FastJsonResponse.Field<ArrayList<String>, O> var1, ArrayList<String> var2) {
      if(var1.zapz != null) {
         this.zaa(var1, (Object)var2);
      } else {
         this.setStringsInternal(var1, var1.zapu, var2);
      }
   }

   @KeepForSdk
   @ShowFirstParty
   @SafeParcelable.Class(
      creator = "FieldCreator"
   )
   @VisibleForTesting
   public static class Field<I extends Object, O extends Object> extends AbstractSafeParcelable {

      public static final zai CREATOR = new zai();
      @SafeParcelable.VersionField(
         getter = "getVersionCode",
         id = 1
      )
      private final int zale;
      @SafeParcelable.Field(
         getter = "getTypeIn",
         id = 2
      )
      protected final int zapq;
      @SafeParcelable.Field(
         getter = "isTypeInArray",
         id = 3
      )
      protected final boolean zapr;
      @SafeParcelable.Field(
         getter = "getTypeOut",
         id = 4
      )
      protected final int zaps;
      @SafeParcelable.Field(
         getter = "isTypeOutArray",
         id = 5
      )
      protected final boolean zapt;
      @SafeParcelable.Field(
         getter = "getOutputFieldName",
         id = 6
      )
      protected final String zapu;
      @SafeParcelable.Field(
         getter = "getSafeParcelableFieldId",
         id = 7
      )
      protected final int zapv;
      protected final Class<? extends FastJsonResponse> zapw;
      @SafeParcelable.Field(
         getter = "getConcreteTypeName",
         id = 8
      )
      private final String zapx;
      private zak zapy;
      @SafeParcelable.Field(
         getter = "getWrappedConverter",
         id = 9,
         type = "com.google.android.gms.common.server.converter.ConverterWrapper"
      )
      private FastJsonResponse.FieldConverter<I, O> zapz;


      @SafeParcelable.Constructor
      Field(
         @SafeParcelable.Param(
            id = 1
         ) int var1, 
         @SafeParcelable.Param(
            id = 2
         ) int var2, 
         @SafeParcelable.Param(
            id = 3
         ) boolean var3, 
         @SafeParcelable.Param(
            id = 4
         ) int var4, 
         @SafeParcelable.Param(
            id = 5
         ) boolean var5, 
         @SafeParcelable.Param(
            id = 6
         ) String var6, 
         @SafeParcelable.Param(
            id = 7
         ) int var7, 
         @SafeParcelable.Param(
            id = 8
         ) String var8, 
         @SafeParcelable.Param(
            id = 9
         ) com.google.android.gms.common.server.converter.zaa var9) {
         this.zale = var1;
         this.zapq = var2;
         this.zapr = var3;
         this.zaps = var4;
         this.zapt = var5;
         this.zapu = var6;
         this.zapv = var7;
         if(var8 == null) {
            this.zapw = null;
            this.zapx = null;
         } else {
            this.zapw = SafeParcelResponse.class;
            this.zapx = var8;
         }

         if(var9 == null) {
            this.zapz = null;
         } else {
            this.zapz = var9.zaci();
         }
      }

      private Field(int var1, boolean var2, int var3, boolean var4, String var5, int var6, Class<? extends FastJsonResponse> var7, FastJsonResponse.FieldConverter<I, O> var8) {
         this.zale = 1;
         this.zapq = var1;
         this.zapr = var2;
         this.zaps = var3;
         this.zapt = var4;
         this.zapu = var5;
         this.zapv = var6;
         this.zapw = var7;
         if(var7 == null) {
            this.zapx = null;
         } else {
            this.zapx = var7.getCanonicalName();
         }

         this.zapz = var8;
      }

      @KeepForSdk
      @VisibleForTesting
      public static FastJsonResponse.Field<byte[], byte[]> forBase64(String var0, int var1) {
         return new FastJsonResponse.Field(8, false, 8, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      @KeepForSdk
      public static FastJsonResponse.Field<Boolean, Boolean> forBoolean(String var0, int var1) {
         return new FastJsonResponse.Field(6, false, 6, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      @KeepForSdk
      public static <T extends FastJsonResponse> FastJsonResponse.Field<T, T> forConcreteType(String var0, int var1, Class<T> var2) {
         return new FastJsonResponse.Field(11, false, 11, false, var0, var1, var2, (FastJsonResponse.FieldConverter)null);
      }

      @KeepForSdk
      public static <T extends FastJsonResponse> FastJsonResponse.Field<ArrayList<T>, ArrayList<T>> forConcreteTypeArray(String var0, int var1, Class<T> var2) {
         return new FastJsonResponse.Field(11, true, 11, true, var0, var1, var2, (FastJsonResponse.FieldConverter)null);
      }

      @KeepForSdk
      public static FastJsonResponse.Field<Double, Double> forDouble(String var0, int var1) {
         return new FastJsonResponse.Field(4, false, 4, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      @KeepForSdk
      public static FastJsonResponse.Field<Float, Float> forFloat(String var0, int var1) {
         return new FastJsonResponse.Field(3, false, 3, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      @KeepForSdk
      @VisibleForTesting
      public static FastJsonResponse.Field<Integer, Integer> forInteger(String var0, int var1) {
         return new FastJsonResponse.Field(0, false, 0, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      @KeepForSdk
      public static FastJsonResponse.Field<Long, Long> forLong(String var0, int var1) {
         return new FastJsonResponse.Field(2, false, 2, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      @KeepForSdk
      public static FastJsonResponse.Field<String, String> forString(String var0, int var1) {
         return new FastJsonResponse.Field(7, false, 7, false, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      @KeepForSdk
      public static FastJsonResponse.Field<ArrayList<String>, ArrayList<String>> forStrings(String var0, int var1) {
         return new FastJsonResponse.Field(7, true, 7, true, var0, var1, (Class)null, (FastJsonResponse.FieldConverter)null);
      }

      @KeepForSdk
      public static FastJsonResponse.Field withConverter(String var0, int var1, FastJsonResponse.FieldConverter<?, ?> var2, boolean var3) {
         return new FastJsonResponse.Field(var2.zacj(), var3, var2.zack(), false, var0, var1, (Class)null, var2);
      }

      private final String zacm() {
         return this.zapx == null?null:this.zapx;
      }

      private final com.google.android.gms.common.server.converter.zaa zaco() {
         return this.zapz == null?null:com.google.android.gms.common.server.converter.zaa.zaa(this.zapz);
      }

      public final O convert(I var1) {
         return this.zapz.convert(var1);
      }

      public final I convertBack(O var1) {
         return this.zapz.convertBack(var1);
      }

      @KeepForSdk
      public int getSafeParcelableFieldId() {
         return this.zapv;
      }

      public String toString() {
         Objects.ToStringHelper var1 = Objects.toStringHelper(this).add("versionCode", Integer.valueOf(this.zale)).add("typeIn", Integer.valueOf(this.zapq)).add("typeInArray", Boolean.valueOf(this.zapr)).add("typeOut", Integer.valueOf(this.zaps)).add("typeOutArray", Boolean.valueOf(this.zapt)).add("outputFieldName", this.zapu).add("safeParcelFieldId", Integer.valueOf(this.zapv)).add("concreteTypeName", this.zacm());
         Class var2 = this.zapw;
         if(var2 != null) {
            var1.add("concreteType.class", var2.getCanonicalName());
         }

         if(this.zapz != null) {
            var1.add("converterName", this.zapz.getClass().getCanonicalName());
         }

         return var1.toString();
      }

      public void writeToParcel(Parcel var1, int var2) {
         int var3 = SafeParcelWriter.beginObjectHeader(var1);
         SafeParcelWriter.writeInt(var1, 1, this.zale);
         SafeParcelWriter.writeInt(var1, 2, this.zapq);
         SafeParcelWriter.writeBoolean(var1, 3, this.zapr);
         SafeParcelWriter.writeInt(var1, 4, this.zaps);
         SafeParcelWriter.writeBoolean(var1, 5, this.zapt);
         SafeParcelWriter.writeString(var1, 6, this.zapu, false);
         SafeParcelWriter.writeInt(var1, 7, this.getSafeParcelableFieldId());
         SafeParcelWriter.writeString(var1, 8, this.zacm(), false);
         SafeParcelWriter.writeParcelable(var1, 9, this.zaco(), var2, false);
         SafeParcelWriter.finishObjectHeader(var1, var3);
      }

      public final void zaa(zak var1) {
         this.zapy = var1;
      }

      public final FastJsonResponse.Field<I, O> zacl() {
         return new FastJsonResponse.Field(this.zale, this.zapq, this.zapr, this.zaps, this.zapt, this.zapu, this.zapv, this.zapx, this.zaco());
      }

      public final boolean zacn() {
         return this.zapz != null;
      }

      public final FastJsonResponse zacp() throws InstantiationException, IllegalAccessException {
         if(this.zapw == SafeParcelResponse.class) {
            Preconditions.checkNotNull(this.zapy, "The field mapping dictionary must be set if the concrete type is a SafeParcelResponse object.");
            return new SafeParcelResponse(this.zapy, this.zapx);
         } else {
            return (FastJsonResponse)this.zapw.newInstance();
         }
      }

      public final Map<String, FastJsonResponse.Field<?, ?>> zacq() {
         Preconditions.checkNotNull(this.zapx);
         Preconditions.checkNotNull(this.zapy);
         return this.zapy.zai(this.zapx);
      }
   }

   @ShowFirstParty
   public interface FieldConverter<I extends Object, O extends Object> {

      O convert(I var1);

      I convertBack(O var1);

      int zacj();

      int zack();
   }
}
