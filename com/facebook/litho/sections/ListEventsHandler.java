package com.facebook.litho.sections;


public interface ListEventsHandler<QueryParams extends Object> {

   void onPTR(QueryParams var1);

   void onScrollNearBottom(QueryParams var1);

   void onScrollNearTop(QueryParams var1);
}
