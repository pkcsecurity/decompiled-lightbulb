package com.facebook.litho.sections;

import com.facebook.litho.annotations.Event;
import javax.annotation.Nullable;

@Event
public class LoadingEvent {

   public boolean isEmpty;
   public LoadingEvent.LoadingState loadingState;
   @Nullable
   public Throwable t;



   public static enum LoadingState {

      // $FF: synthetic field
      private static final LoadingEvent.LoadingState[] $VALUES = new LoadingEvent.LoadingState[]{INITIAL_LOAD, LOADING, SUCCEEDED, FAILED};
      FAILED("FAILED", 3),
      INITIAL_LOAD("INITIAL_LOAD", 0),
      LOADING("LOADING", 1),
      SUCCEEDED("SUCCEEDED", 2);


      private LoadingState(String var1, int var2) {}
   }
}
