package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.AfterFilter;
import com.alibaba.fastjson.serializer.BeforeFilter;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerialContext;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class JSONSerializer {

   protected List<AfterFilter> afterFilters;
   protected List<BeforeFilter> beforeFilters;
   public final SerializeConfig config;
   protected SerialContext context;
   private DateFormat dateFormat;
   private String dateFormatPattern;
   private int indentCount;
   public Locale locale;
   protected List<NameFilter> nameFilters;
   public final SerializeWriter out;
   protected List<PropertyFilter> propertyFilters;
   protected List<PropertyPreFilter> propertyPreFilters;
   protected IdentityHashMap<Object, SerialContext> references;
   public TimeZone timeZone;
   protected List<ValueFilter> valueFilters;


   public JSONSerializer() {
      this(new SerializeWriter((Writer)null, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY), SerializeConfig.globalInstance);
   }

   public JSONSerializer(SerializeConfig var1) {
      this(new SerializeWriter((Writer)null, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY), var1);
   }

   public JSONSerializer(SerializeWriter var1) {
      this(var1, SerializeConfig.globalInstance);
   }

   public JSONSerializer(SerializeWriter var1, SerializeConfig var2) {
      this.beforeFilters = null;
      this.afterFilters = null;
      this.propertyFilters = null;
      this.valueFilters = null;
      this.nameFilters = null;
      this.propertyPreFilters = null;
      this.indentCount = 0;
      this.references = null;
      this.timeZone = JSON.defaultTimeZone;
      this.locale = JSON.defaultLocale;
      this.out = var1;
      this.config = var2;
      this.timeZone = JSON.defaultTimeZone;
   }

   public static Object processValue(JSONSerializer var0, Object var1, Object var2, Object var3) {
      List var5 = var0.valueFilters;
      Object var4 = var3;
      if(var5 != null) {
         Object var6 = var2;
         if(var2 != null) {
            var6 = var2;
            if(!(var2 instanceof String)) {
               var6 = JSON.toJSONString(var2);
            }
         }

         Iterator var7 = var5.iterator();

         while(true) {
            var4 = var3;
            if(!var7.hasNext()) {
               break;
            }

            var3 = ((ValueFilter)var7.next()).process(var1, (String)var6, var3);
         }
      }

      return var4;
   }

   public static final void write(SerializeWriter var0, Object var1) {
      (new JSONSerializer(var0, SerializeConfig.globalInstance)).write(var1);
   }

   public static final void write(Writer var0, Object var1) {
      SerializeWriter var2 = new SerializeWriter((Writer)null, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);

      try {
         (new JSONSerializer(var2, SerializeConfig.globalInstance)).write(var1);
         var2.writeTo(var0);
      } catch (IOException var5) {
         throw new JSONException(var5.getMessage(), var5);
      } finally {
         var2.close();
      }

   }

   public boolean apply(Object var1, Object var2, Object var3) {
      List var5 = this.propertyFilters;
      if(var5 == null) {
         return true;
      } else {
         Object var4 = var2;
         if(var2 != null) {
            var4 = var2;
            if(!(var2 instanceof String)) {
               var4 = JSON.toJSONString(var2);
            }
         }

         Iterator var6 = var5.iterator();

         do {
            if(!var6.hasNext()) {
               return true;
            }
         } while(((PropertyFilter)var6.next()).apply(var1, (String)var4, var3));

         return false;
      }
   }

   public boolean applyName(Object var1, Object var2) {
      List var3 = this.propertyPreFilters;
      if(var3 == null) {
         return true;
      } else {
         Iterator var4 = var3.iterator();

         PropertyPreFilter var5;
         Object var6;
         do {
            if(!var4.hasNext()) {
               return true;
            }

            var5 = (PropertyPreFilter)var4.next();
            var6 = var2;
            if(var2 != null) {
               var6 = var2;
               if(!(var2 instanceof String)) {
                  var6 = JSON.toJSONString(var2);
               }
            }

            var2 = var6;
         } while(var5.apply(this, var1, (String)var6));

         return false;
      }
   }

   public void close() {
      this.out.close();
   }

   public void config(SerializerFeature var1, boolean var2) {
      this.out.config(var1, var2);
   }

   public void decrementIdent() {
      --this.indentCount;
   }

   public List<AfterFilter> getAfterFilters() {
      if(this.afterFilters == null) {
         this.afterFilters = new ArrayList();
      }

      return this.afterFilters;
   }

   public List<BeforeFilter> getBeforeFilters() {
      if(this.beforeFilters == null) {
         this.beforeFilters = new ArrayList();
      }

      return this.beforeFilters;
   }

   public SerialContext getContext() {
      return this.context;
   }

   public DateFormat getDateFormat() {
      if(this.dateFormat == null && this.dateFormatPattern != null) {
         this.dateFormat = new SimpleDateFormat(this.dateFormatPattern, this.locale);
         this.dateFormat.setTimeZone(this.timeZone);
      }

      return this.dateFormat;
   }

   public String getDateFormatPattern() {
      return this.dateFormat instanceof SimpleDateFormat?((SimpleDateFormat)this.dateFormat).toPattern():this.dateFormatPattern;
   }

   public List<NameFilter> getNameFilters() {
      if(this.nameFilters == null) {
         this.nameFilters = new ArrayList();
      }

      return this.nameFilters;
   }

   public List<PropertyFilter> getPropertyFilters() {
      if(this.propertyFilters == null) {
         this.propertyFilters = new ArrayList();
      }

      return this.propertyFilters;
   }

   public List<PropertyPreFilter> getPropertyPreFilters() {
      if(this.propertyPreFilters == null) {
         this.propertyPreFilters = new ArrayList();
      }

      return this.propertyPreFilters;
   }

   public List<ValueFilter> getValueFilters() {
      if(this.valueFilters == null) {
         this.valueFilters = new ArrayList();
      }

      return this.valueFilters;
   }

   public SerializeWriter getWriter() {
      return this.out;
   }

   public void incrementIndent() {
      ++this.indentCount;
   }

   public void println() {
      this.out.write(10);

      for(int var1 = 0; var1 < this.indentCount; ++var1) {
         this.out.write(9);
      }

   }

   public Object processKey(Object var1, Object var2, Object var3) {
      List var6 = this.nameFilters;
      Object var5 = var2;
      if(var6 != null) {
         Object var4 = var2;
         if(var2 != null) {
            var4 = var2;
            if(!(var2 instanceof String)) {
               var4 = JSON.toJSONString(var2);
            }
         }

         Iterator var7 = var6.iterator();

         while(true) {
            var5 = var4;
            if(!var7.hasNext()) {
               break;
            }

            var4 = ((NameFilter)var7.next()).process(var1, (String)var4, var3);
         }
      }

      return var5;
   }

   public void setContext(SerialContext var1, Object var2, Object var3, int var4) {
      if((this.out.features & SerializerFeature.DisableCircularReferenceDetect.mask) == 0) {
         this.context = new SerialContext(var1, var2, var3, var4);
         if(this.references == null) {
            this.references = new IdentityHashMap();
         }

         this.references.put(var2, this.context);
      }

   }

   public void setDateFormat(String var1) {
      this.dateFormatPattern = var1;
      if(this.dateFormat != null) {
         this.dateFormat = null;
      }

   }

   public void setDateFormat(DateFormat var1) {
      this.dateFormat = var1;
      if(this.dateFormatPattern != null) {
         this.dateFormatPattern = null;
      }

   }

   public String toString() {
      return this.out.toString();
   }

   public final void write(Object var1) {
      if(var1 == null) {
         this.out.writeNull();
      } else {
         Class var2 = var1.getClass();
         ObjectSerializer var4 = this.config.get(var2);

         try {
            var4.write(this, var1, (Object)null, (Type)null);
         } catch (IOException var3) {
            throw new JSONException(var3.getMessage(), var3);
         }
      }
   }

   public final void write(String var1) {
      if(var1 == null) {
         if((this.out.features & SerializerFeature.WriteNullStringAsEmpty.mask) != 0) {
            this.out.writeString("");
         } else {
            this.out.writeNull();
         }
      } else if((this.out.features & SerializerFeature.UseSingleQuotes.mask) != 0) {
         this.out.writeStringWithSingleQuote(var1);
      } else {
         this.out.writeStringWithDoubleQuote(var1, '\u0000', true);
      }
   }

   protected final void writeKeyValue(char var1, String var2, Object var3) {
      if(var1 != 0) {
         this.out.write(var1);
      }

      this.out.writeFieldName(var2, true);
      this.write(var3);
   }

   public void writeReference(Object var1) {
      SerialContext var3 = this.context;
      if(var1 == var3.object) {
         this.out.write("{\"$ref\":\"@\"}");
      } else {
         SerialContext var4 = var3.parent;
         SerialContext var2 = var3;
         if(var4 != null) {
            var2 = var3;
            if(var1 == var4.object) {
               this.out.write("{\"$ref\":\"..\"}");
               return;
            }
         }

         while(var2.parent != null) {
            var2 = var2.parent;
         }

         if(var1 == var2.object) {
            this.out.write("{\"$ref\":\"$\"}");
         } else {
            String var5 = ((SerialContext)this.references.get(var1)).toString();
            this.out.write("{\"$ref\":\"");
            this.out.write(var5);
            this.out.write("\"}");
         }
      }
   }

   public final void writeWithFieldName(Object var1, Object var2) {
      this.writeWithFieldName(var1, var2, (Type)null, 0);
   }

   public final void writeWithFieldName(Object param1, Object param2, Type param3, int param4) {
      // $FF: Couldn't be decompiled
   }

   public final void writeWithFormat(Object var1, String var2) {
      if(var1 instanceof Date) {
         DateFormat var4 = this.getDateFormat();
         Object var3 = var4;
         if(var4 == null) {
            var3 = new SimpleDateFormat(var2, this.locale);
            ((DateFormat)var3).setTimeZone(this.timeZone);
         }

         String var5 = ((DateFormat)var3).format((Date)var1);
         this.out.writeString(var5);
      } else {
         this.write(var1);
      }
   }
}
