package com.facebook.jni;

import com.facebook.proguard.annotations.DoNotStrip;
import java.util.Iterator;
import javax.annotation.Nullable;

@DoNotStrip
public class IteratorHelper {

   @Nullable
   @DoNotStrip
   private Object mElement;
   private final Iterator mIterator;


   @DoNotStrip
   public IteratorHelper(Iterable var1) {
      this.mIterator = var1.iterator();
   }

   @DoNotStrip
   public IteratorHelper(Iterator var1) {
      this.mIterator = var1;
   }

   @DoNotStrip
   boolean hasNext() {
      if(this.mIterator.hasNext()) {
         this.mElement = this.mIterator.next();
         return true;
      } else {
         this.mElement = null;
         return false;
      }
   }
}
