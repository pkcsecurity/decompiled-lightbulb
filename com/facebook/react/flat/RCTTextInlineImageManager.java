package com.facebook.react.flat;

import com.facebook.react.flat.RCTTextInlineImage;
import com.facebook.react.flat.VirtualViewManager;

public final class RCTTextInlineImageManager extends VirtualViewManager<RCTTextInlineImage> {

   static final String REACT_CLASS = "RCTTextInlineImage";


   public RCTTextInlineImage createShadowNodeInstance() {
      return new RCTTextInlineImage();
   }

   public String getName() {
      return "RCTTextInlineImage";
   }

   public Class<RCTTextInlineImage> getShadowNodeClass() {
      return RCTTextInlineImage.class;
   }
}
