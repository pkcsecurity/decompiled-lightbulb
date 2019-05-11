package com.facebook.react.bridge.queue;


public class MessageQueueThreadSpec {

   public static final long DEFAULT_STACK_SIZE_BYTES = 0L;
   private static final MessageQueueThreadSpec MAIN_UI_SPEC = new MessageQueueThreadSpec(MessageQueueThreadSpec.ThreadType.MAIN_UI, "main_ui");
   private final String mName;
   private final long mStackSize;
   private final MessageQueueThreadSpec.ThreadType mThreadType;


   private MessageQueueThreadSpec(MessageQueueThreadSpec.ThreadType var1, String var2) {
      this(var1, var2, 0L);
   }

   private MessageQueueThreadSpec(MessageQueueThreadSpec.ThreadType var1, String var2, long var3) {
      this.mThreadType = var1;
      this.mName = var2;
      this.mStackSize = var3;
   }

   public static MessageQueueThreadSpec mainThreadSpec() {
      return MAIN_UI_SPEC;
   }

   public static MessageQueueThreadSpec newBackgroundThreadSpec(String var0) {
      return new MessageQueueThreadSpec(MessageQueueThreadSpec.ThreadType.NEW_BACKGROUND, var0);
   }

   public static MessageQueueThreadSpec newBackgroundThreadSpec(String var0, long var1) {
      return new MessageQueueThreadSpec(MessageQueueThreadSpec.ThreadType.NEW_BACKGROUND, var0, var1);
   }

   public static MessageQueueThreadSpec newUIBackgroundTreadSpec(String var0) {
      return new MessageQueueThreadSpec(MessageQueueThreadSpec.ThreadType.NEW_BACKGROUND, var0);
   }

   public String getName() {
      return this.mName;
   }

   public long getStackSize() {
      return this.mStackSize;
   }

   public MessageQueueThreadSpec.ThreadType getThreadType() {
      return this.mThreadType;
   }

   public static enum ThreadType {

      // $FF: synthetic field
      private static final MessageQueueThreadSpec.ThreadType[] $VALUES = new MessageQueueThreadSpec.ThreadType[]{MAIN_UI, NEW_BACKGROUND};
      MAIN_UI("MAIN_UI", 0),
      NEW_BACKGROUND("NEW_BACKGROUND", 1);


      private ThreadType(String var1, int var2) {}
   }
}
