package com.facebook.litho.dataflow;

import com.facebook.litho.dataflow.BadGraphSetupException;

public class DetectedCycleException extends BadGraphSetupException {

   public DetectedCycleException(String var1) {
      super(var1);
   }
}
