package com.facebook.litho.sections.common;

import com.facebook.litho.annotations.Event;
import com.facebook.litho.sections.common.RenderSectionEvent;

@Event
public class ConnectionStateEvent {

   public Object connectionData;
   public RenderSectionEvent.DataSource dataSource;
   public Throwable fetchError;
   public RenderSectionEvent.FetchState fetchState;
   public RenderSectionEvent.FetchType fetchType;
   public boolean isEmpty;


}
