package com.facebook.jni;

import com.facebook.proguard.annotations.DoNotStrip;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@DoNotStrip
public class MapIteratorHelper {

   @DoNotStrip
   private final Iterator<Entry> mIterator;
   @Nullable
   @DoNotStrip
   private Object mKey;
   @Nullable
   @DoNotStrip
   private Object mValue;


   @DoNotStrip
   public MapIteratorHelper(Map var1) {
      this.mIterator = var1.entrySet().iterator();
   }

   @DoNotStrip
   boolean hasNext() {
      if(this.mIterator.hasNext()) {
         Entry var1 = (Entry)this.mIterator.next();
         this.mKey = var1.getKey();
         this.mValue = var1.getValue();
         return true;
      } else {
         this.mKey = null;
         this.mValue = null;
         return false;
      }
   }
}
