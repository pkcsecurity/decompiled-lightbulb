package com.alibaba.wireless.security.open.securesignature;

import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;

public interface ISecureSignatureComponent extends IComponent {

   String getSafeCookie(String var1, String var2, String var3) throws SecException;

   String signRequest(SecurityGuardParamContext var1, String var2) throws SecException;
}
