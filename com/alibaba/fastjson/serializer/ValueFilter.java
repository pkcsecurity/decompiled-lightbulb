package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.SerializeFilter;

public interface ValueFilter extends SerializeFilter {

   Object process(Object var1, String var2, Object var3);
}
