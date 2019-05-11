package com.facebook.react.flat;

import com.facebook.react.flat.RCTTextInput;
import com.facebook.react.views.textinput.ReactTextInputManager;

public class RCTTextInputManager extends ReactTextInputManager {

   static final String REACT_CLASS = "AndroidTextInput";


   public RCTTextInput createShadowNodeInstance() {
      return new RCTTextInput();
   }

   public Class<RCTTextInput> getShadowNodeClass() {
      return RCTTextInput.class;
   }
}
