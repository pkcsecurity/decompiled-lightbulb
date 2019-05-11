package com.facebook.litho.sections.logger;

import com.facebook.litho.widget.RenderInfo;

public interface SectionsDebugLogger {

   void logDelete(String var1, int var2, String var3);

   void logInsert(String var1, int var2, RenderInfo var3, String var4);

   void logMove(String var1, int var2, int var3, String var4);

   void logRequestFocus(String var1, int var2, RenderInfo var3, String var4);

   void logRequestFocusWithOffset(String var1, int var2, int var3, RenderInfo var4, String var5);

   void logShouldUpdate(String var1, Object var2, Object var3, String var4, String var5, Boolean var6, String var7);

   void logUpdate(String var1, int var2, RenderInfo var3, String var4);
}
