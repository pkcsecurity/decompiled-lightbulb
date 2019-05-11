package com.facebook;

import android.os.Handler;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GraphRequestBatch extends AbstractList<GraphRequest> {

   private static AtomicInteger idGenerator = new AtomicInteger();
   private String batchApplicationId;
   private Handler callbackHandler;
   private List<GraphRequestBatch.Callback> callbacks;
   private final String id;
   private List<GraphRequest> requests = new ArrayList();
   private int timeoutInMilliseconds = 0;


   public GraphRequestBatch() {
      this.id = Integer.valueOf(idGenerator.incrementAndGet()).toString();
      this.callbacks = new ArrayList();
      this.requests = new ArrayList();
   }

   public GraphRequestBatch(GraphRequestBatch var1) {
      this.id = Integer.valueOf(idGenerator.incrementAndGet()).toString();
      this.callbacks = new ArrayList();
      this.requests = new ArrayList(var1);
      this.callbackHandler = var1.callbackHandler;
      this.timeoutInMilliseconds = var1.timeoutInMilliseconds;
      this.callbacks = new ArrayList(var1.callbacks);
   }

   public GraphRequestBatch(Collection<GraphRequest> var1) {
      this.id = Integer.valueOf(idGenerator.incrementAndGet()).toString();
      this.callbacks = new ArrayList();
      this.requests = new ArrayList(var1);
   }

   public GraphRequestBatch(GraphRequest ... var1) {
      this.id = Integer.valueOf(idGenerator.incrementAndGet()).toString();
      this.callbacks = new ArrayList();
      this.requests = Arrays.asList(var1);
   }

   public final void add(int var1, GraphRequest var2) {
      this.requests.add(var1, var2);
   }

   public final boolean add(GraphRequest var1) {
      return this.requests.add(var1);
   }

   public void addCallback(GraphRequestBatch.Callback var1) {
      if(!this.callbacks.contains(var1)) {
         this.callbacks.add(var1);
      }

   }

   public final void clear() {
      this.requests.clear();
   }

   public final List<GraphResponse> executeAndWait() {
      return this.executeAndWaitImpl();
   }

   List<GraphResponse> executeAndWaitImpl() {
      return GraphRequest.executeBatchAndWait(this);
   }

   public final GraphRequestAsyncTask executeAsync() {
      return this.executeAsyncImpl();
   }

   GraphRequestAsyncTask executeAsyncImpl() {
      return GraphRequest.executeBatchAsync(this);
   }

   public final GraphRequest get(int var1) {
      return (GraphRequest)this.requests.get(var1);
   }

   public final String getBatchApplicationId() {
      return this.batchApplicationId;
   }

   final Handler getCallbackHandler() {
      return this.callbackHandler;
   }

   final List<GraphRequestBatch.Callback> getCallbacks() {
      return this.callbacks;
   }

   final String getId() {
      return this.id;
   }

   final List<GraphRequest> getRequests() {
      return this.requests;
   }

   public int getTimeout() {
      return this.timeoutInMilliseconds;
   }

   public final GraphRequest remove(int var1) {
      return (GraphRequest)this.requests.remove(var1);
   }

   public void removeCallback(GraphRequestBatch.Callback var1) {
      this.callbacks.remove(var1);
   }

   public final GraphRequest set(int var1, GraphRequest var2) {
      return (GraphRequest)this.requests.set(var1, var2);
   }

   public final void setBatchApplicationId(String var1) {
      this.batchApplicationId = var1;
   }

   final void setCallbackHandler(Handler var1) {
      this.callbackHandler = var1;
   }

   public void setTimeout(int var1) {
      if(var1 < 0) {
         throw new IllegalArgumentException("Argument timeoutInMilliseconds must be >= 0.");
      } else {
         this.timeoutInMilliseconds = var1;
      }
   }

   public final int size() {
      return this.requests.size();
   }

   public interface OnProgressCallback extends GraphRequestBatch.Callback {

      void onBatchProgress(GraphRequestBatch var1, long var2, long var4);
   }

   public interface Callback {

      void onBatchCompleted(GraphRequestBatch var1);
   }
}
