package com.facebook.react.flat;

import com.facebook.react.flat.RCTVirtualText;
import com.facebook.react.flat.VirtualViewManager;

public final class RCTVirtualTextManager extends VirtualViewManager<RCTVirtualText> {

   static final String REACT_CLASS = "RCTVirtualText";


   public RCTVirtualText createShadowNodeInstance() {
      return new RCTVirtualText();
   }

   public String getName() {
      return "RCTVirtualText";
   }

   public Class<RCTVirtualText> getShadowNodeClass() {
      return RCTVirtualText.class;
   }
}
