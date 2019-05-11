package com.facebook;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.internal.Utility;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.List;

public class GraphRequestAsyncTask extends AsyncTask<Void, Void, List<GraphResponse>> {

   private static final String TAG = GraphRequestAsyncTask.class.getCanonicalName();
   private final HttpURLConnection connection;
   private Exception exception;
   private final GraphRequestBatch requests;


   public GraphRequestAsyncTask(GraphRequestBatch var1) {
      this((HttpURLConnection)null, var1);
   }

   public GraphRequestAsyncTask(HttpURLConnection var1, GraphRequestBatch var2) {
      this.requests = var2;
      this.connection = var1;
   }

   public GraphRequestAsyncTask(HttpURLConnection var1, Collection<GraphRequest> var2) {
      this(var1, new GraphRequestBatch(var2));
   }

   public GraphRequestAsyncTask(HttpURLConnection var1, GraphRequest ... var2) {
      this(var1, new GraphRequestBatch(var2));
   }

   public GraphRequestAsyncTask(Collection<GraphRequest> var1) {
      this((HttpURLConnection)null, new GraphRequestBatch(var1));
   }

   public GraphRequestAsyncTask(GraphRequest ... var1) {
      this((HttpURLConnection)null, new GraphRequestBatch(var1));
   }

   protected List<GraphResponse> doInBackground(Void ... var1) {
      try {
         if(this.connection == null) {
            return this.requests.executeAndWait();
         } else {
            List var3 = GraphRequest.executeConnectionAndWait(this.connection, this.requests);
            return var3;
         }
      } catch (Exception var2) {
         this.exception = var2;
         return null;
      }
   }

   protected final Exception getException() {
      return this.exception;
   }

   protected final GraphRequestBatch getRequests() {
      return this.requests;
   }

   protected void onPostExecute(List<GraphResponse> var1) {
      super.onPostExecute(var1);
      if(this.exception != null) {
         Utility.logd(TAG, String.format("onPostExecute: exception encountered during request: %s", new Object[]{this.exception.getMessage()}));
      }

   }

   protected void onPreExecute() {
      super.onPreExecute();
      if(FacebookSdk.isDebugEnabled()) {
         Utility.logd(TAG, String.format("execute async task: %s", new Object[]{this}));
      }

      if(this.requests.getCallbackHandler() == null) {
         Handler var1;
         if(Thread.currentThread() instanceof HandlerThread) {
            var1 = new Handler();
         } else {
            var1 = new Handler(Looper.getMainLooper());
         }

         this.requests.setCallbackHandler(var1);
      }

   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("{RequestAsyncTask: ");
      var1.append(" connection: ");
      var1.append(this.connection);
      var1.append(", requests: ");
      var1.append(this.requests);
      var1.append("}");
      return var1.toString();
   }
}
