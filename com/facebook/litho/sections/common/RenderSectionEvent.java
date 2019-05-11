package com.facebook.litho.sections.common;

import com.facebook.litho.annotations.Event;
import com.facebook.litho.sections.Children;

@Event(
   returnType = Children.class
)
public class RenderSectionEvent {

   public RenderSectionEvent.DataSource dataSource;
   public Throwable error;
   public RenderSectionEvent.FetchType fetchType;
   public Object lastNonNullModel;
   public Object model;
   public RenderSectionEvent.FetchState state;



   public static enum FetchState {

      // $FF: synthetic field
      private static final RenderSectionEvent.FetchState[] $VALUES = new RenderSectionEvent.FetchState[]{INITIAL_STATE, DOWNLOADING_STATE, IDLE_STATE, DOWNLOAD_ERROR};
      DOWNLOADING_STATE("DOWNLOADING_STATE", 1),
      DOWNLOAD_ERROR("DOWNLOAD_ERROR", 3),
      IDLE_STATE("IDLE_STATE", 2),
      INITIAL_STATE("INITIAL_STATE", 0);


      private FetchState(String var1, int var2) {}
   }

   public static enum DataSource {

      // $FF: synthetic field
      private static final RenderSectionEvent.DataSource[] $VALUES = new RenderSectionEvent.DataSource[]{UNSET, FROM_NETWORK, FROM_LOCAL_CACHE};
      FROM_LOCAL_CACHE("FROM_LOCAL_CACHE", 2),
      FROM_NETWORK("FROM_NETWORK", 1),
      UNSET("UNSET", 0);


      private DataSource(String var1, int var2) {}
   }

   public static enum FetchType {

      // $FF: synthetic field
      private static final RenderSectionEvent.FetchType[] $VALUES = new RenderSectionEvent.FetchType[]{REFRESH_FETCH, HEAD_FETCH, TAIL_FETCH};
      HEAD_FETCH("HEAD_FETCH", 1),
      REFRESH_FETCH("REFRESH_FETCH", 0),
      TAIL_FETCH("TAIL_FETCH", 2);


      private FetchType(String var1, int var2) {}
   }
}
