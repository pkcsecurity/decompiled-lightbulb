package com.facebook.react.flat;

import com.facebook.react.flat.RCTRawText;
import com.facebook.react.flat.VirtualViewManager;

public final class RCTRawTextManager extends VirtualViewManager<RCTRawText> {

   static final String REACT_CLASS = "RCTRawText";


   public RCTRawText createShadowNodeInstance() {
      return new RCTRawText();
   }

   public String getName() {
      return "RCTRawText";
   }

   public Class<RCTRawText> getShadowNodeClass() {
      return RCTRawText.class;
   }
}
