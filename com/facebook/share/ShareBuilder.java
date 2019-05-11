package com.facebook.share;


public interface ShareBuilder<P extends Object, E extends Object & ShareBuilder> {

   P build();
}
