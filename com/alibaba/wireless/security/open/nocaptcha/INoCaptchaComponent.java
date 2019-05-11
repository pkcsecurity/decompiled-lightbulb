package com.alibaba.wireless.security.open.nocaptcha;

import android.os.Handler;
import android.view.MotionEvent;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;
import java.util.HashMap;

public interface INoCaptchaComponent extends IComponent {

   int NC_INIT_STAGE = 1;
   int NC_VERIFY_SATGE = 2;
   int SG_ERROR_NOCAPTCHA = 1200;
   int SG_NC_FAILED = 105;
   int SG_NC_GET_CONFIG_FAILED = 1213;
   int SG_NC_HTTP_NO_TOKEN = 1206;
   int SG_NC_HTTP_REQUEST_FAIL = 1207;
   int SG_NC_INIT_SUCCEED = 101;
   int SG_NC_INVALID_PARA = 1201;
   int SG_NC_NOT_INIT_YET = 1203;
   int SG_NC_NO_MEMORY = 1202;
   int SG_NC_QUEUE_FULL = 1204;
   int SG_NC_RETRY = 103;
   int SG_NC_RETRY_TO_MAX = 1205;
   int SG_NC_SERVER_FAULT = 104;
   int SG_NC_SERVER_RETURN_ERROR = 1208;
   int SG_NC_START = 100;
   int SG_NC_UNKNOWN_ERROR = 1299;
   int SG_NC_VERI_APPKEY_MISMATCH = 1211;
   int SG_NC_VERI_GET_TRACE_FAIL = 1210;
   int SG_NC_VERI_GET_WUA_FAIL = 1209;
   int SG_NC_VERI_SESSION_EXPIRED = 1212;
   int SG_NC_VERI_SUCCEED = 102;
   String errorCode = "errorCode";
   String sessionId = "sessionId";
   String sig = "sig";
   String status = "status";
   String token = "token";
   String x1 = "x1";
   String x2 = "x2";
   String y1 = "y1";
   String y2 = "y2";


   void initNoCaptcha(String var1, String var2, int var3, int var4, int var5, Handler var6, String var7) throws SecException;

   String noCaptchaForwardAuth(String var1, HashMap<String, String> var2, int var3) throws SecException;

   void noCaptchaVerification(String var1) throws SecException;

   boolean putNoCaptchaTraceRecord(MotionEvent var1) throws SecException;
}
