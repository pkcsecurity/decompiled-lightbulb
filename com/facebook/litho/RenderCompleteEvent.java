package com.facebook.litho;

import com.facebook.litho.annotations.Event;

@Event
public class RenderCompleteEvent {

   public RenderCompleteEvent.RenderState renderState;
   public long timestampMillis;



   public static enum RenderState {

      // $FF: synthetic field
      private static final RenderCompleteEvent.RenderState[] $VALUES = new RenderCompleteEvent.RenderState[]{RENDER_DRAWN, RENDER_ADDED, FAILED_EXCEED_MAX_ATTEMPTS};
      FAILED_EXCEED_MAX_ATTEMPTS("FAILED_EXCEED_MAX_ATTEMPTS", 2),
      RENDER_ADDED("RENDER_ADDED", 1),
      RENDER_DRAWN("RENDER_DRAWN", 0);


      private RenderState(String var1, int var2) {}
   }
}
