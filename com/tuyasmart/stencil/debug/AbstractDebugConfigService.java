package com.tuyasmart.stencil.debug;

import android.content.Context;
import android.support.v4.util.Pair;
import com.tuya.smart.android.base.provider.ApiUrlProvider;
import com.tuyasmart.stencil.app.ApiConfig;

public abstract class AbstractDebugConfigService extends ajd {

   public abstract ApiUrlProvider getApiUrlProvider(Context var1);

   public abstract ApiConfig.EnvConfig getEnv();

   public abstract Pair<String, String> getH5Url();

   public abstract void saveDebugFile(String var1, String var2, boolean var3);
}
