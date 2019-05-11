package com.facebook.react.devsupport.interfaces;

import android.util.Pair;
import com.facebook.react.devsupport.interfaces.StackFrame;

public interface ErrorCustomizer {

   Pair<String, StackFrame[]> customizeErrorInfo(Pair<String, StackFrame[]> var1);
}
