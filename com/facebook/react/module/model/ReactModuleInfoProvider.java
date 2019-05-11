package com.facebook.react.module.model;

import com.facebook.react.module.model.ReactModuleInfo;
import java.util.Map;

public interface ReactModuleInfoProvider {

   Map<Class, ReactModuleInfo> getReactModuleInfos();
}
