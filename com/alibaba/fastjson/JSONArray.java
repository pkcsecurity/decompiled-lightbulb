package com.alibaba.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public class JSONArray extends JSON implements Serializable, Cloneable, List<Object>, RandomAccess {

   protected transient Type componentType;
   private final List<Object> list;
   protected transient Object relatedArray;


   public JSONArray() {
      this.list = new ArrayList(10);
   }

   public JSONArray(int var1) {
      this.list = new ArrayList(var1);
   }

   public JSONArray(List<Object> var1) {
      this.list = var1;
   }

   public void add(int var1, Object var2) {
      this.list.add(var1, var2);
   }

   public boolean add(Object var1) {
      return this.list.add(var1);
   }

   public boolean addAll(int var1, Collection<? extends Object> var2) {
      return this.list.addAll(var1, var2);
   }

   public boolean addAll(Collection<? extends Object> var1) {
      return this.list.addAll(var1);
   }

   public void clear() {
      this.list.clear();
   }

   public Object clone() {
      return new JSONArray(new ArrayList(this.list));
   }

   public boolean contains(Object var1) {
      return this.list.contains(var1);
   }

   public boolean containsAll(Collection<?> var1) {
      return this.list.containsAll(var1);
   }

   public boolean equals(Object var1) {
      return this.list.equals(var1);
   }

   public Object get(int var1) {
      return this.list.get(var1);
   }

   public BigDecimal getBigDecimal(int var1) {
      return TypeUtils.castToBigDecimal(this.get(var1));
   }

   public BigInteger getBigInteger(int var1) {
      return TypeUtils.castToBigInteger(this.get(var1));
   }

   public Boolean getBoolean(int var1) {
      Object var2 = this.get(var1);
      return var2 == null?null:TypeUtils.castToBoolean(var2);
   }

   public boolean getBooleanValue(int var1) {
      Object var2 = this.get(var1);
      return var2 == null?false:TypeUtils.castToBoolean(var2).booleanValue();
   }

   public Byte getByte(int var1) {
      return TypeUtils.castToByte(this.get(var1));
   }

   public byte getByteValue(int var1) {
      Object var2 = this.get(var1);
      return var2 == null?0:TypeUtils.castToByte(var2).byteValue();
   }

   public Type getComponentType() {
      return this.componentType;
   }

   public Date getDate(int var1) {
      return TypeUtils.castToDate(this.get(var1));
   }

   public Double getDouble(int var1) {
      return TypeUtils.castToDouble(this.get(var1));
   }

   public double getDoubleValue(int var1) {
      Object var2 = this.get(var1);
      return var2 == null?0.0D:TypeUtils.castToDouble(var2).doubleValue();
   }

   public Float getFloat(int var1) {
      return TypeUtils.castToFloat(this.get(var1));
   }

   public float getFloatValue(int var1) {
      Object var2 = this.get(var1);
      return var2 == null?0.0F:TypeUtils.castToFloat(var2).floatValue();
   }

   public int getIntValue(int var1) {
      Object var2 = this.get(var1);
      return var2 == null?0:TypeUtils.castToInt(var2).intValue();
   }

   public Integer getInteger(int var1) {
      return TypeUtils.castToInt(this.get(var1));
   }

   public JSONArray getJSONArray(int var1) {
      Object var2 = this.list.get(var1);
      return var2 instanceof JSONArray?(JSONArray)var2:(JSONArray)toJSON(var2);
   }

   public JSONObject getJSONObject(int var1) {
      Object var2 = this.list.get(var1);
      return var2 instanceof JSONObject?(JSONObject)var2:(JSONObject)toJSON(var2);
   }

   public Long getLong(int var1) {
      return TypeUtils.castToLong(this.get(var1));
   }

   public long getLongValue(int var1) {
      Object var2 = this.get(var1);
      return var2 == null?0L:TypeUtils.castToLong(var2).longValue();
   }

   public <T extends Object> T getObject(int var1, Class<T> var2) {
      return TypeUtils.castToJavaBean(this.list.get(var1), var2);
   }

   public Object getRelatedArray() {
      return this.relatedArray;
   }

   public Short getShort(int var1) {
      return TypeUtils.castToShort(this.get(var1));
   }

   public short getShortValue(int var1) {
      Object var2 = this.get(var1);
      return var2 == null?0:TypeUtils.castToShort(var2).shortValue();
   }

   public String getString(int var1) {
      return TypeUtils.castToString(this.get(var1));
   }

   public int hashCode() {
      return this.list.hashCode();
   }

   public int indexOf(Object var1) {
      return this.list.indexOf(var1);
   }

   public boolean isEmpty() {
      return this.list.isEmpty();
   }

   public Iterator<Object> iterator() {
      return this.list.iterator();
   }

   public int lastIndexOf(Object var1) {
      return this.list.lastIndexOf(var1);
   }

   public ListIterator<Object> listIterator() {
      return this.list.listIterator();
   }

   public ListIterator<Object> listIterator(int var1) {
      return this.list.listIterator(var1);
   }

   public Object remove(int var1) {
      return this.list.remove(var1);
   }

   public boolean remove(Object var1) {
      return this.list.remove(var1);
   }

   public boolean removeAll(Collection<?> var1) {
      return this.list.removeAll(var1);
   }

   public boolean retainAll(Collection<?> var1) {
      return this.list.retainAll(var1);
   }

   public Object set(int var1, Object var2) {
      return this.list.set(var1, var2);
   }

   public void setComponentType(Type var1) {
      this.componentType = var1;
   }

   public void setRelatedArray(Object var1) {
      this.relatedArray = var1;
   }

   public int size() {
      return this.list.size();
   }

   public List<Object> subList(int var1, int var2) {
      return this.list.subList(var1, var2);
   }

   public Object[] toArray() {
      return this.list.toArray();
   }

   public <T extends Object> T[] toArray(T[] var1) {
      return this.list.toArray(var1);
   }
}
