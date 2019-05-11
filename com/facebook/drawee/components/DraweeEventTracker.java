package com.facebook.drawee.components;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class DraweeEventTracker {

   private static final int MAX_EVENTS_TO_TRACK = 20;
   private static boolean sEnabled;
   private static final DraweeEventTracker sInstance = new DraweeEventTracker();
   private final Queue<DraweeEventTracker.Event> mEventQueue = new ArrayBlockingQueue(20);


   public static void disable() {
      sEnabled = false;
   }

   public static DraweeEventTracker newInstance() {
      return sEnabled?new DraweeEventTracker():sInstance;
   }

   public void recordEvent(DraweeEventTracker.Event var1) {
      if(sEnabled) {
         if(this.mEventQueue.size() + 1 > 20) {
            this.mEventQueue.poll();
         }

         this.mEventQueue.add(var1);
      }
   }

   public String toString() {
      return this.mEventQueue.toString();
   }

   public static enum Event {

      // $FF: synthetic field
      private static final DraweeEventTracker.Event[] $VALUES = new DraweeEventTracker.Event[]{ON_SET_HIERARCHY, ON_CLEAR_HIERARCHY, ON_SET_CONTROLLER, ON_CLEAR_OLD_CONTROLLER, ON_CLEAR_CONTROLLER, ON_INIT_CONTROLLER, ON_ATTACH_CONTROLLER, ON_DETACH_CONTROLLER, ON_RELEASE_CONTROLLER, ON_DATASOURCE_SUBMIT, ON_DATASOURCE_RESULT, ON_DATASOURCE_RESULT_INT, ON_DATASOURCE_FAILURE, ON_DATASOURCE_FAILURE_INT, ON_HOLDER_ATTACH, ON_HOLDER_DETACH, ON_DRAWABLE_SHOW, ON_DRAWABLE_HIDE, ON_ACTIVITY_START, ON_ACTIVITY_STOP, ON_RUN_CLEAR_CONTROLLER, ON_SCHEDULE_CLEAR_CONTROLLER, ON_SAME_CONTROLLER_SKIPPED, ON_SUBMIT_CACHE_HIT};
      ON_ACTIVITY_START("ON_ACTIVITY_START", 18),
      ON_ACTIVITY_STOP("ON_ACTIVITY_STOP", 19),
      ON_ATTACH_CONTROLLER("ON_ATTACH_CONTROLLER", 6),
      ON_CLEAR_CONTROLLER("ON_CLEAR_CONTROLLER", 4),
      ON_CLEAR_HIERARCHY("ON_CLEAR_HIERARCHY", 1),
      ON_CLEAR_OLD_CONTROLLER("ON_CLEAR_OLD_CONTROLLER", 3),
      ON_DATASOURCE_FAILURE("ON_DATASOURCE_FAILURE", 12),
      ON_DATASOURCE_FAILURE_INT("ON_DATASOURCE_FAILURE_INT", 13),
      ON_DATASOURCE_RESULT("ON_DATASOURCE_RESULT", 10),
      ON_DATASOURCE_RESULT_INT("ON_DATASOURCE_RESULT_INT", 11),
      ON_DATASOURCE_SUBMIT("ON_DATASOURCE_SUBMIT", 9),
      ON_DETACH_CONTROLLER("ON_DETACH_CONTROLLER", 7),
      ON_DRAWABLE_HIDE("ON_DRAWABLE_HIDE", 17),
      ON_DRAWABLE_SHOW("ON_DRAWABLE_SHOW", 16),
      ON_HOLDER_ATTACH("ON_HOLDER_ATTACH", 14),
      ON_HOLDER_DETACH("ON_HOLDER_DETACH", 15),
      ON_INIT_CONTROLLER("ON_INIT_CONTROLLER", 5),
      ON_RELEASE_CONTROLLER("ON_RELEASE_CONTROLLER", 8),
      ON_RUN_CLEAR_CONTROLLER("ON_RUN_CLEAR_CONTROLLER", 20),
      ON_SAME_CONTROLLER_SKIPPED("ON_SAME_CONTROLLER_SKIPPED", 22),
      ON_SCHEDULE_CLEAR_CONTROLLER("ON_SCHEDULE_CLEAR_CONTROLLER", 21),
      ON_SET_CONTROLLER("ON_SET_CONTROLLER", 2),
      ON_SET_HIERARCHY("ON_SET_HIERARCHY", 0),
      ON_SUBMIT_CACHE_HIT("ON_SUBMIT_CACHE_HIT", 23);


      private Event(String var1, int var2) {}
   }
}
