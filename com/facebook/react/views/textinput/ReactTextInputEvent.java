package com.facebook.react.views.textinput;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ReactTextInputEvent extends Event<ReactTextInputEvent> {

   public static final String EVENT_NAME = "topTextInput";
   private String mPreviousText;
   private int mRangeEnd;
   private int mRangeStart;
   private String mText;


   public ReactTextInputEvent(int var1, String var2, String var3, int var4, int var5) {
      super(var1);
      this.mText = var2;
      this.mPreviousText = var3;
      this.mRangeStart = var4;
      this.mRangeEnd = var5;
   }

   private WritableMap serializeEventData() {
      WritableMap var1 = Arguments.createMap();
      WritableMap var2 = Arguments.createMap();
      var2.putDouble("start", (double)this.mRangeStart);
      var2.putDouble("end", (double)this.mRangeEnd);
      var1.putString("text", this.mText);
      var1.putString("previousText", this.mPreviousText);
      var1.putMap("range", var2);
      var1.putInt("target", this.getViewTag());
      return var1;
   }

   public boolean canCoalesce() {
      return false;
   }

   public void dispatch(RCTEventEmitter var1) {
      var1.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
   }

   public String getEventName() {
      return "topTextInput";
   }
}
