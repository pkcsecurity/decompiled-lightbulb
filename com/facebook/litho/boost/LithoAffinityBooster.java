package com.facebook.litho.boost;


public interface LithoAffinityBooster {

   String getIdentifier();

   boolean isSupported();

   void release();

   boolean request();
}
