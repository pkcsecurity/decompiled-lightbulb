package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.deserializer.ParseProcess;

public interface ExtraProcessor extends ParseProcess {

   void processExtra(Object var1, String var2, Object var3);
}
