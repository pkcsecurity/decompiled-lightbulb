package com.facebook.appevents;

import android.content.Context;
import com.facebook.GraphRequest;
import com.facebook.appevents.AppEvent;
import com.facebook.internal.AttributionIdentifiers;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

class SessionEventsState {

   private final int MAX_ACCUMULATED_LOG_EVENTS = 1000;
   private List<AppEvent> accumulatedEvents = new ArrayList();
   private String anonymousAppDeviceGUID;
   private AttributionIdentifiers attributionIdentifiers;
   private List<AppEvent> inFlightEvents = new ArrayList();
   private int numSkippedEventsDueToFullBuffer;


   public SessionEventsState(AttributionIdentifiers var1, String var2) {
      this.attributionIdentifiers = var1;
      this.anonymousAppDeviceGUID = var2;
   }

   private void populateRequest(GraphRequest param1, Context param2, int param3, JSONArray param4, boolean param5) {
      // $FF: Couldn't be decompiled
   }

   public void accumulatePersistedEvents(List<AppEvent> var1) {
      synchronized(this){}

      try {
         this.accumulatedEvents.addAll(var1);
      } finally {
         ;
      }

   }

   public void addEvent(AppEvent var1) {
      synchronized(this){}

      try {
         if(this.accumulatedEvents.size() + this.inFlightEvents.size() >= 1000) {
            ++this.numSkippedEventsDueToFullBuffer;
         } else {
            this.accumulatedEvents.add(var1);
         }
      } finally {
         ;
      }

   }

   public void clearInFlightAndStats(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public int getAccumulatedEventCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.accumulatedEvents.size();
      } finally {
         ;
      }

      return var1;
   }

   public List<AppEvent> getEventsToPersist() {
      synchronized(this){}

      List var1;
      try {
         var1 = this.accumulatedEvents;
         this.accumulatedEvents = new ArrayList();
      } finally {
         ;
      }

      return var1;
   }

   public int populateRequest(GraphRequest param1, Context param2, boolean param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }
}
