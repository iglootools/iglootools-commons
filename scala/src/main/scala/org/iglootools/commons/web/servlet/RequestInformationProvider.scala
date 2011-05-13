package org.iglootools.commons.web.servlet

import javax.servlet.http.HttpServletRequest

object RequestInformationProvider {
  def apply(request: HttpServletRequest, useModProxy:Boolean=false): RequestInformationProvider = {
    if(useModProxy) {
      new ModProxyRequestInformationProvider(request)
    } else {
      new HttpServletRequestInformationProvider(request)
    }
  }
}

trait RequestInformationProvider extends ClientInformationProvider
                                  with LocaleInformationProvider
                                  with ProxyInformationProvider
                                  with ServletRequestInformationProvider {

}