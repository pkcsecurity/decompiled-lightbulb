package com.facebook.appevents;

import com.facebook.appevents.FlushResult;

class FlushStatistics {

   public int numEvents = 0;
   public FlushResult result;


   FlushStatistics() {
      this.result = FlushResult.SUCCESS;
   }
}
