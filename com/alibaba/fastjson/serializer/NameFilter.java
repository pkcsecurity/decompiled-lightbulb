package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.SerializeFilter;

public interface NameFilter extends SerializeFilter {

   String process(Object var1, String var2, Object var3);
}
