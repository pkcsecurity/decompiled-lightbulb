package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.collect.MapMaker;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;
import javax.annotation.concurrent.ThreadSafe;

@Beta
@ThreadSafe
public class CycleDetectingLockFactory {

   private static final ConcurrentMap<Class<? extends Enum>, Map<? extends Enum, Object>> a = (new MapMaker()).e().k();
   private static final Logger b = Logger.getLogger(CycleDetectingLockFactory.class.getName());
   private static final ThreadLocal<ArrayList<Object>> c = new ThreadLocal() {
      protected ArrayList<Object> a() {
         return rl.a(3);
      }
      // $FF: synthetic method
      protected Object initialValue() {
         return this.a();
      }
   };



   @Beta
   @ThreadSafe
   public interface Policy {
   }

   interface CycleDetectingLock {
   }
}
