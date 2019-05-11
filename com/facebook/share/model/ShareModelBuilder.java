package com.facebook.share.model;

import com.facebook.share.ShareBuilder;
import com.facebook.share.model.ShareModel;

public interface ShareModelBuilder<P extends Object & ShareModel, E extends Object & ShareModelBuilder> extends ShareBuilder<P, E> {

   E readFrom(P var1);
}
