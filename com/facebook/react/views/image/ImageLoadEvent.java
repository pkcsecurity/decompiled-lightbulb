package com.facebook.react.views.image;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import javax.annotation.Nullable;

public class ImageLoadEvent extends Event<ImageLoadEvent> {

   public static final int ON_ERROR = 1;
   public static final int ON_LOAD = 2;
   public static final int ON_LOAD_END = 3;
   public static final int ON_LOAD_START = 4;
   public static final int ON_PROGRESS = 5;
   private final int mEventType;
   private final int mHeight;
   @Nullable
   private final String mImageUri;
   private final int mWidth;


   public ImageLoadEvent(int var1, int var2) {
      this(var1, var2, (String)null);
   }

   public ImageLoadEvent(int var1, int var2, String var3) {
      this(var1, var2, var3, 0, 0);
   }

   public ImageLoadEvent(int var1, int var2, @Nullable String var3, int var4, int var5) {
      super(var1);
      this.mEventType = var2;
      this.mImageUri = var3;
      this.mWidth = var4;
      this.mHeight = var5;
   }

   public static String eventNameForType(int var0) {
      switch(var0) {
      case 1:
         return "topError";
      case 2:
         return "topLoad";
      case 3:
         return "topLoadEnd";
      case 4:
         return "topLoadStart";
      case 5:
         return "topProgress";
      default:
         StringBuilder var1 = new StringBuilder();
         var1.append("Invalid image event: ");
         var1.append(Integer.toString(var0));
         throw new IllegalStateException(var1.toString());
      }
   }

   public void dispatch(RCTEventEmitter var1) {
      WritableMap var2;
      if(this.mImageUri == null && this.mEventType != 2) {
         var2 = null;
      } else {
         WritableMap var3 = Arguments.createMap();
         if(this.mImageUri != null) {
            var3.putString("uri", this.mImageUri);
         }

         var2 = var3;
         if(this.mEventType == 2) {
            var2 = Arguments.createMap();
            var2.putDouble("width", (double)this.mWidth);
            var2.putDouble("height", (double)this.mHeight);
            if(this.mImageUri != null) {
               var2.putString("url", this.mImageUri);
            }

            var3.putMap("source", var2);
            var2 = var3;
         }
      }

      var1.receiveEvent(this.getViewTag(), this.getEventName(), var2);
   }

   public short getCoalescingKey() {
      return (short)this.mEventType;
   }

   public String getEventName() {
      return eventNameForType(this.mEventType);
   }
}
