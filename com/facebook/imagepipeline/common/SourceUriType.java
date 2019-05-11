package com.facebook.imagepipeline.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface SourceUriType {

   int SOURCE_TYPE_DATA = 7;
   int SOURCE_TYPE_LOCAL_ASSET = 5;
   int SOURCE_TYPE_LOCAL_CONTENT = 4;
   int SOURCE_TYPE_LOCAL_FILE = 1;
   int SOURCE_TYPE_LOCAL_IMAGE_FILE = 3;
   int SOURCE_TYPE_LOCAL_RESOURCE = 6;
   int SOURCE_TYPE_LOCAL_VIDEO_FILE = 2;
   int SOURCE_TYPE_NETWORK = 0;
   int SOURCE_TYPE_QUALIFIED_RESOURCE = 8;
   int SOURCE_TYPE_UNKNOWN = -1;

}
