package com.tuya.smart.android.common.task;

import com.tuya.smart.android.common.task.TuyaExecutor.1;
import com.tuya.smart.android.common.task.TuyaExecutor.2;
import com.tuya.smart.android.common.task.TuyaExecutor.TuyaRunable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;

public class TuyaExecutor {

   private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
   private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
   private static final long KEEP_ALIVE = 1L;
   private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 4 + 1;
   private static final String TAG = "TuyaExecutor";
   private static final int TYPE_ABORT_POLICY = 0;
   private static final int TYPE_CALLER_RUNS_POLICY = 2;
   private static final int TYPE_DISCARD_OLDEST_POLICY = 1;
   private static final int TYPE_DISCARD_POLICY = 3;
   private static final BlockingQueue<Runnable> mPoolWorkQueue = new LinkedBlockingQueue(128);
   private static volatile TuyaExecutor mTuyaExecutor;
   private final AbortPolicy mAbortPolicy = new AbortPolicy();
   private final CallerRunsPolicy mCallerRunsPolicy = new CallerRunsPolicy();
   private final DiscardOldestPolicy mDiscardOldestPolicy = new DiscardOldestPolicy();
   private final DiscardPolicy mDiscardPolicy = new DiscardPolicy();
   private ThreadPoolExecutor mExecutorService;


   private TuyaExecutor() {
      this.mExecutorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 1L, TimeUnit.SECONDS, mPoolWorkQueue, new 1(this), new 2(this));
   }

   // $FF: synthetic method
   static CallerRunsPolicy access$100(TuyaExecutor var0) {
      return var0.mCallerRunsPolicy;
   }

   // $FF: synthetic method
   static DiscardOldestPolicy access$200(TuyaExecutor var0) {
      return var0.mDiscardOldestPolicy;
   }

   // $FF: synthetic method
   static DiscardPolicy access$300(TuyaExecutor var0) {
      return var0.mDiscardPolicy;
   }

   private void excutorAbortPolicy(Runnable var1) {
      this.getExecutorService().execute(new TuyaRunable(0, var1));
   }

   public static TuyaExecutor getInstance() {
      // $FF: Couldn't be decompiled
   }

   public void excutorCallerRunsPolicy(Runnable var1) {
      this.getExecutorService().execute(new TuyaRunable(2, var1));
   }

   public void excutorDiscardOldestPolicy(Runnable var1) {
      this.getExecutorService().execute(new TuyaRunable(1, var1));
   }

   public void excutorDiscardPolicy(Runnable var1) {
      this.getExecutorService().execute(new TuyaRunable(3, var1));
   }

   public ExecutorService getExecutorService() {
      return this.mExecutorService;
   }

   public ExecutorService getTuyaExecutorService() {
      return this.mExecutorService;
   }

   public void onDestroy() {}

   public void submitCallerRunsPolicy(Runnable var1) {
      this.getExecutorService().submit(new TuyaRunable(2, var1));
   }

   public void submitDiscardOldestPolicy(Runnable var1) {
      this.getExecutorService().submit(new TuyaRunable(1, var1));
   }

   public void submitDiscardPolicy(Runnable var1) {
      this.getExecutorService().submit(new TuyaRunable(3, var1));
   }
}
