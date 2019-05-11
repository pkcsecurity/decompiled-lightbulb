package com.facebook.react.flat;

import com.facebook.react.flat.FlatViewManager;
import com.facebook.react.flat.RCTText;

public final class RCTTextManager extends FlatViewManager {

   static final String REACT_CLASS = "RCTText";


   public RCTText createShadowNodeInstance() {
      return new RCTText();
   }

   public String getName() {
      return "RCTText";
   }

   public Class<RCTText> getShadowNodeClass() {
      return RCTText.class;
   }
}
