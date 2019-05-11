package com.facebook.react.bridge;

import com.facebook.react.bridge.JavaScriptExecutor;

public interface JavaScriptExecutorFactory {

   JavaScriptExecutor create() throws Exception;
}
